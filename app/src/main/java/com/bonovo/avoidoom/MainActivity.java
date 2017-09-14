package com.bonovo.avoidoom;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ainActivity";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.ImageVI);


        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        Log.d(TAG, "onCreate: ------maxmemory = "+maxMemory);

        //此处使用options设置告诉设备不给图片分配内存，只是测量宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.dragon,options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        Log.d(TAG, "onCreate: ---imageHeight = "+imageHeight);
        Log.d(TAG, "onCreate: ---imageWidth = "+imageWidth);
        Log.d(TAG, "onCreate: ---imageType  ="+imageType);

        imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(),R.drawable.dragon,400,400));
    }

    public static  int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final  int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width >reqWidth){
            final int heightRatio = Math.round((float) height/(float) reqHeight);
            final int widthRatio = Math.round((float) width/(float) reqWidth);
           inSampleSize = heightRatio < widthRatio ? heightRatio:widthRatio;
        }
      return inSampleSize;
    }


    public  static Bitmap decodeSampledBitmapFromResource(Resources res,
                                        int resId,int reqWidth,int reqHeight ){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //加载前
        options.inJustDecodeBounds = true; //不为当前资源图片分配内存
        BitmapFactory.decodeResource(res,resId,options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        //压缩后
        options.inJustDecodeBounds = false;//可以为当前资源分配内存
        return BitmapFactory.decodeResource(res,resId,options);
    }












}
