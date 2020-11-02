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

import org.thinkit.common.Preconditions;
import org.thinkit.framework.envali.annotation.ParameterMapping;
import org.thinkit.framework.envali.annotation.RequireEndWith;
import org.thinkit.framework.envali.annotation.RequireNegative;
import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.entity.ValidatableEntity;

import lombok.NonNull;

/**
 *
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
public interface Envali {

    public static void validate(@NonNull ValidatableEntity entity) {

        final Class<? extends ValidatableEntity> entityClass = entity.getClass();
        final ParameterMapping parameter = entityClass.getAnnotation(ParameterMapping.class);

        Arrays.asList(entityClass.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);

            Arrays.asList(field.getAnnotations()).forEach(annotation -> {
                try {
                    final Class<? extends Annotation> annotationType = annotation.annotationType();

                    if (annotationType.equals(RequireNonNull.class)) {
                        Preconditions.requireNonNull(field.get(entity), "");
                    } else if (annotationType.equals(RequirePositive.class)) {

                    } else if (annotationType.equals(RequireNegative.class)) {

                    } else if (annotationType.equals(RequireRangeTo.class)) {

                    } else if (annotationType.equals(RequireRangeFromTo.class)) {

                    } else if (annotationType.equals(RequireStartWith.class)) {

                    } else if (annotationType.equals(RequireEndWith.class)) {

                    } else if (annotationType.equals(RequireNonNull.class)) {

                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
