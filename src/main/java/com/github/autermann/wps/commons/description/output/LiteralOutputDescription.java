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

import net.opengis.wps.x100.LiteralOutputType;
import net.opengis.wps.x100.OutputDescriptionType;

import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralOutputDescription extends ProcessOutputDescription {

    private final String dataType;
    private final Set<OwsUOM> uoms;
    private final OwsUOM defaultUOM;

    public LiteralOutputDescription(OwsCodeType identifier,
                                    OwsLanguageString title,
                                    OwsLanguageString abstrakt,
                                    String dataType,
                                    OwsUOM defaultUOM,
                                    Iterable<OwsUOM> uoms) {
        super(identifier, title, abstrakt);
        this.dataType = checkNotNull(dataType);
        this.defaultUOM = defaultUOM;
        this.uoms = Sets.newHashSet(checkNotNull(uoms));
    }

    public String getDataType() {
        return dataType;
    }

    public Set<OwsUOM> getUoms() {
        return Collections.unmodifiableSet(uoms);
    }

    public Optional<OwsUOM> getDefaultUOM() {
        return Optional.fromNullable(this.defaultUOM);
    }

    @Override
    public LiteralOutputDescription asLiteral() {
        return this;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    public static LiteralOutputDescription of(OutputDescriptionType odt) {
        LiteralOutputType literalOutput = odt.getLiteralOutput();
        return new LiteralOutputDescription(
                OwsCodeType.of(odt.getIdentifier()),
                OwsLanguageString.of(odt.getTitle()),
                OwsLanguageString.of(odt.getAbstract()),
                literalOutput.getDataType().getStringValue(),
                OwsUOM.getDefault(literalOutput),
                OwsUOM.getSupported(literalOutput));
    }

}
