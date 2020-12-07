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

import org.thinkit.framework.envali.catalog.ErrorType;

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
public final class ErrorContext implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 4230489225683277809L;

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
    private ErrorContext() {
    }

    /**
     * Constructor
     *
     * @param errorType The error type based on {@link ErrorType}
     * @param message   The message
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ErrorContext(@NonNull ErrorType errorType, @NonNull String message) {
        this.errorType = errorType;
        this.message = message;
    }

    /**
     * Returns the new instance of {@link ErrorContext} based on the data passed as
     * argument.
     *
     * @param errorType The error type based on {@link ErrorType}
     * @param message   The message
     * @return The new instance of {@link ErrorContext} based on the data passed as
     *         argument
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static ErrorContext of(@NonNull ErrorType errorType, @NonNull String message) {
        return new ErrorContext(errorType, message);
    }
}
