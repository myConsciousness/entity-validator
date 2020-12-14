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
import org.thinkit.framework.envali.annotation.RequireRangeFrom;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the unrecoverable {@link RequireRangeFrom}
 * annotation.
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@ParameterMapping(content = "RequireRangeFromForTest")
final class UnrecoverableRequireRangeFromForTest implements ValidatableEntity {

    /**
     * The number within the limits
     */
    @RequireRangeFrom(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private int intFrom;

     /**
     * The long number within the limits
     */
    @RequireRangeFrom(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private long longFrom;

    /**
     * The short number within the limits
     */
    @RequireRangeFrom(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private short shortFrom;

    /**
     * The byte number within the limits
     */
    @RequireRangeFrom(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private byte byteFrom;

    /**
     * The float number within the limits
     */
    @RequireRangeFrom(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private float floatFrom;

    /**
     * The double number within the limits
     */
    @RequireRangeFrom(errorType = ErrorType.UNRECOVERABLE, message = "success")
    private double doubleFrom;

    /**
     * Default constructor
     */
    private UnrecoverableRequireRangeFromForTest() {
    }

    /**
     * Constructor
     *
     * @param intFrom    The int number within the limits (expected)
     * @param longFrom   The long number within the limits (expected)
     * @param shortFrom  The short number within the limits (expected)
     * @param byteFrom   The byte number within the limits (expected)
     * @param floatFrom  The float number within the limits (expected)
     * @param doubleFrom The double number within the limits (expected)
     */
    private UnrecoverableRequireRangeFromForTest(int intFrom, long longFrom, short shortFrom, byte byteFrom, float floatFrom,
            double doubleFrom) {
        this.intFrom = intFrom;
        this.longFrom = longFrom;
        this.shortFrom = shortFrom;
        this.byteFrom = byteFrom;
        this.floatFrom = floatFrom;
        this.doubleFrom = doubleFrom;
    }

    /**
     * Returns the new instance of {@link UnrecoverableRequireRangeFromForTest} with int test
     * value.
     *
     * @param intFrom The int value to be tested
     * @return The new instance of {@link UnrecoverableRequireRangeFromForTest} with int test
     *         value
     */
    public static UnrecoverableRequireRangeFromForTest ofInt(int intFrom) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new UnrecoverableRequireRangeFromForTest(intFrom, 1l, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link UnrecoverableRequireRangeFromForTest} with long test
     * value.
     *
     * @param longFrom The long value to be tested
     * @return The new instance of {@link UnrecoverableRequireRangeFromForTest} with long test
     *         value
     */
    public static UnrecoverableRequireRangeFromForTest ofLong(long longFrom) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new UnrecoverableRequireRangeFromForTest(1, longFrom, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link UnrecoverableRequireRangeFromForTest} with short test
     * value.
     *
     * @param shortFrom The short value to be tested
     * @return The new instance of {@link UnrecoverableRequireRangeFromForTest} with short test
     *         value
     */
    public static UnrecoverableRequireRangeFromForTest ofShort(short shortFrom) {
        byte byteNumber = 1;
        return new UnrecoverableRequireRangeFromForTest(1, 1l, shortFrom, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link UnrecoverableRequireRangeFromForTest} with byte test
     * value.
     *
     * @param byteFrom The byte value to be tested
     * @return The new instance of {@link UnrecoverableRequireRangeFromForTest} with byte test
     *         value
     */
    public static UnrecoverableRequireRangeFromForTest ofByte(byte byteFrom) {
        short shortNumber = 1;
        return new UnrecoverableRequireRangeFromForTest(1, 1l, shortNumber, byteFrom, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link UnrecoverableRequireRangeFromForTest} with float test
     * value.
     *
     * @param floatFrom The float value to be tested
     * @return The new instance of {@link UnrecoverableRequireRangeFromForTest} with float test
     *         value
     */
    public static UnrecoverableRequireRangeFromForTest ofFloat(float floatFrom) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new UnrecoverableRequireRangeFromForTest(1, 1l, shortNumber, byteNumber, floatFrom, 0.1d);
    }

    /**
     * Returns the new instance of {@link UnrecoverableRequireRangeFromForTest} with double test
     * value.
     *
     * @param doubleFrom The double value to be tested
     * @return The new instance of {@link UnrecoverableRequireRangeFromForTest} with double test
     *         value
     */
    public static UnrecoverableRequireRangeFromForTest ofDouble(double doubleFrom) {
        short shortNumber = 1;
        byte byteNumber =1;
        return new UnrecoverableRequireRangeFromForTest(1, 1l, shortNumber, byteNumber, 0.1f, doubleFrom);
    }
}
