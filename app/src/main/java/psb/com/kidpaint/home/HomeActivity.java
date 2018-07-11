package psb.com.kidpaint.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import psb.com.cview.CButton;
import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.HistoryFragment;
import psb.com.kidpaint.home.newPaint.NewPaintFragment;
import psb.com.kidpaint.painting.PaintActivity;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;

public class HomeActivity extends AppCompatActivity implements
        HistoryFragment.OnFragmentInteractionListener,
        NewPaintFragment.OnFragmentInteractionListener
{


    private CButton btnNewPainting;
    private CButton btnHistory;

    private final String TAG_FRAGMENT_HISTORY = "TAG_FRAGMENT_HISTORY";
    private final String TAG_FRAGMENT_NEW_PAINTING = "TAG_FRAGMENT_NEW_PAINTING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnNewPainting = findViewById(R.id.btn_new_painting);
        btnHistory = findViewById(R.id.btn_history);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        btnNewPainting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNewPainting.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
                btnHistory.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewPaintFragment().newInstance(), TAG_FRAGMENT_NEW_PAINTING).commit();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHistory.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
                btnNewPainting.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment().newInstance("", ""), TAG_FRAGMENT_HISTORY).commit();
            }
        });


        ToolbarHandler.makeTansluteToolbar(this, getWindow(), getWindow().getDecorView());

        CButton btnCompetition = findViewById(R.id.competition);
        btnCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, PaintActivity.class));
            }
        });


//        set default values
        btnNewPainting.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
        btnHistory.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewPaintFragment().newInstance(), TAG_FRAGMENT_NEW_PAINTING).commit();

    }


    @Override
    public void onOutlineSelected(int resId) {
        Intent intent=new Intent(HomeActivity.this, PaintActivity.class);
        intent.putExtra(PaintActivity.KEY_RESOURCE_OUTLINE,resId);
        HomeActivity.this.startActivity(intent);
    }
}
