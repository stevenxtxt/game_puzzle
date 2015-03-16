/*
 * Copyright (C), 2014-2015, �������������������޹�˾
 * FileName: ImagePiece.java
 * Author:   xutework
 * Date:     2015-3-16 ����4:41:12
 * Description: //ģ��Ŀ�ġ���������      
 * History: //�޸ļ�¼
 * <author>      <time>      <version>    <desc>
 * �޸�������             �޸�ʱ��            �汾��                  ����
 */
package com.xute.game.utils;

import android.graphics.Bitmap;

/**
 * ��һ�仰���ܼ�����<br>
 * ��������ϸ������
 * 
 * @author xutework
 * @see [�����/����]����ѡ��
 * @since [��Ʒ/ģ��汾] ����ѡ��
 */
public class ImagePiece {

    private int index;
    private Bitmap bitmap;

    public ImagePiece() {

    }

    public ImagePiece(int index, Bitmap bitmap) {
        this.index = index;
        this.bitmap = bitmap;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * @param bitmap the bitmap to set
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ImagePiece [index=" + index + ", bitmap=" + bitmap + "]";
    }

}
