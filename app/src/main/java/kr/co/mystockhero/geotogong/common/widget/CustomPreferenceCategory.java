package kr.co.mystockhero.geotogong.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.common.ControlUtil;

/**
 * Created by sesang on 16. 6. 4..
 */
public class CustomPreferenceCategory extends android.preference.PreferenceCategory{

//    @SuppressLint("NewApi")
    @SuppressLint("NewApi")
    public CustomPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomPreferenceCategory(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.preferenceCategoryStyle);
    }

    public CustomPreferenceCategory(Context context) {
        this(context, null);
    }

    @Override
    public View getView(View convertView, ViewGroup parent) {

        View v = super.getView(convertView, parent);

        v.setBackgroundColor(0xffe6e6e6);
        v.setPadding(v.getPaddingLeft(), ControlUtil.convertHeight(true, 10), v.getPaddingRight(), ControlUtil.convertHeight(true, 5));

//        TextView textView = (TextView) v.findViewById(android.R.id.title);
//        textView.setTextColor(0xff333333);
//        textView.setTypeface(FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold));
        return v;
    }
}
