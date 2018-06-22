package kr.co.mystockhero.geotogong;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ScrollView;

import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.PDFTools;
import kr.co.mystockhero.geotogong.common.layout.LinearLayout;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.common.layout.TitleViewGroup;
import kr.co.mystockhero.geotogong.common.widget.ImageView;
import kr.co.mystockhero.geotogong.common.widget.TextView;

/**
 * Created by Administrator on 2017-02-15.
 */
public class SuggestInvestmentPopup extends Dialog {
    private Context context;
    private String title;
    private String content;
    private int wartermark = 0;

    public SuggestInvestmentPopup(Context context, String title, String content) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.context = context;
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);

        final android.widget.RelativeLayout contentView = new RelativeLayout(context);
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

        int tabLayoutHeight = 130;
        RelativeLayout tabLayout = RelativeLayout.createLayout(null, context, rootLayout,
                RelativeLayout.createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, tabLayoutHeight), 0xffe6e6e6);
        LinearLayout bottomLayout = LinearLayout.createLayout(null, context, tabLayout,
                RelativeLayout.createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, tabLayoutHeight), android.widget.LinearLayout.VERTICAL, 0xffffffff);

        ImageView.createImageView(null, getContext(), tabLayout,
                RelativeLayout.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, 560, 2), R.drawable.line_download);

        ImageView btn_download = ImageView.createImageView(null, getContext(), tabLayout,
                RelativeLayout.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 20, 0, 0, 560, 89), R.drawable.bt_download);
        btn_download.setAdjustViewBounds(true);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PDFTools.downloadAndOpenPDF(context, "http://geotogong.mystockhero.com/reposit_pdf/ABCM_투자자문계약권유문서.pdf");
            }
        });

        // 변경 후(2016.09.22)
        RelativeLayout bodyLayout = rootLayout.makeBody();
        ScrollView bodySv = new ScrollView(this.context);
        RelativeLayout.LayoutParams svparams = (RelativeLayout.LayoutParams) RelativeLayout.createScaledLayoutParams(Gravity.FILL, 30, 50, 30, 150, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

        WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //webView.setWebViewClient(new WebViewClient());
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUseWideViewPort(true);
        //String url = "http://geotogong.mystockhero.com/PCert_Step01_bak.php";
        String url = content;
        webView.loadUrl(url);

        bodySv.addView(webView);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }
}

