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
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the {@link RequireRangeFromTo} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ParameterMapping(content = "RequireRangeFromToForTest")
final class RequireRangeFromToForTest implements ValidatableEntity {

    /**
     * The number within the limits from specific number
     */
    @RequireRangeFromTo
    private int number;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private RequireRangeFromToForTest() {
    }

    /**
     * Constructor
     *
     * @param number The number within the limits from specific number (expected)
     */
    public RequireRangeFromToForTest(int number) {
        this.number = number;
    }
}
