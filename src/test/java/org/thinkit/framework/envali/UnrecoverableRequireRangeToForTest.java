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

import org.thinkit.framework.envali.annotation.ParameterMapping;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the unrecoverable {@link RequireRangeTo}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@ParameterMapping(content = "RequireRangeToForTest")
final class UnrecoverableRequireRangeToForTest implements ValidatableEntity {

    /**
     * The number within the limits
     */
    @RequireRangeTo(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private int number;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private UnrecoverableRequireRangeToForTest() {
    }

    /**
     * Constructor
     *
     * @param number The number within the limits (expected)
     */
    public UnrecoverableRequireRangeToForTest(int number) {
        this.number = number;
    }
}