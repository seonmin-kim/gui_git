package kr.co.mystockhero.geotogong;

/**
 * Created by sesang on 16. 5. 23..
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;

import com.facebook.CallbackManager;

import kr.co.mystockhero.geotogong.common.CommonUtil;

public class SuitabilityActivity extends CommonActivity {

    private CallbackManager callbackManager;

    boolean loadingFinished = true;
    boolean redirect = false;
    static Bundle bundle = null;

    @Override
    protected void makeLayout() {
        String name = CommonUtil.getPreferences(SuitabilityActivity.this, "login_name", "");
        String id = CommonUtil.getPreferences(SuitabilityActivity.this, "login_id", "");
        String domain = CommonUtil.getPreferences(SuitabilityActivity.this, "login_domain", "");
        String password = CommonUtil.getPreferences(SuitabilityActivity.this, "login_password", "");

        topLayout = rootLayout.makeTop("투자적합성평가");
        bodyLayout = rootLayout.makeBody();

        bodyLayout.setBackgroundColor(0xffe5e5e5);

        WebView web = new WebView(getApplicationContext());

        web.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setDomStorageEnabled(true);
        //web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //web.getSettings().setLoadWithOverviewMode(true);
        //web.getSettings().setUseWideViewPort(true);
        web.getSettings().setBuiltInZoomControls(false);
        web.getSettings().setSupportZoom(false);


        bodyLayout.addView(web);

        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result)
            {
                new AlertDialog.Builder(SuitabilityActivity.this)
//                        .setTitle("체크 박스 확인")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            };
        });


        int realId = application.userRealId;
        String url = "http://geotogong.mystockhero.com/zero_suitability_survey.php?id=" + realId;
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String urlNewString) {
                webView.loadUrl(urlNewString);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if( !redirect ) {
                    loadingFinished = true;
                }
                if( loadingFinished && !redirect ) {

                } else {
                    redirect = false;
                }

                String Survey_Url;

                if(url.contains("http://geotogong.mystockhero.com/zero_suitability_survey_complete.php")){
                    // URL 중 ? 부터 시작하는 쿼리스트링 반환
                    Survey_Url = url.substring(url.indexOf('?') +1 , url.length());

                    Intent intent = new Intent(SuitabilityActivity.this, MainTabActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if(url.contains("http://geotogong.mystockhero.com/zero_suitability_exit.php")) {
                    onBackPressed();
                }
            }
        });
        web.loadUrl(url);

        bodyLayout.setFocusable(true);
        bodyLayout.setFocusableInTouchMode(true);

    }
}

