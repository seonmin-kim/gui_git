package kr.co.mystockhero.geotogong;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.igaworks.IgawCommon;
import com.kakao.auth.KakaoSDK;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.data.MasterData;
import kr.co.mystockhero.geotogong.login.KakaoSDKAdapter;

/**
 * Created by macmini02 on 16. 5. 19..
 */
public class InvestmentApplication extends Application {

    public static String server_url = "http://geotogong.mystockhero.com";
    public static String access_token = null;
    public static String device_token = null;

    public int userRealId = 0;

    public String userEmail = "";
    public String userName = "";
    public int holdMaster = 0;
    public int totalMaster = 0;
    public int isHeavyUser = 0;
    public int didReview = 0;

    public int is_InvestmentTastes = 0;
    public int id_InvestmentTastes = 0;
    public String name_InvestmentTastes = "";

    public int suitability_count = 0;

//    public Map<String, String> settingValue;
    public boolean isVIP = false;

    public String joinpath = "";

    public ArrayList<Object> masterList;

    public boolean isThreadRunning = false;

    public static String getRateFormat(double value) {

        DecimalFormat format = new DecimalFormat("###,##0.########");
        return format.format(value) + "%";
    }

    public static String getDecimalFormat(double value) {
        DecimalFormat format = new DecimalFormat("###,##0.########");
        return format.format(value);
    }

    public static String convertStringToIntegerString(String value) {
        return String.valueOf(Integer.parseInt(value));
    }

    public static String convertStringToIntegerCommasString(String value) {
        value = value.replace(",", "");
        DecimalFormat format = new DecimalFormat("###,##0");
        return String.valueOf(format.format(Integer.parseInt(value)));
    }

    public static String convertStringToDecimalString(String value, int sing) {
        //String.format("%.2f", nText) + "%";
        return String.format("%." + sing +"f", Float.parseFloat(value));
    }

    public static String convertStringToDateMonthString(String value) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy년 MM월");

        String tTempMonth = value.substring(4,6);

        CommonUtil.DebugLog(tTempMonth + "Month");
        int nTempMonth = Integer.parseInt(tTempMonth);
        if( nTempMonth < 9 ) {
            nTempMonth += 1;
            tTempMonth = "0" + String.valueOf(nTempMonth);
        } else {
            if(nTempMonth != 12) {
                tTempMonth = String.valueOf(nTempMonth);
            } else{
                tTempMonth = "01";
            }
        }
        value = value.substring(0,4) + tTempMonth + "01";

        String dDate = "";
        try {
            Date sDate = sFormat.parse(value);
            dDate = dFormat.format(sDate);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        return dDate;
    }

    public static String convertStringToDateMonthString2(String value) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy년 MM월");

        String tTempMonth = value.substring(4,6);

        CommonUtil.DebugLog(tTempMonth + "Month");
        int nTempMonth = Integer.parseInt(tTempMonth);
        if( nTempMonth < 9 ) {
            tTempMonth = "0" + String.valueOf(nTempMonth);
        } else {
            tTempMonth = String.valueOf(nTempMonth);
        }
        value = value.substring(0,4) + tTempMonth + "01";

        String dDate = "";
        try {
            Date sDate = sFormat.parse(value);
            dDate = dFormat.format(sDate);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        return dDate;
    }

    public void setMasterList(ArrayList<Object> masterList) {
        this.masterList = masterList;
    }

    public MasterData getMasterData(int id) {

        if ( masterList == null ) return null;

        for( Object data : masterList ) {
            MasterData master = (MasterData)data;
            if ( master.getMasterId() == id ) return master;
        }
        return null;
    }

    public boolean isLogin() {
        if ( access_token != null ) return true;
        return false;
    }

    public void setUserInfo(JSONObject data) throws Exception {

        InvestmentApplication.access_token = data.getString("access_token");
        CommonUtil.DebugLog("InvestmentApplication.access_token = data.getString('access_token') -> " + data.getString("access_token"));

        CommonUtil.setPreferences(getApplicationContext(), "access_token",  InvestmentApplication.access_token);

        userRealId = data.getInt("id");

        userEmail = data.getString("email");
        userName = data.getString("name");
        holdMaster = data.getInt("master");
        totalMaster = data.getInt("total_master_count");

        isVIP = data.getBoolean("is_vip");
        isHeavyUser = data.getInt("is_heavyuser");
        didReview = data.getInt("did_review");

        is_InvestmentTastes = data.getInt("is_InvestmentTastes");
        id_InvestmentTastes = data.getInt("id_InvestmentTastes");
        name_InvestmentTastes = data.getString("name_InvestmentTastes");

         suitability_count = data.getInt("suitability");

        joinpath = data.getString("joinpath");

//        settingValue = new HashMap<String, String>();
//        settingValue.put("alarm_notice", data.getInt("alarm_notice") == 1 ? "true":"false");
//        settingValue.put("alarm_stock", data.getInt("alarm_stock") == 1 ? "true":"false");
//        settingValue.put("alarm_newsletter", data.getInt("alarm_newsletter") == 1 ? "true":"false");
//        settingValue.put("alarm_advice", data.getInt("alarm_advice") == 1 ? "true":"false");

        setSettingValue("alarm_notice", data.getInt("alarm_notice") == 1 );
        setSettingValue("alarm_stock", data.getInt("alarm_stock") == 1);
        setSettingValue("alarm_newsletter", data.getInt("alarm_newsletter") == 1);
        setSettingValue("alarm_advice", data.getInt("alarm_advice") == 1);

    }

    public boolean getSettingValue(String key) {

//        if ( isLogin() && settingValue != null ) {
//            if ( settingValue.containsKey(key) ) return settingValue.get(key).equals("true");
//        }
        return CommonUtil.getPreferences(getApplicationContext(), key, "true").equals("true");
    }

    public void setSettingValue(String key, boolean value) {

//        if ( isLogin() ) {
//            if ( settingValue == null ) settingValue = new HashMap<String, String>();
//            settingValue.put(key, value ? "true":"false");
//            return;
//        }
        CommonUtil.setPreferences(getApplicationContext(), key, value ? "true":"false");
    }


    public void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("kr.co.mystockhero.geotogong", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////
    // 카카오톡 - GlobalApplication
    // ///////////////////////////////////////////////////////////////////////////////////////////

    private static volatile InvestmentApplication instance = null;
    private static volatile Activity currentActivity = null;



    @Override
    public void onCreate() {
        super.onCreate();
        getHashKey();
        instance = this;

        KakaoSDK.init(new KakaoSDKAdapter());

        IgawCommon.autoSessionTracking(InvestmentApplication.this);
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        InvestmentApplication.currentActivity = currentActivity;
    }

    /**
     * singleton 애플리케이션 객체를 얻는다.
     * @return singleton 애플리케이션 객체
     */
    public static InvestmentApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
