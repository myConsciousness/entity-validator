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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.thinkit.common.Preconditions;
import org.thinkit.common.catalog.Extension;
import org.thinkit.framework.content.ContentLoader;
import org.thinkit.framework.envali.annotation.ParameterMapping;
import org.thinkit.framework.envali.annotation.RequireEndWith;
import org.thinkit.framework.envali.annotation.RequireNegative;
import org.thinkit.framework.envali.annotation.RequireNonBlank;
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.catalog.EnvaliContentAttribute;
import org.thinkit.framework.envali.catalog.EnvaliContentCondition;
import org.thinkit.framework.envali.catalog.EnvaliContentRoot;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * {@link Envali} is a powerful interface that provides common and intuitive
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
 * import org.thinkit.framework.envali.ParameterMapping;
 * import org.thinkit.framework.envali.RequireNonNull;
 * import org.thinkit.framework.envali.RequirePositive;
 * import org.thinkit.framework.envali.RangeFromTo;
 * import org.thinkit.framework.envali.entity.ValidatableEntity;
 *
 * &#64;ParameterMapping(content = "EnvaliContent")
 * public class ConcreteEntity implements ValidatableEntity {
 *
 *      &#64;RequireNonNull
 *      private String literal;
 *
 *      &#64;RequirePositive
 *      private int positive;
 *
 *      &#64;RequireRangeFromTo
 *      private int number;
 * }
 * </code>
 * </pre>
 *
 * <pre>
 * Then just run the {@link Envali#validate} method:
 * <code>
 * Envali.validate(concreteEntityObject);
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 *
 * @see RequireNonNull
 * @see RequireNonBlank
 * @see RequireNonEmpty
 * @see RequirePositive
 * @see RequireNegative
 * @see RequireRangeTo
 * @see RequireRangeFromTo
 * @see RequireStartWith
 * @see RequireEndWith
 */
public interface Envali {

    /**
     * Analyzes each annotation set to an entity object for validation and verifies
     * the validity of the field's value.
     *
     * @param entity The entity object to be validated that implements the
     *               {@link ValidatableEntity} interface
     *
     * @exception NullPointerException          If {@code null} is passed as an
     *                                          argument
     * @exception InvalidValueDetectedException If the verification process detects
     *                                          an invalid value
     * @exception UnsupportedOperationException When an unexpected operation is
     *                                          detected during the reflection
     *                                          process
     */
    public static void validate(ValidatableEntity entity) {
        Preconditions.requireNonNull(entity);

        final Class<? extends ValidatableEntity> entityClass = entity.getClass();
        final ParameterMapping contentMapping = entityClass.getAnnotation(ParameterMapping.class);

        Arrays.asList(entityClass.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);

            Arrays.asList(field.getAnnotations()).forEach(annotation -> {
                try {
                    final Class<? extends Annotation> annotationType = annotation.annotationType();

                    if (annotationType.equals(RequireNonNull.class)) {
                        Preconditions.requireNonNull(field.get(entity));
                    } else if (annotationType.equals(RequireNonBlank.class)) {
                        Preconditions.requireNonBlank(String.valueOf(field.get(entity)),
                                new InvalidValueDetectedException());
                    } else if (annotationType.equals(RequirePositive.class)) {
                        Preconditions.requirePositive(Integer.parseInt(String.valueOf(field.get(entity))),
                                new InvalidValueDetectedException());
                    } else if (annotationType.equals(RequireNegative.class)) {
                        Preconditions.requireNegative(Integer.parseInt(String.valueOf(field.get(entity))),
                                new InvalidValueDetectedException());
                    } else if (annotationType.equals(RequireRangeTo.class)) {

                        final List<Map<String, String>> envaliContent = getEnvaliContent(entityClass, contentMapping,
                                field.getName());

                    } else if (annotationType.equals(RequireRangeFromTo.class)) {

                    } else if (annotationType.equals(RequireStartWith.class)) {

                    } else if (annotationType.equals(RequireEndWith.class)) {

                    } else if (annotationType.equals(RequireNonEmpty.class)) {

                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new UnsupportedOperationException(e);
                }
            });
        });
    }

    /**
     * Refer to the content file mapped to the entity object to be validated and get
     * each expected value in {@link List} format to be used at validation.
     *
     * @param entityClass Entity objects to be validated @param contentMapping
     *                    Content mapping object @return Envali's validation content
     *
     * @exception NullPointerException          If {@code null} is passed as an
     *                                          argument
     * @exception IllegalArgumentException      If {@code null} or {@code ""} string
     *                                          is passed as an argument
     * @exception UnsupportedOperationException If couldn't get Envali's content
     */
    private static List<Map<String, String>> getEnvaliContent(final Class<? extends ValidatableEntity> entityClass,
            final ParameterMapping contentMapping, final String variableName) {
        Preconditions.requireNonNull(entityClass);
        Preconditions.requireNonNull(contentMapping);
        Preconditions.requireNonEmpty(variableName, new IllegalArgumentException());

        final List<Map<String, String>> envaliContent = ContentLoader.load(
                entityClass.getClassLoader().getResourceAsStream(
                        EnvaliContentRoot.ROOT.getTag() + contentMapping.content() + Extension.json()),
                getContentAttributes(), getContentConditions(variableName));

        if (envaliContent.isEmpty()) {
            throw new UnsupportedOperationException();
        }

        return envaliContent;
    }

    /**
     * Returns a list containing Envali's content attributes based on the definition
     * information for {@link EnvaliContentAttribute} .
     *
     * @return A list containing Envali's content attributes
     */
    private static List<String> getContentAttributes() {
        return Arrays.asList(EnvaliContentAttribute.values()).stream().map(EnvaliContentAttribute::getTag)
                .collect(Collectors.toList());
    }

    /**
     * Returns a map containing Envali's content conditions based on the definition
     * information for {@link EnvaliContentCondition} .
     *
     * @return A map containing Envali's content conditions
     */
    private static Map<String, String> getContentConditions(final String variableName) {
        return Map.of(EnvaliContentCondition.VARIABLE_NAME.getTag(), variableName);
    }
}
