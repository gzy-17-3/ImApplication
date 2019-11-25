package com.gzy.gzylibrary.core;

import java.io.IOException;

public class XXNetException extends IOException {
    public XXNetException(String s) {
        super(s);
    }
    public XXNetException(String s, XXCallBack.ErrType type) {
        super(s);
    }
}

