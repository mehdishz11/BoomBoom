package psb.com.kidpaint.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import psb.com.cview.CButton;
import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.HistoryFragment;
import psb.com.kidpaint.painting.PaintActivity;

public class HomeActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener,HistoryFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      /*  AppBarLayout appBar = findViewById(R.id.app_bar);
        appBar.addOnOffsetChangedListener(this);*/

        CButton btnCompetition=findViewById(R.id.competition);
        btnCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, PaintActivity.class));
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment()).commit();

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
