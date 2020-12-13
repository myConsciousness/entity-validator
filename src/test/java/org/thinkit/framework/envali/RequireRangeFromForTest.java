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
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * The entity class that defines fields to be used when testing the
 * {@link Envali} interface and the {@link RequireRangeFrom} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ParameterMapping(content = "RequireRangeFromForTest")
final class RequireRangeFromForTest implements ValidatableEntity {

    /**
     * The int number within the limits
     */
    @RequireRangeFrom
    private int intFrom;

    /**
     * The long number within the limits
     */
    @RequireRangeFrom
    private long longFrom;

    /**
     * The short number within the limits
     */
    @RequireRangeFrom
    private short shortFrom;

    /**
     * The byte number within the limits
     */
    @RequireRangeFrom
    private byte byteFrom;

    /**
     * The float number within the limits
     */
    @RequireRangeFrom
    private float floatFrom;

    /**
     * The double number within the limits
     */
    @RequireRangeFrom
    private double doubleFrom;

    /**
     * Default constructor
     */
    private RequireRangeFromForTest() {
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
    private RequireRangeFromForTest(int intFrom, long longFrom, short shortFrom, byte byteFrom, float floatFrom,
            double doubleFrom) {
        this.intFrom = intFrom;
        this.longFrom = longFrom;
        this.shortFrom = shortFrom;
        this.byteFrom = byteFrom;
        this.floatFrom = floatFrom;
        this.doubleFrom = doubleFrom;
    }

    /**
     * Returns the new instance of {@link RequireRangeFromForTest} with int test
     * value.
     *
     * @param intFrom The int value to be tested
     * @return The new instance of {@link RequireRangeFromForTest} with int test
     *         value
     */
    public static RequireRangeFromForTest ofInt(int intFrom) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RequireRangeFromForTest(intFrom, 1l, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RequireRangeFromForTest} with long test
     * value.
     *
     * @param longFrom The long value to be tested
     * @return The new instance of {@link RequireRangeFromForTest} with long test
     *         value
     */
    public static RequireRangeFromForTest ofLong(long longFrom) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RequireRangeFromForTest(1, longFrom, shortNumber, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RequireRangeFromForTest} with short test
     * value.
     *
     * @param shortFrom The short value to be tested
     * @return The new instance of {@link RequireRangeFromForTest} with short test
     *         value
     */
    public static RequireRangeFromForTest ofShort(short shortFrom) {
        byte byteNumber = 1;
        return new RequireRangeFromForTest(1, 1l, shortFrom, byteNumber, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RequireRangeFromForTest} with byte test
     * value.
     *
     * @param byteFrom The byte value to be tested
     * @return The new instance of {@link RequireRangeFromForTest} with byte test
     *         value
     */
    public static RequireRangeFromForTest ofByte(byte byteFrom) {
        short shortNumber = 1;
        return new RequireRangeFromForTest(1, 1l, shortNumber, byteFrom, 0.1f, 0.1d);
    }

    /**
     * Returns the new instance of {@link RequireRangeFromForTest} with float test
     * value.
     *
     * @param floatFrom The float value to be tested
     * @return The new instance of {@link RequireRangeFromForTest} with float test
     *         value
     */
    public static RequireRangeFromForTest ofFloat(float floatFrom) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RequireRangeFromForTest(1, 1l, shortNumber, byteNumber, floatFrom, 0.1d);
    }

    /**
     * Returns the new instance of {@link RequireRangeFromForTest} with double test
     * value.
     *
     * @param doubleFrom The double value to be tested
     * @return The new instance of {@link RequireRangeFromForTest} with double test
     *         value
     */
    public static RequireRangeFromForTest ofDouble(double doubleFrom) {
        short shortNumber = 1;
        byte byteNumber = 1;
        return new RequireRangeFromForTest(1, 1l, shortNumber, byteNumber, 0.1f, doubleFrom);
    }
}
