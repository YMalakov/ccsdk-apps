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

package org.onap.ccsdk.apps.blueprintsprocessor.db

import org.onap.ccsdk.apps.blueprintsprocessor.core.BluePrintProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
@EnableConfigurationProperties
open class BluePrintDBLibConfiguration(private var bluePrintProperties: BluePrintProperties) {

    @Bean("primary-database-properties")
    open fun getPrimaryProperties(): PrimaryDataSourceProperties {
        return bluePrintProperties.propertyBeanType(DBLibConstants.PREFIX_DB_PRIMARY,
                PrimaryDataSourceProperties::class.java)
    }
}

class DBLibConstants {
    companion object {
        const val PREFIX_DB_PRIMARY: String = "blueprintsprocessor.db.primary"
    }
}