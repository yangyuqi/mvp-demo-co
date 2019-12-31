package com.wxjz.tenms_pad.activity;

import android.view.View;

import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.mvp.IBasePresenter;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.mvp.contract.IntegralContract;
import com.wxjz.tenms_pad.mvp.presenter.IntegralPresent;

public class IntegralActivity extends BaseMvpActivity<IntegralPresent> implements IntegralContract.View {
    @Override
    protected IntegralPresent createPresenter() {
        return new IntegralPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    protected void initView() {
        super.initView();

        initListener();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
