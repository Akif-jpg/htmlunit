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
package org.htmlunit.suite.recorder;

import java.io.IOException;
import java.util.List;

import org.htmlunit.WebClient;
import org.htmlunit.suite.record.IRecord;

/**
 * Write results to specified file path in HTML format.
 * @author Akif Esad
 */
public class HtmlRecordWriter extends Recorder implements IRecord {
    /**
     * @param outputPath path of file for save records
     * @param appendMode append mode on or off
     * @param webClient web client for save records to suit
     * @author Akif Esad
     */
    public HtmlRecordWriter(final String outputPath, final boolean appendMode, final WebClient webClient) {
        super(outputPath, appendMode, webClient);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getTestName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTestName'");
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClassName'");
    }

    @Override
    public String getTestStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTestStatus'");
    }

    @Override
    public String getRecord() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRecord'");
    }

    @Override
    public void recordTestResult(final IRecord testResult) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordTestResult'");
    }

    @Override
    public void recordTestResults(final List<IRecord> testResults) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordTestResults'");
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

}
