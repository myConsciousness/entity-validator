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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.thinkit.common.base.precondition.exception.PreconditionFailedException;
import org.thinkit.framework.envali.regex.RequireMatchOfEmailAddressWithFindMethodForTest;
import org.thinkit.framework.envali.regex.RequireMatchOfEmailAddressWithLookingAtMethodForTest;
import org.thinkit.framework.envali.regex.RequireMatchOfEmailAddressWithMatchesMethodForTest;
import org.thinkit.framework.envali.result.BusinessError;
import org.thinkit.framework.envali.result.ValidationResult;

/**
 * The test class that manages test regex cases for {@link Envali} interface.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
public final class EnvaliRegexTest {

    @Nested
    class TestRequireMatchOfEmailAddress {

        @ParameterizedTest
        @ValueSource(strings = { "test@gmail.com", "test@something.co.jp", "test@my.email.jp", "test@my.email.jp" })
        void testFind(final String parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireMatchOfEmailAddressWithFindMethodForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(strings = { "test@gmail.com", "test@something.co.jp", "test@my.email.jp" })
        void testLookingAt(final String parameter) {
            assertDoesNotThrow(
                    () -> Envali.validate(new RequireMatchOfEmailAddressWithLookingAtMethodForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(strings = { "test@gmail.com", "test@something.co.jp", "test@my.email.jp" })
        void testMatches(final String parameter) {
            assertDoesNotThrow(
                    () -> Envali.validate(new RequireMatchOfEmailAddressWithMatchesMethodForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(strings = { "", "testmy.email.jp", "test @my.email.jp", "にほんご@メールアドレス.日本" })
        void testNotFind(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RequireMatchOfEmailAddressWithFindMethodForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RequireMatchOfEmailAddressWithFindMethodForTest.class);

            assertNotNull(businessErrors);
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = { "", "testmy.email.jp", "test @my.email.jp", "にほんご@メールアドレス.日本" })
        void testNotLookingAt(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RequireMatchOfEmailAddressWithLookingAtMethodForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RequireMatchOfEmailAddressWithLookingAtMethodForTest.class);

            assertNotNull(businessErrors);
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = { " test@gmail.com", "test@something.co.jp ", "testmy.email.jp", "tes t@my.email.jp",
                "test @my.email.jp", "test@my.ema il.jp", "にほんご@メールアドレス.日本" })
        void testNotMatch(final String parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequireMatchOfEmailAddressWithMatchesMethodForTest(parameter)));
        }
    }
}
