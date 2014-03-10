package com.github.autermann.wps.commons.description.output;

import java.util.Collections;
import java.util.Set;

import com.github.autermann.wps.commons.description.OwsCodeType;
import com.github.autermann.wps.commons.description.OwsUOM;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralOutputDescription extends ProcessOutputDescription {

    private final String dataType;
    private final Set<OwsUOM> uoms;
    private final OwsUOM defaultUOM;

    public LiteralOutputDescription(OwsCodeType identifier, String dataType,
                                    OwsUOM defaultUOM, Iterable<OwsUOM> uoms) {
        super(identifier);
        this.dataType = Preconditions.checkNotNull(dataType);
        this.defaultUOM = defaultUOM;
        this.uoms = Sets.newHashSet(Preconditions.checkNotNull(uoms));
    }

    public String getDataType() {
        return dataType;
    }

    public Set<OwsUOM> getUoms() {
        return Collections.unmodifiableSet(uoms);
    }

    public Optional<OwsUOM> getDefaultUOM() {
        return Optional.fromNullable(this.defaultUOM);
    }

    @Override
    public LiteralOutputDescription asLiteral() {
        return this;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

}
