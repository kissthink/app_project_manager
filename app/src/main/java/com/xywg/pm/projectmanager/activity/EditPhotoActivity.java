package com.xywg.pm.projectmanager.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.base.BaseActivity;
import com.example.common.base.BasePresenter;
import com.example.common.sutils.utils.DeviceUtil;
import com.example.common.sutils.utils.encrypt.Md5Util;
import com.example.common.sutils.utils.graphic.ImageUtil;
import com.example.common.sutils.utils.time.TimeUtil;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.constant.Const;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.view.PaletteView;

import java.io.File;

@Route(path = RouterPath.ROUTER_EDIT_PHOTO)
public class EditPhotoActivity extends BaseActivity {

    public static final String PHOTO_PATH = "PHOTO_PATH";
    public static final String PHOTO_DATA = "PHOTO_DATA";

    private PaletteView editPhotoPalette;
    private Button confirm;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void findViews() {
        editPhotoPalette = findViewById(R.id.edit_photo_palette);
        confirm = findViewById(R.id.edit_photo_confirm);
        cancel = findViewById(R.id.edit_photo_cancel);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String photoPath = intent.getStringExtra(EditPhotoActivity.PHOTO_PATH);
        int isUri = intent.getIntExtra("isUri", -1);
        Bitmap bitmap = null;
        String systemModel = DeviceUtil.getModel();
        try {
            if (isUri != -1) {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(photoPath)));
            } else {
                bitmap = BitmapFactory.decodeFile(photoPath);
            }
            Bitmap image = null;
            if ("MI 6".equals(systemModel)) {
                image = ImageUtil.rotaingImageView(90, bitmap);
                bitmap.recycle();
                editPhotoPalette.setBackground(new BitmapDrawable(image));
            } else {
                editPhotoPalette.setBackground(new BitmapDrawable(bitmap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initEvent();
    }

    private void initEvent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhotoPalette.clear();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm = editPhotoPalette.buildBitmap();
                bm = ImageUtil.createWatermark(EditPhotoActivity.this, bm, "项目管理 " + TimeUtil.millis2String(System.currentTimeMillis()));
                String photoName = Const.ConstantPath.PIC_PATH + Md5Util.getMD5(String.valueOf(System.currentTimeMillis())) + ".jpg";
                ImageUtil.saveBitmap2file(bm, photoName);
                Intent data = new Intent();
                data.putExtra(EditPhotoActivity.PHOTO_DATA, photoName);
                //通知相册
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File file = new File(photoName);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

}
