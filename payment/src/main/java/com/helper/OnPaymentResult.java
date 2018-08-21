package com.helper;

public interface OnPaymentResult {
     interface OnSetupFinished {
            void onSuccess();
            void onFailed(String message);
    }

    interface OnPaymentFinished{
         void onSuccessPayment(String sku);
         void onFailedPayment(String message);
    }
}
