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
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the recoverable {@link RequireRangeFromTo}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@ParameterMapping(content = "RequireRangeFromToForTest")
final class RecoverableRequireRangeFromToForTest implements ValidatableEntity {

    /**
     * The number within the limits from specific number
     */
    @RequireRangeFromTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private int intFromTo;
    /**
     * The long number within the limits
     */
    @RequireRangeFromTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private long longFromTo;

    /**
     * The short number within the limits
     */
    @RequireRangeFromTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private short shortFromTo;

    /**
     * The byte number within the limits
     */
    @RequireRangeFromTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private byte byteFromTo;

    /**
     * The float number within the limits
     */
    @RequireRangeFromTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private float floatFromTo;

    /**
     * The double number within the limits
     */
    @RequireRangeFromTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private double doubleFromTo;

    /**
     * Default constructor
     */
    private RecoverableRequireRangeFromToForTest() {
    }

    /**
     * Constructor
     *
     * @param intFromTo    The int number within the limits (expected)
     * @param longFromTo   The long number within the limits (expected)
     * @param shortFromTo  The short number within the limits (expected)
     * @param byteFromTo   The byte number within the limits (expected)
     * @param floatFromTo  The float number within the limits (expected)
     * @param doubleFromTo The double number within the limits (expected)
     */
    private RecoverableRequireRangeFromToForTest(int intFromTo, long longFromTo, short shortFromTo, byte byteFromTo,
            float floatFromTo, double doubleFromTo) {
        this.intFromTo = intFromTo;
        this.longFromTo = longFromTo;
        this.shortFromTo = shortFromTo;
        this.byteFromTo = byteFromTo;
        this.floatFromTo = floatFromTo;
        this.doubleFromTo = doubleFromTo;
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeFromToForTest} with
     * int test value.
     *
     * @param intFromTo The int value to be tested
     * @return The new instance of {@link RecoverableRequireRangeFromToForTest} with
     *         int test value
     */
    public static RecoverableRequireRangeFromToForTest ofInt(int intFromTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeFromToForTest(intFromTo, 1l, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeFromToForTest} with
     * long test value.
     *
     * @param longFromTo The long value to be tested
     * @return The new instance of {@link RecoverableRequireRangeFromToForTest} with
     *         long test value
     */
    public static RecoverableRequireRangeFromToForTest ofLong(long longFromTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeFromToForTest(1, longFromTo, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeFromToForTest} with
     * short test value.
     *
     * @param shortFromTo The short value to be tested
     * @return The new instance of {@link RecoverableRequireRangeFromToForTest} with
     *         short test value
     */
    public static RecoverableRequireRangeFromToForTest ofShort(short shortFromTo) {
        byte byteNumber = 1;
        return new RecoverableRequireRangeFromToForTest(1, 1l, shortFromTo, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeFromToForTest} with
     * byte test value.
     *
     * @param byteFromTo The byte value to be tested
     * @return The new instance of {@link RecoverableRequireRangeFromToForTest} with
     *         byte test value
     */
    public static RecoverableRequireRangeFromToForTest ofByte(byte byteFromTo) {
        short shortNumber = 1;
        return new RecoverableRequireRangeFromToForTest(1, 1l, shortNumber, byteFromTo, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeFromToForTest} with
     * float test value.
     *
     * @param floatFromTo The float value to be tested
     * @return The new instance of {@link RecoverableRequireRangeFromToForTest} with
     *         float test value
     */
    public static RecoverableRequireRangeFromToForTest ofFloat(float floatFromTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeFromToForTest(1, 1l, shortNumber, byteNumber, floatFromTo, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeFromToForTest} with
     * double test value.
     *
     * @param doubleFromTo The double value to be tested
     * @return The new instance of {@link RecoverableRequireRangeFromToForTest} with
     *         double test value
     */
    public static RecoverableRequireRangeFromToForTest ofDouble(double doubleFromTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeFromToForTest(1, 1l, shortNumber, byteNumber, 0.1f, doubleFromTo);
    }
}
