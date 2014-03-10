package com.github.autermann.wps.commons.description.output;

import com.github.autermann.wps.commons.description.output.BoundingBoxOutputDescription;
import com.github.autermann.wps.commons.description.output.ComplexOutputDescription;
import com.github.autermann.wps.commons.description.output.LiteralOutputDescription;
import java.util.Arrays;
import java.util.Collections;

import net.opengis.wps.x100.LiteralOutputType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.SupportedCRSsType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.Identifiable;
import com.github.autermann.wps.commons.description.OwsCodeType;
import com.github.autermann.wps.commons.description.OwsUOM;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessOutputDescription implements
        Identifiable<OwsCodeType> {

    private final OwsCodeType identifier;

    public ProcessOutputDescription(OwsCodeType identifier) {
        this.identifier = Preconditions.checkNotNull(identifier);
    }

    @Override
    public OwsCodeType getID() {
        return this.identifier;
    }

    public boolean isComplex() {
        return this instanceof ComplexOutputDescription;
    }

    public boolean isLiteral() {
        return this instanceof LiteralOutputDescription;
    }

    public boolean isBoundingBox() {
        return this instanceof BoundingBoxOutputDescription;
    }

    public ComplexOutputDescription asComplex() {
        if (!isComplex()) {
            throw new UnsupportedOperationException();
        } else {
            return (ComplexOutputDescription) this;
        }
    }

    public LiteralOutputDescription asLiteral() {
        if (!isLiteral()) {
            throw new UnsupportedOperationException();
        } else {
            return (LiteralOutputDescription) this;
        }
    }

    public BoundingBoxOutputDescription asBoundingBox() {
        if (!isBoundingBox()) {
            throw new UnsupportedOperationException();
        } else {
            return (BoundingBoxOutputDescription) this;
        }
    }

    public static ProcessOutputDescription of(OutputDescriptionType odt) {
        OwsCodeType id = OwsCodeType.of(odt.getIdentifier());
        if (odt.getBoundingBoxOutput() != null) {
            SupportedCRSsType boundingBoxOutput = odt.getBoundingBoxOutput();
            final String defaultCRS;
            final Iterable<String> supportedCRS;
            if (boundingBoxOutput.getSupported() != null) {
                supportedCRS
                        = Arrays.asList(boundingBoxOutput.getSupported()
                                .getCRSArray());
            } else {
                supportedCRS = Collections.emptyList();
            }
            if (boundingBoxOutput.getDefault() != null) {
                defaultCRS = boundingBoxOutput.getDefault().getCRS();
            } else {
                defaultCRS = null;
            }
            return new BoundingBoxOutputDescription(id, defaultCRS, supportedCRS);
        } else if (odt.getComplexOutput() != null) {
            return new ComplexOutputDescription(id, Format.getDefault(odt), Format.getSupported(odt));
        } else if (odt.getLiteralOutput() != null) {
            LiteralOutputType literalOutput = odt.getLiteralOutput();
            return new LiteralOutputDescription(id, literalOutput.getDataType()
                    .getStringValue(), OwsUOM.getDefault(literalOutput), OwsUOM.getSupported(literalOutput));
        } else {
            throw new IllegalArgumentException("Can not identify output type of " +
                                               id);
        }
    }

}
