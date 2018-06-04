package kr.co.mystockhero.geotogong.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.layout.LinearLayout;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.R;

/**
 * Created by sesang on 16. 6. 3..
 */
public class SwitchPreference extends android.preference.SwitchPreference {

    @SuppressLint("NewApi")
    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchPreference(Context context) {
        super(context);
    }

    @Override
    public View getView(View convertView, ViewGroup parent) {

        View v = super.getView(convertView, parent);

        ViewGroup viewGroup = (ViewGroup)v;

        for( int i=0; i<viewGroup.getChildCount(); i++ ) {
            View view = viewGroup.getChildAt(i);
            if ( view instanceof ViewGroup ) {
                for( int j=0; j<((ViewGroup)view).getChildCount(); j++ ) {
                    View view2 = ((ViewGroup)view).getChildAt(j);
                }
            }
        }

        v.setBackgroundColor(0xffffffff);

//        AppCompatTextView title = (AppCompatTextView)v.findViewById(android.R.id.title);
//        title.setTextColor(0xff333333);
//
//        AppCompatTextView summary = (AppCompatTextView)v.findViewById(android.R.id.summary);
//        summary.setTextColor(0xff808080);

        return v;
    }
}
