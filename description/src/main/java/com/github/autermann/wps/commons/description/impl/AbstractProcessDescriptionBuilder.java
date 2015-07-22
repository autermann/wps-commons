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
package com.github.autermann.wps.commons.description.impl;

import static com.google.common.base.Strings.emptyToNull;

import java.util.Arrays;
import java.util.Objects;

import com.github.autermann.wps.commons.description.ProcessDescription;
import com.github.autermann.wps.commons.description.ProcessDescriptionBuilder;
import com.github.autermann.wps.commons.description.ProcessInputDescription;
import com.github.autermann.wps.commons.description.ProcessInputDescriptionBuilder;
import com.github.autermann.wps.commons.description.ProcessOutputDescription;
import com.github.autermann.wps.commons.description.ProcessOutputDescriptionBuilder;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.google.common.collect.ImmutableMap;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractProcessDescriptionBuilder<T extends ProcessDescription, B extends ProcessDescriptionBuilder<T, B>>
        extends AbstractDescriptionBuilder<T, B>
        implements ProcessDescriptionBuilder<T, B> {

    private final ImmutableMap.Builder<OwsCodeType, ProcessInputDescription> inputs
            = ImmutableMap.builder();
    private final ImmutableMap.Builder<OwsCodeType, ProcessOutputDescription> outputs
            = ImmutableMap.builder();
    private boolean storeSupported = false;
    private boolean statusSupported = false;
    private String version;

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withVersion(String version) {
        this.version = Objects.requireNonNull(emptyToNull(version));
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B storeSupported(boolean storeSupported) {
        this.storeSupported = storeSupported;
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B statusSupported(boolean statusSupported) {
        this.statusSupported = statusSupported;
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withInput(ProcessInputDescription input) {
        if (input != null) {
            inputs.put(input.getId(), input);
        }
        return (B) this;
    }

    @Override
    public B withInput(ProcessInputDescriptionBuilder<?, ?> input) {
        return withInput(input.build());
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withInput(Iterable<ProcessInputDescription> inputs) {
        for (ProcessInputDescription input : inputs) {
            withInput(input);
        }
        return (B) this;
    }

    @Override
    public B withInput(ProcessInputDescription... inputs) {
        return withInput(Arrays.asList(inputs));
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withOutput(ProcessOutputDescription output) {
        if (output != null) {
            outputs.put(output.getId(), output);
        }
        return (B) this;
    }

    @Override
    public B withOutput(
            ProcessOutputDescriptionBuilder<?, ?> output) {
        return withOutput(output.build());
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withOutput(Iterable<ProcessOutputDescription> outputs) {
        for (ProcessOutputDescription output : outputs) {
            withOutput(output);
        }
        return (B) this;
    }

    @Override
    public B withOutput(ProcessOutputDescription... outputs) {
        return withOutput(Arrays.asList(outputs));
    }

    ImmutableMap.Builder<OwsCodeType, ProcessInputDescription> getInputs() {
        return this.inputs;
    }

    ImmutableMap.Builder<OwsCodeType, ProcessOutputDescription> getOutputs() {
        return this.outputs;
    }

    boolean isStoreSupported() {
        return this.storeSupported;
    }

    boolean isStatusSupported() {
        return this.statusSupported;
    }

    String getVersion() {
        return this.version;
    }

}
