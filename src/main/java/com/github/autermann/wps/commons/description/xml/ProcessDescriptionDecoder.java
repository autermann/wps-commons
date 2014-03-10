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
import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.LiteralInputType;
import net.opengis.wps.x100.LiteralOutputType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType;
import net.opengis.wps.x100.SupportedCRSsType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ProcessDescription;
import com.github.autermann.wps.commons.description.input.BoundingBoxInputDescription;
import com.github.autermann.wps.commons.description.input.ComplexInputDescription;
import com.github.autermann.wps.commons.description.input.InputOccurence;
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
        ProcessDescription pd = createProcessDescription(OwsCodeType.of(xb
                .getIdentifier()), OwsLanguageString.of(xb.getTitle()), OwsLanguageString
                .of(xb.getAbstract()), xb.getProcessVersion(), xb
                .getStoreSupported(), xb.getStatusSupported());
        pd.addInputs(decodeProcessInputs(xb.getDataInputs()));
        pd.addOutputs(decodeProcessOutputs(xb.getProcessOutputs()));
        return pd;
    }

    protected ProcessDescription createProcessDescription(OwsCodeType identifier,
                                                        OwsLanguageString title,
                                                        OwsLanguageString abstrakt,
                                                        String version,
                                                        boolean storeSupported,
                                                        boolean statusSupported) {
        return new ProcessDescription(identifier, title, abstrakt, version, storeSupported, statusSupported);
    }

    protected List<ProcessInputDescription> decodeProcessInputs(ProcessDescriptionType.DataInputs xb) {
        List<ProcessInputDescription> descriptions = new ArrayList<>(xb.getInputArray().length);
        for (InputDescriptionType xbInputDescription : xb.getInputArray()) {
            descriptions.add(decodeProcessInput(xbInputDescription));
        }
        return descriptions;
    }

    protected List<ProcessOutputDescription> decodeProcessOutputs(ProcessDescriptionType.ProcessOutputs xb) {
        List<ProcessOutputDescription> descriptions = new ArrayList<>(xb.getOutputArray().length);
        for (OutputDescriptionType xbOutputDescription : xb.getOutputArray()) {
            descriptions.add(decodeProcessOutput(xbOutputDescription));
        }
        return descriptions;
    }

    protected ProcessOutputDescription decodeProcessOutput(
            OutputDescriptionType odt) {
        if (odt.getBoundingBoxOutput() != null) {
            return decodeBoundingBoxOutputDescription(odt);
        } else if (odt.getComplexOutput() != null) {
            return decodeComplexOutputDescription(odt);
        } else if (odt.getLiteralOutput() != null) {
            return decodeLiteralOutputDescription(odt);
        } else {
            throw new IllegalArgumentException("Can not identify output type");
        }
    }

    protected ProcessInputDescription decodeProcessInput(InputDescriptionType idt) {
        if (idt.getBoundingBoxData() != null) {
            return decodeBoundingBoxInputDescription(idt);
        } else if (idt.getLiteralData() != null) {
            return decodeLiteralInputDescription(idt);
        } else if (idt.getComplexData() != null) {
            return decodeComplexInputDescription(idt);
        } else {
            throw new IllegalArgumentException("Can not identify input type");
        }
    }

    protected InputOccurence decodeInputOccurence(InputDescriptionType idt) {
        return new InputOccurence(idt.getMinOccurs(), idt.getMaxOccurs());
    }

    protected BoundingBoxInputDescription decodeBoundingBoxInputDescription(
            InputDescriptionType idt) {
        SupportedCRSsType boundingBoxData = idt.getBoundingBoxData();
        return new BoundingBoxInputDescription(
                OwsCodeType.of(idt.getIdentifier()),
                OwsLanguageString.of(idt.getTitle()),
                OwsLanguageString.of(idt.getAbstract()),
                decodeInputOccurence(idt),
                OwsCRS.getDefault(boundingBoxData),
                OwsCRS.getSupported(boundingBoxData));
    }

    protected LiteralInputDescription decodeLiteralInputDescription(
            InputDescriptionType idt) {
        LiteralInputType literalData = idt.getLiteralData();
        return new LiteralInputDescription(
                OwsCodeType.of(idt.getIdentifier()),
                OwsLanguageString.of(idt.getTitle()),
                OwsLanguageString.of(idt.getAbstract()),
                decodeInputOccurence(idt),
                literalData.getDataType().getStringValue(),
                decodeOwsAllowedValues(literalData.getAllowedValues()),
                OwsUOM.getDefault(literalData),
                OwsUOM.getSupported(literalData));
        // TODO inputDescription.getLiteralData().getValuesReference()
    }

    protected OwsAllowedValues decodeOwsAllowedValues(
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

    protected OwsAllowedValue decodeOwsAllowedValue(ValueType xbValue) {
        return new OwsAllowedValue(xbValue.getStringValue());
    }

    protected OwsAllowedRange decodeOwsAllowedRange(RangeType xbRange) {
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

    protected ComplexInputDescription decodeComplexInputDescription(
            InputDescriptionType idt) {
        return new ComplexInputDescription(
                OwsCodeType.of(idt.getIdentifier()),
                OwsLanguageString.of(idt.getTitle()),
                OwsLanguageString.of(idt.getAbstract()),
                decodeInputOccurence(idt),
                Format.getDefault(idt),
                Format.getSupported(idt),
                idt.getComplexData().getMaximumMegabytes());
    }

    protected LiteralOutputDescription decodeLiteralOutputDescription(
            OutputDescriptionType odt) {
        LiteralOutputType literalOutput = odt.getLiteralOutput();
        return new LiteralOutputDescription(
                OwsCodeType.of(odt.getIdentifier()),
                OwsLanguageString.of(odt.getTitle()),
                OwsLanguageString.of(odt.getAbstract()),
                literalOutput.getDataType().getStringValue(),
                OwsUOM.getDefault(literalOutput),
                OwsUOM.getSupported(literalOutput));
    }

    protected ComplexOutputDescription decodeComplexOutputDescription(
            OutputDescriptionType odt) {
        return new ComplexOutputDescription(
                OwsCodeType.of(odt.getIdentifier()),
                OwsLanguageString.of(odt.getTitle()),
                OwsLanguageString.of(odt.getAbstract()),
                Format.getDefault(odt),
                Format.getSupported(odt));
    }

    protected BoundingBoxOutputDescription decodeBoundingBoxOutputDescription(
            OutputDescriptionType odt) {
        SupportedCRSsType boundingBoxOutput = odt.getBoundingBoxOutput();
        return new BoundingBoxOutputDescription(
                OwsCodeType.of(odt.getIdentifier()),
                OwsLanguageString.of(odt.getTitle()),
                OwsLanguageString.of(odt.getAbstract()),
                OwsCRS.getDefault(boundingBoxOutput),
                OwsCRS.getSupported(boundingBoxOutput));
    }
}
