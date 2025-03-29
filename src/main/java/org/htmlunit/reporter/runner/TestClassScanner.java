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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Matcher;

import org.htmlunit.reporter.recordannotation.RecordTest;
import org.htmlunit.reporter.recordannotation.RecordTestClass;
import org.htmlunit.reporter.recordannotation.RecordableTestClass;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

/**
 * Scanner class for discovering test class has annotations.
 *
 * @author Akif Esasd
 */
public class TestClassScanner implements IPlanMaker {
    /**
     * Scans and prints all test classes found in the specified package.
     *
     * @param testPackage Package to scan for test classes
     * @return List of specied test classes with {@link RecordTest} and {@link RecordableTestClass}
     *         annotations
     */
    public List<DiscoverySelector> scanTests(final String testPackage) {
        final LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectPackage(testPackage))
                .build();

        final Launcher launcher = LauncherFactory.create();
        final TestPlan testPlan = launcher.discover(request);

        // List all tests found in the test plan
        return testPlan.getRoots().stream()
                .flatMap(root -> testPlan.getDescendants(root).stream())
                .map(testIdentifier -> extractClassName(testIdentifier.getUniqueId())) // Extract class name
                .filter(Objects::nonNull)
                .distinct() // Avoid adding the same class twice
                .flatMap(className -> {
                    try {
                        final Class<?> testClass = Class.forName(className);

                        // Check if the class has the RecordableTestClass annotation
                        if (testClass.isAnnotationPresent(RecordableTestClass.class)) {
                            // If class annototated with RecordTestClass then return all class.
                            if (testClass.isAnnotationPresent(RecordTestClass.class)) {
                                return Stream.of(DiscoverySelectors.selectClass(className));
                            }
                            // Use reflection to get methods with RecordTest annotation
                            return Arrays.stream(testClass.getDeclaredMethods())
                                    .filter(method -> method.isAnnotationPresent(RecordTest.class))
                                    .map(method -> DiscoverySelectors.selectMethod(testClass, method.getName()));
                        }

                        return Stream.empty();
                    }
                    catch (final ClassNotFoundException e) {
                        e.printStackTrace();
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());

    }

    /**
     * Extracts the class name from the unique identifier of a test method.
     *
     * @param uniqueId Unique identifier of the test method
     * @return Class name extracted from the unique identifier
     */
    private static String extractClassName(final String uniqueId) {
        final Pattern pattern = Pattern.compile("testMethod\\((.*?)\\)");
        final Matcher matcher = pattern.matcher(uniqueId);
        if (matcher.find()) {
            final String className = matcher.group(1);
            return className;
        }
        return null;
    }

}
