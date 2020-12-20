/*
 * Copyright 2020 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.framework.envali;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the {@link NestedEntity} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
final class NestedParameterizedValidatableEntityForTest implements ValidatableEntity {

    /**
     * The nested entity
     */
    @NestedEntity
    private List<RequireNegativeForTest> requireNegativeForTestList;

    /**
     * The nested entity
     */
    @NestedEntity
    private Map<String, RecoverableRequireNegativeForTest> requireNegativeForTestMap;

    /**
     * The nested entity
     */
    @NestedEntity
    private Set<UnrecoverableRequireNegativeForTest> requireNegativeForTestSet;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private NestedParameterizedValidatableEntityForTest() {
    }

    /**
     * Constructor
     *
     * @param requireNegativeForTestList The nested entity of list
     * @param requireNegativeForTestMap The nested entity of map
     * @param requireNegativeForTestSet The nested entity of set
     */
    public NestedParameterizedValidatableEntityForTest(List<RequireNegativeForTest> requireNegativeForTestList,
            Map<String, RecoverableRequireNegativeForTest> requireNegativeForTestMap,
            Set<UnrecoverableRequireNegativeForTest> requireNegativeForTestSet) {
        this.requireNegativeForTestList = requireNegativeForTestList;
        this.requireNegativeForTestMap = requireNegativeForTestMap;
        this.requireNegativeForTestSet = requireNegativeForTestSet;
    }
}
