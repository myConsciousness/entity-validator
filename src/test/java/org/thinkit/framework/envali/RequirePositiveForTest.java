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

import org.thinkit.framework.envali.annotation.RequirePositive;
import org.thinkit.framework.envali.entity.ValidatableEntity;

/**
 * {@link Envali} インターフェースと {@link RequirePositive}
 * アノテーションをテストする際に使用するフィールドを定義するクラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
final class RequirePositiveForTest implements ValidatableEntity {

    /**
     * 負数が許容されないフィールド
     */
    @RequirePositive
    private int positive;

    /**
     * デフォルトコンストラクタ
     */
    @SuppressWarnings("unused")
    private RequirePositiveForTest() {
    }

    /**
     * コンストラクタ
     *
     * @param positive 正数
     */
    public RequirePositiveForTest(int positive) {
        this.positive = positive;
    }
}
