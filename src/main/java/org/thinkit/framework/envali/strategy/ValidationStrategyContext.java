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

import org.thinkit.framework.envali.exception.InvalidValueDetectedException;

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
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
final class ValidationStrategyContext {

    /**
     * Validation strategy for field
     */
    private ValidationStrategy validationStrategy;

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
    private ValidationStrategyContext(@NonNull ValidationStrategy validationStrategy) {
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
    protected static ValidationStrategyContext of(@NonNull ValidationStrategy validationStrategy) {
        return new ValidationStrategyContext(validationStrategy);
    }

    /**
     * Execute a validation strategy.
     *
     * @exception InvalidValueDetectedException If the validation process detects an
     *                                          invalid value
     * @exception UnsupportedOperationException When an unexpected operation is
     *                                          detected during the reflection
     *                                          process
     */
    public void validate() {
        this.validationStrategy.validate();
    }
}
