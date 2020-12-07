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

import org.thinkit.api.catalog.BiCatalog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A catalog that manages Envali's content attribute.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@RequiredArgsConstructor
public enum EnvaliContentAttribute implements BiCatalog<EnvaliContentAttribute, String> {

    /**
     * Envali's content attributes: {@code "rangeTo"}
     */
    RANGE_TO(0, "rangeTo"),

    /**
     * Envali's content attributes: {@code "rangeFrom"}
     */
    RANGE_FROM(1, "rangeFrom"),

    /**
     * Envali's content attributes: {@code "startWith"}
     */
    START_WITH(2, "startWith"),

    /**
     * Envali's content attributes: {@code "endWith"}
     */
    END_WITH(3, "endWith"),

    /**
     * Envali's content attributes: {@code "errorType"}
     */
    ERROR_TYPE(4, "errorType"),

    /**
     * Envali's content attributes: {@code "message"}
     */
    MESSAGE(5, "message");

    /**
     * Code value
     */
    @Getter
    private final int code;

    /**
     * Tag
     */
    @Getter
    private final String tag;
}
