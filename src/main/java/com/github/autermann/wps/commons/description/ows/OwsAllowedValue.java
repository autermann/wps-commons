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



import net.opengis.ows.x11.ValueType;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class OwsAllowedValue implements OwsValueRestriction {

    private final String value;

    public OwsAllowedValue(String value) {
        this.value = Preconditions.checkNotNull(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            OwsAllowedValue that = (OwsAllowedValue) obj;
            return Objects.equal(this.value, that.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).addValue(this.value).toString();
    }

    public static OwsAllowedValue of(ValueType xbValue) {
        return new OwsAllowedValue(xbValue.getStringValue());
    }

}
