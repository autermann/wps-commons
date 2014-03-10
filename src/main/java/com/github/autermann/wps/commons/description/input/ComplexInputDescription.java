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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ComplexDescription;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ComplexInputDescription extends ProcessInputDescription implements
        ComplexDescription {

    private final Set<Format> formats;
    private final Format defaultFormat;
    private final BigInteger maximumMegabytes;

    public ComplexInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence,
                                   Format defaultFormat,
                                   Iterable<Format> formats,
                                   BigInteger maximumMegabytes) {
        super(identifier, title, abstrakt, occurence);
        this.defaultFormat = checkNotNull(defaultFormat);
        if (formats == null) {
            this.formats = Collections.singleton(defaultFormat);
        } else {
            this.formats = ImmutableSet.copyOf(formats);
        }
        checkArgument(maximumMegabytes == null ||
                      maximumMegabytes.compareTo(BigInteger.ZERO) > 0);
        this.maximumMegabytes = maximumMegabytes;
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
    public ComplexInputDescription asComplex() {
        return this;
    }

    @Override
    public boolean isComplex() {
        return true;
    }

    public Optional<BigInteger> getMaximumMegabytes() {
        return Optional.fromNullable(this.maximumMegabytes);
    }
}
