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

package org.htmlunit.util.mocks;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import org.htmlunit.util.NameValuePair;

/**
 * Simple mock for {@link WebResponse}.
 *
 * @author Ronald Brill
 */
public class WebResponseMock extends WebResponse {
    private Map<String, String> headers_;

    private Map<String, Integer> callCounts_ = new HashMap<>();

    /**
     * Ctor.
     * @param request the request
     * @param headers the headers
     */
    public WebResponseMock(final WebRequest request, final Map<String, String> headers) {
        super(null, request, 0);

        if (headers == null) {
            headers_ = new HashMap<>();
        }
        else {
            headers_ = headers;
        }
    }

    @Override
    public InputStream getContentAsStream() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getContentAsString() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Charset getContentCharset() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getContentType() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public long getLoadTime() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public List<NameValuePair> getResponseHeaders() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getResponseHeaderValue(final String headerName) {
        count("getResponseHeaderValue");
        for (final Map.Entry<String, String> pair : headers_.entrySet()) {
            if (pair.getKey().equalsIgnoreCase(headerName)) {
                return pair.getValue();
            }
        }
        return null;
    }

    @Override
    public int getStatusCode() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getStatusMessage() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public WebRequest getWebRequest() {
        count("getWebRequest");
        return super.getWebRequest();
    }

    @Override
    public void cleanUp() {
        count("cleanUp");
        super.cleanUp();
    }

    /**
     * @param method the method name
     * @return the number of call for the given method name
     */
    public int getCallCount(final String method) {
        return callCounts_.getOrDefault(method, 0);
    }

    private void count(final String method) {
        callCounts_.put(method, getCallCount(method) + 1);
    }
}
