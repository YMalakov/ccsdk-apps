/*******************************************************************************
 * Copyright © 2018 IBM.
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
 ******************************************************************************
*/

package org.onap.ccsdk.apps.ms.vlantagapi.core.extinf.pm.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PolicyContentTest {

    public PolicyContent policyContent;

    @Before
    public void setUp() {
        policyContent = new PolicyContent();
    }

    @Test
    public void testGetSetPolicyInstanceName() {
        policyContent.setPolicyInstanceName("policyInstanceName");
        assertEquals("policyInstanceName", policyContent.getPolicyInstanceName());
    }
    
    @Test
    public void testToString()
    {
        assertTrue(policyContent.toString() instanceof String);
    }

}
