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

import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.util.List;

/**
 * <p>Executes the test plan with the provided selectors.</p>
 * @author Akif Esad
 */
public class TestPlanExecutor {
    private final Launcher launcher_;

    /**
     * Constructs a new TestPlanExecutor instance.
     */
    public TestPlanExecutor() {
        launcher_ = LauncherFactory.create();
    }

    /**
     * Executes the test plan using the provided discovery selectors.
     *
     * @param selectors List of discovery selectors to be executed.
     */
    private void executeTestPlan(final List<DiscoverySelector> selectors) {
        // Create launcher discovery request using the provided selectors.
        final LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectors)
                .build();

        // Discover the test plan
        final TestPlan testPlan = launcher_.discover(request);

        // Register the summary listener.
        launcher_.registerTestExecutionListeners(new RecordTestExecutionListener());

        // Execute the test plan.
        launcher_.execute(request);
    }

    /**
     * Public method to trigger test execution.
     *
     * @param selectors List of discovery selectors to be executed
     */
    public void runTests(final List<DiscoverySelector> selectors) {
        executeTestPlan(selectors);
    }
}
