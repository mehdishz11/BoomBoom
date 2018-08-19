package psb.com.kidpaint.utils.task;

import psb.com.kidpaint.R;

public enum TaskEnum {
    STEP_1(1, 4,4,"اشتراک گذاری برنامه"),
    STEP_2(2, 6,6,"امتیاز به برنامه");

    private int id;
    private int step;
    private int coin;
    private String message;


    TaskEnum(int id, int step, int coin, String message) {
        this.id = id;
        this.step = step;
        this.coin = coin;
        this.message = message;
    }

    public static int getStep(int id){

        for(TaskEnum introEnum: TaskEnum.values()){
            if(introEnum.id==id){
                return introEnum.step;
            }
        }
        return STEP_1.step;
    }

    public static String getMessage(int id){

        for(TaskEnum introEnum: TaskEnum.values()){
            if(introEnum.id==id){
                return introEnum.message;
            }
        }
        return STEP_1.message;
    }

    public static int getCoin(int id){

        for(TaskEnum introEnum: TaskEnum.values()){
            if(introEnum.id==id){
                return introEnum.coin;
            }
        }
        return STEP_1.coin;
    }

}
