/*
 * Copyright 2020 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy for the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, sfortware distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS for ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.framework.envali.regex;

import org.thinkit.framework.envali.annotation.RequireMatch;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.catalog.RegexMethod;
import org.thinkit.framework.envali.catalog.RegexPreset;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the {@link RequireMatch} annotation with looking
 * at method.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
public class RequireMatchOfEmailAddressWithLookingAtMethodForTest implements ValidatableEntity {

    /**
     * The email address
     */
    @RequireMatch(presetExpression = RegexPreset.EMAIL_ADDRESS, method = RegexMethod.LOOKING_AT, errorType = ErrorType.UNRECOVERABLE, message = "success")
    private String emailAddress;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private RequireMatchOfEmailAddressWithLookingAtMethodForTest() {
    }

    /**
     * Constructor
     *
     * @param emailAddress The email address
     */
    public RequireMatchOfEmailAddressWithLookingAtMethodForTest(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
