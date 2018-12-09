package com.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import net.jhoobin.jhub.CharkhoneSdkApp;

import ir.dorsa.totalpayment.tools.Utils;


public class PaymentHelper {

    private OnPaymentResult.OnSetupFinished onSetupFinished;
    private OnPaymentResult.OnPaymentFinished onPaymentFinished;

    private ProgressDialog pDialog;

    private Context context;

    // Debug tag, for logging
    static final String TAG = "purchase";



    public void init(Context context) {

        try {
            CharkhoneSdkApp.initSdk(context, Utils.getSecrets(context));
        } catch (Exception ex) {

        }

        this.context=context;

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("در حال برقراری ارتباط ...");

        if (onSetupFinished != null) {
            onSetupFinished.onSuccess();
        }
    }

    public void buyProduct(final Activity activity,final int requestCode,final String sku){
        if (onPaymentFinished != null) {
            onPaymentFinished.onSuccessPayment(sku);
        }

    }

    public void destroy() {

    }

    public void setOnSetupFinished(OnPaymentResult.OnSetupFinished onSetupFinished) {
        this.onSetupFinished = onSetupFinished;
    }

    public void setOnPaymentFinished(OnPaymentResult.OnPaymentFinished onPaymentFinished) {
        this.onPaymentFinished = onPaymentFinished;
    }

    public boolean checkActivityResult(int requestCode, int resultCode, Intent data) {
        return false;

    }

    public boolean isSetupFinished(){
//        return mHelper!=null;
        return true;
    }


    private String getPayload(){
        return "";
    }

    public static boolean isAgrigator(){
        return true;
    }

}
