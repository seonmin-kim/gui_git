package kr.co.mystockhero.geotogong.common.widget;

import kr.co.mystockhero.geotogong.common.ControlUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class EditText extends android.widget.EditText {

	public EditText(Context context) {
		
		super(context);
	}

	public EditText(Context context, AttributeSet attrs) {

		super(context, attrs);
	}
	
	public static EditText createEditText(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
										  String hint, String text, int gravity, int line, int max, int size, boolean fix, int color, Typeface font, boolean bold) {
		
		EditText edit = new EditText(context);
		edit.setId(ControlUtil.generateViewId());
		
		if ( tag != null ) {
			edit.setTag(tag);
		}
		edit.setLayoutParams(layoutParams);
		if ( parent != null ) {
			parent.addView(edit);
		}
		
		edit.setText(text);
		edit.setTextColor(color);
		edit.setHintTextColor(0xffcccccc);
		edit.setGravity(gravity);
		if ( line == 1 ) {
			edit.setSingleLine();
		} else if ( line > 0 ){
			edit.setLines(line);
		}
		edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(max)});
		edit.setHint(hint);
		edit.setBackgroundColor(Color.TRANSPARENT);
		edit.setIncludeFontPadding(false);
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);

		if ( fix ) {
			edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, ControlUtil.convertScale(size));
		} else {
			edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, ControlUtil.convertHeight(size));
		}
		if ( font != null ) {
			edit.setTypeface(font);
		} else if ( ControlUtil.typeface != null ) {
			edit.setTypeface(ControlUtil.typeface);
			if ( bold ) {
				edit.setPaintFlags(edit.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			}
		} else {
			if ( bold ) {
				edit.setPaintFlags(edit.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			}
		}
	    return edit;
	}

	public static EditText createEditText(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
											   String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, Typeface font, boolean bold, int bg) {

		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, font, bold);
		edit.setBackgroundColor(bg);
		return edit;
	}
	
//	public static EditText createEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold);
//
//		return edit;
//	}
//
//	public static EditText createEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, true, true, true, true);
//		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold);
//
//		return edit;
//	}
//
	

	
//	public static EditText createImageEditText(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, Typeface font, boolean bold, int bg, int focusBg) {
//
//		return createImageEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, font, bold, ControlUtil.getSelectStateDrawable(context,bg, focusBg));
//	}
	
	@SuppressWarnings("deprecation")
	public static EditText createEditText(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, Typeface font, boolean bold, Drawable drawable) {

		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, font, bold);
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
			edit.setBackgroundDrawable(drawable);
		} else {
			edit.setBackground(drawable);
		}

		return edit;
	}

	@SuppressWarnings("deprecation")
	public static EditText createEditText(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
										  String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, Typeface font, boolean bold,
										  Drawable drawable, Drawable focusDrawable) {

		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, font, bold);

		StateListDrawable states = new StateListDrawable();

		states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_checked}, focusDrawable);
		states.addState(new int[] {android.R.attr.state_focused, -android.R.attr.state_checked}, focusDrawable);
		states.addState(new int[] {-android.R.attr.state_focused, android.R.attr.state_checked}, drawable);
		states.addState(new int[] {-android.R.attr.state_focused, -android.R.attr.state_checked}, drawable);

		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
			edit.setBackgroundDrawable(states);
		} else {
			edit.setBackground(states);
		}

		return edit;
	}

//	public static EditText createImageEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold, int bg) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold);
//		edit.setBackgroundColor(bg);
//		return edit;
//	}
//
//	public static EditText createImageEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold, int bg, int focusBg) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createImageEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold, ControlUtil.getSelectStateDrawable(context,bg, focusBg));
//	}
//
//	@SuppressWarnings("deprecation")
//	public static EditText createImageEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold, Drawable drawable) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold);
//		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
//			edit.setBackgroundDrawable(drawable);
//		} else {
//			edit.setBackground(drawable);
//		}
//
//		return edit;
//	}
//
//
//	public static EditText createImageEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold, int bg) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, true, true, true, true);
//		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold);
//		edit.setBackgroundColor(bg);
//		return edit;
//	}
//
//	public static EditText createImageEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold, int bg, int focusBg) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, true, true, true, true);
//		return createImageEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold, ControlUtil.getSelectStateDrawable(context,bg, focusBg));
//	}
//
//	@SuppressWarnings("deprecation")
//	public static EditText createImageEditText(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity,
//			String hint, String text, int textGravity, int line, int max, int size, boolean fix, int color, boolean bold, Drawable drawable) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, true, true, true, true);
//		EditText edit = createEditText(tag, context, parent, layoutParams, hint, text, textGravity, line, max, size, fix, color, bold);
//		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
//			edit.setBackgroundDrawable(drawable);
//		} else {
//			edit.setBackground(drawable);
//		}
//
//		return edit;
//	}
}
