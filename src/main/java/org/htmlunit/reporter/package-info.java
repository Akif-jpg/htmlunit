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

/**
 * Provides a plugin for automated test recording using HTMLUnit and custom annotations.
 * <p>
 * This plugin enables the recording of various aspects of web application tests,
 * including HTML snapshots, JavaScript outputs, and unit test results.
 * The recordings are intended for regression analysis and documentation purposes.
 * </p>
 * <h3>Key Features:</h3>
 * <ul>
 * <li><strong>HTMLUnit Integration:</strong> Leverages the HTMLUnit library for interacting with web pages.</li>
 * <li><strong>Custom Annotations:</strong>
 * <ul>
 * <li>{@code @RecordableTestClass}: Marks a test class for automatic recording.</li>
 * <li>{@code @RecordTest}: Annotates a test method to enable recording during its execution.</li>
 * </ul>
 * </li>
 * <li><strong>Snapshot Recording:</strong> Captures HTML snapshots of web pages during test execution.</li>
 * <li><strong>JavaScript Output Logging:</strong> Records the output of JavaScript executed on the web pages under test.</li>
 * <li><strong>Unit Test Result Recording:</strong> Logs the results and metadata of the executed unit tests.</li>
 * <li><strong>Structured Recording Management:</strong> Organizes recording data using POJO models within the {@code record} package and manages the recording process through the logic in the {@code recorder} package.</li>
 * </ul>
 * <p>
 * To utilize this plugin, annotate your test classes with {@code @RecordableTestClass}
 * and the specific test methods you want to record with {@code @RecordTest}.
 * Ensure that the {@link org.htmlunit.reporter.recorder.RecorderManager} is used to initialize the recording context,
 * typically within a {@code @BeforeEach} setup method.
 * </p>
 *
 * @see org.htmlunit.reporter.recorder.RecorderManager
 * @see org.htmlunit.reporter.recorder.Recorder
 * @author Akif Esad
 */
package org.htmlunit.reporter;
