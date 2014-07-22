package com.netease.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

public class RoundImageUtil {

	public static Bitmap resizeImage(Bitmap originalBitmap, int newWidth, int newHeight){
		int width = originalBitmap.getWidth();
		int height = originalBitmap.getHeight();
		float scanleWidth = (float)newWidth/width;
		float scanleHeight = (float)newHeight/height;
		Matrix matrix = new Matrix();
		matrix.postScale(scanleWidth,scanleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap,0,0,width,height,matrix,true);
		return resizedBitmap;
	}
	
	public static Bitmap toRoundCorner(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getHeight(),bitmap.getWidth(),Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        RectF rectF = new RectF(rect);
        
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect,rectF, paint);
        return output;
    } 
}
