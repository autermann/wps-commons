package com.github.autermann.wps.commons.description;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.Format;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class ComplexInputDescription extends ProcessInputDescription {

    private final Set<Format> formats;
    private final Format defaultFormat;

    public ComplexInputDescription(OwsCodeType identifier, BigInteger minOccurs,
                                   BigInteger maxOccurs, Format defaultFormat,
                                   Iterable<Format> formats) {
        super(identifier, minOccurs, maxOccurs);
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
