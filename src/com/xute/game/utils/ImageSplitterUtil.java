/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: ImageSplitterUtil.java
 * Author:   xutework
 * Date:     2015-3-16 下午4:46:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xute.game.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ImageSplitterUtil {
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param bitmap
     * @param piece
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<ImagePiece> splitImage(Bitmap bitmap, int piece) {
        List<ImagePiece> imagePieces = new ArrayList<ImagePiece>();
        
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = Math.min(width, height) / piece;
        
        for (int i = 0; i < piece; i++) {
            for (int j = 0; j < piece; j++) {
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.setIndex(j + i * piece);
                
                int x = j * pieceWidth;
                int y = i * pieceWidth;
                imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, pieceWidth, pieceWidth));
                imagePieces.add(imagePiece);
            }
        }
        
        return imagePieces;
    }

}
