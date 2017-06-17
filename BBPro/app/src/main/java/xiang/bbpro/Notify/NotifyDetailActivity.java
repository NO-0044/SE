package xiang.bbpro.Notify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xiang.bbpro.R;

/**
 * Created by xiang on 17-6-16.
 */

public class NotifyDetailActivity extends AppCompatActivity {
    private TextView Caption;
    private TextView Content;
    private Button Edit;
    private Toolbar tool;

    private Notify n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        n = (Notify) getIntent().getSerializableExtra("notify");
        setContentView(R.layout.activity_notifydetail);
        Caption = (TextView) findViewById(R.id.caption);
        Caption.setText(n.Caption);
        Content = (TextView) findViewById(R.id.content);
        Content.setText(n.Content);
        tool = (Toolbar) findViewById(R.id.toolbar);
        Edit = (Button) findViewById(R.id.notify_edit);
        tool.setNavigationOnClickListener(new Toolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotifyNewActivity.class);
                intent.putExtra("notify", n);
                intent.putExtra("type","modify");
                startActivity(intent);
                finish();
            }
        });
    }
}
