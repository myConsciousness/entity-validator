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
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
    class TestRequireNonBlank {

        @ParameterizedTest
        @ValueSource(strings = { " ", "　", "t", "test" })
        void testWhenStringIsNotBlank(final String parameter) {
            assertDoesNotThrow(() -> Envali.validate(new RequireNonBlankForTest(parameter)));
        }

        @Test
        void testWhenStringIsBlank() {
            assertThrows(PreconditionFailedException.class, () -> Envali.validate(new RequireNonBlankForTest("")));
        }

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireNonBlankForTest(0)));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireNonBlank annotation supports String type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireNonBlankForTest#number of type int.",
                    exception.getMessage());
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

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequirePositiveForTest("")));
            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequirePositive annotation supports Integer type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequirePositiveForTest#literal of type java.lang.String.",
                    exception.getMessage());
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

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireNegativeForTest("")));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireNegative annotation supports Integer type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireNegativeForTest#literal of type java.lang.String.",
                    exception.getMessage());
        }
    }

    @Nested
    class TestRequireRangeFrom {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 9, 10, 11, Integer.MAX_VALUE })
        void testWithinTheLimitsCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -9, -2, -1 })
        void testNotWithinTheLimitsCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { 0l, 1l, 2l, 9l, 10l, 11l, Long.MAX_VALUE })
        void testLongWithinTheLimitsCases(final long parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -9l, -2l, -1l })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { 0, 1, 2, 9, 10, 11, Short.MAX_VALUE })
        void testShortWithinTheLimitsCases(final short parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -9, -2, -1 })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { 0, 1, 2, 9, 10, 11, Byte.MAX_VALUE })
        void testByteWithinTheLimitsCases(final byte parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -9, -2, -1 })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { 0.0f, 0.01f, 0.1f, 1.0f, 2.0f, 9.0f, 10.0f, 11.0f, Float.MAX_VALUE })
        void testFloatWithinTheLimitsCases(final float parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -9.0f, -2.0f, -1.0f, -0.1f, -0.01f })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { 0.0d, 0.01d, 0.1d, 1.0d, 2.0d, 9.0d, 10.0d, 11.0d, Double.MAX_VALUE })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromForTest.ofDouble(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -9.0d, -2.0d, -1.0d, -0.1d, -0.01d })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromForTest.ofDouble(parameter)));
        }

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireRangeFromForTest("")));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireRangeFrom annotation supports Integer, Long, Short, Byte, Float, Double type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireRangeFromForTest#literal of type java.lang.String.",
                    exception.getMessage());
        }
    }

    @Nested
    class TestRequireRangeTo {

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { 11, 12, 100, 1000 })
        void testNotWithinTheLimitsCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsCases(final long parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsCases(final short parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsCases(final byte parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -1.0f, 0.0f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsCases(final float parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { 11.0f, 12.0f, 100.0f, Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -1.0d, 0.0d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeToForTest.ofDouble(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { 11.0d, 12.0d, 100.0d, Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeToForTest.ofDouble(parameter)));
        }

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireRangeToForTest(new StringBuilder())));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireRangeTo annotation supports Integer, Long, Short, Byte, Float, Double type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireRangeToForTest#sb of type java.lang.StringBuilder.",
                    exception.getMessage());
        }
    }

    @Nested
    class TestRequireRangeFromTo {

        @ParameterizedTest
        @ValueSource(ints = { -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsFromToCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { -100, -12, -11, 11, 12, 100, 1000 })
        void testNotWithinTheLimitsFromToCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsFromToCases(final long parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -100l, -12l, -11l, 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsFromToCases(final long parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsFromToCases(final short parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -100, -12, -11, 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsFromToCases(final short parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsFromToCases(final byte parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -100, -12, -11, 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsFromToCases(final byte parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -10.0f, -1.0f, -0.1f, 0.0f, 0.1f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsFromToCases(final float parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -100.0f, -12.0f, -11.0f, 11.0f, 12.0f, 100.0f, 1000.0f,
                Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsFromToCases(final float parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -10.0d, -1.0d, -0.1d, 0.0d, 0.1d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsFromToCases(final double parameter) {
            assertDoesNotThrow(() -> Envali.validate(RequireRangeFromToForTest.ofDouble(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -100.0d, -12.0d, -11.0d, 11.0d, 12.0d, 100.0d, 1000.0d,
                Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsFromToCases(final double parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(RequireRangeFromToForTest.ofDouble(parameter)));
        }

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireRangeFromToForTest(new Object())));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireRangeFromTo annotation supports Integer, Long, Short, Byte, Float, Double type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireRangeFromToForTest#object of type java.lang.Object.",
                    exception.getMessage());
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

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireStartWithForTest(new StringBuilder())));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireStartWith annotation supports String type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireStartWithForTest#sb of type java.lang.StringBuilder.",
                    exception.getMessage());
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

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireEndWithForTest(new StringBuilder())));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireEndWith annotation supports String type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireEndWithForTest#sb of type java.lang.StringBuilder.",
                    exception.getMessage());
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class TestRequireNonEmpty {

        Stream<Arguments> nonEmptyObjectProvider() {
            return Stream.of(Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "test" }, List.of("test"), Map.of("test", "test"),
                            Set.of("test")));
        }

        Stream<Arguments> emptyObjectProvider() {
            return Stream.of(Arguments.of("", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] {}, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(""), Map.of(), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of()));
        }

        @ParameterizedTest
        @MethodSource("nonEmptyObjectProvider")
        void testWhenObjectIsNotEmpty(final String literal, final String[] literalArray, final List<String> literalList,
                final Map<String, String> literalMap, final Set<String> literalSet) {
            assertDoesNotThrow(() -> Envali
                    .validate(new RequireNonEmptyForTest(literal, literalArray, literalList, literalMap, literalSet)));
        }

        @ParameterizedTest
        @MethodSource("emptyObjectProvider")
        void testWhenObjectIsEmpty(final String literal, final String[] literalArray, final List<String> literalList,
                final Map<String, String> literalMap, final Set<String> literalSet) {
            assertThrows(PreconditionFailedException.class, () -> Envali
                    .validate(new RequireNonEmptyForTest(literal, literalArray, literalList, literalMap, literalSet)));
        }

        @Test
        void testWhenDataTypeIsNotSupported() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new UnsupportedRequireNonEmptyForTest(new StringBuilder())));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.annotation.RequireNonEmpty annotation supports String, Array, List, Map, Set type, but was specified for the variable org.thinkit.framework.envali.UnsupportedRequireNonEmptyForTest#sb of type java.lang.StringBuilder.",
                    exception.getMessage());
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
        void testWhenNestedGenericEntityIsNotValidatable() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new NestedEntityWithNotValidatableGenericEntityForTest(List.of())));

            assertNotNull(exception);
            assertEquals(
                    "The generic type specified for collection java.util.List<java.lang.String>#genericEntity does not implement the org.thinkit.framework.envali.entity.ValidatableEntity interface.",
                    exception.getMessage());
        }

        @Test
        void testWhenNestedEntityIsNotValidatable() {
            final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                    () -> Envali.validate(new NestedEntityWithNotValidatableEntityForTest(new StringBuilder())));

            assertNotNull(exception);
            assertEquals(
                    "The org.thinkit.framework.envali.NestedEntityWithNotValidatableEntityForTest#entity does not implement the org.thinkit.framework.envali.entity.ValidatableEntity interface.",
                    exception.getMessage());
        }

        @Test
        void testWhenNestedParameterizedEntitiesAreValidatable() {
            assertDoesNotThrow(() -> Envali.validate(new NestedParameterizedValidatableEntityForTest(
                    List.of(new RequireNegativeForTest(-1)), Map.of("test", new RecoverableRequireNegativeForTest(-1)),
                    Set.of(new UnrecoverableRequireNegativeForTest(-1)))));
        }

        @Test
        void testWhenNestedParameterizedEntityIsNotValidatableAtRuntime() {
            assertThrows(PreconditionFailedException.class, () -> Envali
                    .validate(new NestedParameterizedValidatableEntityForTest(List.of(new RequireNegativeForTest(0)),
                            Map.of("test", new RecoverableRequireNegativeForTest(-1)),
                            Set.of(new UnrecoverableRequireNegativeForTest(-1)))));
        }

        @Test
        void testWhenNestedParameterizedEntitiesAreNotValidatable() {
            final ValidationResult validationResult = assertDoesNotThrow(() -> Envali
                    .validate(new NestedParameterizedValidatableEntityForTest(List.of(new RequireNegativeForTest(-1)),
                            Map.of("test", new RecoverableRequireNegativeForTest(0)),
                            Set.of(new UnrecoverableRequireNegativeForTest(0)))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(NestedParameterizedValidatableEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(businessErrors.size() == 2);
            assertTrue(businessErrors.get(0).hasNestedError());
            assertTrue(businessErrors.get(1).hasNestedError());

            final ValidationResult nestedValidationResultForRecoverable = businessErrors.get(0).getNestedError();
            final ValidationResult nestedValidationResultForUnrecoverable = businessErrors.get(1).getNestedError();

            assertNotNull(nestedValidationResultForRecoverable);
            assertNotNull(nestedValidationResultForUnrecoverable);
            assertTrue(nestedValidationResultForRecoverable.hasError());
            assertTrue(nestedValidationResultForUnrecoverable.hasError());

            final List<BusinessError> businessErrorsForRecoverable = nestedValidationResultForRecoverable
                    .getError(RecoverableRequireNegativeForTest.class);
            final List<BusinessError> businessErrorsForUnrecoverable = nestedValidationResultForUnrecoverable
                    .getError(UnrecoverableRequireNegativeForTest.class);

            assertNotNull(businessErrorsForRecoverable);
            assertNotNull(businessErrorsForUnrecoverable);
            assertTrue(businessErrorsForRecoverable.size() == 1);
            assertTrue(businessErrorsForUnrecoverable.size() == 1);
            assertTrue(businessErrorsForRecoverable.get(0).isRecoverable());
            assertTrue(businessErrorsForUnrecoverable.get(0).isUnrecoverable());
            assertEquals("success", businessErrorsForRecoverable.get(0).getMessage());
            assertEquals("success", businessErrorsForUnrecoverable.get(0).getMessage());

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
    @TestInstance(Lifecycle.PER_CLASS)
    class TestRecoverableRequireNonEmpty {

        Stream<Arguments> nonEmptyObjectProvider() {
            return Stream.of(Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "test" }, List.of("test"), Map.of("test", "test"),
                            Set.of("test")));
        }

        Stream<Arguments> emptyObjectProvider() {
            return Stream.of(Arguments.of("", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] {}, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(""), Map.of(), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of()));
        }

        @ParameterizedTest
        @MethodSource("nonEmptyObjectProvider")
        void testWhenObjectIsNotEmpty(final String literal, final String[] literalArray, final List<String> literalList,
                final Map<String, String> literalMap, final Set<String> literalSet) {
            final ValidationResult validationResult = assertDoesNotThrow(() -> Envali.validate(
                    new RecoverableRequireNonEmptyForTest(literal, literalArray, literalList, literalMap, literalSet)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @MethodSource("emptyObjectProvider")
        void testWhenObjectIsEmpty(final String literal, final String[] literalArray, final List<String> literalList,
                final Map<String, String> literalMap, final Set<String> literalSet) {
            final ValidationResult validationResult = assertDoesNotThrow(() -> Envali.validate(
                    new RecoverableRequireNonEmptyForTest(literal, literalArray, literalList, literalMap, literalSet)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> buinessErrors = validationResult
                    .getError(RecoverableRequireNonEmptyForTest.class);

            assertNotNull(buinessErrors);
            assertTrue(!buinessErrors.isEmpty());
            assertTrue(buinessErrors.size() == 1);
            assertTrue(buinessErrors.get(0).isRecoverable());
            assertEquals("success", buinessErrors.get(0).getMessage());
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

    @Nested
    class TestRecoverableRequirePositive {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testPositiveCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequirePositiveForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testNegativeCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequirePositiveForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequirePositiveForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireNegative {

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testNegativeCases(int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireNegativeForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testPositiveCases(int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableRequireNegativeForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireNegativeForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireRangeFrom {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 9, 10, 11, Integer.MAX_VALUE })
        void testWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -9, -2, -1 })
        void testNotWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(longs = { 0l, 1l, 2l, 9l, 10l, 11l, Long.MAX_VALUE })
        void testLongWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -9l, -2l, -1l })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(shorts = { 0, 1, 2, 9, 10, 11, Short.MAX_VALUE })
        void testShortWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -9, -2, -1 })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(bytes = { 0, 1, 2, 9, 10, 11, Byte.MAX_VALUE })
        void testByteWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -9, -2, -1 })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(floats = { 0.0f, 0.1f, 1.0f, 0f, 2.0f, 9.0f, 10.0f, 11.0f, Float.MAX_VALUE })
        void testFloatWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -9.0f, -2.0f, -1.0f, -0.1f })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = { 0.0d, 0.1d, 1.0d, 0d, 2.0d, 9.0d, 10.0d, 11.0d, Double.MAX_VALUE })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -9.0d, -2.0d, -1.0d, -0.1d })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireRangeTo {

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { 11, 12, 100, 1000, Integer.MAX_VALUE })
        void testNotWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(longs = { 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(shorts = { 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(bytes = { 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -1.0f, -0.1f, 0.0f, 0.1f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(floats = { 10.1f, 10.2f, 11.0f, 12.0f, 100.0f, Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -1.0d, -0.1d, 0.0d, 0.1d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(doubles = { 10.1d, 10.2d, 11.0d, 12.0d, 100.0d, Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestRecoverableRequireRangeFromTo {

        @ParameterizedTest
        @ValueSource(ints = { -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsFromToCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -100, -12, -11, 11, 12, 100, 1000, Integer.MAX_VALUE })
        void testNotWithinTheLimitsFromToCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(longs = { -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsFromToCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -100l, -12l, -11l, 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsFromToCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(shorts = { -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsFromToCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -100, -12, -11, 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsFromToCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(bytes = { -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsFromToCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -100, -12, -11, 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsFromToCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(floats = { -10.0f, -1.0f, -0.1f, 0.0f, 0.1f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsFromToCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -100.0f, -12.0f, -11.0f, -10.1f, 10.1f, 11.0f, 12.0f, 100.0f,
                Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsFromToCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -10.0d, -1.0d, -0.1d, 0.0d, 0.1d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsFromToCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -100.0d, -12.0d, -11.0d, -10.1d, 10.1d, 11.0d, 12.0d, 100.0d,
                Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsFromToCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(RecoverableRequireRangeFromToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(RecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class TestRecoverableNestedEntity {

        Stream<Arguments> noErrorObjectProvider() {
            return Stream.of(Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "test" }, List.of("test"), Map.of("test", "test"),
                            Set.of("test")));
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenNoError(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableNestedEntityForTest(List.of(""), Set.of(""),
                            new RecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenThereIsErrorOfListAtFirstLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableNestedEntityForTest(List.of(), Set.of(""),
                            new RecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult.getError(RecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("first layer 1", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenThereIsErrorOfSetAtFirstLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableNestedEntityForTest(List.of(""), Set.of(),
                            new RecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult.getError(RecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).isRecoverable());
            assertEquals("first layer 2", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenThereAreErrorsAtFirstLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableNestedEntityForTest(List.of(), Set.of(),
                            new RecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult.getError(RecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 2);

            for (int i = 0, size = businessErrors.size(); i < size; i++) {
                assertTrue(businessErrors.get(i).isRecoverable());
                assertEquals(String.format("first layer %s", i + 1), businessErrors.get(i).getMessage());
            }
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testWhenThereIsErrorAtSecondLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableNestedEntityForTest(List.of(""), Set.of(""),
                            new RecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult.getError(RecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasNestedError());

            final ValidationResult nestedValidationResult = businessErrors.get(0).getNestedError();

            assertNotNull(nestedValidationResult);
            assertTrue(nestedValidationResult.hasError());

            final List<BusinessError> nestedBusinessErrors = nestedValidationResult
                    .getError(RecoverableRequireNegativeForTest.class);

            assertNotNull(nestedBusinessErrors);
            assertTrue(!nestedBusinessErrors.isEmpty());
            assertTrue(nestedBusinessErrors.size() == 1);
            assertTrue(nestedBusinessErrors.get(0).isRecoverable());
            assertEquals("success", nestedBusinessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testWhenThereAreErrorsAtAllLayers(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new RecoverableNestedEntityForTest(List.of(), Set.of(),
                            new RecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult.getError(RecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 3);

            for (int i = 0, size = businessErrors.size(); i < size; i++) {
                final BusinessError businessError = businessErrors.get(i);

                if (businessError.hasNestedError()) {
                    final ValidationResult nestedValidationResult = businessError.getNestedError();

                    assertNotNull(nestedValidationResult);
                    assertTrue(nestedValidationResult.hasError());

                    final List<BusinessError> nestedBusinessErrors = nestedValidationResult
                            .getError(RecoverableRequireNegativeForTest.class);

                    assertNotNull(nestedBusinessErrors);
                    assertTrue(!nestedBusinessErrors.isEmpty());
                    assertTrue(nestedBusinessErrors.size() == 1);
                    assertTrue(nestedBusinessErrors.get(0).isRecoverable());
                    assertEquals("success", nestedBusinessErrors.get(0).getMessage());
                } else {
                    assertTrue(businessError.isRecoverable());
                    assertEquals(String.format("first layer %s", i + 1), businessError.getMessage());
                }
            }
        }
    }

    @Nested
    class TestUnrecoverableRequireNonNull {

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "　", "test", "t" })
        void testWhenLiteralIsNotNull(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNonNullForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @Test
        void testWhenLiteralIsNull() {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNonNullForTest(null)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireNonNullForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequireNonBlank {

        @ParameterizedTest
        @ValueSource(strings = { " ", "　", "test", "t" })
        void testWhenLiteralIsNotBlank(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNonBlankForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @Test
        void testWhenLiteralIsBlank() {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNonBlankForTest("")));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireNonBlankForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class TestUnrecoverableRequireNonEmpty {

        Stream<Arguments> nonEmptyObjectProvider() {
            return Stream.of(Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "test" }, List.of("test"), Map.of("test", "test"),
                            Set.of("test")));
        }

        Stream<Arguments> emptyObjectProvider() {
            return Stream.of(Arguments.of("", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] {}, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(""), Map.of(), Set.of("")),
                    Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of()));
        }

        @ParameterizedTest
        @MethodSource("nonEmptyObjectProvider")
        void testWhenObjectIsNotEmpty(final String literal, final String[] literalArray, final List<String> literalList,
                final Map<String, String> literalMap, final Set<String> literalSet) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNonEmptyForTest(literal, literalArray, literalList,
                            literalMap, literalSet)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @MethodSource("emptyObjectProvider")
        void testWhenObjectIsEmpty(final String literal, final String[] literalArray, final List<String> literalList,
                final Map<String, String> literalMap, final Set<String> literalSet) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNonEmptyForTest(literal, literalArray, literalList,
                            literalMap, literalSet)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> buinessErrors = validationResult
                    .getError(UnrecoverableRequireNonEmptyForTest.class);

            assertNotNull(buinessErrors);
            assertTrue(!buinessErrors.isEmpty());
            assertTrue(buinessErrors.size() == 1);
            assertTrue(buinessErrors.get(0).isUnrecoverable());
            assertEquals("success", buinessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequireStartWith {

        @ParameterizedTest
        @ValueSource(strings = { "start something", "startsomething", "start" })
        void testWhenLiteralStartsWithSpecifiedPrefix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireStartWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "star", "tart", "something start", "something start something", "aaastartaaa",
                "somethingstart" })
        void testWhenLiteralDoesNotStartWithSpecifiedPrefix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireStartWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireStartWithForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequireEndWith {

        @ParameterizedTest
        @ValueSource(strings = { "something end", "somethingend", "end" })
        void testWhenLiteralEndsWithSpecifiedSuffix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireEndWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "nd", "en", "endsomething", "something end something", "aaaendaaa",
                "endsomething" })
        void testWhenLiteralDoesNotEndWithSpecifiedSuffix(final String parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireEndWithForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireEndWithForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequirePositive {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testPositiveCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequirePositiveForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testNegativeCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequirePositiveForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequirePositiveForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequireNegative {

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testNegativeCases(int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNegativeForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testPositiveCases(int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableRequireNegativeForTest(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireNegativeForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequireRangeFrom {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 9, 10, 11, Integer.MAX_VALUE })
        void testWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -9, -2, -1 })
        void testNotWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(longs = { 0l, 1l, 2l, 9l, 10l, 11l, Long.MAX_VALUE })
        void testLongWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -9l, -2l, -1l })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(shorts = { 0, 1, 2, 9, 10, 11, Short.MAX_VALUE })
        void testShortWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -9, -2, -1 })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(bytes = { 0, 1, 2, 9, 10, 11, Byte.MAX_VALUE })
        void testByteWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -9, -2, -1 })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(floats = { 0.0f, 0.1f, 1.0f, 0f, 2.0f, 9.0f, 10.0f, 11.0f, Float.MAX_VALUE })
        void testFloatWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -9.0f, -2.0f, -1.0f, -0.1f })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = { 0.0d, 0.1d, 1.0d, 0d, 2.0d, 9.0d, 10.0d, 11.0d, Double.MAX_VALUE })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -9.0d, -2.0d, -1.0d, -0.1d })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequireRangeTo {

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { 11, 12, 100, 1000, Integer.MAX_VALUE })
        void testNotWithinTheLimitsCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(longs = { 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(shorts = { 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(bytes = { 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -1.0f, -0.1f, 0.0f, 0.1f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(floats = { 10.1f, 10.2f, 11.0f, 12.0f, 100.0f, Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -1.0d, -0.1d, 0.0d, 0.1d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(doubles = { 10.1d, 10.2d, 11.0d, 12.0d, 100.0d, Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    class TestUnrecoverableRequireRangeFromTo {

        @ParameterizedTest
        @ValueSource(ints = { -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsFromToCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -100, -12, -11, 11, 12, 100, 1000, Integer.MAX_VALUE })
        void testNotWithinTheLimitsFromToCases(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofInt(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(longs = { -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsFromToCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -100l, -12l, -11l, 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsFromToCases(final long parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofLong(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(shorts = { -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsFromToCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -100, -12, -11, 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsFromToCases(final short parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofShort(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(bytes = { -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsFromToCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -100, -12, -11, 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsFromToCases(final byte parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofByte(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(floats = { -10.0f, -1.0f, -0.1f, 0.0f, 0.1f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsFromToCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -100.0f, -12.0f, -11.0f, -10.1f, 10.1f, 11.0f, 12.0f, 100.0f,
                Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsFromToCases(final float parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofFloat(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -10.0d, -1.0d, -0.1d, 0.0d, 0.1d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsFromToCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -100.0d, -12.0d, -11.0d, -10.1d, 10.1d, 11.0d, 12.0d, 100.0d,
                Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsFromToCases(final double parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(UnrecoverableRequireRangeFromToForTest.ofDouble(parameter)));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableRequireRangeFromToForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasError());
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("success", businessErrors.get(0).getMessage());
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class TestUnrecoverableNestedEntity {

        Stream<Arguments> noErrorObjectProvider() {
            return Stream.of(Arguments.of("test", new String[] { "" }, List.of(""), Map.of("", ""), Set.of("")),
                    Arguments.of("test", new String[] { "test" }, List.of("test"), Map.of("test", "test"),
                            Set.of("test")));
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenNoError(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableNestedEntityForTest(List.of(""), Set.of(""),
                            new UnrecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(!validationResult.hasError());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenThereIsErrorOfListAtFirstLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableNestedEntityForTest(List.of(), Set.of(""),
                            new UnrecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("first layer 1", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenThereIsErrorOfSetAtFirstLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableNestedEntityForTest(List.of(""), Set.of(),
                            new UnrecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).isUnrecoverable());
            assertEquals("first layer 2", businessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, -2, -10, -100, -1000 })
        void testWhenThereAreErrorsAtFirstLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableNestedEntityForTest(List.of(), Set.of(),
                            new UnrecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 2);

            for (int i = 0, size = businessErrors.size(); i < size; i++) {
                assertTrue(businessErrors.get(i).isUnrecoverable());
                assertEquals(String.format("first layer %s", i + 1), businessErrors.get(i).getMessage());
            }
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testWhenThereIsErrorAtSecondLayer(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableNestedEntityForTest(List.of(""), Set.of(""),
                            new UnrecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 1);
            assertTrue(businessErrors.get(0).hasNestedError());

            final ValidationResult nestedValidationResult = businessErrors.get(0).getNestedError();

            assertNotNull(nestedValidationResult);
            assertTrue(nestedValidationResult.hasError());

            final List<BusinessError> nestedBusinessErrors = nestedValidationResult
                    .getError(UnrecoverableRequireNegativeForTest.class);

            assertNotNull(nestedBusinessErrors);
            assertTrue(!nestedBusinessErrors.isEmpty());
            assertTrue(nestedBusinessErrors.size() == 1);
            assertTrue(nestedBusinessErrors.get(0).isUnrecoverable());
            assertEquals("success", nestedBusinessErrors.get(0).getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 10, 100, 1000 })
        void testWhenThereAreErrorsAtAllLayers(final int parameter) {
            final ValidationResult validationResult = assertDoesNotThrow(
                    () -> Envali.validate(new UnrecoverableNestedEntityForTest(List.of(), Set.of(),
                            new UnrecoverableRequireNegativeForTest(parameter))));

            assertNotNull(validationResult);
            assertTrue(validationResult.hasError());

            final List<BusinessError> businessErrors = validationResult
                    .getError(UnrecoverableNestedEntityForTest.class);

            assertNotNull(businessErrors);
            assertTrue(!businessErrors.isEmpty());
            assertTrue(businessErrors.size() == 3);

            for (int i = 0, size = businessErrors.size(); i < size; i++) {
                final BusinessError businessError = businessErrors.get(i);

                if (businessError.hasNestedError()) {
                    final ValidationResult nestedValidationResult = businessError.getNestedError();

                    assertNotNull(nestedValidationResult);
                    assertTrue(nestedValidationResult.hasError());

                    final List<BusinessError> nestedBusinessErrors = nestedValidationResult
                            .getError(UnrecoverableRequireNegativeForTest.class);

                    assertNotNull(nestedBusinessErrors);
                    assertTrue(!nestedBusinessErrors.isEmpty());
                    assertTrue(nestedBusinessErrors.size() == 1);
                    assertTrue(nestedBusinessErrors.get(0).isUnrecoverable());
                    assertEquals("success", nestedBusinessErrors.get(0).getMessage());
                } else {
                    assertTrue(businessError.isUnrecoverable());
                    assertEquals(String.format("first layer %s", i + 1), businessError.getMessage());
                }
            }
        }
    }

    @Nested
    class TestAnnotationParamRequireStartWith {

        @ParameterizedTest
        @ValueSource(strings = { "test something", "testsomething", "test" })
        void testWhenLiteralStartsWithSpecifiedPrefix(final String parameter) {
            assertDoesNotThrow(() -> Envali.validate(new AnnotationParamRequireStartWithForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "tes", "est", "something test", "something test something", "aaatestaaa",
                "somethingstart" })
        void testWhenLiteralDoesNotStartWithSpecifiedPrefix(final String parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new AnnotationParamRequireStartWithForTest(parameter)));
        }
    }

    @Nested
    class TestAnnotationParamRequireEndWith {

        @ParameterizedTest
        @ValueSource(strings = { "something test", "somethingtest", "test" })
        void testWhenLiteralEndsWithSpecifiedPrefix(final String parameter) {
            assertDoesNotThrow(() -> Envali.validate(new AnnotationParamRequireEndWithForTest(parameter)));
        }

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "est", "tes", "testsomething", "something test something", "aaatestaaa",
                "endsomething" })
        void testWhenLiteralDoesNotEndWithSpecifiedPrefix(final String parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(new AnnotationParamRequireEndWithForTest(parameter)));
        }
    }

    @Nested
    class TestAnnotationParamRequireRangeFrom {

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 9, 10, 11, Integer.MAX_VALUE })
        void testWithinTheLimitsCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -9, -2, -1 })
        void testNotWithinTheLimitsCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { 0l, 1l, 2l, 9l, 10l, 11l, Long.MAX_VALUE })
        void testLongWithinTheLimitsCases(final long parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -9l, -2l, -1l })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { 0, 1, 2, 9, 10, 11, Short.MAX_VALUE })
        void testShortWithinTheLimitsCases(final short parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -9, -2, -1 })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { 0, 1, 2, 9, 10, 11, Byte.MAX_VALUE })
        void testByteWithinTheLimitsCases(final byte parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -9, -2, -1 })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { 0.0f, 0.01f, 0.1f, 1.0f, 2.0f, 9.0f, 10.0f, 11.0f, Float.MAX_VALUE })
        void testFloatWithinTheLimitsCases(final float parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -9.0f, -2.0f, -1.0f, -0.1f, -0.01f })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { 0.0d, 0.01d, 0.1d, 1.0d, 2.0d, 9.0d, 10.0d, 11.0d, Double.MAX_VALUE })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofDouble(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -9.0d, -2.0d, -1.0d, -0.1d, -0.01d })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromForTest.ofDouble(parameter)));
        }
    }

    @Nested
    class TestAnnotationParamRequireRangeTo {

        @ParameterizedTest
        @ValueSource(ints = { Integer.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { 11, 12, 100, 1000 })
        void testNotWithinTheLimitsCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsCases(final long parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsCases(final long parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsCases(final short parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsCases(final short parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsCases(final byte parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsCases(final byte parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -10.0f, -1.0f, 0.0f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsCases(final float parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { 11.0f, 12.0f, 100.0f, Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsCases(final float parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -10.0d, -1.0d, 0.0d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsCases(final double parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeToForTest.ofDouble(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { 11.0d, 12.0d, 100.0d, Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsCases(final double parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeToForTest.ofDouble(parameter)));
        }
    }

    @Nested
    class TestAnnotationParamRequireRangeFromTo {

        @ParameterizedTest
        @ValueSource(ints = { -10, -1, 0, 1, 9, 10 })
        void testWithinTheLimitsFromToCases(final int parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(ints = { -100, -12, -11, 11, 12, 100, 1000 })
        void testNotWithinTheLimitsFromToCases(final int parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofInt(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { -10l, -1l, 0l, 1l, 9l, 10l })
        void testLongWithinTheLimitsFromToCases(final long parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(longs = { Long.MIN_VALUE, -100l, -12l, -11l, 11l, 12l, 100l, 1000l, Long.MAX_VALUE })
        void testLongNotWithinTheLimitsFromToCases(final long parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofLong(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { -10, -1, 0, 1, 9, 10 })
        void testShortWithinTheLimitsFromToCases(final short parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(shorts = { Short.MIN_VALUE, -100, -12, -11, 11, 12, 100, 1000, Short.MAX_VALUE })
        void testShortNotWithinTheLimitsFromToCases(final short parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofShort(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { -10, -1, 0, 1, 9, 10 })
        void testByteWithinTheLimitsFromToCases(final byte parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(bytes = { Byte.MIN_VALUE, -100, -12, -11, 11, 12, 100, Byte.MAX_VALUE })
        void testByteNotWithinTheLimitsFromToCases(final byte parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofByte(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -10.0f, -1.0f, -0.1f, 0.0f, 0.1f, 1.0f, 9.0f, 10.0f })
        void testFloatWithinTheLimitsFromToCases(final float parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(floats = { -Float.MAX_VALUE, -100.0f, -12.0f, -11.0f, 11.0f, 12.0f, 100.0f, 1000.0f,
                Float.MAX_VALUE })
        void testFloatNotWithinTheLimitsFromToCases(final float parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofFloat(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -10.0d, -1.0d, -0.1d, 0.0d, 0.1d, 1.0d, 9.0d, 10.0d })
        void testDoubleWithinTheLimitsFromToCases(final double parameter) {
            assertDoesNotThrow(() -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofDouble(parameter)));
        }

        @ParameterizedTest
        @ValueSource(doubles = { -Double.MAX_VALUE, -100.0d, -12.0d, -11.0d, 11.0d, 12.0d, 100.0d, 1000.0d,
                Double.MAX_VALUE })
        void testDoubleNotWithinTheLimitsFromToCases(final double parameter) {
            assertThrows(PreconditionFailedException.class,
                    () -> Envali.validate(AnnotationParamRequireRangeFromToForTest.ofDouble(parameter)));
        }
    }
}
