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
package org.htmlunit.reporter.record;

/**
 * This class represents a record of a client-side action (e.g., JavaScript execution, network request).
 * It implements the IRecord interface to provide a consistent structure for storing and retrieving record information.
 *
 * @author Akif Esad
 */
public class ClientActionRecord implements IRecord {

    private final String methodName_;
    private final String className_;
    private final Long timestamp_;
    private final String context_;

    /**
     * Creates a new ClientActionRecord with the specified values.
     * @param methodName the name of the client-side method that was invoked
     * @param className the name of the class containing the client-side method
     * @param timestamp the timestamp of the client-side action
     * @param context additional context information associated with the action
     */
    public ClientActionRecord(final String methodName,
                              final String className,
                              final Long timestamp,
                              final String context) {
        this.methodName_ = methodName;
        this.className_ = className;
        this.timestamp_ = timestamp;
        this.context_ = context;
    }

    /**
     * Gets the name of the test.
     *
     * @return the test name
     */
    @Override
    public String getMethodName() {
        return this.methodName_;
    }

    /**
     * Gets the class name containing the test.
     *
     * @return the class name
     */
    @Override
    public String getClassName() {
        return this.className_;
    }

    /**
     * Retrieves the timestamp associated with the record.
     *
     * @return the timestamp of the record, or null if not available
     */
    @Override
    public Long getTimestamp() {
        return this.timestamp_;
    }

    /**
     * Gets the record data in JSON format.
     *
     * @return the JSON formatted record
     */
    @Override
    public String getContext() {
        return this.context_;
    }

    /**
     * Gets the record type.
     *
     * @return the record type
     */
    @Override
    public RecordType getRecordType() {
        return RecordType.Info_Record;
    }
}
