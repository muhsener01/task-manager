package github.muhsener01.task.manager.domain.core.valueobject;

import github.muhsener01.task.manager.domain.core.exception.Assert;

import java.util.Objects;

public class Title {

    private final String val;


    private Title(String val) {
        Assert.notNull(val, "Title value cannot be null");
        this.val = val;
    }


    public boolean isLongerThan(int length) {
        return val.length() > length;
    }

    public boolean isBlank() {
        return val.isBlank();
    }


    /**
     * Returns Title value-object
     * @param val value of Title
     * @return Title
     * @throws IllegalArgumentException if argument val is null
     */
    public static Title of(String val) throws IllegalArgumentException {
        return new Title(val);
    }


    public String val() {
        return val;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Title title = (Title) object;
        return Objects.equals(val, title.val);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }
}
