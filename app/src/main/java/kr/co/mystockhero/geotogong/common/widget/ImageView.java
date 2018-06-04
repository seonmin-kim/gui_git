package kr.co.mystockhero.geotogong.common.widget;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class ImageView extends android.widget.ImageView  {

	public ImageView(Context context) {
		
		super(context);
	}

	public ImageView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, LayoutParams layoutParams) {

		ImageView imageView = new ImageView(context) {
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// CommonUtil.DebugLog("imageView onTouchEvent");
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return false;
				}
				return super.onTouchEvent(event);
			}
		};
		imageView.setId(ControlUtil.generateViewId());
		if ( tag != null ) {
			imageView.setTag(tag);
		}
		imageView.setScaleType(ScaleType.FIT_XY);
//		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(layoutParams);

		if ( parent != null ) {
			parent.addView(imageView);
		}
		return imageView;
	}

	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, LayoutParams layoutParam, int resId) {

		ImageView imageView = createImageView(tag, context, parent, layoutParam);
		if ( resId > 0 ) {
			imageView.setImageResource(resId);
		} else {
			imageView.setImageDrawable(null);
		}

		return imageView;
	}

	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, LayoutParams layoutParam, Bitmap bitmap) {

		ImageView imageView = createImageView(tag, context, parent, layoutParam);
		imageView.setImageBitmap(bitmap);

		return imageView;
	}

	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, LayoutParams layoutParam, String name) {

		int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());

		ImageView imageView = createImageView(tag, context, parent, layoutParam);
		if ( resId > 0 ) {
			imageView.setImageResource(resId);
		} else {
			imageView.setImageDrawable(null);
		}

		return imageView;
	}

//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, RelativeLayout.LayoutParams layoutParam) {
//
//		ImageView imageView = new ImageView(context);
//		imageView.setId(ControlUtil.generateViewId());
//		if ( tag != null ) {
//	    	imageView.setTag(tag);
//	    }
//
////	    imageView.setScaleType(ScaleType.MATRIX);
////	    Matrix matrix = new Matrix();
////	    float[] value = new float[9];
////		matrix.getValues(value);
////		value[0] = ControlUtil.iScale;
////		value[4] = ControlUtil.iScale;
//
////		matrix.setValues(value);
////		imageView.setImageMatrix(matrix);
//
//		imageView.setScaleType(ScaleType.FIT_XY);
//
//	    imageView.setLayoutParams(layoutParam);
//
//	    if ( parent != null ) {
//			parent.addView(imageView);
//		}
//	    return imageView;
//	}
//
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParam, int resId) {
//
//		ImageView imageView = createImageView(tag, context, parent, layoutParam);
//		if ( resId > 0 ) {
//			imageView.setImageResource(resId);
//		} else {
//			imageView.setImageDrawable(null);
//		}
//	    return imageView;
//	}
//
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int resId) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//	    return createImageView(tag, context, parent, layoutParams, resId);
//	}
//
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent,
//			int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			int resId) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//	    return createImageView(tag, context, parent, layoutParams, resId);
//	}
	
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, LayoutParams layoutParams, int resId, int imgGravity) {
//
//		ImageView imageView = new ImageView(context);
//		imageView.setId(ControlUtil.generateViewId());
//	    if ( tag != null ) {
//	    	imageView.setTag(tag);
//	    }
//
////		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//	    imageView.setLayoutParams(layoutParams);
//
//	    if ( resId > 0 ) {
//			imageView.setImageResource(resId);
//		} else {
//			imageView.setImageDrawable(null);
//		}
//
//
//	    if ( (imgGravity & Gravity.FILL) == Gravity.FILL ) {
//
//	    	imageView.setScaleType(ScaleType.FIT_XY);
//
//	    } else {
//
//	    	imageView.setScaleType(ScaleType.MATRIX);
//
//	    	Point point = ControlUtil.getImageSize(context, resId);
//
//		    Matrix matrix = new Matrix();
//		    float[] value = new float[9];
//			matrix.getValues(value);
//			value[Matrix.MSCALE_X] = ControlUtil.iScale;
//			value[Matrix.MSCALE_Y] = ControlUtil.iScale;
//
//			CommonUtil.DebugLog("***" + value[Matrix.MSCALE_X] + ", " + value[Matrix.MSCALE_Y] + ", " + layoutParams.width + ", " + layoutParams.height);
////			if ( (imgGravity&Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER || (layoutParams. & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL ) {
////				value[Matrix.MTRANS_X] = (layoutParams.width - point.x *ControlUtil.iScale) / 2;
////			} else if ( (imgGravity&Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT ) {
////				value[Matrix.MTRANS_X] = (layoutParams.width - point.x *ControlUtil.iScale);
////			} else {
////				value[Matrix.MTRANS_X] = 0;
////			}
////
////			if ( (imgGravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER || (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL ) {
////				value[Matrix.MTRANS_Y] = (layoutParams.height - point.y *ControlUtil.iScale) / 2;
////			} else if ( (imgGravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM ) {
////				value[Matrix.MTRANS_Y] = (layoutParams.height - point.y *ControlUtil.iScale);
////			} else {
////				value[Matrix.MTRANS_Y] = 0;
////			}
//			matrix.setValues(value);
//			imageView.setImageMatrix(matrix);
//	    }
//
//
//	    if ( parent != null ) {
//			parent.addView(imageView);
//		}
//	    return imageView;
//	}


//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, Bitmap bitmap) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//	    return createImageView(tag, context, parent, layoutParams, bitmap);
//	}
//
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, Bitmap bitmap) {
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//	    return createImageView(tag, context, parent, layoutParams, bitmap);
//	}
	
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, int w, int h, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH,
//			Bitmap bitmap, int imgGravity) {
//
//		if ( bitmap == null ) return null;
//
//		ImageView imageView = new ImageView(context);
//		imageView.setId(ControlUtil.generateViewId());
//		if ( tag != null ) {
//	    	imageView.setTag(tag);
//	    }
//
//		RelativeLayout.LayoutParams layoutParams = ControlUtil.getConvertLayoutParams(x, y, w, h, parent, gravity, fixX, fixY, fixW, fixH);
//	    imageView.setLayoutParams(layoutParams);
//
//	    if ( (imgGravity&Gravity.FILL) == Gravity.FILL ) {
//
//	    	imageView.setScaleType(ScaleType.FIT_XY);
//
//	    } else {
//
//	    	imageView.setScaleType(ScaleType.MATRIX);
//
//		    Matrix matrix = new Matrix();
//		    float[] value = new float[9];
//			matrix.getValues(value);
//			value[Matrix.MSCALE_X] = ControlUtil.iScale;
//			value[Matrix.MSCALE_Y] = ControlUtil.iScale;
//
//			if ( (imgGravity&Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER || (gravity&Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL ) {
//				value[Matrix.MTRANS_X] = (layoutParams.width - bitmap.getWidth() *ControlUtil.iScale) / 2;
//			} else if ( (imgGravity&Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT ) {
//				value[Matrix.MTRANS_X] = (layoutParams.width - bitmap.getWidth() *ControlUtil.iScale);
//			} else {
//				value[Matrix.MTRANS_X] = 0;
//			}
//
//			if ( (imgGravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER || (gravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL ) {
//				value[Matrix.MTRANS_Y] = (layoutParams.height - bitmap.getHeight() *ControlUtil.iScale) / 2;
//			} else if ( (imgGravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM ) {
//				value[Matrix.MTRANS_Y] = (layoutParams.height - bitmap.getHeight() *ControlUtil.iScale);
//			} else {
//				value[Matrix.MTRANS_Y] = 0;
//			}
//			matrix.setValues(value);
//			imageView.setImageMatrix(matrix);
//	    }
//
//	    imageView.setImageBitmap(bitmap);
//
//
//	    if ( parent != null ) {
//			parent.addView(imageView);
//		}
//	    return imageView;
//	}
	
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, int resId) {
//
//		Point point = ControlUtil.getImageSize(context, resId);
//	    return createImageView(tag, context, parent, x, y, point.x, point.y, gravity, fixX, fixY, fixW, fixH, resId);
//	}
//
//
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, int resId) {
//
//		Point point = ControlUtil.getImageSize(context, resId);
//		RelativeLayout.LayoutParams layoutParam = ControlUtil.getConvertLayoutParams(x, y, point.x, point.y, parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//	    return createImageView(tag, context, parent, layoutParam, resId);
//	}
//
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, int gravity, boolean fixX, boolean fixY, boolean fixW, boolean fixH, Bitmap bitmap) {
//
//		if ( bitmap == null ) return null;
//
//	    return createImageView(tag, context, parent, x, y, bitmap.getWidth(), bitmap.getHeight(), gravity, fixX, fixY, fixW, fixH, bitmap);
//	}
//
//	public static ImageView createImageView(Object tag, Context context, ViewGroup parent, int x, int y, Bitmap bitmap) {
//
//		if ( bitmap == null ) return null;
//
//		RelativeLayout.LayoutParams layoutParam = ControlUtil.getConvertLayoutParams(x, y, bitmap.getWidth(), bitmap.getHeight(), parent, Gravity.TOP|Gravity.LEFT, true, true, true, true);
//	    return createImageView(tag, context, parent, layoutParam, bitmap);
//	}
}


