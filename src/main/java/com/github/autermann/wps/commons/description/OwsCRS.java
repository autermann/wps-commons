package com.github.autermann.wps.commons.description;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.SupportedCRSsType;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class OwsCRS {
    private final String value;

    public OwsCRS(String value) {
        this.value = checkNotNull(Strings.emptyToNull(value));
    }

    public String getValue() {
        return value;
    }

    public static OwsCRS of(String value) {
        return new OwsCRS(value);
    }

    public static OwsCRS getDefault(SupportedCRSsType xb) {
        if (xb.getDefault() == null || xb.getDefault().getCRS() == null) {
            return null;
        } else {
            return of(xb.getDefault().getCRS());
        }
    }

    public static Set<OwsCRS> of(Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return Collections.emptySet();
        } else {
            final Set<OwsCRS> supportedCRS = Sets.newHashSet();
            for (String value : values) {
                supportedCRS.add(of(value));
            }
            return supportedCRS;
        }
    }

    public static Set<OwsCRS> getSupported(SupportedCRSsType xb) {
        if (xb.getSupported() == null) {
            return Collections.emptySet();
        } else {
            return of(Arrays.asList(xb.getSupported().getCRSArray()));
        }
    }

}
