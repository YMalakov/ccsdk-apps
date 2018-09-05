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

package org.onap.ccsdk.apps.controllerblueprints.core

/**
 *
 *
 * @author Brinda Santh
 */
object BluePrintTypes {

    @JvmStatic
    val validNodeTypeDerivedFroms: MutableList<String> = arrayListOf(
            BluePrintConstants.MODEL_TYPE_NODES_ROOT,
            BluePrintConstants.MODEL_TYPE_NODE_DG,
            BluePrintConstants.MODEL_TYPE_NODE_COMPONENT,
            BluePrintConstants.MODEL_TYPE_NODE_VNF,
            BluePrintConstants.MODEL_TYPE_NODE_ARTIFACT,
            BluePrintConstants.MODEL_TYPE_NODE_RESOURCE_SOURCE,
            BluePrintConstants.MODEL_TYPE_NODES_COMPONENT_JAVA,
            BluePrintConstants.MODEL_TYPE_NODES_COMPONENT_BUNDLE,
            BluePrintConstants.MODEL_TYPE_NODES_COMPONENT_SCRIPT,
            BluePrintConstants.MODEL_TYPE_NODES_COMPONENT_PYTHON,
            BluePrintConstants.MODEL_TYPE_NODES_COMPONENT_JAVA_SCRIPT
    )

    @JvmStatic
    val validArtifactTypeDerivedFroms: MutableList<String> = arrayListOf(
            BluePrintConstants.MODEL_TYPE_ARTIFACTS_ROOT,
            BluePrintConstants.MODEL_TYPE_ARTIFACT_TYPE_IMPLEMENTATION
    )

    @JvmStatic
    val validDataTypeDerivedFroms: MutableList<String> = arrayListOf(
            BluePrintConstants.MODEL_TYPE_DATATYPES_ROOT,
            BluePrintConstants.MODEL_TYPE_DATA_TYPE_DYNAMIC
    )

    @JvmStatic
    fun validModelTypes(): List<String> {
        val validTypes: MutableList<String> = arrayListOf()
        validTypes.add(BluePrintConstants.MODEL_DEFINITION_TYPE_DATA_TYPE)
        validTypes.add(BluePrintConstants.MODEL_DEFINITION_TYPE_ARTIFACT_TYPE)
        validTypes.add(BluePrintConstants.MODEL_DEFINITION_TYPE_NODE_TYPE)
        validTypes.add(BluePrintConstants.MODEL_DEFINITION_TYPE_CAPABILITY_TYPE)
        validTypes.add(BluePrintConstants.MODEL_DEFINITION_TYPE_RELATIONSHIP_TYPE)
        return validTypes
    }

    @JvmStatic
    fun validPropertyTypes(): List<String> {
        val validTypes: MutableList<String> = arrayListOf()
        validTypes.add(BluePrintConstants.DATA_TYPE_STRING)
        validTypes.add(BluePrintConstants.DATA_TYPE_INTEGER)
        validTypes.add(BluePrintConstants.DATA_TYPE_FLOAT)
        validTypes.add(BluePrintConstants.DATA_TYPE_BOOLEAN)
        validTypes.add(BluePrintConstants.DATA_TYPE_TIMESTAMP)
        validTypes.add(BluePrintConstants.DATA_TYPE_NULL)
        validTypes.add(BluePrintConstants.DATA_TYPE_LIST)
        return validTypes
    }

    @JvmStatic
    fun validPrimitiveTypes(): List<String> {
        val validTypes: MutableList<String> = arrayListOf()
        validTypes.add(BluePrintConstants.DATA_TYPE_STRING)
        validTypes.add(BluePrintConstants.DATA_TYPE_INTEGER)
        validTypes.add(BluePrintConstants.DATA_TYPE_FLOAT)
        validTypes.add(BluePrintConstants.DATA_TYPE_BOOLEAN)
        validTypes.add(BluePrintConstants.DATA_TYPE_TIMESTAMP)
        validTypes.add(BluePrintConstants.DATA_TYPE_NULL)
        return validTypes
    }

    @JvmStatic
    fun validCollectionTypes(): List<String> {
        val validTypes: MutableList<String> = arrayListOf()
        validTypes.add(BluePrintConstants.DATA_TYPE_LIST)
        validTypes.add(BluePrintConstants.DATA_TYPE_MAP)
        return validTypes
    }

    @JvmStatic
    fun validCommands(): List<String> {
        return listOf(BluePrintConstants.EXPRESSION_GET_INPUT,
                BluePrintConstants.EXPRESSION_GET_ATTRIBUTE,
                BluePrintConstants.EXPRESSION_GET_PROPERTY,
                BluePrintConstants.EXPRESSION_GET_ARTIFACT,
                BluePrintConstants.EXPRESSION_GET_OPERATION_OUTPUT,
                BluePrintConstants.EXPRESSION_GET_NODE_OF_TYPE)
    }

    @JvmStatic
    fun rootNodeTypes(): List<String> {
        return listOf(BluePrintConstants.MODEL_TYPE_NODES_ROOT)
    }

    @JvmStatic
    fun rootDataTypes(): List<String> {
        return listOf(BluePrintConstants.MODEL_TYPE_DATATYPES_ROOT)
    }


}