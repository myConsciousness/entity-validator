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
 * An annotation that indicates the field is not allowed to be set to negative
 * numbers.
 * <p>
 * Specify this annotation for fields that do not allow negative numbers as
 * follows. If this annotation is specified for an object of type other than int
 * or Integer, UnsupportedOperationException will always be thrown at runtime.
 *
 * <pre>
 * <code>
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;RequirePositive
 *      private int number;
 *
 *      &#64;RequirePositive(errorType = ErrorType.RECOVERABLE, message = "failed!" )
 *      private int recoverableNumber;
 *
 *      &#64;RequirePositive(errorType = ErrorType.UNRECOVERABLE, message = "failed!" )
 *      private int unrecoverableNumber;
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
public @interface RequirePositive {

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
}
