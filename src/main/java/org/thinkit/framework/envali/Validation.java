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

package org.thinkit.framework.envali;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.thinkit.api.catalog.BiCatalog;
import org.thinkit.common.base.precondition.Preconditions;
import org.thinkit.framework.envali.catalog.ParameterConfig;
import org.thinkit.framework.envali.catalog.ValidationPattern;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.result.BusinessError;
import org.thinkit.framework.envali.strategy.ValidationStrategyContext;
import org.thinkit.framework.envali.strategy.ValidationStrategyFactory;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * The object that represents the validation process.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
final class Validation {

    /**
     * The parameter configuration
     */
    private ParameterConfig parameterConfig;

    /**
     * The entity for validation
     */
    private ValidatableEntity validatableEntity;

    /**
     * The field for validation
     */
    private Field field;

    /**
     * The annotation type for validation
     */
    private Class<? extends Annotation> annotationType;

    /**
     * Default constructor
     */
    private Validation() {
    }

    /**
     * Returns the inner builder class for {@link Validation} .
     *
     * @return The inner builder class for {@link Validation}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The inner builder class for {@link Validation} .
     *
     * @since 1.0.2
     */
    public static class Builder {

        /**
         * Default constructor
         */
        private Builder() {
        }

        /**
         * The parameter configuration
         */
        private ParameterConfig parameterConfig = ParameterConfig.ANNOTATION;

        /**
         * The entity for validation
         */
        private ValidatableEntity validatableEntity;

        /**
         * The field for validation
         */
        private Field field;

        /**
         * The annotation type for validation
         */
        private Class<? extends Annotation> annotationType;

        public Builder contentConfig() {
            this.parameterConfig = ParameterConfig.CONTENT;
            return this;
        }

        public Builder validatableEntity(@NonNull ValidatableEntity validatableEntity) {
            this.validatableEntity = validatableEntity;
            return this;
        }

        public Builder field(@NonNull Field field) {
            this.field = field;
            return this;
        }

        public Builder annotationType(@NonNull Class<? extends Annotation> annotationType) {
            this.annotationType = annotationType;
            return this;
        }

        public Validation build() {
            Preconditions.requireNonNull(this.parameterConfig, new IllegalStateException());
            Preconditions.requireNonNull(this.validatableEntity, new IllegalStateException());
            Preconditions.requireNonNull(this.field, new IllegalStateException());
            Preconditions.requireNonNull(this.annotationType, new IllegalStateException());

            final Validation Validation = new Validation();
            Validation.parameterConfig = this.parameterConfig;
            Validation.validatableEntity = this.validatableEntity;
            Validation.field = this.field;
            Validation.annotationType = this.annotationType;

            return Validation;
        }
    }

    /**
     * Execute a validation strategy based on the data type of the annotation.
     * <p>
     * Returns {@code null} if annotation is not supported by Envali framework.
     *
     * @return The business error detected in the validate process
     */
    public BusinessError validate() {

        final ValidationPattern validationPattern = BiCatalog.getEnumByTag(ValidationPattern.class,
                this.annotationType);

        if (validationPattern == null) {
            return null;
        }

        return ValidationStrategyContext.of(ValidationStrategyFactory.of(this.validatableEntity, this.field)
                .createValidationStrategy(validationPattern)).validate();
    }
}
