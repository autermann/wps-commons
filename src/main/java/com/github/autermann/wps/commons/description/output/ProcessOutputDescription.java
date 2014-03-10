package com.github.autermann.wps.commons.description.output;

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
