package com.gzy.imapplication.net;

public interface URLSet {

    String HOST = "http://im.rdxer.com";

    interface Auth{

        String PATH = HOST + "/auth";

        String Login = PATH + "/login";
        String Signin = PATH + "/signin";
    }

    interface Mine{

    }

    interface File{

    }



}