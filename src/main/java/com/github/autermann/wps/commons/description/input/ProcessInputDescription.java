package com.github.autermann.wps.commons.description.input;

import java.math.BigInteger;

import net.opengis.wps.x100.InputDescriptionType;

import com.github.autermann.wps.commons.Identifiable;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessInputDescription implements
        Identifiable<OwsCodeType> {

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
        return false;
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isBoundingBox() {
        return false;
    }

    public ComplexInputDescription asComplex() {
        throw new UnsupportedOperationException();
    }

    public LiteralInputDescription asLiteral() {
        throw new UnsupportedOperationException();
    }

    public BoundingBoxInputDescription asBoundingBox() {
        throw new UnsupportedOperationException();
    }

    public static ProcessInputDescription of(InputDescriptionType idt) {
        if (idt.getBoundingBoxData() != null) {
            return BoundingBoxInputDescription.of(idt);
        } else if (idt.getLiteralData() != null) {
            return LiteralInputDescription.of(idt);
        } else if (idt.getComplexData() != null) {
            return ComplexInputDescription.of(idt);
        } else {
            throw new IllegalArgumentException("Can not identify input type");
        }
    }
}
