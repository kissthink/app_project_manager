package com.example.common;

/**
 * Created by zhangyinlei on 2018/5/19 13:54
 */
public class Constant {

    // 选择相册模块，拍照模块，裁剪图片
    public static final int REQUEST_CODE_CHOOSE = 0;
    public static final int REQUEST_CODE_TAKE_PHOTO = 3;
    public static final int REQUEST_CODE_CROP_PHOTO = 4;
    public static final String EXTRA_CLIP_IMAGE = "EXTRA_CLIP_IMAGE";

    //预览图片界面，所有图片和当前图片位置
    public static final String EXTRA_COMMON_PREVIEW_IMAGE_LIST = "EXTRA_COMMON_PREVIEW_IMAGE_LIST";
    public static final String EXTRA_COMMON_PREVIEW_IMAGE_LIST_CAN_DELETE = "EXTRA_COMMON_PREVIEW_IMAGE_LIST_CAN_DELETE";
    public static final String EXTRA_COMMON_PREVIEW_IMAGE_POSITION = "EXTRA_COMMON_PREVIEW_IMAGE_POSITION";

}
