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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.thinkit.common.base.precondition.exception.PreconditionFailedException;
import org.thinkit.framework.envali.result.BusinessError;
import org.thinkit.framework.envali.result.ValidationResult;

/**
 * The test class that manages test cases for {@link Envali} interface.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public final class EnvaliTest {

    @Nested
    class TestRequireNonNull {

        @ParameterizedTest
        @ValueSource(strings = { "test", "t", "test something", "", " ", " test", "test " })
        void testSimpleCases(final String parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireNonNullForTest(parameter)));
        }

        @Test
        void testWhenStringIsNull() {
            final String empty = null;
            assertThrows(NullPointerException.class, () -> Envali.validate(new RequireNonNullForTest(empty)));
        }
    }

    @Nested
    class TestRequirePositive {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testPositiveCases(int parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequirePositiveForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testNegativeCases(int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequirePositiveForTest(parameter)));
        }
    }

    @Nested
    class TestRequireNegative {

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testNegativeCases(int parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireNegativeForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testPositiveCases(int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequireNegativeForTest(parameter)));
        }
    }

    @Nested
    class TestRequireRangeFrom {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 9, 10, 11 })
        void testWithinTheLimitsCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireRangeFromForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { -10, -9, -2, -1 })
        void testNotWithinTheLimitsCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequireRangeFromForTest(parameter)));
        }
    }

    @Nested
    class TestRequireRangeTo {

        @ParameterizedTest
        @ValueSource(ints = { -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireRangeToForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { 11, 12, 100, 1000 })
        void testNotWithinTheLimitsCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequireRangeToForTest(parameter)));
        }
    }

    @Nested
    class TestRequireRangeFromTo {

        @ParameterizedTest
        @ValueSource(ints = { -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsFromToCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireRangeFromToForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { -100, -12, -11, 11, 12, 100, 1000 })
        void testNotWithinTheLimitsFromToCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequireRangeFromToForTest(parameter)));
        }
    }

    @Nested
    class TestRequireStartWith {

        @ParameterizedTest
        @ValueSource(strings = { "start something", "startsomething", "start" })
        void testWhenLiteralStartsWithSpecifiedPrefix(final String parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireStartWithForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "star", "tart", "something start", "something start something", "aaastartaaa",
                "somethingstart" })
        void testWhenLiteralDoesNotStartWithSpecifiedPrefix(final String parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequireStartWithForTest(parameter)));
        }
    }

    @Nested
    class TestRequireEndWith {

        @ParameterizedTest
        @ValueSource(strings = { "something end", "somethingend", "end" })
        void testWhenLiteralEndsWithSpecifiedPrefix(final String parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireEndWithForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "nd", "en", "endsomething", "something end something", "aaaendaaa",
                "endsomething" })
        void testWhenLiteralDoesNotEndWithSpecifiedPrefix(final String parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new RequireEndWithForTest(parameter)));
        }
    }

    @Nested
    class TestNestedEntity {

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testNestedRequireNegativeWhenNumbersAreNegative(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(new NestedEntityForTest(new RequireNegativeForTest(parameter))));
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 10, 100, 1000 })
        void testNestedRequireNegativeWhenNumbersArePositive(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new NestedEntityForTest(new RequireNegativeForTest(parameter))));
        }

        @Test
        void testWhenNestedEntityIsNotValidatable() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new NestedEntityWithNotValidatableEntityForTest(List.of())));

            assertEquals("Any object with NestedEntity annotation must implement ValidatableEntity interface",
                    exception.getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireNonNull {

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "　", "test", "t" })
        void testWhenLiteralIsNotNull(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireNonNullForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @Test
        void testWhenLiteralIsNull() {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireNonNullForTest(null)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireNonNullForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireNonBlank {

        @ParameterizedTest
        @ValueSource(strings = { " ", "　", "test", "t" })
        void testWhenLiteralIsNotBlank(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireNonBlankForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @Test
        void testWhenLiteralIsBlank() {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireNonBlankForTest("")));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireNonBlankForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireStartWith {

        @ParameterizedTest
        @ValueSource(strings = { "start something", "startsomething", "start" })
        void testWhenLiteralStartsWithSpecifiedPrefix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireStartWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "star", "tart", "something start", "something start something", "aaastartaaa",
                "somethingstart" })
        void testWhenLiteralDoesNotStartWithSpecifiedPrefix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireStartWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireStartWithForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireEndWith {

        @ParameterizedTest
        @ValueSource(strings = { "something end", "somethingend", "end" })
        void testWhenLiteralEndsWithSpecifiedSuffix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireEndWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "nd", "en", "endsomething", "something end something", "aaaendaaa",
                "endsomething" })
        void testWhenLiteralDoesNotEndWithSpecifiedSuffix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireEndWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireEndWithForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }
}
