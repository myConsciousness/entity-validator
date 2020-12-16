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
import org.thinkit.framework.envali.annotation.RequireMatch;
import org.thinkit.framework.envali.annotation.RequireNegative;
import org.thinkit.framework.envali.annotation.RequireNonBlank;
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.annotation.RequireRangeFrom;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.catalog.ValidationPattern;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * The concrete factory class that generating the strategy class for the
 * validation process.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
@ToString
@EqualsAndHashCode
public final class ValidationStrategyFactory implements StrategyFactory {

    /**
     * The validatable entity to be validated
     */
    @Getter
    private ValidatableEntity validatableEntity;

    /**
     * The field to be validated
     */
    @Getter
    private Field field;

    /**
     * Default constructor
     */
    private ValidationStrategyFactory() {
    }

    /**
     * Constructor
     *
     * @param validatableEntity The validatable entity to be validated
     * @param field             The field to be validated
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ValidationStrategyFactory(@NonNull ValidatableEntity validatableEntity, @NonNull Field field) {
        this.validatableEntity = validatableEntity;
        this.field = field;
    }

    /**
     * Returns the new instance of {@link ValidationStrategyFactory} based on the
     * data passed as an argument.
     *
     * @param validatableEntity The validatable entity to be validated
     * @param field             The field to be validated
     * @return The new instance of {@link ValidationStrategyFactory}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static StrategyFactory of(@NonNull ValidatableEntity validatableEntity, @NonNull Field field) {
        return new ValidationStrategyFactory(validatableEntity, field);
    }

    @Override
    public ValidationStrategy<? extends Annotation> createValidationStrategy(
            @NonNull ValidationPattern validationPattern) {
        return switch (validationPattern) {
            case REQUIRE_NON_NULL -> {
                yield RequireNonNullStrategy.of(
                        ErrorContext.of(field.getAnnotation(RequireNonNull.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_NON_BLANK -> {
                yield RequireNonBlankStrategy.of(
                        ErrorContext.of(field.getAnnotation(RequireNonBlank.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_POSITIVE -> {
                yield RequirePositiveStrategy.of(
                        ErrorContext.of(field.getAnnotation(RequirePositive.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_NEGATIVE -> {
                yield RequireNegativeStrategy.of(
                        ErrorContext.of(field.getAnnotation(RequireNegative.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_RANGE_FROM -> {
                yield RequireRangeFromStrategy.of(
                        ErrorContext.of(this.validatableEntity.getParameterConfig(),
                                field.getAnnotation(RequireRangeFrom.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_RANGE_TO -> {
                yield RequireRangeToStrategy.of(
                        ErrorContext.of(this.validatableEntity.getParameterConfig(),
                                field.getAnnotation(RequireRangeTo.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_RANGE_FROM_TO -> {
                yield RequireRangeFromToStrategy.of(
                        ErrorContext.of(this.validatableEntity.getParameterConfig(),
                                field.getAnnotation(RequireRangeFromTo.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_START_WITH -> {
                yield RequireStartWithStrategy.of(
                        ErrorContext.of(this.validatableEntity.getParameterConfig(),
                                field.getAnnotation(RequireStartWith.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_END_WITH -> {
                yield RequireEndWithStrategy.of(
                        ErrorContext.of(this.validatableEntity.getParameterConfig(),
                                field.getAnnotation(RequireEndWith.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case REQUIRE_NON_EMPTY -> {
                yield RequireNonEmptyStrategy.of(
                        ErrorContext.of(field.getAnnotation(RequireNonEmpty.class), validationPattern),
                        this.validatableEntity, this.field);
            }

            case NESTED_ENTITY -> {
                yield NestedEntityStrategy.of(ErrorContext.of(validationPattern), this.validatableEntity, this.field);
            }

            case REQUIRE_MATCH -> {
                yield RequireMatchStrategy.of(
                        ErrorContext.of(field.getAnnotation(RequireMatch.class), validationPattern),
                        this.validatableEntity, this.field);
            }
        };
    }
}
