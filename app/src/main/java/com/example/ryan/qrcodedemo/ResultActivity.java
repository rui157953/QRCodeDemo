package com.example.ryan.qrcodedemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryan.qrcodedemo.utils.QRCodeUtil;
import com.google.zxing.Result;

public class ResultActivity extends AppCompatActivity {

    private TextView mResultTv;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mResultTv = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        if (intent != null) {

            String flag = intent.getStringExtra("Flag");
            //扫描结果
            if ("SCAN_QR".equals(flag)) {
                Bundle bundle = intent.getExtras();
                String result = bundle.getString("result");
                Log.i("ryan", "二维码信息 == " + result);
                mResultTv.setText(result);
                mImageView.setImageBitmap((Bitmap) intent.getParcelableExtra("bitmap"));
            }
            //从图片选择结果
            if ("CHOOSE_PIC".equals(flag)) {
                String imgPath = null;
                String[] proj = new String[]{MediaStore.MediaColumns.DATA};
                Cursor cursor = this.getContentResolver().query(intent.getData(), proj, null, null, null);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                    System.out.println(columnIndex);
                    //获取到用户选择的二维码图片的绝对路径
                    imgPath = cursor.getString(columnIndex);
                }
                cursor.close();

                //获取解析结果
                Result ret = QRCodeUtil.parseQRcodeBitmap(imgPath);
                if (ret != null && !TextUtils.isEmpty(ret.toString())) {
//                    Toast.makeText(this, "解析结果：" + ret.toString(), Toast.LENGTH_LONG).show();
                    mResultTv.setText(ret.toString());
                } else {
                    Toast.makeText(this, "无法解析该图片", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        }

    }
}
