package xiang.bbpro.Notify;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import xiang.bbpro.R;

/**
 * Created by xiang on 17-6-17.
 */

public class NotifyNewActivity extends AppCompatActivity{
    private EditText Caption;
    private EditText Content;
    private Button Save;
    private Toolbar tool;
    private String type;

    private Notify n;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        setContentView(R.layout.activity_notifynew);
        Caption = (EditText) findViewById(R.id.caption);
        Content = (EditText) findViewById(R.id.content);
        if (type.equals("modify")) {
            n = (Notify) getIntent().getSerializableExtra("notify");
            Caption.setText(n.Caption);
            Content.setText(n.Content);
        }
        tool = (Toolbar)findViewById(R.id.toolbar);
        Save = (Button)findViewById(R.id.notify_save);
        tool.setNavigationOnClickListener(new Toolbar.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notify new_n = new Notify();
                new_n.Caption = Caption.getText().toString();
                new_n.Content = Content.getText().toString();
                switch (type){
                    case "modify":
                        NotifyCommunication.modify(new_n);
                        break;
                    case "new":
                        NotifyCommunication.add(new_n);
                        break;
                }
                return;
            }
        });
    }
}
