/*
 * Copyright © 2017-2018 AT&T Intellectual Property.
 * Modifications Copyright © 2019 Bell Canada.
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

package org.onap.ccsdk.apps.controllerblueprints.db.resources.repository

import org.jetbrains.annotations.NotNull
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

/**
 * @param <T> Model
 */
@NoRepositoryBean
interface ModelRepository<T> : JpaRepository<T, String> {

    /**
     * This is a findById method
     *
     * @param id id
     * @return Optional<T>
     */
    @NotNull
    override fun findById(@NotNull id: String): Optional<T>

    /**
     * This is a findByArtifactNameAndArtifactVersion method
     *
     * @param artifactName artifactName
     * @param artifactVersion artifactVersion
     * @return Optional<T>
     */
    fun findByArtifactNameAndArtifactVersion(artifactName: String, artifactVersion: String): Optional<T>

    /**
     * This is a findTopByArtifactNameOrderByArtifactIdDesc method
     *
     * @param artifactName artifactName
     * @return Optional<T>
     */
    fun findTopByArtifactNameOrderByArtifactVersionDesc(artifactName: String): Optional<T>

    /**
     * This is a findTopByArtifactName method
     *
     * @param artifactName artifactName
     * @return Optional<T>
     */
    fun findTopByArtifactName(artifactName: String): List<T>

    /**
     * This is a findByTagsContainingIgnoreCase method
     *
     * @param tags tags
     * @return Optional<ModelType>
     */
    fun findByTagsContainingIgnoreCase(tags: String): List<T>

    /**
     * This is a deleteByArtifactNameAndArtifactVersion method
     *
     * @param artifactName artifactName
     * @param artifactVersion artifactVersion
     */
    fun deleteByArtifactNameAndArtifactVersion(artifactName: String, artifactVersion: String)

    /**
     * This is a deleteById method
     *
     * @param id id
     */
    override fun deleteById(@NotNull id: String)

}