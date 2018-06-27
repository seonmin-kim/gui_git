package kr.co.mystockhero.geotogong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.igaworks.adbrix.IgawAdbrix;
import com.igaworks.commerce.IgawCommerce;
import com.igaworks.commerce.IgawCommerceProductAttrModel;
import com.igaworks.commerce.IgawCommerceProductCategoryModel;
import com.igaworks.commerce.IgawCommerceProductModel;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.RealPathUtil;
import kr.co.mystockhero.geotogong.common.iab.IabHelper;
import kr.co.mystockhero.geotogong.common.iab.IabResult;
import kr.co.mystockhero.geotogong.common.iab.Inventory;
import kr.co.mystockhero.geotogong.common.iab.Purchase;
import kr.co.mystockhero.geotogong.common.network.AsyncNetworkTask;
import kr.co.mystockhero.geotogong.common.widget.EditText;
import kr.co.mystockhero.geotogong.data.MasterData;
import kr.co.mystockhero.geotogong.data.PackagePurchaseData;
import kr.co.mystockhero.geotogong.data.PriceData;
import kr.co.mystockhero.geotogong.data.SmartscorePurchaseData;
import kr.co.mystockhero.geotogong.login.LoginActivity;

/**
 * Created by Lee,YongSuk 16. 10. 31
 * Since modifying 17.02.16
 */
public class MainTabActivity extends IabActivity implements IabActivity.OnIabListener{

    private String url;
    PriceData priceData;
    MasterData masterData;
    SmartscorePurchaseData smartPurchaseData;
    PackagePurchaseData packagePurchaseData;
    JSONObject purchaseData;
    public static WebView webViewLayout;

    private static final String TYPE_IMAGE = "image/*";
    private static final int INPUT_FILE_REQUEST_CODE = 1;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    private ProgressDialog asyncDialog;

    IgawCommerceProductAttrModel attrModel;
    private static final String INTENT_PROTOCOL_START = "intent:";
    private static final String INTENT_PROTOCOL_INTENT = "#Intent;";
    private static final String INTENT_PROTOCOL_END = ";end;";
    private static final String GOOGLE_PLAY_STORE_PREFIX = "https://play.google.com/store/apps/details?id=com.kakao.talk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void makeLayout() {
        // 애드브릭스
        HashMap attrModel1 = new HashMap<>();
        attrModel1.put("userRealId ", application.userRealId);
        attrModel = new IgawCommerceProductAttrModel(attrModel1);

        // Progress 선언
        asyncDialog = new ProgressDialog(MainTabActivity.this);

        bodyLayout = rootLayout.makeBody();
        // 앱 구동시 뱃지 카운트 삭제
        CommonUtil.badge_count = 0;
        updateIconBadgeCount(getApplicationContext(), 0);

        CommonUtil.DebugLog("MainTabActivity - makeLayout Executed: " + application.userRealId);
        url = "http://mystockhero.com/mystockhero_android_sm/successlogin.php?id="+application.userRealId;
        if(getIntent().getExtras().getString("url") != null){
            url = getIntent().getExtras().getString("url");
        }

        bodyLayout = rootLayout.makeBody(0xffe5e5e5);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );

        webViewLayout = new WebView(getApplicationContext());
        webViewLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        webViewLayout.getSettings().setJavaScriptEnabled(true);
        webViewLayout.getSettings().setDomStorageEnabled(true);
        webViewLayout.getSettings().setBuiltInZoomControls(false);
        webViewLayout.getSettings().setSupportZoom(false);
        webViewLayout.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webViewLayout.getSettings().setLoadWithOverviewMode(true);
        webViewLayout.getSettings().setUseWideViewPort(true);
        webViewLayout.getSettings().setSupportMultipleWindows(true);
        webViewLayout.setFocusable(true);
        webViewLayout.setFocusableInTouchMode(true);
        // Web data import
        webViewLayout.addJavascriptInterface(this, "Android");

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            webViewLayout.getSettings().setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            webViewLayout.getSettings().setTextZoom(100);
        }

        final Context myApp = this;

        webViewLayout.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onCloseWindow(WebView w) {
                super.onCloseWindow(w);
                finish();
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)
            {
                new AlertDialog.Builder(myApp).setTitle("거장들의 투자공식").setMessage(message)
                        .setPositiveButton(android.R.string.ok,new AlertDialog.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                result.confirm();
                            }
                        }).setCancelable(false).create().show();
                return true;
            }
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(myApp).setTitle("거장들의 투자공식").setMessage(message)
                        .setPositiveButton(android.R.string.ok,new AlertDialog.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                result.confirm();
                            }
                        }).setCancelable(false).create().show();
                return true;
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                final WebSettings settings = view.getSettings();
                settings.setDomStorageEnabled(true);
                settings.setJavaScriptEnabled(true);
                settings.setAllowFileAccess(true);
                settings.setAllowContentAccess(true);
                view.setWebChromeClient(this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(view);
                resultMsg.sendToTarget();
                return false;
            }

            // For Android Version < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                CommonUtil.DebugLog("WebViewActivity OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU), n=1");
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(TYPE_IMAGE);
                startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
            }

            // For 3.0 <= Android Version < 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                CommonUtil.DebugLog("WebViewActivity 3<A<4.1, OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU,aT), n=2");
                openFileChooser(uploadMsg, acceptType, "");
            }

            // For 4.1 <= Android Version < 5.0
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                CommonUtil.DebugLog("WebViewActivity 4.1<A<5.0, OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU,aT), n=2");
                Log.d(getClass().getName(), "openFileChooser : "+acceptType+"/"+capture);
                mUploadMessage = uploadFile;
                imageChooser();
            }

            // For Android Version 5.0+
            // Ref: https://github.com/GoogleChrome/chromium-webview-samples/blob/master/input-file-example/app/src/main/java/inputfilesample/android/chrome/google/com/inputfilesample/MainFragment.java
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                CommonUtil.DebugLog("WebViewActivity A>5, OS Version : " + Build.VERSION.SDK_INT + "\t onSFC(WV,VCUB,FCP), n=3");
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;
                imageChooser();
                return true;
            }

            private void imageChooser() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(getClass().getName(), "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:"+photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType(TYPE_IMAGE);

                Intent[] intentArray;
                if(takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
            }
        });

        webViewLayout.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String urlNewString) {
                 CommonUtil.DebugLog(urlNewString + " : " + url);

                // 앱 설치 or 업데이트시 처음 한번만 프로필 수정 화면 띄우기
                /*if(!CommonUtil.getPreferences(getApplicationContext(), "first_setting", "").equals("done")) {
                    FirebaseMessaging.getInstance().subscribeToTopic("geotogong_all");
                    webViewLayout.loadUrl("http://mystockhero.com/mystockhero_android_sm/mypage/settingprofile.php");
                    CommonUtil.setPreferences(getApplicationContext(), "first_setting", "done");
                }*/

                if(urlNewString.equals("http://mystockhero.com/mystockhero_android_sm/index.php")){
                    goLogout();
                    return true;
                }else if(urlNewString.equals("http://mystockhero.com/mystockhero_android_sm/mainhome/pricetable.php")){
                    // ////////////////////////////////////////////////////////////////////////////////////
                    //  결제 페이지 앱으로 적용시 아래 코드 사용
                    // ////////////////////////////////////////////////////////////////////////////////////
                    // ((MainTabActivity) getActivity()).fragmentAdd(PriceTableFragment.class, null, null);
                    // return true;
                }else if(urlNewString.contains("openUrl")){

//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", urlNewString.split("\\?")[1].split("=")[1]);
//                    bundle.putString("pagetitle", "거장들의 투자공식");
//                    ((MainTabActivity) getActivity()).fragmentAdd(WebviewFragment.class, bundle, null);

                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlNewString.split("openUrl=")[1])); startActivity(myIntent);
                    return true;

                }else if(urlNewString.contains("connectNH")){

                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlNewString.split("\\@")[1])); startActivity(myIntent);
                    return true;

                }else if(urlNewString.contains("alarm")){

                    if(urlNewString.split("\\?")[1].split("=")[1].equals("off")){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("geotogong_all");
                    }else{
                        FirebaseMessaging.getInstance().subscribeToTopic("geotogong_all");
                    }
                    return false;

                } else if(urlNewString.contains("serviceterms")){

                    Bundle bundle = new Bundle();
                    bundle.putInt("productid", Integer.parseInt(urlNewString.split("\\?")[1].split("&")[0].split("=")[1]));
                    bundle.putInt("type", 2);

                    // ////////////////////////////////////////////////////////////////////////////////////
                    //  결제 페이지 앱으로 적용시 아래 코드 사용
                    // ////////////////////////////////////////////////////////////////////////////////////
                    // ((MainTabActivity)getActivity()).fragmentAdd(ServiceTermsFragment.class, bundle, null);
                }  else if(urlNewString.contains("buyitem")) {
                    priceData = new PriceData();
                    masterData = new MasterData();
                    String params = urlNewString.split("\\?")[1]; // ex) market=apple&appleproductid=gm12v1gall&priceid=45&masterid=2&userid=1188
                    priceData.product_id = params.split("&")[1].split("=")[1];
                    priceData.discount = Integer.parseInt(params.split("&")[3].split("=")[1]);
                    priceData.id = Integer.parseInt(params.split("&")[2].split("=")[1]);

                    if(priceData.product_id.contains("auto")){
                        excuteSubscription(priceData.product_id, MainTabActivity.this);
                    }else{
                        excutePurchase(priceData.product_id, MainTabActivity.this);
                    }
                } else if (urlNewString.contains("buysmart")) {
                    priceData = new PriceData();
                    smartPurchaseData = new SmartscorePurchaseData();
                    String smart_params = urlNewString.split("\\?")[1]; // market=google & google_product_id=smartproduct_test & product_id=1 & user_id=32
                    smartPurchaseData.id = Integer.parseInt(smart_params.split("&")[2].split("=")[1]);
                    smartPurchaseData.google_product_id = smart_params.split("&")[1].split("=")[1];
                    priceData.id = smartPurchaseData.id;
                    priceData.product_id = smartPurchaseData.google_product_id;

                    CommonUtil.DebugLog("id: " + smartPurchaseData.id + ", google_product_id:" + smartPurchaseData.google_product_id);
                    if(smartPurchaseData.google_product_id.contains("ax")){
                        excuteSubSmartscore(smartPurchaseData.google_product_id, MainTabActivity.this);
                    }else {
                        excutePurchaseSmartscore(smartPurchaseData.google_product_id, MainTabActivity.this);
                    }
                } else if (urlNewString.contains("buypackage")) {
                    priceData = new PriceData();
                    packagePurchaseData = new PackagePurchaseData();
                    String package_params = urlNewString.split("\\?")[1]; // market=google & google_product_id=smartproduct_test & product_id=1 & user_id=32
                    packagePurchaseData.id = Integer.parseInt(package_params.split("&")[2].split("=")[1]);
                    packagePurchaseData.google_product_id = package_params.split("&")[1].split("=")[1];
                    priceData.id = packagePurchaseData.id;
                    priceData.product_id = packagePurchaseData.google_product_id;

                    CommonUtil.DebugLog("id: " + packagePurchaseData.id + ", google_product_id:" + packagePurchaseData.google_product_id);
                    if(packagePurchaseData.google_product_id.contains("rnb")){
                        excuteSubPackage(packagePurchaseData.google_product_id, MainTabActivity.this);
                    }else {
                        excutePurchasePackage(packagePurchaseData.google_product_id, MainTabActivity.this);
                    }
                }  else if (urlNewString.startsWith("tel:")) {
                    Intent dial = new Intent(Intent.ACTION_VIEW, Uri.parse(urlNewString));
                    startActivity(dial);
                    return true;
                } else if (urlNewString.startsWith("mailto:")) {
                    Intent mail = new Intent(Intent.ACTION_SEND);
                    mail.setType("application/octet-stream");
                    mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"mif@mystockhero.com"});
                    startActivity(mail);
                    return true;
                } else if (urlNewString.startsWith(INTENT_PROTOCOL_START)){
                    final int customUrlStartIndex = INTENT_PROTOCOL_START.length();
                    final int customUrlEndIndex = urlNewString.indexOf(INTENT_PROTOCOL_INTENT);
                    Intent kakaoIntent;
                    final String customUrl = urlNewString.substring(customUrlStartIndex, customUrlEndIndex);
                    try {
                        kakaoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(customUrl));
                        kakaoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getBaseContext().startActivity(kakaoIntent);
                    } catch (ActivityNotFoundException e) {
                        final int packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length();
                        final int packageEndIndex = urlNewString.indexOf(INTENT_PROTOCOL_END);

                        kakaoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_PREFIX));
                        kakaoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getBaseContext().startActivity(kakaoIntent);
                    }
                    return true;
                }

                return false;

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

        });

        webViewLayout.loadUrl(url);


        bodyLayout.addView(webViewLayout);
    }

    /**
     * More info this method can be found at
     * http://developer.android.com/training/camera/photobasics.html
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    private Uri getResultUri(Intent data) {
        Uri result = null;
        if(data == null || TextUtils.isEmpty(data.getDataString())) {
            // If there is not data, then we may have taken a photo
            if(mCameraPhotoPath != null) {
                result = Uri.parse(mCameraPhotoPath);
            }
        } else {
            String filePath = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                filePath = data.getDataString();
            } else {
                filePath = "file:" + RealPathUtil.getRealPath(this, data.getData());
            }
            result = Uri.parse(filePath);
        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INPUT_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mFilePathCallback == null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
                Uri[] results = new Uri[]{getResultUri(data)};

                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            } else {
                if (mUploadMessage == null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
                Uri result = getResultUri(data);

                Log.d(getClass().getName(), "openFileChooser : "+result);
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else {
            if (mFilePathCallback != null) mFilePathCallback.onReceiveValue(null);
            if (mUploadMessage != null) mUploadMessage.onReceiveValue(null);
            mFilePathCallback = null;
            mUploadMessage = null;
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        FragmentManager manager = getSupportFragmentManager();

        final Activity activity;

        if(this.webViewLayout.canGoBack()){
            this.webViewLayout.goBack();
        }else {
            AlertDialog.Builder ysleeBuilder = new AlertDialog.Builder(this).
                    setIcon(R.drawable.icon).
                    setTitle("거장들의 투자공식 앱 종료").
                    setMessage("거장들의 투자공식을\n종료하시겠습니까?").
                    setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).
                    setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            ysleeBuilder.show();
        }



    }

    public void updateIconBadgeCount(Context context, int count) {

        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", getLauncherClassName(context));
        intent.putExtra("badge_count", count);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            //intent.setFlags(0x00000020);
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

    @Override
    public void onIabPurchaseFinished(IabResult result, final Purchase purchase) {
        CommonUtil.DebugLog("오잉!!");
        if(purchase !=null) {
            CommonUtil.DebugLog("isSuccess(): " + result.isSuccess());
            CommonUtil.DebugLog("ItemType: " + purchase.getItemType());
            CommonUtil.DebugLog("OrderId: " + purchase.getOrderId());
            CommonUtil.DebugLog("Sku: " + purchase.getSku());
            CommonUtil.DebugLog("Purchase.getSku(): " + purchase.getSku() + ", Purchase.getToken(): " + purchase.getToken());
        }

        if ( !result.isSuccess() ) {
            // 이미 구매하였으면 consume처리
            if ( result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED ) {

                CommonUtil.DebugLog("consume start");
                Inventory inventory =  queryPurchase(priceData.product_id);
                CommonUtil.DebugLog("query : " + inventory.toString());
                CommonUtil.DebugLog("query : " + inventory.getPurchase(priceData.product_id) + ", " + inventory.getPurchase(priceData.product_id).getItemType());

                if ( inventory != null && inventory.getPurchase(priceData.product_id) != null ) {
                    CommonUtil.DebugLog("smart!!! ::  " + priceData.product_id);
                    excuteConsume(inventory.getPurchase(priceData.product_id), true, MainTabActivity.this);
                }
            }
            return;
        }

        //애드브릭스
        //상품 구매 API 호출
        IgawAdbrix.purchase(MainTabActivity.this,
                purchase.getOrderId(),                                          //String orderID
                IgawCommerceProductModel.create(                               //구매상품정의
                        priceData.product_id,                                  //String productID
                        priceData.product,                                     //String productName
                        priceData.total,                                       //double price
                        0.00,                                        //double discount
                        1,                                           //int quantity
                        IgawCommerce.Currency.KR_KRW,                          //Currency
                        IgawCommerceProductCategoryModel.create("subscription"),       //상품 카테고리 정의
                        attrModel),                                            // Attr 카테고리 정의
                IgawCommerce.IgawPaymentMethod.MobilePayment);                 //PaymentMethod
        // 애드브릭스 끝

        showLoadingBar(false);

        if(priceData.product_id.contains("smart")) {
            CommonUtil.DebugLog("Smartscore onIabPurchaseFinished");

            JSONObject params = new JSONObject();
            try {
                params.put("product_id", priceData.id);
                params.put("market", "google");
                String receipt = purchase.getSku() + "," + purchase.getOrderId() + "," + purchase.getToken();
                // CommonUtil.DebugLog("receipt : " + receipt);
                params.put("receipt", receipt);
            } catch (Exception e) {
                e.printStackTrace();
            }

            excuteNetworkTask("/mystockhero_android_sm/subscribe_smartscore.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
                @Override
                public void onError(int errorCode, String message) {
                    hideLoadingBar();
                    showAlert("오류", message, "확인", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }

                @Override
                public void onRespons(Object result) throws Exception {
                    hideLoadingBar();
                    purchaseData = (JSONObject) result;
                    CommonUtil.DebugLog("Smartscore onIabPurchaseFinished - onResponse(subscribe_smartscore.php)");

                    if ((priceData.product_id).contains("ax")) {
                        excuteAutoSmartscoreConsume(purchase);
                    } else {
                        excuteConsume(purchase, false, MainTabActivity.this);
                    }
                }
            });

        } else if(priceData.product_id.contains("pkg")) {
            CommonUtil.DebugLog("Package onIabPurchaseFinished");

            JSONObject params = new JSONObject();
            try {
                params.put("product_id", priceData.id);
                params.put("market", "google");
                String receipt = purchase.getSku() + "," + purchase.getOrderId() + "," + purchase.getToken();
                // CommonUtil.DebugLog("receipt : " + receipt);
                params.put("receipt", receipt);
            } catch (Exception e) {
                e.printStackTrace();
            }

            excuteNetworkTask("/mystockhero_android_sm/subscribe_package.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
                @Override
                public void onError(int errorCode, String message) {
                    hideLoadingBar();
                    showAlert("오류", message, "확인", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }

                @Override
                public void onRespons(Object result) throws Exception {
                    hideLoadingBar();
                    purchaseData = (JSONObject) result;
                    CommonUtil.DebugLog("Package onIabPurchaseFinished - onResponse(subscribe_package.php)");

                    if ((priceData.product_id).contains("rnb")) {
                        excuteAutoPackageConsume(purchase);
                    } else {
                        excuteConsume(purchase, false, MainTabActivity.this);
                    }
                }
            });

        } else {
            JSONObject params = new JSONObject();
            try {
                params.put("master_id", priceData.discount);
                params.put("product_id", priceData.id);
                params.put("market", "google");
                String receipt = purchase.getSku() + "," + purchase.getOrderId() + "," + purchase.getToken();
                // CommonUtil.DebugLog("receipt : " + receipt);
                params.put("receipt", receipt);
            } catch (Exception e) {
                e.printStackTrace();
            }

            excuteNetworkTask("/subscribe_master.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
                @Override
                public void onError(int errorCode, String message) {
                    hideLoadingBar();
                    showAlert("오류", message, "확인", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }

                @Override
                public void onRespons(Object result) throws Exception {
                    hideLoadingBar();
                    purchaseData = (JSONObject) result;
                    if ((priceData.product_id).contains("auto")) {
                        // CommonUtil.DebugLog("subscribe_master!!!!! > auto");
                        excuteAutoConsume(purchase);
                    } else {
                        excuteConsume(purchase, false, MainTabActivity.this);
                        // CommonUtil.DebugLog("subscribe_master!!!!! > not auto");
                    }
                }
            });
        }
    }

    public void excuteAutoConsume(Purchase purchase) {
        JSONObject params = new JSONObject();
        try {
            String receipt = purchase.getSku() + "," + purchase.getOrderId() + "," + purchase.getToken();
            params.put("receipt", receipt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        excuteNetworkTask("/zero_consume_product.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
            @Override
            public void onError(int errorCode, String message) {
                hideLoadingBar();
                showAlert("오류", message, "확인", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }

            @Override
            public void onRespons(Object result) throws Exception {
                hideLoadingBar();
                int master_id = 0;
                int old_master_id = 0;
                try {
                    JSONObject data = (JSONObject) result;
                    master_id = data.getInt("master_id");
                    if ( purchaseData != null ) {
                        old_master_id = purchaseData.getInt("master_id");
                    }
                } catch(Exception e) {
                }

                if (purchaseData != null ) { // 정상 결제 실행시
                    try {
                        application.isVIP = purchaseData.getBoolean("is_vip");
                        application.holdMaster = purchaseData.getInt("master");
                        masterData.is_subscribed = 1;
                    }catch (Exception e) {}

                    showAlert("결제", "구독 성공하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            webViewLayout.loadUrl("http://mystockhero.com/mystockhero_android_sm/masterpremium/index.php");
                        }
                    });

                }
            }
        });
    }

    public void excuteAutoSmartscoreConsume(Purchase purchase) {
        if (purchaseData != null ) { // 정상 결제 실행시
            try {
            }catch (Exception e) {}

            showAlert("결제", "구독 성공하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    webViewLayout.loadUrl("http://mystockhero.com/mystockhero_android_sm/quantreport/index.php");
                }
            });

        }
    }

    public void excuteAutoPackageConsume(Purchase purchase) {
        if (purchaseData != null ) { // 정상 결제 실행시
            try {
            }catch (Exception e) {}

            showAlert("결제", "구독 성공하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    webViewLayout.loadUrl("http://mystockhero.com/mystockhero_android_sm/quantreport/index.php");
                }
            });

        }
    }


    @Override
    public void onConsumeFinished(IabResult result, final boolean repurchase, Purchase purchase) {
        CommonUtil.DebugLog("Smartscore onConsumeFinished");
        // if we were disposed of in the meantime, quit.
        if ( !result.isSuccess() ) {
            return;
        }
        showLoadingBar(false);

        if(priceData.product_id.contains("smart")) {    // Smartscore
            JSONObject params = new JSONObject();
            try {
                String receipt = purchase.getSku() + "," + purchase.getOrderId() + "," + purchase.getToken();
                params.put("receipt", receipt);
            } catch (Exception e) {
                e.printStackTrace();
            }

            excuteNetworkTask("/mystockhero_android_sm/consume_smartscore.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
                @Override
                public void onError(int errorCode, String message) {
                    hideLoadingBar();
                    showAlert("오류", message, "확인", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }

                @Override
                public void onRespons(Object result) throws Exception {
                    hideLoadingBar();
                    int product_id = 0;
                    try {
                        JSONObject data = (JSONObject) result;
                        product_id =  data.getInt("product_id");
                    } catch (Exception e) {
                    }
                    if (!repurchase || purchaseData != null) { // 정상 결제 실행시
                        showAlert("결제", "구독 성공하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                webViewLayout.loadUrl("http://mystockhero.com/mystockhero_android_sm/quantreport/index.php");
                            }
                        });
                    } else { // 이미 구매한 상품을 소모할 시
                        if (product_id == priceData.id) {
                            showAlert("결제", "구독 처리하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onBackPressed();
                                }
                            });
                        } else {
                            // 기존 아이템 소모 처리 후, 재 구매
                            CommonUtil.DebugLog("excutePurchaseSmartscore - 기존 아이템 소모 처리 후, 재구매");
                            excutePurchaseSmartscore(priceData.product_id, MainTabActivity.this);
                        }
                        purchaseData = null;
                    }
                }
            });
        }
        else {  // Master 거장
            JSONObject params = new JSONObject();
            try {
                String receipt = purchase.getSku() + "," + purchase.getOrderId() + "," + purchase.getToken();
                params.put("receipt", receipt);
                params.put("email", ((EditText) bodyLayout.findViewWithTag("editEmail")).getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            excuteNetworkTask("/zero_consume_product.php", params, true, new AsyncNetworkTask.NetworkTaskListener() {
                @Override
                public void onError(int errorCode, String message) {
                    hideLoadingBar();
                    showAlert("오류", message, "확인", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }

                @Override
                public void onRespons(Object result) throws Exception {
                    hideLoadingBar();
                    int master_id = 0;
                    int old_master_id = 0;
                    try {
                        JSONObject data = (JSONObject) result;
                        master_id = data.getInt("master_id");
                        if (purchaseData != null) {
                            old_master_id = purchaseData.getInt("master_id");
                        }
                    } catch (Exception e) {
                    }

                    if (!repurchase || purchaseData != null) { // 정상 결제 실행시
                        try {
                            application.isVIP = purchaseData.getBoolean("is_vip");
                            application.holdMaster = purchaseData.getInt("master");
                            masterData.is_subscribed = 1;
                        } catch (Exception e) {
                        }

                        showAlert("결제", "구독 성공하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                webViewLayout.loadUrl("http://mystockhero.com/mystockhero_android_sm/masterpremium/index.php");
                            }
                        });
                    } else { // 이미 구매한 상품을 소모할 시
                        if (master_id == masterData.id) {
                            showAlert("결제", "구독 처리하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onBackPressed();
                                }
                            });
                            if (masterData.is_subscribed == 0) {
                                masterData.is_subscribed = 1;
                                application.holdMaster++;
                                application.isVIP = true;
                            }
                        } else {
                            // 기존 아이템 소모 처리 후, 재 구매
                            excutePurchase(priceData.product_id, MainTabActivity.this);
                            MasterData master = application.getMasterData(master_id);
                            if (master != null) {
                                if (master.is_subscribed == 0) {
                                    master.is_subscribed = 1;
                                    application.holdMaster++;
                                    application.isVIP = true;
                                }
                            }
                        }
                        purchaseData = null;
                    }
                }
            });
        }
    }

    public void goLogout() {
        CommonUtil.setPreferences(MainTabActivity.this, "login_from", "false");
        CommonUtil.setPreferences(MainTabActivity.this, "login_type", "false");
        Intent intent = new Intent(MainTabActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private Handler handler = new Handler();
    private Thread thread;
    @JavascriptInterface
    public void showLoading(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        asyncDialog.setMessage("데이터를 불러오는 중입니다.");
                        // show dialog
                        asyncDialog.show();
                    }
                });
            }
        });
        thread.start();
    }
    @JavascriptInterface
    public void hideLoading() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                asyncDialog.cancel();
            }
        });
    }
}
