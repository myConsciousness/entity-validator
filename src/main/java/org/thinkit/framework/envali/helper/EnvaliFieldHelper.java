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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * The helper class that provides access to the field values defined in
 * {@link ValidatableEntity} .
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class EnvaliFieldHelper {

    /**
     * The validatable entity
     */
    private ValidatableEntity validatableEntity;

    /**
     * The field
     */
    private Field field;

    /**
     * Default Constructor
     */
    private EnvaliFieldHelper() {
    }

    /**
     * Constructor
     *
     * @param validatableEntity The validatable entity
     * @param field             The field
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private EnvaliFieldHelper(@NonNull ValidatableEntity validatableEntity, @NonNull Field field) {
        this.validatableEntity = validatableEntity;
        this.field = field;
    }

    /**
     * Returns the new instance of {@link EnvaliFieldHelper} object;
     *
     * @param validatableEntity The validatable entity
     * @param field             The field
     * @return The new instance of {@link EnvaliFieldHelper} object
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static EnvaliFieldHelper of(@NonNull ValidatableEntity validatableEntity, @NonNull Field field) {
        return new EnvaliFieldHelper(validatableEntity, field);
    }

    /**
     * Returns an object value from a field object.
     *
     * @return An object field value
     *
     * @exception UnsupportedOperationException If a different object is passed
     *                                          during the reflection process, or if
     *                                          an attempt is made to access an area
     *                                          that does not meet the permissions
     *                                          during the reflection process
     */
    public Object get() {
        try {
            return this.field.get(this.validatableEntity);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * Returns a string value from a field object.
     *
     * @return A string field value
     */
    public String getString() {
        return String.valueOf(this.get());
    }

    /**
     * Returns an int value from a field object.
     *
     * @return An int field value
     */
    public int getInt() {
        return Integer.parseInt(this.getString());
    }

    /**
     * Returns a long value from a field object.
     *
     * @return A long field value
     */
    public long getLong() {
        return Long.parseLong(this.getString());
    }

    /**
     * Returns a short value from a field object.
     *
     * @return A short field value
     */
    public short getShort() {
        return Short.parseShort(this.getString());
    }

    /**
     * Returns a byte value from a field object.
     *
     * @return A byte field value
     */
    public byte getByte() {
        return Byte.parseByte(this.getString());
    }

    /**
     * Returns a float value from a field object.
     *
     * @return A float field value
     */
    public float getFloat() {
        return Float.parseFloat(this.getString());
    }

    /**
     * Returns a double value from a field object.
     *
     * @return A double field value
     */
    public double getDouble() {
        return Double.parseDouble(this.getString());
    }

    /**
     * Returns a list from a field object.
     *
     * @return A field object of list
     */
    public List<?> getList() {
        return Arrays.asList(this.get());
    }

    /**
     * Returns a map from a field object.
     *
     * @return A field object of map
     */
    public Map<?, ?> getMap() {
        return (Map<?, ?>) this.get();
    }

    /**
     * Returns a set from a field object.
     *
     * @return A field object of set
     */
    public Set<?> getSet() {
        return (Set<?>) this.get();
    }

    /**
     * Returns a validatable entity from a field object.
     *
     * @return A field object of validatable entity
     */
    public ValidatableEntity getValidatableEntity() {
        return (ValidatableEntity) this.get();
    }
}
