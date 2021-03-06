/*
 * Copyright © 2017-2018 AT&T Intellectual Property.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.ccsdk.apps.blueprintsprocessor.services.workflow.executor

import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.onap.ccsdk.apps.blueprintsprocessor.core.api.data.ExecutionServiceInput
import org.onap.ccsdk.apps.blueprintsprocessor.services.execution.AbstractComponentFunction
import org.onap.ccsdk.apps.blueprintsprocessor.services.workflow.BlueprintSvcLogicContext
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintConstants
import org.onap.ccsdk.apps.controllerblueprints.core.asJsonNode
import org.onap.ccsdk.apps.controllerblueprints.core.putJsonElement
import org.onap.ccsdk.sli.core.sli.SvcLogicContext
import org.onap.ccsdk.sli.core.sli.SvcLogicException
import org.onap.ccsdk.sli.core.sli.SvcLogicNode
import org.onap.ccsdk.sli.core.sli.provider.base.ExecuteNodeExecutor
import org.onap.ccsdk.sli.core.sli.provider.base.SvcLogicExpressionResolver
import org.onap.ccsdk.sli.core.sli.provider.base.SvcLogicServiceBase
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
open class ComponentExecuteNodeExecutor : ExecuteNodeExecutor() {

    private val log = LoggerFactory.getLogger(ComponentExecuteNodeExecutor::class.java)

    @Autowired
    private lateinit var context: ApplicationContext

    fun getComponentFunction(pluginName: String): AbstractComponentFunction {
        return context.getBean(pluginName, AbstractComponentFunction::class.java)
    }

    @Throws(SvcLogicException::class)
    override fun execute(svc: SvcLogicServiceBase, node: SvcLogicNode, svcLogicContext: SvcLogicContext)
            : SvcLogicNode = runBlocking {
        coroutineScope {
            val job = async { executeAsy(svc, node, svcLogicContext) }
            job.await()
        }
    }


    private fun executeAsy(svc: SvcLogicServiceBase, node: SvcLogicNode, svcLogicContext: SvcLogicContext): SvcLogicNode {

        var outValue: String

        val ctx = svcLogicContext as BlueprintSvcLogicContext

        val nodeTemplateName = SvcLogicExpressionResolver.evaluate(node.getAttribute("plugin"), node, ctx)

        try {
            // Get the Blueprint Context
            val blueprintContext = ctx.getBluePrintService().bluePrintContext()
            // Get the Component Name, NodeTemplate type is mapped to Component Name
            val componentName = blueprintContext.nodeTemplateByName(nodeTemplateName).type

            val interfaceName = blueprintContext.nodeTemplateFirstInterfaceName(nodeTemplateName)

            val operationName = blueprintContext.nodeTemplateFirstInterfaceFirstOperationName(nodeTemplateName)

            log.info("executing node template($nodeTemplateName) component($componentName) interface($interfaceName) operation($operationName)")
            // Get the Component Instance
            val plugin = this.getComponentFunction(componentName)
            // Set the Blueprint Service
            plugin.bluePrintRuntimeService = ctx.getBluePrintService()
            plugin.stepName = nodeTemplateName

            val executionInput = ctx.getRequest() as ExecutionServiceInput

            // Populate Step Meta Data
            val stepInputs: MutableMap<String, JsonNode> = hashMapOf()
            stepInputs.putJsonElement(BluePrintConstants.PROPERTY_CURRENT_NODE_TEMPLATE, nodeTemplateName)
            stepInputs.putJsonElement(BluePrintConstants.PROPERTY_CURRENT_INTERFACE, interfaceName)
            stepInputs.putJsonElement(BluePrintConstants.PROPERTY_CURRENT_OPERATION, operationName)

            plugin.bluePrintRuntimeService.put("$nodeTemplateName-step-inputs", stepInputs.asJsonNode())

            // Get the Request from the Context and Set to the Function Input and Invoke the function
            val executionOutput = plugin.apply(executionInput)

            ctx.setResponse(executionOutput)

            outValue = executionOutput.status.message
            ctx.status = executionOutput.status.message

        } catch (e: Exception) {
            this.log.error("Could not execute plugin($nodeTemplateName) : ", e)
            outValue = "failure"
            ctx.status = "failure"
        }

        return this.getNextNode(node, outValue)
    }
}