package com.github.autermann.wps.commons.description.output;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.description.OwsCodeType;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class BoundingBoxOutputDescription extends ProcessOutputDescription {

    private final Set<String> supportedCRS;
    private final String defaultCRS;

    public BoundingBoxOutputDescription(OwsCodeType identifier,
                                        String defaultCRS,
                                        Iterable<String> supportedCRS) {
        super(identifier);
        this.supportedCRS = Sets.newHashSet(checkNotNull(supportedCRS));
        this.defaultCRS = defaultCRS;
    }

    public Set<String> getSupportedCRS() {
        return Collections.unmodifiableSet(supportedCRS);
    }

    public Optional<String> getDefaultCRS() {
        return Optional.fromNullable(this.defaultCRS);
    }

    @Override
    public BoundingBoxOutputDescription asBoundingBox() {
        return this;
    }

    @Override
    public boolean isBoundingBox() {
        return true;
    }

}
