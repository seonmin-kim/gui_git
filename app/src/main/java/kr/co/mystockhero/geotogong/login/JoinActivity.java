package kr.co.mystockhero.geotogong.login;

import android.app.Dialog;
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

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.mystockhero.geotogong.CommonActivity;
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

/**
 * Created by sesang on 16. 5. 22..
 */
public class JoinActivity extends CommonActivity {

    @Override
    protected void makeLayout() {

        makeJoinLayout();
    }

    private void makeJoinLayout() {

        topLayout = rootLayout.makeTop("", 0xffffffff, 0xff252525, -1, R.drawable.exit_button, this);
        bodyLayout = rootLayout.makeBody();

        bodyLayout.setBackgroundColor(0xffffffff);
        bodyLayout.setClickable(true);
        bodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    ControlUtil.hideKeyboard(JoinActivity.this, view);
                }
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        RelativeLayout layout = RelativeLayout.createLayout(null, this, bodyLayout,
                bodyLayout.createScaledHeightLayoutParams(Gravity.FILL, 45, 80, 45, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout layout_body = LinearLayout.createLayout(null, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.FILL_VERTICAL, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.VERTICAL, 0xffffffff);

        TextView.createTextView(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "계정 만들기", Gravity.CENTER_HORIZONTAL, 1, 42, 0, true, 0xff333333,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        EditText editname = EditText.createEditText("join_name", this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 60, 0, 0, 230, 60),
                "이름", "", Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        editname.setPadding(ControlUtil.convertWidth(20), 0, ControlUtil.convertWidth(20), 0);

        LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, 0xff707070);


        LinearLayout layout_email = LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.FILL_HORIZONTAL, 0, 20, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.HORIZONTAL, 0xffffffff);

        EditText editEmail = EditText.createEditText("join_id", this, layout_email,
                layout_email.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, 230, 60),
                "이메일", "", Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        editEmail.setPadding(ControlUtil.convertWidth(20), 0, ControlUtil.convertWidth(20), 0);

        createImageView(null, this, layout_email,
                layout_email.createLayoutParams(Gravity.LEFT|Gravity.TOP, 33, 30, 0, 0, 25, 25), R.drawable.email_gray);

        EditText editDomain = EditText.createEditText("join_domain", this, layout_email,
                layout_email.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, 230, 60),
                "직접입력", "", Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
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

        EditText editPassChk = EditText.createEditText("join_passwordChk", this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 20, 0, 0, 230, 60),
                "비밀번호 확인", "", Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 20, 28, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), true);
        editPassChk.setPadding(ControlUtil.convertWidth(20), 0, ControlUtil.convertWidth(20), 0);
        editPassChk.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        editPassChk.setTransformationMethod(PasswordTransformationMethod.getInstance());



        LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 2),
                LinearLayout.HORIZONTAL, 0xff707070);

        LinearLayout agreement_layout = LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 0, 30, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.HORIZONTAL, 0xffffffff);

        TextView.createTextView(null, this, agreement_layout,
                agreement_layout.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "계정을 생성하시면 서비스의 ", Gravity.LEFT|Gravity.TOP, 1, 22, 0, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        TextView agreement1 = TextView.createTextView("terms_service", this, agreement_layout,
                agreement_layout.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "이용약관", Gravity.LEFT|Gravity.TOP, 1, 22, 0, true, 0xff000000,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), false);

        agreement1.setOnClickListener(showSuggestion);

        TextView.createTextView(null, this, agreement_layout,
                agreement_layout.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "과 ", Gravity.LEFT|Gravity.TOP, 1, 22, 0, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        TextView agreement2 = TextView.createTextView("terms_private", this, agreement_layout,
                agreement_layout.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "개인 정보 및 수집", Gravity.LEFT|Gravity.TOP, 1, 22, 0, true, 0xff000000,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), false);

        agreement2.setOnClickListener(showSuggestion);


        LinearLayout agreement_layout2 = LinearLayout.createLayout(null, this, layout_body,
                layout_body.createScaledLayoutParams(Gravity.TOP|Gravity.LEFT, 0, 10, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                LinearLayout.HORIZONTAL, 0xffffffff);

        TextView.createTextView(null, this, agreement_layout2,
                agreement_layout2.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "및 ", Gravity.LEFT|Gravity.TOP, 1, 22, 0, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        TextView agreement3 = TextView.createTextView("terms_exemption", this, agreement_layout2,
                agreement_layout2.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "면책조항", Gravity.LEFT|Gravity.TOP, 1, 22, 0, true, 0xff000000,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), false);

        agreement3.setOnClickListener(showSuggestion);

        TextView.createTextView(null, this, agreement_layout2,
                agreement_layout2.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 0, 0, 0, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                "에 동의하는 것입니다.", Gravity.LEFT|Gravity.TOP, 1, 22, 0, true, 0xff666666,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        ImageView textView_signup = createImageView(null, this, layout_body,
                layout_body.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 40, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 78), R.drawable.account_button);

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excuteJoin();
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ControlUtil.showKeyboard(JoinActivity.this, (EditText)bodyLayout.findViewWithTag("join_id"));
            }
        },100);
    }

    public void onClickTopLeftButton() {


    }

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

        RelativeLayout layout = RelativeLayout.createLayout(null, JoinActivity.this, null,
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

    private View.OnClickListener checkAgreementListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener showTermsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(JoinActivity.this, TermsActivity.class);
            intent.putExtra("category", v.getTag().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    };

    private void makeAgreement(RelativeLayout layout, int y, String text, String tag) {

        CheckBox.createCheckBox(tag, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 5, y - 8, 0, 0, ControlUtil.getImageSize(this, R.drawable.check_off_03)),
                R.drawable.check_off_03,  R.drawable.check_on_03, R.drawable.check_off_03, checkAgreementListener);

        TextView.createTextView(null, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 60, y, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 36),
                text, Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 28, 0, true, 0xff333333,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        Button.createButton(tag, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.RIGHT|Gravity.TOP, 0, y - 5, 18, 0, 40,50),
                R.drawable.btn_document, showTermsListener);
    }

    private void makeSuggestion(RelativeLayout layout, int y, String text, String tag) {
        CheckBox check_suggestion = CheckBox.createCheckBox(tag, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.LEFT|Gravity.TOP, 5, y - 8, 0, 0, ControlUtil.getImageSize(this, R.drawable.check_off_03)),
                R.drawable.check_off_03,  R.drawable.check_on_03, R.drawable.check_off_03, checkSuggestionAgreementListener);

        TextView.createTextView(null, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.FILL_HORIZONTAL|Gravity.TOP, 60, y, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 36),
                text, Gravity.LEFT|Gravity.CENTER_VERTICAL, 1, 28, 0, true, 0xff333333,
                FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Regular), false);

        Button.createButton(tag, this, layout,
                RelativeLayout.createScaledLayoutParams(Gravity.RIGHT|Gravity.TOP, 0, y - 5, 18, 0, 40,50),
                R.drawable.btn_document, showSuggestion);
    }

    private View.OnClickListener checkSuggestionAgreementListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // CheckBox cb = (CheckBox)v;
            CheckBox cb = (CheckBox)bodyLayout.findViewWithTag("terms_suggestion");
            if(cb.isChecked()) {
                Intent intent = new Intent(JoinActivity.this, TermsActivity.class);
                intent.putExtra("category", v.getTag().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener showSuggestion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // String url = "http://geotogong.mystockhero.com/zero_suggest_doc_view.php";
            // new SuggestInvestmentPopup(getApplicationContext(), "투자 권유 문서", url).show();

            Intent intent = new Intent(JoinActivity.this, TermsActivity.class);
            intent.putExtra("category", v.getTag().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    };


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

    private boolean checkPasswordChk(String tag1, String tag2) {
        View view1 = bodyLayout.findViewWithTag(tag1);
        View view2 = bodyLayout.findViewWithTag(tag2);
        if( view1 == null || view2 == null ) return false;

        String text1 = ((EditText)view1).getText().toString();
        String text2 = ((EditText)view2).getText().toString();
        return text1.equals(text2);
    }

    private boolean checkAgreement(String tag, String msg) {

        View view = bodyLayout.findViewWithTag(tag);
        if ( view == null ) return false;

        if ( !((CheckBox)view).isChecked() ) {
            showAlert("", "\n" + msg + "\n", "확인", "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            return false;
        }
        return true;
    }

    private boolean vaildateEmail(String email) {

        // CommonUtil.DebugLog("vaildateEmail : " + email);

        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {

        //final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$"); // 4자리 ~ 16자리까지 가능
        final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{6,12}$"); // 6자리 ~ 12자리까지 가능

        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(password);
        return matcher.matches();
    }

    private void showInputAlert(final EditText editText, String msg) {

        Dialog dialog = showAlert("", "\n" + msg + "\n", "확인", "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                editText.requestFocus();
                ControlUtil.showKeyboard(JoinActivity.this, editText);
            }
        });

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void excuteJoin() {

        View view = getCurrentFocus();
        if (view != null) {
            ControlUtil.hideKeyboard(JoinActivity.this, view);
        }

        String name = "신규회원_" + String.valueOf((int)(Math.random()*1000000));
        String id = null;
        String domain = null;
        String password = null;

        if ( (name = checkFieldEmpty("join_name", "이름을 입력해 주세요.")) == null ) {
            return;
        }
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
        if ( (password = checkFieldEmpty("join_passwordChk", "비밀번호 확인을 입력해 주세요.")) == null ) {
            return;
        }

        if( !checkPasswordChk("join_password", "join_passwordChk")) {
            showInputAlert((EditText)bodyLayout.findViewWithTag("join_passwordChk"), "비밀번호가 일치하지 않습니다.");
            return;
        }

        if ( !validatePassword(password) ) {
            showInputAlert((EditText)bodyLayout.findViewWithTag("join_password"), "올바른 비밀번호를 입력해 주세요.\n영문,숫자,!@#$%. 6~12자\n영문과 숫자는 1개 이상 포함.");
            return;
        }


//        if ( !checkAgreement("terms_tendency", "성향분석에 동의해 주세요.")) {
//            return;
//        }
//        if( !checkAgreement("terms_service", "거투공 서비스 이용약관에 동의해주세요.")) {
//            return;
//        }
//        if ( !checkAgreement("terms_private", "개인정보 수집 이용에 동의해 주세요.") ) {
//            return;
//        }
//        if ( !checkAgreement("terms_exemption", "거투공 서비스 면책 조항에 동의해 주세요.") ) {
//            return;
//        }
//        if ( !checkAgreement("terms_suggestion", "권유문서 확인 후 동의해 주세요.") ) {
//            return;
//        }

        excuteJoin(name, id, domain, password);
    }

    private void excuteJoin(final String name, final String id, final String domain, final String password) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String url = "/register.php";
        JSONObject params = new JSONObject();

        try {

//            params.put("name", URLEncoder.encode(name, "utf-8"));
//            params.put("email", URLEncoder.encode(id + "@" + domain, "utf-8"));
//            params.put("password", URLEncoder.encode(password, "utf-8"));
//            params.put("deviceid", CommonUtil.getDeviceId(this));

            params.put("name", name);
            params.put("email", id + "@" + domain);
            params.put("password", password);
            // params.put("deviceid", CommonUtil.getDeviceId(this));

        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/register.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {

            @Override
            public void onRespons(Object result) {

                CommonUtil.setPreferences(JoinActivity.this, "login_name", name);
                CommonUtil.setPreferences(JoinActivity.this, "login_id", id);
                CommonUtil.setPreferences(JoinActivity.this, "login_domain", domain);
                CommonUtil.setPreferences(JoinActivity.this, "login_password", password);


                showAlert("회원가입", "회원가입에 성공하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // CommonUtil.DebugLog("startActivity");
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String message) {

                showAlert("회원가입", message, "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                });
            }
        });

    }
}
