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

package org.thinkit.framework.envali.strategy;

import java.lang.reflect.Field;

import org.thinkit.framework.envali.Envali;
import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A strategy to perform validation process for {@link NestedEntity} annotation.
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class NestedEntityStrategy extends ValidationStrategy {

    /**
     * Constructor
     *
     * @param entity The entity for validation
     * @param field  The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private NestedEntityStrategy(@NonNull ValidatableEntity entity, @NonNull Field field) {
        super(entity, field);
    }

    /**
     * Returns the new instance of {@link NestedEntityStrategy} class.
     *
     * @param entity The entity for validation
     * @param field  The field for validation
     * @return The new instance of {@link NestedEntityStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static ValidationStrategy of(@NonNull ValidatableEntity entity, @NonNull Field field) {
        return new NestedEntityStrategy(entity, field);
    }

    @Override
    public void validate() {
        Envali.validate(super.getEntity());
    }
}