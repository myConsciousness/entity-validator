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

import org.thinkit.framework.envali.Envali;
import org.thinkit.framework.envali.catalog.ErrorType;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import lombok.NonNull;

/**
 * A data class that manages the results of validation by {@link Envali} . Manages {@link ErrorType#RECOVERABLE} and {@link ErrorType#UNRECOVERABLE}
 * business errors as a result of validation. Detected business errors are managed on a per-entity basis implementing the {@link ValidatableEntity} interface.
 * <p>
 * It provides the following features to operate comfortably with detected business errors.
 *
 * <pre>
 * Test for the presence of business errors on any entity that implements {@link ValidatableEntity} .
 * <code>
 * final ValidationResult validationResult = Envali.validate(concreteValidatableEntity);
 * // Returns {@code true} if there is a error on any {@link ValidatableEntity} , otherwise {@code false}
 * validationResult.hasError();
 * </code>
 * </pre> 
 *
 * <pre>
 * Test for the presence of business errors on a specified entity that implements {@link ValidatableEntity} .
 * <code>
 * final ValidationResult validationResult = Envali.validate(concreteValidatableEntity); 
 * // Returns {@code true} if there is a error on ConcreteValidatableEntity, otherwise {@code false}
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
 *     // Returns List<BusinessError>
 *     validationResult.getError(ConcreteValidatableEntity.class);
 * }
 * </code>
 * </pre> 
 *
 * @author Kato Shinya
 * @since 1.0.1
 */
public final class ValidationResult {

    /**
     * The validation result that manages business errors per-entity
     */
    private final Map<Class<? extends ValidatableEntity>, List<BusinessError>> validationResult;

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
     * Returns the new instance of {@link ValidationResult} based on {@code validationResult} passed as an argument.
     *
     * @return The new instance of {@link ValidationResult}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static ValidationResult of(@NonNull Map<Class<? extends ValidatableEntity>, List<BusinessError>> validationResult) {
        return new ValidationResult(validationResult);
    }

    public List<BusinessError> getError(@NonNull Class<? extends ValidatableEntity> validatableEntity) {
        return List.copyOf(this.validationResult.get(validatableEntity));
    }

    public boolean hasError() {
        return !this.validationResult.isEmpty();
    }
    
    public boolean hasError(@NonNull Class<? extends ValidatableEntity> validatableEntity) {
        return this.validationResult.containsKey(validatableEntity);
    }
}
