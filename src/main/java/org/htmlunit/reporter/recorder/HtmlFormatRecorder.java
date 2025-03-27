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

import org.htmlunit.reporter.record.IRecord;

import java.io.IOException;
import java.util.List;

/**
 * Recorder implementation that saves {@link IRecord} objects to an HTML file.
 * The generated HTML document represents the recorded test results.
 *
 * @author Akif Esad
 */
public final class HtmlFormatRecorder extends Recorder {
    /**
     * Creates a new Recorder.
     *
     * @param outputPath path of file for save records
     * @param appendMode append mode on or off
     */
    public HtmlFormatRecorder(final String outputPath, final boolean appendMode) {
        super(outputPath, appendMode);
    }

    /**
     * Records a single test result.
     *
     * @param record the test result to record
     * @throws IOException if there's an error writing the record
     */
    @Override
    public void saveRecord(final IRecord record) throws IOException {

    }

    /**
     * Records multiple test results.
     *
     * @param records list of test results to record
     * @throws IOException if there's an error writing the records
     */
    @Override
    public void saveRecords(final List<IRecord> records) throws IOException {

    }

    /**
     * Finalizes the recording process. Should be called after all records are written.
     *
     * @throws IOException if there's an error during finalization
     */
    @Override
    public void close() throws IOException {

    }
}
