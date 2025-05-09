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
package org.htmlunit.javascript.host.css;

import static org.htmlunit.BrowserVersionFeatures.JS_SELECTOR_TEXT_LOWERCASE;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlunit.css.WrappedCssStyleDeclaration;
import org.htmlunit.cssparser.dom.CSSStyleRuleImpl;
import org.htmlunit.javascript.configuration.JsxClass;
import org.htmlunit.javascript.configuration.JsxConstructor;
import org.htmlunit.javascript.configuration.JsxGetter;
import org.htmlunit.javascript.configuration.JsxSetter;
import org.htmlunit.util.StringUtils;

/**
 * A JavaScript object for {@code CSSStyleRule}.
 *
 * @author Ahmed Ashour
 * @author Marc Guillemot
 * @author Ronald Brill
 */
@JsxClass
public class CSSStyleRule extends CSSRule {
    private static final Pattern SELECTOR_PARTS_PATTERN = Pattern.compile("[.#]?[a-zA-Z]+");
    private static final Pattern SELECTOR_REPLACE_PATTERN = Pattern.compile("\\*([.#])");

    /**
     * Creates a new instance.
     */
    public CSSStyleRule() {
        super();
    }

    /**
     * Creates an instance.
     */
    @JsxConstructor
    @Override
    public void jsConstructor() {
        super.jsConstructor();
    }

    /**
     * Creates a new instance.
     * @param stylesheet the Stylesheet of this rule.
     * @param rule the wrapped rule
     */
    protected CSSStyleRule(final CSSStyleSheet stylesheet, final CSSStyleRuleImpl rule) {
        super(stylesheet, rule);
    }

    /**
     * Returns the textual representation of the selector for the rule set.
     * @return the textual representation of the selector for the rule set
     */
    @JsxGetter
    public String getSelectorText() {
        String selectorText = ((CSSStyleRuleImpl) getRule()).getSelectorText();
        final Matcher m = SELECTOR_PARTS_PATTERN.matcher(selectorText);
        final StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String fixedName = m.group();
            // this should be handled with the right regex but...
            if (getBrowserVersion().hasFeature(JS_SELECTOR_TEXT_LOWERCASE)
                    && !fixedName.isEmpty() && '.' != fixedName.charAt(0) && '#' != fixedName.charAt(0)) {
                fixedName = fixedName.toLowerCase(Locale.ROOT);
            }
            fixedName = StringUtils.sanitizeForAppendReplacement(fixedName);
            m.appendReplacement(sb, fixedName);
        }
        m.appendTail(sb);

        // ".foo" and not "*.foo"
        selectorText = SELECTOR_REPLACE_PATTERN.matcher(sb.toString()).replaceAll("$1");
        return selectorText;
    }

    /**
     * Sets the textual representation of the selector for the rule set.
     * @param selectorText the textual representation of the selector for the rule set
     */
    @JsxSetter
    public void setSelectorText(final String selectorText) {
        ((CSSStyleRuleImpl) getRule()).setSelectorText(selectorText);
    }

    /**
     * Returns the declaration-block of this rule set.
     * @return the declaration-block of this rule set
     */
    @JsxGetter
    public CSSStyleDeclaration getStyle() {
        final WrappedCssStyleDeclaration styleDeclaration
                = new WrappedCssStyleDeclaration(((CSSStyleRuleImpl) getRule()).getStyle(), getBrowserVersion());
        return new CSSStyleDeclaration(getParentStyleSheet(), styleDeclaration);
    }
}
