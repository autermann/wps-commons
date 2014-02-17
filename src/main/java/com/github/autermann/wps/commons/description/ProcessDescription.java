/*
 * Copyright (C) 2014 Christian Autermann
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType;

import com.github.autermann.wps.commons.Identifiable;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class ProcessDescription implements Identifiable<OwsCodeType> {

    private final OwsCodeType identifier;
    private final Map<OwsCodeType, ProcessInputDescription> inputs;
    private final Map<OwsCodeType, ProcessOutputDescription> outputs;
    private final boolean storeSupported;
    private final boolean statusSupported;

    public ProcessDescription(OwsCodeType identifier) {
        this(identifier, false, false);

    }

    public ProcessDescription(OwsCodeType identifier,
                              boolean storeSupported,
                              boolean statusSupported) {
        this.inputs = new HashMap<>();
        this.outputs = new HashMap<>();
        this.identifier = identifier;
        this.storeSupported = storeSupported;
        this.statusSupported = statusSupported;
    }

    @Override
    public OwsCodeType getID() {
        return this.identifier;
    }

    public void addInput(ProcessInputDescription input) {
        this.inputs.put(input.getID(), input);
    }

    public void addOutput(ProcessOutputDescription output) {
        this.outputs.put(output.getID(), output);
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

    public Set<OwsCodeType> getOutputs() {
        return Collections.unmodifiableSet(outputs.keySet());
    }

    public boolean isStoreSupported() {
        return storeSupported;
    }

    public boolean isStatusSupported() {
        return statusSupported;
    }

    public static ProcessDescription of(ProcessDescriptionType xb) {
        OwsCodeType id = OwsCodeType.of(xb.getIdentifier());
        ProcessDescription pd = new ProcessDescription(id, xb
                .getStoreSupported(), xb.getStatusSupported());
        for (InputDescriptionType xbInputDescription : xb.getDataInputs().getInputArray()) {
            pd.addInput(ProcessInputDescription.of(xbInputDescription));
        }
        for (OutputDescriptionType xbOutputDescription : xb.getProcessOutputs().getOutputArray()) {
            pd.addOutput(ProcessOutputDescription.of(xbOutputDescription));
        }
        return pd;
    }
}
