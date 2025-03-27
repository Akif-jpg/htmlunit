/*
 * Copyright (c) 2002-2025 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.htmlunit.reporter.recordannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark and record individual test methods.
 * It includes the following attributes:
 * - {@code value}: The name of the test.
 * - {@code description}: A brief description of the test.
 * - {@code enabled}: A flag to indicate whether the test is enabled or disabled.
 *
 * @author Akif Esad
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RecordTest {
    /**
     * The name of the test.
     * @return the name of the test
     */
    String value() default "";

    /**
     * A brief description of the test.
     * @return the description of the test
     */
    String description() default "";

    /**
     * Indicates if the test is enabled.
     * @return true if the test is enabled, false otherwise
     */
    boolean enabled() default true;
}
