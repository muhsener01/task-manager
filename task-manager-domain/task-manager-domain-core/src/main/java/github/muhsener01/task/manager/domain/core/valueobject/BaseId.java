package github.muhsener01.task.manager.domain.core.valueobject;

import github.muhsener01.task.manager.domain.core.exception.Assert;

import java.util.Objects;

public class BaseId<T>{

    private final T val;



    public BaseId(T val) {
        Assert.notNull(val , "ID value cannot be null");
        this.val = val;
    }


    public T val() {
        return val;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseId<?> baseId = (BaseId<?>) o;
        return Objects.equals(val, baseId.val);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }
}
