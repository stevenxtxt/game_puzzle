/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: GamePuzzleLayout.java
 * Author:   xutework
 * Date:     2015-3-19 下午4:53:19
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xute.game_puzzle.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xute.game.utils.ImagePiece;
import com.xute.game.utils.ImageSplitterUtil;
import com.xute.game_puzzle.R;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class GamePuzzleLayout extends RelativeLayout implements OnClickListener {

    private int mColumn = 3;
    private int mPadding;
    private int mMargin = 3;

    private ImageView[] mGamePuzzleItems;

    private int mItemWidth;

    private int mWidth;

    private Bitmap mBitmap;

    private List<ImagePiece> mItemBitmaps;

    private boolean once;

    private ImageView mFirst;
    private ImageView mSecond;

    private RelativeLayout mAnimLayout;
    private boolean isAniming;

    private boolean isGameSuccess;
    private boolean isGameOver;
    private boolean isPause;

    private int mLevel = 1;

    public interface GamePuzzleListener {
        void nextlevel(int nextLevel);

        void timechanged(int currentTime);

        void gameover();
    }

    private Handler mHandler = new Handler() {

        /*
         * (non-Javadoc)
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NEXT_LEVEL:
                    mLevel = mLevel + 1;
                    if (mListener != null) {
                        mListener.nextlevel(mLevel);
                    } else {
                        nextLevel();
                    }
                    break;
                case TIME_CHANGED:
                    if (isGameOver || isGameSuccess || isPause) {
                        return;
                    }
                    if (mListener != null) {
                        mListener.timechanged(mTime);
                        if (mTime == 0) {
                            isGameOver = true;
                            mListener.gameover();
                            return;
                        }
                    }
                    mTime--;
                    mHandler.sendEmptyMessageDelayed(TIME_CHANGED, 1000);
                    break;
                default:
                    break;
            }
        }

    };

    private GamePuzzleListener mListener;

    public static final int NEXT_LEVEL = 0x110;
    public static final int TIME_CHANGED = 0x111;

    private boolean isTimeEnabled = false;
    private int mTime;

    /**
     * @param isTimeEnabled the isTimeEnabled to set
     */
    public void setTimeEnabled(boolean isTimeEnabled) {
        this.isTimeEnabled = isTimeEnabled;
    }

    /**
     * @param context
     */
    public GamePuzzleLayout(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public GamePuzzleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public GamePuzzleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void init() {
        // TODO Auto-generated method stub
        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

        mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
    }

    /*
     * (non-Javadoc)
     * @see android.widget.RelativeLayout#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());

        if (!once) {
            initBitmap();

            initItem();
            
            checkTimeEnabled();

            once = true;
        }
        setMeasuredDimension(mWidth, mWidth);
    }

    /**
     * 功能描述: 判断是否开启时间
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void checkTimeEnabled() {
        // TODO Auto-generated method stub
        if (isTimeEnabled) {
            countTimeBaseLevel();
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    /**
     * 功能描述: 根据当前等级设置时间
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void countTimeBaseLevel() {
        // TODO Auto-generated method stub
        mTime = (int) (Math.pow(2, mLevel) * 60);
    }

    /**
     * 功能描述: 进行切图，以及排序 〈功能详细描述〉
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void initBitmap() {
        // TODO Auto-generated method stub
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        }

        mItemBitmaps = ImageSplitterUtil.splitImage(mBitmap, mColumn);

        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {

            @Override
            public int compare(ImagePiece lhs, ImagePiece rhs) {
                // TODO Auto-generated method stub
                return Math.random() > 0.5 ? 1 : -1;
            }
        });

    }

    /**
     * 功能描述: 设置imageview(item)的宽高等属性 〈功能详细描述〉
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void initItem() {
        // TODO Auto-generated method stub
        mItemWidth = (mWidth - mPadding * 2 - mMargin * (mColumn - 1)) / mColumn;
        mGamePuzzleItems = new ImageView[mColumn * mColumn];

        for (int i = 0; i < mGamePuzzleItems.length; i++) {
            ImageView item = new ImageView(getContext());
            item.setOnClickListener(this);

            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());

            mGamePuzzleItems[i] = item;
            item.setId(i + 1);

            item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);

            if ((i + 1) % mColumn != 0) {
                lp.rightMargin = mMargin;
            }

            if (i % mColumn != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF, mGamePuzzleItems[i - 1].getId());
            }

            if ((i + 1) > mColumn) {
                lp.topMargin = mMargin;
                lp.addRule(RelativeLayout.BELOW, mGamePuzzleItems[i - mColumn].getId());
            }

            addView(item, lp);
        }
    }

    public void restart() {
        isGameOver = false;
        mColumn--;
        nextLevel();
    }
    
    public void pause() {
        isPause = true;
        mHandler.removeMessages(TIME_CHANGED);
    }
    
    public void resume() {
        if (isPause) {
            isPause = false;
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }
    
    public void nextLevel() {
        this.removeAllViews();
        mAnimLayout = null;
        mColumn++;
        isGameSuccess = false;
        checkTimeEnabled();
        initBitmap();
        initItem();
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private int min(int... params) {
        // TODO Auto-generated method stub
        int min = params[0];
        for (int param : params) {
            if (param < min) {
                min = param;
            }
        }
        return min;
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (isAniming) {
            return;
        }
        if (mFirst == v) {
            mFirst.setColorFilter(null);
            mFirst = null;
            return;
        }

        if (mFirst == null) {
            mFirst = (ImageView) v;
            mFirst.setColorFilter(Color.parseColor("#55ff0000"));
        } else {
            mSecond = (ImageView) v;

            exchangeView();
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void exchangeView() {
        // TODO Auto-generated method stub
        mFirst.setColorFilter(null);

        setUpAnimLayout();

        ImageView first = new ImageView(getContext());
        final Bitmap firstBm = mItemBitmaps.get(getImageIdByTag((String) mFirst.getTag())).getBitmap();
        first.setImageBitmap(firstBm);

        LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
        lp.leftMargin = mFirst.getLeft() - mPadding;
        lp.topMargin = mFirst.getTop() - mPadding;
        first.setLayoutParams(lp);
        mAnimLayout.addView(first);

        ImageView second = new ImageView(getContext());
        final Bitmap secondBm = mItemBitmaps.get(getImageIdByTag((String) mSecond.getTag())).getBitmap();
        second.setImageBitmap(secondBm);

        LayoutParams lp2 = new LayoutParams(mItemWidth, mItemWidth);
        lp2.leftMargin = mSecond.getLeft() - mPadding;
        lp2.topMargin = mSecond.getTop() - mPadding;
        second.setLayoutParams(lp2);
        mAnimLayout.addView(second);

        TranslateAnimation anim = new TranslateAnimation(0, mSecond.getLeft() - mFirst.getLeft(), 0, mSecond.getTop()
                - mFirst.getTop());
        anim.setDuration(300);
        anim.setFillAfter(true);
        first.startAnimation(anim);

        TranslateAnimation animSecond = new TranslateAnimation(0, -mSecond.getLeft() + mFirst.getLeft(), 0,
                -mSecond.getTop() + mFirst.getTop());
        animSecond.setDuration(300);
        animSecond.setFillAfter(true);
        second.startAnimation(animSecond);

        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                mFirst.setVisibility(View.INVISIBLE);
                mSecond.setVisibility(View.INVISIBLE);
                isAniming = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                String firstTag = (String) mFirst.getTag();
                String secondTag = (String) mSecond.getTag();

                mSecond.setImageBitmap(firstBm);
                mFirst.setImageBitmap(secondBm);

                mFirst.setTag(secondTag);
                mSecond.setTag(firstTag);

                mFirst.setVisibility(View.VISIBLE);
                mSecond.setVisibility(View.VISIBLE);
                mFirst = mSecond = null;
                mAnimLayout.removeAllViews();

                checkSuccess();

                isAniming = false;
            }
        });

    }

    /**
     * 功能描述: 判断用户游戏是否成功 〈功能详细描述〉
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected void checkSuccess() {
        // TODO Auto-generated method stub
        boolean isSuccess = true;
        for (int i = 0; i < mGamePuzzleItems.length; i++) {
            ImageView imageView = mGamePuzzleItems[i];
            if (getImageIndex((String) imageView.getTag()) != i) {
                isSuccess = false;
            }

        }
        if (isSuccess) {
            isGameSuccess = true;
            mHandler.removeMessages(TIME_CHANGED);
            Toast.makeText(getContext(), "Success, level up!", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(NEXT_LEVEL);
        }
    }

    public int getImageIdByTag(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[0]);
    }

    public int getImageIndex(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }

    /**
     * 功能描述: 构造动画层 〈功能详细描述〉
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void setUpAnimLayout() {
        // TODO Auto-generated method stub
        if (mAnimLayout == null) {
            mAnimLayout = new RelativeLayout(getContext());
            addView(mAnimLayout);
        }
    }

    /**
     * 
     * 功能描述: 设置接口回调 〈功能详细描述〉
     * 
     * @param mListener
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setGamePuzzleListener(GamePuzzleListener mListener) {
        this.mListener = mListener;
    }
}
