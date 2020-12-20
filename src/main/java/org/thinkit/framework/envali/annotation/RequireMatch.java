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
 * An annotation that indicates the value of the field matches the regular
 * expression specified in the {@link #expression()} or
 * {@link #presetExpression()} .
 * <p>
 * Several regular expression presets have already been defined as
 * {@link RegexPreset} for this annotation. If you want to use a regular
 * expression preset, specify the {@link RegexPreset} element for
 * {@link #presetExpression()} . The regular expression can also be specified as
 * an arbitrary string instead of {@link RegexPreset} . To specify an arbitrary
 * regular expression, specify a regular expression literal for
 * {@link #expression()} . If a regular expression preset and a regular
 * expression literal are specified in the same annotation at the same time,
 * like {@code &#64;RequireMatch(expression =
 * "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", presetExpression =
 * RegexPreset.EMAIL_ADDRESS)} , {@link #presetExpression()} and
 * {@link RegexPreset} will be used preferentially in the validation process.
 * <p>
 * This annotation validation process provides several methods for applying
 * regular expressions as {@link RegexMethod} . Each of them provides
 * {@link RegexMethod#FIND} , {@link RegexMethod#LOOKING_AT} and
 * {@link RegexMethod#MATCHES} methods, and the specification is similar to that
 * of Matcher class which is widely known in Java. By specifying
 * {@link RegexMethod} element in the annotation, like
 * {@code &#64;RequireMatch(method = RegexMethod.MATCHES)} , the regular
 * expression will be applied in the specified method during the validation
 * process.
 * <p>
 * You can specify any modifier flag by specifying {@link RegexModifier} to the
 * annotation like <code>&#64;RequireMatch(modifiers =
 * {RegexModifier.UNIX_LINES, RegexModifier.DOTALL})</code>.
 * <p>
 * If this annotation is specified for an object of type other than String,
 * UnsupportedOperationException will always be thrown at runtime.
 *
 * <pre>
 * Specify the expected value for Content Framework:
 * <code>
 * &#64;ParameterMapping( content = "Parameter" )
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;RequireMatch
 *      private String literal;
 *
 *      &#64;RequireMatch( method = RegexMethod.MATCHES, errorType = ErrorType.UNRECOVERABLE, message = "failed!" )
 *      private String unrecoverableLiteral;
 * }
 * </code>
 * </pre>
 *
 * <pre>
 * Specify the expected value for the annotation:
 * <code>
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;RequireMatch( presetExpression = RegexPreset.EMAIL_ADDRESS )
 *      private String literalWithRegexPreset;
 *
 *      &#64;RequireMatch( expression = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*" )
 *      private String literalWithSpesifiedExpression;
 *
 *      &#64;RequireMatch( presetExpression = RegexPreset.EMAIL_ADDRESS, method = RegexMethod.MATCHES )
 *      private String literalWithMatchesMethod;
 *
 *      &#64;RequireMatch( presetExpression = RegexPreset.EMAIL_ADDRESS, method = RegexMethod.LOOKING_AT, errorType = ErrorType.RECOVERABLE, message = "failed!" )
 *      private String recoverableLiteral;
 * }
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireMatch {

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
     * Returns the regular expression of {@link String} data type, and empty
     * ({@code ""}) is set as the default.
     *
     * @return The regular expression
     */
    public String expression() default "";

    /**
     * Returns the preset of regular expression based on {@link RegexPreset} , and
     * {@link RegexPreset#NONE} is set as the default.
     *
     * @return The preset of regular expression based on {@link RegexPreset}
     */
    public RegexPreset presetExpression() default RegexPreset.NONE;

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
