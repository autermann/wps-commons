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

import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.description.BoundingBoxDescription;
import com.github.autermann.wps.commons.description.ows.OwsCRS;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class BoundingBoxInputDescription extends ProcessInputDescription
        implements BoundingBoxDescription {

    private final Set<OwsCRS> supportedCrs;
    private final OwsCRS defaultCrs;

    public BoundingBoxInputDescription(OwsCodeType identifier,
                                       OwsLanguageString title,
                                       OwsLanguageString abstrakt,
                                       InputOccurence occurence,
                                       OwsCRS defaultCrs,
                                       Iterable<OwsCRS> supportedCrs) {
        super(identifier, title, abstrakt, occurence);
        if (supportedCrs == null) {
            if (defaultCrs == null) {
                this.supportedCrs = Collections.emptySet();
            } else {
                this.supportedCrs = Collections.singleton(defaultCrs);
            }
        } else {
            this.supportedCrs = ImmutableSet.copyOf(supportedCrs);
        }
        this.defaultCrs = defaultCrs;
    }

    public BoundingBoxInputDescription(OwsCodeType identifier,
                                       OwsLanguageString title,
                                       OwsLanguageString abstrakt,
                                       InputOccurence occurence,
                                       OwsCRS defaultCrs) {
        this(identifier, title, abstrakt, occurence, defaultCrs, null);
    }

    public BoundingBoxInputDescription(OwsCodeType identifier,
                                       OwsLanguageString title,
                                       OwsLanguageString abstrakt,
                                       InputOccurence occurence) {
        this(identifier, title, abstrakt, occurence, null, null);
    }

    @Override
    public Set<OwsCRS> getSupportedCRS() {
        return Collections.unmodifiableSet(supportedCrs);
    }

    @Override
    public Optional<OwsCRS> getDefaultCRS() {
        return Optional.fromNullable(this.defaultCrs);
    }

    @Override
    public boolean isBoundingBox() {
        return true;
    }

    @Override
    public BoundingBoxInputDescription asBoundingBox() {
        return this;
    }
}
