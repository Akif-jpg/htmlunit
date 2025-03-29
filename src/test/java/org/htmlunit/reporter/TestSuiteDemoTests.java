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
package org.htmlunit.reporter;

import static org.junit.Assert.assertEquals;

import org.htmlunit.reporter.recordannotation.RecordTest;
import org.htmlunit.reporter.recordannotation.RecordTestClass;
import org.htmlunit.reporter.recordannotation.RecordableTestClass;
import org.htmlunit.reporter.recorder.RecorderManager;
import org.junit.Test;

/**
 * Test class for demonstration purposes.
 *
 * @author Akif Esad
 */
@RecordableTestClass
@RecordTestClass
public class TestSuiteDemoTests {

    /**
     * Test method that performs some operation.
     */
    @Test
    public void testMethod() {
        RecorderManager.obtainRecorder("testId", "output.html", true);
        System.out.println("testMethod");
        assertEquals(0, 4);
    }

    /**
     * Test method that performs some operation.
     */
    @Test
    @RecordTest
    public void testMethod2() {
        RecorderManager.obtainRecorder("testId", "output.html", true);
        System.out.println("testMethod2");
        assertEquals(0, 0);
    }

    /**
     * Test method that performs some operation.
     */
    public void tesMethod3() {
        System.out.println("testMethod3");
    }
}
