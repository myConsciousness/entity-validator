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
import java.util.Arrays;
import java.util.List;

import org.thinkit.framework.envali.Envali;
import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.result.BusinessError;
import org.thinkit.framework.envali.result.ValidationResult;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A strategy to perform validation process for {@link NestedEntity} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class NestedEntityStrategy extends ValidationStrategy {

    /**
     * Constructor
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private NestedEntityStrategy(@NonNull ErrorContext errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        super(errorContext, entity, field);
    }

    /**
     * Returns the new instance of {@link NestedEntityStrategy} class.
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     * @return The new instance of {@link NestedEntityStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static ValidationStrategy of(@NonNull ErrorContext errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        return new NestedEntityStrategy(errorContext, entity, field);
    }

    @Override
    public BusinessError validate() {

        if (!this.isValidatableEntity()) {
            throw new UnsupportedOperationException(
                    "Any object with NestedEntity annotation must implement ValidatableEntity interface");
        }

        final ValidationResult validationResult = Envali.validate(super.getFieldHelper().getValidatableEntity());

        if (validationResult.isEmpty()) {
            return BusinessError.none();
        }

        return BusinessError.nestedError(validationResult);
    }

    /**
     * Determines if a field object annotated with {@link NestedEntity} implements
     * the {@link ValidatableEntity} interface.
     *
     * @return {@code true} if a field object implemets the the
     *         {@link ValidatableEntity} , or {@code false}
     */
    private boolean isValidatableEntity() {

        final List<Class<?>> interfaces = Arrays.asList(super.getFieldHelper().get().getClass().getInterfaces());

        for (Class<?> _interface : interfaces) {
            if (_interface.equals(ValidatableEntity.class)) {
                return true;
            }
        }

        return false;
    }
}
