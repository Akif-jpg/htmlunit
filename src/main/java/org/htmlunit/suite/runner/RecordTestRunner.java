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
package org.htmlunit.suite.runner;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * This class is responsible for running test suites and recording their execution.
 * @author Akif Esad
 */
public class RecordTestRunner {
    private static final Logger LOGGER = Logger.getLogger(RecordTestRunner.class.getName());
    private final TestClassScanner scanner_;

    /**
     * Constructs a new RecordTestRunner instance.
     */
    public RecordTestRunner() {
        scanner_ = new TestClassScanner();
    }

    /**
     * Main entry point for the test runner.
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        LOGGER.info("RecordTestRunner is running...");
        Arrays.stream(args).forEach(arg -> LOGGER.info(arg));
        final RecordTestRunner runner = new RecordTestRunner();
        runner.scanner_.scanTests();
    }
}
