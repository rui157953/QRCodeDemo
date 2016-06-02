package com.example.ryan.qrcodedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int CHOOSE_PIC = 100 ;
    private static final int SCAN_QR = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ryanryan","onCreate == "+ "onCreate" );
    }



    public void action(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_create:

                intent.setClass(this,CreateQRCodeActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_scan:
                Intent intent1 = new Intent();
                intent1.setClass(this,ScanQRActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent1,SCAN_QR);
                break;

            case R.id.btn_pickFile:
                //跳转到图片选择界面去选择一张二维码图片
//			if(android.os.Build.VERSION.SDK_INT < 19){
//				intent1.setAction(Intent.ACTION_GET_CONTENT);
//			}else{
//				intent1.setAction(Intent.ACTION_OPEN_DOCUMENT);
//			}
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                Intent intent2 =  Intent.createChooser(intent, "选择二维码图片");
                startActivityForResult(intent2, CHOOSE_PIC);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ryanryan","onRestart == "+ "onRestart" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ryanryan","onResume == "+ "onResume" );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Intent intent = data;
            Log.d("ryanryan","onActivityResult == " );
            switch (requestCode) {

                case SCAN_QR:
                    Log.d("ryanryan","onActivityResult == "+ "SCAN_QR" );
//                     Bundle bundle = data.getExtras();
                    // String result = bundle.getString("result");
                    // Log.i("ryan", "二维码信息 == " +result);
                    // // mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                    intent.putExtra("Flag","SCAN_QR");
                    break;

                case CHOOSE_PIC:
                    intent.putExtra("Flag","CHOOSE_PIC");
                    break;
            }
            intent.setClass(MainActivity.this,ResultActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ryanryan","onDestroy");
    }
}
