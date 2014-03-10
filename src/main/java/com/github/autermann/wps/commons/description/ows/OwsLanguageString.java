/*
 * Copyright (C) 2013 Christian Autermann
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.github.autermann.wps.commons.description.ows;

import static com.google.common.base.Preconditions.checkNotNull;

import net.opengis.ows.x11.LanguageStringType;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Strings;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class OwsLanguageString {
    private final String lang;
    private final String value;

    public OwsLanguageString(String lang, String value) {
        this.lang = Strings.emptyToNull(lang);
        this.value = checkNotNull(Strings.emptyToNull(value));
    }

    public Optional<String> getLang() {
        return Optional.fromNullable(this.lang);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.lang, this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OwsLanguageString that = (OwsLanguageString) obj;
        return Objects.equal(this.lang, that.lang) &&
               Objects.equal(this.value, that.value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .omitNullValues()
                .add("lang", this.lang)
                .add("value", this.value)
                .toString();
    }

    public static OwsLanguageString of(LanguageStringType xb) {
        if (xb == null) {
            return null;
        } else {
            return new OwsLanguageString(xb.getLang(), xb.getStringValue());
        }
    }
}
