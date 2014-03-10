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
package com.github.autermann.wps.commons.description;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.github.autermann.wps.commons.description.input.ProcessInputDescription;
import com.github.autermann.wps.commons.description.output.ProcessOutputDescription;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.github.autermann.wps.commons.description.ows.OwsLanguageString;
import com.google.common.base.Strings;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ProcessDescription extends AbstractDescription {

    private final Map<OwsCodeType, ProcessInputDescription> inputs;
    private final Map<OwsCodeType, ProcessOutputDescription> outputs;
    private final boolean storeSupported;
    private final boolean statusSupported;
    private final String version;

    public ProcessDescription(OwsCodeType identifier,
                              OwsLanguageString title,
                              OwsLanguageString abstrakt,
                              String version) {
        this(identifier, title, abstrakt, version, false, false);

    }

    public ProcessDescription(OwsCodeType identifier,
                              OwsLanguageString title,
                              OwsLanguageString abstrakt,
                              String version,
                              boolean storeSupported,
                              boolean statusSupported) {
        super(identifier, title, abstrakt);
        this.version = checkNotNull(Strings.emptyToNull(version));
        this.inputs = new HashMap<>();
        this.outputs = new HashMap<>();
        this.storeSupported = storeSupported;
        this.statusSupported = statusSupported;
    }

    public void addInput(ProcessInputDescription input) {
        this.inputs.put(input.getID(), input);
    }

    public void addInputs(Iterable<? extends ProcessInputDescription> inputs) {
        for (ProcessInputDescription input : inputs) {
            addInput(input);
        }
    }

    public void addOutput(ProcessOutputDescription output) {
        this.outputs.put(output.getID(), output);
    }

    public void addOutputs(Iterable<? extends ProcessOutputDescription> outputs) {
        for (ProcessOutputDescription output : outputs) {
            addOutput(output);
        }
    }

    public ProcessInputDescription getInput(OwsCodeType id) {
        return this.inputs.get(id);
    }

    public ProcessOutputDescription getOutput(OwsCodeType id) {
        return this.outputs.get(id);
    }

    public Set<OwsCodeType> getInputs() {
        return Collections.unmodifiableSet(inputs.keySet());
    }

    public Collection<ProcessInputDescription> getInputDescriptions() {
        return Collections.unmodifiableCollection(inputs.values());
    }

    public Set<OwsCodeType> getOutputs() {
        return Collections.unmodifiableSet(outputs.keySet());
    }

    public Collection<ProcessOutputDescription> getOutputDescriptions() {
        return Collections.unmodifiableCollection(outputs.values());
    }

    public boolean isStoreSupported() {
        return storeSupported;
    }

    public boolean isStatusSupported() {
        return statusSupported;
    }

    public String getVersion() {
        return version;
    }
}
