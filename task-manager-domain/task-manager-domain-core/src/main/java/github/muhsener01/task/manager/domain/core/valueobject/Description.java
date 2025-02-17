package github.muhsener01.task.manager.domain.core.valueobject;

import github.muhsener01.task.manager.domain.core.exception.Assert;

import java.util.Objects;

public class Description {

    private final String val;

    private Description(String val) {
        Assert.notNull(val, "Description value cannot be null");
        this.val = val;
    }

    public boolean isLongerThan(int length) {
        return val.length() > length;
    }

    public boolean isBlank(){
        return val.isBlank();
    }

    public String val() {
        return val;
    }

    /**
     * returns Description value object with provided value inside it
     * @param val value of Description
     * @return Description
     * @throws IllegalArgumentException if argument val is null
     */
    public static Description of(String val) throws IllegalArgumentException{
        return new Description(val);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Description that = (Description) object;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }
}
