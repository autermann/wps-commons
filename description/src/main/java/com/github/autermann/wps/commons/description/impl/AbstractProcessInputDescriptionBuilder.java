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
package com.github.autermann.wps.commons.description.impl;

import java.math.BigInteger;
import java.util.Objects;

import com.github.autermann.wps.commons.description.InputOccurence;
import com.github.autermann.wps.commons.description.ProcessInputDescription;
import com.github.autermann.wps.commons.description.ProcessInputDescriptionBuilder;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractProcessInputDescriptionBuilder<T extends ProcessInputDescription, B extends ProcessInputDescriptionBuilder<T, B>>
        extends AbstractDescriptionBuilder<T, B>
        implements ProcessInputDescriptionBuilder<T, B> {

    private BigInteger minimalOccurence = BigInteger.ONE;
    private BigInteger maximalOccurence = BigInteger.ONE;

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withMinimalOccurence(BigInteger min) {
        if (min != null) {
            Preconditions.checkArgument(min.compareTo(BigInteger.ZERO) > 0, "minimalOccurence");
            this.minimalOccurence = min;
        } else {
            this.minimalOccurence = BigInteger.ONE;
        }
        return (B) this;
    }

    @Override
    public B withMinimalOccurence(long min) {
        return withMinimalOccurence(BigInteger.valueOf(min));
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withMaximalOccurence(BigInteger max) {
        if (max != null) {
            Preconditions
                    .checkArgument(max.compareTo(BigInteger.ZERO) > 0, "maximalOccurence");
            this.maximalOccurence = max;
        } else {
            this.maximalOccurence = BigInteger.ONE;
        }
        return (B) this;
    }

    @Override
    public B withMaximalOccurence(long max) {
        return withMaximalOccurence(BigInteger.valueOf(max));
    }

    @Override
    public B withOccurence(InputOccurence occurence) {
        Objects.requireNonNull(occurence);
        return withMaximalOccurence(occurence.getMax())
                .withMinimalOccurence(occurence.getMin());
    }

    BigInteger getMinimalOccurence() {
        return minimalOccurence;
    }

    BigInteger getMaximalOccurence() {
        return maximalOccurence;
    }

}
