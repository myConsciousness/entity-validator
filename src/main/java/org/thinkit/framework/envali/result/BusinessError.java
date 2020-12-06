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

import org.thinkit.framework.envali.catalog.ErrorType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * The class that represents a business error.
 * <p>
 * {@link BusinessError} handles both recoverable error and unrecoverable error,
 * and can be instantiated by calling the dedicated static constructor for each.
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
    @Getter
    private ErrorType errorType;

    /**
     * The message
     */
    @Getter
    private String message;

    /**
     * Default constructor
     */
    private BusinessError() {
    }

    /**
     * Constructor
     *
     * @param errorType The error type
     * @param message   The error message
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private BusinessError(@NonNull ErrorType errorType, @NonNull String message) {
        this.errorType = errorType;
        this.message = message;
    }

    /**
     * Returns the new instance of {@link BusinessError} with the error type
     * {@link ErrorType#RECOVERABLE} . The instance of {@link BusinessError} created
     * by {@link #recoverable(String)} indicates a recoverable error.
     * <p>
     * Use {@link #unrecoverable(String)} if you want to get an instance that
     * indicates an unrecoverable error.
     *
     * @param message The error message
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static BusinessError recoverable(@NonNull String message) {
        return new BusinessError(ErrorType.RECOVERABLE, message);
    }

    /**
     * Returns the new instance of {@link BusinessError} with the error type
     * {@link ErrorType#UNRECOVERABLE} . The instance of {@link BusinessError}
     * created by {@link #unrecoverable(String)} indicates a unrecoverable error.
     * <p>
     * Use {@link #recoverable(String)} if you want to get an instance that
     * indicates an recoverable error.
     *
     * @param message The error message
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static BusinessError unrecoverable(@NonNull String message) {
        return new BusinessError(ErrorType.UNRECOVERABLE, message);
    }
}
