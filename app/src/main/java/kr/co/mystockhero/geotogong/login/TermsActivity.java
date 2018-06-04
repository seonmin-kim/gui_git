package kr.co.mystockhero.geotogong.login;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ScrollView;

import org.json.JSONObject;

import java.net.URLEncoder;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.layout.LinearLayout;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.common.network.AsyncNetworkTask;
import kr.co.mystockhero.geotogong.common.widget.CheckBox;
import kr.co.mystockhero.geotogong.common.widget.Layout;
import kr.co.mystockhero.geotogong.common.widget.TextView;
import kr.co.mystockhero.geotogong.CommonActivity;
import kr.co.mystockhero.geotogong.R;

/**
 * Created by sesang on 16. 5. 23..
 */
public class TermsActivity extends CommonActivity {

    @Override
    protected void makeLayout() {

        topLayout = rootLayout.makeTop("전문보기", 0xffffffff, 0xff252525, -1, R.drawable.close_01, this);
        bodyLayout = rootLayout.makeBody();

        bodyLayout.setBackgroundColor(0xffffffff);

        ScrollView scrollView = Layout.createScrollView(null, this, bodyLayout,
                RelativeLayout.createLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout layout = LinearLayout.createLayout(null, this, scrollView,
                LinearLayout.createScaledLayoutParams(Gravity.FILL, 20, 20, 20, 40, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.VERTICAL, 0xffe5e5e5);


        // makeTerms("약관", layout);
        // makeTerms("개인정보", layout);
        // makeTerms("면책", layout);

        Intent intent = getIntent();
        String type=   intent.getStringExtra("category");
        if (type.equals("terms_tendency")) {
            makeTerms("성향", layout);
        }else if (type.equals("terms_service")) {
            makeTerms("약관", layout);
        }else if(type.equals("terms_private")){
            makeTerms("개인정보", layout);
        }else if(type.equals("terms_exemption")){
            makeTerms("면책", layout);
        }else if(type.equals("terms_suggestion")) {
            makeSuggestion("투자 권유 문서", layout);
        }
    }

    private void makeTerms(String category, LinearLayout layout) {

        int padding = ControlUtil.convertScale(20);

        TextView subject = TextView.createTextView(null, this, layout,
                LinearLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, padding, padding, padding, padding, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "", Gravity.LEFT, -1, 32, 0, true, 0xff252525,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);

        TextView content = TextView.createTextView(null, this, layout,
                LinearLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL, padding, padding, padding, padding, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "", Gravity.LEFT, -1, 26, 10, true, 0xff333333,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        content.setBackgroundColor(0xffffffff);
        content.setPadding(padding, padding, padding, padding);

        subject.setVisibility(View.INVISIBLE);
        content.setVisibility(View.INVISIBLE);

        queryTerms(category, subject, content);
    }

    private void queryTerms(String category, final TextView subject, final TextView content) {

        JSONObject params = new JSONObject();
        try {
            params.put("category", category);
        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/yslee_get_policy.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {

            @Override
            public void onError(int errorCode, String message) {

                subject.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
            }

            @Override
            public void onRespons(Object result) throws Exception {

                JSONObject data = (JSONObject) result;
                JSONObject policy = data.getJSONObject("policy");
                subject.setText(policy.getString("subject"));
                content.setText(policy.getString("content"));

                subject.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
            }
        });
    }

    private void makeSuggestion(String category, LinearLayout layout) {

        int padding = ControlUtil.convertScale(20);

        layout.setBackgroundColor(Color.WHITE);
        RelativeLayout wrapper = RelativeLayout.createLayout(null, getApplicationContext(), layout,
                layout.createLayoutParams(Gravity.FILL, padding, padding, padding, padding, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        WebView webView = new WebView(getApplicationContext());
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //webView.setWebViewClient(new WebViewClient());
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUseWideViewPort(true);
        //String url = "http://geotogong.mystockhero.com/PCert_Step01_bak.php";
        String url = "http://geotogong.mystockhero.com/zero_suggest_doc_view.php";
        webView.loadUrl(url);

        wrapper.addView(webView);

        TextView.createTextView(null, this, layout,
                layout.createLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, padding, -20, padding, padding, ViewGroup.LayoutParams.MATCH_PARENT, 50),
                "* 로그인 후 '권유문서' 재확인하실 수 있습니다.", Gravity.LEFT|Gravity.TOP, -1, 20, 0, true, 0xff252525,  FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        //LinearLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, padding, -20, padding, padding, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
        //"* 로그인 후 '권유문서' 재확인하실 수 있습니다.", Gravity.LEFT|Gravity.TOP, -1, 20, 0, true, 0xff252525,  FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
    }

    @Override
    public void onClickTopRightButton() {
        finish();
    }
}
