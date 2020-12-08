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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.thinkit.common.base.precondition.Preconditions;
import org.thinkit.framework.envali.annotation.ParameterMapping;
import org.thinkit.framework.envali.annotation.RequireEndWith;
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.exception.InvalidValueDetectedException;
import org.thinkit.framework.envali.result.BusinessError;
import org.thinkit.framework.envali.result.ValidationResult;
import org.thinkit.framework.envali.strategy.AnnotationContext;

/**
 * {@link Envali} is a powerful validator that provides common and intuitive
 * validation process for entity fields.
 * <p>
 * It is very easy and intuitive to use, you just need to annotate the fields of
 * the entity to be validated with such as {@link RequireNonNull} and
 * {@link RequireNonEmpty} and other annotations as follows.
 * <p>
 * Once you have annotated the fields of the entity to be validated, then
 * implement the {@link ValidatableEntity} interface. This interface has no
 * processing, but this marker interface required when using the
 * {@link Envali#validate} method.
 * <p>
 * For {@link RequireRangeTo} , {@link RequireRangeFromTo} ,
 * {@link RequireStartWith}, {@link RequireEndWith} you need to create a content
 * file that defines your expectations and map them with the
 * {@link ParameterMapping} annotation.
 * <p>
 * The above description can be summarized as follows.
 *
 * <pre>
 * Define the entities to be validated:
 * <code>
 * import org.thinkit.framework.envali.catalog.ErrorType;
 * import org.thinkit.framework.envali.annotation.ParameterMapping;
 * import org.thinkit.framework.envali.annotation.RequireNonNull;
 * import org.thinkit.framework.envali.annotation.RequirePositive;
 * import org.thinkit.framework.envali.annotation.RangeFromTo;
 * import org.thinkit.framework.envali.annotation.NestedEntity;
 * import org.thinkit.framework.envali.entity.ValidatableEntity;
 * import org.thinkit.framework.envali.result.ValidationResult;
 * import org.thinkit.framework.envali.result.BusinessError;
 *
 * &#64;ParameterMapping(content = "EnvaliContent")
 * public class ConcreteEntity implements ValidatableEntity {
 *
 *      &#64;RequireNonNull
 *      private String literal;
 *
 *      &#64;RequirePositive( errorType = ErrorType.RECOVERABLE, message = "The number is negative!")
 *      private int positive;
 *
 *      &#64;RequireRangeFromTo( errorType = ErrorType.UNRECOVERABLE, message = "The number is out of range!")
 *      private int number;
 *
 *      &#64;NestedEntity
 *      private ConcreteNestedEntity concreteNestedEntity;
 * }
 * </code>
 * </pre>
 *
 * <pre>
 * Then just run the {@link Envali#validate} method:
 * <code>
 * ValidationResult validationResult = Envali.validate(concreteEntityObject);
 *
 * // Returns true if there is no error, otherwise false
 * if (validationResult.hasError()) {
 *
 *      // Returns the list of business errors associated with specified validatable entity
 *      // Returns the empty list if there is no error associated with specified validatable entity
 *      List<BusinessError> businessErrors = validationResult.getError(ConcreteEntity.class);
 *
 *      for (BusinessError businessError : businessErrors) {
 *           if (businessError.isRecoverable()) {
 *                // do something for recoverable error
 *                Syetem.out.println(businessError.getMessage());
 *           } else if (businessError.isUnrecoverable()) {
 *                // do something for unrecoverable error
 *                Syetem.out.println(businessError.getMessage());
 *           }
 *      }
 * }
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public final class Envali {

    /**
     * Default constructor
     */
    private Envali() {
    }

    /**
     * Analyzes each annotation set to an entity object for validation and verifies
     * the validity of the field's value.
     *
     * @param entity The entity object to be validated that implements the
     *               {@link ValidatableEntity} interface
     * @return The validation result includes business errors
     *
     * @exception NullPointerException          If {@code null} is passed as an
     *                                          argument
     * @exception InvalidValueDetectedException If the validation process detects an
     *                                          invalid value
     * @exception UnsupportedOperationException When an unexpected operation is
     *                                          detected during the reflection
     *                                          process
     */
    public static ValidationResult validate(final ValidatableEntity entity) {
        Preconditions.requireNonNull(entity);

        final List<BusinessError> businessErrors = new ArrayList<>();

        for (Field field : Arrays.asList(entity.getClass().getDeclaredFields())) {
            field.setAccessible(true);

            for (Annotation annotation : Arrays.asList(field.getAnnotations())) {
                final BusinessError businessError = AnnotationContext.of(entity, field, annotation.annotationType())
                        .validate();

                if (businessError != null && businessError.hasError()) {
                    businessErrors.add(businessError);
                }
            }
        }

        if (businessErrors.isEmpty()) {
            return ValidationResult.none();
        }

        return ValidationResult.of(Map.of(entity.getClass(), businessErrors));
    }
}
