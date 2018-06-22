package kr.co.mystockhero.geotogong.login;

/**
 * Created by sesang on 16. 5. 23..
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.mystockhero.geotogong.CommonActivity;
import kr.co.mystockhero.geotogong.MainTabActivity;
import kr.co.mystockhero.geotogong.R;
import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.layout.LinearLayout;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.common.network.AsyncNetworkTask;
import kr.co.mystockhero.geotogong.common.widget.Button;
import kr.co.mystockhero.geotogong.common.widget.CheckBox;
import kr.co.mystockhero.geotogong.common.widget.EditText;
import kr.co.mystockhero.geotogong.common.widget.ImageView;
import kr.co.mystockhero.geotogong.common.widget.TextView;

import static kr.co.mystockhero.geotogong.common.widget.ImageView.createImageView;

public class LoginActivity extends CommonActivity {

    private CallbackManager callbackManager;

    @Override
    protected void makeLayout() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        String name = CommonUtil.getPreferences(LoginActivity.this, "login_name", "");
        String id = CommonUtil.getPreferences(LoginActivity.this, "login_id", "");
        String domain = CommonUtil.getPreferences(LoginActivity.this, "login_domain", "");
        String password = CommonUtil.getPreferences(LoginActivity.this, "login_password", "");
        makeLoginLayout(id, domain, "");

        // checkAvailableConnection();

        // getIPAddress();
    }

    public void getIPAddress() {
        Socket socket = null;
        try {
            socket = new Socket("www.google.com", 80);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String localAddr = socket.getLocalAddress().getHostAddress();
        // CommonUtil.DebugLog("IPAddress: " + localAddr);
    }

    public void checkAvailableConnection() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        // CommonUtil.DebugLog("IP Address: " + inetAddress.getHostAddress().toString());
                    }
                }
            }
        } catch (Exception ex) {
            // CommonUtil.DebugLog("IP Address", ex.toString());
        }
    }
    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "--";
    }
    public String getWifiIPAddress() {
        // 20170526 수정
        // WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return  Formatter.formatIpAddress(ip);
    }

    private void makeLoginLayout(String id, String domain, String password) {

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        bodyLayout = rootLayout.makeBody();

        bodyLayout.setBackgroundColor(0xffffffff);
        bodyLayout.setClickable(true);
        bodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    ControlUtil.hideKeyboard(LoginActivity.this, view);
                }
            }
        });

        RelativeLayout layout = RelativeLayout.createLayout(null, this, bodyLayout,
                bodyLayout.createScaledHeightLayoutParams(Gravity.FILL, 45, 147, 45, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout layout_body = LinearLayout.createLayout(null, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.FILL_VERTICAL, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.VERTICAL, 0xffffffff);


        boolean check_id = true;
        if( id != null && !id.equals("")) {
            for(char c : id.toCharArray()) {
                if(!Character.isDigit(c)) { check_id = false; }
            }
        }
        if( check_id ) { id = ""; domain = ""; password = ""; }


        TextView.createTextView(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "로그인", Gravity.CENTER_HORIZONTAL, 1, 42, 0, true, 0xff333333,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        Button KakaoLoginButton = Button.createButton(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 90, 0, 0, 550, 78), R.drawable.kakao_button,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), KakaoLoginActivity.class);
//                        intent.putExtra("auto_others_login", ((CheckBox) bodyLayout.findViewWithTag("auto_others_login")).isChecked() ? "true" : "false");
                        startActivity(intent);
                        finish();
                    }
                });

        if(getIntent() != null) {
            Intent corporation_intent = getIntent();
            if (corporation_intent.getExtras() != null) {
                if(corporation_intent.getExtras().getString("corporation_type") != null) {
                    String corporation = corporation_intent.getExtras().getString("corporation_type");

                    CommonUtil.DebugLog("corporation : " + corporation);

                    if (corporation.equals("카카오톡")) {
                        excuteLoginOthers(corporation_intent.getExtras().getString("kakao_nickname"), corporation_intent.getExtras().getString("kakao_id"), corporation);
                    }

                    if( corporation.equals("Google") ) {
                        excuteLoginOthers(corporation_intent.getExtras().getString("google_displayname"), corporation_intent.getExtras().getString("google_id"), corporation);
                    }
                }
            }
        }


        Button GoogleLoginButton = Button.createButton(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 15, 0, 0, 550, 78), R.drawable.google_button,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login_Google();
                    }
                });



        Button NaverLoginButton =  Button.createButton(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 15, 0, 0, 550, 78), R.drawable.naver_button,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login_NAVER();
                    }
                });

        Button FacebookLoginButton = Button.createButton("facebookLoginBtn", this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 15, 0, 0, 550, 78), R.drawable.face_button,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FacebookSdk.sdkInitialize(getApplicationContext());
                        if( AccessToken.getCurrentAccessToken() != null ) {
                            LoginManager.getInstance().logOut();
                        } else {
                        }
                        callbackManager = CallbackManager.Factory.create();  //로그인 응답을 처리할 콜백 관리자

                        //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
                        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                                Arrays.asList("public_profile", "user_friends"));
                        LoginManager.getInstance().registerCallback(callbackManager,
                                new FacebookCallback<LoginResult>() {
                                    @Override
                                    public void onSuccess(final LoginResult loginResult) {

                                        GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                                                new GraphRequest.GraphJSONObjectCallback() {
                                                    @Override
                                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                                        try {
                                                            // CommonUtil.DebugLog("user profile: " + object.toString());
                                                            excuteLoginOthers(object.getString("name"), loginResult.getAccessToken().getUserId(), "FaceBook");
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                        request.executeAsync();
                                    }

                                    @Override
                                    public void onCancel() {
                                        Log.e("onCancel", "onCancel");
                                    }

                                    @Override
                                    public void onError(FacebookException exception) {
                                        Log.e("onError", "onError " + exception.getLocalizedMessage());
                                    }
                                });

                    }
                });

        createImageView(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 48, 0, 0, 550, 23), R.drawable.login_join);



        LinearLayout layout_email = LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.FILL_HORIZONTAL, 0, 60, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.HORIZONTAL, 0xffffffff);


        EditText editEmail = EditText.createEditText("join_id", this, layout_email,
                layout_email.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, 230, 60),
                "이메일", id, Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        editEmail.setPadding(ControlUtil.convertWidth(20), 0, ControlUtil.convertWidth(20), 0);

        createImageView(null, this, layout_email,
                layout_email.createLayoutParams(Gravity.LEFT|Gravity.TOP, 33, 30, 0, 0, 25, 25), R.drawable.email);

        EditText editDomain = EditText.createEditText("join_domain", this, layout_email,
                layout_email.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, 230, 60),
                "직접입력", domain, Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        editDomain.setPadding(ControlUtil.convertWidth(30), 0, ControlUtil.convertWidth(30), 0);

        Button.createButton("btn_domain_popup", this, layout_email,
                layout_email.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 20, 0, 0, 28,28),
                R.drawable.scroll, showEmailDomainPopupOnClickLIstener());

        LinearLayout layout_email_line = LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.FILL_HORIZONTAL, 0, 10, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.HORIZONTAL, 0xffffffff);

        LinearLayout.createLayout(null, this, layout_email_line,
                layout_email_line.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 0, 0, 0, 0, 250, 2),
                LinearLayout.HORIZONTAL, 0xfffe7237);

        LinearLayout.createLayout(null, this, layout_email_line,
                layout_email_line.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 50, 0, 0, 0, 250, 2),
                LinearLayout.HORIZONTAL, 0xfffe7237);

        ImageView go_on = createImageView(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 30, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 78), R.drawable.go_on);

        go_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = null;
                String domain = null;

                if ( (id = checkFieldEmpty("join_id", "이메일을 입력해 주세요.")) == null ) {
                    return;
                }
                if ( (domain = checkFieldEmpty("join_domain", "이메일을 입력해 주세요.")) == null ) {
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, LoginEmailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",id);
                intent.putExtra("domain",domain);
                startActivity(intent);
                finish();
            }
        });


        ImageView textView_signup = createImageView(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 18, 0, 0, 350, 50), R.drawable.join_button);

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ControlUtil.showKeyboard(LoginActivity.this, (EditText)bodyLayout.findViewWithTag("join_id"));
            }
        },100);

        bodyLayout.setFocusable(true);
        bodyLayout.setFocusableInTouchMode(true);
    }

    // Naver 로그인을 위한 변수
    private static String OAUTH_CLIENT_ID = "mrxvdWMzUAvuNABv6xK0";
    private static String OAUTH_CLIENT_SECRET = "DpXu3gq2gX";
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";
    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;
    private static String mOauthAT ="";
    private static String mOauthRT ="";
    private static String mOauthExpires ="";
    private static String mOauthTokenType = "";
    private static String mOAuthState = "";

    public void login_NAVER() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(getApplicationContext(), OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        mContext = getApplicationContext();
        // OAuthLoginDefine.DEVELOPER_VERSION = true;

        mOAuthLoginInstance.logout(mContext);
        mOAuthLoginInstance.startOauthLoginActivity(this, mOAuthLoginHandler);

    }

    private  class NaverUserInfo {
        String naver_email = "";
        String naver_nickname = "";
        String naver_enc_id = "";
        String naver_profile_image = "";
        String naver_age = "";
        String naver_gender = "";
        String naver_id = "";
        String naver_name = "";
        String naver_birthday = "";
    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            // CommonUtil.DebugLog("onPreExecute()");
        }
        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }
        protected void onPostExecute(String content) {
            // CommonUtil.DebugLog("RequestApiTask 결과: " + (String) content);
            NaverUserInfo info = new NaverUserInfo();
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(content));

                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    switch(eventType) {
                        case XmlPullParser.START_TAG:
                            String startTag = parser.getName();
                            // CommonUtil.DebugLog("startTag: " + startTag);
                            if(startTag.equals("email")) { info.naver_email = parser.nextText(); }
                            if(startTag.equals("nickname")) { info.naver_nickname = parser.nextText(); }
                            if(startTag.equals("enc_id")) { info.naver_enc_id = parser.nextText(); }
                            if(startTag.equals("profile_image")) { info.naver_profile_image = parser.nextText(); }
                            if(startTag.equals("age")) { info.naver_age = parser.nextText(); }
                            if(startTag.equals("gender")) { info.naver_gender = parser.nextText(); }
                            if(startTag.equals("id")) { info.naver_id = parser.nextText(); }
                            if(startTag.equals("name")) { info.naver_name = parser.nextText(); }
                            if(startTag.equals("birthday")) { info.naver_birthday = parser.nextText(); }
                            break;
                        case XmlPullParser.END_TAG:
                            String endTag = parser.getName();
                            // CommonUtil.DebugLog("endTag: " + endTag);
                            if(endTag.equals("data")) {
                                excuteLoginOthers(info.naver_name, info.naver_id, "네이버");
                            }
                            break;
                    }

                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                mOauthAT = accessToken;
                mOauthRT = refreshToken;
                mOauthExpires = String.valueOf(expiresAt);
                mOauthTokenType = tokenType;
                mOAuthState = mOAuthLoginInstance.getState(mContext).toString();

                new RequestApiTask().execute();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                // Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                // CommonUtil.DebugLog("errorCode:" + errorCode + ", errorDesc:" + errorDesc);
            }
        };
    };


    public void login_Google() {
        Intent intent = new Intent(getApplicationContext(), GoogleLoginActivity.class);
//        intent.putExtra("auto_others_login", ((CheckBox) bodyLayout.findViewWithTag("auto_others_login")).isChecked() ? "true" : "false");
        startActivity(intent);
        finish();
        //((MainTabActivity)this.getFragmentManager().fragmentAdd(GoogleLoginActivity.class, null, null);
    }

    public void login_FaceBook() {
        Intent intent = new Intent(getApplicationContext(), FaceBookLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickTopLeftButton() {
    }

    @Override
    public void onClickTopRightButton() {

        onBackPressed();
    }

    private View.OnClickListener checkAgreementListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CommonUtil.DebugLog("Clicked CheckBox: " + v.getTag().toString());
            String viewName = v.getTag().toString();
            String auto_login = "auto_login";
            String auto_others_login = "auto_others_login";
            if( ((CheckBox)v).isChecked() ) {
                if (viewName.equals(auto_login)) {
                    ((CheckBox) bodyLayout.findViewWithTag(auto_others_login)).setChecked(false);
                } else if (viewName.equals("auto_others_login")) {
                    ((CheckBox) bodyLayout.findViewWithTag(auto_login)).setChecked(false);
                }
            }
        }
    };

    private View.OnClickListener showTermsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(LoginActivity.this, TermsActivity.class);
            intent.putExtra("category", v.getTag().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    };

    private View.OnClickListener showEmailDomainPopupOnClickLIstener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmailDomainPopup();
            }
        };
    }

    private void showEmailDomainPopup() {

        View anchor = bodyLayout.findViewWithTag("btn_domain_popup");
        int[] position = new int[2];
        anchor.getLocationOnScreen(position);

        int width = ControlUtil.convertScale(300);
        int height = ControlUtil.convertScale(280);
        int x = position[0] - width + anchor.getWidth();
        int y = position[1] + anchor.getHeight();

        RelativeLayout layout = RelativeLayout.createLayout(null, LoginActivity.this, null,
                new ViewGroup.LayoutParams(width, height),
                0xffffffff);

        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
            layout.setBackgroundDrawable(ControlUtil.getBorderDrawable(0xffffffff, R.color.fragment_top_line, 1));
        } else {
            layout.setBackground(ControlUtil.getBorderDrawable(0xffffffff, R.color.fragment_top_line, 1));
        }

        final String[] domains = new String[]{"직접입력", "naver.com", "hanmail.net", "gmail.com"};

        View.OnClickListener onClickDomain = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText)bodyLayout.findViewWithTag("join_domain");
                if ( v.getTag().toString().equals(domains[0]) ) {
                    editText.setText("");
                    editText.setEnabled(true);
                    editText.requestFocus();
                } else {
                    editText.setText(v.getTag().toString());
                    editText.setEnabled(false);
                }
                if ( popup != null ) {
                    popup.dismiss();
                }
            }
        };

        for( int i=0; i<domains.length; i++ ) {

            TextView domain = TextView.createTextView(domains[i], this, layout,
                    layout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 20, i * 70, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 70),
                    domains[i], Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 28, 0, true, 0xff333333,
                    FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);
            domain.setOnClickListener(onClickDomain);
            domain.setClickable(true);

            if ( i < domains.length - 1 ) {
                LinearLayout.createLayout(null, this, layout,
                        layout.createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, i * 70 + 68, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                        android.widget.LinearLayout.HORIZONTAL, ControlUtil.getColor(this, R.color.fragment_top_line));

            }
        }
        showPopup(layout, Gravity.TOP|Gravity.LEFT, x, y, width, height, true);
    }

    private String checkFieldEmpty(String tag, String msg) {

        final View view = bodyLayout.findViewWithTag(tag);
        if ( view == null ) return null;

        String text = ((EditText)view).getText().toString();
        if ( text.length() == 0 ) {
            showInputAlert(((EditText)view), msg);
            return null;
        }
        return text;
    }


    private boolean vaildateEmail(String email) {

        // CommonUtil.DebugLog("vaildateEmail : " + email);

        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {

        //final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$"); // 4자리 ~ 16자리까지 가능
        final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{6,16}$"); // 4자리 ~ 16자리까지 가능

        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(password);
        return matcher.matches();
    }

    private void showInputAlert(final EditText editText, String msg) {
        Dialog dialog = showAlert("", "\n" + msg + "\n", "확인", "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                editText.requestFocus();
                ControlUtil.showKeyboard(LoginActivity.this, editText);
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void excuteLoginOthers(final String name, final String id, final String login_from) {
        if(checkUpdate()) {
            final ProgressDialog login_progressDialog = ProgressDialog.show(LoginActivity.this, "", "로그인 중입니다. 잠시 기다려주세요...", true);

            JSONObject params = new JSONObject();

            try {
                params.put("name", name);
                params.put("id", id + "(" + login_from + ")");
                params.put("login_from", login_from);
                params.put("deviceid", CommonUtil.getPreferences(this, "device_token", ""));

            } catch (Exception e) {
                e.printStackTrace();
            }

            excuteNetworkTask("/zero_checkuser.php", params, false, new AsyncNetworkTask.NetworkTaskListener() {

                @Override
                public void onRespons(Object result) throws Exception {
                    JSONObject data = ((JSONObject) result);

                    application.setUserInfo(data);

                    CommonUtil.setPreferences(LoginActivity.this, "login_id", id);
                    CommonUtil.setPreferences(LoginActivity.this, "login_from", login_from);
                    CommonUtil.setPreferences(LoginActivity.this, "login_type", "others");
                    CommonUtil.setPreferences(LoginActivity.this, "login_domain", login_from);
                    CommonUtil.setPreferences(LoginActivity.this, "login_password", login_from);

                    login_progressDialog.dismiss();
                    check_autosubscriptions();
                    check_subs_smartscore();
                    check_subs_package();
                    CommonUtil.DebugLog("LoginActivity - ExcuteLoginOthers - checkautosubscriptions");

                    Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(int errorCode, String message) {
                    showAlert("로그인", message, "확인", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    public void check_autosubscriptions() {
        excuteNetworkTask("/mystockhero_android/check_subscriptions.php", new JSONObject(), false, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
            }

            @Override
            public void onRespons(Object result) throws Exception {
            }
        });
    }

    public void check_subs_smartscore() {
        excuteNetworkTask("/mystockhero_android/check_subs_smartscore.php", new JSONObject(), false, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
            }

            @Override
            public void onRespons(Object result) throws Exception {
            }
        });
    }

    public void check_subs_package() {
        excuteNetworkTask("/mystockhero_android/check_subs_package.php", new JSONObject(), false, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
            }

            @Override
            public void onRespons(Object result) throws Exception {
            }
        });
    }
}

