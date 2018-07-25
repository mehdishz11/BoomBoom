package psb.com.kidpaint.utils;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;

public enum IntroEnum {
    STEP_1(1, R.raw.green,R.layout.intro_step_1,"step_1"),
    STEP_2(2, R.raw.brown,R.layout.intro_step_2,"step_2"),
    STEP_3(3, R.raw.yellow,R.layout.intro_step_3,"step_3"),
    STEP_4(4, R.raw.bgr_be_happy,R.layout.intro_step_4,"step_4"),
    STEP_5(5, R.raw.bgr_happy_sunshine,R.layout.intro_step_5,"step_5"),
    STEP_6(6, R.raw.are_you_sure_exit,R.layout.intro_step_6,"step_6"),
    STEP_7(7, R.raw.click_bubbles_1,R.layout.intro_step_7,"step_7"),
    STEP_8(8, R.raw.bgr_happy_sunshine,R.layout.intro_step_8,"step_8"),
    STEP_9(9, R.raw.yellow,R.layout.intro_step_9,"step_9"),
    STEP_10(10, R.raw.brown,R.layout.intro_step_10,"step_10"),
    STEP_11(11, R.raw.bgr_happy_sunshine,R.layout.intro_step_11,"step_11"),
    STEP_12(12, R.raw.bgr_be_happy,R.layout.intro_step_12,"step_12"),
    STEP_13(13, R.raw.bgr_happy_sunshine,R.layout.intro_step_13,"step_13"),
    STEP_14(14, R.raw.bgr_be_happy,R.layout.intro_step_14,"step_14")
    ;

    private int id;
    private int soundId;
    private int layoutId;
    private String strId;


    IntroEnum(int id, int soundId, int layoutId,String strId) {
        this.id=id;
        this.soundId=soundId;
        this.layoutId=layoutId;
        this.strId=strId;
    }


    public int getId() {
        return id;
    }

    public int getSoundId() {
        return soundId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public static int getSoundId(int id){

        for(IntroEnum introEnum:IntroEnum.values()){
            if(introEnum.id==id){
                return introEnum.soundId;
            }
        }
        return STEP_1.soundId;
    }
    public static int getLayoutId(int id){

        for(IntroEnum introEnum:IntroEnum.values()){
            if(introEnum.id==id){
                return introEnum.layoutId;
            }
        }
        return STEP_1.layoutId;
    }

    public static String getShareId(int id){

        for(IntroEnum introEnum:IntroEnum.values()){
            if(introEnum.id==id){
                return introEnum.strId;
            }
        }
        return STEP_1.strId;
    }
}
