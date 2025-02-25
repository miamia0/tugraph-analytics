/*
 * Copyright 2023 AntGroup CO., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.dsl.runtime.traversal.data;

import com.antgroup.geaflow.common.binary.BinaryString;
import com.antgroup.geaflow.common.type.IType;
import com.antgroup.geaflow.dsl.common.data.Row;
import com.antgroup.geaflow.dsl.common.data.RowVertex;
import com.antgroup.geaflow.model.graph.vertex.IVertex;
import java.util.Objects;

public class FieldAlignVertex implements RowVertex {

    private final RowVertex baseVertex;

    private final int[] fieldMapping;

    public FieldAlignVertex(RowVertex baseVertex, int[] fieldMapping) {
        this.baseVertex = baseVertex;
        this.fieldMapping = fieldMapping;
    }

    @Override
    public Object getField(int i, IType<?> type) {
        int mappingIndex = fieldMapping[i];
        if (mappingIndex < 0) {
            return null;
        }
        return baseVertex.getField(mappingIndex, type);
    }

    @Override
    public void setValue(Row value) {
        baseVertex.setValue(value);
    }

    @Override
    public String getLabel() {
        return baseVertex.getLabel();
    }

    @Override
    public void setLabel(String label) {
        baseVertex.setLabel(label);
    }

    @Override
    public Object getId() {
        return baseVertex.getId();
    }

    @Override
    public void setId(Object id) {
        baseVertex.setId(id);
    }

    @Override
    public Row getValue() {
        return baseVertex.getValue();
    }

    @Override
    public IVertex<Object, Row> withValue(Row value) {
        return new FieldAlignVertex((RowVertex) baseVertex.withValue(value), fieldMapping);
    }

    @Override
    public IVertex<Object, Row> withLabel(String label) {
        return new FieldAlignVertex((RowVertex) baseVertex.withLabel(label), fieldMapping);
    }

    @Override
    public IVertex<Object, Row> withTime(long time) {
        return new FieldAlignVertex((RowVertex) baseVertex.withTime(time), fieldMapping);
    }

    @Override
    public int compareTo(Object o) {
        return baseVertex.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RowVertex)) {
            return false;
        }
        RowVertex that = (RowVertex) o;
        return getId().equals(that.getId()) && Objects.equals(getBinaryLabel(), that.getBinaryLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBinaryLabel());
    }

    @Override
    public BinaryString getBinaryLabel() {
        return baseVertex.getBinaryLabel();
    }

    @Override
    public void setBinaryLabel(BinaryString label) {
        baseVertex.setBinaryLabel(label);
    }

    @Override
    public String toString() {
        return getId() + "#" + getBinaryLabel() + "#" + getValue();
    }
}
