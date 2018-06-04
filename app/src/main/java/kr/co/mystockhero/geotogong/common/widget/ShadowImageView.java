package kr.co.mystockhero.geotogong.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import kr.co.mystockhero.geotogong.R;

/**
 * Created by sesang on 16. 5. 27..
 */
public class ShadowImageView extends ImageView {

    public Bitmap shadowImage = null;

    public ShadowImageView(Context context) {

        super(context);
    }

    public ShadowImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        Drawable drawable = getDrawable();
        if ( drawable instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable)getDrawable()).getBitmap();
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShadowLayer(5.5f, 6.0f, 6.0f, Color.BLACK);
            canvas.drawColor(Color.GRAY);
            canvas.drawRect(50, 50, 50 + bmp.getWidth(), 50 + bmp.getHeight(), paint);
            canvas.drawBitmap(bmp, 50, 50, null);
        }
    }

    private static Bitmap getDropShadow3(Bitmap bitmap) {

        if (bitmap==null) return null;
        int think = 6;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int newW = w - (think);
        int newH = h - (think);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(w, h, conf);
        Bitmap sbmp = Bitmap.createScaledBitmap(bitmap, newW, newH, false);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas c = new Canvas(bmp);

        // Right
        Shader rshader = new LinearGradient(newW, 0, w, 0, Color.GRAY, Color.LTGRAY, Shader.TileMode.CLAMP);
        paint.setShader(rshader);
        c.drawRect(newW, think, w, newH, paint);

        // Bottom
        Shader bshader = new LinearGradient(0, newH, 0, h, Color.GRAY, Color.LTGRAY, Shader.TileMode.CLAMP);
        paint.setShader(bshader);
        c.drawRect(think, newH, newW  , h, paint);

        //Corner
        Shader cchader = new LinearGradient(0, newH, 0, h, Color.LTGRAY, Color.LTGRAY, Shader.TileMode.CLAMP);
        paint.setShader(cchader);
        c.drawRect(newW, newH, w  , h, paint);

        c.drawBitmap(sbmp, 0, 0, null);

        return bmp;
    }

}