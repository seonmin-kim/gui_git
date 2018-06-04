/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package kr.co.mystockhero.geotogong.common.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kr.co.mystockhero.geotogong.IntroActivity;
import kr.co.mystockhero.geotogong.MainTabActivity;
import kr.co.mystockhero.geotogong.R;
import kr.co.mystockhero.geotogong.common.CommonUtil;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "remoteMessage: " + remoteMessage.toString());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map data = remoteMessage.getData();

            sendNotification(data);

            Log.d(TAG, "Message data payload: " + data.get("body"));

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.e(TAG, "onMessageReceived: remoteMessage.getNotification() is null");

        } else {
            Log.e(TAG, "onMessageReceived: remoteMessage.getNotification() is null");
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param data FCM message body received.
     */
    private void sendNotification(Map data) {

        if (data == null) return;

        // 뱃지 카운트를 작성하기 위한 영역
        CommonUtil.badge_count++;
        Log.d(TAG, "badge_count: " + CommonUtil.badge_count);
        updateIconBadgeCount(getApplicationContext(), CommonUtil.badge_count);
        CommonUtil.thread_flag = false;
        try {
                CommonUtil.th.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CommonUtil.getPreferences(getApplicationContext(), "setting_pushmsg", "").equals("false")) {
            return;
        }
        String title = data.get("title").toString();
        String body = data.get("body").toString();
        String category = data.get("category1").toString();
        String[] bodyarray = body.split("/");

        String category2 = data.get("category2").toString();

        CommonUtil.DebugLog("title : " + title);
        CommonUtil.DebugLog("body : " +  body);
        CommonUtil.DebugLog("category : " + category);
        CommonUtil.DebugLog("category2 : " + data.get("category2").toString());


        Intent intent = new Intent(this, IntroActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("master_id", Integer.parseInt(data.get("master_id").toString()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, CommonUtil.msgindex, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(this);
        nbuilder.setSmallIcon(R.drawable.icon_push);
        nbuilder.setContentTitle(title);

        //if (bodyarray.length > 2 && category.equals("home") && !category2.equals("apology")) {
        if ( category2.equals("highrate") ) {
            nbuilder.setContentText(bodyarray[0]);

            CommonUtil.setPreferences(getApplicationContext(), "check_hotratepopup_"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "true");
//            CommonUtil.setPreferences(getApplicationContext(), "hotratedate", new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
//            CommonUtil.setPreferences(getApplicationContext(), "hotratestockcode", bodyarray[1]);
//            CommonUtil.setPreferences(getApplicationContext(), "hotratestockname", bodyarray[2]);
//            CommonUtil.setPreferences(getApplicationContext(), "hotratemastername", bodyarray[3]);
//            CommonUtil.setPreferences(getApplicationContext(), "hotratestockrate", bodyarray[4]);


        } else if (category2.equals("changestock")) {

            nbuilder.setContentText(bodyarray[0]);
            CommonUtil.setPreferences(getApplicationContext(), "check_changestockpopup", "true");
//            CommonUtil.setPreferences(getApplicationContext(), "changestockdate", new SimpleDateFormat("yyyy_MM").format(new Date()));
//            CommonUtil.setPreferences(getApplicationContext(), "changestockmastername", bodyarray[1]);

        }else if (category2.equals("morningbreif")){

            nbuilder.setContentText( "["+new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + " 모닝브리핑]" +body);
//            CommonUtil.setPreferences(getApplicationContext(), "category2", data.get("category2").toString());
//            CommonUtil.setPreferences(getApplicationContext(), "category3", data.get("category3").toString());

        }else if (category2.equals("notice")){

            nbuilder.setContentText( "[공지사항] " +body);
//            CommonUtil.setPreferences(getApplicationContext(), "category2", data.get("category2").toString());
//            CommonUtil.setPreferences(getApplicationContext(), "category3", data.get("category3").toString());

        }else if (category.equals("MasterLeague")){

            nbuilder.setContentText(body);
            CommonUtil.setPreferences(getApplicationContext(), "category2", data.get("category2").toString());

        } else if (category2.equals("apology")){
            CommonUtil.setPreferences(getApplicationContext(), "check_apologypopup", "true");
            CommonUtil.setPreferences(getApplicationContext(), "apology_title", bodyarray[1]);
            CommonUtil.setPreferences(getApplicationContext(), "apology_contents", bodyarray[2]);
        } else
        {
            nbuilder.setContentText(body);
        }

        nbuilder.setAutoCancel(true);
        nbuilder.setSound(defaultSoundUri);
        nbuilder.setContentIntent(pendingIntent);
        // 진동 설정
        nbuilder.setVibrate(new long[]{0,2000});
        Notification notification = nbuilder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(CommonUtil.msgindex, notification);

        CommonUtil.msgindex++;

    }

    // 뱃지 카운트를 위한 함수
    public void updateIconBadgeCount(Context context, int count) {

        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", getLauncherClassName(context));
        intent.putExtra("badge_count", count);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            intent.setFlags(0x00000020);
        }
        sendBroadcast(intent);
    }
    private String getLauncherClassName(Context context) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(getPackageName());
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, 0);

        if(resolveInfoList != null && resolveInfoList.size() > 0) {
            return resolveInfoList.get(0).activityInfo.name;
        }else{
            return "";
        }

    }

}
