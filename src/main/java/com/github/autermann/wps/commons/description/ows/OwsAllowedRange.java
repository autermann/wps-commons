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
package com.github.autermann.wps.commons.description.ows;



import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class OwsAllowedRange extends OwsValueRestriction {
    public static final String CLOSED = "closed";
    public static final String CLOSED_OPEN = "closed-open";
    public static final String OPEN_CLOSED = "open-closed";
    public static final String OPEN = "open";
    private final Bound lowerBound;
    private final Bound upperBound;

    public OwsAllowedRange(String lowerBound, BoundType lowerBoundType,
                           String upperBound, BoundType upperBoundType) {
        this.lowerBound = new Bound(lowerBoundType, lowerBound);
        this.upperBound = new Bound(upperBoundType, upperBound);
    }

    public Optional<String> getLowerBound() {
        return this.lowerBound.getValue();
    }

    public Optional<String> getUpperBound() {
        return this.upperBound.getValue();
    }

    public BoundType getLowerBoundType() {
        return this.lowerBound.getType();
    }

    public BoundType getUpperBoundType() {
        return this.upperBound.getType();
    }

    public String getType() {
        if (getLowerBoundType() == BoundType.OPEN) {
            if (getUpperBoundType() == BoundType.OPEN) {
                return OPEN;
            } else {
                return OPEN_CLOSED;
            }
        } else {
            if (getUpperBoundType() == BoundType.OPEN) {
                return CLOSED_OPEN;
            } else {
                return CLOSED;
            }
        }
    }

    @Override
    public OwsAllowedRange asRange() {
        return this;
    }

    @Override
    public boolean isRange() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.lowerBound, this.upperBound);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            OwsAllowedRange that = (OwsAllowedRange) obj;
            return Objects.equal(this.lowerBound, that.lowerBound) &&
                   Objects.equal(this.upperBound, that.upperBound);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(this.lowerBound.asLower() + ", " +
                          this.upperBound.asUpper()).toString();
    }

    private static class Bound {
        private final BoundType type;
        private final Optional<String> value;

        Bound(BoundType type, String value) {
            this.type = Preconditions.checkNotNull(type);
            this.value = Optional.fromNullable(value);
        }

        BoundType getType() {
            return type;
        }

        Optional<String> getValue() {
            return value;
        }

        String asUpper() {
            return this.getType().asUpper() + getValue().or("\u221e");
        }

        String asLower() {
            return this.getType().asLower() + getValue().or("-\u221e");
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.type, this.value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj.getClass() == getClass()) {
                Bound that = (Bound) obj;
                return Objects.equal(this.type, that.type) &&
                       Objects.equal(this.value, that.value);
            }
            return false;
        }
    }

    public static enum BoundType {
        OPEN {
            @Override
            char asUpper() {
                return ')';
            }

            @Override
            char asLower() {
                return '(';
            }
        },
        CLOSED {
            @Override
            char asUpper() {
                return ']';
            }

            @Override
            char asLower() {
                return '[';
            }
        };
        abstract char asUpper();
        abstract char asLower();
    }

}
