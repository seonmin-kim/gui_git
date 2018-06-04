package kr.co.mystockhero.geotogong.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.layout.LinearLayout;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.R;

/**
 * Created by sesang on 16. 6. 3..
 */
public class ClickPreference extends Preference {

    public ClickPreference(Context context) {
        super(context);
    }

    public ClickPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public View getView(View convertView, ViewGroup parent) {

        View v = super.getView(convertView, parent);

        v.setBackgroundColor(0xffffffff);

        ViewGroup viewGroup = (ViewGroup)v;

        for( int i=0; i<viewGroup.getChildCount(); i++ ) {
            View view = viewGroup.getChildAt(i);
            if ( view instanceof ViewGroup ) {
                for( int j=0; j<((ViewGroup)view).getChildCount(); j++ ) {
                    View view2 = ((ViewGroup)view).getChildAt(j);

                }
            }
        }

//        AppCompatTextView title = (AppCompatTextView)v.findViewById(android.R.id.title);
//        title.setTextColor(0xff333333);
//
//        AppCompatTextView summary = (AppCompatTextView)v.findViewById(android.R.id.summary);
//        summary.setTextColor(0xff808080);

        View view = v.findViewById(android.R.id.title);

        ImageView.createImageView(null, getContext(), (ViewGroup)v,
                RelativeLayout.createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0, 0, 32, 0, ControlUtil.getImageSize(getContext(), R.drawable.next_01)),
                R.drawable.next_01);
//
//        LinearLayout.createLayout(null, getContext(), layout,
//                layout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
//                LinearLayout.VERTICAL, 0xffe5e5e5);

        return v;
    }
}