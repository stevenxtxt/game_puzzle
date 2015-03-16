/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: ImagePiece.java
 * Author:   xutework
 * Date:     2015-3-16 下午4:41:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xute.game.utils;

import android.graphics.Bitmap;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
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
