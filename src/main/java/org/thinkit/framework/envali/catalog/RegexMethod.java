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
 * The catalog that manages the method used to apply regular expression.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
@RequiredArgsConstructor
public enum RegexMethod implements Catalog<RegexMethod> {

    /**
     * Attempts to find the next subsequence of the input sequence that matches the
     * pattern.
     *
     * <p>
     * This method starts at the beginning of this matcher's region, or, if a
     * previous invocation of the method was successful and the matcher has not
     * since been reset, at the first character not matched by the previous match.
     */
    FIND(0),

    /**
     * Attempts to match the input sequence, starting at the beginning of the
     * region, against the pattern.
     *
     * <p>
     * Like the matches method, this method always starts at the beginning of the
     * region; unlike that method, it does not require that the entire region be
     * matched.
     */
    LOOKING_AT(1),

    /**
     * Attempts to match the entire region against the pattern.
     */
    MATCHES(2);

    /**
     * The code
     */
    @Getter
    private final int code;
}
