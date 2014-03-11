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
package com.github.autermann.wps.commons.description.input;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import java.util.Arrays;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ComplexDescription;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ComplexInputDescription extends ProcessInputDescription implements
        ComplexDescription {

    private final ImmutableSet<Format> supportedFormats;
    private final Format defaultFormat;
    private final Optional<BigInteger> maximumMegabytes;

    protected ComplexInputDescription(Builder<?, ?> builder) {
        super(builder);
        this.defaultFormat = checkNotNull(builder.getDefaultFormat());
        this.supportedFormats = builder.getSupportedFormats().build();
        this.maximumMegabytes = Optional.fromNullable(builder
                .getMaximumMegabytes());
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
    public ComplexInputDescription asComplex() {
        return this;
    }

    @Override
    public boolean isComplex() {
        return true;
    }

    public Optional<BigInteger> getMaximumMegabytes() {
        return this.maximumMegabytes;
    }

    @Override
    public <T> T visit(ReturningVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static Builder<?, ?> builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl extends Builder<ComplexInputDescription, BuilderImpl> {
        @Override
        public ComplexInputDescription build() {
            return new ComplexInputDescription(this);
        }
    }

    public static abstract class Builder<T extends ComplexInputDescription, B extends Builder<T, B>>
            extends ProcessInputDescription.Builder<T, B> {
        private final ImmutableSet.Builder<Format> supportedFormats
                = ImmutableSet.builder();
        private Format defaultFormat;
        private BigInteger maximumMegabytes;

        @SuppressWarnings("unchecked")
        public B withMaximumMegabytes(BigInteger maximumMegabytes) {
            checkArgument(maximumMegabytes == null ||
                          maximumMegabytes.compareTo(BigInteger.ZERO) > 0);
            this.maximumMegabytes = maximumMegabytes;
            return (B) this;
        }

        public B withMaximumMegabytes(long maximumMegabytes) {
            return withMaximumMegabytes(BigInteger.valueOf(maximumMegabytes));
        }

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

        private BigInteger getMaximumMegabytes() {
            return maximumMegabytes;
        }
    }
}
