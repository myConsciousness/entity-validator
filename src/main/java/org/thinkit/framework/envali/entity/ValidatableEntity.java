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

package org.thinkit.framework.envali.entity;

import org.thinkit.framework.envali.annotation.ParameterMapping;

/**
 * A marker interface that indicates that it is a verifiable entity.
 * <p>
 * There are no specific operations defined for this marker interface, only that
 * the class that implements it is a verifiable entity.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public interface ValidatableEntity {

    /**
     * Tests whether the {@link ParameterMapping} annotation is specified in the
     * concrete class that implements {@link ValidatableEntity} .
     *
     * @return {@code true} if the {@link ParameterMapping} annotation is specified
     *         in the concrete class that implements {@link ValidatableEntity} ,
     *         otherwise {@code false}
     *
     * @since 1.0.2
     */
    default boolean hasParameterMapping() {
        return this.getClass().getAnnotation(ParameterMapping.class) != null;
    }
}
