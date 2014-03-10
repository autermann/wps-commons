package com.github.autermann.wps.commons.description.output;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Set;

import net.opengis.wps.x100.LiteralOutputType;
import net.opengis.wps.x100.OutputDescriptionType;

import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.github.autermann.wps.commons.description.ows.OwsUOM;
import com.google.common.base.Optional;
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

    public LiteralOutputDescription(OwsCodeType identifier,
                                    OwsLanguageString title,
                                    OwsLanguageString abstrakt,
                                    String dataType,
                                    OwsUOM defaultUOM,
                                    Iterable<OwsUOM> uoms) {
        super(identifier, title, abstrakt);
        this.dataType = checkNotNull(dataType);
        this.defaultUOM = defaultUOM;
        this.uoms = Sets.newHashSet(checkNotNull(uoms));
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

    public static LiteralOutputDescription of(OutputDescriptionType odt) {
        LiteralOutputType literalOutput = odt.getLiteralOutput();
        return new LiteralOutputDescription(
                OwsCodeType.of(odt.getIdentifier()),
                OwsLanguageString.of(odt.getTitle()),
                OwsLanguageString.of(odt.getAbstract()),
                literalOutput.getDataType().getStringValue(),
                OwsUOM.getDefault(literalOutput),
                OwsUOM.getSupported(literalOutput));
    }

}
