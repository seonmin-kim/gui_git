package kr.co.mystockhero.geotogong.common.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.R;

/**
 * Created by sesang on 16. 5. 20..
 */
public class LinearLayout extends android.widget.LinearLayout {

    public LinearLayout(Context context) {

        super(context);
    }

    public LinearLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public void setDrawable(Drawable drawable) {

        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }

    public void setBorder(int bgColor, int borderColor, int stroke) {

        setDrawable(ControlUtil.getBorderDrawable(bgColor, borderColor, stroke));
    }

    public static LinearLayout createLayout(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams) {

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(layoutParams);
        layout.setId(ControlUtil.generateViewId());
        if ( tag != null ) {
            layout.setTag(tag);
        }
        if ( parent != null ) {
            parent.addView(layout);
        }
        return layout;
    }

    public static LinearLayout createLayout(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int orientation) {

        LinearLayout layout = createLayout(tag, context, parent, layoutParams);
        layout.setOrientation(orientation);

        return layout;
    }

    public static LinearLayout createLayout(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int orientation, int color) {

        LinearLayout layout = createLayout(tag, context, parent, layoutParams, orientation);
        layout.setBackgroundColor(color);

        return layout;
    }

    public static LinearLayout createLayout(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int orientation, int color, int border, int stroke) {

        LinearLayout layout = createLayout(tag, context, parent, layoutParams, orientation);
        layout.setDrawable(ControlUtil.getBorderDrawable(color, border, stroke));
        return layout;
    }

    public static LinearLayout createLayout(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int orientation, int color, int border, int stroke, float corner) {

        LinearLayout layout = createLayout(tag, context, parent, layoutParams, orientation);
        if ( corner > 0 ) {
            layout.setDrawable(ControlUtil.getRoundBorderDrawable(color, border, stroke, corner));
        } else {
            layout.setDrawable(ControlUtil.getBorderDrawable(color, border, stroke));
        }
        return layout;
    }

    public static ViewGroup.LayoutParams createLayoutParams(int width, int height) {

        width = ControlUtil.convertWidth(false, width);
        height = ControlUtil.convertHeight(false, height);

        LayoutParams layoutParams = new LayoutParams(width, height);

        return layoutParams;
    }

    public static ViewGroup.LayoutParams createHorizontalWeightLayoutParams(int gravity, int left, int top, int right, int bottom, float weight, int height) {

        LayoutParams layoutParams = (LayoutParams)createScaledHeightLayoutParams(gravity, left, top, right, bottom, LayoutParams.MATCH_PARENT, height);
        layoutParams.weight = weight;

        return layoutParams;
    }

    public static ViewGroup.LayoutParams createVerticalWeightLayoutParams(int gravity, int left, int top, int right, int bottom, int width, float weight) {

        LayoutParams layoutParams = (LayoutParams)createScaledHeightLayoutParams(gravity, left, top, right, bottom, width, LayoutParams.MATCH_PARENT);
        layoutParams.weight = weight;

        return layoutParams;
    }

    public static ViewGroup.LayoutParams createHorizontalLayoutParams(int gravity, int left, int right, int width, int height) {

        return createLayoutParams(gravity, left, 0, right, 0, width, height);
    }

    public static ViewGroup.LayoutParams createHorizontalScaledLayoutParams(int gravity, int left, int right, int width, int height) {

        return createScaledLayoutParams(gravity, left, 0, right, 0, width, height);
    }

    public static ViewGroup.LayoutParams createVerticalLayoutParams(int gravity, int top, int bottom, int width, int height) {

        return createLayoutParams(gravity, 0, top, 0, bottom, width, height);
    }

    public static ViewGroup.LayoutParams createVerticalScaledLayoutParams(int gravity, int top, int bottom, int width, int height) {

        return createScaledLayoutParams(gravity, 0, top, 0, bottom, width, height);
    }

    public static ViewGroup.LayoutParams createWeightLayoutParams(float weight, int width, int height) {

        width = ControlUtil.convertWidth(false, width);
        height = ControlUtil.convertHeight(false, height);

        LayoutParams layoutParams = new LayoutParams(width, height);

        layoutParams.weight = weight;

        return layoutParams;
    }


    public static ViewGroup.LayoutParams createLayoutParams(int gravity, int left, int top, int right, int bottom, int width, int height) {

        return createLayoutParams(gravity, left, top, right, bottom, width, height, false, false, false, false, false, false);
    }

    public static ViewGroup.LayoutParams createScaledLayoutParams(int gravity, int left, int top, int right, int bottom, int width, int height) {

        return createLayoutParams(gravity, left, top, right, bottom, width, height, true, true, true, true, true, true);
    }

    public static ViewGroup.LayoutParams createScaledHeightLayoutParams(int gravity, int left, int top, int right, int bottom, int width, int height) {

        return createLayoutParams(gravity, left, top, right, bottom, width, height, false, true, false, true, false, true);
    }

    public static ViewGroup.LayoutParams createScaledWidthLayoutParams(int gravity, int left, int top, int right, int bottom,  int width, int height) {

        return createLayoutParams(gravity, left, top, right, bottom, width, height, true, false, true, false, true, false);
    }

    public static ViewGroup.LayoutParams createLayoutParams(int gravity, int left, int top, int right, int bottom, Point size) {

        return createLayoutParams(gravity, left, top, right, bottom, size, false, false, false, false, false, false);
    }

    public static ViewGroup.LayoutParams createScaledLayoutParams(int gravity, int left, int top, int right, int bottom, Point size) {

        return createLayoutParams(gravity, left, top, right, bottom, size, true, true, true, true, true, true);
    }

    public static ViewGroup.LayoutParams createLayoutParams(int gravity, int left, int top, int right, int bottom, Point size,
                                                            boolean scaleLeft, boolean scaleTop, boolean scaleRight, boolean scaleBottom, boolean scaleWidth, boolean scaleHeight) {

        return createLayoutParams(gravity, left, top, right, bottom, size.x, size.y, scaleLeft, scaleTop, scaleRight, scaleBottom, scaleWidth, scaleHeight);
    }

    public static ViewGroup.LayoutParams createLayoutParams(int gravity, int left, int top, int right, int bottom, int width, int height,
                                                     boolean scaleLeft, boolean scaleTop, boolean scaleRight, boolean scaleBottom, boolean scaleWidth, boolean scaleHeight) {

        left =  ControlUtil.convertWidth(scaleLeft, left);
        top = ControlUtil.convertHeight(scaleTop, top);

        right = ControlUtil.convertWidth(scaleRight, right);
        bottom = ControlUtil.convertHeight(scaleBottom, bottom);

        width = ControlUtil.convertWidth(scaleWidth, width);
        height = ControlUtil.convertHeight(scaleHeight, height);

        LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.gravity = gravity;

        if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL || (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL ) {

            layoutParams.leftMargin = left;
            layoutParams.rightMargin = right;

        } else if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.LEFT ) {

            layoutParams.leftMargin = left;

        } else if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT ) {

//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            layoutParams.rightMargin = right;

        } else if ( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL || (gravity & Gravity.CENTER) == Gravity.CENTER ) {

//            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParams.leftMargin = left;
            layoutParams.rightMargin = right;

        } else {

            layoutParams.leftMargin = left;
            layoutParams.rightMargin = right;
        }


        if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL || (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL ) {

            layoutParams.topMargin = top;
            layoutParams.bottomMargin = bottom;

        } else if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP ) {

            layoutParams.topMargin = top;

        } else if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM ) {

//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.bottomMargin = bottom;

        } else if ( (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL || (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER ) {

//            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            layoutParams.topMargin = top;
            layoutParams.bottomMargin = bottom;

        } else {
            layoutParams.topMargin = top;
            layoutParams.bottomMargin = bottom;
        }

        return layoutParams;
    }
}
