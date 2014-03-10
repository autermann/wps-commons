package com.github.autermann.wps.commons.description.input;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.LiteralInputType;
import net.opengis.wps.x100.SupportedCRSsType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.Identifiable;
import com.github.autermann.wps.commons.description.OwsAllowedValues;
import com.github.autermann.wps.commons.description.OwsCodeType;
import com.github.autermann.wps.commons.description.OwsUOM;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public abstract class ProcessInputDescription implements Identifiable<OwsCodeType> {

    private final OwsCodeType identifier;
    private final BigInteger minOccurs;
    private final BigInteger maxOccurs;

    public ProcessInputDescription(OwsCodeType identifier, BigInteger minOccurs,
                                   BigInteger maxOccurs) {
        this.identifier = Preconditions.checkNotNull(identifier);
        this.minOccurs = minOccurs == null ? BigInteger.ONE : minOccurs;
        this.maxOccurs = maxOccurs == null ? BigInteger.ONE : maxOccurs;
    }

    @Override
    public OwsCodeType getID() {
        return this.identifier;
    }

    public BigInteger getMinOccurs() {
        return this.minOccurs;
    }

    public BigInteger getMaxOccurs() {
        return this.maxOccurs;
    }

    public boolean isComplex() {
        return this instanceof LiteralInputDescription;
    }

    public boolean isLiteral() {
        return this instanceof ComplexInputDescription;
    }

    public boolean isBoundingBox() {
        return this instanceof BoundingBoxInputDescription;
    }

    public ComplexInputDescription asComplex() {
        if (!isComplex()) {
            throw new UnsupportedOperationException();
        } else {
            return (ComplexInputDescription) this;
        }
    }

    public LiteralInputDescription asLiteral() {
        if (!isLiteral()) {
            throw new UnsupportedOperationException();
        } else {
            return (LiteralInputDescription) this;
        }
    }

    public BoundingBoxInputDescription asBoundingBox() {
        if (!isBoundingBox()) {
            throw new UnsupportedOperationException();
        } else {
            return (BoundingBoxInputDescription) this;
        }
    }

    public static ProcessInputDescription of(InputDescriptionType idt) {
        OwsCodeType id = OwsCodeType.of(idt.getIdentifier());
        BigInteger minOccurs = idt.getMinOccurs();
        BigInteger maxOccurs = idt.getMaxOccurs();
        if (idt.getBoundingBoxData() != null) {
            SupportedCRSsType boundingBoxData = idt.getBoundingBoxData();
            final String defaultCRS;
            final Iterable<String> supportedCRS;
            if (boundingBoxData.getSupported() != null) {
                supportedCRS
                        = Arrays.asList(boundingBoxData.getSupported()
                        .getCRSArray());
            } else {
                supportedCRS = Collections.emptyList();
            }
            if (boundingBoxData.getDefault() != null) {
                defaultCRS = boundingBoxData.getDefault().getCRS();
            } else {
                defaultCRS = null;
            }
            return new BoundingBoxInputDescription(id, minOccurs, maxOccurs, defaultCRS, supportedCRS);
        } else if (idt.getLiteralData() != null) {
            LiteralInputType literalData = idt.getLiteralData();
            return new LiteralInputDescription(id, minOccurs, maxOccurs, literalData.getDataType()
                    .getStringValue(), OwsAllowedValues.of(literalData.getAllowedValues()), OwsUOM.getDefault(literalData), OwsUOM.getSupported(literalData));
            // TODO inputDescription.getLiteralData().getValuesReference()
        } else if (idt.getComplexData() != null) {
            return new ComplexInputDescription(id, minOccurs, maxOccurs, Format.getDefault(idt), Format.getSupported(idt));
        } else {
            throw new IllegalArgumentException("Can not identify input type of " +
                                               id);
        }
    }

}
