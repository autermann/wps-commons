package com.github.autermann.wps.commons.description.output;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.OutputDescriptionType;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ComplexOutputDescription extends ProcessOutputDescription {

    private final Set<Format> formats;
    private final Format defaultFormat;

    public ComplexOutputDescription(OwsCodeType identifier,
                                    OwsLanguageString title,
                                    OwsLanguageString abstrakt,
                                    Format defaultFormat,
                                    Iterable<Format> formats) {
        super(identifier, title, abstrakt);
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
    public ComplexOutputDescription asComplex() {
        return this;
    }

    @Override
    public boolean isComplex() {
        return true;
    }

    public static ComplexOutputDescription of(OutputDescriptionType odt) {
        return new ComplexOutputDescription(
                OwsCodeType.of(odt.getIdentifier()),
                OwsLanguageString.of(odt.getTitle()),
                OwsLanguageString.of(odt.getAbstract()),
                Format.getDefault(odt),
                Format.getSupported(odt));
    }

}
