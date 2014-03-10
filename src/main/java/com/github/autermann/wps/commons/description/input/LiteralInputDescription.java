package com.github.autermann.wps.commons.description.input;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.LiteralInputType;

import com.github.autermann.wps.commons.description.OwsAllowedValues;
import com.github.autermann.wps.commons.description.OwsCodeType;
import com.github.autermann.wps.commons.description.OwsUOM;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralInputDescription extends ProcessInputDescription {

    private final String dataType;
    private final Set<OwsUOM> uoms;
    private final OwsUOM defaultUom;
    private final OwsAllowedValues allowedValues;

    public LiteralInputDescription(OwsCodeType identifier, BigInteger minOccurs,
                                   BigInteger maxOccurs, String dataType,
                                   OwsAllowedValues allowedValues,
                                   OwsUOM defaultUom, Iterable<OwsUOM> uoms) {
        super(identifier, minOccurs, maxOccurs);
        this.dataType = Preconditions.checkNotNull(dataType);
        this.allowedValues = Preconditions.checkNotNull(allowedValues);
        this.uoms = Sets.newHashSet(Preconditions.checkNotNull(uoms));
        this.defaultUom = defaultUom;
    }

    public String getDataType() {
        return dataType;
    }

    public OwsAllowedValues getAllowedValues() {
        return allowedValues;
    }

    public Set<OwsUOM> getUoms() {
        return Collections.unmodifiableSet(uoms);
    }

    public OwsUOM getDefaultUom() {
        return defaultUom;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public LiteralInputDescription asLiteral() {
        return this;
    }

    public static LiteralInputDescription of(InputDescriptionType idt) {
        LiteralInputType literalData = idt.getLiteralData();
        return new LiteralInputDescription(
                OwsCodeType.of(idt.getIdentifier()),
                idt.getMinOccurs(),
                idt.getMaxOccurs(),
                literalData.getDataType()
                .getStringValue(),
                OwsAllowedValues.of(literalData.getAllowedValues()),
                OwsUOM.getDefault(literalData),
                OwsUOM.getSupported(literalData));
        // TODO inputDescription.getLiteralData().getValuesReference()
    }

}
