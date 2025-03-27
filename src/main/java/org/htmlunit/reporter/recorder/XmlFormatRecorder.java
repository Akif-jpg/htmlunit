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

import java.io.IOException;
import java.util.List;

import org.htmlunit.reporter.record.IRecord;

/**
 * Write results to specified file path in HTML format.
 * @author Akif Esad
 */
public class XmlFormatRecorder extends Recorder {
    /**
     * @param outputPath path of file for save records
     * @param appendMode append mode on or off
     * @author Akif Esad
     */
    public XmlFormatRecorder(final String outputPath, final boolean appendMode) {
        super(outputPath, appendMode);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void saveRecord(final IRecord record) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordTestResult'");
    }

    @Override
    public void saveRecords(final List<IRecord> records) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordTestResults'");
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }
}
