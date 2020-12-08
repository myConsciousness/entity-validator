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
import java.util.Set;

import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the recoverable {@link NestedEntity} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
final class RecoverableNestedEntityForTest implements ValidatableEntity {

    /**
     * The non empty list
     */
    @RequireNonEmpty(errorType = ErrorType.RECOVERABLE, message = "first layer 1")
    private List<String> literalList;

    /**
     * The non empty set
     */
    @RequireNonEmpty(errorType = ErrorType.RECOVERABLE, message = "first layer 2")
    private Set<String> literalSet;

    /**
     * The nested entity of {@link RequireNegativeForTest}
     */
    @NestedEntity
    private RecoverableRequireNegativeForTest recoverableRequireNegativeForTest;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private RecoverableNestedEntityForTest() {
    }

    /**
     * Constructor
     *
     * @param literalList                       The literal list
     * @param literalSet                        The literal set
     * @param recoverableRequireNegativeForTest The nested entity
     */
    public RecoverableNestedEntityForTest(List<String> literalList, Set<String> literalSet,
            RecoverableRequireNegativeForTest recoverableRequireNegativeForTest) {
        this.literalSet = literalSet;
        this.literalList = literalList;
        this.recoverableRequireNegativeForTest = recoverableRequireNegativeForTest;
    }
}
