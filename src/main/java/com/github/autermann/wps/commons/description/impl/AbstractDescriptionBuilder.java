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

import java.util.Objects;

import com.github.autermann.wps.commons.description.Description;
import com.github.autermann.wps.commons.description.DescriptionBuilder;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractDescriptionBuilder<T extends Description, B extends DescriptionBuilder<T, B>>
        implements DescriptionBuilder<T, B> {

    private OwsCodeType id;
    private OwsLanguageString title;
    private OwsLanguageString abstrakt;

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withIdentifier(OwsCodeType id) {
        this.id = Objects.requireNonNull(id);
        return (B) this;
    }

    @Override
    public B withIdentifier(String id) {
        return withIdentifier(new OwsCodeType(id));
    }

    @Override
    public B withIdentifier(String codespace, String id) {
        return withIdentifier(new OwsCodeType(codespace, id));
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withTitle(OwsLanguageString title) {
        this.title = Objects.requireNonNull(title);
        return (B) this;
    }

    @Override
    public B withTitle(String title) {
        return withTitle(new OwsLanguageString(title));
    }

    @Override
    public B withTitle(String lang, String title) {
        return withTitle(new OwsLanguageString(lang, title));
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withAbstract(OwsLanguageString abstrakt) {
        this.abstrakt = abstrakt;
        return (B) this;
    }

    @Override
    public B withAbstract(String abstrakt) {
        return withAbstract(abstrakt == null ? null
                                    : new OwsLanguageString(abstrakt));
    }

    @Override
    public B withAbstract(String lang, String abstrakt) {
        return withAbstract(abstrakt == null ? null
                                    : new OwsLanguageString(lang, abstrakt));
    }

    OwsCodeType getId() {
        return id;
    }

    OwsLanguageString getTitle() {
        return title;
    }

    OwsLanguageString getAbstract() {
        return abstrakt;
    }

}
