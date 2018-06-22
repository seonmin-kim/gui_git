package kr.co.mystockhero.geotogong;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import org.json.JSONObject;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.common.layout.TitleViewGroup;
import kr.co.mystockhero.geotogong.common.network.AsyncNetworkTask;
import kr.co.mystockhero.geotogong.login.LoginActivity;

/**
 * Created by macmini02 on 16. 5. 19..
 */
public abstract class CommonActivity extends AppCompatActivity implements View.OnClickListener {

    class SoundData {

        public int index;
        public String name;
        public int soundId;
        public int resourceId;

        public SoundData(int index, String name, int resourceId) {

            this.index = index;
            this.name = name;
            this.resourceId = resourceId;
            this.soundId = -1;
        }
    }

    private String kinds = "finder";
    private SoundData selectSoundData;


    public InvestmentApplication application = null;

    protected TitleViewGroup rootLayout;

    protected RelativeLayout topLayout;
    protected RelativeLayout bodyLayout;

    protected Dialog progressDialog = null;
    protected boolean isFinished = false;

    protected int orientation = Configuration.ORIENTATION_PORTRAIT;

    protected final int TITLE_HEIGHT = 129 - 40;
    protected final int BOTTOM_HEIGHT = 98;

    protected int topHeight = 0;
    protected int bottomHeight = 0;
    protected int bodyWidth = 0;
    protected int bodyHeight = 0;
    protected boolean isInit = false;


    // protected SeetalkApplication application;
    // protected SoundPlayer soundPlayer = null;

    protected PopupWindow popup = null;


    private Location lastKnowLocation = null;
    private String locationProvider = "";

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        application = (InvestmentApplication)getApplication();

        initLayout(false);

        rootLayout = new TitleViewGroup(this);
        setContentView(rootLayout);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.BLACK);
//        }

        makeLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // CommonUtil.DebugLog("CommonActivity.onActivityResult : " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {

        if (popup != null) {
            popup.dismiss();
            popup = null;
        }

        super.onDestroy();

        isFinished = true;
    }

    protected void setScreenInfo(int orientation, boolean status) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ControlUtil.setScreenInfo(this, displayMetrics, orientation, status);
    }

    protected void initLayout(boolean fullScreen) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT > 9) {
            new StrictMode.ThreadPolicy.Builder().permitAll().build();
        }

        application = (InvestmentApplication) getApplicationContext();

        if ( fullScreen ) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            if (Build.VERSION.SDK_INT < 16) {

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            } else {

                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            setScreenInfo(Configuration.ORIENTATION_PORTRAIT, true);

        } else {

//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//
//                setScreenInfo(Configuration.ORIENTATION_PORTRAIT, false);
//
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
////                getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//                setScreenInfo(Configuration.ORIENTATION_PORTRAIT, true);
//
//            } else {
//
////                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                setScreenInfo(Configuration.ORIENTATION_PORTRAIT, true);
//            }
            setScreenInfo(Configuration.ORIENTATION_PORTRAIT, false);
        }

    }

    protected void makeLayout() {


    }

//
//    protected void makeBody() {
//
//
//    }
//
//    protected void makeBottom() {
//
//
//    }

    @Override
    public void onClick(View v) {

        if ( "top_left_btn".equals(v.getTag().toString()) ) {
            onClickTopLeftButton();
        } else if ( "top_right_btn".equals(v.getTag().toString()) ) {
            onClickTopRightButton();
        }
    }

    public void onClickTopLeftButton() {


    }

    public void onClickTopRightButton() {


    }

    public void showPopup(View view, int gravity, int x, int y, int w, int h, boolean cancelable) {

        showPopup(view, gravity, x, y, w, h, true, true);
    }

    public void showPopup(View view, int gravity, int x, int y, int w, int h, boolean cancelable, boolean round) {

        if (popup != null) {
            popup.dismiss();
            popup = null;
        }

        popup = new PopupWindow(view, w, h);
        popup.setFocusable(true);
        popup.setOutsideTouchable(cancelable);
//        popup.setBackgroundDrawable(new ColorDrawable());
//        popup.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        if ( round ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popup.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shadow_round_border));
            } else {
                popup.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shadow_round_border));
            }
        }
//        popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_round_border));
        popup.setAnimationStyle(android.R.style.Animation_Dialog);
        popup.showAtLocation(view, gravity, x, y);
    }

    public void closePopup() {

        if (popup != null) {
            popup.dismiss();
            popup = null;
        }
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (KeyCode == KeyEvent.KEYCODE_BACK) {
                if (popup != null) {
                    if ( popup.isOutsideTouchable() ) {
                        popup.dismiss();
                        popup = null;
                    }
                    return false;
                }
            }
        }
        return super.onKeyDown(KeyCode, event);
    }

    public void showLoadingBar(boolean canelable) {
        // CommonUtil.DebugLog("showLoadingBar ");
        if ( progressDialog != null ) return;
        progressDialog = new Dialog(CommonActivity.this, R.style.Progress_Dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        progressDialog.setTitle("");
        progressDialog.setCancelable(canelable);
        progressDialog.setOnCancelListener(null);
		/* The next line will add the ProgressBar to the dialog. */
        progressDialog.addContentView(new ProgressBar(CommonActivity.this), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        progressDialog.show();
    }

    public void hideLoadingBar() {
        // CommonUtil.DebugLog("hideLoadingBar ");
        if ( progressDialog != null ) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public Dialog showAlert(String title, String msg, String btnPositive, String btnNagative, final DialogInterface.OnClickListener onClickListener) {

        if(msg.equals("")){

        }

        return showAlert(title, msg, btnPositive, btnNagative, onClickListener, false);
    }

    public Dialog showAlert(String title, String msg, String btnPositive, String btnNegative, final DialogInterface.OnClickListener onClickListener, final boolean finish) {

        AlertDialog dialog = new AlertDialog.Builder(CommonActivity.this).setTitle(title).setMessage(msg).setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // CommonUtil.DebugLog("positive");
                dialog.dismiss();
                if ( onClickListener != null ) {
                    onClickListener.onClick(dialog, which);
                }
                if ( finish ) {
                    finish();
                }

            }
        }).setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //CommonUtil.DebugLog("negative");
                dialog.dismiss();
                if ( onClickListener != null ) {
                    onClickListener.onClick(dialog, which);
                }
                if ( finish ) {
                    finish();
                }

            }
        }).create();

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
//        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.show();

        return dialog;
    }

    public Dialog showAlert(String title, View msg, String btnPositive, String btnNagative, final DialogInterface.OnClickListener onClickListener) {

        if(msg.equals("")){

        }

        return showAlert(title, msg, btnPositive, btnNagative, onClickListener, false);
    }

    public Dialog showAlert(String title, View msg, String btnPositive, String btnNegative, final DialogInterface.OnClickListener onClickListener, final boolean finish) {

        AlertDialog dialog = new AlertDialog.Builder(CommonActivity.this).setTitle(title).setView(msg).setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // CommonUtil.DebugLog("positive");
                dialog.dismiss();
                if ( onClickListener != null ) {
                    onClickListener.onClick(dialog, which);
                }
                if ( finish ) {
                    finish();
                }

            }
        }).setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //CommonUtil.DebugLog("negative");
                dialog.dismiss();
                if ( onClickListener != null ) {
                    onClickListener.onClick(dialog, which);
                }
                if ( finish ) {
                    finish();
                }

            }
        }).create();

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
//        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.show();

        return dialog;
    }

    public static String deviceVersion=null;
    public static String storeVersion=null;

    public boolean checkUpdate() {


        MarketVersionChecker mvc = new MarketVersionChecker();
        storeVersion = mvc.getDBVersionFast();

        int index = 0;
        while(true){
            if(storeVersion == null){
                storeVersion = mvc.getDBVersionFast();
                CommonUtil.DebugLog(getPackageName() + ", " + storeVersion + ", " + deviceVersion);
            }else{
                CommonUtil.DebugLog(getPackageName() + ", " + storeVersion + ", " + deviceVersion);
                break;
            }

            if(index == 20){

                showAlert("로그인", "구글 스토어에서 버전 정보를 얻는 중에 오류가 발생 했습니다. 잠시 후 다시 실행해 주세요", "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            }

            index ++;
        }
        //디바이스 버전 가져옴
        try {
            deviceVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        CommonUtil.DebugLog(getPackageName() + ", " + storeVersion + ", " + deviceVersion);

        //핸들러에서 넘어온 값 체크
        if(storeVersion != null && deviceVersion != null) {
            //if (storeVersion.compareTo(deviceVersion) > 0) {
            if( versionCompare(deviceVersion, storeVersion) < 0 ) {
                // 업데이트 필요

                android.app.AlertDialog.Builder alertDialogBuilder =
                        new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light));
                alertDialogBuilder.setIcon(R.drawable.icon);
                alertDialogBuilder.setTitle("업데이트");
                alertDialogBuilder
                        .setMessage("새로운 버전이 있습니다.\n보다 나은 사용을 위해\n업데이트 해주세요.")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton("업데이트", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 구글플레이 업데이트 링크
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=kr.co.mystockhero.geotogong")); startActivity(myIntent);
                            }
                        });
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();

                return false;
            } else {
                // 업데이트 불필요
                return true;
            }
        }else{

            showAlert("로그인", "인터넷 환경이 불안정합니다. 잠시 후 다시 시도해 주세요", "확인", "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(CommonActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
            return false;
        }

    }

    public static int versionCompare(String str1, String str2) {
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void excuteNetworkTask(String url, JSONObject params, final boolean showLoadingBar, final AsyncNetworkTask.NetworkTaskListener listener) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        AsyncNetworkTask networkTask = new AsyncNetworkTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if ( showLoadingBar ) {
                    showLoadingBar(false);
                }
            }

            @Override
            protected void onError(int errorCode, String message) {

                if(message.equals("세션이 만료되었습니다. 다시 로그인 해주세요!")){

//                    showAlert("오류", message, "확인", "", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            CommonUtil.thread_flag = false;
//                            try {
//                                CommonUtil.th.interrupt();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            Intent intent = new Intent(CommonActivity.this, IntroActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();
//
//                        }
//                    });

                    Intent intent = new Intent(CommonActivity.this, IntroActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                super.onError(errorCode, message);

                if ( showLoadingBar ) {
                    hideLoadingBar();
                }
                if ( listener != null ) {
                    try {
                        listener.onError(errorCode, message);
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onRespons(Object result) throws Exception {

                super.onRespons(result);

                if ( showLoadingBar ) {
                    hideLoadingBar();
                }
                if ( listener != null ) {
                    listener.onRespons(result);
                }
            }
        };

        if(Build.VERSION.SDK_INT >= 11) {
            networkTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "post", url, params);
        } else {

            networkTask.execute("post", url, params.toString());
        }
    }

}
