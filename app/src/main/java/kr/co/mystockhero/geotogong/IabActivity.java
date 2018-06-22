package kr.co.mystockhero.geotogong;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.iab.IabBroadcastReceiver;
import kr.co.mystockhero.geotogong.common.iab.IabException;
import kr.co.mystockhero.geotogong.common.iab.IabHelper;
import kr.co.mystockhero.geotogong.common.iab.IabResult;
import kr.co.mystockhero.geotogong.common.iab.Inventory;
import kr.co.mystockhero.geotogong.common.iab.Purchase;

/**
 * Created by sesang on 16. 7. 26..
 */
public class IabActivity extends CommonActivity  implements IabBroadcastReceiver.IabBroadcastListener {

//    static final String TAG = "Investment";
    static final int RC_REQUEST = 10001;

    private IabHelper mHelper;

    private IabBroadcastReceiver mBroadcastReceiver;
    private OnIabListener mIabListener;

    public interface OnIabListener {

        void onIabPurchaseFinished(IabResult result, Purchase purchase);
        void onConsumeFinished(IabResult result, boolean repurchase, Purchase purchase);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       //  String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5zGbEEK55vakqjmfeGguDoSBr9lT0SzAKdGWT4vSnohEBTWfJkaARvaFLinKdsgNn5i3NleuN8ZPHV4CQeJI2k8FMRABmlgv1mHhR+26z+od61yViWtS7vkkEv9WBBwruzpujTEm+zCUKPZZWY97PUEslrYAo5nZhaVz2AxVrm+S1RSJ/fp0cKKVxDa28GBUznpynERF6xVVMcCgnah3AFhJ/Ly7pGGNpFbW3kKucgPH6pkJuZQ6Y9T4Mbo61/qr+on9OvyiR7ACXpEJgmx5jjTiv39wJJoIE8sBKtB+DTSTjAX+1aEYPwFr4gztvamr20Q/Ipv68p9FmQgALdfI1wIDAQAB";
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5zGbEEK55vakqjmfeGguDoSBr9lT0SzAKdGWT4vSnohEBTWfJkaARvaFLinKdsgNn5i3NleuN8ZPHV4CQeJI2k8FMRABmlgv1mHhR+26z+od61yViWtS7vkkEv9WBBwruzpujTEm+zCUKPZZWY97PUEslrYAo5nZhaVz2AxVrm+S1RSJ/fp0cKKVxDa28GBUznpynERF6xVVMcCgnah3AFhJ/Ly7pGGNpFbW3kKucgPH6pkJuZQ6Y9T4Mbo61/qr+on9OvyiR7ACXpEJgmx5jjTiv39wJJoIE8sBKtB+DTSTjAX+1aEYPwFr4gztvamr20Q/Ipv68p9FmQgALdfI1wIDAQAB";
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {

                if (!result.isSuccess()) {
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                if (mHelper == null) return;

                mBroadcastReceiver = new IabBroadcastReceiver(IabActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }
        }
    };

    @Override
    public void receivedBroadcast() {

        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error querying inventory. Another async operation in progress.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mHelper == null) return;

        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {

        public void onIabPurchaseFinished(final IabResult result, final Purchase purchase) {

            if (mHelper == null) return;

            if (result.isFailure()) {
                if ( result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED ) {
//                    showAlert("결제", "이미 구매하였습니다. 확인을 누르면 구독여부가 갱신됩니다.", "확인", "", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if ( mIabListener != null ) {
//                                mIabListener.onIabPurchaseFinished(result, purchase);
//                            }
//                        }
//                    });
                } else {
                    complain("Error purchasing: " + result);
                    return;
                }
            }
            if ( mIabListener != null ) {
                mIabListener.onIabPurchaseFinished(result, purchase);
            }
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if (mHelper == null) return;
            if (result.isSuccess()) {
                if ( mIabListener != null ) {
                    mIabListener.onConsumeFinished(result, repurchase, purchase);
                }
            }
            else {
                complain("Error while consuming: " + result);
            }
        }
    };

    IabHelper.OnIabPurchaseFinishedListener mPurchaseSmartscoreFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {

        public void onIabPurchaseFinished(final IabResult result, final Purchase purchase) {
            CommonUtil.DebugLog("purchase 끝이다!@#!@#!@#!@#");
            if (mHelper == null) return;

            if (result.isFailure()) {
                if ( result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED ) {
//                    showAlert("결제", "이미 구매하였습니다. 확인을 누르면 구독여부가 갱신됩니다.", "확인", "", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if ( mIabListener != null ) {
//                                mIabListener.onIabPurchaseFinished(result, purchase);
//                            }
//                        }
//                    });
                } else {
                    complain("Error purchasing: " + result);
                    return;
                }
            }
            if ( mIabListener != null ) {
                mIabListener.onIabPurchaseFinished(result, purchase);
            }
        }
    };

    IabHelper.OnIabPurchaseFinishedListener mPurchasePackageFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {

        public void onIabPurchaseFinished(final IabResult result, final Purchase purchase) {
            CommonUtil.DebugLog("Package Product Purchase FinishedListener");
            if (mHelper == null) return;

            if (result.isFailure()) {
                if ( result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED ) {
                } else {
                    complain("Error purchasing: " + result);
                    return;
                }
            }
            if ( mIabListener != null ) {
                mIabListener.onIabPurchaseFinished(result, purchase);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    void complain(String message) {
        /*
        showAlert("오류", "거장 구독에 실패하였습니다.", "확인", "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        */
    }


    public Inventory queryPurchase(String productId) {

        List<String> itemSkus = new ArrayList<String>();
        itemSkus.add(productId);

        try {
            Inventory inventory = mHelper.queryInventory(true, itemSkus, null);
            return inventory;

        } catch (IabException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
        }
        return null;
    }

    public void excutePurchase(String productId, OnIabListener iabListener) {

        String payload = "";

        if ( iabListener != null ) {
            this.mIabListener = iabListener;
        }

        try {
            mHelper.launchPurchaseFlow(this, productId, RC_REQUEST, mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
        }
    }

    public void excuteSubscription(String productId, OnIabListener iabListener) {
        String payload = "";

        if ( iabListener != null ) {
            this.mIabListener = iabListener;
        }

        try {
            mHelper.launchSubscriptionPurchaseFlow(this, productId, RC_REQUEST, mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
        }
    }

    public void excutePurchaseSmartscore(String google_product_id, OnIabListener iabListener) {
        String payload = "";

        if ( iabListener != null ) {
            this.mIabListener = iabListener;
        }
        try {
            mHelper.launchPurchaseSmartscoreFlow(this, google_product_id, RC_REQUEST, mPurchaseSmartscoreFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
        }
    }

    public void excuteSubSmartscore(String google_product_id, OnIabListener iabListener) {
        String payload = "";

        if ( iabListener != null ) {
            this.mIabListener = iabListener;
        }
        try {
            mHelper.launchSubSmartscoreFlow(this, google_product_id, RC_REQUEST, mPurchaseSmartscoreFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
        }
    }

    public void excutePurchasePackage(String google_product_id, OnIabListener iabListener) {
        String payload = "";

        if ( iabListener != null ) {
            this.mIabListener = iabListener;
        }
        try {
            mHelper.launchPurchaseSmartscoreFlow(this, google_product_id, RC_REQUEST, mPurchasePackageFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
        }
    }

    public void excuteSubPackage(String google_product_id, OnIabListener iabListener) {
        String payload = "";

        if ( iabListener != null ) {
            this.mIabListener = iabListener;
        }
        try {
            mHelper.launchSubSmartscoreFlow(this, google_product_id, RC_REQUEST, mPurchasePackageFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
        }
    }

    private boolean repurchase = false;

    public void excuteConsume(Purchase purchase, boolean repurchase, OnIabListener iabListener) {
        this.mIabListener = iabListener;
        this.repurchase = repurchase;
        try {
            mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error consuming gas. Another async operation in progress.");
            return;
        }
    }
}
