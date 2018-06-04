package kr.co.mystockhero.geotogong.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.mystockhero.geotogong.CommonActivity;
import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.network.AsyncNetworkTask;
import kr.co.mystockhero.geotogong.common.widget.EditText;

/**
 * Created by Administrator on 2017-03-06.
 */
public class PublicJoinLogin extends CommonActivity {

    public void excuteLogin(final String name, final String id, final String login_from) {
        JSONObject params = new JSONObject();

        try {
            params.put("name", name);
            params.put("id", id + "(" + login_from + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/zero_checkuser.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {

            @Override
            public void onRespons(Object result) {
                JSONObject data = ((JSONObject) result);
                try {
                    CommonUtil.DebugLog(data.getString("check"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                CommonUtil.setPreferences(PublicJoinLogin.this, "login_name", name);
//                CommonUtil.setPreferences(PublicJoinLogin.this, "login_id", id);
//                CommonUtil.setPreferences(PublicJoinLogin.this, "login_domain", domain);
//                CommonUtil.setPreferences(PublicJoinLogin.this, "login_password", password);

            }

            @Override
            public void onError(int errorCode, String message) {
                CommonUtil.DebugLog("Error");
            }
        });
    }
}
