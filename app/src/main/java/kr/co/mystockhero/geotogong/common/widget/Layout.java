package kr.co.mystockhero.geotogong.common.widget;

import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)

public class Layout {

	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams) {
		
		RelativeLayout layout = new RelativeLayout(context);
		layout.setId(ControlUtil.generateViewId());
		
		if ( tag != null ) {
			layout.setTag(tag);
		}
		layout.setLayoutParams(layoutParams);
		if ( parent != null ) {
			parent.addView(layout);
		}	
		return layout;
	}
		
	@SuppressWarnings("deprecation")
	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams, Drawable drawable) {
		
		RelativeLayout layout = createLayout(tag, context, parent, layoutParams);
		
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {		
			layout.setBackgroundDrawable(drawable);
		} else {
			layout.setBackground(drawable);
		}
		
		return layout;
	}
	
	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams, int bgColor) {
		
		RelativeLayout layout = createLayout(tag, context, parent, layoutParams);
		layout.setBackgroundColor(bgColor);
		return layout;
	}
	
	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams, int bgColor, int lineColor, int line) {
		
		return createLayout(tag, context, parent, layoutParams, ControlUtil.getBorderDrawable(bgColor, lineColor, line));
	}
	
	
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createLayout(tag, context, parent, layoutParams);
//	}
//
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, Drawable drawable) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createLayout(tag, context, parent, layoutParams, drawable);
//	}
//
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int bgColor) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createLayout(tag, context, parent, layoutParams, bgColor);
//	}
//
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int bgColor, int lineColor, int line) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createLayout(tag, context, parent, layoutParams, ControlUtil.getBorderDrawable(bgColor, lineColor, line));
//	}
//
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createLayout(tag, context, parent,layoutParams);
//	}
//
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, int bgColor) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createLayout(tag, context, parent,layoutParams, bgColor);
//	}
//
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, Drawable drawable) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//		return createLayout(tag, context, parent, layoutParams, drawable);
//	}
//
//	public static RelativeLayout createLayout(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, int bgColor, int lineColor, int line) {
//
//		return createLayout(tag, context, parent, x, y, w, h, gravity, fixX, fixY, fixW, fixH, ControlUtil.getBorderDrawable(bgColor, lineColor, line));
//	}
	
	
	
	public static RelativeLayout createLine(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParams, int lineColor) {
		
		return createLayout(tag, context, parent, layoutParams, lineColor);
	}
	
//	public static RelativeLayout createLine(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int lineColor) {
//
//		return createLayout(tag, context, parent, x, y, w, h, lineColor);
//	}
//	public static RelativeLayout createLine(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, int lineColor) {
//
//		return createLayout(tag, context, parent, x, y, w, h, gravity, fixX, fixY, fixW, fixH, lineColor);
//	}
//
	
	
//	public static ListView createList(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
//			BaseAdapter listAdapter, int bgColor, int dividerColor, int dividerHeight) {
//
//		ListView listView = new ListView(context);
//
//		listView.setId(ControlUtil.generateViewId());
//
//		if ( tag != null ) {
//			listView.setTag(tag);
//		}
//		listView.setLayoutParams(layoutParams);
//		if ( parent != null ) {
//			parent.addView(listView);
//		}
//
//		listView.setClipChildren(false);
//		listView.setClipToPadding(false);
//		listView.setDescendantFocusability(ScrollView.FOCUS_BLOCK_DESCENDANTS);
//		listView.setAdapter(listAdapter);
//		listView.setSelector(new PaintDrawable(0x00000000));
//		listView.setCacheColorHint(0x00000000);
//
//		listView.setBackgroundColor(bgColor);
//
//		listView.setDivider(new ColorDrawable(dividerColor));
//		listView.setDividerHeight(dividerHeight);
//
//		return listView;
//	}
//
////	public static ListView createList(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h,
////			BaseAdapter listAdapter, int bgColor, int dividerColor, int dividerHeight) {
////
////		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
////		return createList(tag, context, parent, layoutParams, listAdapter, bgColor, dividerColor, dividerHeight);
////	}
//
//	public static ListView createList(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
//			BaseAdapter listAdapter, int bgColor) {
//
//		return createList(tag, context, parent, layoutParams, listAdapter, bgColor, bgColor, 0);
//	}
//
////	public static ListView createList(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h,
////			BaseAdapter listAdapter, int bgColor) {
////
////		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
////		return createList(tag, context, parent, layoutParams, listAdapter, bgColor, bgColor, 0);
////	}
////
////	public static ListView createList(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h,
////			BaseAdapter listAdapter) {
////
////		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
////		return createList(tag, context, parent, layoutParams, listAdapter, Color.TRANSPARENT, Color.TRANSPARENT, 0);
////	}
//
//	public static ListView createList(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
//			BaseAdapter listAdapter) {
//
//		return createList(tag, context, parent, layoutParams, listAdapter, Color.BLACK, Color.TRANSPARENT, 0);
//	}
	
	
	public static ScrollView createScrollView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int bgColor) {
		
		ScrollView scrollView = new ScrollView(context);
		scrollView.setId(ControlUtil.generateViewId());
		
		if ( tag != null ) {
			scrollView.setTag(tag);
		}
		scrollView.setLayoutParams(layoutParams);
		if ( parent != null ) {
			parent.addView(scrollView);
		}	
		scrollView.setBackgroundColor(bgColor);
		
		return scrollView;
	}
	
//	public static ScrollView createScrollView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int bgColor) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createScrollView(tag, context, parent, layoutParams, bgColor);
//	}

	public static ScrollView createScrollView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams) {
		
		return createScrollView(tag, context, parent, layoutParams, Color.TRANSPARENT);
	}



//	public static ScrollView createScrollView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createScrollView(tag, context, parent, layoutParams, Color.TRANSPARENT);
//	}
	
	
	
	public static HorizontalScrollView createHorizontalScrollView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int bgColor) {
		
		HorizontalScrollView scrollView = new HorizontalScrollView(context);
		scrollView.setId(ControlUtil.generateViewId());
		
		if ( tag != null ) {
			scrollView.setTag(tag);
		}
		scrollView.setLayoutParams(layoutParams);
		if ( parent != null ) {
			parent.addView(scrollView);
		}	
		scrollView.setBackgroundColor(bgColor);
		
		return scrollView;
	}
	
//	public static HorizontalScrollView createHorizontalScrollView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int bgColor) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createHorizontalScrollView(tag, context, parent, layoutParams, bgColor);
//	}

	public static HorizontalScrollView createHorizontalScrollView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams) {
		
		return createHorizontalScrollView(tag, context, parent, layoutParams, Color.TRANSPARENT);
	}
	

//	public static HorizontalScrollView createHorizontalScrollView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//		return createHorizontalScrollView(tag, context, parent, layoutParams, Color.TRANSPARENT);
//	}
}
