package com.github.autermann.wps.commons.description.input;

import java.math.BigInteger;

import net.opengis.wps.x100.InputDescriptionType;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class InputOccurence {

    private final BigInteger min;
    private final BigInteger max;

    public InputOccurence(BigInteger min, BigInteger max) {
        this.min = min == null ? BigInteger.ONE : min;
        this.max = max == null ? BigInteger.ONE : max;
    }

    public BigInteger getMin() {
        return min;
    }

    public BigInteger getMax() {
        return max;
    }

    public boolean isInBounds(BigInteger occurence) {
        return this.min.compareTo(occurence) >= 0 &&
               this.max.compareTo(occurence) <= 0;
    }

    public static InputOccurence of(InputDescriptionType idt) {
        return new InputOccurence(idt.getMinOccurs(), idt.getMaxOccurs());
    }
}
