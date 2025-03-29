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

import java.lang.reflect.Method;
import java.util.List;

/**
 * A formatter that converts records into an XML representation.
 * This class implements the {@code IFormatter} interface for formatting single or multiple
 * {@code IRecord} instances into XML format.
 *
 * @author Akif Esad (Original JsonFormatter)
 * @author GPT (Generated XmlFormatter based on JsonFormatter)
 */
public class XmlFormatter implements IFormatter {

    private static final String INDENT_RECORD = "    ";
    private static final String INDENT_PROPERTY = "      ";

    @Override
    public String format(final List<IRecord> records, final String recorderUUID) {
        final StringBuilder xml = new StringBuilder("<HtmlUnitReport>\n");
        xml.append("  <recorderUUID>").append(escapeXml(recorderUUID)).append("</recorderUUID>\n")
                .append("  <records>\n");

        for (final IRecord record : records) {
            xml.append(INDENT_RECORD).append("<record>\n");
            appendRecordProperties(xml, record);
            xml.append(INDENT_RECORD).append("</record>\n");
        }

        xml.append("  </records>\n")
                .append("</HtmlUnitReport>\n");
        return xml.toString();
    }

    @Override
    public String format(final IRecord record, final String recorderUUID) {
        final StringBuilder xml = new StringBuilder("<HtmlUnitReport>\n");
        xml.append("  <recorderUUID>").append(escapeXml(recorderUUID)).append("</recorderUUID>\n")
                .append("  <record>\n");

        appendRecordProperties(xml, record);

        xml.append("  </record>\n")
                .append("</HtmlUnitReport>\n");
        return xml.toString();
    }

    /**
     * Appends the properties of a given record as XML elements to the StringBuilder.
     *
     * @param xml    The StringBuilder to append to.
     * @param record The record whose properties are to be appended.
     */
    private void appendRecordProperties(final StringBuilder xml, final IRecord record) {
        for (final Method recordProperty : IFormatter.orderAndFilterMethods(record.getClass().getDeclaredMethods())) {
            try {
                final Object property = recordProperty.invoke(record);
                final String fieldName = IFormatter.getFieldName(recordProperty.getName());
                final String value = (property == null) ? "" : property.toString();

                xml.append(XmlFormatter.INDENT_PROPERTY)
                        .append("<").append(fieldName).append(">")
                        .append(escapeXml(value))
                        .append("</").append(fieldName).append(">\n");
            }
            catch (final Exception e) {
                // Consider logging this error more formally
                e.printStackTrace();
                // Optionally append an error indicator in the XML
                // xml.append(indent).append("<error method=\"").append(recordProperty.getName()).append("\">")
                //    .append(escapeXml(e.getMessage()))
                //    .append("</error>\n");
            }

        }
    }

    /**
     * Escapes characters that have special meaning in XML.
     *
     * @param value The string to escape.
     * @return The escaped string.
     */
    private String escapeXml(final String value) {
        if (value == null) {
            return "";
        }
        // Basic XML escaping
        return value.replace("&", "&")
                .replace("<", "<")
                .replace(">", ">")
                .replace("\"", "\"")
                .replace("'", "'");
    }
}
