package com.github.autermann.wps.commons.description.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.InputDescriptionType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ComplexInputDescription extends ProcessInputDescription {

    private final Set<Format> formats;
    private final Format defaultFormat;

    public ComplexInputDescription(OwsCodeType identifier, BigInteger minOccurs,
                                   BigInteger maxOccurs, Format defaultFormat,
                                   Iterable<Format> formats) {
        super(identifier, minOccurs, maxOccurs);
        this.formats = Sets.newHashSet(checkNotNull(formats));
        this.defaultFormat = checkNotNull(defaultFormat);
    }

    public Set<Format> getFormats() {
        return Collections.unmodifiableSet(formats);
    }

    public Format getDefaultFormat() {
        return defaultFormat;
    }

    @Override
    public ComplexInputDescription asComplex() {
        return this;
    }

    @Override
    public boolean isComplex() {
        return true;
    }

    public static ComplexInputDescription of(InputDescriptionType idt) {
        return new ComplexInputDescription(
                OwsCodeType.of(idt.getIdentifier()),
                idt.getMinOccurs(),
                idt.getMaxOccurs(),
                Format.getDefault(idt),
                Format.getSupported(idt));
    }

}
