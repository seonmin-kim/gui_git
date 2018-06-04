package kr.co.mystockhero.geotogong.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by macmini02 on 16. 5. 19..
 */

public class CommonUtil {

    public static final boolean DEBUG = true;//BuildConfig.DEBUG;
    public static final String TAG = "Investment";

    public static int msgindex = 0;
    public static int badge_count = 0;
    public static String errmsg = "네트워크 오류가 발생 하였습니다.\n인터넷 연결 상태를 확인한 후, 다시 실행해주세요.";
    public static  Thread th = null;
    public static  boolean thread_flag = false;



    public static void DebugLog(String log) {
        if ( DEBUG ) Log.d(TAG, log);
    }

    public static void DebugLog(String format, Object... args) {
        if ( DEBUG ) Log.d(TAG, String.format(format, args));
    }

    public static void DebugError(String log) {
        if ( DEBUG ) Log.e(TAG, log);
    }

    public static void DebugError(String format, Object... args) {
        if ( DEBUG ) Log.e(TAG, String.format(format, args));
    }

    public static void DebugInfo(String log) {
        if ( DEBUG ) Log.i(TAG, log);
    }

    public static void DebugInfo(String format, Object... args) {
        if ( DEBUG ) Log.i(TAG, String.format(format, args));
    }

    public static void DebugNetwork(String log) {
        if ( DEBUG ) Log.d(TAG, log);
    }

    public static void DebugNetwork(String format, Object... args) {
        if ( DEBUG ) Log.d(TAG, String.format(format, args));
    }


    public static void setPreferences(Context context, String key, String value) {

        SharedPreferences prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreferences(Context context, String key, String value) {

        SharedPreferences prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return prefs.getString(key, value);
    }

    public static String getDeviceId(Context context)
    {
        String deviceId = "";
        TelephonyManager tManager = null;
        try {
            tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tManager.getDeviceId();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if ( deviceId.length() == 0 ) {
            deviceId = "emulator";
        }
        return deviceId;
    }

    public static String moneyFormat(Object money) {

        DecimalFormat fmt = new DecimalFormat("#,##0.###");
        String decimal =  fmt.format(money);

        return decimal;
        //return NumberFormat.getCurrencyInstance().format(money);
    }

    public static  String getRateFormat(double value) {

        //DecimalFormat format = new DecimalFormat("###,##0.########");
        DecimalFormat format = new DecimalFormat("###,##0.00");
        return format.format(value) + "%";
    }

    public static  String getRateFormatX100(double value) {

        //DecimalFormat format = new DecimalFormat("###,##0.########");
        DecimalFormat format = new DecimalFormat("###,##0.00");
        return format.format(value*100) + "%";
    }

    public static String getPointFormat1(double value) {
        DecimalFormat format = new DecimalFormat("###,##0.#");
        return format.format(value);
    }
    public static String getPointFormat(double value, int nPoint) {
        String fm = "###,##0.";
        for(int i=0; i<nPoint; i++) {
            fm += "0";
        }
        DecimalFormat format = new DecimalFormat(fm);
        return format.format(value);
    }
    public static String[] getStringArrayPointFormat(String[] str, int nPoint) {
        String fm = "###,##0.";
        for(int i=0; i<nPoint; i++) {
            fm += "0";
        }
        DecimalFormat format = new DecimalFormat(fm);
        for(int i=0; i<str.length; i++) {
            str[i] = format.format(Double.parseDouble(str[i]));
        }
        return str;
    }

    public static String getLineFormat(String str, int nChar) {
        StringBuilder justifiedText = new StringBuilder();
        StringBuilder justifiedLine = new StringBuilder();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length; i++) {
            justifiedLine.append(words[i]).append(" ");
            if (i+1 == words.length || justifiedLine.length() + words[i+1].length() > nChar) {
                justifiedLine.deleteCharAt(justifiedLine.length() - 1);
                justifiedText.append(justifiedLine.toString()).append("\n");
                justifiedLine = new StringBuilder();
            }
        }

        return justifiedText.toString().substring(0, justifiedText.lastIndexOf("\n"));
    }

    public static String replaceCharToStar(String str, float nPer) {
        if(str.length() > 0) {
            String result = "";
            result += str.substring(0, (int)(str.length()*nPer));
            for( int i=0; i<str.length() - (int)(str.length()*nPer); i++) {
                result += "*";
            }
            return result;
        }
        return "";
    }

    public static String ConvertToSecretEmail(String email) {
        String id = email.split("@")[0];
        String domain = email.split("@")[1] == null? "" : email.split("@")[1];
        String result = "";

        if(id.length() > 0) {
            result += id.substring(0, (int)(id.length()*0.5));
            for( int i=0; i<id.length() - (int)(id.length()*0.5); i++) {
                result += "#";
            }
        }
        result += "@" + domain;

        return result;
    }

    public static <K extends Comparable,V extends Comparable> Map<K,V> sortByKey(Map<K,V> map){
        List<K> keys = new LinkedList<K>(map.keySet());
        Collections.sort(keys, Collections.<K>reverseOrder());

        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
        for(K key: keys){
            sortedMap.put(key, map.get(key));
        }

        return sortedMap;
    }

    public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();

        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static List sortByValue(final Map map){
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator(){

            public int compare(Object o1,Object o2){
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v1).compareTo(v2);
            }

        });
        Collections.reverse(list); // 주석시 오름차순
        return list;
    }


    public static boolean openApp(Context context, String packageName, String install_uri) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            Uri uri = Uri.parse(install_uri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);

            return false;
            //throw new PackageManager.NameNotFoundException();
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        DebugLog("PackageName : " + packageName);
        context.startActivity(i);
        return true;
    }


}
