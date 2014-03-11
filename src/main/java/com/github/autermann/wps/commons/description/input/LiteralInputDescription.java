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

import static com.google.common.base.Preconditions.checkNotNull;


import com.github.autermann.wps.commons.description.LiteralDescription;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValues;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralInputDescription
        extends ProcessInputDescription
        implements LiteralDescription {

    private final String dataType;
    private final ImmutableSet<OwsUOM> supportedUOM;
    private final Optional<OwsUOM> defaultUOM;
    private final OwsAllowedValues allowedValues;

    protected LiteralInputDescription(Builder<?,?> builder) {
        super(builder);
        this.dataType = checkNotNull(builder.getDataType());
        this.allowedValues = checkNotNull(builder.getAllowedValues());
        this.supportedUOM = builder.getSupportedUOM().build();
        this.defaultUOM = Optional.fromNullable(builder.getDefaultUOM());
    }

    @Override
    public String getDataType() {
        return this.dataType;
    }

    public OwsAllowedValues getAllowedValues() {
        return this.allowedValues;
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
    public boolean isLiteral() {
        return true;
    }

    @Override
    public LiteralInputDescription asLiteral() {
        return this;
    }

    public static Builder<?,?> builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl extends Builder<LiteralInputDescription, BuilderImpl> {
        @Override
        public LiteralInputDescription build() {
            return new LiteralInputDescription(this);
        }
    }

    public static abstract class Builder<T extends LiteralInputDescription, B extends Builder<T, B>>
            extends ProcessInputDescription.Builder<T, B> {
        private String dataType;
        private OwsAllowedValues allowedValues = OwsAllowedValues.any();
        private OwsUOM defaultUOM;
        private final ImmutableSet.Builder<OwsUOM> supportedUOM = ImmutableSet.builder();

        @SuppressWarnings("unchecked")
        public B withDataType(String dataType) {
            this.dataType = checkNotNull(Strings.emptyToNull(dataType));
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withAllowedValues(OwsAllowedValues allowedValues) {
            this.allowedValues = checkNotNull(allowedValues, "allowedValues");
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

        private OwsAllowedValues getAllowedValues() {
            return allowedValues;
        }

        private OwsUOM getDefaultUOM() {
            return defaultUOM;
        }

        private ImmutableSet.Builder<OwsUOM> getSupportedUOM() {
            return supportedUOM;
        }
    }
}
