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

    interface AddFriendRequest {

        String PATH =  HOST + "/add_friend_request";

        String ADD_FRIEND_REQUEST =  PATH + "/"; // 获取 新好友列表

        String FIND =  PATH + "/find";  // 查找联系人
        String APPLY =  PATH + "/apply"; // 发起 申请添加某人为好友
        String REPLY =  PATH + "/reply"; // 应答 添加某人为好友
        String COUNT =  PATH + "/count"; // 计数 申请添加某人为好友

    }
    interface Friend {

//        String PATH =  HOST + "/add_friend_request";
//
//        String ADD_FRIEND_REQUEST =  PATH + "/"; // 获取 新好友列表
//
//        String FIND =  PATH + "/find";  // 查找联系人
//        String APPLY =  PATH + "/apply"; // 发起 申请添加某人为好友
//        String REPLY =  PATH + "/reply"; // 应答 添加某人为好友

    }



}
