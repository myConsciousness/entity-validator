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

import java.lang.annotation.Annotation;

import org.thinkit.common.base.precondition.exception.PreconditionFailedException;
import org.thinkit.framework.envali.result.BusinessError;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Context class to execute the validation strategy.
 * <p>
 * create an instance of the class using the {@link #of(ValidationStrategy)} and
 * validate it according to the annotations using the {@link #validate()}
 * method.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class ValidationStrategyContext {

    /**
     * Validation strategy for field
     */
    private ValidationStrategy<? extends Annotation> validationStrategy;

    /**
     * Default constructor
     */
    private ValidationStrategyContext() {
    }

    /**
     * Constructor
     *
     * @param validationStrategy The validation strategy for field
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ValidationStrategyContext(@NonNull ValidationStrategy<? extends Annotation> validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    /**
     * Returns the new instance of {@link ValidationStrategyContext} class.
     *
     * @param validationStrategy The validation strategy for field
     * @return The new instance of {@link ValidationStrategyContext} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static ValidationStrategyContext of(@NonNull ValidationStrategy<? extends Annotation> validationStrategy) {
        return new ValidationStrategyContext(validationStrategy);
    }

    /**
     * Execute a validation strategy.
     *
     * @return The business error detected in the validate process
     *
     * @exception PreconditionFailedException If the validation process detects an
     *                                        invalid value
     */
    public BusinessError validate() {
        return this.validationStrategy.validate();
    }
}
