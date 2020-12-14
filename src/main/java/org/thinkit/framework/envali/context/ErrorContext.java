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

package org.thinkit.framework.envali.context;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.catalog.ParameterConfig;
import org.thinkit.framework.envali.catalog.ValidationPattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * The context that manages error details such as {@link ErrorType} and error
 * message.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@ToString
@EqualsAndHashCode
public final class ErrorContext<T extends Annotation> implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 4230489225683277809L;

    /**
     * The parameter configuration
     */
    @Getter
    private ParameterConfig parameterConfig;

    /**
     * The envali anotation
     */
    @Getter
    private T annotation;

    /**
     * The validation pattern
     */
    @Getter
    private ValidationPattern validationPattern;

    /**
     * Default constructor
     */
    private ErrorContext() {
    }

    /**
     * Constructor
     *
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ErrorContext(@NonNull ValidationPattern validationPattern) {
        this.validationPattern = validationPattern;
    }

    /**
     * Constructor
     *
     * @param annotation        The envali annotation
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ErrorContext(@NonNull T annotation, @NonNull ValidationPattern validationPattern) {
        this.annotation = annotation;
        this.validationPattern = validationPattern;
    }

    /**
     * Constructor
     *
     * @param parameterConfig   The parameter configuration based on
     *                          {@link ParameterConfig}
     * @param annotation        The envali annotation
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ErrorContext(@NonNull ParameterConfig parameterConfig, @NonNull T annotation,
            @NonNull ValidationPattern validationPattern) {
        this.parameterConfig = parameterConfig;
        this.annotation = annotation;
        this.validationPattern = validationPattern;
    }

    /**
     * Returns the new instance of {@link ErrorContext} based on the data passed as
     * argument.
     *
     * @param <T>               The annotation type with
     *                          {@link java.lang.annotation.Annotation} class as a
     *                          parent
     * @param parameterConfig   The parameter configuration based on
     *                          {@link ParameterConfig}
     * @param annotation        The Envali supported annotation
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     * @return The new instance of {@link ErrorContext} based on the data passed as
     *         argument
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static <T extends Annotation> ErrorContext<T> of(@NonNull ParameterConfig parameterConfig,
            @NonNull T annotation, @NonNull ValidationPattern validationPattern) {
        return new ErrorContext<>(parameterConfig, annotation, validationPattern);
    }

    /**
     * Returns the new instance of {@link ErrorContext} based on the data passed as
     * argument.
     *
     * @param <T>               The annotation type with
     *                          {@link java.lang.annotation.Annotation} class as a
     *                          parent
     * @param annotation        The envali annotation
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     * @return The new instance of {@link ErrorContext} based on the data passed as
     *         argument
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static <T extends Annotation> ErrorContext<T> of(@NonNull T annotation,
            @NonNull ValidationPattern validationPattern) {
        return new ErrorContext<>(annotation, validationPattern);
    }

    /**
     * Returns the new instance of {@link ErrorContext} based on the data passed as
     * argument.
     *
     * @param <T>               The annotation type with
     *                          {@link java.lang.annotation.Annotation} class as a
     *                          parent
     * @param validationPattern The validation pattern based on
     *                          {@link ValidationPattern}
     * @return The new instance of {@link ErrorContext} based on the data passed as
     *         argument
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static <T extends Annotation> ErrorContext<T> of(@NonNull ValidationPattern validationPattern) {
        return new ErrorContext<>(validationPattern);
    }
}
