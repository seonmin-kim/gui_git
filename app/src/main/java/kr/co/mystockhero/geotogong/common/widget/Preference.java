package kr.co.mystockhero.geotogong.common.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.R;
import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;

public class Preference extends android.preference.Preference {

    public Preference(Context context) {
        super(context);
    }

    public Preference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Preference(Context context, AttributeSet attrs, int defStyle) {
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

        return v;
    }
}