package io.goooler.demoapp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import io.goooler.demoapp.R;
import io.goooler.demoapp.async.ClickHandler;
import io.goooler.demoapp.base.BaseActivity;
import io.goooler.demoapp.base.BaseApplication;
import io.goooler.demoapp.databinding.ActivityMainBinding;
import io.goooler.demoapp.util.ToastUtil;

public class MainActivity extends BaseActivity implements ClickHandler {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setClickHandler(this);
    }

    @Override
    protected void onDestroy() {
        BaseApplication.destroyGlobalObject();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonPanel) {
            ToastUtil.showToast("button clicked");
        }
    }
}
