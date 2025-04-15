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
import org.htmlunit.WebWindowEvent;
import org.htmlunit.WebWindowListener;
import org.htmlunit.reporter.EnvironmentReader;
import org.htmlunit.reporter.record.ClientActionRecord;
import org.htmlunit.reporter.recorder.Recorder;
import org.htmlunit.reporter.recorder.RecorderManager;

/**
 * An implementation of the {@link RecordingStrategy} interface that registers a {@link WebWindowListener}
 * to record actions related to web window events. This class configures a {@link WebClient} to monitor
 * opening, closing, and content changes in web windows.
 *
 * @author Akif Esad
 */
public class WindowActionRecordingStrategy extends RecordingStrategy {
    /**
     * Creates a new instance of the RecordingStrategy class and initializes the recording environment.
     * This constructor uses the provided recordId to obtain a corresponding recorder instance
     * by interacting with the {@link RecorderManager} and the {@link EnvironmentReader}.
     *
     * @param recordId The unique identifier for the recorder instance to be obtained
     */

    private static final String WEB_WINDOW_OPENED = "Web window opened.";
    private static final String WEB_WINDOW_CHANGED = "Web window changed";
    private static final String WEB_WINDOW_CLOSED = "Web window closed";

    WindowActionRecordingStrategy(final String recordId) {
        super(recordId);
    }

    /**
     * @param webClient the WebClient instance to be configured and registered
     */
    @Override
    public void prepareAndRegister(final WebClient webClient) {
        webClient.addWebWindowListener(new WebWindowActionRecorder(
                this.getRecorder_(),
                this.getMethodName_(),
                this.getClassName_()
                )
        );
    }

    private static class WebWindowActionRecorder implements WebWindowListener {

        private final Recorder recorder_;
        private final String methodName_;
        private final String className_;

        /**
         * Creates a new instance of the {@code WebWindowActionRecorder}. This constructor initializes
         * the recorder, method name, and class name, which are used to record web window actions.
         *
         * @param recorder The {@link Recorder} instance responsible for recording the actions.
         * @param methodName The name of the method where the web window action occurred.
         * @param className The name of the class where the web window action occurred.
         */
        WebWindowActionRecorder(final Recorder recorder, final String methodName, final String className) {
            this.recorder_ = recorder;
            this.methodName_ = methodName;
            this.className_ = className;
        }
        /**
         * Handles the opening of a new web window. This method is invoked when a new window is opened,
         * but before the content of the window is loaded. Consequently, the {@link WebWindowEvent}'s
         * {@code oldPage} and {@code newPage} properties will be {@code null}.  This method records
         * a "Web window opened" event with relevant metadata such as the current timestamp, method name,
         * and class name.
         *
         * @param event the {@link WebWindowEvent} describing the window opening event. Note that
         *              the {@code oldPage} and {@code newPage} properties of this event will be {@code null}.
         */
        @Override
        public void webWindowOpened(final WebWindowEvent event) {
            this.recorder_.addRecord(new ClientActionRecord(this.methodName_,
                    this.className_,
                    System.currentTimeMillis(),
                    WindowActionRecordingStrategy.WEB_WINDOW_OPENED));
        }

        /**
         * Handles changes in the content of a web window. This method is invoked when the content of
         * a web window is modified.  This could be due to navigation to a new page, dynamic updates
         * via JavaScript, or other modifications to the DOM. The method records a "Web window changed"
         * event along with the current timestamp, method name, and class name, providing context
         * for the content change.
         *
         * @param event the {@link WebWindowEvent} describing the content change event, providing
         *              details about the window whose content was modified. This includes the old
         *              and new pages involved in the change.
         */
        @Override
        public void webWindowContentChanged(final WebWindowEvent event) {
            this.recorder_.addRecord(new ClientActionRecord(this.methodName_,
                    this.className_,
                    System.currentTimeMillis(),
                    WindowActionRecordingStrategy.WEB_WINDOW_CHANGED));
        }

        /**
         * Handles the closing of a web window. This method is triggered when a web window is closed.
         * It records a "Web window closed" event, capturing the timestamp, method name, and class
         * name associated with the closure action. This information can be valuable for debugging
         * and understanding the sequence of window operations.
         *
         * @param event the {@link WebWindowEvent} representing the window closing event.  This event
         *              object provides context about the closed window.
         */
        @Override
        public void webWindowClosed(final WebWindowEvent event) {
            this.recorder_.addRecord(new ClientActionRecord(this.methodName_,
                    this.className_,
                    System.currentTimeMillis(),
                    WindowActionRecordingStrategy.WEB_WINDOW_CLOSED));
        }
    }
}
