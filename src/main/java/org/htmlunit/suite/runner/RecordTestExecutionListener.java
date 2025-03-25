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

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

/**
 *  <p>Listen execution of tests and records test result with provided recorder from {@link org.htmlunit.suite.recorder.RecorderFactory}.</p>
 *  <p>It is a {@link TestExecutionListener} implementation that listens to test execution events and records the test results.</p>
 *  <p>It is used by {@link org.htmlunit.suite.launcher.RecordTestLauncher} to record test results.</p>
 *
 * @author Akif Esad
 */
public class RecordTestExecutionListener implements TestExecutionListener {
    /**
     * Called when the execution of the {@link TestPlan} has started,
     * <em>before</em> any test has been executed.
     *
     * <p>Called from the same thread as {@link #testPlanExecutionFinished(TestPlan)}.
     *
     * @param testPlan describes the tree of tests about to be executed
     */
    @Override
    public void testPlanExecutionStarted(final TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionStarted(testPlan);
    }

    /**
     * Called when the execution of the {@link TestPlan} has finished,
     * <em>after</em> all tests have been executed.
     *
     * <p>Called from the same thread as {@link #testPlanExecutionStarted(TestPlan)}.
     *
     * @param testPlan describes the tree of tests that have been executed
     */
    @Override
    public void testPlanExecutionFinished(final TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionFinished(testPlan);
    }

    /**
     * Called when the execution of a leaf or subtree of the {@link TestPlan}
     * is about to be started.
     *
     * <p>The {@link TestIdentifier} may represent a test or a container.
     *
     * <p>This method will only be called if the test or container has not
     * been {@linkplain #executionSkipped skipped}.
     *
     * <p>This method will be called for a container {@code TestIdentifier}
     * <em>before</em> {@linkplain #executionStarted starting} or
     * {@linkplain #executionSkipped skipping} any of its children.
     *
     * @param testIdentifier the identifier of the started test or container
     */
    @Override
    public void executionStarted(final TestIdentifier testIdentifier) {
        TestExecutionListener.super.executionStarted(testIdentifier);
    }

    /**
     * Called when the execution of a leaf or subtree of the {@link TestPlan}
     * has finished, regardless of the outcome.
     *
     * <p>The {@link TestIdentifier} may represent a test or a container.
     *
     * <p>This method will only be called if the test or container has not
     * been {@linkplain #executionSkipped skipped}.
     *
     * <p>This method will be called for a container {@code TestIdentifier}
     * <em>after</em> all of its children have been
     * {@linkplain #executionSkipped skipped} or have
     * {@linkplain #executionFinished finished}.
     *
     * <p>The {@link TestExecutionResult} describes the result of the execution
     * for the supplied {@code TestIdentifier}. The result does not include or
     * aggregate the results of its children. For example, a container with a
     * failing test will be reported as {@link Status#SUCCESSFUL SUCCESSFUL} even
     * if one or more of its children are reported as {@link Status#FAILED FAILED}.
     *
     * @param testIdentifier      the identifier of the finished test or container
     * @param testExecutionResult the (unaggregated) result of the execution for
     *                            the supplied {@code TestIdentifier}
     * @see TestExecutionResult
     */
    @Override
    public void executionFinished(final TestIdentifier testIdentifier, final TestExecutionResult testExecutionResult) {
        TestExecutionListener.super.executionFinished(testIdentifier, testExecutionResult);
    }
}
