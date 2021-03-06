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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import net.opengis.ows.x11.DomainMetadataType;
import net.opengis.wps.x100.LiteralInputType;
import net.opengis.wps.x100.LiteralOutputType;
import net.opengis.wps.x100.SupportedUOMsType;
import net.opengis.wps.x100.UOMsType;

import com.google.common.base.MoreObjects;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class OwsUOM {

    private final String reference;
    private final String value;

    public OwsUOM(String reference, String value) {
        this.reference = emptyToNull(reference);
        this.value = Objects.requireNonNull(emptyToNull(value));
    }

    public OwsUOM(String value) {
        this(null, value);
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(this.reference);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.reference, this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OwsUOM that = (OwsUOM) obj;
        return Objects.equals(this.getValue(), that.getValue()) &&
               Objects.equals(this.getReference(), that.getReference());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("reference", getReference().orElse(null))
                .add("value", getValue())
                .toString();
    }

    public static Iterable<OwsUOM> getSupported(LiteralOutputType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return getSupported(xb.getUOMs());
        }
    }

    public static Iterable<OwsUOM> getSupported(LiteralInputType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return getSupported(xb.getUOMs());
        }
    }

    public static Iterable<OwsUOM> getSupported(SupportedUOMsType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return of(xb.getSupported());
        }
    }

    public static Iterable<OwsUOM> of(UOMsType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return of(xb.getUOMArray());
        }
    }

    public static Iterable<OwsUOM> of(DomainMetadataType[] xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            List<OwsUOM> uoms = new ArrayList<>(xb.length);
            for (DomainMetadataType dmt : xb) {
                OwsUOM uom = of(dmt);
                if (uom != null) {
                    uoms.add(uom);
                }
            }
            return uoms;
        }
    }

    public static OwsUOM getDefault(LiteralOutputType xb) {
        return xb == null ? null : getDefault(xb.getUOMs());
    }

    public static OwsUOM getDefault(LiteralInputType xb) {
        return xb == null ? null : getDefault(xb.getUOMs());
    }

    public static OwsUOM getDefault(SupportedUOMsType xb) {
        return xb == null ? null : of(xb.getDefault());
    }

    public static OwsUOM of(SupportedUOMsType.Default xb) {
        return xb == null ? null : of(xb.getUOM());
    }

    public static OwsUOM of(DomainMetadataType xb) {
        return xb == null ? null : new OwsUOM(xb.getReference(),
                                              xb.getStringValue());
    }

}
