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
import java.util.Collection;

import org.thinkit.framework.envali.Envali;
import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.framework.envali.context.ErrorContext;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.helper.EnvaliFieldHelper;
import org.thinkit.framework.envali.result.BusinessError;
import org.thinkit.framework.envali.result.ValidationResult;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A strategy to perform validation process for {@link NestedEntity} annotation.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
final class NestedEntityStrategy extends ValidationStrategy<NestedEntity> {

    /**
     * Constructor
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private NestedEntityStrategy(@NonNull ErrorContext<NestedEntity> errorContext, @NonNull ValidatableEntity entity,
            @NonNull Field field) {
        super(errorContext, entity, field);
    }

    /**
     * Returns the new instance of {@link NestedEntityStrategy} class.
     *
     * @param errorContext The error context
     * @param entity       The entity for validation
     * @param field        The field for validation
     * @return The new instance of {@link NestedEntityStrategy} class
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static ValidationStrategy<NestedEntity> of(@NonNull ErrorContext<NestedEntity> errorContext,
            @NonNull ValidatableEntity entity, @NonNull Field field) {
        return new NestedEntityStrategy(errorContext, entity, field);
    }

    @Override
    public BusinessError validate() {

        final EnvaliFieldHelper field = super.getFieldHelper();

        if (field.isCollection()) {
            for (ValidatableEntity validatableEntity : this.getValidatableEntityCollection(field)) {
                final ValidationResult validationResult = Envali.validate(validatableEntity);

                if (validationResult.hasError()) {
                    return BusinessError.nestedError(validationResult);
                }
            }

            return BusinessError.none();
        }

        final ValidationResult validationResult = Envali.validate(field.getValidatableEntity());
        return validationResult.hasError() ? BusinessError.nestedError(validationResult) : BusinessError.none();
    }

    /**
     * Returns the collection of validatable entity from the field.
     *
     * @param field The field to be validated
     * @return The collection of validatable entity from the field
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private Collection<ValidatableEntity> getValidatableEntityCollection(@NonNull EnvaliFieldHelper field) {
        return field.isList() ? field.getValidatableEntityList()
                : field.isSet() ? field.getValidatableEntitySet() : field.getValidatableEntityMap().values();
    }
}
