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

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class responsible for configuring and building an instance of {@code WebClient}
 * with custom configurations provided by {@code RecordingStrategy} instances.
 * <p>
 * This class allows the aggregation of multiple strategies that modify the configuration
 * of a {@code WebClient} and applies these strategies during the build process.
 *
 * @author Akif Esad
 */
public class RecorderWebClientBuilder {
    private final List<RecordingStrategy> strategies_;
    private final String recordId_;

    /**
     * Constructs a new instance of {@code RecorderWebClientBuilder}.
     *
     * Initializes an internal list to hold {@code RecordingStrategy} instances.
     * These strategies can be used to configure and modify the behavior of a {@code WebClient}
     * during the build process.
     *
     * @param recordId The unique identifier for the recording session, used to associate
     *                  strategies and configurations with a specific recording instance.
     */

    public RecorderWebClientBuilder(final String recordId) {
        strategies_ = new ArrayList<>();
        this.recordId_ = recordId;
    }

    /**
     * Adds a {@link WindowActionRecordingStrategy} to the list of strategies.
     * This strategy records user interactions with web windows, such as opening, closing,
     * and resizing, during the web browsing session. The recorded actions are associated
     * with the provided {@code recordId}.
     *
     * @return this {@code RecorderWebClientBuilder} instance to allow method chaining
     */
    public RecorderWebClientBuilder addWebWindowActionRecordingStrategy() {
        strategies_.add(new WindowActionRecordingStrategy(this.recordId_));
        return this;
    }

    /**
     * Creates and returns a new instance of WebClient.
     * The method initializes the WebClient and applies
     * a list of strategies to prepare and register the client
     * before it is returned.
     *
     * @return a configured WebClient instance
     */
    public WebClient build() {
        final WebClient webClient = new WebClient();

        for (final RecordingStrategy strategy : strategies_) {
            strategy.prepareAndRegister(webClient);
        }

        return webClient;
    }
}
