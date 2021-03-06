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

package org.thinkit.framework.envali.result;

import java.io.Serializable;

import org.thinkit.framework.envali.catalog.InternalErrorType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * The class that represents a business error.
 * <p>
 * {@link BusinessError} handles both recoverable error and unrecoverable error,
 * and can be instantiated by calling the dedicated static constructor for each.
 * <p>
 * Provides {@link #hasNestedError()} as a feature to test if a nested error of
 * {@link ValidationResult} exists, and {@link #hasError()} to test if a
 * business error exists.
 *
 * <pre>
 * No error:
 * <code>
 * BusinessError businessError = BusinessError.none();
 * </code>
 * </pre>
 *
 * <pre>
 * Recoverable error:
 * <code>
 * BusinessError businessError = BusinessError.recoverable("any error message");
 * </code>
 * </pre>
 *
 * <pre>
 * Unrecoverable error:
 * <code>
 * BusinessError businessError = BusinessError.unrecoverable("any error message");
 * </code>
 * </pre>
 *
 * <pre>
 * Nested error:
 * <code>
 * BusinessError businessError = BusinessError.nestedError(validationResult);
 * businessError.hasNestedError(); // It returns true
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@ToString
@EqualsAndHashCode
public final class BusinessError implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -842747765124841065L;

    /**
     * The error type
     */
    private InternalErrorType errorType;

    /**
     * The message
     */
    @Getter
    private String message;

    /**
     * The nested error
     */
    @Getter
    private ValidationResult nestedError;

    /**
     * Default constructor
     */
    private BusinessError() {
    }

    /**
     * Constructor for business errors.
     *
     * @param errorType The error type
     * @param message   The error message
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private BusinessError(@NonNull InternalErrorType errorType, @NonNull String message) {
        this.errorType = errorType;
        this.message = message;
    }

    /**
     * Constructor for nested error.
     *
     * @param nestedError The nested error of {@link ValidationResult}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private BusinessError(@NonNull ValidationResult nestedError) {
        this.errorType = InternalErrorType.NESTED;
        this.message = "";
        this.nestedError = nestedError;
    }

    /**
     * Returns the new instance of {@link BusinessError} with the error type
     * {@link InternalErrorType#NONE} . The instance of {@link BusinessError}
     * created by {@link #none()} indicates that there is no error.
     *
     * @return The new instance of {@link BusinessError} with the error type
     *         {@link InternalErrorType#NONE}
     */
    public static BusinessError none() {
        return new BusinessError(InternalErrorType.NONE, "");
    }

    /**
     * Returns the new instance of {@link BusinessError} with the error type
     * {@link InternalErrorType#RECOVERABLE} . The instance of {@link BusinessError}
     * created by {@link #recoverable(String)} indicates a recoverable error.
     * <p>
     * Use {@link #unrecoverable(String)} if you want to get an instance that
     * indicates an unrecoverable error.
     *
     * @param message The error message
     * @return The new instance of {@link BusinessError} with the error type
     *         {@link InternalErrorType#RECOVERABLE}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static BusinessError recoverable(@NonNull String message) {
        return new BusinessError(InternalErrorType.RECOVERABLE, message);
    }

    /**
     * Returns the new instance of {@link BusinessError} with the error type
     * {@link InternalErrorType#UNRECOVERABLE} . The instance of
     * {@link BusinessError} created by {@link #unrecoverable(String)} indicates a
     * unrecoverable error.
     * <p>
     * Use {@link #recoverable(String)} if you want to get an instance that
     * indicates an recoverable error.
     *
     * @param message The error message
     * @return The new instance of {@link BusinessError} with the error type
     *         {@link InternalErrorType#UNRECOVERABLE}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static BusinessError unrecoverable(@NonNull String message) {
        return new BusinessError(InternalErrorType.UNRECOVERABLE, message);
    }

    /**
     * Returns the new instance of {@link BusinessError} with the nested error of
     * {@link ValidationResult} .
     *
     * @param nestedError The nested error of {@link ValidationResult}
     * @return The new instance of {@link BusinessError} with the nested error of
     *         {@link ValidationResult}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static BusinessError nestedError(@NonNull ValidationResult nestedError) {
        return new BusinessError(nestedError);
    }

    /**
     * Tests for the presence of business error.
     *
     * @return {@code true} if there is a business error, otherwise {@code false}
     */
    public boolean hasError() {
        return this.errorType != InternalErrorType.NONE;
    }

    /**
     * Tests for the presence of nested error.
     *
     * @return {@code true} if there is a nested error, otherwise {@code false}
     */
    public boolean hasNestedError() {
        return this.errorType == InternalErrorType.NESTED;
    }

    /**
     * Tests if a business error is a recoverable error.
     *
     * @return {@code true} if a business error is a recoverable error, otherwise
     *         {@code false}
     */
    public boolean isRecoverable() {
        return this.errorType == InternalErrorType.RECOVERABLE;
    }

    /**
     * Tests if a business error is an unrecoverable error.
     *
     * @return {@code true} if a business error is an unrecoverable error, otherwise
     *         {@code false}
     */
    public boolean isUnrecoverable() {
        return this.errorType == InternalErrorType.UNRECOVERABLE;
    }
}
