package kr.co.mystockhero.geotogong.common.widget;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressWarnings("deprecation")
public class ToggleButton extends android.widget.ToggleButton {
	
	public ToggleButton(Context context) {
		
		super(context);
	}

	public ToggleButton(Context context, AttributeSet attrs) {

		super(context, attrs);
	}
	
	private static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, OnClickListener onClickListener) {

		ToggleButton button = new ToggleButton(context);
		button.setId(ControlUtil.generateViewId());		
		button.setLayoutParams(layoutParams);
		
		button.setButtonDrawable(null);
		button.setText("");
		button.setTextOff("");
		button.setTextOn("");
		
		button.setFreezesText(false);
		
		button.setOnClickListener(onClickListener);
		
		if ( tag != null ) {
			button.setTag(tag);
		}		
		if ( parent != null ) {
			parent.addView(button);
		}
	    return button;
	}

	private static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
			Drawable drawable, OnClickListener onClickListener) {

		ToggleButton button = createToggleButton(tag, context, parent, layoutParams, onClickListener);

		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
			button.setBackgroundDrawable(drawable);
		} else {
			button.setBackground(drawable);
		}
	    return button;
	}
//
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
//			int resId, OnClickListener onClickListener) {
//
//		return createToggleButton(tag, context, parent, layoutParams, context.getResources().getDrawable(resId), onClickListener);
//	}
//
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
//			int resId, int selectId, OnClickListener onClickListener) {
//
//		return createToggleButton(tag, context, parent, layoutParams, ControlUtil.getSelectStateDrawable(context, resId, selectId), onClickListener);
//	}
		
	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
			int resId, int selectId, int desableId, OnClickListener onClickListener) {

		return createToggleButton(tag, context, parent, layoutParams, ControlUtil.getSelectStateDrawable(context, resId, selectId, desableId), onClickListener);
	}

//	private static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
//			int resId, int selectId, int pressId, int selectPressId, int desableId, OnClickListener onClickListener) {
//
//		return createToggleButton(tag, context, parent, layoutParams, ControlUtil.getSelectStateDrawable(context, resId, selectId, pressId, selectPressId, desableId), onClickListener);
//	}

	
	
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int resId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		ToggleButton button = createToggleButton(tag, context, parent, layoutParams, resId, onClickListener);
//
//	    return button;
//	}
//
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int resId, int selectId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createToggleButton(tag, context, parent, layoutParams, resId, selectId, onClickListener);
//	}
//
//	private static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int resId, int selectId, int desableId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createToggleButton(tag, context, parent, layoutParams, resId, selectId, desableId, onClickListener);
//	}
//
//	private static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int resId, int selectId, int pressId, int selectPressId, int desableId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createToggleButton(tag, context, parent, layoutParams, resId, selectId, pressId, selectPressId, desableId, onClickListener);
//	}
//
//
//
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			int resId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		ToggleButton button = createToggleButton(tag, context, parent, layoutParams, resId, onClickListener);
//
//	    return button;
//	}
//
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			int resId, int selectId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createToggleButton(tag, context, parent, layoutParams, resId, selectId, onClickListener);
//	}
//
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			int resId, int selectId, int desableId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createToggleButton(tag, context, parent, layoutParams, resId, selectId, desableId, onClickListener);
//	}
//
//	public static ToggleButton createToggleButton(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			int resId, int selectId, int pressId, int selectPressId, int desableId, OnClickListener onClickListener) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createToggleButton(tag, context, parent, layoutParams, resId, selectId, pressId, selectPressId, desableId, onClickListener);
//	}
}
