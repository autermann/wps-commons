package com.github.autermann.wps.commons.description.output;

import static com.google.common.base.Preconditions.checkNotNull;

import net.opengis.wps.x100.OutputDescriptionType;

import com.github.autermann.wps.commons.Identifiable;
import com.github.autermann.wps.commons.description.OwsCodeType;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessOutputDescription implements
        Identifiable<OwsCodeType> {

    private final OwsCodeType identifier;

    public ProcessOutputDescription(OwsCodeType identifier) {
        this.identifier = checkNotNull(identifier);
    }

    @Override
    public OwsCodeType getID() {
        return this.identifier;
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

    public ComplexOutputDescription asComplex() {
        throw new UnsupportedOperationException();
    }

    public LiteralOutputDescription asLiteral() {
        throw new UnsupportedOperationException();
    }

    public BoundingBoxOutputDescription asBoundingBox() {
        throw new UnsupportedOperationException();
    }

    public static ProcessOutputDescription of(OutputDescriptionType odt) {
        if (odt.getBoundingBoxOutput() != null) {
            return BoundingBoxOutputDescription.of(odt);
        } else if (odt.getComplexOutput() != null) {
            return ComplexOutputDescription.of(odt);
        } else if (odt.getLiteralOutput() != null) {
            return LiteralOutputDescription.of(odt);
        } else {
            throw new IllegalArgumentException("Can not identify output type");
        }
    }
}
