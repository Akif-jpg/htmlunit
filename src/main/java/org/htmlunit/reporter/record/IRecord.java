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
 * Interface for test records in the HTML Unit test reporter.
 *
 * @author Akif Esad
 */
public interface IRecord {
    /**
     * Gets the name of the test.
     * @return the test name
     */
    String getMethodName();

    /**
     * Gets the class name containing the test.
     * @return the class name
     */
    String getClassName();

    /**
     * Retrieves the timestamp associated with the record.
     *
     * @return the timestamp of the record, or null if not available
     */
    Long getTimestamp();

    /**
     * Gets the record data in JSON format.
     * @return the JSON formatted record
     */
    String getContext();

    /**
     * Gets the record type.
     * @return the record type
     */
    RecordType getRecordType();
}
