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

import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the {@link NestedEntity} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
final class NestedEntityWithNotValidatableGenericEntityForTest implements ValidatableEntity {

    /**
     * The entity with generic
     */
    @NestedEntity
    private List<String> genericEntity;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private NestedEntityWithNotValidatableGenericEntityForTest() {
    }

    /**
     * Constructor
     *
     * @param genericEntity The entity with generic type
     */
    public NestedEntityWithNotValidatableGenericEntityForTest(List<String> genericEntity) {
        this.genericEntity = genericEntity;
    }
}
