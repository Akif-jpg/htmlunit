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
package org.htmlunit.reporter.recorder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.htmlunit.reporter.formatter.IFormatter;
import org.htmlunit.reporter.record.IRecord;

/**
 * Abstract class for recording {@link IRecord} and saving.
 * Implementations should provide a way to save {@link IRecord} lists to a file.
 *
 * @author Akif Esad
 */
public abstract class Recorder {
    /** Path of file for save records. */
    private final String outputPath_;
    /** Append mode on or off. */
    private final boolean appendMode_;
    /** UUID of the recorder. */
    private final String recorderUUID_;
    /** List of records. */
    private final List<IRecord> recordList_;
    /** Formatter for format current records to target file format.  */
    private IFormatter formatter_;

    /**
     * Creates a new Recorder.
     * @param outputPath path of file for save records
     * @param appendMode append mode on or off
     */
    public Recorder(final String outputPath, final boolean appendMode) {
        this.outputPath_ = outputPath;
        this.appendMode_ = appendMode;
        this.recordList_ = new ArrayList<>();
        this.recorderUUID_ = java.util.UUID.randomUUID().toString();
    }

    /**
     * Gets the list of records.
     * @return the list of records
     */
    public List<IRecord> getRecords() {
        return new ArrayList<>(recordList_);
    }

    /**
     * Gets the formatted records as a String.
     * The formatting is done by the assigned {@link IFormatter}.
     *
     * @return the formatted records as a String, or null if no formatter is set
     */
    public String getFormattedRecords() {
        return this.formatter_.format(this.recordList_, this.recorderUUID_);
    }

    /**
     * Adds a new record to the list.
     * @param record the record to add
     */
    public void addRecord(final IRecord record) {
        recordList_.add(record);
    }

    /**
     * Saves all records to the output file.
     * @throws IOException if there's an error writing the records
     */
    public void saveAllRecords() throws IOException {
        saveRecords(recordList_);
    }

    /**
     * Records a single test result.
     * @param record the test result to record
     * @throws IOException if there's an error writing the record
     */
    public abstract void saveRecord(IRecord record) throws IOException;

    /**
     * Records multiple test results.
     * @param records list of test results to record
     * @throws IOException if there's an error writing the records
     */
    public abstract void saveRecords(List<IRecord> records) throws IOException;

    /**
     * Finalizes the recording process. Should be called after all records are written.
     * @throws IOException if there's an error during finalization
     */
    public abstract void close() throws IOException;

    protected File getOutputFile() {
        return new File(outputPath_);
    }

    protected boolean isAppendMode() {
        return appendMode_;
    }

    protected void ensureOutputDirectoryExists() {
        final File outputFile = getOutputFile();
        final File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    protected String getRecorderUUID() {
        return recorderUUID_;
    }

    /**
     * Saves the given string to the output file.
     * @param savedString the string to save
     * @throws IOException if there's an error writing the string
     */
    protected void save(final String savedString) throws IOException {
        ensureOutputDirectoryExists();
        if (isAppendMode() && getOutputFile().exists()) {
            try (FileWriter writer = new FileWriter(
                    getOutputFile()
                    + java.time.LocalDate.now().toString()
                    + "_"
                    + getRecorderUUID())) {
                writer.write(savedString);
            }
            return;
        }
        try (FileWriter writer = new FileWriter(getOutputFile())) {
            writer.write(savedString);
        }
    }

    /**
     * A setter method for formatter.
     *
     * @param formatter implemented methods to {@link IFormatter}
     */
    public void setFormatter_(final IFormatter formatter) {
        this.formatter_ = formatter;
    }

    /**
     * A getter method for return current formatter.
     *
     * @return This method return a formatter.
     */
    public IFormatter getFormatter_() {
        return this.formatter_;
    }
}
