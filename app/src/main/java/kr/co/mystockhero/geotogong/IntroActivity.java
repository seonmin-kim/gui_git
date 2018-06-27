package kr.co.mystockhero.geotogong;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.network.AsyncNetworkTask;
import kr.co.mystockhero.geotogong.common.widget.ImageView;
import kr.co.mystockhero.geotogong.login.LoginActivity;

/**
 * Created by sesang on 16. 5. 20..
 */
public class IntroActivity extends  CommonActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver registrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    private static final String[] TOPICS = {"global"};

    @Override
    protected void makeLayout() {

        bodyLayout = rootLayout.makeBody();


        ImageView imageView = ImageView.createImageView(null, this, bodyLayout,
                bodyLayout.createLayoutParams(Gravity.FILL, 0, -40, 0, -1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                R.drawable.splash1031);

        String token = CommonUtil.getPreferences(IntroActivity.this, "device_token", "");
        if (checkPlayServices() ) {
//            if ( "".equals(token) ) {
                registGCM();
//            } else {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        checkLogin();
//                    }
//                },1000);
//            }
        } else {

        }
    }


    void registGCM(){

        new AsyncTask<Void,Void,String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoadingBar(false);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
                    String token = instanceID.getToken();

//                    instanceID.deleteInstanceId();
//                    CommonUtil.DebugLog("deleteInstanceId");


//                    GcmPubSub pubSub = GcmPubSub.getInstance(IntroActivity.this);
//                    for (String topic : TOPICS) {
//                        pubSub.subscribe(token, "/topics/" + topic, null);
//                    }
                    return token;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String token) {

                super.onPostExecute(token);

                hideLoadingBar();

                // CommonUtil.DebugLog("Device registered, registration ID=" + token);
                CommonUtil.setPreferences(IntroActivity.this, "device_token", token);
                checkLogin();
            }

        }.execute(null, null, null);
    }

    void unregistGCM(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
                    instanceID.deleteInstanceId();

                    // CommonUtil.DebugLog("Device unregistered");

                    CommonUtil.setPreferences(getApplicationContext(), "device_token", "");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(null, null, null);
    }



    private boolean checkPlayServices() {

//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        CommonUtil.DebugLog("checkPlayService : " + resultCode + ", " + ConnectionResult.SUCCESS);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST, new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        finish();
//                    }
//                }).show();
//            } else {
//                CommonUtil.DebugLog("Device registered error : This device is not supported.");
//                finish();
//            }
//            return false;
//        }

        return true;
    }

    private void checkLogin() {

        String id = CommonUtil.getPreferences(IntroActivity.this, "login_id", "");
        String domain = CommonUtil.getPreferences(IntroActivity.this, "login_domain", "");
        String password = CommonUtil.getPreferences(IntroActivity.this, "login_password", "");
        String login_from = CommonUtil.getPreferences(IntroActivity.this, "login_from", "");
        String login_type = CommonUtil.getPreferences(IntroActivity.this, "login_type", "");

        CommonUtil.DebugLog("check : "+id + ", " + domain + ", " + password + ", " + login_type + ", " + login_from);

        if ( id.length() == 0 || domain.length() == 0 || password.length() == 0 ) {
            Intent intent = new Intent(IntroActivity.this, GuideActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else  if ( "normal".equals(login_type) ) {
            excuteLogin(id, domain, password);
        } else if ( "others".equals(login_type) ) {
            CommonUtil.DebugLog("--------------------------------------------------------------------------------");
            excuteLoginOthers(id, login_from);
        } else {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void excuteLogin(final String id, final String domain, String password) {
        CommonUtil.DebugLog("IntroActivity - excuteLogin");
        JSONObject params = new JSONObject();
        try {
            params.put("email", URLEncoder.encode(id + "@" + domain, "utf-8"));
            params.put("password", URLEncoder.encode(password, "utf-8"));
            params.put("deviceid", CommonUtil.getPreferences(this, "device_token", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/yslee_login.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
                showAlert("로그인", message, "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    }
                });
            }

            @Override
            public void onRespons(Object result) throws Exception {
                if(checkUpdate()) {
                    JSONObject data = ((JSONObject) result);
                    application.setUserInfo(data);
                    check_autosubscriptions();
                    check_subs_smartscore();
                    check_subs_package();
                    Intent intent = new Intent(IntroActivity.this, MainTabActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void excuteLoginOthers(final String id, final String login_from) {
        CommonUtil.DebugLog("IntroActivity - excuteLoginOthers");
        JSONObject params = new JSONObject();
        try {
            params.put("id", URLEncoder.encode(id + "(" + login_from + ")", "utf-8"));
            params.put("deviceid", CommonUtil.getPreferences(this, "device_token", ""));
            params.put("name", "name");
            params.put("login_from", URLEncoder.encode(login_from, "utf-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/zero_checkuser.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {

                showAlert("로그인", message, "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onRespons(Object result) throws Exception {
                if(checkUpdate()) {
                    JSONObject data = ((JSONObject) result);
                    application.setUserInfo(data);
                    check_autosubscriptions();
                    check_subs_smartscore();
                    check_subs_package();
                    Intent intent = new Intent(IntroActivity.this, MainTabActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
                }
            }
        });
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
        excuteNetworkTask("/mystockhero_android_sm/check_subs_smartscore.php", new JSONObject(), false, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
                CommonUtil.DebugLog("Error: " + message);
            }

            @Override
            public void onRespons(Object result) throws Exception {
                CommonUtil.DebugLog("subs_smartscore");
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
