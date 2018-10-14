package com.cdye.baselibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.cdye.baselibrary.R;


/**
 * @author cdy
 * @date 2018/9/21
 * @describe TODO
 */
@TargetApi( Build.VERSION_CODES.LOLLIPOP)
public class BaseToolBar extends Toolbar {
    private TextView tv_title,tv_right;
    private ImageView iv_back;
    private OnBackClickListener onBackClickListener;
    private OnRightClickListener onRightClickListener;

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    public BaseToolBar(Context context) {
        super(context);
        init(context,null);
    }

    public BaseToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public BaseToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs){

        LayoutInflater.from(context).inflate(R.layout.tool_bar,this);
        tv_title=findViewById(R.id.tv_title);
        tv_right=findViewById(R.id.tv_right);
        iv_back=findViewById(R.id.iv_back);
        if (attrs!=null){
            TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.BaseToolBar);
            String title=array.getString(R.styleable.BaseToolBar_btb_title_text);
            String right=array.getString(R.styleable.BaseToolBar_btb_right_text);
            int title_color=array.getColor(R.styleable.BaseToolBar_btn_title_text_color,0);
            int right_color=array.getColor(R.styleable.BaseToolBar_btn_right_text_color,0);
            float title_size=array.getDimensionPixelSize(R.styleable.BaseToolBar_btb_title_text_size,0);
            float right_size=array.getDimensionPixelSize(R.styleable.BaseToolBar_btb_right_text_size,0);

            if (!TextUtils.isEmpty(title)){
                tv_title.setText(title);
            }
            array.recycle();
        }
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackClickListener!=null){
                    onBackClickListener.onBackClick(v);
                }
            }
        });
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightClickListener!=null){
                    onRightClickListener.onRightClick(v);
                }

            }
        });
    }

    public void setTitle(CharSequence title){
        tv_title.setText(title);
    }
    public void setRight(CharSequence right){
        tv_right.setText(right);
    }
    public void setBackImage(int id){
        iv_back.setImageResource(id);
    }
    public interface OnBackClickListener{
        void onBackClick(View view);
    }
    public interface OnRightClickListener{
        void onRightClick(View view);
    }
}
