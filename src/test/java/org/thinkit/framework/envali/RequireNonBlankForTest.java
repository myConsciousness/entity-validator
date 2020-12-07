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

import org.thinkit.framework.envali.annotation.RequireNonBlank;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the {@link RequireNonBlank} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
final class RequireNonBlankForTest implements ValidatableEntity {

    /**
     * The non blank field
     */
    @RequireNonBlank
    private String literal;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private RequireNonBlankForTest() {
    }

    /**
     * Constructor
     *
     * @param literal The non blank literal
     */
    public RequireNonBlankForTest(String literal) {
        this.literal = literal;
    }
}
