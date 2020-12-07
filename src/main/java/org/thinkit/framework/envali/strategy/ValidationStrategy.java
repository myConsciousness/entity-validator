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

import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.helper.EnvaliContentHelper;
import org.thinkit.framework.envali.helper.EnvaliFieldHelper;
import org.thinkit.framework.envali.result.BusinessError;

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
     * The error context
     */
    @Getter(AccessLevel.PROTECTED)
    private ErrorContext errorContext;

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
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected ValidationStrategy(@NonNull ErrorContext errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        this.errorContext = errorContext;
        this.fieldHelper = EnvaliFieldHelper.of(entity, field);
        this.contentHelper = EnvaliContentHelper.of(entity, field);
    }

    /**
     * Execute the validation process according to the strategy and return the
     * business error as {@link BusinessError} if any error exists.
     *
     * @return The business error detected in the validate process
     */
    public abstract BusinessError validate();
}
