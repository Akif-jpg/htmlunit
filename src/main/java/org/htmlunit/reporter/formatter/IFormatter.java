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
package org.htmlunit.reporter.formatter;

import org.htmlunit.reporter.record.IRecord;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code IFormatter} interface is designed to provide a standard
 * mechanism for formatting {@link IRecord} objects into specific output formats.
 * It facilitates the representation of test records in a structured, customizable manner.
 *
 * @author Akif Esad
 */
public interface IFormatter {
    /**
     * Formats a list of IRecord objects into a specific string representation.
     *
     * @param records the list of IRecord objects to be formatted
     * @param recorderUUID the unique identifier of the recorder used for formatting
     * @return the formatted string representation of the provided records
     *
     * @author Akif Esad
     */
    String format(List<IRecord> records, String recorderUUID);

    /**
     * Formats an IRecord objects into a specific string representation.
     *
     * @param record the IRecord objects to be formatted
     * @param recorderUUID the unique identifier of the recorder used for formatting
     * @return the formatted string representation of the provided record
     *
     * @author Akif Esad
     */
    String format(IRecord record, String recorderUUID);

    /**
     * Checks if a method is a standard getter method (starts with "get", has no parameters,
     * and returns a non-void type).
     *
     * @param method The method to check.
     * @return true if the method is a getter, false otherwise.
     */
    static boolean isGetter(final Method method) {
        // Ensure it's public if dealing with interfaces or complex hierarchies,
        // but getDeclaredMethods includes private/protected/package-private.
        // For simplicity matching the JSON formatter, we just check the name, params, and return type.
        return method.getName().startsWith("get")
                && method.getParameterCount() == 0
                && method.getReturnType() != void.class;
    }

    /**
     * Derives a field name from a getter method name (e.g., "getValue" becomes "value").
     *
     * @param methodName The name of the getter method.
     * @return The derived field name.
     */
    static String getFieldName(final String methodName) {
        if (methodName.startsWith("get") && methodName.length() > 3) {
            // Convert "getPropertyName" to "propertyName"
            return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        }
        // Fallback for methods not following the "get" convention (though isGetter should filter these)
        return methodName;
    }

    /**
     * Order methods and return it.
     *
     * @param methods list of declared methods
     * @return ordered list alphabetically
     */
    static Method[] orderAndFilterMethods(final Method[] methods) {
        return Arrays.stream(methods)
                .filter(IFormatter::isGetter)
                .sorted(Comparator.comparing(Method::getName))
                .toArray(Method[]::new);
    }
}
