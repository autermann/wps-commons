/*
 * Copyright (C) 2013 Christian Autermann
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
