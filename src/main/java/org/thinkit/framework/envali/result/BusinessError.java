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

import org.thinkit.framework.envali.catalog.ErrorType;

import lombok.NonNull;

/**
 * - 
 * 
 * @author Kato Shinya
 * @since 1.0.1
 */
public final class BusinessError {
 
    /**
     * The error type
     */
    private ErrorType errorType;

    /**
     * The message
     */
    private String message;

    /**
     * Default constructor
     */
    private BusinessError() {
    }
 
    private BusinessError(@NonNull ErrorType errorType, @NonNull String message) {
        this.errorType = errorType;
        this.message = message;
    }
 
    public static BusinessError recoverable(@NonNull String message) {
        return new BusinessError(ErrorType.RECOVERABLE, message);
    }
 
    public static BusinessError unrecoverable(@NonNull String message) {
        return new BusinessError(ErrorType.UNRECOVERABLE, message);
    }
}
