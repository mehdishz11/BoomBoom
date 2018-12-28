package psb.com.kidpaint.utils.reward;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.helper.PaymentHelper;
import com.rasa.sharecontent.ShareContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Utils;

import static psb.com.kidpaint.utils.reward.TaskEnum.STEP_1;
import static psb.com.kidpaint.utils.reward.TaskEnum.STEP_2;

public class RewardHelper {

    private static final String KEY_REWARD = "KEY_REWARD";
    private static final String KEY_REWARD_RUNNING_COUNT = "KEY_REWARD_RUNNING_COUNT";
    private static final String KEY_REWARD_COUNT_ = "KEY_REWARD_COUNT_";
    private static final String KEY_REWARD_EXECUTED_ = "KEY_REWARD_EXECUTED_";
    private Context context;

    private static boolean isChecked = false;

    public RewardHelper(Context context) {
        this.context = context;
    }


    public void checkState(OnTaskCheckCompeleted onTaskCheckCompeleted) {
        Log.d(App.TAG, "checkState0: ");


        isChecked = true;

        ArrayList<TaskEnum> arrTasks = new ArrayList<>();
        SharedPreferences shpRun = context.getSharedPreferences(KEY_REWARD, Context.MODE_PRIVATE);

        //get currect runnint count
        int runningCount = shpRun.getInt(KEY_REWARD_RUNNING_COUNT, 1);

        //increase running count
        SharedPreferences.Editor editor = shpRun.edit();
        editor.putInt(KEY_REWARD_RUNNING_COUNT, runningCount + 1);
        editor.apply();

        Log.d(App.TAG, "checkState1: " + runningCount);

        if (runningCount < 2) {
            if (onTaskCheckCompeleted != null) {
                onTaskCheckCompeleted.nothingForShow();
            }
            return;
        }

        for (TaskEnum taskEnum :
                TaskEnum.values()) {

            int currentRunTask = shpRun.getInt(KEY_REWARD_COUNT_ + taskEnum.getId(), taskEnum.getStep());

            if (
                    runningCount >= currentRunTask &&
                            currentRunTask % runningCount == 0 && //its time to show task
                            !shpRun.getBoolean(KEY_REWARD_EXECUTED_ + taskEnum.getId(), false)) {// not executed before


                editor = shpRun.edit();
                editor.putInt(KEY_REWARD_COUNT_ + taskEnum.getId(), currentRunTask + taskEnum.getStep());
                editor.apply();

                arrTasks.add(taskEnum);

            }
        }

        //sort by steps

        if (arrTasks.size() > 0) {
            Collections.sort(arrTasks, new Comparator<TaskEnum>() {
                @Override
                public int compare(TaskEnum p1, TaskEnum p2) {
                    return p1.getStep() - p2.getStep(); // Ascending
                }
            });

            if (onTaskCheckCompeleted != null) {
                onTaskCheckCompeleted.newTaskForShow(arrTasks.get(0), getIntentById(arrTasks.get(0).getId()));
            }
        } else {
            onTaskCheckCompeleted.nothingForShow();
        }

    }

    public Intent getIntentById(int id) {
        Intent intent = null;
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        if (id == STEP_1.getId()) {

            String sAux = "\nمن با برنامه بوم بوم با استیکر ها نقاشی می کنم، یه تجربه جدید، حتما از لینک زیر دانلود کنین\n\n";
            if (PaymentHelper.isAgrigator()) {
                sAux = sAux + App.sharedUrl+"\n\n";
            } else {
                sAux = sAux + "https://cafebazaar.ir/app/" + appPackageName + "/?l=fa \n\n";
            }
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_app);
            intent = new ShareContent(context).getIntentShareImage(bm, sAux);

        } else if (id == STEP_2.getId()) {
            if (!PaymentHelper.isAgrigator() && Utils.isPackageExisted("com.farsitel.bazaar")) {
                intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(Uri.parse("bazaar://details?id=" + appPackageName));
                intent.setPackage("com.farsitel.bazaar");
            } else if (PaymentHelper.isAgrigator() && Utils.isPackageExisted("com.farsitel.bazaar")) {
                /*intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("dorsabazar://details?AppId="));*/
                String sAux = "\nمن با برنامه بوم بوم با استیکر ها نقاشی می کنم، یه تجربه جدید، حتما از لینک زیر دانلود کنین\n\n";
                sAux = sAux + App.sharedUrl+"\n\n";
                Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_app);
                intent = new ShareContent(context).getIntentShareImage(bm, sAux);

            } else if (PaymentHelper.isAgrigator()) {
                String sAux = "\nمن با برنامه بوم بوم با استیکر ها نقاشی می کنم، یه تجربه جدید، حتما از لینک زیر دانلود کنین\n\n";
                sAux = sAux + App.sharedUrl+" \n\n";
                Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_app);
                intent = new ShareContent(context).getIntentShareImage(bm, sAux);
            }
        }
        return intent;
    }

    public void executedTask(int taskId) {
        SharedPreferences shpRun = context.getSharedPreferences(KEY_REWARD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shpRun.edit();
        editor.putBoolean(KEY_REWARD_EXECUTED_ + taskId, true);
        editor.apply();
    }


    public interface OnTaskCheckCompeleted {
        void nothingForShow();

        void newTaskForShow(TaskEnum taskEnum, Intent intent);
    }


}
