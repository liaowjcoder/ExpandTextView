package com.zeal.expandtextview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zeal on 16/12/5.
 */

public class ExpandTextView extends LinearLayout {
    private TextView mTextView;
    private ImageView mArrowImageView;
    private String mText = "我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你我爱你";
    /**
     * 默认可以展示的最大行数
     */
    private int mMaxLines = 1;

    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置方向为竖直方向
        setOrientation(VERTICAL);


        //初始化view
        mTextView = new TextView(getContext());
        mArrowImageView = new ImageView(getContext());


        //设置子view需要展示的内容
        mTextView.setText(mText);
        mArrowImageView.setImageResource(R.mipmap.ic_launcher);

        //添加子view
        addView(mTextView);
        addView(mArrowImageView);


        initTextViewData(mMaxLines);

        bindListener();


    }

    //点击展开与折叠，不再赘述
    protected void bindListener() {
        setOnClickListener(new View.OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                mTextView.clearAnimation();
                final int deltaValue;
                final int startValue = mTextView.getHeight();
                int durationMillis = 350;
                if (isExpand) {
                    deltaValue = mTextView.getLineHeight() * mTextView.getLineCount() - startValue;
                    RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    mArrowImageView.startAnimation(animation);
                } else {
                    deltaValue = mTextView.getLineHeight() * mMaxLines - startValue;
                    RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    mArrowImageView.startAnimation(animation);
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        mTextView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }

                };
                animation.setDuration(durationMillis);
                mTextView.startAnimation(animation);
            }
        });
    }


    private void initTextViewData(final int lines) {
        mTextView.setHeight(mTextView.getLineHeight() * lines);

        post(new Runnable() {
            @Override
            public void run() {
                //mTextView.getLineCount()
                mArrowImageView.setVisibility(mTextView.getLineCount() > lines ? VISIBLE : GONE);
            }
        });
    }


}
