package com.github.autermann.wps.commons.description.ows;



import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.opengis.ows.x11.AllowedValuesDocument;
import net.opengis.ows.x11.RangeType;
import net.opengis.ows.x11.ValueType;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class OwsAllowedValues implements Iterable<OwsValueRestriction> {
    private static final OwsAllowedValues ANY = new OwsAllowedValues();
    private final Set<OwsValueRestriction> restrictions = new HashSet<>();

    public void add(OwsValueRestriction restriction) {
        this.restrictions.add(checkNotNull(restriction));
    }

    @Override
    public Iterator<OwsValueRestriction> iterator() {
        return restrictions.iterator();
    }

    public static OwsAllowedValues of(
            AllowedValuesDocument.AllowedValues xbAllowedValues) {
        if (xbAllowedValues == null ||
            (xbAllowedValues.getRangeArray().length == 0 &&
             xbAllowedValues.getValueArray().length == 0)) {
            return any();
        }
        OwsAllowedValues owsAllowedValues = new OwsAllowedValues();
        for (RangeType xbRange : xbAllowedValues.getRangeArray()) {
            owsAllowedValues.add(OwsAllowedRange.of(xbRange));
        }
        for (ValueType xbValue : xbAllowedValues.getValueArray()) {
            owsAllowedValues.add(OwsAllowedValue.of(xbValue));
        }
        return owsAllowedValues;
    }

    public static OwsAllowedValues any() {
        return ANY;
    }
}
