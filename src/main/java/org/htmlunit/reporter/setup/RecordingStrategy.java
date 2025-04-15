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
package org.htmlunit.reporter.setup;

import org.htmlunit.WebClient;
import org.htmlunit.reporter.EnvironmentReader;
import org.htmlunit.reporter.recorder.Recorder;
import org.htmlunit.reporter.recorder.RecorderManager;

/**
 * Defines a strategy for preparing and registering custom configurations for a {@link WebClient}.
 * Implementations of this asbstract class specify how a WebClient should be configured before use.
 *
 * @author Akif Esad
 */
public abstract class RecordingStrategy {

    private final Recorder recorder_;
    private final String methodName_;
    private final String className_;
    /**
     * Creates a new instance of the RecordingStrategy class and initializes the recording environment.
     * This constructor uses the provided recordId to obtain a corresponding recorder instance
     * by interacting with the {@link RecorderManager} and the {@link EnvironmentReader}.
     *
     * @param recordId The unique identifier for the recorder instance to be obtained
     */
    RecordingStrategy(final String recordId) {

        final EnvironmentReader env = EnvironmentReader.getInstance();
        this.methodName_ = getMethodNameFromRecordId(recordId);
        this.className_ = getClassNameFromRecordId(recordId);

        this.recorder_ = RecorderManager.obtainRecorder(recordId);
    }

    String getMethodNameFromRecordId(final String recordId) {
        if (recordId.contains("#")) {
            return recordId.split("#")[1];
        }
        else {
            return "";  // Or throw an exception, depending on your requirements
        }
    }

    String getClassNameFromRecordId(final String recordId) {
        final String[] parts = recordId.split("#");
        final String classNamePart = parts[0]; // Safe since split always returns at least one element

        if (classNamePart.contains(".")) {
            final String[] parsed = classNamePart.split("\\."); // Escape . as it's a special regex character
            return parsed[parsed.length - 1];
        }
        else {
            return classNamePart; // Handle the case where there are no dots
        }
    }

    /**
     * Prepares the given WebClient with custom configurations and registers it for use.
     *
     * @param webClient the WebClient instance to be configured and registered
     */
    abstract void prepareAndRegister(WebClient webClient);

    /**
     * Returns the recorder associated with this strategy.
     * @return the recorder
     */
    public Recorder getRecorder_() {
        return recorder_;
    }

    /**
     * Returns the method name extracted from the recordId.
     * @return the method name
     */
    public String getMethodName_() {
        return methodName_;
    }

    /**
     * Returns the class name extracted from the recordId.
     * @return the class name
     */
    public String getClassName_() {
        return className_;
    }
}
