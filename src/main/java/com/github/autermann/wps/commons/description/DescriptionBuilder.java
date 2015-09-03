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
package com.github.autermann.wps.commons.description;

import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 * @param <T> the builded type
 * @param <B> the builder type
 */
public interface DescriptionBuilder<T extends Description, B extends DescriptionBuilder<T, B>> {

    T build();

    B withAbstract(OwsLanguageString abstrakt);

    B withAbstract(String abstrakt);

    B withAbstract(String lang, String abstrakt);

    B withIdentifier(OwsCodeType id);

    B withIdentifier(String id);

    B withIdentifier(String codespace, String id);

    B withTitle(OwsLanguageString title);

    B withTitle(String title);

    B withTitle(String lang, String title);

}
