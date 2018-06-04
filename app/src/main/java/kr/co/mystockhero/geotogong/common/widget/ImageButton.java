package kr.co.mystockhero.geotogong.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.common.ControlUtil;

/**
 * Created by sesang on 16. 5. 23..
 */
public class ImageButton extends android.widget.ImageButton {

    public ImageButton(Context context) {

        super(context);
    }

    public ImageButton(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    private static ImageButton createImageButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, OnClickListener onClickListener) {

        ImageButton button = new ImageButton(context);
        button.setId(ControlUtil.generateViewId());
        button.setLayoutParams(layoutParams);
        button.setOnClickListener(onClickListener);

        if ( tag != null ) {
            button.setTag(tag);
        }
        if ( parent != null ) {
            parent.addView(button);
        }
        return button;
    }

    public static ImageButton createImageButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, Drawable drawable, OnClickListener onClickListener) {

        ImageButton button = createImageButton(tag, context, parent, layoutParams, onClickListener);

        if ( drawable != null ) {
            button.setImageDrawable(drawable);
        }

        return button;
    }

//	public static Button createImageButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, Drawable drawable, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createImageButton(tag, context, parent, layoutParams, drawable, onClickListener);
//	}
//
//	public static Button createImageButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, Drawable drawable, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createImageButton(tag, context, parent, layoutParams, drawable, onClickListener);
//	}

    public static ImageButton createImageButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int resId, OnClickListener onClickListener) {

//        layoutParams.width += 30;
//        layoutParams.height += 30;
        ImageButton button = createImageButton(tag, context, parent, layoutParams, onClickListener);

        if ( resId > 0 ) {
            button.setImageResource(resId);
        }

        return button;
    }

    public static ImageButton createImageButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int resId, int pressId, OnClickListener onClickListener) {

        return createImageButton(tag, context, parent, layoutParams, ControlUtil.getStateDrawable(context, resId, pressId), onClickListener);
    }

    public static ImageButton createImageButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int resId, int pressId, int desableId, OnClickListener onClickListener) {

        return createImageButton(tag, context, parent, layoutParams, ControlUtil.getStateDrawable(context, resId, pressId, desableId), onClickListener);
    }

}
