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

import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.description.LiteralDescription;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValues;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.google.common.base.Optional;
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
    private final Set<OwsUOM> uoms;
    private final OwsUOM defaultUOM;
    private final OwsAllowedValues allowedValues;

    public LiteralInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence,
                                   String dataType,
                                   OwsAllowedValues allowedValues,
                                   OwsUOM defaultUOM, Iterable<OwsUOM> uoms) {
        super(identifier, title, abstrakt, occurence);
        this.dataType = checkNotNull(dataType);
        this.allowedValues = checkNotNull(allowedValues);
        if (uoms == null) {
            if (defaultUOM == null) {
                this.uoms = Collections.emptySet();
            } else {
                this.uoms = Collections.singleton(defaultUOM);
            }
        } else {
            this.uoms = ImmutableSet.copyOf(uoms);
        }
        this.defaultUOM = defaultUOM;
    }

    public LiteralInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence,
                                   String dataType,
                                   OwsAllowedValues allowedValues,
                                   OwsUOM defaultUOM) {
        this(identifier, title, abstrakt, occurence, dataType, allowedValues, defaultUOM, null);
    }

    public LiteralInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence,
                                   String dataType,
                                   OwsAllowedValues allowedValues) {
        this(identifier, title, abstrakt, occurence, dataType, allowedValues, null, null);
    }

    public LiteralInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence,
                                   String dataType) {
        this(identifier, title, abstrakt, occurence, dataType, OwsAllowedValues
                .any(), null, null);
    }

    @Override
    public String getDataType() {
        return this.dataType;
    }

    public OwsAllowedValues getAllowedValues() {
        return this.allowedValues;
    }

    @Override
    public Set<OwsUOM> getUOMs() {
        return Collections.unmodifiableSet(this.uoms);
    }

    @Override
    public Optional<OwsUOM> getDefaultUOM() {
        return Optional.fromNullable(this.defaultUOM);
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public LiteralInputDescription asLiteral() {
        return this;
    }
}
