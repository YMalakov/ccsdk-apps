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

package org.onap.ccsdk.apps.controllerblueprints.service.enhancer

import com.att.eelf.configuration.EELFLogger
import com.att.eelf.configuration.EELFManager
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintError
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintException
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintTypes
import org.onap.ccsdk.apps.controllerblueprints.core.data.InterfaceDefinition
import org.onap.ccsdk.apps.controllerblueprints.core.data.NodeType
import org.onap.ccsdk.apps.controllerblueprints.core.data.OperationDefinition
import org.onap.ccsdk.apps.controllerblueprints.core.format
import org.onap.ccsdk.apps.controllerblueprints.core.interfaces.BluePrintNodeTypeEnhancer
import org.onap.ccsdk.apps.controllerblueprints.core.interfaces.BluePrintTypeEnhancerService
import org.onap.ccsdk.apps.controllerblueprints.core.service.BluePrintContext
import org.onap.ccsdk.apps.controllerblueprints.core.service.BluePrintRepoService
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
open class BluePrintNodeTypeEnhancerImpl(private val bluePrintRepoService: BluePrintRepoService,
                                         private val bluePrintTypeEnhancerService: BluePrintTypeEnhancerService) : BluePrintNodeTypeEnhancer {

    private val log: EELFLogger = EELFManager.getInstance().getLogger(BluePrintNodeTypeEnhancerImpl::class.toString())

    lateinit var bluePrintContext: BluePrintContext
    lateinit var error: BluePrintError

    override fun enhance(bluePrintContext: BluePrintContext, error: BluePrintError, name: String, nodeType: NodeType) {
        this.bluePrintContext = bluePrintContext
        this.error = error

        val derivedFrom = nodeType.derivedFrom

        if (!BluePrintTypes.rootNodeTypes().contains(derivedFrom)) {
            val derivedFromNodeType = populateNodeType(name)
            // Enrich NodeType
            enhance(bluePrintContext, error, derivedFrom, derivedFromNodeType)
        }

        // NodeType Property Definitions
        enrichNodeTypeProperties(name, nodeType)

        //NodeType Requirement
        enrichNodeTypeRequirements(name, nodeType)

        //NodeType Capability
        enrichNodeTypeCapabilityProperties(name, nodeType)

        //NodeType Interface
        enrichNodeTypeInterfaces(name, nodeType)

    }

    open fun enrichNodeTypeProperties(nodeTypeName: String, nodeType: NodeType) {
        nodeType.properties?.let {
            bluePrintTypeEnhancerService.enhancePropertyDefinitions(bluePrintContext, error, nodeType.properties!!)
        }
    }

    open fun enrichNodeTypeRequirements(nodeTypeName: String, nodeType: NodeType) {

        nodeType.requirements?.forEach { _, requirementDefinition ->
            // Populate Requirement Node
            requirementDefinition.node?.let { requirementNodeTypeName ->
                // Get Requirement NodeType from Repo and Update Service Template
                val requirementNodeType = populateNodeType(requirementNodeTypeName)
                // Enhanypece Node T
                enhance(bluePrintContext, error, requirementNodeTypeName, requirementNodeType)
            }
        }
    }

    open fun enrichNodeTypeCapabilityProperties(nodeTypeName: String, nodeType: NodeType) {
        nodeType.capabilities?.forEach { _, capabilityDefinition ->
            capabilityDefinition.properties?.let { properties ->
                bluePrintTypeEnhancerService.enhancePropertyDefinitions(bluePrintContext, error, properties)
            }
        }
    }

    open fun enrichNodeTypeInterfaces(nodeTypeName: String, nodeType: NodeType) {
        nodeType.interfaces?.forEach { interfaceName, interfaceObj ->
            // Populate Node type Interface Operation
            log.debug("Enriching NodeType({}) Interface({})", nodeTypeName, interfaceName)
            populateNodeTypeInterfaceOperation(nodeTypeName, interfaceName, interfaceObj)

        }
    }

    open fun populateNodeTypeInterfaceOperation(nodeTypeName: String, interfaceName: String, interfaceObj: InterfaceDefinition) {

        interfaceObj.operations?.forEach { operationName, operation ->
            enrichNodeTypeInterfaceOperationInputs(nodeTypeName, operationName, operation)
            enrichNodeTypeInterfaceOperationOputputs(nodeTypeName, operationName, operation)
        }
    }

    open fun enrichNodeTypeInterfaceOperationInputs(nodeTypeName: String, operationName: String, operation: OperationDefinition) {
        operation.inputs?.let { inputs ->
            bluePrintTypeEnhancerService.enhancePropertyDefinitions(bluePrintContext, error, inputs)
        }
    }

    open fun enrichNodeTypeInterfaceOperationOputputs(nodeTypeName: String, operationName: String, operation: OperationDefinition) {
        operation.outputs?.let { inputs ->
            bluePrintTypeEnhancerService.enhancePropertyDefinitions(bluePrintContext, error, inputs)
        }
    }

    open fun populateNodeType(nodeTypeName: String): NodeType {

        val nodeType = bluePrintContext.serviceTemplate.nodeTypes?.get(nodeTypeName)
                ?: bluePrintRepoService.getNodeType(nodeTypeName)
                ?: throw BluePrintException(format("Couldn't get NodeType({}) from repo.", nodeTypeName))
        bluePrintContext.serviceTemplate.nodeTypes?.put(nodeTypeName, nodeType)
        return nodeType
    }

}