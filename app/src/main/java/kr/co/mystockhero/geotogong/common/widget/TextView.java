package kr.co.mystockhero.geotogong.common.widget;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.gms.common.api.Scope;

import java.util.ResourceBundle;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class TextView extends android.widget.TextView {

	public TextView(Context context) {
		
		super(context);
	}

	public TextView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public static void setTypeface(TextView textView, Typeface typeface) {
		
		if ( typeface == null ) {
			textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		} else {
			textView.setTypeface(typeface);
		}
	}
	
	public static TextView createTextView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
			int gravity, int line, int size, int spacing, boolean fix, int color, Typeface font, boolean bold) {

		TextView textView = new TextView(context) {
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// CommonUtil.DebugLog("textView onTouchEvent");
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return false;
				}
				return super.onTouchEvent(event);
			}

//			@Override
//			public boolean dispatchTouchEvent(MotionEvent e) {
//				if ( getParent() != null ) {
//					getParent().requestDisallowInterceptTouchEvent(true);
//					return true;
//				}
//				return super.dispatchTouchEvent(e);
//			}
		};
		textView.setId(ControlUtil.generateViewId());
		
		if ( tag != null ) {
			textView.setTag(tag);
		}
		textView.setLayoutParams(layoutParams);
		if ( parent != null ) {
			parent.addView(textView);
		}

		textView.setTextColor(color);
		textView.setGravity(gravity);
		if ( line == 1 ) {
			textView.setSingleLine();
			textView.setScrollContainer(false);
			textView.setEllipsize(TextUtils.TruncateAt.END);
		} else if ( line > 0 ){
			textView.setLines(line);
		}
		textView.setLineSpacing(ControlUtil.convertHeight(fix, spacing), 1);
		textView.setBackgroundColor(Color.TRANSPARENT);
		textView.setIncludeFontPadding(false);
		textView.setPadding(0, 0, 0, 5);


//		textView.setScrollContainer(false);
//		textView.setEllipsize(TextUtils.TruncateAt.END);
//		textView.setFocusable(false);
//		textView.setFocusableInTouchMode(false);
//		textView.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
////				if ( v.getParent() != null ) {
////					v.getParent().requestDisallowInterceptTouchEvent(true);
////					return true;
////				}
////				return true;
//
//				CommonUtil.DebugLog("textview");
//				return true;
//			}
//		});

		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ControlUtil.convertHeight(fix, size));

		if ( font != null ) {
			textView.setTypeface(font);
			if ( bold ) {
				textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			}
		} else if ( ControlUtil.typeface != null ) {
			textView.setTypeface(ControlUtil.typeface);
			if ( bold ) {
				textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			}
		} else {
			if ( bold ) {
				textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
			}
		}

	    return textView;
	}

	public static TextView createTextView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
										  String text, int gravity, int line, int size, int spacing, boolean fix, int color, Typeface font, boolean bold) {

		TextView textView = createTextView(tag, context, parent, layoutParams, gravity, line, size, spacing, fix, color, font, bold);
		textView.setText(text);
		return textView;
	}

	public static TextView createTextView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
										  Spanned text, int gravity, int line, int size, int spacing, boolean fix, int color, Typeface font, boolean bold) {

		TextView textView = createTextView(tag, context, parent, layoutParams, gravity, line, size, spacing, fix, color, font, bold);
		textView.setText(text);
		return textView;
	}
//	public static TextView createTextView(Object tag, Context context, ViewGroup parent,
//										  int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//										  String text, int textGravity, int line, int size, int spacing, boolean fix, int color, Typeface font, boolean bold) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createTextView(tag, context, parent, layoutParams, text, textGravity, line, size, spacing, fix, color, font, bold);
//	}
//
//	public static TextView createTextView(Object tag, Context context, ViewGroup parent,
//										  int x, int y, int w, int h, int gravity,
//										  String text, int textGravity, int line, int size, int spacing, boolean fix, int color, Typeface font, boolean bold) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, false, false, false, false);
//		return createTextView(tag, context, parent, layoutParams, text, textGravity, line, size, spacing, fix, color, font, bold);
//	}
//



//	public void addRule(int verb, int anchor) {
//
//		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
//		layoutParams.addRule(verb, anchor);
//	}




	
//	public static TextView createTextView(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams,
//			String text, int gravity, int line, int size, int spacing, boolean fix, int color, boolean bold, int bg) {
//
//		TextView textView = createTextView(tag, context, parent, layoutParams, text, gravity, line, size, spacing, fix, color, bold, bg);
//		textView.setBackgroundColor(bg);
//
//		return textView;
//	}
//
//
//	public static TextView createTextView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h,
//			String text, int gravity, int line, int size, int spacing, boolean fix, int color, boolean bold, int bg) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createTextView(tag, context, parent, layoutParams, text, gravity, line, size, spacing, fix, color, bold, bg);
//	}
//
//	@SuppressWarnings("deprecation")
//	public static TextView createImageTextView(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams,
//			String text, int gravity, int line, int size, int spacing, boolean fix, int color, boolean bold, Drawable drawable) {
//
//		TextView textView = createTextView(tag, context, parent, layoutParams, text, gravity, line, size, spacing, fix, color, bold);
//
//		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
//			textView.setBackgroundDrawable(drawable);
//		} else {
//			textView.setBackground(drawable);
//		}
//
//		return textView;
//	}
//
//	public static TextView createImageTextView(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			String text, int textGravity, int line, int size, int spacing, boolean fix, int color, boolean bold, Drawable drawable) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createImageTextView(tag, context, parent, layoutParams, text, textGravity, line, size, spacing, fix, color, bold, drawable);
//	}
//
//
//	public static TextView createImageTextView(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams,
//			String text, int gravity, int line, int size, int spacing, boolean fix, int color, boolean bold, int resId) {
//
//
//		return createImageTextView(tag, context, parent, layoutParams, text, gravity, line, size, spacing, fix, color, bold, context.getResources().getDrawable(resId));
//	}
//
//	public static TextView createImageTextView(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			String text, int textGravity, int line, int size, int spacing, boolean fix, int color, boolean bold, int resId) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createImageTextView(tag, context, parent, layoutParams, text, textGravity, line, size, spacing, fix, color, bold, context.getResources().getDrawable(resId));
//	}
//
}
