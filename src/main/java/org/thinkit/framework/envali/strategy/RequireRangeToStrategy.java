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
import org.thinkit.framework.envali.annotation.RequireRangeTo;
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
 * A strategy to perform validation process for {@link RequireRangeTo}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class RequireRangeToStrategy extends ValidationStrategy<RequireRangeTo> {

    /**
     * Constructor
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private RequireRangeToStrategy(@NonNull ErrorContext<RequireRangeTo> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        super(errorContext, entity, field);
    }

    /**
     * Returns the new instance of {@link RequireRangeToStrategy} class.
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     * @return The new instance of {@link RequireRangeToStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static ValidationStrategy<RequireRangeTo> of(@NonNull ErrorContext<RequireRangeTo> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        return new RequireRangeToStrategy(errorContext, entity, field);
    }

    @Override
    public BusinessError validate() {

        final ErrorContext<RequireRangeTo> errorContext = super.getErrorContext();
        final RequireRangeTo annotation = errorContext.getAnnotation();

        return switch (annotation.errorType()) {
            case RECOVERABLE -> {
                try {
                    this.validate(annotation, super.getFieldHelper(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.recoverable(annotation.message());
                }
            }

            case UNRECOVERABLE -> {
                try {
                    this.validate(annotation, super.getFieldHelper(), new InvalidValueDetectedException());
                    yield BusinessError.none();
                } catch (InvalidValueDetectedException e) {
                    yield BusinessError.unrecoverable(annotation.message());
                }
            }

            case RUNTIME -> {
                this.validate(annotation, super.getFieldHelper());
                yield BusinessError.none();
            }
        };
    }

    /**
     * Validates the field value and object based on the {@code field} passed as
     * arguments.
     *
     * @param annotation The annotation of {@link RequireRangeTo}
     * @param field      The field to be validated
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private void validate(@NonNull RequireRangeTo annotation, @NonNull EnvaliFieldHelper field) {
        this.validate(annotation, field, new PreconditionFailedException());
    }

    /**
     * Validates the field value and object based on the {@code field} passed as
     * arguments.
     *
     * @param annotation The annotation of {@link RequireRangeTo}
     * @param field      The field to be validated
     * @param exception  The exception to be thrown when there is a error
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private void validate(@NonNull RequireRangeTo annotation, @NonNull EnvaliFieldHelper field,
            @NonNull RuntimeException exception) {
        if (field.isInteger()) {
            Preconditions.requireRangeTo(field.getInt(),
                    super.isContentConfig()
                            ? Integer.parseInt(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO))
                            : annotation.intTo(),
                    exception);
        } else if (field.isLong()) {
            Preconditions.requireRangeTo(field.getLong(),
                    super.isContentConfig()
                            ? Long.parseLong(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO))
                            : annotation.longTo(),
                    exception);
        } else if (field.isShort()) {
            Preconditions.requireRangeTo(field.getShort(),
                    super.isContentConfig()
                            ? Short.parseShort(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO))
                            : annotation.shortTo(),
                    exception);
        } else if (field.isByte()) {
            Preconditions.requireRangeTo(field.getByte(),
                    super.isContentConfig()
                            ? Byte.parseByte(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO))
                            : annotation.byteTo(),
                    exception);
        } else if (field.isFloat()) {
            Preconditions.requireRangeTo(field.getFloat(),
                    super.isContentConfig()
                            ? Float.parseFloat(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO))
                            : annotation.floatTo(),
                    exception);
        } else if (field.isDouble()) {
            Preconditions.requireRangeTo(field.getDouble(),
                    super.isContentConfig()
                            ? Double.parseDouble(super.getContentHelper().get(EnvaliContentAttribute.RANGE_TO))
                            : annotation.doubleTo(),
                    exception);
        }
    }
}
