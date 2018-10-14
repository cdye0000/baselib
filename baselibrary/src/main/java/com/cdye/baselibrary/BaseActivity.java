package com.cdye.baselibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cdye.baselibrary.view.BaseToolBar;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author cdy
 * @date 2018/9/10
 * @describe TODO
 */
public abstract class BaseActivity extends SupportActivity {
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    protected BaseToolBar toolbar;
    public ImmersionBar mImmersionBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        this.setContentView(rootView);
        initToolBar();
        initImmersionBar();
        init(savedInstanceState);
//        EventBus.getDefault().register(this);

    }

    protected abstract int getLayoutId();
    protected abstract void init(Bundle savedInstanceState);
    @TargetApi( Build.VERSION_CODES.LOLLIPOP)
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        if (OSUtils.isEMUI3_1()) {

            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
            //第二种,禁止对导航栏的设置
            //mImmersionBar.navigationBarEnable(false).init();
        }
        if (toolbar!=null){
//            if (isToolBarTransParent()){
//                toolbar.getBackground().setAlpha(0);
//            }else {
//                toolbar.getBackground().setAlpha(255);
//            }
            mImmersionBar.titleBar(toolbar).init();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void initToolBar() {
        toolbar=findViewById(R.id.toolbar);
        if (toolbar!=null){
            toolbar.setBackImage(R.drawable.back);
            toolbar.setOnBackClickListener(new BaseToolBar.OnBackClickListener() {
                @Override
                public void onBackClick(View view) {
                    onNavigationClick();
                }
            });
        }
    }
    protected void onNavigationClick(){
        finish();
    }
    public void setTitle(CharSequence title){
        if (toolbar!=null){
            toolbar.setTitle(title);
        }
    }
    protected void setOnRightClickListener(BaseToolBar.OnRightClickListener onRightClickListener){
        if (toolbar!=null){
            toolbar.setOnRightClickListener(onRightClickListener);
        }
    }

    @Override
    protected void onDestroy() {

        toolbar=null;
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    protected void closeSoftKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                        .fullScreen(false)
                        .init();
            }
        }
    };

}
