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
package com.github.autermann.wps.commons.description;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.autermann.wps.commons.Identifiable;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractDescription implements Identifiable<OwsCodeType> {

    private final OwsCodeType id;
    private final OwsLanguageString title;
    private final OwsLanguageString abstrakt;

    public AbstractDescription(Builder<?, ?> builder) {
        this.id = checkNotNull(builder.getId(), "id");
        this.title = builder.getTitle() != null ? builder.getTitle()
                     : new OwsLanguageString(builder.getId().getValue());
        this.abstrakt = builder.getAbstract();
    }

    @Override
    public OwsCodeType getID() {
        return this.id;
    }

    public OwsLanguageString getTitle() {
        return title;
    }

    public Optional<OwsLanguageString> getAbstract() {
        return Optional.fromNullable(this.abstrakt);
    }

    public static abstract class Builder<T extends AbstractDescription, B extends Builder<T, B>> {
        private OwsCodeType id;
        private OwsLanguageString title;
        private OwsLanguageString abstrakt;

        @SuppressWarnings("unchecked")
        public B withIdentifier(OwsCodeType id) {
            this.id = Preconditions.checkNotNull(id);
            return (B) this;
        }

        public B withIdentifier(String id) {
            return withIdentifier(new OwsCodeType(id));
        }

        public B withIdentifier(String codespace, String id) {
            return withIdentifier(new OwsCodeType(codespace, id));
        }

        @SuppressWarnings("unchecked")
        public B withTitle(OwsLanguageString title) {
            this.title = Preconditions.checkNotNull(title);
            return (B) this;
        }

        public B withTitle(String title) {
            return withTitle(title == null ? null : new OwsLanguageString(title));
        }

        public B withTitle(String lang, String title) {
            return withTitle(title == null ? null
                             : new OwsLanguageString(lang, title));
        }

        @SuppressWarnings("unchecked")
        public B withAbstract(OwsLanguageString abstrakt) {
            this.abstrakt = abstrakt;
            return (B) this;
        }

        public B withAbstract(String abstrakt) {
            return withAbstract(abstrakt == null ? null
                                : new OwsLanguageString(abstrakt));
        }

        public B withAbstract(String lang, String abstrakt) {
            return withAbstract(abstrakt == null ? null
                                : new OwsLanguageString(lang, abstrakt));
        }

        public abstract T build();

        private OwsCodeType getId() {
            return id;
        }

        private OwsLanguageString getTitle() {
            return title;
        }

        private OwsLanguageString getAbstract() {
            return abstrakt;
        }
    }
}
