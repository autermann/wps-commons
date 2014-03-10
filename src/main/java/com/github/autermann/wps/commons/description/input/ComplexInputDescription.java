package com.github.autermann.wps.commons.description.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.InputDescriptionType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ComplexInputDescription extends ProcessInputDescription {

    private final Set<Format> formats;
    private final Format defaultFormat;

    public ComplexInputDescription(OwsCodeType identifier,
                                   OwsLanguageString title,
                                   OwsLanguageString abstrakt,
                                   InputOccurence occurence,
                                   Format defaultFormat,
                                   Iterable<Format> formats) {
        super(identifier, title, abstrakt, occurence);
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
                OwsLanguageString.of(idt.getTitle()),
                OwsLanguageString.of(idt.getAbstract()),
                InputOccurence.of(idt),
                Format.getDefault(idt),
                Format.getSupported(idt));
    }

}
