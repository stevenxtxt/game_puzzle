/*
 * Copyright (C), 2014-2015, �������������������޹�˾
 * FileName: ImageSplitterUtil.java
 * Author:   xutework
 * Date:     2015-3-16 ����4:46:13
 * Description: //ģ��Ŀ�ġ���������      
 * History: //�޸ļ�¼
 * <author>      <time>      <version>    <desc>
 * �޸�������             �޸�ʱ��            �汾��                  ����
 */
package com.xute.game.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

/**
 * ��һ�仰���ܼ�����<br> 
 * ��������ϸ������
 *
 * @author xutework
 * @see [�����/����]����ѡ��
 * @since [��Ʒ/ģ��汾] ����ѡ��
 */
public class ImageSplitterUtil {
    
    /**
     * 
     * ��������: <br>
     * ��������ϸ������
     *
     * @param bitmap
     * @param piece
     * @return
     * @see [�����/����](��ѡ)
     * @since [��Ʒ/ģ��汾](��ѡ)
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
