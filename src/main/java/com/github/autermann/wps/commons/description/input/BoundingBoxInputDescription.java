package com.github.autermann.wps.commons.description.input;

import com.github.autermann.wps.commons.description.OwsCodeType;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class BoundingBoxInputDescription extends ProcessInputDescription {

    private final Set<String> supportedCrs;
    private final String defaultCrs;

    public BoundingBoxInputDescription(OwsCodeType identifier,
                                       BigInteger minOccurs,
                                       BigInteger maxOccurs,
                                       String defaultCrs,
                                       Iterable<String> supportedCrs) {
        super(identifier, minOccurs, maxOccurs);
        this.supportedCrs = Sets.newHashSet(checkNotNull(supportedCrs));
        this.defaultCrs = Strings.emptyToNull(defaultCrs);
    }

    public Set<String> getSupportedCRS() {
        return Collections.unmodifiableSet(supportedCrs);
    }

    public Optional<String> getDefaultCRS() {
        return Optional.fromNullable(this.defaultCrs);
    }
}
