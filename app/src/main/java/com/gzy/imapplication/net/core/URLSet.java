package com.gzy.imapplication.net.core;

public interface URLSet {

    String HOST = "http://im.rdxer.com";

    interface Auth{

        String PATH = "/auth";

        String Login = PATH + "/login";
        String Signin = PATH + "/signin";
    }

    interface Mine{

    }

    interface File{

    }



}
