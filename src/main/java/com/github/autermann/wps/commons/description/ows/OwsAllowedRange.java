package com.github.autermann.wps.commons.description.ows;



import net.opengis.ows.x11.RangeType;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class OwsAllowedRange implements OwsValueRestriction {

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

    public static OwsAllowedRange of(RangeType xbRange) {
        String lower
                = xbRange.isSetMaximumValue()
                ? xbRange.getMinimumValue().getStringValue() : null;
        String upper
                = xbRange.isSetMaximumValue()
                ? xbRange.getMaximumValue().getStringValue() : null;
        String type = null;
        if (xbRange.isSetRangeClosure() && !xbRange.getRangeClosure().isEmpty()) {
            type = (String) xbRange.getRangeClosure().get(0);
        }
        if (type == null) {
            type = "closed";
        }
        final BoundType upperType;
        final BoundType lowerType;
        switch (type) {
            case "closed-open":
                lowerType = BoundType.CLOSED;
                upperType = BoundType.OPEN;
                break;
            case "open":
                lowerType = BoundType.OPEN;
                upperType = BoundType.OPEN;
                break;
            case "open-closed":
                lowerType = BoundType.OPEN;
                upperType = BoundType.CLOSED;
                break;
            case "closed":
            default:
                lowerType = BoundType.CLOSED;
                upperType = BoundType.CLOSED;
        }
        return new OwsAllowedRange(lower, lowerType, upper, upperType);
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
