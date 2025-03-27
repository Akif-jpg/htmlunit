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

import org.junit.Test;

import java.util.Properties;
import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the EnvironmentReader class.
 *
 * @author Akif Esad
 */
public class EnvironmentReaderTest {

    /**
     * Tests the functionality of reading environment configurations from the default environment file.
     *
     * This method ensures the environment file does not exist before starting the test by deleting it
     * if it exists. It then reads the properties from the environment file using the
     * {@link EnvironmentReader#getInstance()} and {@link EnvironmentReader#getProperties_()} methods.
     *
     * The method verifies that the expected properties, such as the "appendMode" property, are read correctly.
     * After the test, it ensures any created environment file is deleted to maintain a clean state.
     *
     * Assertions:
     * - Verifies that the "appendMode" property in the environment file is set to "on".
     *
     * @author Akif Esad
     */
    @Test
    public void testReadEnvFile() {
        // Ensure the environment file does not exist before the test
        final File envFile = new File(EnvironmentReader.DEFAULT_ENV_FILE);
        if (envFile.exists()) {
            envFile.delete();
        }

        // Read the environment file
        final Properties properties = EnvironmentReader.getInstance().getProperties_();
        // Verify the properties
        assertEquals("on", properties.getProperty("appendMode"));

        if (envFile.exists()) {
            envFile.delete();
        }
    }
}
