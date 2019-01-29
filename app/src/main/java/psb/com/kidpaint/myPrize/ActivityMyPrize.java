package psb.com.kidpaint.myPrize;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import psb.com.kidpaint.R;
import psb.com.kidpaint.myPrize.adapter.Adapter_MyPrize;
import psb.com.kidpaint.utils.customView.ProgressView;

public class ActivityMyPrize extends AppCompatActivity implements IVMyPrize {
    private ProgressView progressView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private TextView emptyView,back;

    private PMyPrize pMyPrize;
    private Adapter_MyPrize adapter_myPrize;
    private int mLoadMode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prize);
        pMyPrize=new PMyPrize(this);
        initView();
        setAdapter();

        pMyPrize.getMyPrize(0);
    }
    void initView(){
        progressView=findViewById(R.id.progressView);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        recyclerView=findViewById(R.id.recyclerView);

        emptyView=findViewById(R.id.emptyView);
        back=findViewById(R.id.icon_back);
    }

    void setAdapter(){
        adapter_myPrize=new Adapter_MyPrize(pMyPrize);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter_myPrize);

        emptyView.setVisibility(adapter_myPrize.getItemCount()>0? View.GONE:View.VISIBLE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pMyPrize.getMyPrize(1);
            }
        });

    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startGetMyPrize(int loadMode) {
        mLoadMode=loadMode;

        if (loadMode==0) {
            progressView.setVisibility(View.VISIBLE);
            progressView.showProgress();
        }
    }

    @Override
    public void onSuccessGetMyPrize() {

        recyclerView.getAdapter().notifyDataSetChanged();
        emptyView.setVisibility(adapter_myPrize.getItemCount()>0? View.GONE:View.VISIBLE);

        swipeRefreshLayout.setRefreshing(false);
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onFailedGetMyPrize(int errorCode, String errorMessage) {
        if (mLoadMode==0) {
            progressView.showError(errorMessage, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pMyPrize.getMyPrize(0);
                }
            });
        }else{
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
