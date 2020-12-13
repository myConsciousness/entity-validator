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
 * {@link Envali} interface and the recoverable {@link RequireRangeTo}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@ParameterMapping(content = "RequireRangeToForTest")
final class RecoverableRequireRangeToForTest implements ValidatableEntity {

    /**
     * The number within the limits
     */
    @RequireRangeTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private int intTo;

    /**
     * The long number within the limits
     */
    @RequireRangeTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private long longTo;

    /**
     * The short number within the limits
     */
    @RequireRangeTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private short shortTo;

    /**
     * The byte number within the limits
     */
    @RequireRangeTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private byte byteTo;

    /**
     * The float number within the limits
     */
    @RequireRangeTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private float floatTo;

    /**
     * The double number within the limits
     */
    @RequireRangeTo(errorType = ErrorType.RECOVERABLE, message = "success")
    private double doubleTo;

    /**
     * Default constructor
     */
    private RecoverableRequireRangeToForTest() {
    }

    /**
     * Constructor
     *
     * @param intTo    The int number within the limits (expected)
     * @param longTo   The long number within the limits (expected)
     * @param shortTo  The short number within the limits (expected)
     * @param byteTo   The byte number within the limits (expected)
     * @param floatTo  The float number within the limits (expected)
     * @param doubleTo The double number within the limits (expected)
     */
    private RecoverableRequireRangeToForTest(int intTo, long longTo, short shortTo, byte byteTo, float floatTo,
            double doubleTo) {
        this.intTo = intTo;
        this.longTo = longTo;
        this.shortTo = shortTo;
        this.byteTo = byteTo;
        this.floatTo = floatTo;
        this.doubleTo = doubleTo;
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeToForTest} with int
     * test value.
     *
     * @param intTo The int value to be tested
     * @return The new instance of {@link RecoverableRequireRangeToForTest} with int
     *         test value
     */
    public static RecoverableRequireRangeToForTest ofInt(int intTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeToForTest(intTo, 1l, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeToForTest} with
     * long test value.
     *
     * @param longTo The long value to be tested
     * @return The new instance of {@link RecoverableRequireRangeToForTest} with
     *         long test value
     */
    public static RecoverableRequireRangeToForTest ofLong(long longTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeToForTest(1, longTo, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeToForTest} with
     * short test value.
     *
     * @param shortTo The short value to be tested
     * @return The new instance of {@link RecoverableRequireRangeToForTest} with
     *         short test value
     */
    public static RecoverableRequireRangeToForTest ofShort(short shortTo) {
        byte byteNumber = 1;
        return new RecoverableRequireRangeToForTest(1, 1l, shortTo, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeToForTest} with
     * byte test value.
     *
     * @param byteTo The byte value to be tested
     * @return The new instance of {@link RecoverableRequireRangeToForTest} with
     *         byte test value
     */
    public static RecoverableRequireRangeToForTest ofByte(byte byteTo) {
        short shortNumber = 1;
        return new RecoverableRequireRangeToForTest(1, 1l, shortNumber, byteTo, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeToForTest} with
     * float test value.
     *
     * @param floatTo The float value to be tested
     * @return The new instance of {@link RecoverableRequireRangeToForTest} with
     *         float test value
     */
    public static RecoverableRequireRangeToForTest ofFloat(float floatTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeToForTest(1, 1l, shortNumber, byteNumber, floatTo, 0.1d);
    }

    /**
     * Returns the new instance of {@link RecoverableRequireRangeToForTest} with
     * double test value.
     *
     * @param doubleTo The double value to be tested
     * @return The new instance of {@link RecoverableRequireRangeToForTest} with
     *         double test value
     */
    public static RecoverableRequireRangeToForTest ofDouble(double doubleTo) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RecoverableRequireRangeToForTest(1, 1l, shortNumber, byteNumber, 0.1f, doubleTo);
    }
}
