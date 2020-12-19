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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * The helper class that provides access to the field values defined in
 * {@link ValidatableEntity} .
 *
 * @author Kato Shinya
 * @since 1.0.0
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
    @Getter
    private Class<?> type;

    /**
     * The list of parameterized validatable entity
     */
    @Getter
    private ParameterizedType parameterizedType;

    /**
     * The list of parameterized validatable entity
     */
    @Getter
    private List<Class<?>> parameterizedValidatableEntities = new ArrayList<>(0);

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
        this.type = field.getType();

        final Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType) {
            this.parameterizedType = (ParameterizedType) genericType;

            Arrays.asList(this.parameterizedType.getActualTypeArguments()).forEach(actualTypeArgument -> {
                try {
                    this.parameterizedValidatableEntities.add(Class.forName(actualTypeArgument.getTypeName()));
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            });
        }
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
     *                                          during the reflection process,
     *                                          otherwise if an attempt is made to
     *                                          access an area that does not meet
     *                                          the permissions during the
     *                                          reflection process
     */
    public Object get() {
        try {
            return this.field.get(this.validatableEntity);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * Returns the name of validatable entity.
     *
     * @return The field type
     */
    public String getEntityName() {
        return this.validatableEntity.getClass().getName();
    }

    /**
     * Returns the simple name of validatable entity.
     *
     * @return The field type
     */
    public String getEntitySimpleName() {
        return this.validatableEntity.getClass().getSimpleName();
    }

    /**
     * Returns the generic type.
     *
     * @return The generic type
     */
    public Type getGenericType() {
        return this.field.getGenericType();
    }

    /**
     * Returns the field name.
     *
     * @return The field name
     */
    public String getName() {
        return this.field.getName();
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
     * Returns an array from a field object.
     *
     * @return A field object of array
     */
    public Object[] getArray() {
        return (Object[]) this.get();
    }

    /**
     * Returns a list from a field object.
     *
     * @return A field object of list
     */
    public List<?> getList() {
        return (List<?>) this.get();
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
     * Tests if the data type of the field is String.
     *
     * @return {@code true} if the data type of field is String, otherwise
     *         {@code false}
     */
    public boolean isString() {
        return this.type.equals(String.class);
    }

    /**
     * Tests if the data type of the field is Integer.
     *
     * @return {@code true} if the data type of field is Integer, otherwise
     *         {@code false}
     */
    public boolean isInteger() {
        return this.type.equals(Integer.class) || this.type.equals(int.class);
    }

    /**
     * Tests if the data type of the field is Long.
     *
     * @return {@code true} if the data type of field is Long, otherwise
     *         {@code false}
     */
    public boolean isLong() {
        return this.type.equals(Long.class) || this.type.equals(long.class);
    }

    /**
     * Tests if the data type of the field is Short.
     *
     * @return {@code true} if the data type of field is Short, otherwise
     *         {@code false}
     */
    public boolean isShort() {
        return this.type.equals(Short.class) || this.type.equals(short.class);
    }

    /**
     * Tests if the data type of the field is Byte.
     *
     * @return {@code true} if the data type of field is Byte, otherwise
     *         {@code false}
     */
    public boolean isByte() {
        return this.type.equals(Byte.class) || this.type.equals(byte.class);
    }

    /**
     * Tests if the data type of the field is Float.
     *
     * @return {@code true} if the data type of field is Float, otherwise
     *         {@code false}
     */
    public boolean isFloat() {
        return this.type.equals(Float.class) || this.type.equals(float.class);
    }

    /**
     * Tests if the data type of the field is Array.
     *
     * @return {@code true} if the data type of field is Array, otherwise
     *         {@code false}
     */
    public boolean isArray() {
        return this.type.isArray();
    }

    /**
     * Tests if the data type of the field is List.
     *
     * @return {@code true} if the data type of field is List, otherwise
     *         {@code false}
     */
    public boolean isList() {
        return this.type.equals(List.class);
    }

    /**
     * Tests if the data type of the field is Map.
     *
     * @return {@code true} if the data type of field is Map, otherwise
     *         {@code false}
     */
    public boolean isMap() {
        return this.type.equals(Map.class);
    }

    /**
     * Tests if the data type of the field is Set.
     *
     * @return {@code true} if the data type of field is Set, otherwise
     *         {@code false}
     */
    public boolean isSet() {
        return this.type.equals(Set.class);
    }

    /**
     * Tests if the data type of the field is collection.
     *
     * @return {@code true} if the data type of field is List, Map or Set, otherwise
     *         {@code false}
     */
    public boolean isCollection() {
        return this.isList() || this.isMap() || this.isSet();
    }

    /**
     * Tests if the data type of the field is Double.
     *
     * @return {@code true} if the data type of field is Double, otherwise
     *         {@code false}
     */
    public boolean isDouble() {
        return this.type.equals(Double.class) || this.type.equals(double.class);
    }

    /**
     * Tests if the data type of the field is {@link ValidatableEntity} .
     *
     * @return {@code true} if the data type of field is {@link ValidatableEntity} ,
     *         otherwise {@code false}
     */
    public boolean isValidatableEntity() {
        return this.isValidatableEntity(this.get().getClass().getInterfaces());
    }

    /**
     * Tests if the field has {@link ValidatableEntity} as parameterized data type.
     *
     * @return {@code true} if the field has {@link ValidatableEntity} as
     *         parameterized data type, otherwise {@code false}
     */
    public boolean hasParameterizedValidatableEntity() {

        if (this.parameterizedValidatableEntities.isEmpty()) {
            return false;
        }

        if (this.isList() || this.isSet()) {
            return this.isValidatableEntity(this.parameterizedValidatableEntities.get(0).getInterfaces());
        } else if (this.isMap()) {
            return this.isValidatableEntity(this.parameterizedValidatableEntities.get(1).getInterfaces());
        }

        return false;
    }

    /**
     * Tests if a field object annotated with NestedEntity implements the
     * {@link ValidatableEntity} interface.
     *
     * @param interfaces The array of interfaces to be validated
     * @return {@code true} if a field object implemets the the ValidatableEntity} ,
     *         or {@code false}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private boolean isValidatableEntity(@NonNull Class<?>[] interfaces) {

        for (Class<?> _interface : interfaces) {
            if (_interface.equals(ValidatableEntity.class)) {
                return true;
            }
        }

        return false;
    }
}
