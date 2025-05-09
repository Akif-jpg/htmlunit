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
package org.htmlunit.javascript.host.html;

import org.htmlunit.html.DomElement;
import org.htmlunit.html.HtmlData;
import org.htmlunit.javascript.configuration.JsxClass;
import org.htmlunit.javascript.configuration.JsxConstructor;
import org.htmlunit.javascript.configuration.JsxGetter;
import org.htmlunit.javascript.configuration.JsxSetter;

/**
 * The JavaScript object {@code HTMLDataElement}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@JsxClass(domClass = HtmlData.class)
public class HTMLDataElement extends HTMLElement {

    /**
     * JavaScript constructor.
     */
    @Override
    @JsxConstructor
    public void jsConstructor() {
        super.jsConstructor();
    }

    /**
     * Sets the value of the attribute {@code value}.
     * @param newValue the new value to set
     */
    @JsxSetter
    public void setValue(final String newValue) {
        getDomNodeOrDie().setAttribute(DomElement.VALUE_ATTRIBUTE, newValue);
    }

    /**
     * Returns the {@code value} property.
     * @return the {@code value} property
     */
    @JsxGetter
    @Override
    public String getValue() {
        return getDomNodeOrDie().getAttributeDirect(DomElement.VALUE_ATTRIBUTE);
    }
}
