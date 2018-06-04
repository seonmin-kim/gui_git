package kr.co.mystockhero.geotogong.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import kr.co.mystockhero.geotogong.common.CommonUtil;

/**
 * Created by Administrator on 2017-03-03.
 */
public class KakaoSignupActivity extends Activity {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                CommonUtil.DebugLog("[카카오톡] 세션 닫힘");
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                CommonUtil.DebugLog("[카카오톡] 카카오톡 회원이 아님");
            } // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                CommonUtil.DebugLog("[카카오톡] " + userProfile);
                CommonUtil.DebugLog("[카카오톡] Nickname    " + userProfile.getNickname());
                CommonUtil.DebugLog("[카카오톡] ProfileImagePath    " + userProfile.getProfileImagePath());
                CommonUtil.DebugLog("[카카오톡] ThumbnailImagePath    " + userProfile.getThumbnailImagePath());
                CommonUtil.DebugLog("[카카오톡] Properties    " + userProfile.getProperties());
                CommonUtil.DebugLog("[카카오톡] UUID    " + userProfile.getUUID());
                CommonUtil.DebugLog("[카카오톡] Id    " + userProfile.getId());
                CommonUtil.DebugLog("[카카오톡] RemainingInviteCount    " + userProfile.getRemainingInviteCount());
                CommonUtil.DebugLog("[카카오톡] serviceUserId    " + userProfile.getServiceUserId());
                CommonUtil.DebugLog("[카카오톡] RemainingGroupMsgCount    " + userProfile.getRemainingGroupMsgCount());
                redirectMainActivity(userProfile); // 로그인 성공시 MainActivity로
            }
        });
    }

    private void redirectMainActivity(UserProfile userProfile) {
        //(new PublicJoinLogin).excuteJoin();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("corporation_type", "카카오톡");
        intent.putExtra("kakao_id", String.valueOf(userProfile.getId()));
        intent.putExtra("kakao_describecontents", String.valueOf(userProfile.describeContents()));
        intent.putExtra("kakao_nickname", userProfile.getNickname());
        intent.putExtra("kakao_profileimagepath", userProfile.getProfileImagePath());
        intent.putExtra("kakao_serviceuserid", String.valueOf(userProfile.getServiceUserId()));
        intent.putExtra("kakao_remaininggroupmsgcount", String.valueOf(userProfile.getRemainingGroupMsgCount()));
        intent.putExtra("kakao_remininginveitecount", String.valueOf(userProfile.getRemainingInviteCount()));
        intent.putExtra("kakao_thumbnailimagepath", userProfile.getThumbnailImagePath());
        intent.putExtra("kakao_uuid", userProfile.getUUID());

        if(getIntent() != null) {
            if( getIntent().getExtras().getString("auto_others_login") != null ) {
                CommonUtil.setPreferences(KakaoSignupActivity.this, "setting_autologin_others",  getIntent().getExtras().getString("auto_others_login"));
            }
        }

        startActivity(intent);

        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}
