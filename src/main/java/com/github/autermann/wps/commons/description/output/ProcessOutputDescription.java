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
package com.github.autermann.wps.commons.description.output;

import com.github.autermann.wps.commons.description.AbstractDescription;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class ProcessOutputDescription extends AbstractDescription {

    protected ProcessOutputDescription(Builder<?, ?> builder) {
        super(builder);
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isComplex() {
        return false;
    }

    public boolean isBoundingBox() {
        return false;
    }

    public ComplexOutputDescription asComplex() {
        throw new UnsupportedOperationException();
    }

    public LiteralOutputDescription asLiteral() {
        throw new UnsupportedOperationException();
    }

    public BoundingBoxOutputDescription asBoundingBox() {
        throw new UnsupportedOperationException();
    }

    public abstract <T> T visit(
            ReturningVisitor<T> visitor);

    public void visit(Visitor visitor) {
        visit(new VoidWrapper(visitor));
    }

    public interface ReturningVisitor<T> {
        T visit(BoundingBoxOutputDescription output);

        T visit(ComplexOutputDescription output);

        T visit(LiteralOutputDescription output);
    }

    public interface Visitor {
        void visit(BoundingBoxOutputDescription input);

        void visit(ComplexOutputDescription input);

        void visit(LiteralOutputDescription input);
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

    public static abstract class Builder<T extends ProcessOutputDescription, B extends Builder<T, B>>
            extends AbstractDescription.Builder<T, B> {
    }
}
