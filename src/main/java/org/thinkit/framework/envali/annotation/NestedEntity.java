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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that indicates the field is the nested validatable entity.
 * <p>
 * Specify this annotation for fields that is nested validatable entity as
 * follows. Whenever this annotation is specified for an object that does not
 * implement the ValidatableEntity annotation, UnsupportedOperationException
 * will be thrown at runtime.
 * <p>
 * This annotation can also be specified for collection classes that implement
 * interfaces such as List, Map, and Set. When this annotation is specified for
 * a collection class, the type specified in the generics of the collection is
 * parsed, and if the type specified in the generics implements the
 * ValidatableEntity interface, validation can be performed. If the type
 * specified in the generic does not implement the ValidatableEntity interface,
 * UnsupportedOperationException will be thrown at runtime.
 * <p>
 * For List and Set, the zeroth generic type (of course) must be a
 * ValidatableEntity, and for Map the value, like Map&lt;String,
 * ConcreteValidatableEntity&gt;), of the value generic must be a
 * ValidatableEntity.
 *
 * <pre>
 * <code>
 * public class ConcreteEntity implements ValidatableEntity, Serializable {
 *
 *      &#64;NestedEntity
 *      private ValidatableEntity entity;
 *
 *      &#64;NestedEntity
 *      private List&lt;ConcreteValidatableEntity&gt; validatableEntityList;
 *
 *      &#64;NestedEntity
 *      private Map&lt;String, ConcreteValidatableEntity&gt; validatableEntityMap;
 *
 *      &#64;NestedEntity
 *      private Set&lt;ConcreteValidatableEntity&gt; validatableEntitySet;
 * }
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NestedEntity {
}
