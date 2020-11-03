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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.thinkit.common.Preconditions;
import org.thinkit.common.catalog.Extension;
import org.thinkit.framework.content.ContentLoader;
import org.thinkit.framework.envali.annotation.ParameterMapping;
import org.thinkit.framework.envali.catalog.EnvaliContentAttribute;
import org.thinkit.framework.envali.catalog.EnvaliContentCondition;
import org.thinkit.framework.envali.catalog.EnvaliContentRoot;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * An abstract class that abstracts the strategy of validation.
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
abstract class ValidationStrategy {

    /**
     * The entity for validation
     */
    @Getter(AccessLevel.PROTECTED)
    private ValidatableEntity entity;

    /**
     * The field for validation
     */
    @Getter(AccessLevel.PROTECTED)
    private Field field;

    /**
     * An entity class for validation
     */
    private Class<? extends ValidatableEntity> entityClass;

    /**
     * Envali's content mapping
     */
    private ParameterMapping contentMapping;

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    private ValidationStrategy() {
    }

    /**
     * Constructor
     *
     * @param entity The entity for validation
     * @param field  The field for validation
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected ValidationStrategy(@NonNull ValidatableEntity entity, @NonNull Field field) {
        this.entity = entity;
        this.field = field;
        this.entityClass = entity.getClass();
        this.contentMapping = this.entityClass.getAnnotation(ParameterMapping.class);
    }

    /**
     * Execute the validation process according to the strategy.
     */
    public abstract void validate();

    /**
     * Returns a string value from a field object.
     *
     * @return A string field value
     *
     * @throws IllegalArgumentException If a different object is passed during the
     *                                  reflection process
     * @throws IllegalAccessException   If an attempt is made to access an area that
     *                                  does not meet the permissions during the
     *                                  reflection process
     */
    protected String getString() throws IllegalArgumentException, IllegalAccessException {
        return String.valueOf(this.field.get(this.entity));
    }

    /**
     * Returns an int value from a field object.
     *
     * @return An int field value
     *
     * @throws IllegalArgumentException If a different object is passed during the
     *                                  reflection process
     * @throws IllegalAccessException   If an attempt is made to access an area that
     *                                  does not meet the permissions during the
     *                                  reflection process
     */
    protected int getInt() throws NumberFormatException, IllegalArgumentException, IllegalAccessException {
        return Integer.parseInt(this.getString());
    }

    /**
     * Refer to the content file mapped to the entity object to be validated and get
     * each expected value in {@link List} format to be used at validation.
     *
     * @return Envali's validation content
     *
     * @exception NullPointerException          If no {@link ParameterMapping}
     *                                          annotation is attached to the entity
     *                                          to be validated
     * @exception UnsupportedOperationException If couldn't get Envali's content
     */
    protected List<Map<String, String>> getEnvaliContent() {
        Preconditions.requireNonNull(this.contentMapping);

        final List<Map<String, String>> envaliContent = ContentLoader.load(
                this.entityClass.getClassLoader().getResourceAsStream(
                        EnvaliContentRoot.ROOT.getTag() + this.contentMapping.content() + Extension.json()),
                this.getContentAttributes(), this.getContentConditions());

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
    private List<String> getContentAttributes() {
        return Arrays.asList(EnvaliContentAttribute.values()).stream().map(EnvaliContentAttribute::getTag)
                .collect(Collectors.toList());
    }

    /**
     * Returns a map containing Envali's content conditions based on the definition
     * information for {@link EnvaliContentCondition} .
     *
     * @return A map containing Envali's content conditions
     */
    private Map<String, String> getContentConditions() {
        return Map.of(EnvaliContentCondition.VARIABLE_NAME.getTag(), this.field.getName());
    }
}
