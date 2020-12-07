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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.framework.envali.annotation.RequireEndWith;
import org.thinkit.framework.envali.annotation.RequireNegative;
import org.thinkit.framework.envali.annotation.RequireNonBlank;
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.annotation.RequireRangeFrom;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.common.base.precondition.exception.PreconditionFailedException;
import org.thinkit.framework.envali.result.BusinessError;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Context class to determine the validation strategy based on the annotation
 * data type.
 * <p>
 * create an instance of the class using the
 * {@link #of(ValidatableEntity, Field, Class)} and validate it according to the
 * annotations using the {@link #validate()} method.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class AnnotationContext {

        /**
         * The entity for validation
         */
        @Getter(AccessLevel.PRIVATE)
        private ValidatableEntity entity;

        /**
         * The field for validation
         */
        @Getter(AccessLevel.PRIVATE)
        private Field field;

        /**
         * The annotation type for validation
         */
        @Getter(AccessLevel.PRIVATE)
        private Class<? extends Annotation> annotationType;

        /**
         * Default constructor
         */
        private AnnotationContext() {
        }

        /**
         * Constructor
         *
         * @param entity         The entity for validation
         * @param field          The field for validation
         * @param annotationType The annotation type for validation
         *
         * @exception NullPointerException If {@code null} is passed as an argument
         */
        private AnnotationContext(@NonNull ValidatableEntity entity, @NonNull Field field,
                        @NonNull Class<? extends Annotation> annotationType) {
                this.entity = entity;
                this.field = field;
                this.annotationType = annotationType;
        }

        /**
         * Returns the new instance of {@link RequireEndWithStrategy} class.
         *
         * @param entity         The entity for validation
         * @param field          The field for validation
         * @param annotationType The annotation type for validation
         * @return The new instance of {@link RequireEndWithStrategy} class
         *
         * @exception NullPointerException If {@code null} is passed as an argument
         */
        public static AnnotationContext of(@NonNull ValidatableEntity entity, @NonNull Field field,
                        @NonNull Class<? extends Annotation> annotationType) {
                return new AnnotationContext(entity, field, annotationType);
        }

        /**
         * Execute a validation strategy based on the data type of the annotation.
         * <p>
         * Returns {@code null} if annotation is not supported by Envali framework.
         *
         * @return The business error detected in the validate process
         *
         * @exception PreconditionFailedException If the validation process detects an
         *                                        invalid value
         */
        public BusinessError validate() {

                final Class<? extends Annotation> annotationType = this.getAnnotationType();

                if (annotationType.equals(RequireNonNull.class)) {
                        final RequireNonNull annotation = this.field.getAnnotation(RequireNonNull.class);
                        return ValidationStrategyContext.of(RequireNonNullStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireNonBlank.class)) {
                        final RequireNonBlank annotation = this.field.getAnnotation(RequireNonBlank.class);
                        return ValidationStrategyContext.of(RequireNonBlankStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequirePositive.class)) {
                        final RequirePositive annotation = this.field.getAnnotation(RequirePositive.class);
                        return ValidationStrategyContext.of(RequirePositiveStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireNegative.class)) {
                        final RequireNegative annotation = this.field.getAnnotation(RequireNegative.class);
                        return ValidationStrategyContext.of(RequireNegativeStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireRangeFrom.class)) {
                        final RequireRangeFrom annotation = this.field.getAnnotation(RequireRangeFrom.class);
                        return ValidationStrategyContext.of(RequireRangeFromStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireRangeTo.class)) {
                        final RequireRangeTo annotation = this.field.getAnnotation(RequireRangeTo.class);
                        return ValidationStrategyContext.of(RequireRangeToStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireRangeFromTo.class)) {
                        final RequireRangeFromTo annotation = this.field.getAnnotation(RequireRangeFromTo.class);
                        return ValidationStrategyContext.of(RequireRangeFromToStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireStartWith.class)) {
                        final RequireStartWith annotation = this.field.getAnnotation(RequireStartWith.class);
                        return ValidationStrategyContext.of(RequireStartWithStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireEndWith.class)) {
                        final RequireEndWith annotation = this.field.getAnnotation(RequireEndWith.class);
                        return ValidationStrategyContext.of(RequireEndWithStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(RequireNonEmpty.class)) {
                        final RequireNonEmpty annotation = this.field.getAnnotation(RequireNonEmpty.class);
                        return ValidationStrategyContext.of(RequireNonEmptyStrategy.of(
                                        ErrorContext.of(annotation.errorType(), annotation.message()), this.getEntity(),
                                        this.getField())).validate();
                } else if (annotationType.equals(NestedEntity.class)) {
                        return ValidationStrategyContext.of(NestedEntityStrategy
                                        .of(ErrorContext.of(ErrorType.RUNTIME, ""), this.getEntity(), this.getField()))
                                        .validate();
                }

                return null;
        }
}
