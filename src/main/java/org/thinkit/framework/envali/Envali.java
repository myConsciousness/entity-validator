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
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.catalog.EnvaliContentAttribute;
import org.thinkit.framework.envali.catalog.EnvaliContentRoot;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * {@link Envali} is a powerful interface that provides common and intuitive
 * validation process for entity fields.
 * <p>
 * It is very easy and intuitive to use, you just need to annotate the fields of
 * the entity to be validated such as {@link RequireNonNull} and
 * {@link RequireNonEmpty} and others.
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 *
 * @see RequireNonNull
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
     * {@link ValidatableEntity}
     * インターフェースを実装したエンティティオブジェクトに設定されたアノテーションを解析しフィールドの値の有効性を検証します。
     *
     * @param entity {@link ValidatableEntity} インターフェースを実装した検証対象のエンティティオブジェクト
     *
     * @exception NullPointerException          引数として {@code null} が渡された場合
     * @exception InvalidValueDetectedException 検証処理で無効な値を検知した場合
     * @exception UnsupportedOperationException リフレクション処理時に想定外のオペレーションを検知した場合
     */
    public static void validate(ValidatableEntity entity) {
        Preconditions.requireNonNull(entity);

        final Class<? extends ValidatableEntity> entityClass = entity.getClass();
        getEnvaliContent(entityClass);

        Arrays.asList(entityClass.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);

            Arrays.asList(field.getAnnotations()).forEach(annotation -> {
                try {
                    final Class<? extends Annotation> annotationType = annotation.annotationType();

                    if (annotationType.equals(RequireNonNull.class)) {
                        Preconditions.requireNonNull(field.get(entity));
                    } else if (annotationType.equals(RequirePositive.class)) {
                        Preconditions.requirePositive(Integer.parseInt(String.valueOf(field.get(entity))),
                                new InvalidValueDetectedException());
                    } else if (annotationType.equals(RequireNegative.class)) {
                        Preconditions.requireNegative(Integer.parseInt(String.valueOf(field.get(entity))),
                                new InvalidValueDetectedException());
                    } else if (annotationType.equals(RequireRangeTo.class)) {

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
     * <p>
     * If the content is not mapped to the entity object to be validated, an empty
     * {@link List} is returned.
     *
     * @param entityClass Entity objects to be validated
     * @return Envali's validation content
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private static List<Map<String, String>> getEnvaliContent(final Class<? extends ValidatableEntity> entityClass) {
        Preconditions.requireNonNull(entityClass);

        final ParameterMapping content = entityClass.getAnnotation(ParameterMapping.class);

        if (content == null) {
            return List.of();
        }

        return ContentLoader.load(
                entityClass.getClassLoader()
                        .getResourceAsStream(EnvaliContentRoot.ROOT.getTag() + content.content() + Extension.json()),
                getContentAttributes());
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
}
