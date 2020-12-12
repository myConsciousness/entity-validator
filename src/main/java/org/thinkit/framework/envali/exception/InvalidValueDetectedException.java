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

package org.thinkit.framework.envali.exception;

import org.thinkit.framework.envali.Envali;

/**
 * Thrown to indicate that the validation process on the {@link Envali}
 * interface has detected an invalid value.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public final class InvalidValueDetectedException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 0L;

    /**
     * Constructs an <code>InvalidValueDetectedException</code> with no detail
     * message.
     */
    public InvalidValueDetectedException() {
        super();
    }

    /**
     * Constructs an <code>InvalidValueDetectedException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public InvalidValueDetectedException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with <code>cause</code> is <i>not</i>
     * automatically incorporated in this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link Throwable#getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method). (A <code>null</code>
     *                value is permitted, and indicates that the cause is
     *                nonexistent or unknown.)
     * @since 1.0
     */
    public InvalidValueDetectedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * <code>(cause==null ? null : cause.toString())</code> (which typically
     * contains the class and detail message of <code>cause</code>). This
     * constructor is useful for exceptions that are little more than wrappers for
     * other throwables (for example,
     * {@link java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method). (A <code>null</code> value
     *              is permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.0
     */
    public InvalidValueDetectedException(Throwable cause) {
        super(cause);
    }
}
