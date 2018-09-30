package psb.com.kidpaint.utils.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import psb.com.kidpaint.utils.Utils;

import static psb.com.kidpaint.utils.task.TaskEnum.STEP_1;
import static psb.com.kidpaint.utils.task.TaskEnum.STEP_2;

public class TaskHelper {
    private static String KEY_TASK = "KEY_TASK";
    private static String KEY_NUMBER_OF_SIGNUPS = "KEY_NUMBER_OF_SIGNUPS";

    private static String KEY_SHARE_STEP = "KEY_SHARE_STEP";//ids =1
    private static String KEY_SHARE_STATUS = "KEY_SHARE_STATUS";

    private static String KEY_RATE_STEP = "KEY_RATE_STEP";//ids=2
    private static String KEY_RATE_STATUS = "KEY_RATE_STATUS";


    public static void increaseNumberOfSignUps(Context context) {
        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        int oldEntries = task.getInt(KEY_NUMBER_OF_SIGNUPS, 0);
        SharedPreferences.Editor editor = task.edit();
        editor.putInt(KEY_NUMBER_OF_SIGNUPS, (1 + oldEntries));
        if (oldEntries == 0) {
            editor.putInt(KEY_SHARE_STEP, TaskEnum.getStep(1));
            editor.putInt(KEY_RATE_STEP, TaskEnum.getStep(2));
            editor.putBoolean(KEY_SHARE_STATUS, false);
            editor.putBoolean(KEY_RATE_STATUS, false);

        }
        editor.apply();
        editor.commit();

    }

    public static boolean checkTaskIsShow(Context context, int taskId) {
        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        if (taskId == STEP_1.getId()) {//share
            return task.getBoolean(KEY_SHARE_STATUS, false);
        } else if (taskId == STEP_2.getId()) {//rate
            return task.getBoolean(KEY_RATE_STATUS, false);
        }
        return false;
    }

    public static int getTaskShowStep(Context context, int taskId) {
        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        if (taskId == STEP_1.getId()) {//share
            return task.getInt(KEY_SHARE_STEP, TaskEnum.getStep(taskId));
        } else if (taskId == STEP_2.getId()) {//rate
            return task.getInt(KEY_RATE_STEP, TaskEnum.getStep(taskId));
        }
        return 0;
    }

    public static int getNumberOfSignUps(Context context) {
        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        return task.getInt(KEY_NUMBER_OF_SIGNUPS, 0);
    }

    public static void setTaskToNextLevel(Context context, int taskId) {

        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        int taskStep = TaskEnum.getStep(taskId);
        SharedPreferences.Editor editor = task.edit();
        if (taskId == STEP_1.getId()) {
            int oldStep = task.getInt(KEY_SHARE_STEP, taskStep);
            editor.putInt(KEY_SHARE_STEP, (oldStep + taskStep));

        } else if (taskId == STEP_2.getId()) {
            int oldStep = task.getInt(KEY_RATE_STEP, taskStep);
            editor.putInt(KEY_RATE_STEP, (oldStep + taskStep));
        }
        editor.apply();
        editor.commit();
    }

    public static void setTaskToShowed(Context context, int taskId) {

        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = task.edit();
        if (taskId == STEP_1.getId()) {
            editor.putBoolean(KEY_SHARE_STATUS, true);
        } else if (taskId == STEP_2.getId()) {
            editor.putBoolean(KEY_RATE_STATUS, true);
        }
        editor.apply();
        editor.commit();
    }

    public static void checkTaskExistsForShow(Context context, iTask itask) {


        int numberOfSignUps = getNumberOfSignUps(context);

        int shareShowStep = getTaskShowStep(context, STEP_1.getId());
        int rateShowStep = getTaskShowStep(context, STEP_2.getId());

        boolean shareIsShowed = checkTaskIsShow(context, STEP_1.getId());
        boolean rateIsShowed = checkTaskIsShow(context, STEP_2.getId());

        if (shareIsShowed && rateIsShowed) {
            if (itask != null) {
                itask.allTaskIsShow();
            }


        } else {

            List<ModelTask> modelTasks=new ArrayList<>();
            //share
            ModelTask modelShare=new ModelTask();
            modelShare.setId(STEP_1.getId());
            modelShare.setStep(shareShowStep);
            modelShare.setShowed(shareIsShowed);
            //rate
            ModelTask modelRate=new ModelTask();
            modelRate.setId(STEP_2.getId());
            modelRate.setStep(rateShowStep);
            modelRate.setShowed(rateIsShowed);
            // add to list
            modelTasks.add(modelShare);
            modelTasks.add(modelRate);

            ModelTask model=findSmallTaskStepsForShow(modelTasks);
            if (model!=null&&numberOfSignUps >= model.getStep()) {
                if (itask != null) {
                    Intent intent = null;
                    final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                    if (model.getId()==STEP_1.getId()) {

                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "بوم بوم");
                        String sAux = "\nدنیای نقاشی کودکان\n\n";
                        sAux = sAux + "https://cafebazaar.ir/app/"+appPackageName+"/?l=fa \n\n";
                        intent.putExtra(Intent.EXTRA_TEXT, sAux);



                    }else if (model.getId()==STEP_2.getId()){
                        if (Utils.isPackageExisted("com.farsitel.bazaar")) {
                            intent = new Intent(Intent.ACTION_EDIT);
                            intent.setData(Uri.parse("bazaar://details?id=" + appPackageName));
                            intent.setPackage("com.farsitel.bazaar");
                        }
                    }
                    
                    itask.newTaskForShow(model.getId(), TaskEnum.getMessage(model.getId()), TaskEnum.getCoin(model.getId()), intent);
                }
            }else {
                if (itask != null) {
                    itask.nothingForShow();
                }
            }

        }




    }

    public static ModelTask  findSmallTaskStepsForShow(List<ModelTask> stepList){
         ModelTask modelTask=null;
        Collections.sort(stepList);

        for (int i = 0; i <stepList.size() ; i++) {
            if (!stepList.get(i).getShowed()) {
              modelTask=stepList.get(i);
              break;

            }
        }
      return modelTask;
    }

    public interface iTask {
        void allTaskIsShow();
        void nothingForShow();

        void newTaskForShow(int taskId, String message, int coin, Intent intent);
    }
}
