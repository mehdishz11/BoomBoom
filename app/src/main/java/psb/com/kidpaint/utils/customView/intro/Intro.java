package psb.com.kidpaint.utils.customView.intro;

import android.app.Activity;
import android.view.View;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.customView.intro.showCase.DismissListener;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnShowListener;
import psb.com.kidpaint.utils.customView.intro.showCase.OnViewInflateListener;

public class Intro {

    public static FancyShowCaseView addIntroTo(final Activity activity, final View view, final int relId, final IntroPosition introPosition) {
        return addIntroTo(
                activity,
                view,
                relId,
                introPosition,
                -1,
                "",
                null,
                null
        );
    }

    public static FancyShowCaseView addIntroTo(
            final Activity activity,
            final View view,
            final int relId,
            final IntroPosition introPosition,
            int soundId,
            String srtid,
            DismissListener dismissListener,
            OnShowListener onShowListener
    ) {

        return addIntroTo(
                activity,
                view,
                relId,
                introPosition,
                soundId,
                srtid,
                dismissListener,
                onShowListener,
                null);
    }

    public static FancyShowCaseView addIntroTo(
            final Activity activity,
            final View view,
            final int relId,
            final IntroPosition introPosition,
            int soundId,
            String srtid,
            DismissListener dismissListener,
            OnShowListener onShowListener,
            final OnViewInflateListener onViewInflateListener
    ) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0] + (view.getWidth() / 2);
        int y = location[1] + (view.getHeight() / 2);
        int radius = (int) Math.sqrt(Math.pow(view.getWidth(), 2) + Math.pow(view.getHeight(), 2)) / 2;
        final FancyShowCaseView fancyShowCaseView = new FancyShowCaseView.Builder(activity)
                .focusCircleAtPosition(x, y, radius)
                .customView(R.layout.intro_content, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated(View pView) {
                        if (pView instanceof IntroView) {
                            IntroView introView = (IntroView) pView;
                            introView.setup(view);
                            introView.addView(relId, introPosition);
                            if (onViewInflateListener != null) {
                                onViewInflateListener.onViewInflated(pView);
                            }
                        }
                    }
                })

                //.showOnce(srtid)
                .build();

        fancyShowCaseView.setSoundId(soundId);
        fancyShowCaseView.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow() {
                if (fancyShowCaseView.getSoundId() != -1) {
//                    SoundHelper.playSound(fancyShowCaseView.getSoundId());
                }
            }
        });
        fancyShowCaseView.setDismissListener(dismissListener);
        fancyShowCaseView.setOnShowListener(onShowListener);
        return fancyShowCaseView;
    }
}
