package com.github.autermann.wps.commons.description;

import com.github.autermann.wps.commons.Identifiable;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.base.Optional;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractDescription implements Identifiable<OwsCodeType> {

    private final OwsCodeType id;
    private final OwsLanguageString title;
    private final OwsLanguageString abstrakt;

    public AbstractDescription(OwsCodeType id,
                               OwsLanguageString title,
                               OwsLanguageString abstrakt) {
        this.id = id;
        this.title = title;
        this.abstrakt = abstrakt;
    }

    @Override
    public OwsCodeType getID() {
        return this.id;
    }

    public OwsLanguageString getTitle() {
        return title;
    }

    public Optional<OwsLanguageString> getAbstract() {
        return Optional.fromNullable(this.abstrakt);
    }
}
