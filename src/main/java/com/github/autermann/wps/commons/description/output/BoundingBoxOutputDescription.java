package com.github.autermann.wps.commons.description.output;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.SupportedCRSsType;

import com.github.autermann.wps.commons.description.ows.OwsCRS;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class BoundingBoxOutputDescription extends ProcessOutputDescription {

    private final Set<OwsCRS> supportedCRS;
    private final OwsCRS defaultCRS;

    public BoundingBoxOutputDescription(OwsCodeType identifier,
                                        OwsCRS defaultCRS,
                                        Iterable<OwsCRS> supportedCRS) {
        super(identifier);
        this.supportedCRS = Sets.newHashSet(checkNotNull(supportedCRS));
        this.defaultCRS = defaultCRS;
    }

    public Set<OwsCRS> getSupportedCRS() {
        return Collections.unmodifiableSet(supportedCRS);
    }

    public Optional<OwsCRS> getDefaultCRS() {
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

    public static BoundingBoxOutputDescription of(OutputDescriptionType odt) {
        SupportedCRSsType boundingBoxOutput = odt.getBoundingBoxOutput();
        return new BoundingBoxOutputDescription(
                OwsCodeType.of(odt.getIdentifier()),
                OwsCRS.getDefault(boundingBoxOutput),
                OwsCRS.getSupported(boundingBoxOutput));
    }
}
