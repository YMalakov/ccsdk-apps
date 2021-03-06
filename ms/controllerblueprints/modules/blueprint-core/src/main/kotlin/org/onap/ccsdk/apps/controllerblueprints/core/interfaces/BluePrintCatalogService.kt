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

package org.onap.ccsdk.apps.controllerblueprints.core.interfaces

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.onap.ccsdk.apps.controllerblueprints.core.BluePrintException
import java.io.File
import java.nio.file.Path

interface BluePrintCatalogService {

    /**
     * Save the CBA to database.
     * @param blueprintFile Either a directory, or an archive
     * @param validate whether to validate blueprint content. Default true.
     * @return The unique blueprint identifier
     * @throws BluePrintException if process failed
     */
    @NotNull
    @Throws(BluePrintException::class)
    fun saveToDatabase(@NotNull blueprintFile: File, @Nullable validate: Boolean = true): String

    /**
     * Retrieve the CBA from database either archived or extracted.
     * @param name Name of the blueprint
     * @param version Version of the blueprint
     * @param extract true to extract the content, false for archived content. Default to true
     * @return Path where CBA is located
     * @throws BluePrintException if process failed
     */
    @NotNull
    @Throws(BluePrintException::class)
    fun getFromDatabase(@NotNull name: String, @NotNull version: String, @Nullable extract: Boolean = true): Path

    /**
     * Delete the CBA from database.
     * @param name Name of the blueprint
     * @param version Version of the blueprint
     * @throws BluePrintException if process failed
     */
    @NotNull
    @Throws(BluePrintException::class)
    fun deleteFromDatabase(@NotNull name: String, @NotNull version: String)
}