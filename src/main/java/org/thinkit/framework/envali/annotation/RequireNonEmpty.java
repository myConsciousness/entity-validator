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
 * An annotation that indicates the field is not allowed to be set to
 * {@code null} or empty.
 * <p>
 * Specify this annotation {@link RequireNonNull} for fields that do not allow
 * {@code null} or empty as follows. This annotation can be specified for Array,
 * String, List, Map, and Set, otherwise UnsupportedOperationException will be
 * thrown at runtime.
 *
 * <pre>
 * <code>
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;RequireNonEmpty
 *      private String literal;
 *
 *      &#64;RequireNonEmpty( ErrorType = ErrorType.RECOVERABLE, message = "failed!" )
 *      private String[] literals;
 *
 *      &#64;RequireNonEmpty( ErrorType = ErrorType.RECOVERABLE, message = "failed!" )
 *      private List&lt;String&gt; literalList;
 *
 *      &#64;RequireNonEmpty( ErrorType = ErrorType.UNRECOVERABLE, message = "failed!" )
 *      private Map&lt;String, String&gt; literalMap;
 *
 *      &#64;RequireNonEmpty( ErrorType = ErrorType.UNRECOVERABLE, message = "failed!" )
 *      private Set&lt;String&gt; literalSet;
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
public @interface RequireNonEmpty {

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
