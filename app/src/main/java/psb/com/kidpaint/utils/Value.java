package psb.com.kidpaint.utils;

import psb.com.kidpaint.App;

public class Value {
    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = App.getContext().getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }
}
