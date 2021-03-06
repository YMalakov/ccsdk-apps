/*-
 * ============LICENSE_START=======================================================
 * ONAP : CCSDK.apps
 * ================================================================================
 * Copyright (C) 2018 IBM.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.ccsdk.apps.ms.neng.core.resource.model;

import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {
    private HelloWorld helloWorld;

    @Test
    public void testGetSetMessage() {
        helloWorld = new HelloWorld();
        helloWorld.setMessage("new Message");
        Assert.assertEquals("new Message", helloWorld.getMessage());
    }

    @Test
    public void testToString() {
        helloWorld = new HelloWorld("new Message");
        Assert.assertEquals("message = new Message", helloWorld.toString());
    }
}
