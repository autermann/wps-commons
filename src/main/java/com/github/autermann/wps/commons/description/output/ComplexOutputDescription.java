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

import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ComplexDescription;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ComplexOutputDescription
        extends ProcessOutputDescription
        implements ComplexDescription {

    private final Set<Format> formats;
    private final Format defaultFormat;

    public ComplexOutputDescription(OwsCodeType identifier,
                                    OwsLanguageString title,
                                    OwsLanguageString abstrakt,
                                    Format defaultFormat,
                                    Iterable<Format> formats) {
        super(identifier, title, abstrakt);
        this.formats = Sets.newHashSet(checkNotNull(formats));
        this.defaultFormat = checkNotNull(defaultFormat);
    }

    @Override
    public Set<Format> getFormats() {
        return Collections.unmodifiableSet(formats);
    }

    @Override
    public Format getDefaultFormat() {
        return defaultFormat;
    }

    @Override
    public ComplexOutputDescription asComplex() {
        return this;
    }

    @Override
    public boolean isComplex() {
        return true;
    }
}
