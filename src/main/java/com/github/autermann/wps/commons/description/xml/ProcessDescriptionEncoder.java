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

import java.util.Collections;

import net.opengis.ows.x11.AllowedValuesDocument.AllowedValues;
import net.opengis.ows.x11.RangeType;
import net.opengis.ows.x11.ValueType;
import net.opengis.wps.x100.CRSsType;
import net.opengis.wps.x100.ComplexDataCombinationsType;
import net.opengis.wps.x100.DescriptionType;
import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.LiteralInputType;
import net.opengis.wps.x100.LiteralOutputType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType.DataInputs;
import net.opengis.wps.x100.ProcessDescriptionType.ProcessOutputs;
import net.opengis.wps.x100.SupportedCRSsType;
import net.opengis.wps.x100.SupportedComplexDataInputType;
import net.opengis.wps.x100.SupportedComplexDataType;
import net.opengis.wps.x100.SupportedUOMsType;
import net.opengis.wps.x100.UOMsType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.BoundingBoxDescription;
import com.github.autermann.wps.commons.description.BoundingBoxInputDescription;
import com.github.autermann.wps.commons.description.BoundingBoxOutputDescription;
import com.github.autermann.wps.commons.description.ComplexDescription;
import com.github.autermann.wps.commons.description.ComplexInputDescription;
import com.github.autermann.wps.commons.description.ComplexOutputDescription;
import com.github.autermann.wps.commons.description.Description;
import com.github.autermann.wps.commons.description.LiteralDescription;
import com.github.autermann.wps.commons.description.LiteralInputDescription;
import com.github.autermann.wps.commons.description.LiteralOutputDescription;
import com.github.autermann.wps.commons.description.ProcessDescription;
import com.github.autermann.wps.commons.description.ProcessInputDescription;
import com.github.autermann.wps.commons.description.ProcessOutputDescription;
import com.github.autermann.wps.commons.description.ows.OwsAllowedRange;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValue;
import com.github.autermann.wps.commons.description.ows.OwsAllowedValues;
import com.github.autermann.wps.commons.description.ows.OwsCRS;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.github.autermann.wps.commons.description.ows.OwsValueRestriction;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ProcessDescriptionEncoder {


    public ProcessDescriptionType encodeProcessDescription(ProcessDescription description) {
        ProcessDescriptionType xb = ProcessDescriptionType.Factory.newInstance();
        encodeProcessDescription(description, xb);
        return xb;
    }

    public void encodeProcessDescription(ProcessDescription description,
                                         ProcessDescriptionType xbDescription) {
        encodeDescription(description, xbDescription);
        xbDescription.setProcessVersion(description.getVersion());
        xbDescription.setStatusSupported(description.isStatusSupported());
        xbDescription.setStoreSupported(description.isStoreSupported());
        encodeInputDescriptions(description.getInputDescriptions(),
                                xbDescription.addNewDataInputs());
        encodeOutputDescriptions(description.getOutputDescriptions(),
                                 xbDescription.addNewProcessOutputs());
    }

    public void encodeDescription(Description description,
                                  DescriptionType xbDescription) {
        description.getId().encodeTo(xbDescription.addNewIdentifier());
        description.getTitle().encodeTo(xbDescription.addNewTitle());
        if (description.getAbstract().isPresent()) {
            description.getAbstract().get()
                    .encodeTo(xbDescription.addNewAbstract());
        }
    }

    public void encodeOutputDescriptions(
            Iterable<? extends ProcessOutputDescription> outputDescriptions,
            ProcessOutputs xbOutputDescriptions) {
        for (ProcessOutputDescription outputDescription : outputDescriptions) {
            encodeOutputDescription(outputDescription, xbOutputDescriptions
                                    .addNewOutput());
        }
    }

    public void encodeOutputDescription(ProcessOutputDescription output,
                                        OutputDescriptionType xbOutput) {
        encodeDescription(output, xbOutput);
        if (output.isBoundingBox()) {
            encodeBoundingBoxOutput(output.asBoundingBox(),
                                    xbOutput.addNewBoundingBoxOutput());
        } else if (output.isComplex()) {
            encodeComplexOutput(output.asComplex(),
                                xbOutput.addNewComplexOutput());
        } else if (output.isLiteral()) {
            encodeLiteralOutput(output.asLiteral(),
                                xbOutput.addNewLiteralOutput());
        }
    }

    public void encodeLiteralOutput(LiteralOutputDescription output,
                                    LiteralOutputType xbOutput) {
        encodeLiteralDescription(output, xbOutput);
    }

    public void encodeLiteralDescription(LiteralDescription description,
                                         LiteralOutputType xbDescription) {
        xbDescription.addNewDataType().setReference(description.getDataType());
        if (description.getDefaultUOM().isPresent() || !description
            .getSupportedUOM()
            .isEmpty()) {
            SupportedUOMsType xbUoms = xbDescription.addNewUOMs();
            if (description.getDefaultUOM().isPresent()) {
                xbUoms.addNewDefault().addNewUOM().setStringValue(description
                        .getDefaultUOM().get().getValue());
            }
            if (!description.getSupportedUOM().isEmpty()) {
                UOMsType xbSupported = xbUoms.addNewSupported();
                for (OwsUOM uom : description.getSupportedUOM()) {
                    xbSupported.addNewUOM().setStringValue(uom.getValue());
                }
            }
        }
    }

    public void encodeBoundingBoxOutput(BoundingBoxOutputDescription output,
                                        SupportedCRSsType xbOutput) {
        encodeBoundingBoxDescription(output, xbOutput);
    }

    public void encodeBoundingBoxDescription(
            BoundingBoxDescription description,
            SupportedCRSsType xbDescription) {
        if (description.getDefaultCRS().isPresent()) {
            xbDescription.addNewDefault().setCRS(
                    description.getDefaultCRS().get().getValue());
        }
        if (!description.getSupportedCRS().isEmpty()) {
            CRSsType supported = xbDescription.addNewSupported();
            for (OwsCRS crs : description.getSupportedCRS()) {
                supported.addCRS(crs.getValue());
            }
        }
    }

    public void encodeComplexOutput(ComplexOutputDescription output,
                                    SupportedComplexDataType xbOutput) {
        encodeComplexDescription(output, xbOutput);
    }

    public void encodeComplexDescription(ComplexDescription description,
                                         SupportedComplexDataType xbDescription) {
        description.getDefaultFormat().encodeTo(xbDescription.addNewDefault()
                .addNewFormat());
        if (!description.getSupportedFormats().isEmpty()) {
            ComplexDataCombinationsType supported = xbDescription
                    .addNewSupported();
            for (Format format : description.getSupportedFormats()) {
                format.encodeTo(supported.addNewFormat());
            }
        }
    }

    public void encodeInputDescriptions(
            Iterable<? extends ProcessInputDescription> inputDescriptions,
            DataInputs xbInputDescriptions) {
        for (ProcessInputDescription inputDescription : inputDescriptions) {
            encodeInputDescription(inputDescription, xbInputDescriptions
                                   .addNewInput());
        }
    }

    public void encodeInputDescription(ProcessInputDescription input,
                                       InputDescriptionType xbInput) {
        encodeDescription(input, xbInput);
        xbInput.setMaxOccurs(input.getOccurence().getMax());
        xbInput.setMinOccurs(input.getOccurence().getMin());
        if (input.isBoundingBox()) {
            encodeBoundingBoxInput(input.asBoundingBox(),
                                   xbInput.addNewBoundingBoxData());
        } else if (input.isComplex()) {
            encodeComplexInput(input.asComplex(),
                               xbInput.addNewComplexData());
        } else if (input.isLiteral()) {
            encodeLiteralInput(input.asLiteral(),
                               xbInput.addNewLiteralData());
        }
    }

    public void encodeComplexInput(ComplexInputDescription input,
                                   SupportedComplexDataInputType xbInput) {
        if (input.getMaximumMegabytes().isPresent()) {
            xbInput.setMaximumMegabytes(input.getMaximumMegabytes().get());
        }
        encodeComplexDescription(input, xbInput);
    }

    public void encodeBoundingBoxInput(BoundingBoxInputDescription input,
                                       SupportedCRSsType xbInput) {
        encodeBoundingBoxDescription(input, xbInput);
    }

    public void encodeLiteralInput(LiteralInputDescription input,
                                   LiteralInputType xbInput) {
        encodeLiteralDescription(input, xbInput);
        if (input.getAllowedValues().isAny()) {
            xbInput.addNewAnyValue();
        } else {
            encodeAllowedValues(input.getAllowedValues(),
                                xbInput.addNewAllowedValues());
        }
    }

    public void encodeAllowedValues(OwsAllowedValues allowedValues,
                                    AllowedValues xbAllowedValues) {
        for (OwsValueRestriction restriction : allowedValues) {
            if (restriction.isRange()) {
                encodeAllowedRange(restriction.asRange(),
                                   xbAllowedValues.addNewRange());
            } else if (restriction.isValue()) {
                encodeAllowedValue(restriction.asValue(),
                                   xbAllowedValues.addNewValue());
            }
        }
    }

    public void encodeAllowedRange(OwsAllowedRange range, RangeType xbRange) {
        if (range.getLowerBound().isPresent()) {
            xbRange.addNewMinimumValue()
                    .setStringValue(range.getLowerBound().get());
        }
        if (range.getUpperBound().isPresent()) {
            xbRange.addNewMaximumValue()
                    .setStringValue(range.getUpperBound().get());
        }
        xbRange.setRangeClosure(Collections.singletonList(range.getType()));
    }

    public void encodeAllowedValue(OwsAllowedValue value, ValueType xbValue) {
        xbValue.setStringValue(value.getValue());
    }
}
