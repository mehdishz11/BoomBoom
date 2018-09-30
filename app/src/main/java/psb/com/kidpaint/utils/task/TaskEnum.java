package psb.com.kidpaint.utils.task;

public enum TaskEnum {
    STEP_1(3, 4,15,"برنامه را با دوستان خود به اشتراک بگذارید و ۱۵ سکه جایزه بگیرید"),
    STEP_2(4, 6,20,"به برنامه امتیاز دهید و ۲۰ سکه جایزه بگیرید");

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

    public int getId() {
        return id;
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
