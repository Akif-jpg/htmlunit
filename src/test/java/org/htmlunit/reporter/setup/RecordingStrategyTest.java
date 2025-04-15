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
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecordingStrategyTest {

    private static final String RECORD_ID = "org.htmlunit.myclass#mymethod";
    private static final String CLASS_NAME = "myclass";
    private static final String METHOD_NAME = "mymethod";

    // Dummy concrete class for testing abstract class methods.
    private static class TestRecordingStrategy extends RecordingStrategy {
        TestRecordingStrategy(String recordId) {
            super(recordId);
        }

        /**
         * Prepares the given WebClient with custom configurations and registers it for use.
         *
         * @param webClient the WebClient instance to be configured and registered
         */
        @Override
        void prepareAndRegister(WebClient webClient) {

        }

    }


    @Test
    public void testGetMethodNameFromRecordId() {
        RecordingStrategy strategy = new TestRecordingStrategy(RECORD_ID);
        String methodName = strategy.getMethodNameFromRecordId(RECORD_ID);
        assertEquals(METHOD_NAME, methodName);
    }


    @Test
    public void testGetClassNameFromRecordId() {
        RecordingStrategy strategy = new TestRecordingStrategy(RECORD_ID);
        String className = strategy.getClassNameFromRecordId(RECORD_ID);
        assertEquals(CLASS_NAME, className);
    }

    @Test
    public void testGetMethodNameFromRecordId_NoHash() {
        RecordingStrategy strategy = new TestRecordingStrategy("org.htmlunit.myclass");
        String methodName = strategy.getMethodNameFromRecordId("org.htmlunit.myclass");
        assertEquals("", methodName); // or consider throwing an exception for invalid format
    }

    @Test
    public void testGetClassNameFromRecordId_NoHash() {
        RecordingStrategy strategy = new TestRecordingStrategy("org.htmlunit.myclass");
        String className = strategy.getClassNameFromRecordId("org.htmlunit.myclass");
        assertEquals("myclass", className);
    }

    @Test
    public void testGetClassNameFromRecordId_MultipleDots() {
        RecordingStrategy strategy = new TestRecordingStrategy("org.htmlunit.more.packages.myclass#mymethod");
        String className = strategy.getClassNameFromRecordId("org.htmlunit.more.packages.myclass#mymethod");
        assertEquals("myclass", className);
    }



}