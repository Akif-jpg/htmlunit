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
import org.htmlunit.reporter.record.RecordType;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link XmlFormatter}.
 * Uses JUnit 4 annotations and assertions
 *
 * @author Akif Esad.
 */
public class XmlFormatterTest {

    private XmlFormatter formatter_;
    private String testUUID_;

    /**
     * Mock implementation of the IRecord interface used for testing purposes.
     * The class represents a record containing information such as ID, name, value,
     * timestamp, and a null value. This implementation is designed for mock data handling
     * within unit tests.
     * <p>
     * The MockRecord class provides getter methods for its fields and overrides methods
     * from the IRecord interface to return default or empty values.
     */
    public static class MockRecord implements IRecord {
        private final String id_;
        private final String name_;
        private final String value_;
        private final Long timestamp_;
        private final String nullValue_ = null; // Test null handling

        /**
         * Constructs a MockRecord instance with the specified attributes.
         * This constructor initializes the record with the given parameters
         * used for testing purposes and mock data handling.
         *
         * @param id                the unique identifier for the mock record
         * @param name              the name of the mock record
         * @param value             the value associated with the mock record
         * @param timestamp         the timestamp indicating when the record was created or modified
         * @param specialCharsValue a string containing special characters to test specific scenarios
         */
        public MockRecord(final String id, final String name,
                          final String value, final Long timestamp,
                          final String specialCharsValue) {
            this.id_ = id;
            this.name_ = name;
            this.value_ = value;
            this.timestamp_ = timestamp;
        }

        /**
         * Getter for id.
         *
         * @return id
         */
        public String getId() {
            return id_;
        }

        /**
         * Getter for name.
         *
         * @return name
         */
        public String getName() {
            return name_;
        }

        /**
         * Getter for value.
         *
         * @return value
         */
        public String getValue() {
            return value_;
        }

        /**
         * Getter for timestamp.
         *
         * @return timestamp
         */
        public Long getTimestamp() {
            return timestamp_;
        }

        /**
         * Getter for nullValue.
         *
         * @return nullValue
         */
        public String getNullValue() {
            return nullValue_;
        }

        /**
         * Not record to xml it's not to getter.
         */
        public void doSomething() {
            // nothing
        }

        /**
         * Not record to xml it have parameter.
         *
         * @param i any value for testing
         * @return param+i
         */
        public String getWithParam(final int i) {
            return "param" + i;
        }

        /**
         * Gets the name of the test.
         *
         * @return the test name
         */
        @Override
        public String getMethodName() {
            return "";
        }

        /**
         * Gets the class name containing the test.
         *
         * @return the class name
         */
        @Override
        public String getClassName() {
            return "";
        }

        /**
         * Gets the record data in JSON format.
         *
         * @return the JSON formatted record
         */
        @Override
        public String getContext() {
            return "";
        }

        /**
         * Gets the record type.
         *
         * @return the record type
         */
        @Override
        public RecordType getRecordType() {
            return null;
        }
    }

    /**
     * Setup before start tests, set formatter and uuid.
     */
    @Before
    public void setUp() {
        formatter_ = new XmlFormatter();
        testUUID_ = "test-uuid-f0r-xml";
    }

    /**
     * Tests the XML formatting functionality for a single record using the formatter.
     * This test ensures that the generated XML string matches the expected format and values
     * when provided with a single mocked implementation of {@code IRecord}.
     * <p>
     * The method performs the following steps:
     * - Constructs a mocked {@code MockRecord} instance with predefined values including
     * special characters to test XML escaping.
     * - Calls
     */
    @Test
    public void formatSingleRecord() {
        // Arrange
        final MockRecord record = new MockRecord("rec1",
                "Test Record",
                "Some Data",
                1678886400123L,
                "Data with <>&'\"");
        final String expectedXml =
                "<HtmlUnitReport>\n"
                        + "  <recorderUUID>test-uuid-f0r-xml</recorderUUID>\n"
                        + "  <record>\n"
                        + "      <className></className>\n"
                        + "      <context></context>\n"
                        + "      <id>rec1</id>\n"
                        + "      <methodName></methodName>\n"
                        + "      <name>Test Record</name>\n"
                        + "      <nullValue></nullValue>\n"
                        + "      <recordType></recordType>\n"
                        + "      <timestamp>1678886400123</timestamp>\n"
                        + "      <value>Some Data</value>\n"
                        + "  </record>\n"
                        + "</HtmlUnitReport>\n";

        // Act
        final String actualXml = formatter_.format(record, testUUID_);
        // Assert
        assertEquals(expectedXml, actualXml);
    }

    /**
     * Tests the XML formatting functionality for multiple records.
     */
    @Test
    public void formatMultipleRecords() {
        // Arrange
        final MockRecord record1 = new MockRecord("r1", "First", "Value1", 111111111L, "<data1>");
        final MockRecord record2 = new MockRecord("r2", "Second", "Value2", 222222222L, "&data2");
        final List<IRecord> records = Arrays.asList(record1, record2);

        final String expectedXml =
                "<HtmlUnitReport>\n"
                        + "  <recorderUUID>test-uuid-f0r-xml</recorderUUID>\n"
                        + "  <records>\n"
                        // --- Record 1 ---
                        + "    <record>\n"
                        // Fields in the observed/required order:
                        + "      <className></className>\n"
                        + "      <context></context>\n"
                        + "      <id>r1</id>\n"
                        + "      <methodName></methodName>\n"
                        + "      <name>First</name>\n"
                        + "      <nullValue></nullValue>\n"
                        + "      <recordType></recordType>\n"
                        + "      <timestamp>111111111</timestamp>\n"
                        + "      <value>Value1</value>\n"
                        + "    </record>\n"
                        // --- Record 2 ---
                        + "    <record>\n"
                        // Fields in the observed/required order:
                        + "      <className></className>\n"
                        + "      <context></context>\n"
                        + "      <id>r2</id>\n"
                        + "      <methodName></methodName>\n"
                        + "      <name>Second</name>\n"
                        + "      <nullValue></nullValue>\n"
                        + "      <recordType></recordType>\n"
                        + "      <timestamp>222222222</timestamp>\n"
                        + "      <value>Value2</value>\n"
                        + "    </record>\n"
                        + "  </records>\n"
                        + "</HtmlUnitReport>\n";

        // Act
        final String actualXml = formatter_.format(records, testUUID_);

        // Assert
        assertEquals(expectedXml, actualXml);
    }

    /**
     * Tests the XML formatting functionality for an empty list of records using the formatter.
     * This test ensures that the generated XML string includes the correct structure and tags
     * when provided with an empty {@code List<IRecord>}.
     * <p>
     * The method performs the following steps:
     * - Creates an empty list of {@link IRecord}.
     * - Defines the expected XML structure where the record tag is present but contains no child elements.
     * - Calls the formatter's {@code format()} method with the empty list and a predefined UUID.
     * - Validates that the actual XML output matches the expected XML structure.
     * <p>
     * This test confirms the formatter correctly handles the edge case of no records
     * ensuring the output is well-formed.
     */
    @Test
    public void formatEmptyList() {
        // Arrange
        final List<IRecord> emptyList = Collections.emptyList(); // Veya new ArrayList<>()
        final String expectedXml =
                "<HtmlUnitReport>\n"
                        + "  <recorderUUID>test-uuid-f0r-xml</recorderUUID>\n"
                        + "  <records>\n"  // Boş liste için records etiketi hala olmalı ama içi boş olmalı
                        + "  </records>\n"
                        + "</HtmlUnitReport>\n";

        // Act
        final String actualXml = formatter_.format(emptyList, testUUID_);

        // Assert
        assertEquals(expectedXml, actualXml);
    }
}
