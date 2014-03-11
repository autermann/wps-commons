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

import com.github.autermann.wps.commons.description.LiteralDescription;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralOutputDescription
        extends ProcessOutputDescription
        implements LiteralDescription {

    private final String dataType;
    private final ImmutableSet<OwsUOM> supportedUOM;
    private final Optional<OwsUOM> defaultUOM;

    protected LiteralOutputDescription(Builder<?, ?> builder) {
        super(builder);
        this.dataType = checkNotNull(builder.getDataType());
        this.supportedUOM = builder.getSupportedUOM().build();
        this.defaultUOM = Optional.fromNullable(builder.getDefaultUOM());
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    @Override
    public ImmutableSet<OwsUOM> getSupportedUOM() {
        return this.supportedUOM;
    }

    @Override
    public Optional<OwsUOM> getDefaultUOM() {
        return this.defaultUOM;
    }

    @Override
    public LiteralOutputDescription asLiteral() {
        return this;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public <T> T visit(ReturningVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static Builder<?, ?> builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl extends Builder<LiteralOutputDescription, BuilderImpl> {
        @Override
        public LiteralOutputDescription build() {
            return new LiteralOutputDescription(this);
        }
    }

    public static abstract class Builder<T extends LiteralOutputDescription, B extends Builder<T, B>>
            extends ProcessOutputDescription.Builder<T, B> {
        private String dataType;
        private OwsUOM defaultUOM;
        private ImmutableSet.Builder<OwsUOM> supportedUOM = ImmutableSet
                .builder();

        @SuppressWarnings("unchecked")
        public B withDataType(String dataType) {
            this.dataType = checkNotNull(Strings.emptyToNull(dataType));
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withDefaultUOM(OwsUOM defaultUOM) {
            this.defaultUOM = defaultUOM;
            return (B) this;
        }

        public B withDefaultUOM(String defaultUOM) {
            return withDefaultUOM(defaultUOM == null ? null
                                  : new OwsUOM(defaultUOM));
        }

        @SuppressWarnings("unchecked")
        public B withSupportedUOM(Iterable<OwsUOM> uoms) {
            for (OwsUOM uom : uoms) {
                withSupportedUOM(uom);
            }
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withSupportedUOM(OwsUOM uom) {
            if (uom != null) {
                this.supportedUOM.add(uom);
            }
            return (B) this;
        }

        public B withSupportedUOM(String uom) {
            return withSupportedUOM(uom == null ? null : new OwsUOM(uom));
        }

        public B withSupportedUOM(String ref, String uom) {
            return withSupportedUOM(uom == null ? null : new OwsUOM(ref, uom));
        }

        private String getDataType() {
            return dataType;
        }

        private OwsUOM getDefaultUOM() {
            return defaultUOM;
        }

        private ImmutableSet.Builder<OwsUOM> getSupportedUOM() {
            return supportedUOM;
        }
    }
}
