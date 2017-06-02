package com.winx.test.river;

/**
 * @author wangwenxiang
 * @create 2017-05-22.
 */
public class TestKey {

    private String name;
    private String value;

    public TestKey(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestKey)) return false;

        TestKey testKey = (TestKey) o;

        if (name != null ? !name.equals(testKey.name) : testKey.name != null) return false;
        return value != null ? value.equals(testKey.value) : testKey.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
