package com.mdgarrison.opengl.android.tutorials;

import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;

public class LessonView extends GLSurfaceView {
    private Context mContext;
    private LessonRenderer mRenderer;
	PointF mTouchPointActionDown = new PointF();
	PointF mTouchPointActionMove = new PointF();
	PointF mTouchPointActionUp   = new PointF();
	float angleMod = 0.0f;
	volatile float mPreviousX = 0f;
	volatile float mPreviousY = 0f;
	float mDensity = 0;
	float mCamDistance = 3000.0f;
    
	public LessonView(Context context) 
	{
		super(context);
		mContext = context;
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		Activity activity = (Activity) mContext;
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		mDensity = displayMetrics.density;
		
		mRenderer = new LessonRenderer(context);	

		setEGLContextClientVersion(2);
		setEGLConfigChooser(8, 8, 8, 8, 16, 1);
		
		getHolder().setFormat(PixelFormat.RGBA_8888);

		setRenderer(mRenderer);
	}

	public LessonView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		mContext = context;
		
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		Activity activity = (Activity) mContext;
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		mDensity = displayMetrics.density;
		
		mRenderer = new LessonRenderer(context);	

		setEGLContextClientVersion(2);
		setEGLConfigChooser(8, 8, 8, 8, 16, 1);
		
		getHolder().setFormat(PixelFormat.RGBA_8888);

		setRenderer(mRenderer);
	}
	
    public boolean getToggleSphereVisible() {
    	return mRenderer.getToggleSphereVisible();
    }
    
	public boolean toggleSphereVisible() {
		return mRenderer.toggleSphereVisible();
	}

	public int getToggleMagnitude() {
		return mRenderer.getToggleMagnitude();
	}
	
	public int toggleMagnitude() {
		return mRenderer.toggleMagnitude();
	}

	public void toggleDebugger() {
		mRenderer.toggleDebugger();
	}

	public boolean getToggleLockX() {
		return mRenderer.getToggleLockX();
	}
	
	public boolean toggleLockX() {
		return mRenderer.toggleLockX();
	}

	public boolean getToggleLockY() {
		return mRenderer.getToggleLockY();
	}
	
	public boolean toggleLockY() {
		return mRenderer.toggleLockY();
	}
	
	public boolean getToggleRefs() {
		return mRenderer.getToggleRefs();
	}
	
	public boolean toggleRefs() {
		return mRenderer.toggleRefs();
	}
	
	public boolean getToggleSwap() {
		return mRenderer.getToggleSwap();
	}
	
	public boolean toggleSwap() {
		return mRenderer.toggleSwap();
	}
	
	public boolean getTogglePov() {
		return mRenderer.getTogglePov();
	}
	
	public boolean togglePov() {
		return mRenderer.togglePov();
	}

	public void doReset() {
		mRenderer.doReset();
	}
	
	public int getToggleStep() {
		return mRenderer.getToggleStep();
	}
	
	public int toggleStep() {
		return mRenderer.toggleStep();
	}
	
	public boolean getToggleProjection() {
		return mRenderer.getToggleProjection();
	}
	
	public boolean toggleProjection() {
		return mRenderer.toggleProjection();
	}
	
	public boolean getToggleGridRefsVisible() {
		return mRenderer.getToggleGridRefsVisible();
	}
	
	public boolean toggleGridRefsVisible() {
		return mRenderer.toggleGridRefsVisible();
	}
	
	public String getToggleActiveCamera() {
		return mRenderer.getToggleActiveCamera();
	}
	
	public String toggleActiveCamera() {
		return mRenderer.toggleActiveCamera();
	}

    public boolean getTogglePlanetRotation() {
    	return mRenderer.getTogglePlanetRotation();
    }

    public void setTogglePlanetRotation(boolean val) {
    	mRenderer.setTogglePlanetRotation(val);
    }
    
    public boolean togglePlanetRotation() {
    	return mRenderer.togglePlanetRotation();
    }
    
    public boolean getToggleLightRotation() {
    	return mRenderer.getToggleLightRotation();
    }
    
    public void setToggleLightRotation(boolean val) {
    	mRenderer.setToggleLightRotation(val);
    }
    
    public boolean toggleLightRotation() {
    	return mRenderer.toggleLightRotation();
    }
    
    public boolean getToggleCameraRotation() {
    	return mRenderer.getToggleCameraRotation();
    }

    public void setToggleCameraRotation(boolean val) {
    	mRenderer.setToggleCameraRotation(val);
    }

    public boolean toggleCameraRotation() {
    	return mRenderer.toggleCameraRotation();
    }
}


