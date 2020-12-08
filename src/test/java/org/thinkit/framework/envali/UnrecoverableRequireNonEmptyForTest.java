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

package org.thinkit.framework.envali;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the unrecoverable {@link RequireNonEmpty}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
final class UnrecoverableRequireNonEmptyForTest implements ValidatableEntity {

    /**
     * The non empty literal
     */
    @RequireNonEmpty(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private String literal;

    /**
     * The non empty literal array
     */
    @RequireNonEmpty(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private String[] literalArray;

    /**
     * The non empty literal list
     */
    @RequireNonEmpty(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private List<String> literalList;

    /**
     * The non empty literal map
     */
    @RequireNonEmpty(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private Map<String, String> literalMap;

    /**
     * The non empty literal set
     */
    @RequireNonEmpty(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private Set<String> literalSet;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private UnrecoverableRequireNonEmptyForTest() {
    }

    /**
     * Constructor
     *
     * @param literal      The non empty literal
     * @param literalArray The non empty literal array
     * @param literalList  The non empty literal list
     * @param literalMap   The non empty literal map
     * @param literlSet    The non empty literal set
     */
    public UnrecoverableRequireNonEmptyForTest(String literal, String[] literalArray, List<String> literalList,
            Map<String, String> literalMap, Set<String> literalSet) {
        this.literal = literal;
        this.literalArray = literalArray;
        this.literalList = literalList;
        this.literalMap = literalMap;
        this.literalSet = literalSet;
    }
}
