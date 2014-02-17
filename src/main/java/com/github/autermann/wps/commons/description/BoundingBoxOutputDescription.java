package com.github.autermann.wps.commons.description;

import java.util.Collections;
import java.util.Set;


import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class BoundingBoxOutputDescription extends ProcessOutputDescription {

    private final Set<String> supportedCRS;
    private final String defaultCRS;

    public BoundingBoxOutputDescription(OwsCodeType identifier,
                                        String defaultCRS,
                                        Iterable<String> supportedCRS) {
        super(identifier);
        this.supportedCRS
                = Sets.newHashSet(Preconditions.checkNotNull(supportedCRS));
        this.defaultCRS = defaultCRS;
    }

    public Set<String> getSupportedCRS() {
        return Collections.unmodifiableSet(supportedCRS);
    }

    public Optional<String> getDefaultCRS() {
        return Optional.fromNullable(this.defaultCRS);
    }

}
