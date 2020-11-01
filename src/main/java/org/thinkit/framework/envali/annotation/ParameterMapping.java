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

package org.thinkit.framework.envali.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that maps a content file that defines the expectations for
 * entity validation.
 * <p>
 * In the content file, define your expectations in a format that conforms to
 * the content framework's specifications. You may obtain information of the
 * Content Framework at
 *
 * https://github.com/myConsciousness/content-framework
 *
 * <p>
 * Specify the annotation to the class of the entity to be verified as follows.
 *
 * <pre>
 * <code>
 * &#64;ParameterMapping(content = "./content")
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *      // define something
 * }
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterMapping {

    /**
     * Returns the path to a content file with defined expectations to be used when
     * performing the verification.
     *
     * @return The path to a content file with defined expectations to be used when
     *         performing the verification
     */
    public String content();
}
