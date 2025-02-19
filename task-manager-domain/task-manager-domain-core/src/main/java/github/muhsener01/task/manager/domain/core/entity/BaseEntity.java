package github.muhsener01.task.manager.domain.core.entity;

import java.util.Objects;

public class BaseEntity<ID>{

    protected ID id;



    public ID getId() {
        return id;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
