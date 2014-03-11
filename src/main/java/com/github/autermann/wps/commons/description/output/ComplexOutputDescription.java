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
package com.github.autermann.wps.commons.description.output;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ComplexDescription;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ComplexOutputDescription
        extends ProcessOutputDescription
        implements ComplexDescription {

    private final ImmutableSet<Format> supportedFormats;
    private final Format defaultFormat;

    protected ComplexOutputDescription(Builder<?, ?> builder) {
        super(builder);
        this.defaultFormat = checkNotNull(builder.getDefaultFormat());
        this.supportedFormats = builder.getSupportedFormats().build();
    }

    @Override
    public ImmutableSet<Format> getSupportedFormats() {
        return supportedFormats;
    }

    @Override
    public Format getDefaultFormat() {
        return defaultFormat;
    }

    @Override
    public ComplexOutputDescription asComplex() {
        return this;
    }

    @Override
    public boolean isComplex() {
        return true;
    }

    @Override
    public <T> T visit(ReturningVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static Builder<?, ?> builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl extends Builder<ComplexOutputDescription, BuilderImpl> {

        @Override
        public ComplexOutputDescription build() {
            return new ComplexOutputDescription(this);
        }

    }

    public static abstract class Builder<T extends ComplexOutputDescription, B extends Builder<T, B>>
            extends ProcessOutputDescription.Builder<T, B> {
        private final ImmutableSet.Builder<Format> supportedFormats
                = ImmutableSet.builder();
        private Format defaultFormat;

        @SuppressWarnings("unchecked")
        public B withDefaultFormat(Format format) {
            this.defaultFormat = checkNotNull(format);
            this.supportedFormats.add(format);
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withSupportedFormat(Format format) {
            if (format != null) {
                this.supportedFormats.add(format);
            }
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withSupportedFormat(Iterable<Format> formats) {
            if (formats != null) {
                for (Format format : formats) {
                    withSupportedFormat(format);
                }
            }
            return (B) this;
        }

        public B withSupportedFormat(Format... formats) {
            return withSupportedFormat(Arrays.asList(formats));
        }

        private ImmutableSet.Builder<Format> getSupportedFormats() {
            return supportedFormats;
        }

        private Format getDefaultFormat() {
            return defaultFormat;
        }
    }
}
