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
package org.htmlunit.reporter.recorder;

import org.htmlunit.reporter.EnvironmentReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A factory class for managing Recorder objects.
 * A static Factory for generating and managing {@link Recorder} type objects
 * This class provides static methods to add, retrieve, remove, check, and clear
 * Recorder objects stored in a static map. It is designed to prevent instantiation.
 * Usage example:
 * RecorderManager.addRecorder("key1", new Recorder());
 * Recorder recorder = RecorderManager.getRecorder("key1");
 * boolean exists = RecorderManager.containsRecorder("key1");
 * RecorderManager.removeRecorder("key1");
 * Map<> allRecorders = RecorderManager.getAllRecorders();
 * RecorderManager.clearAllRecorders();
 * @author Akif Esad
 */
public final class RecorderManager {
    // Static map to store recorders.
    private static final Map<String, Recorder> RECORDER_MAP = new HashMap<>();

    // Private constructor to prevent instantiation.
    private RecorderManager() {
        throw new AssertionError("RecorderManager cannot be instantiated");
    }

    /**
     * If recorder not exists generates a new recorder and adds it to the static map.
     * Returns the recorder associated with the key.
     * @param recordId The unique identifier for the recorder
     * @return {@link Recorder} The newly created recorder
     */
    public static Recorder obtainRecorder(final String recordId) {
        if (!containsRecorder(recordId)) {
            final Recorder recorder;
            final EnvironmentReader environmentReader = EnvironmentReader.getInstance();
            final String recorderFormat = environmentReader.getProperty(EnvironmentReader.OUTPUT_PATH);
            final boolean appendMode = Objects.equals(
                    environmentReader.getProperty(EnvironmentReader.APPEND_MODE),
                    "True"
            );
            final String outputPath = environmentReader.getProperty(EnvironmentReader.OUTPUT_PATH);
            switch (recorderFormat) {
                case "json":
                    recorder = new JsonFormatRecorder(outputPath
                            + "jsonRecords" + recordId, appendMode);
                    break;
                case "xml":
                    recorder = new XmlFormatRecorder(outputPath
                            + "xtmlRecords" + recordId, appendMode);
                    break;
                default:
                    recorder = new HtmlFormatRecorder(outputPath
                            + "xmlRecords" + recordId, appendMode);
            }

            addRecorder(recordId, recorder);
        }
        return getRecorder(recordId);
    }
    /**
     * Adds a recorder to the static map.
     *
     * @param key The unique identifier for the recorder
     * @param recorder The recorder to be added
     */
    private static void addRecorder(final String key, final Recorder recorder) {
        RECORDER_MAP.put(key, recorder);
    }

    /**
     * Retrieves a recorder by its key.
     *
     * @param key The unique identifier of the recorder
     * @return The recorder associated with the key, or null if not found
     */
    public static Recorder getRecorder(final String key) {
        return RECORDER_MAP.get(key);
    }

    /**
     * Removes a recorder from the static map.
     *
     * @param key The unique identifier of the recorder to remove
     */
    public static void removeRecorder(final String key) {
        RECORDER_MAP.remove(key);
    }

    /**
     * Checks if a recorder exists with the given key.
     *
     * @param key The unique identifier to check
     * @return true if the recorder exists, false otherwise
     */
    public static boolean containsRecorder(final String key) {
        return RECORDER_MAP.containsKey(key);
    }

    /**
     * Returns a copy of all current recorders.
     *
     * @return A map of all current recorders
     */
    public static Map<String, Recorder> getAllRecorders() {
        return new HashMap<>(RECORDER_MAP);
    }

    /**
     * Clears all recorders from the static map.
     */
    public static void clearAllRecorders() {
        RECORDER_MAP.clear();
    }
}
