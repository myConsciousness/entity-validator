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
     * The field data type
     */
    private Class<?> fieldDataType;

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
        this.fieldDataType = field.getType();
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

    /**
     * Checks if the data type of the field is String.
     *
     * @return {@code true} if the data type of field is String, or {@code false}
     */
    public boolean isString() {
        return this.fieldDataType.equals(String.class);
    }

    /**
     * Checks if the data type of the field is Integer.
     *
     * @return {@code true} if the data type of field is Integer, or {@code false}
     */
    public boolean isInteger() {
        return this.fieldDataType.equals(Integer.class) || this.fieldDataType.equals(int.class);
    }

    /**
     * Checks if the data type of the field is Short.
     *
     * @return {@code true} if the data type of field is Short, or {@code false}
     */
    public boolean isShort() {
        return this.fieldDataType.equals(Short.class) || this.fieldDataType.equals(short.class);
    }

    /**
     * Checks if the data type of the field is Byte.
     *
     * @return {@code true} if the data type of field is Byte, or {@code false}
     */
    public boolean isByte() {
        return this.fieldDataType.equals(Byte.class) || this.fieldDataType.equals(byte.class);
    }

    /**
     * Checks if the data type of the field is Float.
     *
     * @return {@code true} if the data type of field is Float, or {@code false}
     */
    public boolean isFloat() {
        return this.fieldDataType.equals(Float.class) || this.fieldDataType.equals(float.class);
    }

    /**
     * Checks if the data type of the field is Array.
     *
     * @return {@code true} if the data type of field is Array, or {@code false}
     */
    public boolean isArray() {
        return this.fieldDataType.isArray();
    }

    /**
     * Checks if the data type of the field is List.
     *
     * @return {@code true} if the data type of field is List, or {@code false}
     */
    public boolean isList() {
        return this.fieldDataType.equals(List.class);
    }

    /**
     * Checks if the data type of the field is Map.
     *
     * @return {@code true} if the data type of field is Map, or {@code false}
     */
    public boolean isMap() {
        return this.fieldDataType.equals(Map.class);
    }

    /**
     * Checks if the data type of the field is Set.
     *
     * @return {@code true} if the data type of field is Set, or {@code false}
     */
    public boolean isSet() {
        return this.fieldDataType.equals(Set.class);
    }

    /**
     * Checks if the data type of the field is Double.
     *
     * @return {@code true} if the data type of field is Double, or {@code false}
     */
    public boolean isDouble() {
        return this.fieldDataType.equals(Double.class) || this.fieldDataType.equals(double.class);
    }

    /**
     * Checks if the data type of the field is {@link ValidatableEntity} .
     *
     * @return {@code true} if the data type of field is {@link ValidatableEntity} ,
     *         or {@code false}
     */
    public boolean isValidatableEntity() {
        return this.fieldDataType.equals(ValidatableEntity.class);
    }
}
