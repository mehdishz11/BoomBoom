package psb.com.kidpaint.myPrize;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.customView.ProgressView;

public class ActivityMyPrize extends AppCompatActivity {
    private ProgressView progressView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private TextView emptyView,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prize);
        initView();
    }
    void initView(){
        progressView=findViewById(R.id.progressView);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        recyclerView=findViewById(R.id.recyclerView);

        emptyView=findViewById(R.id.emptyView);
        back=findViewById(R.id.icon_back);
    }

}
