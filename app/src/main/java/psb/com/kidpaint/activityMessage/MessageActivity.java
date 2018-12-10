package psb.com.kidpaint.activityMessage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.customView.BaseActivity;

public class MessageActivity extends BaseActivity {

    public static final String KEY_TITLE="KEY_TITLE";
    public static final String KEY_MESSAGE="KEY_MESSAGE";
    public static final String KEY_BTN_TEXT="KEY_BTN_TEXT";

    private TextView textTitle1;
    private TextView textTitle2;

    private ImageView imgClose;

    private TextView textMessage;

    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);

        textTitle1=findViewById(R.id.text_title_1);
        textTitle2=findViewById(R.id.text_title_2);

        imgClose=findViewById(R.id.img_btn_close);

        textMessage=findViewById(R.id.text_desc);

        btnSave=findViewById(R.id.btn_save);


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().getStringExtra(KEY_BTN_TEXT)!=null && !getIntent().getStringExtra(KEY_BTN_TEXT).isEmpty()){
            btnSave.setText(getIntent().getStringExtra(KEY_BTN_TEXT));
        }

        if(getIntent().getStringExtra(KEY_TITLE)!=null && !getIntent().getStringExtra(KEY_TITLE).isEmpty()){
            textTitle1.setText(getIntent().getStringExtra(KEY_TITLE));
            textTitle2.setText(getIntent().getStringExtra(KEY_TITLE));
        }

        if(getIntent().getStringExtra(KEY_MESSAGE)!=null && !getIntent().getStringExtra(KEY_MESSAGE).isEmpty()){
            textMessage.setText(getIntent().getStringExtra(KEY_MESSAGE));
        }

    }
}
