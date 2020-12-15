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

import org.thinkit.framework.envali.catalog.ParameterConfig;
import org.thinkit.framework.envali.catalog.ValidationPattern;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.helper.EnvaliContentHelper;
import org.thinkit.framework.envali.helper.EnvaliFieldHelper;
import org.thinkit.framework.envali.result.BusinessError;
import org.thinkit.framework.envali.rule.AnnotationRequirement;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * An abstract class that abstracts the strategy of validation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
abstract class ValidationStrategy<T extends Annotation> {

    /**
     * The error context
     */
    @Getter(AccessLevel.PROTECTED)
    private ErrorContext<T> errorContext;

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
     * @exception NullPointerException          If {@code null} is passed as an
     *                                          argument
     * @exception UnsupportedOperationException When specific Envali annotation is
     *                                          specified for the field of
     *                                          unsupported data type
     */
    protected ValidationStrategy(@NonNull ErrorContext<T> errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        this.errorContext = errorContext;
        this.fieldHelper = EnvaliFieldHelper.of(entity, field);

        getAnnotationRequirement(errorContext.getValidationPattern()).requireSupportedDataType(this.fieldHelper);

        if (errorContext.getParameterConfig() == ParameterConfig.CONTENT) {
            this.contentHelper = EnvaliContentHelper.of(entity, field);
        }
    }

    /**
     * Execute the validation process according to the strategy and return the
     * business error as {@link BusinessError} if any error exists.
     *
     * @return The business error detected in the validate process
     */
    public abstract BusinessError validate();

    /**
     * Tests if the error context has {@link ParameterConfig#CONTENT} .
     *
     * @return {@code true} if the error context has {@link ParameterConfig#CONTENT}
     *         , otherwise {@code false}
     *
     * @since 1.0.2
     */
    protected boolean isContentConfig() {
        return this.errorContext.getParameterConfig() == ParameterConfig.CONTENT;
    }

    /**
     * Returns the {@link AnnotationRequirement} associated with the validation
     * pattern passed as an argument.
     *
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     * @return The {@link AnnotationRequirement} associated with the validation
     *         pattern passed as an argument.
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     *
     * @since 1.0.2
     */
    private static AnnotationRequirement getAnnotationRequirement(@NonNull ValidationPattern validationPattern) {
        return switch (validationPattern) {
            case REQUIRE_NON_NULL -> AnnotationRequirement.REQUIRE_NON_NULL;
            case REQUIRE_NON_BLANK -> AnnotationRequirement.REQUIRE_NON_BLANK;
            case REQUIRE_POSITIVE -> AnnotationRequirement.REQUIRE_POSITIVE;
            case REQUIRE_NEGATIVE -> AnnotationRequirement.REQUIRE_NEGATIVE;
            case REQUIRE_RANGE_FROM -> AnnotationRequirement.REQUIRE_RANGE_FROM;
            case REQUIRE_RANGE_TO -> AnnotationRequirement.REQUIRE_RANGE_TO;
            case REQUIRE_RANGE_FROM_TO -> AnnotationRequirement.REQUIRE_RANGE_FROM_TO;
            case REQUIRE_START_WITH -> AnnotationRequirement.REQUIRE_START_WITH;
            case REQUIRE_END_WITH -> AnnotationRequirement.REQUIRE_END_WITH;
            case REQUIRE_NON_EMPTY -> AnnotationRequirement.REQUIRE_NON_EMPTY;
            case NESTED_ENTITY -> AnnotationRequirement.NESTED_ENTITY;
            case REQUIRE_MATCH -> AnnotationRequirement.REQUIRE_MATCH;
            case REQUIRE_MATCH_PRESET -> AnnotationRequirement.REQUIRE_MATCH_PRESET;
        };
    }
}
