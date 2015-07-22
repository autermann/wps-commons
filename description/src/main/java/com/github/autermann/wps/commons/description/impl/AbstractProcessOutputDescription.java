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

import com.github.autermann.wps.commons.description.BoundingBoxOutputDescription;
import com.github.autermann.wps.commons.description.ComplexOutputDescription;
import com.github.autermann.wps.commons.description.LiteralOutputDescription;
import com.github.autermann.wps.commons.description.ProcessOutputDescription;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AbstractProcessOutputDescription extends AbstractDescription implements ProcessOutputDescription {

    protected AbstractProcessOutputDescription(AbstractProcessOutputDescriptionBuilder<?, ?> builder) {
        super(builder);
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    @Override
    public boolean isComplex() {
        return false;
    }

    @Override
    public boolean isBoundingBox() {
        return false;
    }

    @Override
    public ComplexOutputDescriptionImpl asComplex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiteralOutputDescriptionImpl asLiteral() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BoundingBoxOutputDescriptionImpl asBoundingBox() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Visitor visitor) {
        visit(new VoidWrapper(visitor));
    }

    private static class VoidWrapper implements ReturningVisitor<Void> {
        private final Visitor visitor;

        VoidWrapper(Visitor visitor) {
            this.visitor = visitor;
        }

        @Override
        public Void visit(BoundingBoxOutputDescription input) {
            visitor.visit(input);
            return null;
        }

        @Override
        public Void visit(ComplexOutputDescription input) {
            visitor.visit(input);
            return null;
        }

        @Override
        public Void visit(LiteralOutputDescription input) {
            visitor.visit(input);
            return null;
        }
    }

}
