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
package com.github.autermann.wps.commons;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.nullToEmpty;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.opengis.wps.x100.ComplexDataCombinationType;
import net.opengis.wps.x100.ComplexDataCombinationsType;
import net.opengis.wps.x100.ComplexDataDescriptionType;
import net.opengis.wps.x100.ComplexDataType;
import net.opengis.wps.x100.DocumentOutputDefinitionType;
import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.InputReferenceType;
import net.opengis.wps.x100.OutputDefinitionType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.OutputReferenceType;
import net.opengis.wps.x100.SupportedComplexDataType;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class Format {

    private final Optional<String> mimeType;
    private final Optional<String> encoding;
    private final Optional<String> schema;

    public Format(String mimeType) {
        this(mimeType, null, null);
    }

    public Format(String mimeType, String encoding) {
        this(mimeType, encoding, null);
    }

    public Format(String mimeType, String encoding, String schema) {
        this.mimeType = Optional.ofNullable(emptyToNull(mimeType));
        this.encoding = Optional.ofNullable(emptyToNull(encoding));
        this.schema = Optional.ofNullable(emptyToNull(schema));
    }

    public Format() {
        this(null, null, null);
    }

    public Optional<String> getMimeType() {
        return mimeType;
    }

    public Optional<String> getEncoding() {
        return encoding;
    }

    public Optional<String> getSchema() {
        return schema;
    }

    public boolean isEmpty() {
        return !hasMimeType() && !hasEncoding() && !hasSchema();
    }

    public boolean hasSchema() {
        return getSchema().isPresent();
    }

    public boolean hasEncoding() {
        return getEncoding().isPresent();
    }

    public boolean hasMimeType() {
        return getMimeType().isPresent();
    }

    public boolean hasMimeType(String mimeType) {
        return getMimeType().orElse("").equalsIgnoreCase(nullToEmpty(mimeType));
    }

    public boolean hasEncoding(String encoding) {
        return getEncoding().orElse("").equalsIgnoreCase(nullToEmpty(encoding));
    }

    public boolean hasSchema(String schema) {
        return getSchema().orElse("").equalsIgnoreCase(nullToEmpty(schema));
    }

    public boolean hasMimeType(Format other) {
        return hasMimeType(other.getMimeType().orElse(null));
    }

    public boolean hasEncoding(Format other) {
        return hasEncoding(other.getEncoding().orElse(null));
    }

    public boolean hasSchema(Format other) {
        return hasSchema(other.getSchema().orElse(null));
    }

    public boolean matchesMimeType(String mimeType) {
        return !hasMimeType() || hasMimeType(mimeType);
    }

    public boolean matchesEncoding(String encoding) {
        return !hasEncoding() || hasEncoding(encoding);
    }

    public boolean matchesSchema(String schema) {
        return !hasSchema() || hasSchema(schema);
    }

    public boolean matchesMimeType(Format other) {
        return !hasMimeType() || hasMimeType(other);
    }

    public boolean matchesEncoding(Format other) {
        return !hasEncoding() || hasEncoding(other);
    }

    public boolean matchesSchema(Format other) {
        return !hasSchema() || hasSchema(other);
    }

    public Format withEncoding(String encoding) {
        return new Format(getMimeType().orElse(null),
                          encoding,
                          getSchema().orElse(null));
    }

    public Format withBase64Encoding() {
        return withEncoding("Base64");
    }

    public Format withUTF8Encoding() {
        return withEncoding("UTF-8");
    }

    public Format withSchema(String schema) {
        return new Format(getMimeType().orElse(null),
                          getEncoding().orElse(null),
                          schema);
    }

    public Format withMimeType(String mimeType) {
        return new Format(mimeType,
                          getEncoding().orElse(null),
                          getSchema().orElse(null));
    }

    public Format withoutMimeType() {
        return new Format(null,
                          getEncoding().orElse(null),
                          getSchema().orElse(null));
    }

    public Format withoutEncoding() {
        return new Format(getMimeType().orElse(null),
                          null,
                          getSchema().orElse(null));
    }

    public Format withoutSchema() {
        return new Format(getMimeType().orElse(null),
                          getEncoding().orElse(null),
                          null);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("mimeType", this.mimeType.orElse(null))
                .add("encoding", this.encoding.orElse(null))
                .add("schema", this.schema.orElse(null)).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mimeType, this.encoding, this.schema);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Format) {
            final Format that = (Format) obj;
            return Objects.equals(this.mimeType, that.getMimeType()) &&
                   Objects.equals(this.encoding, that.getEncoding()) &&
                   Objects.equals(this.schema, that.getSchema());
        }
        return false;
    }

    public Predicate<Format> matchingEncoding() {
        return this::hasEncoding;
    }

    public Predicate<Format> matchingSchema() {
        return this::hasSchema;
    }

    public Predicate<Format> matchingMimeType() {
        return this::hasMimeType;
    }

    public void setTo(Consumer<String> encoding,
                      Consumer<String> mimeType,
                      Consumer<String> schema) {
        getEncoding().ifPresent(encoding);
        getMimeType().ifPresent(mimeType);
        getSchema().ifPresent(schema);

    }

    public void encodeTo(InputReferenceType irt) {
        setTo(irt::setEncoding, irt::setMimeType, irt::setSchema);
    }

    public void encodeTo(DocumentOutputDefinitionType dodt) {
        setTo(dodt::setEncoding, dodt::setMimeType, dodt::setSchema);
    }

    public void encodeTo(ComplexDataDescriptionType cddt) {
        setTo(cddt::setEncoding, cddt::setMimeType, cddt::setSchema);
    }

    public void encodeTo(ComplexDataType cdt) {
        setTo(cdt::setEncoding, cdt::setMimeType, cdt::setSchema);
    }

    public void encodeTo(OutputReferenceType ort) {
        setTo(ort::setEncoding, ort::setMimeType, ort::setSchema);
    }

    public static Format of(OutputDefinitionType odt) {
        return new Format(odt.getMimeType(),
                          odt.getEncoding(),
                          odt.getSchema());
    }

    public static Format of(ComplexDataType cdt) {
        return new Format(cdt.getMimeType(),
                          cdt.getEncoding(),
                          cdt.getSchema());
    }

    public static Format of(ComplexDataCombinationType cddct) {
        return of(cddct.getFormat());
    }

    public static Format of(InputReferenceType irt) {
        return new Format(irt.getMimeType(),
                          irt.getEncoding(),
                          irt.getSchema());
    }

    public static Format of(ComplexDataDescriptionType cddt) {
        return new Format(cddt.getMimeType(),
                          cddt.getEncoding(),
                          cddt.getSchema());
    }

    public static Iterable<Format> of(ComplexDataDescriptionType[] list) {
        if (list == null) {
            return Collections.emptyList();
        } else {
            return new ComplexDataDescriptionTypeIterable(list);
        }

    }

    public static Iterable<Format> of(ComplexDataCombinationsType list) {
        if (list == null) {
            return Collections.emptyList();
        } else {
            return of(list.getFormatArray());
        }
    }

    public static Format getDefault(InputDescriptionType idt) {
        if (idt == null) {
            return null;
        } else {
            return getDefault(idt.getComplexData());
        }
    }

    public static Iterable<Format> getSupported(InputDescriptionType idt) {
        if (idt == null) {
            return Collections.emptyList();
        } else {
            return getSupported(idt.getComplexData());
        }
    }

    public static Format getDefault(OutputDescriptionType odt) {
        if (odt == null) {
            return null;
        } else {
            return getDefault(odt.getComplexOutput());
        }
    }

    public static Iterable<Format> getSupported(OutputDescriptionType idt) {
        if (idt == null) {
            return Collections.emptyList();
        } else {
            return getSupported(idt.getComplexOutput());
        }
    }

    public static Iterable<Format> getSupported(SupportedComplexDataType scdt) {
        if (scdt == null) {
            return Collections.emptyList();
        } else {
            return of(scdt.getSupported());
        }
    }

    public static Format getDefault(SupportedComplexDataType scdt) {
        if (scdt == null) {
            return null;
        } else {
            return of(scdt.getDefault());
        }
    }

    private static class ComplexDataDescriptionTypeIterable
            implements Iterable<Format> {
        private final ComplexDataDescriptionType[] supported;

        ComplexDataDescriptionTypeIterable(
                ComplexDataDescriptionType[] supported) {
            this.supported = Objects.requireNonNull(supported);
        }

        @Override
        public Iterator<Format> iterator() {
            return new ComplexDataDescriptionTypeIterator(supported);
        }

    }

    private static class ComplexDataDescriptionTypeIterator
            extends UnmodifiableIterator<Format> {
        final Iterator<ComplexDataDescriptionType> iter;

        ComplexDataDescriptionTypeIterator(ComplexDataDescriptionType[] supported) {
            this.iter = Iterators.forArray(supported);
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public Format next() {
            return Format.of(iter.next());
        }
    }
}
