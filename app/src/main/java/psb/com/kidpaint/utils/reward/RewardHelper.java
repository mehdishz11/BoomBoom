package psb.com.kidpaint.utils.reward;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import psb.com.kidpaint.App;
import psb.com.kidpaint.utils.Utils;

import static psb.com.kidpaint.utils.reward.TaskEnum.STEP_1;
import static psb.com.kidpaint.utils.reward.TaskEnum.STEP_2;

public class RewardHelper {

    private static final String KEY_REWARD = "KEY_REWARD";
    private static final String KEY_REWARD_RUNNING_COUNT = "KEY_REWARD_RUNNING_COUNT";
    private static final String KEY_REWARD_COUNT_ = "KEY_REWARD_COUNT_";
    private static final String KEY_REWARD_EXECUTED_ = "KEY_REWARD_EXECUTED_";
    private Context context;

    private static boolean isChecked=false;

    public RewardHelper(Context context) {
        this.context = context;
    }


    public void checkState(OnTaskCheckCompeleted onTaskCheckCompeleted) {
        Log.d(App.TAG, "checkState0: ");


        isChecked=true;

        ArrayList<TaskEnum> arrTasks = new ArrayList<>();
        SharedPreferences shpRun = context.getSharedPreferences(KEY_REWARD, Context.MODE_PRIVATE);

        //get currect runnint count
        int runningCount=shpRun.getInt(KEY_REWARD_RUNNING_COUNT,1);

        //increase running count
        SharedPreferences.Editor editor = shpRun.edit();
        editor.putInt(KEY_REWARD_RUNNING_COUNT, runningCount+1);
        editor.apply();

        Log.d(App.TAG, "checkState1: "+runningCount);

        if(runningCount<2){
            if (onTaskCheckCompeleted != null) {
                onTaskCheckCompeleted.nothingForShow();
            }
            return;
        }

        for (TaskEnum taskEnum :
                TaskEnum.values()) {

            int currentRunTask = shpRun.getInt(KEY_REWARD_COUNT_ + taskEnum.getId(), taskEnum.getStep());

            Log.d(App.TAG, "checkState-runningCount->: "+runningCount);
            Log.d(App.TAG, "checkState-currentRunTask->: "+currentRunTask);
            Log.d(App.TAG, "checkState-runningCount->: "+(currentRunTask%runningCount));
            Log.d(App.TAG, "checkState-executed->: "+!shpRun.getBoolean(KEY_REWARD_EXECUTED_ + taskEnum.getId(), false));

            if(
                    runningCount>=currentRunTask &&
                    currentRunTask%runningCount==0 && //its time to show task
                    !shpRun.getBoolean(KEY_REWARD_EXECUTED_ + taskEnum.getId(), false)) {// not executed before


                editor = shpRun.edit();
                editor.putInt(KEY_REWARD_COUNT_ + taskEnum.getId(), currentRunTask + taskEnum.getStep());
                editor.apply();

                arrTasks.add(taskEnum);

            }
        }

        //sort by steps



        Log.d(App.TAG, "checkState2: "+arrTasks.size());

        if(arrTasks.size()>0){
            Log.d(App.TAG, "checkState3: "+arrTasks.get(0).getId());

            Collections.sort(arrTasks, new Comparator< TaskEnum >() {
                @Override public int compare(TaskEnum p1, TaskEnum p2) {
                    return p1.getStep()- p2.getStep(); // Ascending
                }
            });
            Log.d(App.TAG, "checkState3: "+arrTasks.get(0).getId());

            if (onTaskCheckCompeleted != null) {
                onTaskCheckCompeleted.newTaskForShow(arrTasks.get(0),getIntentById(arrTasks.get(0).getId()));
            }
        }else{
            onTaskCheckCompeleted.nothingForShow();
        }

    }

    private Intent getIntentById(int id){
        Intent intent = null;
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        if (id==STEP_1.getId()) {

            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "بوم بوم");
            String sAux = "\nدنیای نقاشی کودکان\n\n";
            sAux = sAux + "https://cafebazaar.ir/app/"+appPackageName+"/?l=fa \n\n";
            intent.putExtra(Intent.EXTRA_TEXT, sAux);

            intent=Intent.createChooser(intent, "اشتراک گذاری با ...");

        }else if (id==STEP_2.getId()){
            if (Utils.isPackageExisted("com.farsitel.bazaar")) {
                intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(Uri.parse("bazaar://details?id=" + appPackageName));
                intent.setPackage("com.farsitel.bazaar");
            }
        }
        return intent;
    }

    public void executedTask(int taskId){
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
