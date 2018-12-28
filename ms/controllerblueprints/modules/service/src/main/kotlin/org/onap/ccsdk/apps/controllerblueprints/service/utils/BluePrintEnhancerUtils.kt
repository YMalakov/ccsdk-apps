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

package org.onap.ccsdk.apps.controllerblueprints.service.utils

import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintException
import org.onap.ccsdk.apps.controllerblueprints.core.data.ArtifactType
import org.onap.ccsdk.apps.controllerblueprints.core.data.DataType
import org.onap.ccsdk.apps.controllerblueprints.core.data.NodeType
import org.onap.ccsdk.apps.controllerblueprints.core.format
import org.onap.ccsdk.apps.controllerblueprints.core.interfaces.BluePrintRepoService
import org.onap.ccsdk.apps.controllerblueprints.core.service.BluePrintContext


class BluePrintEnhancerUtils {
    companion object {

        fun populateDataTypes(bluePrintContext: BluePrintContext, bluePrintRepoService: BluePrintRepoService,
                              dataTypeName: String): DataType {
            val dataType = bluePrintContext.serviceTemplate.dataTypes?.get(dataTypeName)
                    ?: bluePrintRepoService.getDataType(dataTypeName)
                    ?: throw BluePrintException("couldn't get DataType($dataTypeName) from repo.")
            bluePrintContext.serviceTemplate.dataTypes?.put(dataTypeName, dataType)
            return dataType
        }


        fun populateNodeType(bluePrintContext: BluePrintContext, bluePrintRepoService: BluePrintRepoService,
                             nodeTypeName: String): NodeType {

            val nodeType = bluePrintContext.serviceTemplate.nodeTypes?.get(nodeTypeName)
                    ?: bluePrintRepoService.getNodeType(nodeTypeName)
                    ?: throw BluePrintException(format("Couldn't get NodeType({}) from repo.", nodeTypeName))
            bluePrintContext.serviceTemplate.nodeTypes?.put(nodeTypeName, nodeType)
            return nodeType
        }

        fun populateArtifactType(bluePrintContext: BluePrintContext, bluePrintRepoService: BluePrintRepoService,
                                 artifactTypeName: String): ArtifactType {

            val artifactType = bluePrintContext.serviceTemplate.artifactTypes?.get(artifactTypeName)
                    ?: bluePrintRepoService.getArtifactType(artifactTypeName)
                    ?: throw BluePrintException("couldn't get ArtifactType($artifactTypeName) from repo.")
            bluePrintContext.serviceTemplate.artifactTypes?.put(artifactTypeName, artifactType)
            return artifactType
        }
    }
}