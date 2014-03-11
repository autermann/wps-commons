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
package com.github.autermann.wps.commons.description.xml;

import static com.github.autermann.wps.commons.description.ows.OwsAllowedValues.any;

import java.util.ArrayList;
import java.util.List;

import net.opengis.ows.x11.AllowedValuesDocument;
import net.opengis.ows.x11.RangeType;
import net.opengis.ows.x11.ValueType;
import net.opengis.wps.x100.DescriptionType;
import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.AbstractDescription;
import com.github.autermann.wps.commons.description.ProcessDescription;
import com.github.autermann.wps.commons.description.input.BoundingBoxInputDescription;
import com.github.autermann.wps.commons.description.input.ComplexInputDescription;
import com.github.autermann.wps.commons.description.input.LiteralInputDescription;
import com.github.autermann.wps.commons.description.input.ProcessInputDescription;
import com.github.autermann.wps.commons.description.output.BoundingBoxOutputDescription;
import com.github.autermann.wps.commons.description.output.ComplexOutputDescription;
import com.github.autermann.wps.commons.description.output.LiteralOutputDescription;
import com.github.autermann.wps.commons.description.output.ProcessOutputDescription;
import com.github.autermann.wps.commons.description.ows.OwsAllowedRange;
import com.github.autermann.wps.commons.description.ows.OwsAllowedRange.BoundType;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValue;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValues;
import com.github.autermann.wps.commons.description.ows.OwsCRS;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.github.autermann.wps.commons.description.ows.OwsUOM;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ProcessDescriptionDecoder {
    public ProcessDescription decodeProcessDescription(ProcessDescriptionType xb) {
        return decodeAbstractDescription(newProcessDescriptionBuilder(xb), xb)
                .withVersion(xb.getProcessVersion())
                .statusSupported(xb.getStatusSupported())
                .storeSupported(xb.getStoreSupported())
                .withInput(decodeProcessInputs(xb.getDataInputs()))
                .withOutput(decodeProcessOutputs(xb.getProcessOutputs()))
                .build();
    }

    private List<ProcessInputDescription> decodeProcessInputs(
            ProcessDescriptionType.DataInputs xb) {
        List<ProcessInputDescription> descriptions = new ArrayList<>(xb
                .getInputArray().length);
        for (InputDescriptionType xbInputDescription : xb.getInputArray()) {
            descriptions.add(decodeProcessInput(xbInputDescription));
        }
        return descriptions;
    }

    private List<ProcessOutputDescription> decodeProcessOutputs(
            ProcessDescriptionType.ProcessOutputs xb) {
        List<ProcessOutputDescription> descriptions = new ArrayList<>(xb
                .getOutputArray().length);
        for (OutputDescriptionType xbOutputDescription : xb.getOutputArray()) {
            descriptions.add(decodeProcessOutput(xbOutputDescription));
        }
        return descriptions;
    }

    private ProcessOutputDescription decodeProcessOutput(
            OutputDescriptionType odt) {
        ProcessOutputDescription.Builder<?, ?> b;
        if (odt.getBoundingBoxOutput() != null) {
            b = decodeBoundingBoxOutputDescription(odt);
        } else if (odt.getComplexOutput() != null) {
            b = decodeComplexOutputDescription(odt);
        } else if (odt.getLiteralOutput() != null) {
            b = decodeLiteralOutputDescription(odt);
        } else {
            throw new IllegalArgumentException("Can not identify output type");
        }
        return decodeAbstractDescription(b, odt).build();
    }

    private ProcessInputDescription decodeProcessInput(
            InputDescriptionType idt) {
        ProcessInputDescription.Builder<?, ?> b;
        if (idt.getBoundingBoxData() != null) {
            b = decodeBoundingBoxInputDescription(idt);
        } else if (idt.getLiteralData() != null) {
            b = decodeLiteralInputDescription(idt);
        } else if (idt.getComplexData() != null) {
            b = decodeComplexInputDescription(idt);
        } else {
            throw new IllegalArgumentException("Can not identify input type");
        }
        return decodeAbstractDescription(b, idt)
                .withMinimalOccurence(idt.getMinOccurs())
                .withMaximalOccurence(idt.getMaxOccurs())
                .build();
    }

    private <B extends AbstractDescription.Builder<?, ? extends B>> B decodeAbstractDescription(
            B b, DescriptionType idt) {
        return b.withIdentifier(OwsCodeType.of(idt.getIdentifier()))
                .withTitle(OwsLanguageString.of(idt.getTitle()))
                .withAbstract(OwsLanguageString.of(idt.getAbstract()));
    }

    private BoundingBoxInputDescription.Builder<?, ?> decodeBoundingBoxInputDescription(
            InputDescriptionType idt) {
        return newBoundingBoxInputDescriptionBuilder(idt)
                .withDefaultCRS(OwsCRS.getDefault(idt.getBoundingBoxData()))
                .withSupportedCRS(OwsCRS.getSupported(idt.getBoundingBoxData()));
    }

    private LiteralInputDescription.Builder<?, ?> decodeLiteralInputDescription(
            InputDescriptionType idt) {
        // TODO idt.getLiteralData().getValuesReference()
        return newLiteralInputDescriptionBuilder(idt)
                .withDataType(idt.getLiteralData().getDataType().getReference())
                .withAllowedValues(decodeOwsAllowedValues(idt.getLiteralData()
                                .getAllowedValues()))
                .withDefaultUOM(OwsUOM.getDefault(idt.getLiteralData()))
                .withSupportedUOM(OwsUOM.getSupported(idt.getLiteralData()));

    }

    private OwsAllowedValues decodeOwsAllowedValues(
            AllowedValuesDocument.AllowedValues xbAllowedValues) {
        if (xbAllowedValues == null ||
            (xbAllowedValues.getRangeArray().length == 0 &&
             xbAllowedValues.getValueArray().length == 0)) {
            return any();
        }
        OwsAllowedValues owsAllowedValues = new OwsAllowedValues();
        for (RangeType xbRange : xbAllowedValues.getRangeArray()) {
            owsAllowedValues.add(decodeOwsAllowedRange(xbRange));
        }
        for (ValueType xbValue : xbAllowedValues.getValueArray()) {
            owsAllowedValues.add(decodeOwsAllowedValue(xbValue));
        }
        return owsAllowedValues;
    }

    private OwsAllowedValue decodeOwsAllowedValue(ValueType xbValue) {
        return new OwsAllowedValue(xbValue.getStringValue());
    }

    private OwsAllowedRange decodeOwsAllowedRange(RangeType xbRange) {
        String lower = xbRange.isSetMaximumValue()
                       ? xbRange.getMinimumValue().getStringValue() : null;
        String upper = xbRange.isSetMaximumValue()
                       ? xbRange.getMaximumValue().getStringValue() : null;
        String type = null;
        if (xbRange.isSetRangeClosure() && !xbRange.getRangeClosure().isEmpty()) {
            type = (String) xbRange.getRangeClosure().get(0);
        }
        if (type == null) {
            type = OwsAllowedRange.CLOSED;
        }
        final BoundType upperType;
        final BoundType lowerType;
        switch (type) {
            case OwsAllowedRange.CLOSED_OPEN:
                lowerType = BoundType.CLOSED;
                upperType = BoundType.OPEN;
                break;
            case OwsAllowedRange.OPEN:
                lowerType = BoundType.OPEN;
                upperType = BoundType.OPEN;
                break;
            case OwsAllowedRange.OPEN_CLOSED:
                lowerType = BoundType.OPEN;
                upperType = BoundType.CLOSED;
                break;
            case OwsAllowedRange.CLOSED:
            default:
                lowerType = BoundType.CLOSED;
                upperType = BoundType.CLOSED;
        }
        return new OwsAllowedRange(lower, lowerType, upper, upperType);
    }

    private ComplexInputDescription.Builder<?, ?> decodeComplexInputDescription(
            InputDescriptionType idt) {
        return newComplexInputDescriptionBuilder(idt)
                .withDefaultFormat(Format.getDefault(idt))
                .withSupportedFormat(Format.getSupported(idt))
                .withMaximumMegabytes(idt.getComplexData().getMaximumMegabytes());
    }

    private LiteralOutputDescription.Builder<?, ?> decodeLiteralOutputDescription(
            OutputDescriptionType odt) {
        return newLiteralOutputDescriptionBuilder(odt)
                .withDataType(odt.getLiteralOutput().getDataType()
                        .getReference())
                .withDefaultUOM(OwsUOM.getDefault(odt.getLiteralOutput()))
                .withSupportedUOM(OwsUOM.getSupported(odt.getLiteralOutput()));
    }

    private ComplexOutputDescription.Builder<?, ?> decodeComplexOutputDescription(
            OutputDescriptionType odt) {
        return newComplexOutputDescriptionBuilder(odt)
                .withDefaultFormat(Format.getDefault(odt))
                .withSupportedFormat(Format.getSupported(odt));
    }

    private BoundingBoxOutputDescription.Builder<?, ?> decodeBoundingBoxOutputDescription(
            OutputDescriptionType odt) {
        return newBoundingBoxOutputDescriptionBuilder(odt)
                .withSupportedCRS(OwsCRS
                        .getSupported(odt.getBoundingBoxOutput()))
                .withDefaultCRS(OwsCRS.getDefault(odt.getBoundingBoxOutput()));
    }

    protected ProcessDescription.Builder<?, ?> newProcessDescriptionBuilder(
            ProcessDescriptionType xb) {
        return ProcessDescription.builder();
    }

    protected BoundingBoxInputDescription.Builder<?, ?> newBoundingBoxInputDescriptionBuilder(
            InputDescriptionType xb) {
        return BoundingBoxInputDescription.builder();
    }

    protected ComplexInputDescription.Builder<?, ?> newComplexInputDescriptionBuilder(
            InputDescriptionType xb) {
        return ComplexInputDescription.builder();
    }

    protected LiteralInputDescription.Builder<?, ?> newLiteralInputDescriptionBuilder(
            InputDescriptionType xb) {
        return LiteralInputDescription.builder();
    }

    protected BoundingBoxOutputDescription.Builder<?, ?> newBoundingBoxOutputDescriptionBuilder(
            OutputDescriptionType xb) {
        return BoundingBoxOutputDescription.builder();
    }

    protected ComplexOutputDescription.Builder<?, ?> newComplexOutputDescriptionBuilder(
            OutputDescriptionType xb) {
        return ComplexOutputDescription.builder();
    }

    protected LiteralOutputDescription.Builder<?, ?> newLiteralOutputDescriptionBuilder(
            OutputDescriptionType xb) {
        return LiteralOutputDescription.builder();
    }

}
