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
package com.github.autermann.wps.commons.description.impl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ComplexOutputDescription;
import com.github.autermann.wps.commons.description.ComplexOutputDescriptionBuilder;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractComplexOutputDescriptionBuilder<T extends ComplexOutputDescription, B extends AbstractComplexOutputDescriptionBuilder<T, B>>
        extends AbstractProcessOutputDescriptionBuilder<T, B>
        implements ComplexOutputDescriptionBuilder<T, B> {

    private final ImmutableSet.Builder<Format> supportedFormats = ImmutableSet
            .builder();
    private Format defaultFormat;

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withDefaultFormat(Format format) {
        this.defaultFormat = Objects.requireNonNull(format);
        this.supportedFormats.add(format);
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withSupportedFormat(Format format) {
        if (format != null) {
            this.supportedFormats.add(format);
        }
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withSupportedFormat(Iterable<Format> formats) {
        if (formats != null) {
            for (Format format : formats) {
                withSupportedFormat(format);
            }
        }
        return (B) this;
    }

    @Override
    public B withSupportedFormat(Format... formats) {
        return withSupportedFormat(Arrays.asList(formats));
    }

    Set<Format> getSupportedFormats() {
        return supportedFormats.build();
    }

    Format getDefaultFormat() {
        return defaultFormat;
    }

}
