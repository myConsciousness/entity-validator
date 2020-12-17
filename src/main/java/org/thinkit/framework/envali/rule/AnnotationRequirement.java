/*
 * Copyright 2020 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy for the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, sfortware distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS for ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.framework.envali.rule;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import org.thinkit.framework.envali.entity.ValidatableEntity;
import org.thinkit.framework.envali.helper.EnvaliFieldHelper;

import lombok.NonNull;

/**
 * The rule that manages the requirement of Envali annotation.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
public enum AnnotationRequirement {

    /**
     * The requirement for RequireNonNull
     */
    REQUIRE_NON_NULL {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            // !No requirements
        }
    },

    /**
     * The requirement for RequireNonBlank
     */
    REQUIRE_NON_BLANK {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!field.isString()) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireNonBlank annotation supports String type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequirePositive
     */
    REQUIRE_POSITIVE {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!field.isInteger()) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequirePositive annotation supports Integer type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequireNegative
     */
    REQUIRE_NEGATIVE {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!field.isInteger()) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireNegative annotation supports Integer type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequireRangeFrom
     */
    REQUIRE_RANGE_FROM {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!(field.isInteger() || field.isLong() || field.isShort() || field.isByte() || field.isFloat()
                    || field.isDouble())) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireRangeFrom annotation supports Integer, Long, Short, Byte, Float, Double type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequireRangeTo
     */
    REQUIRE_RANGE_TO {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!(field.isInteger() || field.isLong() || field.isShort() || field.isByte() || field.isFloat()
                    || field.isDouble())) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireRangeTo annotation supports Integer, Long, Short, Byte, Float, Double type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequireRangeFromTo
     */
    REQUIRE_RANGE_FROM_TO {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!(field.isInteger() || field.isLong() || field.isShort() || field.isByte() || field.isFloat()
                    || field.isDouble())) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireRangeFromTo annotation supports Integer, Long, Short, Byte, Float, Double type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequireStartWith
     */
    REQUIRE_START_WITH {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!field.isString()) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireStartWith annotation supports String type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequireEndWith
     */
    REQUIRE_END_WITH {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!field.isString()) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireEndWith annotation supports String type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for RequireNonEmpty
     */
    REQUIRE_NON_EMPTY {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!(field.isString() || field.isArray() || field.isCollection())) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireNonEmpty annotation supports String, Array, List, Map, Set type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    },

    /**
     * The requirement for NestedEntity
     */
    NESTED_ENTITY {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (field.isCollection()) {
                try {
                    final ParameterizedType type = ((ParameterizedType) field.getGenericType());

                    if (!this.isValidatableEntity(Arrays
                            .asList(Class.forName(type.getActualTypeArguments()[0].getTypeName()).getInterfaces()))) {
                        throw new UnsupportedOperationException(String.format(
                                "The generic type specified for collection %s#%s does not implement the org.thinkit.framework.envali.entity.ValidatableEntity interface.",
                                type, field.getName()));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                if (!this.isValidatableEntity(Arrays.asList(field.get().getClass().getInterfaces()))) {
                    throw new UnsupportedOperationException(String.format(
                            "The %s#%s does not implement the org.thinkit.framework.envali.entity.ValidatableEntity interface.",
                            field.getEntityName(), field.getName()));
                }
            }
        }

        /**
         * Tests if a field object annotated with NestedEntity implements the
         * {@link ValidatableEntity} interface.
         *
         * @param interfaces The list of to be validated
         * @return {@code true} if a field object implemets the the ValidatableEntity} ,
         *         or {@code false}
         *
         * @exception NullPointerException If {@code null} is passed as an argument
         */
        private boolean isValidatableEntity(@NonNull List<Class<?>> interfaces) {

            for (Class<?> _interface : interfaces) {
                if (_interface.equals(ValidatableEntity.class)) {
                    return true;
                }
            }

            return false;
        }
    },

    /**
     * The requirement for RequireMatch
     */
    REQUIRE_MATCH {
        @Override
        public void requireSupportedDataType(@NonNull EnvaliFieldHelper field) {
            if (!field.isString()) {
                throw new UnsupportedOperationException(String.format(
                        "The org.thinkit.framework.envali.annotation.RequireMatch annotation supports String type, but was specified for the variable %s#%s of type %s.",
                        field.getEntityName(), field.getName(), field.getType().getName()));
            }
        }
    };

    /**
     * Tests if the data type of the annotated field to be validated is the data
     * type supported by the specified Envali annotation.
     *
     * @param field The field to be validated
     *
     * @exception NullPointerException          If {@code null} is passed as an
     *                                          argument
     * @exception UnsupportedOperationException If the data type of the annotated
     *                                          field is not supported
     */
    public abstract void requireSupportedDataType(@NonNull EnvaliFieldHelper field);
}
