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
 * An annotation indicating that the value is not under the lower limit.
 * <p>
 * Specify this annotation for fields that do not allow numbers to exceed the
 * limit as follows. If this annotation is specified for an object of type other
 * than int, long, short, byte, float, double, Integer, Long, Short, Byte, Float
 * or Double, UnsupportedOperationException will always be thrown at runtime.
 *
 * <pre>
 * Specify the expected value for Content Framework:
 * <code>
 * &#64;ParameterMapping( content = "Parameter" )
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;RequireRangeFrom
 *      private int intFrom;
 *
 *      &#64;RequireRangeFrom( ErrorType.RECOVERABLE, message = "failed!" )
 *      private long longFrom;
 *
 *      &#64;RequireRangeFrom( ErrorType.UNRECOVERABLE, message = "failed!" )
 *      private double doubleFrom;
 * }
 * </code>
 * </pre>
 *
 * <pre>
 * Specify the expected value for the annotation:
 * <code>
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;RequireRangeFrom( intFrom = 10 )
 *      private int intFrom;
 *
 *      &#64;RequireRangeFrom( longFrom = 10l, ErrorType.RECOVERABLE, message = "failed!" )
 *      private long longFrom;
 *
 *      &#64;RequireRangeFrom( doubleFrom = 10.0d, ErrorType.UNRECOVERABLE, message = "failed!" )
 *      private double doubleFrom;
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
public @interface RequireRangeFrom {

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
     * @return The lower limit of int type
     *
     * @since 1.0.2
     */
    public int intFrom() default Integer.MIN_VALUE;

    /**
     * Returns the lower limit of long type. The default value is
     * {@link Long#MIN_VALUE} .
     *
     * @return The lower limit of long type
     *
     * @since 1.0.2
     */
    public long longFrom() default Long.MIN_VALUE;

    /**
     * Returns the lower limit of float type. The default value is
     * {@link Float#MIN_VALUE} .
     *
     * @return The lower limit of float type
     *
     * @since 1.0.2
     */
    public float floatFrom() default Float.MIN_VALUE;

    /**
     * Returns the lower limit of double type. The default value is
     * {@link Double#MIN_VALUE} .
     *
     * @return The lower limit of double type
     *
     * @since 1.0.2
     */
    public double doubleFrom() default Double.MIN_VALUE;

    /**
     * Returns the lower limit of short type. The default value is
     * {@link Short#MIN_VALUE} .
     *
     * @return The lower limit of short type
     *
     * @since 1.0.2
     */
    public short shortFrom() default Short.MIN_VALUE;

    /**
     * Returns the lower limit of byte type. The default value is
     * {@link Byte#MIN_VALUE} .
     *
     * @return The lower limit of byte type
     *
     * @since 1.0.2
     */
    public byte byteFrom() default Byte.MIN_VALUE;
}
