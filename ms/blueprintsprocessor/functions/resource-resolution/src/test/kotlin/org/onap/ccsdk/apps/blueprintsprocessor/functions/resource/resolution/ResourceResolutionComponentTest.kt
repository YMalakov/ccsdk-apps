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

package org.onap.ccsdk.apps.blueprintsprocessor.functions.resource.resolution

import com.fasterxml.jackson.databind.JsonNode
import org.junit.Test
import org.junit.runner.RunWith
import org.onap.ccsdk.apps.blueprintsprocessor.core.api.data.ExecutionServiceInput
import org.onap.ccsdk.apps.blueprintsprocessor.core.utils.PayloadUtils
import org.onap.ccsdk.apps.blueprintsprocessor.functions.resource.resolutionprocessor.DataBaseResourceAssignmentProcessor
import org.onap.ccsdk.apps.blueprintsprocessor.functions.resource.resolutionprocessor.DefaultResourceAssignmentProcessor
import org.onap.ccsdk.apps.blueprintsprocessor.functions.resource.resolutionprocessor.InputResourceAssignmentProcessor
import org.onap.ccsdk.apps.blueprintsprocessor.functions.resource.resolutionprocessor.SimpleRestResourceAssignmentProcessor
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintConstants
import org.onap.ccsdk.apps.controllerblueprints.core.asJsonNode
import org.onap.ccsdk.apps.controllerblueprints.core.putJsonElement
import org.onap.ccsdk.apps.controllerblueprints.core.utils.BluePrintMetadataUtils
import org.onap.ccsdk.apps.controllerblueprints.core.utils.JacksonUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ResourceResolutionComponent::class, ResourceResolutionService::class,
    InputResourceAssignmentProcessor::class, DefaultResourceAssignmentProcessor::class,
    DataBaseResourceAssignmentProcessor::class, SimpleRestResourceAssignmentProcessor::class])
class ResourceResolutionComponentTest {

    @Autowired
    lateinit var resourceResolutionComponent: ResourceResolutionComponent

    @Test
    fun testProcess() {

        val bluePrintRuntimeService = BluePrintMetadataUtils.getBluePrintRuntime("1234",
                "./../../../../components/model-catalog/blueprint-model/starter-blueprint/baseconfiguration")

        val executionServiceInput = JacksonUtils.readValueFromClassPathFile("payload/requests/sample-resourceresolution-request.json",
                ExecutionServiceInput::class.java)!!

        // Prepare Inputs
        PayloadUtils.prepareInputsFromWorkflowPayload(bluePrintRuntimeService, executionServiceInput.payload, "resource-assignment")

        val stepMetaData: MutableMap<String, JsonNode> = hashMapOf()
        stepMetaData.putJsonElement(BluePrintConstants.PROPERTY_CURRENT_NODE_TEMPLATE, "resource-assignment")
        stepMetaData.putJsonElement(BluePrintConstants.PROPERTY_CURRENT_INTERFACE, "ResourceAssignmentComponent")
        stepMetaData.putJsonElement(BluePrintConstants.PROPERTY_CURRENT_OPERATION, "process")
        bluePrintRuntimeService.put("resource-assignment-step-inputs", stepMetaData.asJsonNode())

        resourceResolutionComponent.bluePrintRuntimeService = bluePrintRuntimeService
        resourceResolutionComponent.stepName = "resource-assignment"
        resourceResolutionComponent.apply(executionServiceInput)
    }
}