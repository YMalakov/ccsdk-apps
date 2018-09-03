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

package org.onap.ccsdk.apps.controllerblueprints;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author Brinda Santh
 */
@SpringBootApplication
@EnableWebFlux
@ComponentScan(basePackages = {"org.onap.ccsdk.apps.controllerblueprints"})
@EnableAutoConfiguration
public class ControllerBluprintsApplication {
    private static EELFLogger log = EELFManager.getInstance().getLogger(ControllerBluprintsApplication.class);

    public static void main(String[] args) {
        log.info("****** Starting Controlled Blueprints Application ******");
        SpringApplication.run(ControllerBluprintsApplication.class, args);
    }

}