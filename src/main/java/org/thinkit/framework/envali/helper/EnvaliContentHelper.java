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

package org.thinkit.framework.envali.helper;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.thinkit.common.base.precondition.Preconditions;
import org.thinkit.common.catalog.Extension;
import org.thinkit.framework.content.ContentLoader;
import org.thinkit.framework.envali.annotation.ParameterMapping;
import org.thinkit.framework.envali.catalog.EnvaliContentAttribute;
import org.thinkit.framework.envali.catalog.EnvaliContentCondition;
import org.thinkit.framework.envali.catalog.EnvaliContentRoot;
import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.exception.ContentNotFoundException;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A helper class that provides access to Envali's content.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class EnvaliContentHelper {

    /**
     * An entity class for validation
     */
    private Class<? extends ValidatableEntity> entityClass;

    /**
     * The name of validatable field
     */
    private String fieldName;

    /**
     * Envali's content mapping
     */
    private ParameterMapping contentMapping;

    /**
     * Envali's content
     */
    private Map<String, String> envaliContent;

    /**
     * Default constructor
     */
    private EnvaliContentHelper() {
    }

    /**
     * Constructor
     *
     * @param entity The validatable entity
     * @param field  The field
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private EnvaliContentHelper(@NonNull ValidatableEntity entity, @NonNull Field field) {
        this.entityClass = entity.getClass();
        this.contentMapping = this.entityClass.getAnnotation(ParameterMapping.class);
        this.fieldName = field.getName();
    }

    /**
     * Returns the new instance of {@link EnvaliContentHelper} object.
     *
     * @param entity The validatable entity
     * @param field  The field
     * @return The new instance of {@link EnvaliContentHelper} object
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static EnvaliContentHelper of(@NonNull ValidatableEntity entity, @NonNull Field field) {
        return new EnvaliContentHelper(entity, field);
    }

    /**
     * Refer to the content file mapped to the entity object to be validated and get
     * each expected value in {@link Map} format to be used at validation.
     * <p>
     * Because the content is cached on the first call to
     * {@link #get(EnvaliContentAttribute)} method, there is no performance
     * degradation due to consecutive calls to
     * {@link #get(EnvaliContentAttribute)}ethod.
     *
     * @param attribute The {@link EnvaliContentAttribute} element to be retrieved
     *                  from the content
     * @return Envali's validation content
     *
     * @exception NullPointerException          If no {@link ParameterMapping}
     *                                          annotation is attached to the entity
     *                                          to be validated
     * @exception ContentNotFoundException      If the content file defined in
     *                                          {@link ParameterMapping} annotation
     *                                          was not found
     * @exception UnsupportedOperationException If couldn't get Envali's content
     */
    public String get(EnvaliContentAttribute attribute) {
        Preconditions.requireNonNull(attribute);
        Preconditions.requireNonNull(this.contentMapping);

        if (this.envaliContent == null) {
            this.cacheContent();
        }

        return this.envaliContent.get(attribute.getTag());
    }

    /**
     * Caches the content mapped by {@link ParameterMapping} annotation.
     */
    private void cacheContent() {

        final String contentResourcePath = new StringBuilder().append(EnvaliContentRoot.ROOT.getTag())
                .append(this.contentMapping.content()).append(Extension.json()).toString();
        final InputStream contentStream = this.entityClass.getClassLoader().getResourceAsStream(contentResourcePath);

        if (contentStream == null) {
            throw new ContentNotFoundException(String.format(
                    "The content file defined in ParameterMapping annotation was not found. Please check the path to the resource. Resource path to the defined content: %s",
                    contentResourcePath));
        }

        final List<Map<String, String>> envaliContent = ContentLoader.load(contentStream, this.getContentAttributes(),
                this.getContentConditions());

        if (envaliContent.isEmpty()) {
            throw new UnsupportedOperationException();
        }

        this.envaliContent = envaliContent.get(0);
    }

    /**
     * Returns a set containing Envali's content attributes based on the definition
     * information for {@link EnvaliContentAttribute} .
     *
     * @return A set containing Envali's content attributes
     */
    private Set<String> getContentAttributes() {
        return Arrays.asList(EnvaliContentAttribute.values()).stream().map(EnvaliContentAttribute::getTag)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list containing Envali's content conditions based on the definition
     * information for {@link EnvaliContentCondition} .
     *
     * @return A list containing Envali's content conditions
     */
    private List<Map<String, String>> getContentConditions() {
        return List.of(Map.of(EnvaliContentCondition.VARIABLE_NAME.getTag(), this.fieldName));
    }
}
