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

import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.helper.EnvaliContentHelper;
import org.thinkit.framework.envali.helper.EnvaliFieldHelper;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * An abstract class that abstracts the strategy of validation.
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
abstract class ValidationStrategy {

    /**
     * The field for validation
     */
    @Getter(AccessLevel.PROTECTED)
    private Field field;

    /**
     * The field helper
     */
    @Getter(AccessLevel.PROTECTED)
    private EnvaliFieldHelper fieldHelper;

    /**
     * The content helper
     */
    @Getter(AccessLevel.PROTECTED)
    private EnvaliContentHelper contentHelper;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private ValidationStrategy() {
    }

    /**
     * Constructor
     *
     * @param entity The entity for validation
     * @param field  The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected ValidationStrategy(@NonNull ValidatableEntity entity, @NonNull Field field) {
        this.field = field;
        this.fieldHelper = EnvaliFieldHelper.of(entity, field);
        this.contentHelper = EnvaliContentHelper.of(entity, field);
    }

    /**
     * Execute the validation process according to the strategy.
     */
    public abstract void validate();
}
