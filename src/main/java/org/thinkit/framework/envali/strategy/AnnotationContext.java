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

import org.thinkit.framework.envali.annotation.RequireEndWith;
import org.thinkit.framework.envali.annotation.RequireNegative;
import org.thinkit.framework.envali.annotation.RequireNonBlank;
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
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

    public void validate() {

        final Class<? extends Annotation> annotationType = this.getAnnotationType();

        if (annotationType.equals(RequireNonNull.class)) {
            ValidationStrategyContext.of(RequireNonNullStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequireNonBlank.class)) {
            ValidationStrategyContext.of(RequireNonBlankStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequirePositive.class)) {
            ValidationStrategyContext.of(RequirePositiveStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequireNegative.class)) {
            ValidationStrategyContext.of(RequireNegativeStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequireRangeTo.class)) {
            ValidationStrategyContext.of(RequireRangeToStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequireRangeFromTo.class)) {
            ValidationStrategyContext.of(RequireRangeFromToStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequireStartWith.class)) {
            ValidationStrategyContext.of(RequireStartWithStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequireEndWith.class)) {
            ValidationStrategyContext.of(RequireEndWithStrategy.of(this.getEntity(), this.getField())).validate();
        } else if (annotationType.equals(RequireNonEmpty.class)) {
            ValidationStrategyContext.of(RequireNonEmptyStrategy.of(this.getEntity(), this.getField())).validate();
        }
    }
}