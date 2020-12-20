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

package org.thinkit.framework.envali.strategy;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.thinkit.common.base.precondition.Preconditions;
import org.thinkit.common.base.precondition.exception.PreconditionFailedException;
import org.thinkit.common.regex.Epitaph;
import org.thinkit.common.regex.catalog.RegexOption;
import org.thinkit.framework.envali.annotation.RequireMatch;
import org.thinkit.framework.envali.catalog.EnvaliContentAttribute;
import org.thinkit.framework.envali.catalog.RegexMethod;
import org.thinkit.framework.envali.catalog.RegexModifier;
import org.thinkit.framework.envali.catalog.RegexPreset;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.result.BusinessError;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A strategy to perform validation process for {@link RequireMatch} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class RequireMatchStrategy extends ValidationStrategy<RequireMatch> {

    /**
     * Constructor
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private RequireMatchStrategy(@NonNull ErrorContext<RequireMatch> errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        super(errorContext, entity, field);
    }

    /**
     * Returns the new instance of {@link RequireMatchStrategy} class.
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     * @return The new instance of {@link RequireMatchStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static ValidationStrategy<RequireMatch> of(@NonNull ErrorContext<RequireMatch> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        return new RequireMatchStrategy(errorContext, entity, field);
    }

    @Override
    public BusinessError validate() {

        final ErrorContext<RequireMatch> errorContext = super.getErrorContext();
        final RequireMatch annotation = errorContext.getAnnotation();

        return switch (annotation.errorType()) {
            case RECOVERABLE -> this.validate(annotation) ? BusinessError.none()
                    : BusinessError.recoverable(annotation.message());

            case UNRECOVERABLE -> this.validate(annotation) ? BusinessError.none()
                    : BusinessError.unrecoverable(annotation.message());

            case RUNTIME -> {
                if (this.validate(annotation)) {
                    yield BusinessError.none();
                }

                throw new PreconditionFailedException();
            }
        };
    }

    /**
     * Performs regular expression validation based on the value set in the
     * {@link RequireMatch} annotation.
     *
     * @param annotation The {@link RequireMatch} annotation
     * @return {@code true} if the value of the field matches the regular
     *         expression, otherwise {@code false}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private boolean validate(@NonNull RequireMatch annotation) {

        final Epitaph.Builder builder = Epitaph.builder().input(super.getFieldHelper().getString());
        final RegexPreset presetExpression = annotation.presetExpression();
        Preconditions.requireNonNull(presetExpression);

        if (presetExpression == RegexPreset.NONE) {
            builder.pattern(
                    super.isContentConfig() ? super.getContentHelper().get(EnvaliContentAttribute.REGEX_EXPRESSION)
                            : annotation.expression());
        } else {
            builder.pattern(presetExpression.getTag());
        }

        final List<RegexModifier> regexModifiers = Arrays.asList(annotation.modifiers());

        if (regexModifiers.isEmpty()) {
            return this.validate(builder.build(), annotation.method());
        }

        return this.validate(builder.option(EnumSet.copyOf(this.toRegexOption(regexModifiers))).build(),
                annotation.method());
    }

    /**
     * Performs regular expression validation by {@link Epitaph} based on the enum
     * element of the {@link RegexMethod} .
     *
     * @param epitaph     The regex engine
     * @param regexMethod The regex method based on {@link RegexMethod}
     * @return {@code true} if the value of the field matches the regular
     *         expression, otherwise {@code false}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private boolean validate(@NonNull Epitaph epitaph, @NonNull RegexMethod regexMethod) {
        return switch (regexMethod) {
            case FIND -> epitaph.find();
            case LOOKING_AT -> epitaph.lookingAt();
            case MATCHES -> epitaph.matches();
        };
    }

    /**
     * Converts to the {@link List} of {@link RegexOption} from the {@link List} of
     * {@link RegexModifier} .
     *
     * @param regexModifiers The {@link List} of {@link RegexModifier}
     * @return The {@link List} of {@link RegexOption}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private List<RegexOption> toRegexOption(@NonNull List<RegexModifier> regexModifiers) {
        return regexModifiers.stream().map(RegexModifier::getTag).collect(Collectors.toList());
    }
}
