package com.helper;

import android.app.Activity;
import android.util.Log;

import com.util.IabHelper;
import com.util.IabResult;
import com.util.Inventory;

public class PaymentHelper {

    Activity activity;

    String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDHKOv4rqDaWI4hKjvImGItZuQvttzVB7HCvDdc6OQfczRObhvAjn3MKz8e7BLIoniY1+I7YwEOuM+fvgEp6ann1TNJTzmqObsXFankVdwvtshpeeheff4OUtyS0X+ARBKXHW6Gtz7pSRJ6RiLN9nvu+95zBQB79Yge6uKOev7lzudlJk1HMS08rFrkDUTWU82GUxWbp1BPUOzriSHSRECYKr9Doti2WQhUE5TG/E0CAwEAAQ==\n";

    // Debug tag, for logging
    static final String TAG = "";

    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU_PREMIUM = "package_test";

    // Does the user have the premium upgrade?
    boolean mIsPremium = false;

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 123;

    // The helper object
    IabHelper mHelper;


    public void init(){
        mHelper = new IabHelper(activity, base64EncodedPublicKey);

        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!
                mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
                    @Override
                    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                        if (result.isFailure()) {
                            Log.d(TAG, "Failed to query inventory: " + result);
                            return;
                        }
                        else {
                            Log.d(TAG, "Query inventory was successful.");
                            // does the user have the premium upgrade?
                            mIsPremium = inventory.hasPurchase(SKU_PREMIUM);

                            // update UI accordingly

                            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
                        }

                        Log.d(TAG, "Initial inventory query finished; enabling main UI.");

                    }
                });
            }
        });
    }

}
