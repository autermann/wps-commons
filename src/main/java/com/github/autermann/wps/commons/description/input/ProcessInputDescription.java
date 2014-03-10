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

import net.opengis.wps.x100.InputDescriptionType;

import com.github.autermann.wps.commons.description.AbstractDescription;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessInputDescription extends AbstractDescription {

    private final InputOccurence occurence;

    public ProcessInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence) {
        super(identifier, title, abstrakt);
        this.occurence = checkNotNull(occurence);
    }

    public InputOccurence getOccurence() {
        return this.occurence;
    }

    public boolean isComplex() {
        return false;
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isBoundingBox() {
        return false;
    }

    public ComplexInputDescription asComplex() {
        throw new UnsupportedOperationException();
    }

    public LiteralInputDescription asLiteral() {
        throw new UnsupportedOperationException();
    }

    public BoundingBoxInputDescription asBoundingBox() {
        throw new UnsupportedOperationException();
    }

    public static ProcessInputDescription of(InputDescriptionType idt) {
        if (idt.getBoundingBoxData() != null) {
            return BoundingBoxInputDescription.of(idt);
        } else if (idt.getLiteralData() != null) {
            return LiteralInputDescription.of(idt);
        } else if (idt.getComplexData() != null) {
            return ComplexInputDescription.of(idt);
        } else {
            throw new IllegalArgumentException("Can not identify input type");
        }
    }
}
