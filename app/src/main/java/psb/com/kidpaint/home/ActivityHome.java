package psb.com.kidpaint.home;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.FragmentHistory;

public class ActivityHome extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener,FragmentHistory.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      /*  AppBarLayout appBar = findViewById(R.id.app_bar);
        appBar.addOnOffsetChangedListener(this);*/
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHistory()).commit();

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
