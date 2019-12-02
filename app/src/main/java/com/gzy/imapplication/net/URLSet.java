package com.gzy.imapplication.net;

public interface URLSet {

    String HOST = "http://im.rdxer.com";

    interface Auth{

        String PATH = HOST + "/auth";

        String Login = PATH + "/login";
        String Signin = PATH + "/signin";
    }

    interface Mine{

        String PATH = HOST + "/mine";

    }

    interface File{

        String PATH =  HOST + "/file";

        String UPLOAD =  PATH + "/upload";


    }

    interface Friend{

        String PATH =  HOST + "/add_friend_request";

        String FIND =  PATH + "/find";


    }



}
