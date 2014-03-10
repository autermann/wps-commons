package com.github.autermann.wps.commons.description.ows;



import net.opengis.ows.x11.ValueType;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class OwsAllowedValue implements OwsValueRestriction {

    private final String value;

    public OwsAllowedValue(String value) {
        this.value = Preconditions.checkNotNull(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            OwsAllowedValue that = (OwsAllowedValue) obj;
            return Objects.equal(this.value, that.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).addValue(this.value).toString();
    }

    public static OwsAllowedValue of(ValueType xbValue) {
        return new OwsAllowedValue(xbValue.getStringValue());
    }

}
