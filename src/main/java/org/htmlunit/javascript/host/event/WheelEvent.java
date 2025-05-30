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
package org.htmlunit.javascript.host.event;

import org.htmlunit.corejs.javascript.ScriptableObject;
import org.htmlunit.javascript.configuration.JsxClass;
import org.htmlunit.javascript.configuration.JsxConstant;
import org.htmlunit.javascript.configuration.JsxConstructor;

/**
 * A JavaScript object for {@code WheelEvent}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@JsxClass
public class WheelEvent extends MouseEvent {

    /** Constant for {@code DOM_DELTA_PIXEL}. */
    @JsxConstant
    public static final int DOM_DELTA_PIXEL = 0;
    /** Constant for {@code DOM_DELTA_LINE}. */
    @JsxConstant
    public static final int DOM_DELTA_LINE = 1;
    /** Constant for {@code DOM_DELTA_PAGE}. */
    @JsxConstant
    public static final int DOM_DELTA_PAGE = 2;

    /**
     * JavaScript constructor.
     *
     * @param type the event type
     * @param details the event details (optional)
     */
    @Override
    @JsxConstructor
    public void jsConstructor(final String type, final ScriptableObject details) {
        super.jsConstructor(type, details);
    }
}
