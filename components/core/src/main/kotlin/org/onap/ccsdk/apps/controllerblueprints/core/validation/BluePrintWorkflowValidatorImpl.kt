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

package org.onap.ccsdk.apps.controllerblueprints.core.validation

import com.att.eelf.configuration.EELFLogger
import com.att.eelf.configuration.EELFManager
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintConstants
import org.onap.ccsdk.apps.controllerblueprints.core.data.Workflow
import org.onap.ccsdk.apps.controllerblueprints.core.interfaces.BluePrintTypeValidatorService
import org.onap.ccsdk.apps.controllerblueprints.core.interfaces.BluePrintWorkflowValidator
import org.onap.ccsdk.apps.controllerblueprints.core.service.BluePrintRuntimeService

open class BluePrintWorkflowValidatorImpl(private val bluePrintTypeValidatorService: BluePrintTypeValidatorService) : BluePrintWorkflowValidator {

    private val log: EELFLogger = EELFManager.getInstance().getLogger(BluePrintServiceTemplateValidatorImpl::class.toString())
    lateinit var bluePrintRuntimeService: BluePrintRuntimeService<*>

    var paths: MutableList<String> = arrayListOf()

    override fun validate(bluePrintRuntimeService: BluePrintRuntimeService<*>, workflowName: String, workflow: Workflow) {
        log.info("Validating Workflow($workflowName)")

        this.bluePrintRuntimeService = bluePrintRuntimeService


        paths.add(workflowName)
        paths.joinToString(BluePrintConstants.PATH_DIVIDER)

        // Step Validation Start
        paths.add("steps")
        workflow.steps?.forEach { stepName, _ ->
            paths.add(stepName)
            paths.joinToString(BluePrintConstants.PATH_DIVIDER)
            // TODO("Step Validation")
            paths.removeAt(paths.lastIndex)
        }
        paths.removeAt(paths.lastIndex)
        // Step Validation Ends
        paths.removeAt(paths.lastIndex)
    }
}