package kr.co.mystockhero.geotogong.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.mystockhero.geotogong.CommonActivity;
import kr.co.mystockhero.geotogong.R;
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

/**
 * Created by sesang on 16. 8. 1..
 */
public class FindPasswordActivity extends CommonActivity {

    @Override
    protected void makeLayout() {

        String id = getIntent().getExtras().getString("id");
        String domain = getIntent().getExtras().getString("domain");

        makeLoginLayout(id, domain);
    }

    private void makeLoginLayout(String id, String domain) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        topLayout = rootLayout.makeTop("", 0xffffffff, 0xffffffff, -1, R.drawable.exit_button, this);
        bodyLayout = rootLayout.makeBody();

        bodyLayout.setBackgroundColor(0xffffffff);
        bodyLayout.setClickable(true);
        bodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    ControlUtil.hideKeyboard(FindPasswordActivity.this, view);
                }
            }
        });

        RelativeLayout layout = RelativeLayout.createLayout(null, this, bodyLayout,
                bodyLayout.createScaledHeightLayoutParams(Gravity.FILL, 45, 80, 45, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout layout_body = LinearLayout.createLayout(null, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.FILL_VERTICAL, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.VERTICAL, 0xffffffff);

        TextView.createTextView(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "비밀번호 찾기", Gravity.CENTER_HORIZONTAL, 1, 42, 0, true, 0xff333333,
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

        ImageView textView_signup = createImageView(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 30, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 78), R.drawable.find_pw);

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excuteFindPassword();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ControlUtil.showKeyboard(FindPasswordActivity.this, (EditText)bodyLayout.findViewWithTag("join_id"));
            }
        },100);
    }

    @Override
    public void onClickTopLeftButton() {


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

        RelativeLayout layout = RelativeLayout.createLayout(null, FindPasswordActivity.this, null,
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
                    RelativeLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 20, i * 70, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 70),
                    domains[i], Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 28, 0, true, 0xff333333,
                    FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);
            domain.setOnClickListener(onClickDomain);
            domain.setClickable(true);

            if ( i < domains.length - 1 ) {
                LinearLayout.createLayout(null, this, layout,
                        RelativeLayout.createScaledHeightLayoutParams(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, i * 70 + 68, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
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
                ControlUtil.showKeyboard(FindPasswordActivity.this, editText);
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void excuteFindPassword() {

        View view = getCurrentFocus();
        if (view != null) {
            ControlUtil.hideKeyboard(FindPasswordActivity.this, view);
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
            showInputAlert((EditText)bodyLayout.findViewWithTag("join_id"), "올바른 이메일을 입력해 주세요.");
            return;
        }

        excuteFindPassword(id, domain);
    }

    private void excuteFindPassword(final String id, final String domain) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        JSONObject params = new JSONObject();
        try {
            params.put("email", URLEncoder.encode(id + "@" + domain, "utf-8"));
            //params.put("deviceid", CommonUtil.getPreferences(this, "device_token", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/request_resetpassword.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {

            @Override
            public void onError(int errorCode, String message) {


                showAlert("비밀번호 찾기", message, "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                });
            }

            @Override
            public void onRespons(Object result) throws Exception {

                JSONObject data = ((JSONObject)result);

                showAlert("비밀번호 찾기", data.getString("message"), "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });



            }
        });
    }
}

