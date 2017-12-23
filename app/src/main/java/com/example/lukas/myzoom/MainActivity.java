package com.example.lukas.myzoom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TouchImageView img;
    private float maxImgH = 10, maxImgW = 20, x = 2, y = 2;
    private Paint p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (TouchImageView) findViewById(R.id.img);
        VectorDrawable drawable = (VectorDrawable) ContextCompat.getDrawable(this, R.drawable.map);
        img.setImageBitmap(drawableToBitmap(drawable));

        p = new Paint();
        p.setAntiAlias(true);
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.rgb(254, 0, 1));
    }


    public void draw(){
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.map);
        Bitmap bm = drawableToBitmap(drawable);
        Canvas c = new Canvas(bm);
        int maxH = bm.getHeight();
        int maxW = bm.getWidth();
        c.drawCircle((x/maxImgW)*maxW, (y/maxImgH)*maxH, 50f, p);
        Log.d("Main", "Draw " + x*10 + ", " + y*10 + " max: " + maxW + ", " + maxH);
        img.setImageBitmap(bm);
    }

    public void moveUp(View v){
        y += -1;
        draw();
    }

    public void moveDown(View v){
        y += 1;
        draw();
    }

    public void moveRight(View v){
        x += 1;
        draw();
    }

    public void moveLeft(View v){
        x += -1;
        draw();
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
