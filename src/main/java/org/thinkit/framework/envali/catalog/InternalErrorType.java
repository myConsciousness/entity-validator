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

package org.thinkit.framework.envali.catalog;

import org.thinkit.api.catalog.Catalog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The catalog that manages internal error type.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@RequiredArgsConstructor
public enum InternalErrorType implements Catalog<InternalErrorType> {

    /**
     * The recoverable error
     */
    RECOVERABLE(0),

    /**
     * The unrecoverable error
     */
    UNRECOVERABLE(1),

    /**
     * The runtime error
     */
    RUNTIME(2),

    /**
     * The nested error
     */
    NESTED(3),

    /**
     * No error
     */
    NONE(4);

    /**
     * The code
     */
    @Getter
    private final int code;
}
