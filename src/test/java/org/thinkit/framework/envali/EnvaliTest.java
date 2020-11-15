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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.thinkit.framework.envali.exception.InvalidValueDetectedException;

/**
 * The test class that manages test cases for {@link Envali} interface.
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
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
            assertThrows(InvalidValueDetectedException.class,
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
            assertThrows(InvalidValueDetectedException.class,
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
            assertThrows(InvalidValueDetectedException.class,
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
            assertThrows(InvalidValueDetectedException.class,
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
            assertThrows(InvalidValueDetectedException.class,
                    () -> Envali.validate(new RequireRangeFromToForTest(parameter)));
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
            assertThrows(InvalidValueDetectedException.class,
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
}
