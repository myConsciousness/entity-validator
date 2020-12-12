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
 * An annotation indicating that the string starts with a specific string.
 * <p>
 * Specify this annotation for fields that do not allow the string not to start
 * with a specific string as follows.
 *
 * <pre>
 * Specify the expected value for Content Framework:
 * <code>
 * &#64;ParameterMapping( content = "Parameter" )
 * public class ConcreteEntity implements ValidatableEntity {
 *
 *      &#64;RequireStartWith
 *      private String literal;
 *
 *      &#64;RequireStartWith( errorType = ErrorType.RECOVERABLE, message = "failed!" )
 *      private String recoverableLiteral;
 * }
 * </code>
 * </pre>
 *
 * <pre>
 * Specify the expected value for the annotation:
 * <code>
 * public class ConcreteEntity implements ValidatableEntity {
 *
 *      &#64;RequireStartWith( prefix = "something" )
 *      private String literal;
 *
 *      &#64;RequireStartWith( prefix = "something", errorType = ErrorType.UNRECOVERABLE, message = "failed!" )
 *      private String unrecoverableLiteral;
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
public @interface RequireStartWith {

    /**
     * Returns the error type based on {@link ErrorType} , and
     * {@link ErrorType#RUNTIME} is set as the default.
     *
     * @return The error type based on the {@link ErrorType}
     *
     * @since 1.0.1
     */
    public ErrorType errorType() default ErrorType.RUNTIME;

    /**
     * Returns the error type based on {@link ErrorType} , and empty ({@code ""}) is
     * set as the default.
     *
     * @return The message
     *
     * @since 1.0.1
     */
    public String message() default "";

    /**
     * Returns the expected prefix of String.
     *
     * @return The expected prefix
     *
     * @since 1.0.2
     */
    public String prefix() default "";
}
