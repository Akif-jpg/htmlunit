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
package org.htmlunit.reporter.runner;

import org.junit.platform.engine.DiscoverySelector;

import java.util.List;

/**
 * Interface for creating a test plan.
 *
 * @author Akif Esad
 */
public interface IPlanMaker {
    /**
     * Scans the provided test package for tests.
     * @param testPackage The package to scan for tests.
     * @return List of discovery selectors for the tests.
     */
    List<DiscoverySelector> scanTests(String testPackage);
}
