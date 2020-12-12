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

package org.thinkit.framework.envali.catalog;

import java.lang.annotation.Annotation;

import org.thinkit.framework.envali.annotation.RequireNonNull;
import org.thinkit.framework.envali.annotation.RequireNonBlank;
import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.annotation.RequireNegative;
import org.thinkit.framework.envali.annotation.RequireRangeFrom;
import org.thinkit.framework.envali.annotation.RequireRangeTo;
import org.thinkit.framework.envali.annotation.RequireRangeFromTo;
import org.thinkit.framework.envali.annotation.RequireStartWith;
import org.thinkit.framework.envali.annotation.RequireEndWith;
import org.thinkit.framework.envali.annotation.RequireNonEmpty;
import org.thinkit.framework.envali.annotation.NestedEntity;
import org.thinkit.api.catalog.BiCatalog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The catalog that manages validation pattern.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
@RequiredArgsConstructor
public enum ValidationPattern implements BiCatalog<ValidationPattern, Class<? extends Annotation>> {

    /**
     * The pattern of {@link RequireNonNull}
     */
    REQUIRE_NON_NULL(0, RequireNonNull.class),

    /**
     * The pattern of {@link RequireNonBlank}
     */
    REQUIRE_NON_BLANK(1, RequireNonBlank.class),

    /**
     * The pattern of {@link RequirePositive}
     */
    REQUIRE_POSITIVE(2, RequirePositive.class),

    /**
     * The pattern of {@link RequireNegative}
     */
    REQUIRE_NEGATIVE(3, RequireNegative.class),

    /**
     * The pattern of {@link RequireRangeFrom}
     */
    REQUIRE_RANGE_FROM(4, RequireRangeFrom.class),

    /**
     * The pattern of {@link RequireRangeTo}
     */
    REQUIRE_RANGE_TO(5, RequireRangeTo.class),

    /**
     * The pattern of {@link RequireRangeFromTo}
     */
    REQUIRE_RANGE_FROM_TO(6, RequireRangeFromTo.class),

    /**
     * The pattern of {@link RequireStartWith}
     */
    REQUIRE_START_WITH(7, RequireStartWith.class),

    /**
     * The pattern of {@link RequireEndWith}
     */
    REQUIRE_END_WITH(8, RequireEndWith.class),

    /**
     * The pattern of {@link RequireNonEmpty}
     */
    REQUIRE_NON_EMPTY(9, RequireNonEmpty.class),

    /**
     * The pattern of {@link NestedEntity}
     */
    NESTED_ENTITY(10, NestedEntity.class);

    /**
     * The code
     */
    @Getter
    private final int code;

    /**
     * The tag
     */
    @Getter
    private final Class<? extends Annotation> tag;
}
