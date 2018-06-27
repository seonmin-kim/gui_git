package kr.co.mystockhero.geotogong.login;

/**
 * Created by sesang on 16. 5. 23..
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.facebook.CallbackManager;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.URLEncoder;
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
import kr.co.mystockhero.geotogong.common.widget.EditText;
import kr.co.mystockhero.geotogong.common.widget.ImageView;
import kr.co.mystockhero.geotogong.common.widget.TextView;

import static kr.co.mystockhero.geotogong.common.widget.ImageView.createImageView;

public class LoginEmailActivity extends CommonActivity {

    private CallbackManager callbackManager;
    LoginActivity loginActivity = (LoginActivity)LoginActivity.loginActivity;

    @Override
    protected void makeLayout() {

        String id = getIntent().getExtras().getString("id");
        String domain = getIntent().getExtras().getString("domain");
        String password = CommonUtil.getPreferences(LoginEmailActivity.this, "login_password", "");
        makeLoginLayout(id, domain, "");
        getIPAddress();
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

    private void makeLoginLayout(String id, String domain, String password) {

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        topLayout = rootLayout.makeTop("", 0xffffffff, 0xffffffff, -1, R.drawable.back_button, this);
        bodyLayout = rootLayout.makeBody();

        bodyLayout.setBackgroundColor(0xffffffff);
        bodyLayout.setClickable(true);
        bodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    ControlUtil.hideKeyboard(LoginEmailActivity.this, view);
                }
            }
        });

        RelativeLayout layout = RelativeLayout.createLayout(null, this, bodyLayout,
                bodyLayout.createScaledHeightLayoutParams(Gravity.FILL, 45, 80, 45, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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

        LinearLayout layout_email = LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.FILL_HORIZONTAL, 0, 60, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.HORIZONTAL, 0xffffffff);

        EditText editEmail = EditText.createEditText("join_id", this, layout_email,
                layout_email.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, 230, 60),
                "이메일", id, Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        editEmail.setPadding(ControlUtil.convertWidth(20), 0, ControlUtil.convertWidth(20), 0);

        createImageView(null, this, layout_email,
                layout_email.createLayoutParams(Gravity.LEFT|Gravity.TOP, 33, 30, 0, 0, 25, 25), R.drawable.email_gray);

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
                LinearLayout.HORIZONTAL, 0xff707070);

        LinearLayout.createLayout(null, this, layout_email_line,
                layout_email_line.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 50, 0, 0, 0, 250, 2),
                LinearLayout.HORIZONTAL, 0xff707070);

        EditText editPass = EditText.createEditText("join_password", this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 20, 0, 0, 230, 60),
                "비밀번호", "", Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        editPass.setPadding(ControlUtil.convertWidth(20), 0, ControlUtil.convertWidth(20), 0);
        editPass.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        editPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, 0xff707070);

        ImageView textView_signup = createImageView(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 30, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 78), R.drawable.login_button);

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excuteLogin();
            }
        });

        TextView textView_findPwd = TextView.createTextView(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 33, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "비밀번호 찾기", Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 1, 28, 0, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        textView_findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id = null;
                String domain = null;

                if ( (id = checkFieldEmpty("join_id", "이메일을 입력해 주세요.")) == null ) {
                    return;
                }
                if ( (domain = checkFieldEmpty("join_domain", "이메일을 입력해 주세요.")) == null ) {
                    return;
                }

                Intent intent = new Intent(LoginEmailActivity.this, FindPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",id);
                intent.putExtra("domain",domain);
                startActivity(intent);
            }
        });




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ControlUtil.showKeyboard(LoginEmailActivity.this, (EditText)bodyLayout.findViewWithTag("join_id"));
            }
        },100);

        bodyLayout.setFocusable(true);
        bodyLayout.setFocusableInTouchMode(true);
    }

    @Override
    public void onClickTopLeftButton() {
        onBackPressed();
    }

    @Override
    public void onClickTopRightButton() {

        onBackPressed();
    }

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

        RelativeLayout layout = RelativeLayout.createLayout(null, LoginEmailActivity.this, null,
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
                ControlUtil.showKeyboard(LoginEmailActivity.this, editText);
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void excuteLogin() {
        View view = getCurrentFocus();
        if (view != null) {
            ControlUtil.hideKeyboard(LoginEmailActivity.this, view);
        }

        String id = null;
        String domain = null;
        String password = null;

        if ( (id = checkFieldEmpty("join_id", "이메일을 입력해 주세요.")) == null ) {
            return;
        }
        if ( (domain = checkFieldEmpty("join_domain", "이메일을 입력해 주세요.")) == null ) {
            return;
        }
        if ( !vaildateEmail(id + "@" + domain) ) {
            showInputAlert((EditText)bodyLayout.findViewWithTag("join_id"), "올바른 이메일을 입력해 주세요. \n이메일에 공백/특수문자 사용여부를 확인해 주세요.");
            return;
        }
        if ( (password = checkFieldEmpty("join_password", "비밀번호를 입력해 주세요.")) == null ) {

            return;
        }
        if ( !validatePassword(password) ) {
            showInputAlert((EditText)bodyLayout.findViewWithTag("join_password"), "올바른 비밀번호를 입력해 주세요.");
            return;
        }
        excuteLogin(id, domain, password);
    }


    private void excuteLogin(final String id, final String domain, final String password) {
        final ProgressDialog login_progressDialog = ProgressDialog.show(LoginEmailActivity.this, "", "로그인 중입니다. 잠시 기다려주세요...", true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        JSONObject params = new JSONObject();
        try {
            params.put("email", URLEncoder.encode(id + "@" + domain, "utf-8"));
            params.put("password", URLEncoder.encode(password, "utf-8"));
            params.put("deviceid", CommonUtil.getPreferences(this, "device_token", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/yslee_login.php", params, false, new AsyncNetworkTask.NetworkTaskListener() {

            @Override
            public void onError(int errorCode, String message) {
                login_progressDialog.dismiss();
                showAlert("로그인", message, "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                });
            }

            @Override
            public void onRespons(Object result) throws Exception {

                if(checkUpdate()) {
                    JSONObject data = ((JSONObject) result);
                    application.setUserInfo(data);
                    CommonUtil.setPreferences(LoginEmailActivity.this, "login_id", id);
                    CommonUtil.setPreferences(LoginEmailActivity.this, "login_domain", domain);
                    CommonUtil.setPreferences(LoginEmailActivity.this, "login_password", password);
                    CommonUtil.setPreferences(LoginEmailActivity.this, "login_type", "normal");
                    CommonUtil.setPreferences(LoginEmailActivity.this, "setting_autologin",  "true" );

                    login_progressDialog.dismiss();
                    check_autosubscriptions();
                    check_subs_smartscore();
                    check_subs_package();
                    CommonUtil.DebugLog("LoginEmailActivity - ExcuteLogin - checkautosubscriptions");
                    Intent intent = new Intent(LoginEmailActivity.this, MainTabActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
                    loginActivity.finish();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void check_autosubscriptions() {
        excuteNetworkTask("/mystockhero_android_sm/check_subscriptions.php", new JSONObject(), false, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
            }

            @Override
            public void onRespons(Object result) throws Exception {
            }
        });
    }

    public void check_subs_smartscore() {
        CommonUtil.DebugLog("check_subs_smartscore()");
        excuteNetworkTask("/mystockhero_android_sm/check_subs_smartscore.php", new JSONObject(), false, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
            }

            @Override
            public void onRespons(Object result) throws Exception {
            }
        });
    }

    public void check_subs_package() {
        excuteNetworkTask("/mystockhero_android_sm/check_subs_package.php", new JSONObject(), false, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
            }

            @Override
            public void onRespons(Object result) throws Exception {
            }
        });
    }
}

