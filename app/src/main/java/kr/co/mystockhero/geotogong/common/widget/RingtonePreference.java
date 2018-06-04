package kr.co.mystockhero.geotogong.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.R;

/**
 * Created by sesang on 16. 6. 4..
 */
public class RingtonePreference extends android.preference.RingtonePreference {


    @SuppressLint("NewApi")
    public RingtonePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RingtonePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RingtonePreference(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.ringtonePreferenceStyle);
    }

    public RingtonePreference(Context context) {
        this(context, null);
    }

    @Override
    public View getView(View convertView, ViewGroup parent) {

        View v = super.getView(convertView, parent);

        v.setBackgroundColor(0xffffffff);

//        AppCompatTextView title = (AppCompatTextView)v.findViewById(android.R.id.title);
//        title.setTextColor(0xff333333);
//
//        AppCompatTextView summary = (AppCompatTextView)v.findViewById(android.R.id.summary);
//        summary.setTextColor(0xff808080);

        ImageView.createImageView(null, getContext(), (ViewGroup)v,
                RelativeLayout.createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0, 0, 32, 0, ControlUtil.getImageSize(getContext(), R.drawable.next_01)),
                R.drawable.next_01);
        return v;
    }
}
