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
import org.thinkit.framework.envali.annotation.RequireRangeFrom;
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
 * A strategy to perform validation process for {@link RequireRangeFrom}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class RequireRangeFromStrategy extends ValidationStrategy {

    /**
     * Constructor
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private RequireRangeFromStrategy(@NonNull ErrorContext errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        super(errorContext, entity, field);
    }

    /**
     * Returns the new instance of {@link RequireRangeFromStrategy} class.
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     * @return The new instance of {@link RequireRangeFromStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static ValidationStrategy of(@NonNull ErrorContext errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        return new RequireRangeFromStrategy(errorContext, entity, field);
    }

    @Override
    public BusinessError validate() {

        final ErrorContext errorContext = super.getErrorContext();

        return switch (errorContext.getErrorType()) {
            case RECOVERABLE -> {
                try {
                    this.validate(super.getFieldHelper(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.recoverable(errorContext.getMessage());
                }
            }

            case UNRECOVERABLE -> {
                try {
                    this.validate(super.getFieldHelper(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.unrecoverable(errorContext.getMessage());
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
            Preconditions.requireRangeFrom(field.getInt(),
                    Integer.parseInt(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)), exception);
        } else if (field.isLong()) {
            Preconditions.requireRangeFrom(field.getLong(),
                    Long.parseLong(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)), exception);
        } else if (field.isShort()) {
            Preconditions.requireRangeFrom(field.getShort(),
                    Short.parseShort(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)), exception);
        } else if (field.isByte()) {
            Preconditions.requireRangeFrom(field.getByte(),
                    Byte.parseByte(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)), exception);
        } else if (field.isFloat()) {
            Preconditions.requireRangeFrom(field.getFloat(),
                    Float.parseFloat(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)), exception);
        } else if (field.isDouble()) {
            Preconditions.requireRangeFrom(field.getDouble(),
                    Double.parseDouble(super.getContentHelper().get(EnvaliContentAttribute.RANGE_FROM)), exception);
        }
    }
}
