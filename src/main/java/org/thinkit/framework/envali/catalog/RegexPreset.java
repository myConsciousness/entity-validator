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

import org.thinkit.api.catalog.BiCatalog;
import org.thinkit.common.regex.catalog.RegexPattern;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The catalog that manages regex preset.
 *
 * @author Kato Shinya
 * @since 1.0.2
 */
@RequiredArgsConstructor
public enum RegexPreset implements BiCatalog<RegexPreset, RegexPattern> {

    /**
     * Indicates that this is none and the default value
     */
    NONE(0, null),

    /**
     * Email address
     */
    EMAIL_ADDRESS(1, RegexPattern.EMAIL_ADDRESS),

    /**
     * Domain name
     */
    DOMAIN_NAME(2, RegexPattern.DOMAIN_NAME),

    /**
     * Web URL (HTTP / HTTPS)
     */
    WEB_URL(3, RegexPattern.WEB_URL),

    /**
     * User id
     */
    USER_ID(4, RegexPattern.USER_ID),

    /**
     * Fixed line phone (Japan)
     */
    FIXED_LINE_PHONE_JP(5, RegexPattern.FIXED_LINE_PHONE_JP),

    /**
     * Fixed line phone with hyphen (Japan)
     */
    FIXED_LINE_PHONE_WITH_HYPHEN_JP(6, RegexPattern.FIXED_LINE_PHONE_WITH_HYPHEN_JP),

    /**
     * Cell phone (Japan)
     */
    CELL_PHONE_JP(7, RegexPattern.CELL_PHONE_JP),

    /**
     * Cell phone with hyphen (Japan)
     */
    CELL_PHONE_WITH_HYPHEN_JP(8, RegexPattern.CELL_PHONE_WITH_HYPHEN_JP),

    /**
     * Password
     * <p>
     * Ensures that password will contain at least 1 upper case letter, 1 lower case
     * letter, 1 number or special character, 8-32 characters in length.
     */
    PASSWORD(9, RegexPattern.PASSWORD),

    /**
     * Date (yyyyMMdd format)
     */
    DATE(10, RegexPattern.DATE),

    /**
     * Date with hyphen (yyyy-MM-dd format)
     */
    DATE_WITH_HYPHEN(11, RegexPattern.DATE_WITH_HYPHEN),

    /**
     * Date with slash (yyyyMMdd format)
     */
    DATE_WITH_SLASH(12, RegexPattern.DATE_WITH_SLASH),

    /**
     * Post code (Japan)
     */
    POST_CODE_JP(13, RegexPattern.POST_CODE_JP),

    /**
     * XML file
     */
    XML_FILE(14, RegexPattern.XML_FILE),

    /**
     * IP address (IPv4)
     */
    IP_ADDRESS(15, RegexPattern.IP_ADDRESS),

    /**
     * IP address with port (IPv4)
     */
    IP_ADDRESS_WITH_PORT(16, RegexPattern.IP_ADDRESS_WITH_PORT),

    /**
     * Numeric
     */
    NUMERIC(17, RegexPattern.NUMERIC),

    /**
     * Alphanumeric character
     */
    ALPHANUMERIC(18, RegexPattern.ALPHANUMERIC),

    /**
     * Alphabet
     */
    ALPHABET(19, RegexPattern.ALPHABET),

    /**
     * Alphabet (upper case)
     */
    ALPHABET_UPPER_CASE(20, RegexPattern.ALPHABET_UPPER_CASE),

    /**
     * Alphabet (lower case)
     */
    ALPHABET_LOWER_CASE(21, RegexPattern.ALPHABET_LOWER_CASE),

    /**
     * FTP URL
     */
    FTP_URL(22, RegexPattern.FTP_URL),

    /**
     * Java file
     */
    JAVA_FILE(23, RegexPattern.JAVA_FILE),

    /**
     * Text file
     */
    TEXT_FILE(24, RegexPattern.TEXT_FILE),

    /**
     * JSON file
     */
    JSON_FILE(25, RegexPattern.JSON_FILE),

    /**
     * Japanese Kanji
     */
    JAPANESE_KANJI(26, RegexPattern.JAPANESE_KANJI),

    /**
     * Hiragana
     */
    HIRAGANA(27, RegexPattern.HIRAGANA),

    /**
     * Hiragana (upper case)
     */
    HIRAGANA_UPPER_CASE(28, RegexPattern.HIRAGANA_UPPER_CASE),

    /**
     * Hiragana (lower case)
     */
    HIRAGANA_LOWER_CASE(29, RegexPattern.HIRAGANA_LOWER_CASE),

    /**
     * Katakana
     */
    KATAKANA(30, RegexPattern.KATAKANA),

    /**
     * Katakana (upper case)
     */
    KATAKANA_UPPER_CASE(31, RegexPattern.KATAKANA_UPPER_CASE),

    /**
     * Katakana (lower case)
     */
    KATAKANA_LOWER_CASE(32, RegexPattern.KATAKANA_LOWER_CASE),

    /**
     * Japanese alphabet
     */
    JAPANESE_ALPHABET(33, RegexPattern.JAPANESE_ALPHABET),

    /**
     * Japanese alphanumeric
     */
    JAPANESE_ALPHANUMERIC(34, RegexPattern.JAPANESE_ALPHANUMERIC);

    /**
     * The code
     */
    @Getter
    private final int code;

    /**
     * The tag
     */
    @Getter
    private final RegexPattern tag;
}
