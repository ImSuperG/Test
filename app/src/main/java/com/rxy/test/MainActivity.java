package com.rxy.test;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;

import com.rxy.test.R;
import com.rxy.test.utils.StringUtil;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText et1;
    private EditText et2;
    private EditText et3;
    private Button more, login, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 获得手机堆内存的大小 MB
         * */
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        Log.d("mylog", "heap_size" + heapSize);
        initView();
    }

    private void initView() {
        more = (Button) findViewById(R.id.more);
        login = (Button) findViewById(R.id.user_login);
        cancel = (Button) findViewById(R.id.user_cancel);
        more.setOnClickListener(this);
        login.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                //进行资源释放操作
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                onMoreClick();
                break;
            case R.id.user_login:
                displayLogin();
                break;
            case R.id.user_cancel:
                displayCancel();
                break;
        }
    }

    private void displayLogin() {
        String userName=et1.getText().toString();
        String userPwd=et2.getText().toString();
        if(StringUtil.nonNull(userName)&&StringUtil.nonNull(userPwd)){
            if(userName.equals("rxy")&&userPwd.equals("123")){
                Intent intent=ImageActivity.newIntent(this,"HELLO");
                startActivity(intent);
            }
        }
    }

    private void displayCancel() {
    }

    private void onMoreClick() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.viewstub);

        if (viewStub != null) {
            View view = viewStub.inflate();
            et1 = (EditText) view.findViewById(R.id.et1);
            et2 = (EditText) view.findViewById(R.id.et2);
            et3 = (EditText) view.findViewById(R.id.et3);
        }
    }
}
