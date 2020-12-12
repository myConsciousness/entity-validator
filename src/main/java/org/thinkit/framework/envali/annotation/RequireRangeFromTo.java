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

package org.thinkit.framework.envali.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.thinkit.framework.envali.catalog.ErrorType;

/**
 * An annotation that indicate that the numbers are within the lower and upper
 * limits.
 * <p>
 * Specify this annotation for fields that do not allow exceed the lower and
 * upper limits,as follows.
 *
 * <pre>
 * <code>
 * &#64;ParameterMapping( content = "Parameter" )
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;RequireRangeFromTo
 *      private int number;
 * }
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRangeFromTo {

    /**
     * Returns the error type based on {@link ErrorType} , and
     * {@link ErrorType#RUNTIME} is set as the default.
     *
     * @return The error type based on the {@link ErrorType}
     */
    public ErrorType errorType() default ErrorType.RUNTIME;

    /**
     * Returns the error type based on {@link ErrorType} , and empty ({@code ""}) is
     * set as the default.
     *
     * @return The message
     */
    public String message() default "";

    /**
     * Returns the lower limit of int type. The default value is
     * {@link Integer#MIN_VALUE} .
     *
     * @return The lower limit of int type.
     */
    public int intFrom() default Integer.MIN_VALUE;

    /**
     * Returns the lower limit of long type. The default value is
     * {@link Long#MIN_VALUE} .
     *
     * @return The lower limit of long type.
     */
    public long longFrom() default Long.MIN_VALUE;

    /**
     * Returns the lower limit of float type. The default value is
     * {@link Float#MIN_VALUE} .
     *
     * @return The lower limit of float type.
     */
    public float floatFrom() default Float.MIN_VALUE;

    /**
     * Returns the lower limit of double type. The default value is
     * {@link Double#MIN_VALUE} .
     *
     * @return The lower limit of double type.
     */
    public double doubleFrom() default Double.MIN_VALUE;

    /**
     * Returns the lower limit of short type. The default value is
     * {@link Short#MIN_VALUE} .
     *
     * @return The lower limit of short type.
     */
    public short shortFrom() default Short.MIN_VALUE;

    /**
     * Returns the lower limit of byte type. The default value is
     * {@link Byte#MIN_VALUE} .
     *
     * @return The lower limit of byte type.
     */
    public byte byteFrom() default Byte.MIN_VALUE;

    /**
     * Returns the upper limit of int type. The default value is
     * {@link Integer#MAX_VALUE} .
     *
     * @return The upper limit of int type.
     */
    public int intTo() default Integer.MAX_VALUE;

    /**
     * Returns the upper limit of long type. The default value is
     * {@link Long#MAX_VALUE} .
     *
     * @return The upper limit of long type.
     */
    public long longTo() default Long.MAX_VALUE;

    /**
     * Returns the upper limit of float type. The default value is
     * {@link Float#MAX_VALUE} .
     *
     * @return The upper limit of float type.
     */
    public float floatTo() default Float.MAX_VALUE;

    /**
     * Returns the upper limit of double type. The default value is
     * {@link Double#MAX_VALUE} .
     *
     * @return The upper limit of double type.
     */
    public double doubleTo() default Double.MAX_VALUE;

    /**
     * Returns the upper limit of short type. The default value is
     * {@link Short#MAX_VALUE} .
     *
     * @return The upper limit of short type.
     */
    public short shortTo() default Short.MAX_VALUE;

    /**
     * Returns the upper limit of byte type. The default value is
     * {@link Byte#MAX_VALUE} .
     *
     * @return The upper limit of byte type.
     */
    public byte byteTo() default Byte.MAX_VALUE;
}
