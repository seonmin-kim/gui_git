package kr.co.mystockhero.geotogong;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.common.layout.TitleViewGroup;
import kr.co.mystockhero.geotogong.common.widget.ImageView;
import kr.co.mystockhero.geotogong.common.widget.TextView;

/**
 * Created by Lee,Yongsuk on 16. 9. 22..
 */

/**
 * 거장의 생애,거장의 전량(+Formula) 팝업에 사용된다.
 */
public class CommonPopup extends Dialog {
    private Context context;
    private String title;
    private String content;
    private int wartermark = 0;

    public CommonPopup(Context context, String title, String content) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.context = context;
        this.title = title;
        this.content = content;
    }

    public CommonPopup(Context context, String title, String content, int wartermark) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.context = context;
        this.title = title;
        this.content = content;
        this.wartermark = wartermark;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);

        android.widget.RelativeLayout contentView = new RelativeLayout(context);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // android.widget.ScrollView contentView = new ScrollView(context);
        // contentView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(contentView);

        TitleViewGroup rootLayout = new TitleViewGroup(context,
                RelativeLayout.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.FILL_VERTICAL, 20, 42, 20, 40, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.addView(rootLayout);

        rootLayout.makeTop(title, Color.TRANSPARENT, 0xffff773e, -1, R.drawable.close_01, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((TextView)rootLayout.findViewWithTag("top_title")).setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);

        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
            rootLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shadow_round_border));
        } else {
            rootLayout.setBackground(context.getResources().getDrawable(R.drawable.shadow_round_border));
        }

        // 변경 후(2016.09.22)
        RelativeLayout bodyLayout = rootLayout.makeBody();
        ScrollView bodySv = new ScrollView(this.context);
        RelativeLayout.LayoutParams svparams = (RelativeLayout.LayoutParams) RelativeLayout.createScaledLayoutParams(Gravity.FILL, 30, 50, 30, 50, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //svparams.setMargins(30,50,30,30);
        bodySv.setLayoutParams(svparams);
        bodySv.setBackgroundColor(Color.WHITE);

        bodyLayout.addView(bodySv);

        if ( wartermark > 0 ) {
            ImageView imageView = ImageView.createImageView(null, context, bodyLayout,
                    RelativeLayout.createLayoutParams(Gravity.CENTER, 0, 0, 0, 0, ControlUtil.getImageSize(getContext(), wartermark)),
                    wartermark);
            imageView.getDrawable().setAlpha(50);
            imageView.setAdjustViewBounds(true);
        }

        TextView bodyTv = new TextView(context);
        bodyTv.setLayoutParams(svparams);
        bodyTv.setId(ControlUtil.generateViewId());
        bodyTv.setTag("content");
        bodyTv.setTextColor(0xff4c4c4c);
        bodyTv.setGravity(Gravity.LEFT|Gravity.TOP);
        bodyTv.setLineSpacing(ControlUtil.convertHeight(true, 15), 1);
        bodyTv.setBackgroundColor(Color.TRANSPARENT);
        bodyTv.setIncludeFontPadding(false);
        bodyTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ControlUtil.convertHeight(true, 24));

        Typeface bodyTvFont = FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular);
        boolean bodyTvBold = true;
        if ( bodyTvFont != null ) {
            bodyTv.setTypeface(bodyTvFont);
        } else if ( ControlUtil.typeface != null ) {
            bodyTv.setTypeface(ControlUtil.typeface);
            if ( bodyTvBold ) {
                bodyTv.setPaintFlags(bodyTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
            }
        } else {
            if ( bodyTvBold ) {
                bodyTv.setPaintFlags(bodyTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
            }
        }
        bodyTv.setText(content+"\n\n\n");
        bodySv.addView(bodyTv);

        /* 변경전
        RelativeLayout bodyLayout = rootLayout.makeBody();

        if ( wartermark > 0 ) {
            ImageView imageView = ImageView.createImageView(null, context, bodyLayout,
                    bodyLayout.createLayoutParams(Gravity.CENTER, 0, 0, 0, 0, ControlUtil.getImageSize(getContext(), wartermark)),
                    wartermark);
            imageView.getDrawable().setAlpha(50);
        }

        TextView.createTextView("content", context, bodyLayout,
                bodyLayout.createScaledLayoutParams(Gravity.FILL, 30, 50, 30, 30, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                content, Gravity.LEFT|Gravity.TOP, -1, 24, 15, true, 0xff4c4c4c,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        */

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }
}
