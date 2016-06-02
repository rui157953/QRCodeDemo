package com.example.ryan.qrcodedemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ryan.qrcodedemo.utils.QRCodeUtil;

import java.io.File;

public class CreateQRCodeActivity extends Activity {

    private EditText mEditText;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qrcode);
        mEditText=  (EditText) findViewById(R.id.content);
        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    public void create(View view) {
        String text = mEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(text)){
            createCode(text);
        }
    }

    /**
     * 生成二维码
     * @param text
     */
    private void createCode(final String text) {
        Bitmap bitmap = null;
        final String filePath = getFileRoot(this) + File.separator + "qr_"
                + System.currentTimeMillis() + ".jpg";

        // 二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QRCodeUtil.createQRImage
                        (text  //二维码内容
                        , 800  //二维码高
                        , 800  //二维码宽
                        ,null  //二维码中间图标
                        ,filePath //生成二维码存放路径
                );
                if (success) {
                    runOnUiThread(new Runnable() { //主线程更新UI
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(BitmapFactory
                                    .decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
    }

    // 获取文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }

}
