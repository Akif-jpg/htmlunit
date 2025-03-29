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
 * A formatter that converts records into a JSON representation.
 * This class implements the {@code IFormatter} interface for formatting single or multiple
 * {@code IRecord} instances.
 *
 * @author Akif Esad
 */
public class JsonFormatter implements IFormatter {
    @Override
    public String format(final List<IRecord> records, final String recorderUUID) {
        final StringBuilder json = new StringBuilder("{\n");
        json.append("  \"recorderUUID\": \"").append(recorderUUID).append("\",\n")
                .append("  \"records\": [\n");

        for (final IRecord record : records) {
            json.append("       {\n");
            appendRecordProperties(json, record);
            json.append("\n      },\n");
        }

        removeLastComma(json);
        json.append("\n   ]\n").append("}\n");
        return json.toString();
    }

    @Override
    public String format(final IRecord record, final String recorderUUID) {
        final StringBuilder json = new StringBuilder("{\n");
        json.append("  \"recorderUUID\": \"").append(recorderUUID).append("\",\n")
                .append("  \"record\": {\n");

        appendRecordProperties(json, record);

        json.append("\n  }\n").append("}\n");
        return json.toString();
    }

    private void appendRecordProperties(final StringBuilder json, final IRecord record) {
        boolean firstProperty = true;
        for (final Method recordProperty : IFormatter.orderAndFilterMethods(record.getClass().getDeclaredMethods())) {
            try {
                final Object property = recordProperty.invoke(record);
                if (!firstProperty) {
                    json.append(",\n");
                }
                json.append("       \"")
                        .append(IFormatter.getFieldName(recordProperty.getName()))
                        .append("\": \"")
                        .append(property.toString())
                        .append("\"");
                firstProperty = false;
            }
            catch (final Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void removeLastComma(final StringBuilder json) {
        if (json.length() > 2) {
            json.delete(json.length() - 2, json.length());
        }
    }
}
