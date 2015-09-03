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

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.github.autermann.wps.commons.description.LiteralInputDescription;
import com.github.autermann.wps.commons.description.LiteralInputDescriptionBuilder;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValues;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralInputDescriptionImpl
        extends AbstractProcessInputDescription
        implements LiteralInputDescription {

    private final String dataType;
    private final Set<OwsUOM> supportedUOM;
    private final Optional<OwsUOM> defaultUOM;
    private final OwsAllowedValues allowedValues;

    protected LiteralInputDescriptionImpl(
            AbstractLiteralInputDescriptionBuilder<?, ?> builder) {
        super(builder);
        this.dataType = Objects.requireNonNull(builder.getDataType());
        this.allowedValues = Objects.requireNonNull(builder.getAllowedValues());
        this.supportedUOM = builder.getSupportedUOM();
        this.defaultUOM = Optional.ofNullable(builder.getDefaultUOM());
    }

    @Override
    public String getDataType() {
        return this.dataType;
    }

    @Override
    public OwsAllowedValues getAllowedValues() {
        return this.allowedValues;
    }

    @Override
    public Set<OwsUOM> getSupportedUOM() {
        return Collections.unmodifiableSet(this.supportedUOM);
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
    public LiteralInputDescriptionImpl asLiteral() {
        return this;
    }

    @Override
    public <T> T visit(ReturningVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static LiteralInputDescriptionBuilder<?, ?> builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl extends AbstractLiteralInputDescriptionBuilder<LiteralInputDescriptionImpl, BuilderImpl> {
        @Override
        public LiteralInputDescriptionImpl build() {
            return new LiteralInputDescriptionImpl(this);
        }
    }

}
