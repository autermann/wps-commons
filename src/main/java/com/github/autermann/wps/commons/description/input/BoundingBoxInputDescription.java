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

import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.SupportedCRSsType;

import com.github.autermann.wps.commons.description.ows.OwsCRS;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class BoundingBoxInputDescription extends ProcessInputDescription {

    private final Set<OwsCRS> supportedCrs;
    private final OwsCRS defaultCrs;

    public BoundingBoxInputDescription(OwsCodeType identifier,
                                       OwsLanguageString title,
                                       OwsLanguageString abstrakt,
                                       InputOccurence occurence,
                                       OwsCRS defaultCrs,
                                       Iterable<OwsCRS> supportedCrs) {
        super(identifier, title, abstrakt, occurence);
        this.supportedCrs = Sets.newHashSet(checkNotNull(supportedCrs));
        this.defaultCrs = defaultCrs;
    }

    public Set<OwsCRS> getSupportedCRS() {
        return Collections.unmodifiableSet(supportedCrs);
    }

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

    public static BoundingBoxInputDescription of(InputDescriptionType idt) {
        SupportedCRSsType boundingBoxData = idt.getBoundingBoxData();
        return new BoundingBoxInputDescription(
                OwsCodeType.of(idt.getIdentifier()),
                OwsLanguageString.of(idt.getTitle()),
                OwsLanguageString.of(idt.getAbstract()),
                InputOccurence.of(idt),
                OwsCRS.getDefault(boundingBoxData),
                OwsCRS.getSupported(boundingBoxData));
    }
}
