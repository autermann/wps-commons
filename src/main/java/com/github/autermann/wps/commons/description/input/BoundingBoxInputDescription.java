package com.github.autermann.wps.commons.description.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.SupportedCRSsType;

import com.github.autermann.wps.commons.description.OwsCRS;
import com.github.autermann.wps.commons.description.OwsCodeType;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class BoundingBoxInputDescription extends ProcessInputDescription {

    private final Set<OwsCRS> supportedCrs;
    private final OwsCRS defaultCrs;

    public BoundingBoxInputDescription(OwsCodeType identifier,
                                       BigInteger minOccurs,
                                       BigInteger maxOccurs,
                                       OwsCRS defaultCrs,
                                       Iterable<OwsCRS> supportedCrs) {
        super(identifier, minOccurs, maxOccurs);
        this.supportedCrs = Sets.newHashSet(checkNotNull(supportedCrs));
        this.defaultCrs = defaultCrs;
    }

    public Set<OwsCRS> getSupportedCRS() {
        return Collections.unmodifiableSet(supportedCrs);
    }

    public Optional<OwsCRS> getDefaultCRS() {
        return Optional.fromNullable(this.defaultCrs);
    }

    @Override
    public boolean isBoundingBox() {
        return true;
    }

    @Override
    public BoundingBoxInputDescription asBoundingBox() {
        return this;
    }

    public static BoundingBoxInputDescription of(InputDescriptionType idt) {
        SupportedCRSsType boundingBoxData = idt.getBoundingBoxData();
        return new BoundingBoxInputDescription(
                OwsCodeType.of(idt.getIdentifier()),
                idt.getMinOccurs(),
                idt.getMaxOccurs(),
                OwsCRS.getDefault(boundingBoxData),
                OwsCRS.getSupported(boundingBoxData));
    }
}
