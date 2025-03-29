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
package org.htmlunit.reporter.record;

/**
 * Enum for record types in the HTML Unit test reporter.
 * Defines the different types of records that can be appended to the timeline.
 *
 * @author Akif Esad
 */
public enum RecordType {
    /** JsRecords. */
    Js_Record,
    /** CssRecords. */
    Css_Record,
    /** HtmlRecords. */
    Html_Record,
    /** JUnitRecords. */
    Junit_Record,
    /** Represents a snapshot of the HTML at a certain point. */
    Html_Snapshot,
    /** Indicates the start of a test. */
    Test_Start,
    /** Indicates the end of a test. */
    Test_End,
    /** Generic info record. */
    Info_Record,
    /** Generic warning record. */
    Warning_Record,
    /** Generic error record. */
    Error_Record,
    /** Generic debug record. */
    Debug_Record,
    /** Generic success record. */
    Success_Record
}
