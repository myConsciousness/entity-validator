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

import org.thinkit.framework.envali.catalog.ValidationPattern;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.NonNull;

/**
 * The abstract factory class that abstracts the process of generating the
 * strategy class for the validation process.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
public interface StrategyFactory {

    /**
     * Returns the validatable entity.
     *
     * @return The validatable entity to be validated
     */
    public ValidatableEntity getValidatableEntity();

    /**
     * Returns the field.
     *
     * @return The field to be validated
     */
    public Field getField();

    /**
     * Returns the validation strategy corresponding to the pattern based on the
     * {@link ValidationPattern} passed as an argument.
     *
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     * @return The validation strategy corresponding to the pattern based on the
     *         {@link ValidationPattern} passed as an argument
     */
    public ValidationStrategy createValidationStrategy(@NonNull ValidationPattern validationPattern);
}
