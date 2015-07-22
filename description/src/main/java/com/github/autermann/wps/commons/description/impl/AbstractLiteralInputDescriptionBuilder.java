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
package com.github.autermann.wps.commons.description.impl;

import java.util.Objects;
import java.util.Set;

import com.github.autermann.wps.commons.description.LiteralInputDescription;
import com.github.autermann.wps.commons.description.LiteralInputDescriptionBuilder;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValues;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractLiteralInputDescriptionBuilder<T extends LiteralInputDescription, B extends LiteralInputDescriptionBuilder<T, B>>
        extends AbstractProcessInputDescriptionBuilder<T, B>
        implements LiteralInputDescriptionBuilder<T, B> {

    private String dataType;
    private OwsAllowedValues allowedValues = OwsAllowedValues.any();
    private OwsUOM defaultUOM;
    private final ImmutableSet.Builder<OwsUOM> supportedUOM
            = ImmutableSet.builder();

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withDataType(String dataType) {
        this.dataType
                = Objects.requireNonNull(Strings.emptyToNull(dataType));
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withAllowedValues(OwsAllowedValues allowedValues) {
        this.allowedValues
                = Objects.requireNonNull(allowedValues, "allowedValues");
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withDefaultUOM(OwsUOM defaultUOM) {
        this.defaultUOM = defaultUOM;
        return (B) this;
    }

    @Override
    public B withDefaultUOM(String defaultUOM) {
        return withDefaultUOM(defaultUOM == null ? null : new OwsUOM(defaultUOM));
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withSupportedUOM(Iterable<OwsUOM> uoms) {
        for (OwsUOM uom : uoms) {
            withSupportedUOM(uom);
        }
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withSupportedUOM(OwsUOM uom) {
        if (uom != null) {
            this.supportedUOM.add(uom);
        }
        return (B) this;
    }

    @Override
    public B withSupportedUOM(String uom) {
        return withSupportedUOM(uom == null ? null : new OwsUOM(uom));
    }

    @Override
    public B withSupportedUOM(String ref, String uom) {
        return withSupportedUOM(uom == null ? null : new OwsUOM(ref, uom));
    }

    String getDataType() {
        return dataType;
    }

    OwsAllowedValues getAllowedValues() {
        return allowedValues;
    }

    OwsUOM getDefaultUOM() {
        return defaultUOM;
    }

    Set<OwsUOM> getSupportedUOM() {
        return this.supportedUOM.build();
    }

}
