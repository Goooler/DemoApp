package io.goooler.demoapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.goooler.demoapp.R;
import io.goooler.demoapp.base.BaseActivity;
import io.goooler.demoapp.base.BaseApplication;
import io.goooler.demoapp.util.LogUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = find(R.id.buttonPanel);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            LogUtil.d("666");
        }
    }

    @Override
    protected void onDestroy() {
        BaseApplication.destroyGlobalObject();
        super.onDestroy();
    }
}
