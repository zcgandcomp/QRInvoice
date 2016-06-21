package org.qrinvoice.core;

/**
 * Created by zcg on 8.6.2016.
 */
public class ValueCodePair {

    public ValueCodePair(Object value, String code) {
        this.value = value;
        this.code = code;


    }

    private Object value;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
