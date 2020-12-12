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

import org.thinkit.common.base.precondition.Preconditions;
import org.thinkit.framework.envali.annotation.RequireNegative;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.exception.InvalidValueDetectedException;
import org.thinkit.framework.envali.result.BusinessError;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A strategy to perform validation process for {@link RequireNegative}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class RequireNegativeStrategy extends ValidationStrategy<RequireNegative> {

    /**
     * Constructor
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private RequireNegativeStrategy(@NonNull ErrorContext<RequireNegative> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        super(errorContext, entity, field);
    }

    /**
     * Returns the new instance of {@link RequireNegativeStrategy} class.
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     * @return The new instance of {@link RequireNegativeStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static ValidationStrategy<RequireNegative> of(@NonNull ErrorContext<RequireNegative> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        return new RequireNegativeStrategy(errorContext, entity, field);
    }

    @Override
    public BusinessError validate() {

        final ErrorContext<RequireNegative> errorContext = super.getErrorContext();
        final RequireNegative annotation = errorContext.getAnnotation();

        return switch (annotation.errorType()) {
            case RECOVERABLE -> {
                try {
                    Preconditions.requireNegative(super.getFieldHelper().getInt(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.recoverable(annotation.message());
                }
            }

            case UNRECOVERABLE -> {
                try {
                    Preconditions.requireNegative(super.getFieldHelper().getInt(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.unrecoverable(annotation.message());
                }
            }

            case RUNTIME -> {
                Preconditions.requireNegative(super.getFieldHelper().getInt());
                yield BusinessError.none();
            }
        };
    }
}
