package com.j0ach1mmall3.jlib.storage.file.json;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since 15/01/16
 */
public abstract class JsonConfiguration {
    protected String doNotChange;

    public String getDoNotChange() {
        return this.doNotChange;
    }

    public void setDoNotChange(String doNotChange) {
        this.doNotChange = doNotChange;
    }
}