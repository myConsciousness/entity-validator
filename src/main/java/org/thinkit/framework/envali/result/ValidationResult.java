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

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.NonNull;

/**
 * - 
 * 
 * @author Kato Shinya
 * @since 1.0.1
 */
public final class ValidationResult {

    private final Map<Class<? extends ValidatableEntity>, List<BusinessError>> validationResult;

  　/**
   　* Default constructor
   　*/
    private ValidationResult() {
        this.validationResult = new HashMap<>(0);
    }

    public static ValidationResult newInstance() {
        return new ValidationResult();
    }

    public void put(@NonNull Class<? extends ValidatableEntity> validatableEntity, @NonNull List<BusinessError> businessErrors) {
        this.validationResult.put(validatableEntity, businessErrors);
    }

    public List<BusinessError> get(@NonNull Class<? extends ValidatableEntity> validatableEntity) {
        return this.validationResult.get(validatableEntity);
    }

    public boolean hasError() {
        return this.validationResult.isEmpty();
    }

    public boolean hasError(@NonNull Class<? extends ValidatableEntity> validatableEntity) {
        return this.validationResult.containsKey(validatableEntity);
    }
}
