package kr.co.mystockhero.geotogong.common.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.common.ControlUtil;

/**
 * Created by sesang on 16. 5. 22..
 */
public class CheckBox extends android.widget.CheckBox {


    public CheckBox(Context context) {

        super(context);
    }

    public CheckBox(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public static CheckBox createCheckBox(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,  OnClickListener onClickListener) {

        CheckBox checkBox = new CheckBox(context);
        checkBox.setId(ControlUtil.generateViewId());
        checkBox.setLayoutParams(layoutParams);
        checkBox.setOnClickListener(onClickListener);

        if ( tag != null ) {
            checkBox.setTag(tag);
        }
        if ( parent != null ) {
            parent.addView(checkBox);
        }
        return checkBox;
    }

    public static CheckBox createCheckBox(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
                                          int resId, int checkId, int disableId,
                                          OnClickListener onClickListener) {

        CheckBox checkBox = createCheckBox(tag, context, parent, layoutParams, onClickListener);
        Drawable drawable = ControlUtil.getSelectStateDrawable(context, resId, checkId, disableId);

        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
            checkBox.setBackgroundDrawable(drawable);
        } else {
            checkBox.setBackground(drawable);
        }
        checkBox.setButtonDrawable(new ColorDrawable());


        return checkBox;
    }

    public static CheckBox createCheckBox(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
                                          Drawable drawable,
                                          OnClickListener onClickListener) {

        CheckBox checkBox = createCheckBox(tag, context, parent, layoutParams, onClickListener);
        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
            checkBox.setBackgroundDrawable(drawable);
        } else {
            checkBox.setBackground(drawable);
        }
        checkBox.setButtonDrawable(null);

        return checkBox;
    }

}
