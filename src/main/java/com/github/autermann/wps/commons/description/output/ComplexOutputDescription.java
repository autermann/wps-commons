package com.github.autermann.wps.commons.description.output;

import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.Format;
import com.github.autermann.wps.commons.description.OwsCodeType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class ComplexOutputDescription extends ProcessOutputDescription {

    private final Set<Format> formats;
    private final Format defaultFormat;

    public ComplexOutputDescription(OwsCodeType identifier, Format defaultFormat,
                                    Iterable<Format> formats) {
        super(identifier);
        this.formats = Sets.newHashSet(Preconditions.checkNotNull(formats));
        this.defaultFormat = Preconditions.checkNotNull(defaultFormat);
    }

    public Set<Format> getFormats() {
        return Collections.unmodifiableSet(formats);
    }

    public Format getDefaultFormat() {
        return defaultFormat;
    }
}