/*
 * Copyright © 2017-2018 AT&T Intellectual Property.
 * Modifications Copyright © 2018 IBM.
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

package org.onap.ccsdk.apps.controllerblueprints.resource.dict.utils;


import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Assert;
import org.junit.Test;
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintConstants;
import org.onap.ccsdk.apps.controllerblueprints.core.data.NodeTemplate;
import org.onap.ccsdk.apps.controllerblueprints.core.utils.JacksonUtils;
import org.onap.ccsdk.apps.controllerblueprints.resource.dict.ResourceAssignment;
import org.onap.ccsdk.apps.controllerblueprints.resource.dict.ResourceDefinition;
import org.onap.ccsdk.apps.controllerblueprints.resource.dict.ResourceDictionaryConstants;
import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;

import java.util.HashMap;
import java.util.Map;
/**
 * ResourceDictionaryUtilsTest.
 *
 * @author Brinda Santh
 */
public class ResourceDictionaryUtilsTest {
    private static final EELFLogger log = EELFManager.getInstance().getLogger(ResourceDictionaryUtilsTest.class);

    @Test
    public void testPopulateSourceMapping() {

        ResourceAssignment resourceAssignment = new ResourceAssignment();
        resourceAssignment.setName("sample-assignment");
        ResourceDefinition resourceDefinition = new ResourceDefinition();
        Map<String, NodeTemplate> sources = new HashMap<>();
        resourceDefinition.setSources(sources);
        // To Check Empty Source
        ResourceDictionaryUtils.populateSourceMapping(resourceAssignment, resourceDefinition);
        Assert.assertEquals("Expected Empty source Input, but.", ResourceDictionaryConstants.SOURCE_INPUT, resourceAssignment.getDictionarySource());

        // To Check First Source
        resourceAssignment.setDictionarySource(null);
        sources.put(ResourceDictionaryConstants.SOURCE_DEFAULT, new NodeTemplate());
        ResourceDictionaryUtils.populateSourceMapping(resourceAssignment, resourceDefinition);
        Assert.assertEquals("Expected First source Default, but.", ResourceDictionaryConstants.SOURCE_DEFAULT, resourceAssignment.getDictionarySource());

        // To Check Assigned Source
        resourceAssignment.setDictionarySource(ResourceDictionaryConstants.SOURCE_PRIMARY_DB);
        ResourceDictionaryUtils.populateSourceMapping(resourceAssignment, resourceDefinition);
        Assert.assertEquals("Expected Assigned source DB, but.", ResourceDictionaryConstants.SOURCE_PRIMARY_DB, resourceAssignment.getDictionarySource());

    }

    @Test
    public void testFindFirstSource() {
        //To check if Empty Source
        Map<String, NodeTemplate> sources = new HashMap<>();
        String firstSource = ResourceDictionaryUtils.findFirstSource(sources);
        Assert.assertNull("Source populated, which is not expected.", firstSource);

        // TO check the first Source
        sources.put(ResourceDictionaryConstants.SOURCE_INPUT, new NodeTemplate());
        String inputFirstSource = ResourceDictionaryUtils.findFirstSource(sources);
        Assert.assertEquals("Expected source Input, but.", ResourceDictionaryConstants.SOURCE_INPUT, inputFirstSource);

        // TO check the multiple Source
        sources.put(ResourceDictionaryConstants.SOURCE_PRIMARY_DB, new NodeTemplate());
        String multipleFirstSource = ResourceDictionaryUtils.findFirstSource(sources);
        Assert.assertEquals("Expected source Input, but.", ResourceDictionaryConstants.SOURCE_INPUT, multipleFirstSource);

    }

    @Test
    public void testAssignInputs() {
        JsonNode data = JacksonUtils.Companion.jsonNodeFromClassPathFile("data/resource-assignment-input.json");
        Map<String, Object> context = new HashMap<>();
        ResourceDictionaryUtils.assignInputs(data, context);
        String path = BluePrintConstants.PATH_INPUTS.concat(BluePrintConstants.PATH_DIVIDER).concat("mapValue");
        log.info("populated context {}", context);
        Assert.assertTrue(String.format("failed to get variable : %s",path),context.containsKey(path));

    }


}
