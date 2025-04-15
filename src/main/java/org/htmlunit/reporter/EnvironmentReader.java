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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class for reading environment variables from a configuration file.
 *
 * @author Akif Esad
 */
public final class EnvironmentReader {

    // --- Configuration Property Keys ---
    // These constants define the keys expected in the environment configuration.

    /**
     * The property key used to specify the directory path where output files should be generated.
     * Example value: "./ReporterRecords"
     */
    public static final String OUTPUT_PATH = "output.path";

    /**
     * The property key used to define the desired format for the output files (e.g., "json", "xml", "csv").
     * Example value: "json"
     */
    public static final String OUTPUT_FORMAT = "output.format";

    /**
     * The property key used to determine whether output should be appended to existing files
     * or if existing files should be overwritten. Expected values are typically "True" or "False".
     * Example value: "False"
     */
    public static final String APPEND_MODE = "output.append";

    /**
     * The property key used to specify the base package that should be scanned,
     * often used for discovering classes (e.g., during test execution).
     * Example value: "com.example.tests"
     */
    public static final String SCAN_PACKAGE = "scan.package";

    // --- Logger ---

    /**
     * Logger instance for logging messages related to the EnvironmentReader's operations.
     * Useful for debugging configuration loading issues.
     */
    private static final Logger LOGGER = Logger.getLogger(EnvironmentReader.class.getName());

    // --- Instance Variables ---

    /**
     * Represents the configuration file (e.g., ".env", "config.properties") from which
     * environment settings might be loaded. This needs to be initialized, perhaps in a constructor
     * or a dedicated loading method.
     */
    private File envFile_; // Should be initialized appropriately

    // --- Default Settings ---

    /**
     * A string defining the default configuration settings in a key=value format, separated by newlines.
     * These defaults are used as a fallback if a configuration file is not provided, not found,
     * or if specific keys are missing within the file.
     *
     * Note: The 'scan.package' property is intentionally left empty in the default configuration.
     * It typically requires an explicit value based on the project structure.
     */
    private static final String DEFAULT_ENV_SETTINGS = OUTPUT_FORMAT
                    + "=json\n"
                    + APPEND_MODE + "=False\n"
                    + OUTPUT_PATH + "=./ReporterRecords\n"
                    + SCAN_PACKAGE + "=";

    /** Default environment file save path. */
    public static final String DEFAULT_ENV_FILE = "htmlunit-reporter.env";

    private static volatile EnvironmentReader Instance_;
    private final Properties properties_;
    private final String envFilePath_;

    private EnvironmentReader() {
        this(DEFAULT_ENV_FILE);
    }

    private EnvironmentReader(final String envFilePath) {
        this.envFilePath_ = envFilePath;
        this.properties_ = new Properties();
        initializeEnvironmentFile();
        loadProperties();
    }

    private void initializeEnvironmentFile() {
        this.envFile_ = new File(envFilePath_);

        if (!isEnvFileExist()) { // If environment file not exist
            try {
                // Use Path to handle directory creation more safely
                final Path filePath = Paths.get(envFilePath_);

                // Create parent directories if they exist
                if (filePath.getParent() != null) {
                    Files.createDirectories(filePath.getParent());
                }

                // Create the file
                Files.createFile(filePath);

                // Write default settings
                try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                    writer.write(DEFAULT_ENV_SETTINGS);
                }
            }
            catch (final IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to create environment file: " + envFilePath_, e);
            }
        }
    }

    /**
     * Check file created before or not.
     *
     * @return boolean environment file exist or not
     */
    public boolean isEnvFileExist() {
        return this.envFile_.exists();
    }

    /**
     * Delete current environment file.
     *
     * @return result of process.
     */
    public boolean deleteEnvFile() {
        return this.envFile_.delete();
    }

    private void loadProperties() {
        try (Reader reader = new FileReader(envFilePath_)) {
            properties_.load(reader);
        }
        catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load properties from: " + envFilePath_, e);
        }
    }

    /**
     * Returns the singleton instance of the EnvironmentReader.
     *
     * @return The singleton instance of the EnvironmentReader
     */
    public static synchronized EnvironmentReader getInstance() {
        if (Instance_ == null) {
            Instance_ = new EnvironmentReader();
        }
        return Instance_;
    }

    /**
     * @param customEnvFilePath The path to the custom environment file
     * @return The singleton instance of the EnvironmentReader
     */
    public static synchronized EnvironmentReader getInstance(final String customEnvFilePath) {
        if (Instance_ == null) {
            Instance_ = new EnvironmentReader(customEnvFilePath);
        }
        return Instance_;
    }

    /**
     * Returns a copy of the properties.
     *
     * @return A copy of the properties
     */
    public Properties getProperties_() {
        return new Properties(properties_);  // Return a copy to prevent direct modification
    }

    /**
     * @param key The key of the property to retrieve
     * @return The value of the property with the given key
     */
    public String getProperty(final String key) {
        return properties_.getProperty(key);
    }

    /**
     * @param key          The key of the property to retrieve
     * @param defaultValue The default value to return if the key is not found
     * @return The value of the property with the given key, or the default value if the key is not found
     */
    public String getProperty(final String key, final String defaultValue) {
        return properties_.getProperty(key, defaultValue);
    }
}
