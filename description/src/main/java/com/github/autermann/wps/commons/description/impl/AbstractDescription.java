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
package com.github.autermann.wps.commons.description.impl;

import java.util.Objects;
import java.util.Optional;

import com.github.autermann.wps.commons.description.Description;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractDescription implements Description {

    private final OwsCodeType id;
    private final OwsLanguageString title;
    private final OwsLanguageString abstrakt;

    public AbstractDescription(AbstractDescriptionBuilder<?, ?> builder) {
        this.id = Objects.requireNonNull(builder.getId(), "id");
        this.title = builder.getTitle() != null ? builder.getTitle()
                     : new OwsLanguageString(builder.getId().getValue());
        this.abstrakt = builder.getAbstract();
    }

    @Override
    public OwsCodeType getId() {
        return this.id;
    }

    @Override
    public OwsLanguageString getTitle() {
        return title;
    }

    @Override
    public Optional<OwsLanguageString> getAbstract() {
        return Optional.ofNullable(this.abstrakt);
    }

}
