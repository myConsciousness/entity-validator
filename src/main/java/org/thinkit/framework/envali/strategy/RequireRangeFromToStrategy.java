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
import org.thinkit.common.base.precondition.exception.PreconditionFailedException;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.catalog.EnvaliContentAttribute;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.exception.InvalidValueDetectedException;
import org.thinkit.framework.envali.helper.EnvaliFieldHelper;
import org.thinkit.framework.envali.result.BusinessError;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A strategy to perform validation process for {@link RequireRangeFromTo}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class RequireRangeFromToStrategy extends ValidationStrategy<RequireRangeFromTo> {

    /**
     * Constructor
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private RequireRangeFromToStrategy(@NonNull ErrorContext<RequireRangeFromTo> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        super(errorContext, entity, field);
    }

    /**
     * Returns the new instance of {@link RequireRangeFromToStrategy} class.
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     * @return The new instance of {@link RequireRangeFromToStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static ValidationStrategy<RequireRangeFromTo> of(@NonNull ErrorContext<RequireRangeFromTo> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        return new RequireRangeFromToStrategy(errorContext, entity, field);
    }

    @Override
    public BusinessError validate() {

        final ErrorContext<RequireRangeFromTo> errorContext = super.getErrorContext();
        final RequireRangeFromTo annotation = errorContext.getAnnotation();

        return switch (annotation.errorType()) {
            case RECOVERABLE -> {
                try {
                    this.validate(super.getFieldHelper(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.recoverable(annotation.message());
                }
            }

            case UNRECOVERABLE -> {
                try {
                    this.validate(super.getFieldHelper(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.unrecoverable(annotation.message());
                }
            }

            case RUNTIME -> {
                this.validate(super.getFieldHelper());
                yield BusinessError.none();
            }
        };
    }

    /**
     * Validates the field value and object based on the {@code field} passed as
     * arguments.
     *
     * @param field The field to be validated
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private void validate(@NonNull EnvaliFieldHelper field) {
        this.validate(field, new PreconditionFailedException());
    }

    /**
     * Validates the field value and object based on the {@code field} passed as
     * arguments.
     *
     * @param field     The field to be validated
     * @param exception The exception to be thrown when there is a error
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private void validate(@NonNull EnvaliFieldHelper field, @NonNull RuntimeException exception) {
        if (field.isInteger()) {
            Preconditions.requireRange(field.getInt(),
                    Integer.parseInt(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)),
                    Integer.parseInt(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO)), exception);
        } else if (field.isLong()) {
            Preconditions.requireRange(field.getLong(),
                    Long.parseLong(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)),
                    Long.parseLong(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO)), exception);
        } else if (field.isShort()) {
            Preconditions.requireRange(field.getShort(),
                    Short.parseShort(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)),
                    Short.parseShort(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO)), exception);
        } else if (field.isByte()) {
            Preconditions.requireRange(field.getByte(),
                    Byte.parseByte(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)),
                    Byte.parseByte(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO)), exception);
        } else if (field.isFloat()) {
            Preconditions.requireRange(field.getFloat(),
                    Float.parseFloat(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)),
                    Float.parseFloat(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO)), exception);
        } else if (field.isDouble()) {
            Preconditions.requireRange(field.getDouble(),
                    Double.parseDouble(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)),
                    Double.parseDouble(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO)), exception);
        }
    }
}
