package com.cdye.baselibrary;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;


import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author cdy
 * @date 2018/9/10
 * @describe TODO
 */
public abstract class BaseFragment extends SupportFragment {
    protected ImmersionBar mImmersionBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View   baseview = inflater.inflate(getLayoutId(), container, false);


        return baseview;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initImmersionBar();

        initView(view,savedInstanceState);
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mImmersionBar!=null)mImmersionBar.destroy();
        super.onDestroy();
    }
    protected abstract int getLayoutId();
    protected abstract void initView(View view,Bundle savedInstanceState);
    /**
     * 初始化沉浸式
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.navigationBarWithKitkatEnable(false).init();
    }
}
