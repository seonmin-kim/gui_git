package kr.co.mystockhero.geotogong.common.layout;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.R;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.widget.Button;
import kr.co.mystockhero.geotogong.common.widget.ImageView;
import kr.co.mystockhero.geotogong.common.widget.TextView;
import kr.co.mystockhero.geotogong.common.widget.ToggleButton;

/**
 * Created by sesang on 16. 5. 23..
 */
public class TitleViewGroup extends RelativeLayout {

    protected final int TITLE_HEIGHT = 129 - 40;
    protected int topHeight = 0;
    protected int bottomHeight = 0;

    protected RelativeLayout topLayout;
    protected RelativeLayout qnaLayout;
    protected RelativeLayout bodyLayout;
    protected RelativeLayout tabLayout = null;
    protected Context context;

    public TitleViewGroup(Context context) {

        super(context);

        this.context = context;

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setId(ControlUtil.generateViewId());

        setClipChildren(false);
        setClipToPadding(false);
    }

    public TitleViewGroup(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public TitleViewGroup(Context context, ViewGroup.LayoutParams layoutParams) {

        super(context);

        this.context = context;

        setLayoutParams(layoutParams);
        setId(ControlUtil.generateViewId());

        setClipChildren(false);
        setClipToPadding(false);
    }


    public RelativeLayout makeTop(String title) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                0xfff27130);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, R.color.fragment_top_line);

        TextView.createTextView("top_title", context, topLayout,
                createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 28, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                title, Gravity.CENTER, 1, 40, 0, true, 0xffffffff,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), true);

        return topLayout;
    }

    public RelativeLayout makeTop(int color, int titleId, int leftId, int rightId, OnClickListener onClickListener) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                color);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, ControlUtil.getColor(context, R.color.fragment_top_line));

        ImageView imageView = ImageView.createImageView(null, context, topLayout,
                createScaledLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 28, ControlUtil.getImageSize(context, titleId)),
                titleId);

        if ( leftId > 0 ) {
            Point size = ControlUtil.getImageSize(context, leftId);
            Button.createButton("top_left_btn", context, topLayout, createScaledLayoutParams(Gravity.LEFT | Gravity.CENTER_VERTICAL, (64 - size.x) / 2, 0, 0, 0, size),
                    leftId, onClickListener);
        }

        if ( rightId > 0 ) {
            Point size = ControlUtil.getImageSize(context, rightId);
            if(rightId == R.drawable.close_01) { size = new Point(30,30); }
            Button.createButton("top_right_btn", context, topLayout, createScaledLayoutParams(Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0, (64 - size.x) / 2 + 4, 0, size),
                    rightId, onClickListener);

            /*
            Point area_size = new Point(50, 50);
            RelativeLayout top_right_btn_area = RelativeLayout.createLayout(null, context, topLayout,
                    topLayout.createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0, 0, (64 - size.x) / 2 - 1, 0, area_size));
            top_right_btn_area.setOnClickListener(onClickListener);
            */

        }
        return topLayout;
    }

    public RelativeLayout makeHomeTop(int color, int titleId, int leftId, int rightId, OnClickListener onLeftClickListener, OnClickListener onRightClickListener) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                color);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, ControlUtil.getColor(context, R.color.fragment_top_line));

        ImageView imageView = ImageView.createImageView(null, context, topLayout,
                createScaledLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 28, ControlUtil.getImageSize(context, titleId)),
                titleId);

        if ( leftId > 0 ) {
            Point size =  new Point(60,40);
            Button.createButton("top_left_btn", context, topLayout, createScaledLayoutParams(Gravity.LEFT | Gravity.CENTER_VERTICAL, 25, 0, 0, 0, size),
                    leftId, onLeftClickListener);
        }

        if ( rightId > 0 ) {
            Point size =  new Point(44,40);
            Button.createButton("top_right_btn", context, topLayout, createScaledLayoutParams(Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0, 25, 0, size),
                    rightId, onRightClickListener);

            /*
            Point area_size = new Point(50, 50);
            RelativeLayout top_right_btn_area = RelativeLayout.createLayout(null, context, topLayout,
                    topLayout.createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0, 0, (64 - size.x) / 2 - 1, 0, area_size));
            top_right_btn_area.setOnClickListener(onClickListener);
            */

        }
        return topLayout;
    }

    public RelativeLayout makeTop(String title, int bgColor, int textColor, int leftId, int rightId, OnClickListener onClickListener) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                bgColor);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, ControlUtil.getColor(context, R.color.fragment_top_line));

        TextView.createTextView("top_title", context, topLayout,
                createScaledLayoutParams(Gravity.FILL, 30, 0, 30, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                title, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 1, 33, 0, true, textColor,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), true);

        int addX = 0;
        if ( leftId > 0 ) {
            Point size = ControlUtil.getImageSize(context, leftId);
            if(leftId == R.drawable.back_01) { size = new Point(25,44); }
            if(  leftId == R.drawable.back_black ) { size = new Point(27,44); }
            if( leftId == R.drawable.question_cancel ) { size = new Point(60,45); addX = 18;}


            LinearLayout top_left_btn_area = LinearLayout.createLayout("top_left_btn", context, topLayout,
                    createScaledLayoutParams(Gravity.LEFT|Gravity.CENTER_VERTICAL, 0,0,0,0, 88, ViewGroup.LayoutParams.MATCH_PARENT));
            top_left_btn_area.setOnClickListener(onClickListener);

            Button.createButton("top_left_btn", context, topLayout, createScaledLayoutParams(Gravity.LEFT | Gravity.CENTER_VERTICAL, (64 - size.x) / 2 + addX, 0, 0, 0, size),
                    leftId, onClickListener);
        }

        if ( rightId > 0 ) {
            Point size = ControlUtil.getImageSize(context, rightId);
            if(rightId == R.drawable.close_01) { size = new Point(30,30); }
            if( rightId == R.drawable.question_regist ) { size = new Point(60,45); addX = 18; }
            if( rightId == R.drawable.starcolumn_writers ) { size = new Point(60,45); addX = 18; }
            if( rightId == R.drawable.close_02 ) { size = new Point(30,30);  }
            if( rightId == R.drawable.realreportdetail_interest_on || rightId == R.drawable.realreportdetail_interest_off) { size = new Point(40,40);  }
            if( rightId ==  R.drawable.mypage_icon){ size = new Point(37,37);}
            if( rightId == R.drawable.exit_button ) { size = new Point(60,50); addX = 18;}

            LinearLayout top_right_btn_area = LinearLayout.createLayout("top_right_btn_area", context, topLayout,
                    createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0,0,0,0, 88, ViewGroup.LayoutParams.MATCH_PARENT));
            top_right_btn_area.setOnClickListener(onClickListener);

            Button.createButton("top_right_btn", context, topLayout, createScaledLayoutParams(Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0, (64 - size.x) / 2 + 4 + addX, 0, size),
                    rightId, onClickListener);
        }

        return topLayout;

    }

    public RelativeLayout makeMasterDetailTop(String title, int bgColor, int textColor, int leftId, int rightId, OnClickListener onClickListener) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                bgColor);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, ControlUtil.getColor(context, R.color.fragment_top_line));

        TextView.createTextView("top_title", context, topLayout,
                createScaledLayoutParams(Gravity.FILL, 30, 0, 30, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                title, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 1, 33, 0, true, textColor,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), true);

        int addX = 0;
        if ( leftId > 0 ) {
            Point size = ControlUtil.getImageSize(context, leftId);
            if(leftId == R.drawable.back_01) { size = new Point(25,44); }
            if(  leftId == R.drawable.back_black ) { size = new Point(27,44); }
            if( leftId == R.drawable.question_cancel ) { size = new Point(60,45); addX = 18;}

            LinearLayout top_left_btn_area = LinearLayout.createLayout("top_left_btn", context, topLayout,
                    createScaledLayoutParams(Gravity.LEFT|Gravity.CENTER_VERTICAL, 0,0,0,0, 88, ViewGroup.LayoutParams.MATCH_PARENT));
            top_left_btn_area.setOnClickListener(onClickListener);

            Button.createButton("top_left_btn", context, topLayout, createScaledLayoutParams(Gravity.LEFT | Gravity.CENTER_VERTICAL, (64 - size.x) / 2 + addX, 0, 0, 0, size),
                    leftId, onClickListener);
        }

        if ( rightId > 0 ) {
            Point size = new Point(80, 45);

            LinearLayout top_right_btn_area = LinearLayout.createLayout("top_right_btn_area", context, topLayout,
                    createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0,0,0,0, 100, ViewGroup.LayoutParams.MATCH_PARENT), android.widget.LinearLayout.HORIZONTAL, Color.BLACK);
            top_right_btn_area.setOnClickListener(onClickListener);

            Button.createButton("top_right_btn", context, topLayout, createScaledLayoutParams(Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0, 20, 0, size),
                    rightId, onClickListener);
        }

        return topLayout;

    }

    public RelativeLayout makestartTop(String title, int bgColor, int textColor, int leftId, int rightId, OnClickListener onLeftClickListener, OnClickListener onRightClickListener) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                bgColor);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, ControlUtil.getColor(context, R.color.fragment_top_line));

        TextView.createTextView("top_title", context, topLayout,
                createScaledLayoutParams(Gravity.FILL, 30, 0, 30, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                title, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 1, 33, 0, true, textColor,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), true);

        int addX = 0;
        if ( leftId > 0 ) {
            Point size = ControlUtil.getImageSize(context, leftId);

            if(leftId == R.drawable.back_01) { size = new Point(25,44); }

            LinearLayout top_left_btn_area = LinearLayout.createLayout("top_left_btn", context, topLayout,
                    createScaledLayoutParams(Gravity.LEFT|Gravity.CENTER_VERTICAL, 0,0,0,0, 88, ViewGroup.LayoutParams.MATCH_PARENT));
            top_left_btn_area.setOnClickListener(onLeftClickListener);

            Button.createButton("top_left_btn", context, topLayout, createScaledLayoutParams(Gravity.LEFT | Gravity.CENTER_VERTICAL, (64 - size.x) / 2 + addX, 0, 0, 0, size),
                    leftId, onLeftClickListener);
        }

        if ( rightId > 0 ) {
            Point size = ControlUtil.getImageSize(context, rightId);
            if( rightId == R.drawable.starcolumn_writers ) { size = new Point(74,47); addX = 25; }

            LinearLayout top_right_btn_area = LinearLayout.createLayout("top_right_btn_area", context, topLayout,
                    createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0,0,0,0, 88, ViewGroup.LayoutParams.MATCH_PARENT));
            top_right_btn_area.setOnClickListener(onRightClickListener);

            Button.createButton("top_right_btn", context, topLayout, createScaledLayoutParams(Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0, (64 - size.x) / 2 + 4 + addX, 0, size),
                    rightId, onRightClickListener);
        }

        return topLayout;

    }

    public RelativeLayout makeMainHomeTop(int color, int titleId, int leftId, int rightId, OnClickListener onLeftClickListener, OnClickListener onRightClickListener) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                color);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, ControlUtil.getColor(context, R.color.fragment_top_line));
        Point size1 =  new Point(310,57);
        ImageView imageView = ImageView.createImageView(null, context, topLayout,
                createScaledLayoutParams(Gravity.CENTER, 0, 0, 0, 0, size1),
                titleId);
        imageView.setAdjustViewBounds(true);

        if ( leftId > 0 ) {
            Point size =  new Point(44,40);
            Button.createButton("top_left_btn", context, topLayout, createScaledLayoutParams(Gravity.LEFT | Gravity.CENTER_VERTICAL, 25, 0, 0, 0, size),
                    leftId, onLeftClickListener);
        }

        if ( rightId > 0 ) {
            Point size =  new Point(44,50);
            Button.createButton("top_right_btn", context, topLayout, createScaledLayoutParams(Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0, 25, 0, size),
                    rightId, onRightClickListener);

            /*
            Point area_size = new Point(50, 50);
            RelativeLayout top_right_btn_area = RelativeLayout.createLayout(null, context, topLayout,
                    topLayout.createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0, 0, (64 - size.x) / 2 - 1, 0, area_size));
            top_right_btn_area.setOnClickListener(onClickListener);
            */

        }
        return topLayout;
    }

    public RelativeLayout makeQuantReportTop(String title, int bgColor, int textColor, int leftId, int rightId, OnClickListener onClickListener) {

        topHeight = TITLE_HEIGHT;

        topLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, topHeight),
                bgColor);

        LinearLayout.createLayout(null, context, topLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, ControlUtil.getColor(context, R.color.fragment_top_line));

        TextView.createTextView("top_title", context, topLayout,
                createScaledLayoutParams(Gravity.FILL, 30, 0, 30, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                title, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 1, 33, 0, true, textColor,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), true);

        int addX = 0;
        if ( leftId > 0 ) {
            Point size = ControlUtil.getImageSize(context, leftId);
            if(leftId == R.drawable.back_01) { size = new Point(25,44); }
            if(  leftId == R.drawable.back_black ) { size = new Point(27,44); }
            if( leftId == R.drawable.question_cancel ) { size = new Point(60,45); addX = 18;}

            LinearLayout top_left_btn_area = LinearLayout.createLayout("top_left_btn", context, topLayout,
                    createScaledLayoutParams(Gravity.LEFT|Gravity.CENTER_VERTICAL, 0,0,0,0, 88, ViewGroup.LayoutParams.MATCH_PARENT));
            top_left_btn_area.setOnClickListener(onClickListener);

            Button.createButton("top_left_btn", context, topLayout, createScaledLayoutParams(Gravity.LEFT | Gravity.CENTER_VERTICAL, (64 - size.x) / 2 + addX, 0, 0, 0, size),
                    leftId, onClickListener);
        }

        Point size = ControlUtil.getImageSize(context, rightId);
        size = new Point(60,45);

        LinearLayout top_right_btn_area = LinearLayout.createLayout("top_right_btn_area", context, topLayout, createScaledLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL, 0,0,0,0, 88, ViewGroup.LayoutParams.MATCH_PARENT));

        top_right_btn_area.setOnClickListener(onClickListener);

        Button.createButton("top_right_btn", context, topLayout, createScaledLayoutParams(Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0, (64 - size.x) / 2 + 4 + addX, 0, size), rightId, onClickListener);

        return topLayout;

    }

    public RelativeLayout makeBottom(int tabCount, OnClickListener onClickListener) {

        int w = ControlUtil.bWidth / tabCount;
        int h = 99;
        bottomHeight = h + 2;

        tabLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, bottomHeight),
                0xffffffff);

        LinearLayout.createLayout(null, context, tabLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                android.widget.LinearLayout.HORIZONTAL, 0xffe6e6e6);

        LinearLayout layout = LinearLayout.createLayout(null, context, tabLayout,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 2, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, h),
                android.widget.LinearLayout.HORIZONTAL, 0xffffffff);

        // CommonUtil.DebugLog("makeBottom : " + w + ", " + h + ", " + tabLayout.getHeight());
        for( int i=0; i<tabCount; i++ ) {
            int resId = getResources().getIdentifier("navi_off_0" + (i + 1), "drawable", context.getPackageName());
            int selectedId = getResources().getIdentifier("navi_on_0" + (i + 1), "drawable", context.getPackageName());
            w = 107;
            if(i == 0 || i == 1){
                w = 106;
            }
            ToggleButton.createToggleButton("tab_" + i, context, layout,
                    LinearLayout.createLayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT),
                    resId, selectedId, resId, onClickListener);
        }
        return tabLayout;
    }

    public RelativeLayout makeBody() {

        bodyLayout = RelativeLayout.createLayout(null, context, this,
                createLayoutParams(Gravity.FILL, 0, topHeight - 2, 0, bottomHeight - 1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                        false, true, false, true, false, false));

        return bodyLayout;
    }

    public RelativeLayout makeBody(int color) {

        bodyLayout = RelativeLayout.createLayout(null, context, this,
                createLayoutParams(Gravity.FILL, 0, topHeight - 2, 0, bottomHeight - 1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                        false, true, false, true, false, false), color);

        return bodyLayout;
    }

    public RelativeLayout qnabottomBody(int rsid) {

        qnaLayout = RelativeLayout.createLayout(null, context, this,
                createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 90, ViewGroup.LayoutParams.MATCH_PARENT, 92),
                0xffffffff);

        qnaLayout.setBackgroundResource(rsid);

        return qnaLayout;
    }
}
