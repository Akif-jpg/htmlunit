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
package org.htmlunit.reporter.formatter;

import java.util.Map;
import java.util.UUID; // Needed for unique IDs for htmlSnapshot

/**
 * Enum representing the different formatting states used in an HTML formatting context.
 * Each state corresponds to a specific type of HTML output or behavior, allowing
 * for customization of the formatting process based on the current state.
 * The states include standard logging levels such as success, info, debug, and warning,
 * as well as specific states for actions like web client interactions or generating HTML snapshots.
 *
 * Each state implements the abstract method {@code buildHtmlElement}, which is responsible
 * for constructing an HTML element representation based on the current state and the provided arguments.
 * This design facilitates consistent and state-specific HTML generation.
 *
 * @author Akif Esad
 */
public enum HtmlFormatterState {
    /**
     * A variable that represents a formatted action for web client interactions within the application.
     * The `webClientAction` is primarily used to generate and style log entries for web client-related
     * activity, treating them as informational events for consistency in the user interface.
     *
     * The implementation relies on the `buildHtmlElement` method, which constructs an HTML
     * representation of a web client action, with attributes like timestamp and message
     * extracted from the provided data.
     *
     * The content generated is expected to adhere to a standardized format, ensuring visual
     * cohesion and clear representation in the application's context of logs or reports.
     * Any missing or null data, such as the timestamp or message, is substituted with default
     * placeholder values to prevent rendering issues.
     */
    webClientAction {
        /**
         * Builds an HTML element for a web client action, typically styled as an 'info' entry.
         * Expects 'getTimestamp' and 'getMessage' keys in the args map.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting keys like 'getTimestamp' and 'getMessage'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            // Treat web client actions like info logs for styling
            final String timestamp = args.getOrDefault("getTimestamp", "N/A");
            final String message = args.getOrDefault("getMessage", "Web client action occurred.");

            html.append("        "
                    + "<div class=\"log-entry\" data-type=\"info\">\n"); // Using 'info' style
            html.append("            "
                    + "<span class=\"timestamp\">").append(escapeHtml(timestamp)).append("</span>\n");
            html.append("            "
                    + "<span class=\"log-message\">").append(escapeHtml(message)).append("</span>\n");
            html.append("        "
                    + "</div>\n");
        }
    },
    /**
     * Represents the functionality for building an HTML element specifically designed to present
     * an HTML snapshot. This feature encapsulates both "View Code" and "View Page" interactive options to provide
     * a dynamic representation of HTML content.
     *
     * The method expects specific content such as a timestamp, optional message, and raw HTML content,
     * leveraging these inputs to construct a structured and interactive display. Unique IDs are generated
     * dynamically for toggling visibility of the HTML code and rendered HTML content.
     *
     * This component is primarily intended for debugging or logging environments where captured HTML snapshots
     * need to be displayed in a formatted and accessible way.
     */
    htmlSnapshot {
        /**
         * Builds an HTML element for an HTML snapshot, including interactive "View Code" and "View Page" options.
         * Expects 'getTimestamp', 'getMessage' (optional description), and 'getHtmlContent' keys in the args map.
         * Generates unique IDs for interactive elements.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting 'getTimestamp', 'getMessage', 'getHtmlContent'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            final String timestamp = args.getOrDefault("getTimestamp", "N/A");
            final String message = args.getOrDefault("getMessage", "HTML Snapshot Captured"); // Default message
            final String snapshotContent = args.get("getHtmlContent"); // Raw HTML content

            // Generate unique IDs for toggling code view and iframe
            final String uniqueId = "snapshot-" + UUID.randomUUID().toString().substring(0, 8);
            final String codeViewId = "code-" + uniqueId;
            final String pageViewId = "page-" + uniqueId;
            final String codeToggleId = "toggle-code-" + uniqueId;
            final String pageToggleId = "toggle-page-" + uniqueId;

            html.append(DOUBLE_TAB
                    + "<div class=\"log-entry\" data-type=\"debug\">\n"); // Style as debug or a custom type
            html.append(DOUBLE_TAB + TAB_CHARACTER
                    + "<span class=\"timestamp\">").append(escapeHtml(timestamp)).append("</span>\n");
            html.append(DOUBLE_TAB + TAB_CHARACTER
                            + "<span class=\"log-message\">")
                    .append(escapeHtml(message))
                    .append(" ") // Add space before buttons
                    // --- View Code Button ---
                    .append("<button id=\"").append(codeToggleId).append("\" ")
                    .append("onclick=\"toggleVisibility('").append(codeViewId).append("', this);\">")
                    .append("View Code</button>")
                    .append(" ") // Add space between buttons
                    // --- View Page Button ---
                    .append("<button id=\"").append(pageToggleId).append("\" ")
                    .append("onclick=\"toggleVisibility('").append(pageViewId).append("', this);\">")
                    .append("View Page</button>")
                    .append("</span>\n");

            // --- Collapsible Code View ---
            html.append(DOUBLE_TAB + TAB_CHARACTER
                    + "<div id=\"").append(codeViewId).append("\" style=\"display:none; margin-top: 10px;\">\n");
            html.append("                "
                    + "<pre><code style=\"white-space: pre-wrap; word-break: break-all;\">");
            // IMPORTANT: Escape the snapshot content thoroughly for displaying as code
            html.append(
                    escapeHtml(snapshotContent != null ? snapshotContent
                                    :
                                    "<!-- No snapshot content available -->"
                            )
            );

            html.append("</code></pre>\n");
            html.append(DOUBLE_TAB + TAB_CHARACTER
                    + "</div>\n");

            // --- Collapsible Page View (Iframe) ---
            html.append(DOUBLE_TAB + TAB_CHARACTER
                    + "<div id=\"").append(pageViewId).append("\" style=\"display:none; margin-top: 10px;\">\n");
            html.append(DOUBLE_TAB + DOUBLE_TAB
                    + "<iframe style=\"width: 95%; height: 400px; border: 1px solid #ccc;\" ");
            // Use srcdoc to embed the HTML directly. Avoids issues with external resources,
            // if the base URL isn't set correctly.
            // IMPORTANT: Do NOT escape the content going into srcdoc, it needs to be raw HTML.
            // Add sandbox for security, adjust permissions as needed (e.g., allow-scripts if required).
            html.append(" sandbox=\"allow-same-origin allow-scripts\" "); // Adjust sandbox as needed
            html.append(" srcdoc=\"")
                    .append(escapeHtmlAttribute(
                            snapshotContent != null ? snapshotContent
                                    :
                                    "<html><body>No snapshot content available</body></html>"))
                    .append("\">");
            html.append("</iframe>\n");
            html.append(DOUBLE_TAB + TAB_CHARACTER
                    + "</div>\n");
            html.append(DOUBLE_TAB
                            + "</div>\n");

            // Add simple JavaScript for toggling visibility (needs to be included once in the main HTML <script> tag)
            // Ensure a function like this exists in the final HTML's <script> block:
            /*
            function toggleVisibility(elementId, button) {
                var element = document.getElementById(elementId);
                if (element) {
                    if (element.style.display === 'none') {
                        element.style.display = 'block';
                        button.textContent = button.textContent.replace('View', 'Hide');
                    } else {
                        element.style.display = 'none';
                         button.textContent = button.textContent.replace('Hide', 'View');
                    }
                }
            }
            */
        }
    },
    /**
     * A formatter implementation for generating an HTML representation of success log entries.
     * The implementation expects specific keys such as 'getTimestamp' and 'getMessage' to be
     * present in the provided argument map for constructing the HTML content.
     */
    success {
        /**
         * Builds an HTML element for a success log entry.
         * Expects 'getTimestamp' and 'getMessage' keys in the args map.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting 'getTimestamp' and 'getMessage'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            buildStandardLogEntry(html, args, "success");
        }
    },
    /**
     * A formatter implementation for generating an HTML representation of success log entries.
     * The implementation expects specific keys such as 'getTimestamp' and 'getMessage' to be
     * present in the provided argument map for constructing the HTML content.
     */
    info {
        /**
         * Builds an HTML element for an info log entry.
         * Expects 'getTimestamp' and 'getMessage' keys in the args map.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting 'getTimestamp' and 'getMessage'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            buildStandardLogEntry(html, args, "info");
        }
    },
    /**
     * A formatter implementation for generating an HTML representation of success log entries.
     * The implementation expects specific keys such as 'getTimestamp' and 'getMessage' to be
     * present in the provided argument map for constructing the HTML content.
     */
    debug {
        /**
         * Builds an HTML element for a debug log entry.
         * Expects 'getTimestamp' and 'getMessage' keys in the args map.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting 'getTimestamp' and 'getMessage'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            buildStandardLogEntry(html, args, "debug");
        }
    },
    /**
     * A formatter implementation for generating an HTML representation of success log entries.
     * The implementation expects specific keys such as 'getTimestamp' and 'getMessage' to be
     * present in the provided argument map for constructing the HTML content.
     */
    warning {
        /**
         * Builds an HTML element for a warning log entry.
         * Expects 'getTimestamp' and 'getMessage' keys in the args map.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting 'getTimestamp' and 'getMessage'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            buildStandardLogEntry(html, args, "warning");
        }
    },
    /**
     * A formatter implementation for generating an HTML representation of success log entries.
     * The implementation expects specific keys such as 'getTimestamp' and 'getMessage' to be
     * present in the provided argument map for constructing the HTML content.
     */
    error {
        /**
         * Builds an HTML element for an error log entry.
         * Expects 'getTimestamp' and 'getMessage' keys in the args map.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting 'getTimestamp' and 'getMessage'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            buildStandardLogEntry(html, args, "error");
        }
    },
    /**
     * A formatter implementation for generating an HTML representation of success log entries.
     * The implementation expects specific keys such as 'getTimestamp' and 'getMessage' to be
     * present in the provided argument map for constructing the HTML content.
     */
    record {
        /**
         * Builds an HTML element for a generic record (Js, Css, Html, Junit).
         * Styles it as a 'debug' entry by default.
         * Expects 'getTimestamp' and 'getMessage' keys in the args map.
         * You might enhance this to use different 'data-type' based on the original RecordType if needed.
         *
         * @param html the StringBuilder instance used to construct and append the HTML content.
         * @param args the map of data extracted from the IRecord, expecting 'getTimestamp' and 'getMessage'.
         */
        @Override
        void buildHtmlElement(final StringBuilder html, final Map<String, String> args) {
            // Default styling for generic records, using 'debug' type.
            // You could potentially inspect args further to refine the data-type if needed.
            buildStandardLogEntry(html, args, "debug");
        }
    };

    private static final String TAB_CHARACTER = "\t";
    private static final String DOUBLE_TAB = "\t\t";
    /**
     * Common helper method to build a standard log entry structure.
     *
     * @param html      The StringBuilder to append to.
     * @param args      The argument map containing 'getTimestamp' and 'getMessage'.
     * @param dataType  The value for the 'data-type' attribute (e.g., "info", "error").
     */
    protected void buildStandardLogEntry(final StringBuilder html,
                                         final Map<String, String> args,
                                         final String dataType) {
        // Retrieve data using expected keys (assuming args uses method names as keys)
        final String timestamp = args.getOrDefault("timestamp", "N/A"); // Default if timestamp is missing
        final String message = args.getOrDefault("getContext", "");        // Default if message is missing

        html.append(DOUBLE_TAB
                + "<div class=\"log-entry\" data-type=\"").append(dataType).append("\">\n");
        html.append(DOUBLE_TAB + TAB_CHARACTER
                + "<span class=\"timestamp\">").append(escapeHtml(timestamp)).append("</span>\n");
        html.append(DOUBLE_TAB + TAB_CHARACTER
                + "<span class=\"log-message\">").append(escapeHtml(message)).append("</span>\n");
        html.append(DOUBLE_TAB
                + "</div>\n");
    }

    /**
     * Constructs an HTML element representation based on the provided arguments.
     * The method processes the provided HTML content and arguments to create a complete
     * HTML element that reflects the current formatting state.
     *
     * @param html the StringBuilder instance used to construct and append the HTML content
     * @param args the map of attributes or data to be included in the HTML element, where
     *             keys represent attribute names (assumed to be getter method names like 'getTimestamp')
     *             and values represent the corresponding data as strings.
     */
    abstract void buildHtmlElement(StringBuilder html, Map<String, String> args);

    /**
     * Escapes characters that have special meaning in HTML text content.
     *
     * @param value The string to escape.
     * @return The escaped string, safe for inclusion in HTML content.
     */
    public String escapeHtml(final String value) {
        if (value == null) {
            return ""; // Return empty string for null to avoid "null" text
        }

        final StringBuilder escaped = new StringBuilder();
        for (final char c : value.toCharArray()) {
            switch (c) {
                case '&':
                    escaped.append("&");
                    break;
                case '<':
                    escaped.append("<");
                    break;
                case '>':
                    escaped.append(">");
                    break;
                // Do not escape quotes for general content, only for attributes
                default:
                    escaped.append(c);
                    break;
            }
        }
        return escaped.toString();
    }

    /**
     * Escapes characters that have special meaning in HTML attributes, particularly quotes.
     * Needed for values placed inside "" like srcdoc="...".
     *
     * @param value The string to escape for use within an HTML attribute value.
     * @return The escaped string, safe for inclusion in HTML attributes.
     */
    public String escapeHtmlAttribute(final String value) {
        if (value == null) {
            return ""; // Return empty string for null
        }
        final StringBuilder escaped = new StringBuilder();
        for (final char c : value.toCharArray()) {
            switch (c) {
                case '&':
                    escaped.append("&");
                    break;
                case '<':
                    escaped.append("<");
                    break;
                case '>':
                    escaped.append(">");
                    break;
                case '"':
                    escaped.append("\""); // Escape double quotes
                    break;
                case '\'':
                    escaped.append("'"); // Escape single quotes
                    break;
                default:
                    escaped.append(c);
                    break;
            }
        }
        return escaped.toString();
    }
}
