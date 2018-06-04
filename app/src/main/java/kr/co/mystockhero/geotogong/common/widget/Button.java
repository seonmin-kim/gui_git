package kr.co.mystockhero.geotogong.common.widget;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressWarnings("deprecation")

public class Button extends android.widget.Button {

	public Button(Context context) {
		
		super(context);
	}

	public Button(Context context, AttributeSet attrs) {

		super(context, attrs);
	}
	
	public void setButtonImage(Context context, int resId, int pressId) {
		
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {		
			setBackgroundDrawable(ControlUtil.getStateDrawable(context, resId, pressId));
		} else {
			setBackground(ControlUtil.getStateDrawable(context, resId, pressId));
		}
	}
	
	public void setButtonImage(Context context, int resId, int pressId, int disableId) {
		
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {		
			setBackgroundDrawable(ControlUtil.getStateDrawable(context, resId, pressId, disableId));
		} else {
			setBackground(ControlUtil.getStateDrawable(context, resId, pressId, disableId));
		}
	}
	
	private static Button createButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, OnClickListener onClickListener) {

		Button button = new Button(context);
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
	
	public static Button createButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, Drawable drawable, OnClickListener onClickListener) {

		Button button = new Button(context);
		button.setId(ControlUtil.generateViewId());
		button.setLayoutParams(layoutParams);
		button.setOnClickListener(onClickListener);
		
		if ( tag != null ) {
			button.setTag(tag);
		}		
		if ( parent != null ) {
			parent.addView(button);
		}
		
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {		
			button.setBackgroundDrawable(drawable);
		} else {
			button.setBackground(drawable);
		}

	    return button;
	}
	
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, Drawable drawable, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createButton(tag, context, parent, layoutParams, drawable, onClickListener);
//	}
//
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, Drawable drawable, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createButton(tag, context, parent, layoutParams, drawable, onClickListener);
//	}
	
	public static Button createButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int resId, OnClickListener onClickListener) {

		Button button = createButton(tag, context, parent, layoutParams, onClickListener);
		if ( resId > 0 ) {
			button.setBackgroundResource(resId);
		}
		
	    return button;
	}
	
	public static Button createButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int resId, int pressId, OnClickListener onClickListener) {

		return createButton(tag, context, parent, layoutParams, ControlUtil.getStateDrawable(context, resId, pressId), onClickListener);
	}
		
	public static Button createButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int resId, int pressId, int desableId, OnClickListener onClickListener) {

		return createButton(tag, context, parent, layoutParams, ControlUtil.getStateDrawable(context, resId, pressId, desableId), onClickListener);
	}


	
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int resId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createButton(tag, context, parent, layoutParams, resId, onClickListener);
//	}
//
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int resId, int pressId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createButton(tag, context, parent, layoutParams, resId, pressId, onClickListener);
//	}
//
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int resId, int pressId, int desableId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createButton(tag, context, parent, layoutParams, resId, pressId, desableId, onClickListener);
//	}
//
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, int resId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createButton(tag, context, parent, layoutParams, resId, onClickListener);
//	}

//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, int resId, int pressId, OnClickListener onClickListener) {
//
//		Point point = ControlUtil.getImageSize(context, resId);
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, point.x, point.y, parent, gravity, fixX, fixY, fixW, fixH);
//		return createButton(tag, context, parent, layoutParams, resId, pressId, onClickListener);
//	}
//
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, int resId, int pressId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createButton(tag, context, parent, layoutParams, resId, pressId, onClickListener);
//	}
//
//	public static Button createButton(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,  int resId, int pressId, int desableId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createButton(tag, context, parent, layoutParams, resId, pressId, desableId, onClickListener);
//	}
}
