/*
 * Copyright (C) 2013-2015 Christian Autermann
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

import static com.google.common.base.Strings.emptyToNull;

import java.util.Objects;
import java.util.Optional;

import net.opengis.ows.x11.CodeType;

import com.google.common.base.MoreObjects;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class OwsCodeType {

    private final String codeSpace;
    private final String value;

    public OwsCodeType(String codeSpace, String value) {
        this.codeSpace = emptyToNull(codeSpace);
        this.value = Objects.requireNonNull(emptyToNull(value));
    }

    public OwsCodeType(String value) {
        this(null, value);
    }

    public Optional<String> getCodeSpace() {
        return Optional.ofNullable(this.codeSpace);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codeSpace, this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OwsCodeType that = (OwsCodeType) obj;
        return Objects.equals(this.getValue(), that.getValue()) &&
               Objects.equals(this.getCodeSpace(), that.getCodeSpace());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("codeSpace", getCodeSpace().orElse(null))
                .add("value", getValue())
                .toString();
    }

    public void encodeTo(CodeType xbCodeType) {
        xbCodeType.setStringValue(getValue());
        if (getCodeSpace().isPresent()) {
            xbCodeType.setCodeSpace(getCodeSpace().get());
        }
    }

    public static OwsCodeType of(CodeType xbCodeType) {
        return new OwsCodeType(xbCodeType.getCodeSpace(),
                               xbCodeType.getStringValue());
    }

}
