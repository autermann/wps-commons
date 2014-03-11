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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.github.autermann.wps.commons.description.input.ProcessInputDescription;
import com.github.autermann.wps.commons.description.output.ProcessOutputDescription;
import com.github.autermann.wps.commons.description.ows.OwsCodeType;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

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

    protected ProcessDescription(Builder<?, ?> builder) {
        super(builder);
        this.version = checkNotNull(builder.getVersion());
        this.inputs = builder.getInputs().build();
        this.outputs = builder.getOutputs().build();
        this.storeSupported = builder.isStoreSupported();
        this.statusSupported = builder.isStatusSupported();
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

    public static Builder<?, ?> builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl extends Builder<ProcessDescription, BuilderImpl> {
        @Override
        public ProcessDescription build() {
            return new ProcessDescription(this);
        }
    }

    public static abstract class Builder<T extends ProcessDescription, B extends Builder<T, B>>
            extends AbstractDescription.Builder<T, B> {
        private final ImmutableMap.Builder<OwsCodeType, ProcessInputDescription> inputs
                = ImmutableMap.builder();
        private final ImmutableMap.Builder<OwsCodeType, ProcessOutputDescription> outputs
                = ImmutableMap.builder();
        private boolean storeSupported = false;
        private boolean statusSupported = false;
        private String version;

        @SuppressWarnings("unchecked")
        public B withVersion(String version) {
            this.version = checkNotNull(Strings.emptyToNull(version));
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B storeSupported(boolean storeSupported) {
            this.storeSupported = storeSupported;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B statusSupported(boolean statusSupported) {
            this.statusSupported = statusSupported;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withInput(ProcessInputDescription input) {
            if (input != null) {
                inputs.put(input.getID(), input);
            }
            return (B) this;
        }

        public B withInput(ProcessInputDescription.Builder<?, ?> input) {
            return withInput(input.build());
        }

        @SuppressWarnings("unchecked")
        public B withInput(Iterable<ProcessInputDescription> inputs) {
            for (ProcessInputDescription input : inputs) {
                withInput(input);
            }
            return (B) this;
        }

        public B withInput(ProcessInputDescription... inputs) {
            return withInput(Arrays.asList(inputs));
        }

        @SuppressWarnings("unchecked")
        public B withOutput(ProcessOutputDescription output) {
            if (output != null) {
                outputs.put(output.getID(), output);
            }
            return (B) this;
        }

        public B withOutput(ProcessOutputDescription.Builder<?, ?> output) {
            return withOutput(output.build());
        }

        @SuppressWarnings("unchecked")
        public B withOutput(Iterable<ProcessOutputDescription> outputs) {
            for (ProcessOutputDescription output : outputs) {
                withOutput(output);
            }
            return (B) this;
        }

        public B withOutput(ProcessOutputDescription... outputs) {
            return withOutput(Arrays.asList(outputs));
        }

        private ImmutableMap.Builder<OwsCodeType, ProcessInputDescription> getInputs() {
            return inputs;
        }

        private ImmutableMap.Builder<OwsCodeType, ProcessOutputDescription> getOutputs() {
            return outputs;
        }

        private boolean isStoreSupported() {
            return storeSupported;
        }

        private boolean isStatusSupported() {
            return statusSupported;
        }

        private String getVersion() {
            return version;
        }
    }
}
