package kr.co.mystockhero.geotogong.common;

/**
 * Created by macmini02 on 16. 5. 19..
 */

import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class ControlUtil {

    public static int sdkVer = Build.VERSION.SDK_INT;

    public static int WIDTH = 640;
    public static int HEIGHT = 1136;

    public static int bWidth = 640;
    public static int bHeight = 1136-50;
    public static float bDensity = 2.0f; // xhdpi
    public static final boolean fullScreen = true;
    public static boolean useDensity = false;

    public static int sX = 0;
    public static int sY = 0;
    public static int fWidth = 0;
    public static int fHeight = 0;
    public static int sWidth = bWidth;
    public static int sHeight = bHeight;
    public static float xRate = 1.0f;
    public static float yRate = 1.0f;
    public static float scale = 0.0f;
    public static float scaledDensity;
    public static float density;
    public static float iScale = 0.0f;
    public static boolean isTablet = false;
    public static float tabletScale = 1.0f;
    public static  Typeface typeface;
    public static int statusHeight = 0;

    public static void setScreenInfo(Context context, DisplayMetrics displayMetrics, int orientation, boolean status) {

        if ( status ) {
            statusHeight = 50;
        } else {
            statusHeight = 0;
        }

//		Configuration config = context.getResources().getConfiguration();
//	    isTablet = ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_LARGE) == Configuration.SCREENLAYOUT_SIZE_LARGE) ||
//	    			((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_XLARGE) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
//


        if ( isTablet ) {

            tabletScale = 1.5f;

            WIDTH = 1200;
            HEIGHT = 1920;

            if ( status ) {
                int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusHeight = context.getResources().getDimensionPixelSize(resourceId);
                }
            }

        } else {

            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if ( status ) {
                if (resourceId > 0) {
                    statusHeight = context.getResources().getDimensionPixelSize(resourceId);
                }
            }
        }

        if ( orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bWidth = HEIGHT;
            bHeight = WIDTH - statusHeight;
        } else {
            bWidth = WIDTH;
            bHeight = HEIGHT - statusHeight;
        }

        fWidth = displayMetrics.widthPixels;
        fHeight = displayMetrics.heightPixels - statusHeight;

        scaledDensity = displayMetrics.scaledDensity;
        if ( useDensity ) {
            density = displayMetrics.density;
        } else {
            density = bDensity;
        }

        CommonUtil.DebugInfo("width[%d], height[%d], scaledDensity[%f], density[%f], densityDpi[%d], xdpi[%f], ydpi[%f], statusHeight[%d] isTablet[%b]",
                WIDTH,
                HEIGHT,
                displayMetrics.scaledDensity,
                displayMetrics.density,
                displayMetrics.densityDpi,
                displayMetrics.xdpi,
                displayMetrics.ydpi,
                statusHeight,
                isTablet);


        int w = fWidth;
        int h = fHeight;

        float sRate = (float)w / (float)h;
        float bRate = (float)bWidth / (float)bHeight;

        if ( fullScreen ) {
            sWidth = w;
            sHeight = h;
        } else {
            if ( sRate > bRate ) {
                sHeight = h;
                sWidth = (int)((float)sHeight * bRate + 0.5f);
            } else if ( sRate < bRate ){
                sWidth = w;
                sHeight = (int)((float)sWidth / bRate + 0.5f);
            }
            sX = (int)(float)((w - sWidth) / 2.0f + 0.5f);
            sY = (int)(float)((h - sHeight) / 2.0f + 0.5f);
        }

        xRate =  (float)sWidth / (float)bWidth;
        yRate =  (float)sHeight / (float)bHeight;
        if ( scale == 0.0f ) {
            scale = xRate;//Math.max(xRate, yRate);
        }
        if ( iScale == 0.0f ) {
            iScale = bDensity / density * scale;
        }

        CommonUtil.DebugInfo("setScreenInfo : fWidth[%d], fHeight[%d], w[%d], h[%d], bRate[%f], sRate[%f], sx[%d], sy[%d], sWidth[%d], sHeight[%d], xRate[%f], yRate[%f], scale[%f]",
                fWidth, fHeight, bWidth, bHeight, bRate, sRate, sX, sY, sWidth, sHeight, xRate, yRate, scale);

        FontUtil.addFont(context, FontUtil.Font_NotoSansKR, FontUtil.Type_Bold);
        FontUtil.addFont(context, FontUtil.Font_NotoSansKR, FontUtil.Type_Light);
        FontUtil.addFont(context, FontUtil.Font_NotoSansKR, FontUtil.Type_Medium);
        FontUtil.addFont(context, FontUtil.Font_NotoSansKR, FontUtil.Type_Regular);
    }

    public static boolean isTablet (Context context) {

        int xlargeBit = Configuration.SCREENLAYOUT_SIZE_XLARGE;  // upgrade to HC SDK to get this
        Configuration config = context.getResources().getConfiguration();

        return (config.screenLayout & xlargeBit) == xlargeBit;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    public static int getColor(Context context, int id) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static void setTypeface(Context context, String fontName) {

        typeface = Typeface.createFromAsset(context.getAssets(), fontName);
    }

    public static Point convertPosition(int x, int y) {

        return new Point((int)((float)x * xRate + 0.5), (int)((float)y * yRate + 0.5));
    }

    public static Point convertSize(int w, int h) {

        return new Point((int)((float)w * xRate + 0.5), (int)((float)h * yRate + 0.5));
    }

    public static int convertWidth(int w) {

        return (int)((float)w * xRate + 0.5);
    }

    public static int convertHeight(int h) {

        return (int)((float)h * yRate + 0.5);
    }


    public static int reverseWidth(int w) {

        return (int)Math.round((float)w / xRate + 0.5);
    }

    public static int reverseHeight(int h) {

        return (int)Math.round((float)h / yRate + 0.5);
    }

    public static int reverseScale(int v) {

        return (int)Math.round((float)v / scale + 0.5);
    }

    public static int getConvertHeight() {

        return convertHeight(bHeight);
    }

    public static int getConvertWidth() {

        return convertHeight(bWidth);
    }

    public static Point getImageSize(Context context, int resId) {

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, dimensions);

        return new Point(dimensions.outWidth, dimensions.outHeight);
    }

    public static Point getImageSize(Context context, String name) {

        int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, dimensions);

        return new Point(dimensions.outWidth, dimensions.outHeight);
    }

    public static float getImageRate(Context context, int resId) {

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, dimensions);

        return ((float)dimensions.outHeight / (float)dimensions.outWidth);
    }

    public static Point convertCenter(int x, int y, int w, int h) {

        int cx = (int)((float)convertWidth(x) + (float)convertWidth(w) / 2f + 0.5f);
        int cy = (int)((float)convertHeight(y) + (float)convertHeight(h) / 2f + 0.5f);
        return new Point(cx, cy);
    }

    public static int convertTablet(int size) {

        return (int)((float)size * tabletScale);
    }

    public static Point convertSizeByScale(int w, int h) {

        return new Point(convertScale(w), convertScale(h));
    }

    public static int convertScale(int value) {
        return Math.round((float)value * scale + 0.5f);
    }

    public static Point convertPosition(int cx, int cy, int w, int h) {
        return new Point(cx - w / 2, cy - h / 2);
    }

    public static Point convertImageSize(int width, int height) {
        return new Point((int)((float)width * scale + 0.5f), (int)((float)height * scale + 0.5f));
    }

//    public static RelativeLayout.LayoutParams getLayoutParams(int x, int y, int w, int h) {
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
//
//        layoutParams.leftMargin = x;
//        layoutParams.topMargin = y;
//
//        return layoutParams;
//    }

//    public static ViewGroup.LayoutParams getLayoutParams(int w, int h) {
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
//        layoutParams.leftMargin = 0;
//        layoutParams.topMargin = 0;
//
//        return layoutParams;
//    }
//
//    public static RelativeLayout.LayoutParams getFullLayoutParams() {
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        layoutParams.leftMargin = 0;
//        layoutParams.topMargin = 0;
//
//        return layoutParams;
//    }
//
//    public static RelativeLayout.LayoutParams getWrapLayoutParams() {
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.leftMargin = 0;
//        layoutParams.topMargin = 0;
//
//        return layoutParams;
//    }

    public static LayoutParams getConvertLayoutParams(int x, int y, int w, int h, ViewGroup parent, int gravity) {

        return getConvertLayoutParams(x, y, w, h, parent, gravity, true, true, true, true);
    }

    public static int convertWidth(boolean scaled, int value) {

        if ( value == LayoutParams.MATCH_PARENT || value == LayoutParams.WRAP_CONTENT) {
            return value;
        }
        return scaled ? convertScale(value) : convertWidth(value);
    }

    public static int convertHeight(boolean scaled, int value) {

        if ( value == LayoutParams.MATCH_PARENT || value == LayoutParams.WRAP_CONTENT) {
            return value;
        }
        return scaled ? convertScale(value) : convertHeight(value);
    }

    public static LayoutParams getConvertLayoutParams(int x, int y, int w, int h, ViewGroup parent, int gravity, boolean fixX, boolean fixY, boolean fixWidth, boolean fixHeight) {

        int pw = sWidth;
        int ph = sHeight;

        int ox = convertWidth(x);
        int oy = convertHeight(y);
        int ow = convertWidth(w);
        int oh = convertHeight(h);

        int sx = fixX ? convertScale(x) : ox;
        int sy = fixY ? convertScale(y) : oy;
        int sw = (w != LayoutParams.WRAP_CONTENT) ? (fixWidth ? convertScale(w) : ow) : w;
        int sh = (h != LayoutParams.WRAP_CONTENT) ? (fixHeight ? convertScale(h) : oh) : h;

        ViewGroup.MarginLayoutParams layoutParams = null;

        if ( parent != null ) {

            if ( parent instanceof  RelativeLayout ) {
                layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            } else if ( parent instanceof LinearLayout ) {
                layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            } else {
                layoutParams = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            }

            if ( parent.getLayoutParams().width == RelativeLayout.LayoutParams.WRAP_CONTENT ) {
                pw = sx + sw;
            } else {
                pw = getControlWidth(parent);
            }
            if ( parent.getLayoutParams().height == RelativeLayout.LayoutParams.WRAP_CONTENT ) {
                ph = sy + sh;
            } else {
                ph = getControlHeight(parent);
            }
        } else {
            layoutParams = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        }

        if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL || (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL ) {
            //sx = 0;
//			if ( pw - sx - sw > 0 ) {
//				layoutParams.rightMargin = pw - sx - sw;
//			}
            layoutParams.leftMargin = sx;
            layoutParams.rightMargin = sw;
            sw = RelativeLayout.LayoutParams.MATCH_PARENT;

        } else if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.LEFT ) {
            //sx = convertScale(x);
            layoutParams.leftMargin = sx;

        } else if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT ) {

            if ( layoutParams instanceof  RelativeLayout.LayoutParams ) {
                ((RelativeLayout.LayoutParams)layoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                layoutParams.rightMargin = sx;
                sx = 0;
            } else {
                sx = pw - sw - sx;
                layoutParams.leftMargin = sx;
            }

            //sx = pw - sw - ox;
            //sx = pw - (pw - convertScale(x + w)) - sw;
        } else if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL || (gravity & Gravity.CENTER) == Gravity.CENTER ) {

            sx = pw / 2 - sw / 2 + sx;//ox + ow / 2 - sw / 2;
            layoutParams.leftMargin = sx;

        } else {//if ( (gravity & Gravity.CENTER_HORIZONTAL) == Gravity.CENTER_HORIZONTAL ) {
            sx = ox + ow / 2 - sw / 2;
            layoutParams.leftMargin = sx;
        }

        if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL || (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL ) {

            //sy = 0;
            layoutParams.topMargin = sy;
            layoutParams.bottomMargin = sh;
            sh = RelativeLayout.LayoutParams.MATCH_PARENT;

        } else if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP ) {
            //sy = convertScale(y);
            layoutParams.topMargin = sy;

        } else if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM ) {

            //layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            //layoutParams.bottomMargin = sy;

            if ( layoutParams instanceof  RelativeLayout.LayoutParams ) {
                //sy = ph - sh - sy;
                ((RelativeLayout.LayoutParams)layoutParams).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                layoutParams.bottomMargin = sy;
                sy = 0;
            } else {
                sy = ph - sh - oy;
                layoutParams.topMargin = sy;
            }
            //sy = 0;
            //sy = ph - sh - oy;
            //sy = ph - (ph - convertScale(y + h)) - sh;

        } else if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL || (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER ) {

            sy = ph / 2 - sh / 2 + sy;
            layoutParams.topMargin = sy;

        } else {//if ( (gravity & Gravity.CENTER_VERTICAL) == Gravity.CENTER_VERTICAL ) {
            sy = oy + oh / 2 - sh / 2;
            layoutParams.topMargin = sy;
        }

        // CommonUtil.DebugLog("....getFixedLayoutParams 1 : ox = %d, oy = %d, ow = %d, oh = %d", ox, oy, ow, oh);
        // CommonUtil.DebugLog("....getFixedLayoutParams 2 : sx = %d, sy = %d, sw = %d, sh = %d", sx, sy, sw, sh);

        layoutParams.width = sw;
        layoutParams.height = sh;


        return layoutParams;
    }

//    public static LayoutParams getConvertLayoutParams(int x, int y, int w, int h, boolean fixX, boolean fixY, boolean fixWidth, boolean fixHeight) {
//
//
//        int sx = fixX ? convertScale(x) : convertWidth(x);
//        int sy = fixY ? convertScale(y) : convertHeight(y);
//
//        int sw = (w != RelativeLayout.LayoutParams.WRAP_CONTENT) ? (fixWidth ? convertScale(w) : convertWidth(w)) : w;
//        int sh = (h != RelativeLayout.LayoutParams.WRAP_CONTENT) ? (fixHeight ? convertScale(h) : convertHeight(h)) : h;
//
//        //CommonUtil.DebugLog("....getFixedLayoutParams 1 : ox = %d, oy = %d, ow = %d, oh = %d", ox, oy, ow, oh);
//        //CommonUtil.DebugLog("....getFixedLayoutParams 2 : sx = %d, sy = %d, sw = %d, sh = %d", sx, sy, sw, sh);
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(sw, sh);
//
//        layoutParams.leftMargin = sx;
//        layoutParams.topMargin = sy;
//
//        return layoutParams;
//    }

//    public static RelativeLayout.LayoutParams getConvertScaledLayoutParams(int x, int y, int w, int h) {
//
//
//        int sx = convertScale(x);
//        int sy = convertScale(y);
//        int sw = (w != RelativeLayout.LayoutParams.WRAP_CONTENT) ? convertScale(w) : w;
//        int sh = (h != RelativeLayout.LayoutParams.WRAP_CONTENT) ? convertScale(h) : h;
//
//        //CommonUtil.DebugLog("....getFixedLayoutParams 1 : ox = %d, oy = %d, ow = %d, oh = %d", ox, oy, ow, oh);
//        //CommonUtil.DebugLog("....getFixedLayoutParams 2 : sx = %d, sy = %d, sw = %d, sh = %d", sx, sy, sw, sh);
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(sw, sh);
//
//        layoutParams.leftMargin = sx;
//        layoutParams.topMargin = sy;
//
//        return layoutParams;
//    }

//    public static RelativeLayout.LayoutParams getConvertRateLayoutParams(int x, int y, int w, int h) {
//
//
//        int sx = convertWidth(x);
//        int sy = convertHeight(y);
//        int sw = (w != RelativeLayout.LayoutParams.WRAP_CONTENT) ? convertWidth(w) : w;
//        int sh = (h != RelativeLayout.LayoutParams.WRAP_CONTENT) ? convertHeight(h) : h;
//
//        //CommonUtil.DebugLog("....getFixedLayoutParams 1 : ox = %d, oy = %d, ow = %d, oh = %d", ox, oy, ow, oh);
//        //CommonUtil.DebugLog("....getFixedLayoutParams 2 : sx = %d, sy = %d, sw = %d, sh = %d", sx, sy, sw, sh);
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(sw, sh);
//
//        layoutParams.leftMargin = sx;
//        layoutParams.topMargin = sy;
//
//        return layoutParams;
//    }

    public static void addLayoutRule(View view, int rule, int value) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.addRule(rule, value);
        view.setLayoutParams(layoutParams);
    }

//    public static void setControlLayout(View view, int x, int y, int width, int height) {
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
//        layoutParams.leftMargin = x;
//        layoutParams.topMargin = y;
//        view.setLayoutParams(layoutParams);
//    }

    public static void setControlX(View view, int x) {

        if ( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            layoutParams.leftMargin = x;
            view.setLayoutParams(layoutParams);
        } else {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
            layoutParams.leftMargin = x;
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setControlY(View view, int y) {

        if ( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            layoutParams.topMargin = y;
            view.setLayoutParams(layoutParams);
        } else {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
            layoutParams.topMargin = y;
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setControlPosition(View view, int x, int y) {

        if ( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            layoutParams.leftMargin = x;
            layoutParams.topMargin = y;
            view.setLayoutParams(layoutParams);
        } else {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
            layoutParams.leftMargin = x;
            layoutParams.topMargin = y;
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setControlSize(View view, int width, int height) {

        LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }


    public static void setControlWidth(View view, int width) {

        LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    public static void setControlHeight(View view, int height) {

        LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public static int getControlX(View view) {

        if ( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            return ((FrameLayout.LayoutParams)view.getLayoutParams()).leftMargin;
        } else {
            return ((RelativeLayout.LayoutParams)view.getLayoutParams()).leftMargin;
        }
    }

    public static int getControlY(View view) {

        if ( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            return ((FrameLayout.LayoutParams)view.getLayoutParams()).topMargin;
        } else {
            return ((RelativeLayout.LayoutParams)view.getLayoutParams()).topMargin;
        }
    }

    public static int getControlWidth(View view) {
        if ( view == null ) return 0;
        if ( view.getLayoutParams() == null ) return 0;
        return view.getLayoutParams().width;
    }

    public static int getControlHeight(View view) {
        if ( view == null ) return 0;
        if ( view.getLayoutParams() == null ) return 0;
        return view.getLayoutParams().height;
    }

    public static StateListDrawable getStateDrawable(Context context, int resId, int pressId) {

        StateListDrawable states = new StateListDrawable();

        Drawable resDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
        Drawable pressDrawable = pressId > 0 ? context.getResources().getDrawable(pressId) : new ColorDrawable(0);

        states.addState(new int[] {android.R.attr.state_pressed}, pressDrawable);
        states.addState(new int[] {android.R.attr.state_focused}, pressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed}, resDrawable);
        states.addState(new int[] {-android.R.attr.state_focused}, resDrawable);

        return states;
    }

    public static StateListDrawable getStateDrawable(Context context, int resId, int pressId, int disableId) {

        StateListDrawable states = new StateListDrawable();

        Drawable resDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
        Drawable pressDrawable = pressId > 0 ? context.getResources().getDrawable(pressId) : new ColorDrawable(0);
        Drawable disableDrawable = disableId > 0 ? context.getResources().getDrawable(disableId) : new ColorDrawable(0);

        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled}, pressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed, android.R.attr.state_enabled}, resDrawable);
        states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_enabled}, resDrawable);
        states.addState(new int[] {-android.R.attr.state_enabled}, disableDrawable);

        return states;
    }

    public static StateListDrawable getSelectStateDrawable(Context context, int resId, int selectId) {

//        StateListDrawable states = new StateListDrawable();

//        Drawable resDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
//        Drawable selectDrawable = selectId > 0 ? context.getResources().getDrawable(selectId) : new ColorDrawable(0);

//        states.addState(new int[] {android.R.attr.state_checked}, selectDrawable);
//        states.addState(new int[] {android.R.attr.state_checked}, selectDrawable);
//        states.addState(new int[] {-android.R.attr.state_checked}, resDrawable);
//        states.addState(new int[] {-android.R.attr.state_checked}, resDrawable);

        StateListDrawable states = new StateListDrawable();

        Drawable resDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
        Drawable selectDrawable = selectId > 0 ? context.getResources().getDrawable(selectId) : new ColorDrawable(0);
        Drawable pressDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
        Drawable selectPressDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);

        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_checked}, selectPressDrawable);
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_checked}, selectPressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed, android.R.attr.state_checked}, selectDrawable);
        states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_checked}, selectDrawable);
        states.addState(new int[] {android.R.attr.state_pressed, -android.R.attr.state_checked}, pressDrawable);
        states.addState(new int[] {android.R.attr.state_focused, -android.R.attr.state_checked}, pressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed, -android.R.attr.state_checked}, resDrawable);
        states.addState(new int[] {-android.R.attr.state_focused, -android.R.attr.state_checked}, resDrawable);

        return states;
    }

    public static StateListDrawable getSelectStateDrawable(Context context, int resId, int selectId, int disableId) {

        StateListDrawable states = new StateListDrawable();

        Drawable resDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
        Drawable selectDrawable = selectId > 0 ? context.getResources().getDrawable(selectId) : new ColorDrawable(0);
        Drawable disableDrawable = disableId > 0 ? context.getResources().getDrawable(disableId) : new ColorDrawable(0);

        states.addState(new int[] {-android.R.attr.state_enabled}, disableDrawable);
        states.addState(new int[] {android.R.attr.state_checked, android.R.attr.state_enabled}, selectDrawable);
        states.addState(new int[] {android.R.attr.state_checked, android.R.attr.state_enabled}, selectDrawable);
        states.addState(new int[] {-android.R.attr.state_checked, android.R.attr.state_enabled}, resDrawable);
        states.addState(new int[] {-android.R.attr.state_checked, android.R.attr.state_enabled}, resDrawable);

        return states;
    }

    public static StateListDrawable getSelectStateDrawable(Context context, int resId, int selectId, int pressId, int selectPressId) {

        StateListDrawable states = new StateListDrawable();

        Drawable resDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
        Drawable selectDrawable = selectId > 0 ? context.getResources().getDrawable(selectId) : new ColorDrawable(0);
        Drawable pressDrawable = pressId > 0 ? context.getResources().getDrawable(pressId) : new ColorDrawable(0);
        Drawable selectPressDrawable = selectPressId > 0 ? context.getResources().getDrawable(selectPressId) : new ColorDrawable(0);

        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_checked}, selectPressDrawable);
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_checked}, selectPressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed, android.R.attr.state_checked}, selectDrawable);
        states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_checked}, selectDrawable);
        states.addState(new int[] {android.R.attr.state_pressed, -android.R.attr.state_checked}, pressDrawable);
        states.addState(new int[] {android.R.attr.state_focused, -android.R.attr.state_checked}, pressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed, -android.R.attr.state_checked}, resDrawable);
        states.addState(new int[] {-android.R.attr.state_focused, -android.R.attr.state_checked}, resDrawable);

        return states;
    }


    public static StateListDrawable getSelectStateDrawable(Context context, int resId, int selectId, int pressId, int selectPressId, int disableId) {

        StateListDrawable states = new StateListDrawable();

        Drawable resDrawable = resId > 0 ? context.getResources().getDrawable(resId) : new ColorDrawable(0);
        Drawable selectDrawable = selectId > 0 ? context.getResources().getDrawable(selectId) : new ColorDrawable(0);
        Drawable pressDrawable = pressId > 0 ? context.getResources().getDrawable(pressId) : new ColorDrawable(0);
        Drawable selectPressDrawable = selectPressId > 0 ? context.getResources().getDrawable(selectPressId) : new ColorDrawable(0);
        Drawable disableDrawable = disableId > 0 ? context.getResources().getDrawable(disableId) : new ColorDrawable(0);

        states.addState(new int[] {-android.R.attr.state_enabled}, disableDrawable);
        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_checked, android.R.attr.state_enabled}, selectPressDrawable);
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_checked, android.R.attr.state_enabled}, selectPressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed, android.R.attr.state_checked, android.R.attr.state_enabled}, selectDrawable);
        states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_checked, android.R.attr.state_enabled}, selectDrawable);
        states.addState(new int[] {android.R.attr.state_pressed, -android.R.attr.state_checked, android.R.attr.state_enabled}, pressDrawable);
        states.addState(new int[] {android.R.attr.state_focused, -android.R.attr.state_checked, android.R.attr.state_enabled}, pressDrawable);
        states.addState(new int[] {-android.R.attr.state_pressed, -android.R.attr.state_checked, android.R.attr.state_enabled}, resDrawable);
        states.addState(new int[] {-android.R.attr.state_focused, -android.R.attr.state_checked, android.R.attr.state_enabled}, resDrawable);

        return states;
    }


    public static StateListDrawable getColorStateDrawable(Context context, int color, int pressColor) {

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_pressed},  new ColorDrawable(pressColor));
        states.addState(new int[] {android.R.attr.state_focused}, new ColorDrawable(pressColor));
        states.addState(new int[] {-android.R.attr.state_pressed}, new ColorDrawable(color));
        states.addState(new int[] {-android.R.attr.state_focused}, new ColorDrawable(color));

        return states;
    }

    public static StateListDrawable getColorStateDrawable(Context context, int color, int pressColor, int disableColor) {

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled}, new ColorDrawable(pressColor));
        states.addState(new int[] {-android.R.attr.state_pressed, android.R.attr.state_enabled}, new ColorDrawable(color));
        states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_enabled}, new ColorDrawable(color));
        states.addState(new int[] {-android.R.attr.state_enabled}, new ColorDrawable(disableColor));

        return states;
    }



    public static StateListDrawable getSelectColorStateDrawable(Context context, int color, int selectColor) {

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_checked}, context.getResources().getDrawable(selectColor));
        states.addState(new int[] {android.R.attr.state_checked}, context.getResources().getDrawable(selectColor));
        states.addState(new int[] {-android.R.attr.state_checked}, context.getResources().getDrawable(color));
        states.addState(new int[] {-android.R.attr.state_checked}, context.getResources().getDrawable(color));

        return states;
    }

    public static StateListDrawable getSelectColorStateDrawable(Context context, int color, int selectColor, int disableColor) {

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {-android.R.attr.state_enabled}, context.getResources().getDrawable(disableColor));
        states.addState(new int[] {android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(selectColor));
        states.addState(new int[] {android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(selectColor));
        states.addState(new int[] {-android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(color));
        states.addState(new int[] {-android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(color));

        return states;
    }

    public static StateListDrawable getSelectColorStateDrawable(Context context, int color, int selectColor, int pressColor, int selectPressColor) {

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_checked}, new ColorDrawable(selectPressColor));
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_checked}, new ColorDrawable(selectPressColor));
        states.addState(new int[] {-android.R.attr.state_pressed, android.R.attr.state_checked}, new ColorDrawable(selectColor));
        states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_checked}, new ColorDrawable(selectColor));
        states.addState(new int[] {android.R.attr.state_pressed, -android.R.attr.state_checked}, new ColorDrawable(pressColor));
        states.addState(new int[] {android.R.attr.state_focused, -android.R.attr.state_checked}, new ColorDrawable(pressColor));
        states.addState(new int[] {-android.R.attr.state_pressed, -android.R.attr.state_checked}, new ColorDrawable(color));
        states.addState(new int[] {-android.R.attr.state_focused, -android.R.attr.state_checked}, new ColorDrawable(color));

        return states;
    }


    public static StateListDrawable getSelectColorStateDrawable(Context context, int color, int selectColor, int pressId, int selectPressId, int disableColor) {

        StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {-android.R.attr.state_enabled}, new ColorDrawable(disableColor));
        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(selectPressId));
        states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(selectPressId));
        states.addState(new int[] {-android.R.attr.state_pressed, android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(selectColor));
        states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(selectColor));
        states.addState(new int[] {android.R.attr.state_pressed, -android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(pressId));
        states.addState(new int[] {android.R.attr.state_focused, -android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(pressId));
        states.addState(new int[] {-android.R.attr.state_pressed, -android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(color));
        states.addState(new int[] {-android.R.attr.state_focused, -android.R.attr.state_checked, android.R.attr.state_enabled}, new ColorDrawable(color));

        return states;
    }

    public static Drawable getBorderDrawable(int bgColor, int lineColor, int line) {

        line = convertScale(line);

        ShapeDrawable drawable1 = new ShapeDrawable(new RectShape());
        drawable1.getPaint().setColor(bgColor);

        ShapeDrawable drawable2 = new ShapeDrawable(new RectShape());
        drawable2.getPaint().setColor(lineColor);
        drawable2.getPaint().setStyle(Style.STROKE);
        drawable2.getPaint().setStrokeWidth(line);
        LayerDrawable layer = new LayerDrawable(new Drawable[]{drawable1, drawable2});

        return layer;
    }

    public static Drawable getRoundDrawable(int bgColor, float lefttop, float righttop, float leftbottom, float rightbottom) {

        lefttop = Math.max(1, lefttop * scale + 0.5f);
        righttop = Math.max(1, righttop * scale + 0.5f);
        leftbottom = Math.max(1, leftbottom * scale + 0.5f);
        rightbottom = Math.max(1, rightbottom * scale + 0.5f);

        float[] outerR = new float[] { lefttop, lefttop, righttop, righttop, leftbottom, leftbottom, rightbottom, rightbottom };

        ShapeDrawable drawable1 = new ShapeDrawable(new RoundRectShape(outerR, null, null));
        drawable1.getPaint().setColor(bgColor);

        return drawable1;
    }

    public static Drawable getRoundBorderDrawable(int bgColor, int lineColor, int line, float corner) {

        corner = Math.max(1, corner * scale + 0.5f);

        float[] outerR = new float[] { corner, corner, corner, corner, corner, corner, corner, corner };

        ShapeDrawable drawable1 = new ShapeDrawable(new RoundRectShape(outerR, null, null));
        drawable1.getPaint().setColor(bgColor);

        ShapeDrawable drawable2 = new ShapeDrawable(new RoundRectShape(outerR, null, null));
        drawable2.getPaint().setColor(lineColor);
        drawable2.getPaint().setStyle(Style.STROKE);
        drawable2.getPaint().setStrokeWidth(line);
        LayerDrawable layer = new LayerDrawable(new Drawable[]{drawable1, drawable2});

        return layer;
    }

    public static Drawable getRoundBorderDrawable(int bgColor, int lineColor, int line, float lefttop, float righttop, float rightbottom, float leftbottom) {

        lefttop = Math.max(1, lefttop * scale + 0.5f);
        righttop = Math.max(1, righttop * scale + 0.5f);
        leftbottom = Math.max(1, leftbottom * scale + 0.5f);
        rightbottom = Math.max(1, rightbottom * scale + 0.5f);

        float[] outerR = new float[] { lefttop, lefttop, righttop, righttop, rightbottom, rightbottom, leftbottom, leftbottom};

        ShapeDrawable drawable1 = new ShapeDrawable(new RoundRectShape(outerR, null, null));
        drawable1.getPaint().setColor(bgColor);

        ShapeDrawable drawable2 = new ShapeDrawable(new RoundRectShape(outerR, null, null));
        drawable2.getPaint().setColor(lineColor);
        drawable2.getPaint().setStyle(Style.STROKE);
        drawable2.getPaint().setStrokeWidth(line);
        LayerDrawable layer = new LayerDrawable(new Drawable[]{drawable1, drawable2});

        return layer;
    }


    public static Drawable getPaddingBorderDrawable(int bgColor, int lineColor, int line, int left, int top, int right, int bottom) {


        ShapeDrawable drawable1 = new ShapeDrawable(new RectShape());
        drawable1.getPaint().setColor(bgColor);

        ShapeDrawable drawable2 = new ShapeDrawable(new RectShape());
        drawable2.getPaint().setColor(lineColor);
        drawable2.getPaint().setStyle(Style.STROKE);
        drawable2.getPaint().setStrokeWidth(line);
        drawable2.setPadding(left, top, right, bottom);
        LayerDrawable layer = new LayerDrawable(new Drawable[]{drawable1, drawable2});

        return layer;
    }

    public static Drawable getLineDrawable(final int bgColor, final int lineColor, int leftStroke, int topStroke, int rightStroke, int bottomStroke) {

        final  float left = Math.max(1, (float)leftStroke * scale + 0.5f);
        final  float top = Math.max(1, (float)topStroke * scale + 0.5f);
        final  float right = Math.max(1, (float)rightStroke * scale + 0.5f);
        final  float bottom = Math.max(1, (float)bottomStroke * scale + 0.5f);

        ShapeDrawable drawable = new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {

                canvas.drawColor(bgColor);

                paint.setColor(Color.RED);
                if ( left > 0 ) {
                    paint.setStrokeWidth(left);
                    canvas.drawLine(0, 0, 0, canvas.getHeight(), paint);
                }
                if ( top > 0 ) {
                    paint.setStrokeWidth(top);
                    canvas.drawLine(0, 0, canvas.getWidth(), 0, paint);
                }
                if ( right > 0 ) {
                    paint.setStrokeWidth(right);
                    canvas.drawLine(canvas.getWidth() - right, 0, canvas.getWidth() - right, canvas.getHeight(), paint);
                }
                if ( bottom > 0 ) {
                    paint.setStrokeWidth(bottom);
                    canvas.drawLine(0, canvas.getHeight() - bottom - 10 , 0, canvas.getHeight() - bottom - 10, paint);
                }
            }
        });

        return drawable;
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {

        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }


    public static void showKeyboard(Context context, EditText editText) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(editText.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0 );
    }

    public static Object findParentTag(View view, Class clazz) {


        while (view != null) {
            if ( view.getParent() != null && view.getParent() instanceof View ) {
                View parent = (View)view.getParent();
                if ( parent.getTag() != null ) {
                    if ( parent.getTag().getClass() == clazz ) {
                        return parent.getTag();
                    }
                }
                view = parent;
            } else {
                break;
            }

        }
        return null;
    }
}
