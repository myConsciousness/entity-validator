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
import org.thinkit.framework.envali.catalog.RegexMethod;
import org.thinkit.framework.envali.catalog.RegexModifier;
import org.thinkit.framework.envali.catalog.RegexPreset;

/**
 * An annotation that indicates the value of the field matches the preset of
 * regular expression specified in the {@link #expression()} .
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireMatchPreset {

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
     * Returns the preset of regular expression based on {@link RegexPreset} .
     *
     * @return The preset of regular expression based on {@link RegexPreset}
     */
    public RegexPreset expression();

    /**
     * Returns the array of regex modifiers based on {@link RegexModifier} , and an
     * enmpty array is set as the default.
     *
     * @return The array of regex modifiers based on {@link RegexModifier}
     */
    public RegexModifier[] modifiers() default {};

    /**
     * Returns the method used to apply regular expression based on
     * {@link RegexMethod} , and {@link RegexMethod#MATCHES} is set as the default.
     *
     * @return The method used to apply regular expression based on
     *         {@link RegexMethod}
     */
    public RegexMethod method() default RegexMethod.MATCHES;
}
