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
 * Base record class for save by recorders.
 *
 * @author Akif Esad
 */
public class Record implements IRecord {
    private final String testName_;
    private final String className_;
    private final RecordType recordType_;
    private final String record_;

    /**
     * Creates a new Record.
     * @param testName the test name
     * @param className the class name
     * @param recordType the record type
     * @param record record value
     */
    public Record(final String testName, final String className,
                  final  RecordType recordType, final String record) {
        this.testName_ = testName;
        this.className_ = className;
        this.recordType_ = recordType;
        this.record_ = record;
    }

    @Override
    public String getMethodName() {
        return testName_;
    }

    @Override
    public String getClassName() {
        return className_;
    }

    @Override
    public String getRecord() {
        return record_;
    }

    @Override
    public RecordType getRecordType() {
        return recordType_;
    }
}
