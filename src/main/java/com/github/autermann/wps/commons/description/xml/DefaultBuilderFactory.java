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
package com.github.autermann.wps.commons.description.xml;

import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType;

import com.github.autermann.wps.commons.description.BoundingBoxInputDescriptionBuilder;
import com.github.autermann.wps.commons.description.BoundingBoxOutputDescriptionBuilder;
import com.github.autermann.wps.commons.description.ComplexInputDescriptionBuilder;
import com.github.autermann.wps.commons.description.ComplexOutputDescriptionBuilder;
import com.github.autermann.wps.commons.description.LiteralInputDescriptionBuilder;
import com.github.autermann.wps.commons.description.LiteralOutputDescriptionBuilder;
import com.github.autermann.wps.commons.description.ProcessDescriptionBuilder;
import com.github.autermann.wps.commons.description.impl.BoundingBoxInputDescriptionImpl;
import com.github.autermann.wps.commons.description.impl.BoundingBoxOutputDescriptionImpl;
import com.github.autermann.wps.commons.description.impl.ComplexInputDescriptionImpl;
import com.github.autermann.wps.commons.description.impl.ComplexOutputDescriptionImpl;
import com.github.autermann.wps.commons.description.impl.LiteralInputDescriptionImpl;
import com.github.autermann.wps.commons.description.impl.LiteralOutputDescriptionImpl;
import com.github.autermann.wps.commons.description.impl.ProcessDescriptionImpl;
import com.github.autermann.wps.commons.description.xml.BuilderFactory;

public class DefaultBuilderFactory implements BuilderFactory {

    @Override
    public ProcessDescriptionBuilder<?, ?> newProcessDescriptionBuilder(
            ProcessDescriptionType xb) {
        return ProcessDescriptionImpl.builder();
    }

    @Override
    public BoundingBoxInputDescriptionBuilder<?, ?> newBoundingBoxInputDescriptionBuilder(
            InputDescriptionType xb) {
        return BoundingBoxInputDescriptionImpl.builder();
    }

    @Override
    public ComplexInputDescriptionBuilder<?, ?> newComplexInputDescriptionBuilder(
            InputDescriptionType xb) {
        return ComplexInputDescriptionImpl.builder();
    }

    @Override
    public LiteralInputDescriptionBuilder<?, ?> newLiteralInputDescriptionBuilder(
            InputDescriptionType xb) {
        return LiteralInputDescriptionImpl.builder();
    }

    @Override
    public BoundingBoxOutputDescriptionBuilder<?, ?> newBoundingBoxOutputDescriptionBuilder(
            OutputDescriptionType xb) {
        return BoundingBoxOutputDescriptionImpl.builder();
    }

    @Override
    public ComplexOutputDescriptionBuilder<?, ?> newComplexOutputDescriptionBuilder(
            OutputDescriptionType xb) {
        return ComplexOutputDescriptionImpl.builder();
    }

    @Override
    public LiteralOutputDescriptionBuilder<?, ?> newLiteralOutputDescriptionBuilder(
            OutputDescriptionType xb) {
        return LiteralOutputDescriptionImpl.builder();
    }

}
