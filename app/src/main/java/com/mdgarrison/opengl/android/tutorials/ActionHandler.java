package com.mdgarrison.opengl.android.tutorials;

import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;

public class ActionHandler {

	public static PointF mMidPoint = new PointF();
	
	public static enum MoveEnum {
	   NONE,
	   DRAG,
	   PINCH,
	   PINCH_IN,
	   PINCH_OUT
	};
	
	public static PointF mOldDragLocation  = new PointF(0f, 0f);
	public static PointF mNewDragLocation  = new PointF(0f, 0f);

	public static float mOldPinchDistance = 0f;
	public static float mNewPinchDistance = 0f;
	
	public static float spacing(MotionEvent event) {
		if (event.getPointerCount() == 2) {
    		float x = event.getX(0) - event.getX(1);
	    	float y = event.getY(0) - event.getY(1);
		   return FloatMath.sqrt((x * x) + (y * y));
		}
		return Float.NaN;
	}
	
	public static void midPoint(MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		mMidPoint.set(x / 2.0f, y / 2.0f);
	}
}

