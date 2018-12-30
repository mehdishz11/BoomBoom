package psb.com.kidpaint.utils.reward;

public enum TaskEnum {
    STEP_2(4, 5,200,15,"امتیاز دادن","به برنامه ۵ امتیاز دهید و ۲۰۰ سکه جایزه بگیرید"),
    STEP_1(3, 8,150,14,"اشتراک گذاری","برنامه را با دوستان خود به اشتراک بگذارید و ۱۵۰ سکه جایزه بگیرید\nدر ضمن با این کار کمک بزرگی به ما کردین");

    private int id;
    private int step;
    private int coin;
    private String title;
    private String message;
    private int requestCode;


    TaskEnum(int id, int step, int coin,int requestCode,String title, String message) {
        this.id = id;
        this.step = step;
        this.coin = coin;
        this.message = message;
        this.title=title;
        this.requestCode=requestCode;
    }

    public int getId() {
        return id;
    }

    public int getStep() {
        return step;
    }

    public int getCoin() {
        return coin;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public int getRequestCode() {
        return requestCode;
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
