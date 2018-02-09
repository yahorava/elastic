package com.epam.elasticadddoc.counter;

import java.util.Objects;

public class AddCounter {
    private int created = 0;
    private int updated = 0;

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public void incrementCreated() {
        created++;
    }

    public void incrementUpdated() {
        updated++;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AddCounter that = (AddCounter) o;
        return created == that.created && updated == that.updated;
    }

    @Override public int hashCode() {

        return Objects.hash(created, updated);
    }

    @Override public String toString() {
        return "AddCounter{" + "created=" + created + ", updated=" + updated + '}';
    }
}
