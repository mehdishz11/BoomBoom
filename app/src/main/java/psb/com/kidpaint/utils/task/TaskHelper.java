package psb.com.kidpaint.utils.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import psb.com.paintingview.DrawView;

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
        if (taskId == 1) {//share
            return task.getBoolean(KEY_SHARE_STATUS, false);
        } else if (taskId == 2) {//rate
            return task.getBoolean(KEY_RATE_STATUS, false);
        }
        return false;
    }

    public static int getTaskShowStep(Context context, int taskId) {
        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        if (taskId == 1) {//share
            return task.getInt(KEY_SHARE_STEP, TaskEnum.getStep(taskId));
        } else if (taskId == 2) {//rate
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
        if (taskId == 1) {
            int oldStep = task.getInt(KEY_SHARE_STEP, taskStep);
            editor.putInt(KEY_SHARE_STEP, (oldStep + taskStep));

        } else if (taskId == 2) {
            int oldStep = task.getInt(KEY_RATE_STEP, taskStep);
            editor.putInt(KEY_RATE_STEP, (oldStep + taskStep));
        }
        editor.apply();
        editor.commit();
    }

    public static void setTaskToShowed(Context context, int taskId) {

        SharedPreferences task = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = task.edit();
        if (taskId == 1) {
            editor.putBoolean(KEY_SHARE_STATUS, true);
        } else if (taskId == 2) {
            editor.putBoolean(KEY_RATE_STATUS, true);
        }
        editor.apply();
        editor.commit();
    }

    public static void checkTaskExistsForShow(Context context, iTask itask) {


        int numberOfSignUps = getNumberOfSignUps(context);

        int shareShowStep = getTaskShowStep(context, 1);
        int rateShowStep = getTaskShowStep(context, 2);

        boolean shareIsShowed = checkTaskIsShow(context, 1);
        boolean rateIsShowed = checkTaskIsShow(context, 2);

        if (shareIsShowed && rateIsShowed) {
            if (itask != null) {
                itask.allTaskIsShow();
            }


        } else {

            List<ModelTask> modelTasks=new ArrayList<>();
            //share
            ModelTask modelShare=new ModelTask();
            modelShare.setId(1);
            modelShare.setStep(shareShowStep);
            modelShare.setShowed(shareIsShowed);
            //rate
            ModelTask modelRate=new ModelTask();
            modelRate.setId(2);
            modelRate.setStep(rateShowStep);
            modelRate.setShowed(rateIsShowed);
            // add to list
            modelTasks.add(modelShare);
            modelTasks.add(modelRate);

            ModelTask model=findSmallTaskStepsForShow(modelTasks);
            if (model!=null&&numberOfSignUps >= model.getStep()) {
                if (itask != null) {
                    itask.newTaskForShow(model.getId(), TaskEnum.getMessage(model.getId()), TaskEnum.getCoin(model.getId()), null);
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
