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

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scanner class for discovering and listing test classes.
 * @author Akif Esasd
 */
public class TestClassScanner {
    private static final Logger LOG = LoggerFactory.getLogger(TestClassScanner.class);

    /**
     * Scans and prints all test classes found in the specified package.
     */
    public void scanTests() {
        // Update with your test package name
        final String testPackage = "org.htmlunit.suite";

        final LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectPackage(testPackage))
                .build();

        final Launcher launcher = LauncherFactory.create();
        final TestPlan testPlan = launcher.discover(request);

        // List all tests found in the test plan
        testPlan.getRoots().forEach(root -> {
            testPlan.getDescendants(root)
                    .stream()
                    .filter(testIdentifier -> testIdentifier.isTest())
                    .forEach(testIdentifier -> {
                        // Print only test names
                        LOG.info("Test name: {}", testIdentifier.getUniqueId());
                    });
        });
    }
}

