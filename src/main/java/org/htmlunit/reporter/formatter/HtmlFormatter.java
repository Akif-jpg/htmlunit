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

import org.htmlunit.reporter.record.IRecord;
import org.htmlunit.reporter.record.RecordType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter; // For formatting timestamp
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Formats IRecord instances into a single-page HTML representation
 * displaying logs in a vertical timeline.
 *
 * This formatter generates a complete HTML document including CSS for styling
 * and JavaScript for filtering and searching capabilities, based on a predefined template.
 *
 * @author Akif Esad (Original Concept & Structure Request)
 * @author GPT (Generated HtmlFormatter implementation)
 */
public class HtmlFormatter implements IFormatter {

    // For formatting the timestamp (Can be changed optionally)
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String INDENT_ENTRY = "            "; // Indentation for log entry div

    private HtmlFormatterState state_ = HtmlFormatterState.info;

    /**
     * Formats a list of IRecord objects into a complete HTML page string.
     *
     * @param records      the list of IRecord objects to be formatted
     * @param recorderUUID the unique identifier of the recorder (added as a comment in HTML)
     * @return A String containing the full HTML page representation of the records.
     */
    @Override
    public String format(final List<IRecord> records, final String recorderUUID) {
        final StringBuilder html = new StringBuilder();

        appendHtmlHeader(html);
        appendBodyStartAndHeader(html);
        appendMainContentStart(html, recorderUUID);

        if (records == null || records.isEmpty()) {
            html.append(INDENT_ENTRY).append("<p>No logs found to display.</p>\n"); // English message
        }
        else {
            for (final IRecord record : records) {
                appendSingleRecordHtml(html, record);
            }
        }

        appendMainContentEnd(html);
        appendFooterAndBodyEnd(html);
        appendJavaScript(html);
        appendHtmlFooter(html);

        return html.toString();
    }

    /**
     * Formats a single IRecord object into a complete HTML page string.
     *
     * @param record       the IRecord object to be formatted
     * @param recorderUUID the unique identifier of the recorder (added as a comment in HTML)
     * @return A String containing the full HTML page representation of the record.
     */
    @Override
    public String format(final IRecord record, final String recorderUUID) {
        // To format a single record, call the main format method with a single-element list
        return format(Collections.singletonList(record), recorderUUID);
    }

    /**
     * Appends the HTML head section including CSS styles.
     */
    private void appendHtmlHeader(final StringBuilder html) {
        html.append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n") // Changed lang to "en" for English context
                .append("<head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                .append("    <title>Log Timeline</title>\n") // English title
                .append("    <style>\n")
                // --- Copy CSS Styles Here ---
                .append("        /* General Page Styles */\n")
                .append("        "
                        + "body {"
                        + "font-family: sans-serif;"
                        + "margin: 0;"
                        + "padding: 0;"
                        + "background-color: #f4f7f6;"
                        + "color: #333;"
                        + " display: flex;"
                        + "flex-direction: column;"
                        + "min-height: 100vh; }\n")
                .append("        /* Header Styles */\n")
                .append("        "
                        + ".navbar {"
                        + "background-color: #333;"
                        + "padding: 10px 20px;"
                        + "display: flex;"
                        + "align-items: center;"
                        + "color: #fff;"
                        + "box-shadow: 0 2px 5px rgba(0,0,0,0.2); }\n")
                .append("        .navbar .logo-link {"
                        + "display: flex;"
                        + " align-items: center;"
                        + "text-decoration: none;"
                        + "color: inherit; }\n")
                // Logo path: Must be adjusted based on where this HTML will be saved/served
                .append("        .navbar img { height: 40px; margin-right: 15px; vertical-align: middle; }\n")
                .append("        .navbar h1 { font-size: 1.4em; margin: 0; font-weight: 500; }\n")
                .append("        /* Main Content Area */\n")
                .append("        main { flex-grow: 1; padding: 20px; }\n")
                .append("        /* Control Area Styles */\n")
                .append("        #controls {"
                        + "margin-bottom: 30px;"
                        + "padding: 15px;"
                        + "background-color: #fff;"
                        + "border-radius: 8px;"
                        + "box-shadow: 0 2px 5px rgba(0,0,0,0.1);"
                        + "display: flex;"
                        + "flex-wrap: wrap;"
                        + "gap: 15px;"
                        + " align-items: center; }\n")
                .append("        #controls label { font-weight: bold; margin-right: 5px; }\n")
                .append("        #controls input[type=\"text\"], #controls select "
                        + "{ padding: 8px 12px; border: 1px solid #ccc; border-radius: 4px; font-size: 1em; }\n")
                .append("        #controls input[type=\"text\"] { flex-grow: 1; min-width: 200px; }\n")
                .append("        #filter-controls {"
                        + "display: flex;"
                        + " gap: 10px;"
                        + " align-items: center;"
                        + " flex-wrap: wrap; }\n")
                .append("        #filter-controls label {"
                        + "font-weight: normal;"
                        + "margin-right: 0;"
                        + "display: flex;"
                        + "align-items: center;"
                        + "cursor: pointer; }\n")
                .append("        #filter-controls input[type=\"checkbox\"] { margin-right: 5px; }\n")
                .append("        /* Timeline Container */\n")
                .append("        #timeline-container { position: relative; padding-left: 40px; margin-left: 20px; }\n")
                .append("        /* Vertical Line */\n")
                .append("        #timeline-container::before {"
                        + "content: '';"
                        + "position: absolute;"
                        + "left: 20px;"
                        + "top: 0;"
                        + " bottom: 0;"
                        + " width: 4px;"
                        + "background-color: #dcdcdc;"
                        + " border-radius: 2px; }\n")
                .append("        /* General Log Entry Styles */\n")
                .append("        .log-entry {"
                        + "position: relative;"
                        + "margin-bottom: 25px;"
                        + "padding: 15px;"
                        + "background-color: #ffffff;"
                        + "border-radius: 8px;"
                        + "box-shadow: 0 1px 3px rgba(0,0,0,0.08);+"
                        + "border-left: 5px solid #ccc;"
                        + "transition: all 0.3s ease; }\n")
                .append("        /* Timeline Marker (Dot) */\n")
                .append("        .log-entry::before {"
                        + "content: '';"
                        + "position: absolute;"
                        + "left: -30px;"
                        + "top: 18px;"
                        + "width: 16px;"
                        + "height: 16px;"
                        + "border-radius: 50%;"
                        + "background-color: #ccc;"
                        + "border: 3px solid #f4f7f6;"
                        + "z-index: 1; }\n")
                .append("        /* Timestamp Style */\n")
                .append("        .timestamp {"
                        + "display: block;"
                        + "font-weight: bold;"
                        + "color: #555;"
                        + "margin-bottom: 8px;"
                        + "font-size: 0.9em; }\n")
                .append("        ")
                .append("/* Log Message Style */\\n\"")
                .append("        ")
                .append(".log-message { color: #333; line-height: 1.5; word-break: break-word; }\n")
                .append("        ")
                .append("/* Colors for Different Log Types */\n")
                .append("        ")
                .append(".log-entry[data-type=\"info\"] { border-left-color: #3498db; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"info\"]::before { background-color: #3498db; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"warning\"]")
                .append("{ border-left-color: #f39c12; background-color: #fffaf0; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"warning\"]::before { background-color: #f39c12; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"error\"] { border-left-color: #e74c3c; background-color: #fff2f2; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"error\"]::before { background-color: #e74c3c; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"error\"] .log-message { font-weight: 500; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"debug\"] { border-left-color: #9b59b6; opacity: 0.85; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"debug\"]::before { background-color: #9b59b6; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"debug\"] .log-message { font-style: italic; color: #666; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"success\"] { border-left-color: #2ecc71; }\n")
                .append("        ")
                .append(".log-entry[data-type=\"success\"]::before { background-color: #2ecc71; }\n")
                // Styles for other log types can be added here
                // Example: .log-entry[data-type="custom"] { ... }
                .append("        /* Elements hidden by filtering */\n")
                .append("        .log-entry.hidden { display: none; }\n")
                .append("        /* Footer Styles */\n")
                .append("        .footer {"
                        + "background-color: #e9ecef;"
                        + "color: #6c757d;"
                        + "text-align: center;"
                        + "padding: 15px 20px;"
                        + "margin-top: 30px;"
                        + "border-top: 1px solid #dee2e6;"
                        + "font-size: 0.9em; }\n")
                .append("        .footer p { margin: 5px 0; }\n")
                // --- End of CSS Styles ---
                .append("    </style>\n")
                .append("</head>\n");
    }

    /**
     * Appends the starting body tag and the static header section.
     */
    private void appendBodyStartAndHeader(final StringBuilder html) {
        html.append("<body>\n")
                .append("\n")
                .append("    <!-- Header -->\n")
                .append("    <div class=\"navbar\">\n")
                .append("        ")
                .append("<a href=\"https://htmlunit.sourceforge.io/\"")
                .append(" class=\"logo-link\" target=\"_blank\" rel=\"noopener noreferrer\">\n")
                // Logo path: '../images/htmlunit.png' used, change if necessary
                .append("            <img src=\"../images/htmlunit.png\" alt=\"HTMLUnit Logo\">\n")
                .append("            <h1>HTMLUnit Automated Test Recorder</h1>\n")
                .append("        </a>\n")
                .append("    </div>\n")
                .append("\n");
    }

    /**
     * Appends the start of the main content area, including controls and timeline container start.
     */
    private void appendMainContentStart(final StringBuilder html, final String recorderUUID) {
        html.append("    <!-- Main Content -->\n")
                .append("    <main>\n")
                .append("        <h1>Program Log Timeline</h1>\n")
                .append("\n")
                .append("        <div id=\"controls\">\n")
                .append("            ")
                .append("<label for=\"searchInput\">Search:</label>\n")
                .append("            ")
                .append("<input type=\"text\" id=\"searchInput\" placeholder=\"Search in logs...\">\n")
                .append("\n")
                .append("            ")
                .append("<div id=\"filter-controls\">\n")
                .append("                <span>Filter:</span>\n")
                .append("\n                ")
                .append("<label>")
                .append("<input type=\"checkbox\" class=\"filter-checkbox\" value=\"info\" checked> Infon")
                .append("</label>")
                .append("\n                ")
                .append("<label>")
                .append("<input type=\"checkbox\" class=\"filter-checkbox\" value=\"warning\" checked> Warning")
                .append("</label>")
                .append("\n                ")
                .append("<label>")
                .append("<input type=\"checkbox\" class=\"filter-checkbox\" value=\"error\" checked> Error")
                .append("</label>")
                .append("\n                ")
                .append("<label>")
                .append("<input type=\"checkbox\" class=\"filter-checkbox\" value=\"debug\" checked> Debug")
                .append("</label>")
                .append("\n                ")
                .append("<label>")
                .append("<input type=\"checkbox\" class=\"filter-checkbox\" value=\"success\" checked> Success")
                .append("</label>")
                // Checkboxes for different log types can be added dynamically here (if desired)
                .append("            </div>\n")
                .append("        </div>\n")
                .append("\n")
                // Add Recorder UUID as a comment
                .append("        <!-- Recorder UUID: ").append(recorderUUID).append(" -->\n")
                .append("\n")
                .append("        <div id=\"timeline-container\">\n");
        // Log entries will be added here (inside the loop)
    }

    /**
     * Appends the HTML representation of a single IRecord to the timeline.
     */
    private void appendSingleRecordHtml(final StringBuilder html, final IRecord record) {
        try {
            // Get current record type this is acting switch state
            final RecordType recordType = (RecordType) record.getClass().getMethod("getRecordType").invoke(record);
            htmlStateSwitcher(recordType); // Switch html state by recordType
            final Map<String, String> args = new HashMap<>(); // Map for record values in record
            for (
                final Method method
                    :
                IFormatter.orderAndFilterMethods(record.getClass().getDeclaredMethods())) {

                final String value = method.invoke(record) != null ? method.invoke(record).toString() : "null";
                final String key = IFormatter.getFieldName(method.getName());
                args.put(key, value);
            }
            state_.buildHtmlElement(html, args);
        }
        catch (final NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (final InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the internal 'state' variable based on the provided RecordType.
     * This determines which HtmlFormatterState will be used to format the output.
     *
     * @param recordType The type of the record being processed.
     */
    private void htmlStateSwitcher(final RecordType recordType) {
        switch (recordType != null ? recordType : RecordType.Info_Record) {
            case Js_Record:
            case Css_Record:
            case Html_Record:
            case Junit_Record:
                // These seem like specific record *types*, let's map them to the generic 'record' state for now.
                // The 'buildHtmlElement' for 'record' state would then use the 'args'
                // map to differentiate and display the specific details.
                // Alternatively, you could create dedicated HtmlFormatterStates if their
                // HTML structure is significantly different.
                this.state_ = HtmlFormatterState.record;
                break;
            case Html_Snapshot:
                this.state_ = HtmlFormatterState.htmlSnapshot;
                break;
            case Test_Start:
            case Test_End:
                // Represent test start/end markers as informational entries.
                this.state_ = HtmlFormatterState.info;
                break;
            case Info_Record:
                this.state_ = HtmlFormatterState.info;
                break;
            case Warning_Record:
                this.state_ = HtmlFormatterState.warning;
                break;
            case Error_Record:
                this.state_ = HtmlFormatterState.error;
                break;
            case Debug_Record:
                this.state_ = HtmlFormatterState.debug;
                break;
            case Success_Record:
                this.state_ = HtmlFormatterState.success;
                break;
            // It's crucial to handle all enum constants.
            // Add cases for any other RecordType values you might have.
            default:
                // Handle unexpected or unmapped record types gracefully.
                // Option 1: Throw an exception (fail-fast)
                throw new IllegalArgumentException("Unsupported RecordType encountered: " + recordType);
                // Option 2: Default to a specific state (e.g., debug or info) and log a warning
                // this.state = HtmlFormatterState.debug;
                // break;
        }
    }

    /**
     * Appends the closing tags for the main content area.
     */
    private void appendMainContentEnd(final StringBuilder html) {
        html.append("        </div> <!-- #timeline-container -->\n")
                .append("    </main>\n")
                .append("\n");
    }

    /**
     * Appends the static footer section and the closing body tag.
     */
    private void appendFooterAndBodyEnd(final StringBuilder html) {
        html.append("    <!-- Footer -->\n")
                .append("    <footer class=\"footer\">\n")
                .append("        <p>© 2024 Your Project or Company Name. All rights reserved.</p>\n")
                .append("        <p>Generated by the HTMLUnit Automated Test Recorder Documentation System.</p>\n")
                .append("    </footer>\n")
                .append("\n");
        // Script block will come right before the closing body tag
    }

    /**
     * Appends the JavaScript block for filtering and searching.
     */
    private void appendJavaScript(final StringBuilder html) {
        html.append("    <script>\n")
                // --- Copy JavaScript Code Here ---
                .append("        const searchInput = document.getElementById('searchInput');\n")
                .append("        const filterCheckboxes = document.querySelectorAll('.filter-checkbox');\n")
                .append("        const timelineContainer = document.getElementById('timeline-container');\n")
                .append("\n")
                .append("        function applyFiltersAndSearch() {\n")
                .append("            const searchTerm = searchInput.value.toLowerCase().trim();\n")
                // Re-selecting logEntries each time is safer for dynamic additions
                .append("            const logEntries = timelineContainer.querySelectorAll('.log-entry');\n")
                .append("\n")
                .append("            const checkedFilters = new Set();\n")
                .append("            filterCheckboxes.forEach(checkbox => {\n")
                .append("                if (checkbox.checked) { checkedFilters.add(checkbox.value); }\n")
                .append("            });\n")
                .append("\n")
                .append("            logEntries.forEach(entry => {\n")
                .append("                const entryType = entry.getAttribute('data-type');\n")
                // Use optional chaining and nullish coalescing for safety
                .append("                ")
                .append("const entryTimestamp = entry.querySelector('.timestamp')?.textContent.toLowerCase() ?? '';\n")
                .append("                ")
                .append("const entryMessage = entry.querySelector('.log-message')?.textContent.toLowerCase() ?? '';\n")
                .append("                ")
                .append("const entryText = `${entryTimestamp} ${entryMessage}`;\n")
                .append("                ")
                .append("const matchesSearch = !searchTerm || entryText.includes(searchTerm);\n")
                .append("                ")
                .append("const matchesFilter = !checkedFilters.size || checkedFilters.has(entryType);\n")
                .append("\n")
                .append("                if (matchesSearch && matchesFilter) {\n")
                .append("                    entry.classList.remove('hidden');\n")
                .append("                } else {\n")
                .append("                    entry.classList.add('hidden');\n")
                .append("                }\n")
                .append("            });\n")
                .append("        }\n")
                .append("\n")
                .append("        searchInput.addEventListener('input', applyFiltersAndSearch);\n")
                .append("\n")
                .append("        filterCheckboxes.forEach(checkbox => {\n")
                .append("            checkbox.addEventListener('change', applyFiltersAndSearch);\n")
                .append("        });\n")
                .append("\n")
                // Apply initial filtering on page load (optional but recommended)
                .append("        document.addEventListener('DOMContentLoaded', applyFiltersAndSearch);\n")
                // --- End of JavaScript Code ---
                .append("    </script>\n");
    }

    /**
     * Appends the closing HTML tags.
     */
    private void appendHtmlFooter(final StringBuilder html) {
        html.append("</body>\n")
                .append("</html>\n");
    }
}
