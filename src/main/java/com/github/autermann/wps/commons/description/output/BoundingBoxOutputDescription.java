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

import java.util.Arrays;

import com.github.autermann.wps.commons.description.BoundingBoxDescription;
import com.github.autermann.wps.commons.description.ows.OwsCRS;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class BoundingBoxOutputDescription
        extends ProcessOutputDescription
        implements BoundingBoxDescription {

    private final ImmutableSet<OwsCRS> supportedCRS;
    private final Optional<OwsCRS> defaultCRS;

    protected BoundingBoxOutputDescription(Builder<?, ?> builder) {
        super(builder);
        this.supportedCRS = builder.getSupportedCRS().build();
        this.defaultCRS = Optional.fromNullable(builder.getDefaultCRS());
    }

    @Override
    public ImmutableSet<OwsCRS> getSupportedCRS() {
        return this.supportedCRS;
    }

    @Override
    public Optional<OwsCRS> getDefaultCRS() {
        return this.defaultCRS;
    }

    @Override
    public BoundingBoxOutputDescription asBoundingBox() {
        return this;
    }

    @Override
    public boolean isBoundingBox() {
        return true;
    }

    public static Builder<?, ?> builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl extends Builder<BoundingBoxOutputDescription, BuilderImpl> {

        @Override
        public BoundingBoxOutputDescription build() {
            return new BoundingBoxOutputDescription(this);
        }
    }

    public static abstract class Builder<T extends BoundingBoxOutputDescription, B extends Builder<T, B>>
            extends ProcessOutputDescription.Builder<T, B> {
        private OwsCRS defaultCRS;
        private ImmutableSet.Builder<OwsCRS> supportedCRS = ImmutableSet
                .builder();

        @SuppressWarnings("unchecked")
        public B withDefaultCRS(OwsCRS defaultCRS) {
            this.defaultCRS = defaultCRS;
            return (B) this;
        }

        public B withDefaultCRS(String defaultCRS) {
            return withDefaultCRS(defaultCRS == null ? null
                                  : new OwsCRS(defaultCRS));
        }

        @SuppressWarnings("unchecked")
        public B withSupportedCRS(Iterable<OwsCRS> crss) {
            for (OwsCRS crs : crss) {
                withSupportedCRS(crs);
            }
            return (B) this;
        }

        public B withSupportedCRS(OwsCRS... crss) {
            return withSupportedCRS(Arrays.asList(crss));
        }

        @SuppressWarnings("unchecked")
        public B withSupportedCRS(OwsCRS uom) {
            if (uom != null) {
                this.supportedCRS.add(uom);
            }
            return (B) this;
        }

        public B withSupportedCRS(String uom) {
            return withSupportedCRS(uom == null ? null : new OwsCRS(uom));
        }

        private OwsCRS getDefaultCRS() {
            return defaultCRS;
        }

        private ImmutableSet.Builder<OwsCRS> getSupportedCRS() {
            return supportedCRS;
        }
    }

}
