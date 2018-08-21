package com.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.util.IabHelper;
import com.util.IabResult;
import com.util.Inventory;
import com.util.Purchase;


public class PaymentHelper {


    private String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDHKOv4rqDaWI4hKjvImGItZuQvttzVB7HCvDdc6OQfczRObhvAjn3MKz8e7BLIoniY1+I7YwEOuM+fvgEp6ann1TNJTzmqObsXFankVdwvtshpeeheff4OUtyS0X+ARBKXHW6Gtz7pSRJ6RiLN9nvu+95zBQB79Yge6uKOev7lzudlJk1HMS08rFrkDUTWU82GUxWbp1BPUOzriSHSRECYKr9Doti2WQhUE5TG/E0CAwEAAQ==\n";

    private OnPaymentResult.OnSetupFinished onSetupFinished;
    private OnPaymentResult.OnPaymentFinished onPaymentFinished;

    private ProgressDialog pDialog;


    // Debug tag, for logging
    static final String TAG = "purchase";



    public static IabHelper mHelper;


    public void init(Context context) {

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("در حال برقراری ارتباط ...");

      if(mHelper!=null){
          if (onSetupFinished != null) {
          onSetupFinished.onSuccess();
      }
          return;
      }
        mHelper = new IabHelper(context, base64EncodedPublicKey);
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    showErrorSetup(result.getMessage());
                    mHelper=null;
                }

                // Hooray, IAB is fully set up!
                if (onSetupFinished != null) {
                    onSetupFinished.onSuccess();
                }
            }
        });
    }

    public void buyProduct(final Activity activity,final int requestCode,final String sku){
        if(mHelper==null){
            showErrorPayment("not setup success 1");
            return;
        }
        try {
            mHelper.launchPurchaseFlow(activity, sku, requestCode,
                    new IabHelper.OnIabPurchaseFinishedListener() {
                        @Override
                        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

                            // if we were disposed of in the meantime, quit.
                            if (mHelper == null){
                                showErrorPayment("not setup success 2");
                                return;
                            }

                            if (result.isFailure()) {
                                showErrorPayment(result.getMessage());
                                Log.d(TAG, "Error purchasing: " + result);
                                return;
                            }
                            if (!verifyDeveloperPayload(purchase)) {
                                showErrorPayment("Error purchasing. Authenticity verification failed.");
                                Log.d(TAG, "Error purchasing. Authenticity verification failed.");
                                return;
                            }

                            Log.d(TAG, "Purchase successful.");

                            if (purchase.getSku().equals(sku)) {
                             queryInventory(sku);
                            }

                        }
                    }, getPayload());
        }catch (Exception ex){
            showErrorPayment(ex.getMessage());
        }

    }

    private void queryInventory(final String sku){
        pDialog.show();
        mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, final Inventory inventory) {
                if (result.isFailure() && result.getResponse()!=6) {
                    Log.d(TAG, "Failed to query inventory: " + result);
                    showErrorPayment(result.getMessage());
                    return;
                } else {
                    consumeProduct(sku,inventory);
                }

            }
        });
    }

    private void consumeProduct(final String sku,Inventory inventory){

        if(inventory==null){
            showErrorPayment("not setup success 2");
            return;
        }
        // Check for gas delivery -- if we own gas, we should fill up the tank immediately
        Purchase gasPurchase = inventory.getPurchase(sku);

        if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
            Log.d(TAG, "We have gas. Consuming it.");
            mHelper.consumeAsync(inventory.getPurchase(sku), new IabHelper.OnConsumeFinishedListener() {
                @Override
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                    pDialog.cancel();

                    Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
                    // if we were disposed of in the meantime, quit.
                    if (mHelper == null){
                        showErrorPayment("not setup success 3");
                        return;
                    }

                    // We know this is the "gas" sku because it's the only one we consume,
                    // so we don't check which sku was consumed. If you have more than one
                    // sku, you probably should check...
                    if (result.isSuccess()) {
                        // successfully consumed, so we apply the effects of the item in our
                        // game world's logic, which in our case means filling the gas tank a bit
                        Log.d(TAG, "Consumption successful. Provisioning.");

                        if (onPaymentFinished != null) {
                            onPaymentFinished.onSuccessPayment(sku);
                        }

                    } else {
                        Log.d(TAG, "Error while consuming: " + result);
                        showErrorPayment("Error while consuming: " + result);
                    }

                }
            });
        }else{
            pDialog.cancel();
            showErrorPayment("محصول خریداری نشده است");
        }

    }

    public void destroy() {
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    public void setOnSetupFinished(OnPaymentResult.OnSetupFinished onSetupFinished) {
        this.onSetupFinished = onSetupFinished;
    }

    public void setOnPaymentFinished(OnPaymentResult.OnPaymentFinished onPaymentFinished) {
        this.onPaymentFinished = onPaymentFinished;
    }

    public boolean checkActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper == null) return false;
        return mHelper.handleActivityResult(requestCode, resultCode, data);

    }

    public boolean isSetupFinished(){
        return mHelper!=null;
    }

    private void showErrorSetup(String message){
        if (onSetupFinished != null) {
            onSetupFinished.onFailed(message);
        }
    }

    private void showErrorPayment(String message){
        if (onPaymentFinished != null) {
            onPaymentFinished.onFailedPayment(message);
        }
    }

    private String getPayload(){
        return "";
    }

    private boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

}
