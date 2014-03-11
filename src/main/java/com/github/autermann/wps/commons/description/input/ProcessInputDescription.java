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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;

import com.github.autermann.wps.commons.description.AbstractDescription;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessInputDescription extends AbstractDescription {

    private final InputOccurence occurence;

    protected ProcessInputDescription(Builder<?, ?> builder) {
        super(builder);
        this.occurence = new InputOccurence(builder.getMinimalOccurence(),
                                            builder.getMaximalOccurence());
    }

    public InputOccurence getOccurence() {
        return this.occurence;
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isComplex() {
        return false;
    }

    public boolean isBoundingBox() {
        return false;
    }

    public ComplexInputDescription asComplex() {
        throw new UnsupportedOperationException();
    }

    public LiteralInputDescription asLiteral() {
        throw new UnsupportedOperationException();
    }

    public BoundingBoxInputDescription asBoundingBox() {
        throw new UnsupportedOperationException();
    }

    public static abstract class Builder<T extends ProcessInputDescription, B extends Builder<T, B>>
            extends AbstractDescription.Builder<T, B> {
        private BigInteger minimalOccurence = BigInteger.ONE;
        private BigInteger maximalOccurence = BigInteger.ONE;

        @SuppressWarnings("unchecked")
        public B withMinimalOccurence(BigInteger min) {
            if (min != null) {
                checkArgument(min.compareTo(BigInteger.ZERO) > 0, "minimalOccurence");
                this.minimalOccurence = min;
            } else {
                this.minimalOccurence = BigInteger.ONE;
            }
            return (B) this;
        }

        public B withMinimalOccurence(long min) {
            return withMinimalOccurence(BigInteger.valueOf(min));
        }

        @SuppressWarnings("unchecked")
        public B withMaximalOccurence(BigInteger max) {
            if (max != null) {
                checkArgument(max.compareTo(BigInteger.ZERO) > 0, "maximalOccurence");
                this.maximalOccurence = max;
            } else {
                this.maximalOccurence = BigInteger.ONE;
            }
            return (B) this;
        }

        public B withMaximalOccurence(long max) {
            return withMaximalOccurence(BigInteger.valueOf(max));
        }

        public B withOccurence(InputOccurence occurence) {
            checkNotNull(occurence);
            return withMaximalOccurence(occurence.getMax())
                    .withMinimalOccurence(occurence.getMin());
        }

        private BigInteger getMinimalOccurence() {
            return minimalOccurence;
        }

        private BigInteger getMaximalOccurence() {
            return maximalOccurence;
        }
    }
}
