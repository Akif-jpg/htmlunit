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

/**
 * Defines a strategy for preparing and registering custom configurations for a {@link WebClient}.
 * Implementations of this interface specify how a WebClient should be configured before use.
 *
 * @author Akif Esad
 */
public interface WebClientRecorderStrategy {

    /**
     * Prepares the given WebClient with custom configurations and registers it for use.
     *
     * @param webClient the WebClient instance to be configured and registered
     */
    void prepareAndRegister(WebClient webClient);

}
