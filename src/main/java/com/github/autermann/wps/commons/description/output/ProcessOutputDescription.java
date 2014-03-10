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

import com.github.autermann.wps.commons.description.AbstractDescription;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessOutputDescription extends AbstractDescription {

    public ProcessOutputDescription(OwsCodeType identifier,
                                    OwsLanguageString title,
                                    OwsLanguageString abstrakt) {
        super(identifier, title, abstrakt);
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isComplex() {
        return false;
    }

    public boolean isBoundingBox() {
        return false;
    }

    public ComplexOutputDescription asComplex() {
        throw new UnsupportedOperationException();
    }

    public LiteralOutputDescription asLiteral() {
        throw new UnsupportedOperationException();
    }

    public BoundingBoxOutputDescription asBoundingBox() {
        throw new UnsupportedOperationException();
    }

}
