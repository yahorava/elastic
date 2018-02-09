package com.epam.elasticadddoc.form;

import java.util.Objects;

public class IndexForm {
    private String path;
    private String index;
    private String type;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        IndexForm indexForm = (IndexForm) o;
        return Objects.equals(path, indexForm.path) && Objects.equals(index, indexForm.index)
                && Objects.equals(type, indexForm.type);
    }

    @Override public int hashCode() {

        return Objects.hash(path, index, type);
    }

    @Override public String toString() {
        return "IndexForm{" + "path='" + path + '\'' + ", index='" + index + '\'' + ", type='"
                + type + '\'' + '}';
    }
}
