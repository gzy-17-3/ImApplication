package com.gzy.imapplication.net.core;

import java.io.IOException;

public class XXJSONException extends IOException {
    public XXJSONException(String s) {
        super(s);
    }
    public XXJSONException(String s,XXCallBack.ErrType type) {
        super(s);
        this.type = type;
    }
    public XXCallBack.ErrType type = XXCallBack.ErrType.unknown;
}

