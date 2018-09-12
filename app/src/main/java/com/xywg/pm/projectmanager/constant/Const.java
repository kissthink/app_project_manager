package com.xywg.pm.projectmanager.constant;

import com.example.common.sutils.utils.io.FileUtil;

public class Const {

    public static final String BASE_URL = "http://192.168.1.64:8081/app/";//正式

    public static final String AUDIO_URL = "http://192.168.1.64:8081/fileUpload/filedownload?remotePath=";
//    public static final String BASE_URL = "http://192.168.20.229:8081/app/";//彬叔
//    public static final String BASE_URL = "http://192.168.20.197:8081/app/";//刘维
//    public static final String BASE_URL = "http://192.168.20.164:8081/app/";//小刚

    // 内测版本，上线版本改为false
    public static boolean DEBUG = false;

    public static class Constant {

        // userName
        public static String mUserName = "";
        // operationId
        public static String mOperationId = "";
        // deviceid
        public static String alipushDeviceId = "";
        // share url
        public static String shareUrl = "";

    }


    public static class ConstantSp {

        // 主题filename
        public static final String SP_THEME_FILE_NAME = "SP_THEME_FILE_NAME";
        public static final String SP_THEME_KEY = "SP_THEME_KEY";

    }

    public static class ConstantPath {

        public static final String PIC_PATH = FileUtil.getSdCardPath() + "/pm/PmImageCache/";

        public static final String AUDIO_PATH = FileUtil.getSdCardPath() + "/pm/PmAudioCache/";

    }


    public static class ConstantCode {
        // CODE
        // EditPhoto
        public static final int EDIT_PHOTO_REQUEST_CODE = 111;

    }

    public static class ConstantKey {
        // 登录成功传值
        public static final String LOGIN_KEY = "LOGIN_KEY";
    }

    public static class ConstantStatus {

        // SUCCESS
        public static final int STATUS_SUCCESS = 1;
    }

    public static class ConstantDS {
        // 用户信息过期
        public static final int DS_LOGIN = 1;
        // 用户使用RFID
        public static final int DS_RFID = 2;
        // 获取operationId
        public static final int DS_OPERATION_ID = 3;
        // 天气预报
        public static final int DS_WEATHER = 4;
        // 播放语音
        public static final int DS_RECORD = 5;
        // 用户名
        public static final int DS_USER_NAME = 6;
    }

    public static class ConstantGetJs {
        // getSessionId
        public static final String GET_SESSION_ID = "getSessionId";

        // getProjectId
        public static final String GET_PROJECT_ID = "getProjectId";

        // getRfid
        public static final String GET_RFID = "getRfid";

    }

    public static final int ORG_ID = 1;// 组织
    public static final int PRO_ID = 2;// 部门

}