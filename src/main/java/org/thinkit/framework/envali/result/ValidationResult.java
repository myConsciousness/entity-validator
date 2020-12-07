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

package org.thinkit.framework.envali.result;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.thinkit.framework.envali.Envali;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A data class that manages the results of validation by {@link Envali} .
 * Manages {@link ErrorType#RECOVERABLE} and {@link ErrorType#UNRECOVERABLE}
 * business errors as a result of validation. Detected business errors are
 * managed on a per-entity basis implementing the {@link ValidatableEntity}
 * interface.
 * <p>
 * It provides the following features to operate comfortably with detected
 * business errors.
 *
 * <pre>
 * Test for the presence of business errors on any entity that implements {@link ValidatableEntity} .
 * <code>
 * final ValidationResult validationResult = Envali.validate(concreteValidatableEntity);
 * // Returns true if there is a error on any {@link ValidatableEntity} , otherwise false
 * validationResult.hasError();
 * </code>
 * </pre>
 *
 * <pre>
 * Test for the presence of business errors on a specified entity that implements {@link ValidatableEntity} .
 * <code>
 * final ValidationResult validationResult = Envali.validate(concreteValidatableEntity);
 * // Returns true if there is a error on ConcreteValidatableEntity, otherwise false
 * validationResult.hasError(ConcreteValidatableEntity.class);
 * </code>
 * </pre>
 *
 * <pre>
 * Get a business error of a specified entity that implements {@link ValidatableEntity} .
 * <code>
 * final ValidationResult validationResult = Envali.validate(concreteValidatableEntity);
 *
 * if (validationResult.hasError(ConcreteValidationResult.class)) {
 *     // Returns List&lt;BusinessError&gt;
 *     validationResult.getError(ConcreteValidatableEntity.class);
 * }
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
@ToString
@EqualsAndHashCode
public final class ValidationResult implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -3775467610787292284L;

    /**
     * The validation result that manages business errors per-entity
     */
    private Map<Class<? extends ValidatableEntity>, List<BusinessError>> validationResult;

    /**
     * Default constructor
     */
    private ValidationResult() {
    }

    /**
     * Constructor
     */
    private ValidationResult(@NonNull Map<Class<? extends ValidatableEntity>, List<BusinessError>> validationResult) {
        this.validationResult = validationResult;
    }

    /**
     * Returns the new instance of empty {@link ValidationResult} .
     *
     * @return The new instance of empty {@link ValidationResult} .
     */
    public static ValidationResult none() {
        return new ValidationResult(Map.of());
    }

    /**
     * Returns the new instance of {@link ValidationResult} based on
     * {@code validationResult} passed as an argument.
     *
     * @param validationResult The validation result
     * @return The new instance of {@link ValidationResult}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static ValidationResult of(
            @NonNull Map<Class<? extends ValidatableEntity>, List<BusinessError>> validationResult) {
        return new ValidationResult(validationResult);
    }

    /**
     * Returns the error list associated with the {@code validatableEntity} passed
     * as an argument.
     * <p>
     * This method returns immutable empty List created by {@link List#of()} if
     * there is no error data associated with {@code validatableEntity} .
     * <p>
     * This method recursively searches the error data associated with the
     * {@code validatableEntity} object passed as argument.
     *
     * @param validatableEntity The validatable entity
     * @return The error list associated with the {@code validatableEntity} passed
     *         as an argument if there is an error, otherwise immutable empty List
     *         created by {@link List#of()} .
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public List<BusinessError> getError(@NonNull Class<? extends ValidatableEntity> validatableEntity) {

        if (this.validationResult.containsKey(validatableEntity)) {
            return List.copyOf(this.validationResult.get(validatableEntity));
        }

        for (Entry<Class<? extends ValidatableEntity>, List<BusinessError>> businessErrors : this.validationResult
                .entrySet()) {
            for (BusinessError businessError : businessErrors.getValue()) {
                if (businessError.hasNestedError()) {
                    return businessError.getNestedError().getError(validatableEntity);
                }
            }
        }

        return List.of();
    }

    /**
     * Tests if there is any error associated with any {@link ValidatableEntity} .
     *
     * @return {@code true} if there is any error associated with any
     *         {@link ValidatableEntity} , otherwise {@code false}
     */
    public boolean hasError() {

        if (!this.isEmpty()) {
            return true;
        }

        for (Entry<Class<? extends ValidatableEntity>, List<BusinessError>> businessErrors : this.validationResult
                .entrySet()) {
            for (BusinessError businessError : businessErrors.getValue()) {
                if (businessError.hasNestedError()) {
                    return businessError.getNestedError().hasError();
                }
            }
        }

        return false;
    }

    /**
     * Tests for the presence of a business error in the validation result.
     *
     * @return {@code true} if there is a business error, otherwise {@code false}
     */
    public boolean isEmpty() {
        return this.validationResult.isEmpty();
    }
}
