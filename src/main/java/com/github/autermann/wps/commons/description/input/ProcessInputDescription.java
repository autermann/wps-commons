package com.github.autermann.wps.commons.description.input;

import static com.google.common.base.Preconditions.checkNotNull;

import net.opengis.wps.x100.InputDescriptionType;

import com.github.autermann.wps.commons.description.AbstractDescription;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessInputDescription extends AbstractDescription {

    private final InputOccurence occurence;

    public ProcessInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence) {
        super(identifier, title, abstrakt);
        this.occurence = checkNotNull(occurence);
    }

    public InputOccurence getOccurence() {
        return this.occurence;
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
