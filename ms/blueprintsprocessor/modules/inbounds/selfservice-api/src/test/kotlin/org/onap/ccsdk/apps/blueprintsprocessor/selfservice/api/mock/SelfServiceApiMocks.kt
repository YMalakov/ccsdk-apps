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

package org.onap.ccsdk.apps.blueprintsprocessor.selfservice.api.mock

import org.onap.ccsdk.apps.blueprintsprocessor.core.api.data.ExecutionServiceInput
import org.onap.ccsdk.apps.blueprintsprocessor.core.api.data.ExecutionServiceOutput
import org.onap.ccsdk.apps.blueprintsprocessor.core.interfaces.BluePrintCatalogService
import org.onap.ccsdk.apps.blueprintsprocessor.services.workflow.BlueprintDGExecutionService
import org.onap.ccsdk.apps.controllerblueprints.core.service.BluePrintRuntimeService
import org.springframework.stereotype.Service
import kotlin.test.assertNotNull

@Service
class MockBlueprintDGExecutionService : BlueprintDGExecutionService {
    override fun executeDirectedGraph(bluePrintRuntimeService: BluePrintRuntimeService<*>, executionServiceInput: ExecutionServiceInput): ExecutionServiceOutput {

        assertNotNull(executionServiceInput, "failed to get executionServiceInput")

        val executionServiceOutput = ExecutionServiceOutput()
        executionServiceOutput.commonHeader = executionServiceInput.commonHeader
        return executionServiceOutput
    }
}

@Service
class MockBluePrintCatalogService : BluePrintCatalogService {
    override fun prepareBluePrint(name: String, version: String): String {
        assertNotNull(name, "failed to get blueprint Name")
        assertNotNull(version, "failed to get blueprint version")
        return "./../../../../../components/model-catalog/blueprint-model/starter-blueprint/baseconfiguration"
    }
}