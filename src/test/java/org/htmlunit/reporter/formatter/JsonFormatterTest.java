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
import org.htmlunit.reporter.record.Record;
import org.htmlunit.reporter.record.RecordType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test class for the {@link JsonFormatter} class. It validates the JSON formatting functionality
 * for single and multiple records using test methods annotated with {@code @Test}. Each test ensures
 * the output JSON contains expected properties and values.
 *
 * @author Akif Esad
 */
public class JsonFormatterTest {

    /**
     * Tests the JSON formatting functionality for multiple records using the {@link JsonFormatter} class.
     * This test verifies that the formatted JSON string contains the expected properties and values
     * for multiple {@link IRecord} objects.
     *
     * The method performs the following steps:
     * - Creates a list of mock records with predefined values.
     * - Uses the {@link JsonFormatter#format(List, String)} method to convert the records into a JSON string.
     * - Asserts that the output JSON string includes the expected values for each record.
     *
     * This test ensures the correct functionality of the formatter in handling multiple records and
     * generating accurate JSON representations.
     *
     * @author Akif Esad
     */
    @Test
    public void formatTest() {
        // Create a list of mock records
        final List<IRecord> records = new ArrayList<>();
        records.add(new Record("test1", "class1", RecordType.Html_Record, "status1"));
        records.add(new Record("test2", "class2", RecordType.Css_Record, "status2"));

        // Create an instance of JsonFormatter
        final JsonFormatter formatter = new JsonFormatter();

        // Format the records
        final String json = formatter.format(records, "recorderUUID");

        // Assert that the JSON string contains expected values
        assertTrue(json.contains("\"methodName\": \"test1\""));
        assertTrue(json.contains("\"className\": \"class1\""));
        assertTrue(json.contains("\"record\": \"status1\""));
        assertTrue(json.contains("\"methodName\": \"test2\""));
        assertTrue(json.contains("\"className\": \"class2\""));
        assertTrue(json.contains("\"record\": \"status2\""));
    }

    /**
     * Tests the JSON formatting functionality for a single record using the {@link JsonFormatter} class.
     * This test ensures that the generated JSON string contains the expected properties and values
     * corresponding to a single {@link IRecord} object.
     *
     * The method performs the following steps:
     * - Creates a mock implementation of the {@link IRecord} with predefined values.
     * - Utilizes the {@link JsonFormatter#format(IRecord, String)} method to convert the record into JSON.
     * - Validates that the formatted JSON string includes the expected properties and their corresponding values.
     *
     * @author Akif Esad
     */
    @Test
    public void formatSingleRecordTest() {
        // Create a mock record
        final IRecord record = new Record("testSingle", "classSingle", RecordType.Html_Record, "statusSingle");

        // Create an instance of JsonFormatter
        final JsonFormatter formatter = new JsonFormatter();

        // Format the single record
        final String json = formatter.format(record, "singleRecorderUUID");

        // Assert that the JSON string contains expected values
        assertTrue(json.contains("\"methodName\": \"testSingle\""));
        assertTrue(json.contains("\"className\": \"classSingle\""));
        assertTrue(json.contains("\"record\": \"statusSingle\""));
    }
}
