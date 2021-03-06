package cn.goal.goal;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.goal.goal.util.DisplayUtil;

public class OneWordEveryDayActivity extends AppCompatActivity {
    public ImageButton comback;
    private FrameLayout mCardMainContainer;
    private LinearLayout mCardFontContainer;
    private RelativeLayout mCardBackContainer;
    private AnimatorSet mRightOutAnimatorSet, mLeftInAnimatorSet;
    private boolean mIsShowBack = false;  //是否显示背面
    //
    private ImageView mNormal, mWave1, mWave2, mWave3;
    private AnimationSet mAnimationSet1, mAnimationSet2, mAnimationSet3;
    private static final int OFFSET = 600;  //每个动画的播放时间间隔
    private static final int MSG_WAVE2_ANIMATION = 2;
    private static final int MSG_WAVE3_ANIMATION = 3;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WAVE2_ANIMATION:
                    mWave2.startAnimation(mAnimationSet2);
                    break;
                case MSG_WAVE3_ANIMATION:
                    mWave3.startAnimation(mAnimationSet3);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_word_every_day);
        initView();
        DisplayUtil.setTranslucentStatus(this);
        mNormal = (ImageView) findViewById(R.id.normal);
        mWave1 = (ImageView) findViewById(R.id.wave1);
        mWave2 = (ImageView) findViewById(R.id.wave2);
        mWave3 = (ImageView) findViewById(R.id.wave3);

        mAnimationSet1 = initAnimationSet();
        mAnimationSet2 = initAnimationSet();
        mAnimationSet3 = initAnimationSet();
        comback= (ImageButton) findViewById(R.id.comeback_from_word);
        comback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mNormal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showWaveAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        clearWaveAnimation();
                        flipCard();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        clearWaveAnimation();
                }
                return true;
            }
        });
        showWaveAnimation();
    }
    //水波动画初始化
    private AnimationSet initAnimationSet() {
        AnimationSet as = new AnimationSet(true);
        ScaleAnimation sa = new ScaleAnimation(1f, 2.3f, 1f, 2.3f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(OFFSET * 3);
        sa.setRepeatCount(Animation.INFINITE);// 设置循环
        AlphaAnimation aa = new AlphaAnimation(1, 0.1f);
        aa.setDuration(OFFSET * 3);
        aa.setRepeatCount(Animation.INFINITE);//设置循环
        as.addAnimation(sa);
        as.addAnimation(aa);
        return as;
    }
    //开始动画
    private void showWaveAnimation() {
        mWave1.startAnimation(mAnimationSet1);
        mHandler.sendEmptyMessageDelayed(MSG_WAVE2_ANIMATION, OFFSET);
        mHandler.sendEmptyMessageDelayed(MSG_WAVE3_ANIMATION, OFFSET * 2);
    }
    //清除动画
    private void clearWaveAnimation() {
        mWave1.clearAnimation();
        mWave2.clearAnimation();
        mWave3.clearAnimation();
    }
    private void initView() {
        mCardMainContainer = (FrameLayout) findViewById(R.id.card_main_container);
        mCardFontContainer = (LinearLayout) findViewById(R.id.card_font_container);
        mCardBackContainer = (RelativeLayout) findViewById(R.id.card_back_container);
        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
    }
    private void setAnimators() {
        mRightOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_right_out);
        mLeftInAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_left_in);
        // 设置点击事件
        mRightOutAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCardMainContainer.setClickable(false);
            }
        });

        mLeftInAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardMainContainer.setClickable(true);
            }
        });
    }

    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFontContainer.setCameraDistance(scale);
        mCardBackContainer.setCameraDistance(scale);
    }

    private void flipCard() {
        //只有正面朝上才会反转
        if (!mIsShowBack) {  // 正面朝上
            mRightOutAnimatorSet.setTarget(mCardFontContainer);
            mLeftInAnimatorSet.setTarget(mCardBackContainer);
            mRightOutAnimatorSet.start();
            mLeftInAnimatorSet.start();
            mIsShowBack = true;

        }
    }
}


