/*
 * Copyright (C), 2014-2015, �������������������޹�˾
 * FileName: GamePuzzleLayout.java
 * Author:   xutework
 * Date:     2015-3-19 ����4:53:19
 * Description: //ģ��Ŀ�ġ���������      
 * History: //�޸ļ�¼
 * <author>      <time>      <version>    <desc>
 * �޸�������             �޸�ʱ��            �汾��                  ����
 */
package com.xute.game_puzzle.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xute.game.utils.ImagePiece;
import com.xute.game.utils.ImageSplitterUtil;
import com.xute.game_puzzle.R;

/**
 * ��һ�仰���ܼ�����<br>
 * ��������ϸ������
 * 
 * @author xutework
 * @see [�����/����]����ѡ��
 * @since [��Ʒ/ģ��汾] ����ѡ��
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
     * ��������: <br>
     * ��������ϸ������
     * 
     * @see [�����/����](��ѡ)
     * @since [��Ʒ/ģ��汾](��ѡ)
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

            once = true;
        }
        setMeasuredDimension(mWidth, mWidth);
    }

    /**
     * ��������: ������ͼ���Լ�����
     * ��������ϸ������
     * 
     * @see [�����/����](��ѡ)
     * @since [��Ʒ/ģ��汾](��ѡ)
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
     * ��������: ����imageview(item)�Ŀ�ߵ�����
     * ��������ϸ������
     * 
     * @see [�����/����](��ѡ)
     * @since [��Ʒ/ģ��汾](��ѡ)
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

    /**
     * ��������: <br>
     * ��������ϸ������
     * 
     * @return
     * @see [�����/����](��ѡ)
     * @since [��Ʒ/ģ��汾](��ѡ)
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

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
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
     * ��������: <br>
     * ��������ϸ������
     *
     * @see [�����/����](��ѡ)
     * @since [��Ʒ/ģ��汾](��ѡ)
     */
    private void exchangeView() {
        // TODO Auto-generated method stub
        mFirst.setColorFilter(null);
        
        String firstTag = (String) mFirst.getTag();
        String secondTag = (String) mSecond.getTag();
        
        String[] firstParams = firstTag.split("_");
        String[] secondParams = secondTag.split("_");
        
        Bitmap firstBm = mItemBitmaps.get(Integer.parseInt(firstParams[0])).getBitmap();
        mSecond.setImageBitmap(firstBm);
        Bitmap secondBm = mItemBitmaps.get(Integer.parseInt(secondParams[0])).getBitmap();
        mFirst.setImageBitmap(secondBm);
        
        mFirst.setTag(secondTag);
        mSecond.setTag(firstTag);
        
        mFirst = mSecond = null;
    }
}
