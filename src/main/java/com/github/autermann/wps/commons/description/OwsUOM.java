package com.github.autermann.wps.commons.description;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.opengis.ows.x11.DomainMetadataType;
import net.opengis.wps.x100.LiteralInputType;
import net.opengis.wps.x100.LiteralOutputType;
import net.opengis.wps.x100.SupportedUOMsType;
import net.opengis.wps.x100.UOMsType;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public class OwsUOM {

    private final String reference;
    private final String value;

    public OwsUOM(String reference, String value) {
        this.reference = emptyToNull(reference);
        this.value = checkNotNull(emptyToNull(value));
    }

    public OwsUOM(String value) {
        this(null, value);
    }

    public Optional<String> getCodeSpace() {
        return Optional.fromNullable(this.reference);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.reference, this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OwsUOM that = (OwsUOM) obj;
        return Objects.equal(this.getValue(), that.getValue()) &&
               Objects.equal(this.getCodeSpace(), that.getCodeSpace());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).omitNullValues()
                .add("reference", getCodeSpace().orNull())
                .add("value", getValue())
                .toString();
    }

    public static Iterable<OwsUOM> getSupported(LiteralOutputType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return getSupported(xb.getUOMs());
        }
    }

    public static Iterable<OwsUOM> getSupported(LiteralInputType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return getSupported(xb.getUOMs());
        }
    }

    public static Iterable<OwsUOM> getSupported(SupportedUOMsType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return of(xb.getSupported());
        }
    }

    public static Iterable<OwsUOM> of(UOMsType xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            return of(xb.getUOMArray());
        }
    }

    public static Iterable<OwsUOM> of(DomainMetadataType[] xb) {
        if (xb == null) {
            return Collections.emptyList();
        } else {
            List<OwsUOM> uoms = new ArrayList<>(xb.length);
            for (DomainMetadataType dmt : xb) {
                OwsUOM uom = of(dmt);
                if (uom != null) {
                    uoms.add(uom);
                }
            }
            return uoms;
        }
    }
    public static OwsUOM getDefault(LiteralOutputType xb) {
        return xb == null ? null : getDefault(xb.getUOMs());
    }

    public static OwsUOM getDefault(LiteralInputType xb) {
        return xb == null ? null : getDefault(xb.getUOMs());
    }

    public static OwsUOM getDefault(SupportedUOMsType xb) {
        return xb == null ? null : of(xb.getDefault());
    }

    public static OwsUOM of(SupportedUOMsType.Default xb) {
        return xb == null ? null : of(xb.getUOM());
    }

    public static OwsUOM of(DomainMetadataType xb) {
        return xb == null ? null : new OwsUOM(xb.getReference(),
                                              xb.getStringValue());
    }

}
