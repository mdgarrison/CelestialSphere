package com.mdgarrison.opengl.android.tutorials;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

public class Moon extends ModelObject {
    private int mDayTexture;
    private int mNightTexture;
    private int mCloudTexture;
    private float[] mLightPosition = new float[3];
    private float[] mEyePosition;
    private float mLunarAngle = 0.0f;
    private float mLunarOrbitalRadius = 5.0f;
    private float[] mLunarPos = {mLunarOrbitalRadius, 0f, mLunarOrbitalRadius};
	public final static int PLANET_BLEND_MODE_NONE = 1;
	public final static int PLANET_BLEND_MODE_ATMO = 2;
	public final static int PLANET_BLEND_MODE_SOLID = 3;
	public final static int PLANET_BLEND_MODE_FADE = 4;
	EarthDaysideShaderProgram dayShader;
	EarthNightsideShaderProgram nightShader;

	public Moon(Context context, SceneManager mgr) {
		super(context, mgr);
		initializeModel();
		mDrawMode = GLES20.GL_TRIANGLE_STRIP;
		mLightPosition[0] = 10.0f; 
		mLightPosition[1] = 2.0f;
		mLightPosition[2] = 10.0f;
	}

    protected void initializeModel() {
    	int m_Stacks = 50;
    	int m_Slices = 50;
    	float radius = 1.0f;
    	float m_Squash = 1.0f;
    	float m_Scale = radius;
    	
    	int vIndex =  0;
    	int cIndex =  4;
    	int nIndex =  8;
    	int tIndex = 11;
    	
    	int	phiIdx, thetaIdx;

        mVertexData = new float[((m_Slices * 2 + 2) * m_Stacks) * (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT + NORMAL_COMPONENT_COUNT + TEXTURE_COMPONENT_COUNT)];

    	for(phiIdx = 0; phiIdx < m_Stacks; phiIdx++)		    		
    	{
    		float phi0 = (float)Math.PI * ((float)(phiIdx+0) * (1.0f/(float)(m_Stacks)) - 0.5f);	
    		float phi1 = (float)Math.PI * ((float)(phiIdx+1) * (1.0f/(float)(m_Stacks)) - 0.5f);	

    		float cosPhi0 = (float)Math.cos(phi0);				
    		float sinPhi0 = (float)Math.sin(phi0);
    		float cosPhi1 = (float)Math.cos(phi1);
    		float sinPhi1 = (float)Math.sin(phi1);
    		
    		float cosTheta, sinTheta;
    		
    		for(thetaIdx=0; thetaIdx < m_Slices; thetaIdx++)			
    		{
    			float theta = (float) (2.0f*(float)Math.PI * ((float)thetaIdx) * (1.0/(float)(m_Slices-1)));			
    			cosTheta = (float)Math.cos(theta);		
    			sinTheta = (float)Math.sin(theta);
    				
    			mVertexData[vIndex +  0] = m_Scale * cosPhi0 * cosTheta;
    			mVertexData[vIndex +  1] = m_Scale * sinPhi0 * m_Squash; 
    			mVertexData[vIndex +  2] = m_Scale * cosPhi0 * sinTheta;
    			mVertexData[vIndex +  3] = 1.0f;
    				
    			mVertexData[vIndex + 13] = m_Scale * cosPhi1 * cosTheta;
    			mVertexData[vIndex + 14] = m_Scale * sinPhi1 * m_Squash; 
    			mVertexData[vIndex + 15] = m_Scale * cosPhi1 * sinTheta;
    			mVertexData[vIndex + 16] = 1.0f;

    			mVertexData[cIndex +  0] = 1.0f;		    				
    			mVertexData[cIndex +  1] = 1.0f;
    			mVertexData[cIndex +  2] = 1.0f;
    			mVertexData[cIndex +  3] = 0.0f;
    			
    			mVertexData[cIndex + 13] = 1.0f;
    			mVertexData[cIndex + 14] = 1.0f;
    			mVertexData[cIndex + 15] = 1.0f;
    			mVertexData[cIndex + 16] = 0.0f;
    			
    			mVertexData[nIndex +  0] = cosPhi0 * cosTheta; 
    			mVertexData[nIndex +  1] = sinPhi0;
    			mVertexData[nIndex +  2] = cosPhi0 * sinTheta; 
    		 	
    			mVertexData[nIndex + 13] = cosPhi1 * cosTheta; 
    			mVertexData[nIndex + 14] = sinPhi1;	
    			mVertexData[nIndex + 15] = cosPhi1 * sinTheta; 

   				float texX = (float)thetaIdx * (1.0f / (float)(m_Slices - 1));
   				
   				mVertexData[tIndex +  0] = texX; 
   				mVertexData[tIndex +  1] = (float)(phiIdx + 0) * (1.0f / (float)(m_Stacks));
   				
   				mVertexData[tIndex + 13] = texX; 
   				mVertexData[tIndex + 14] = (float)(phiIdx + 1) * (1.0f / (float)(m_Stacks));
    			
    			cIndex += 26;
    			vIndex += 26;
    			nIndex += 26;
   				tIndex += 26;
    			
   				mVertexData[vIndex +  0] = mVertexData[vIndex + 13] = mVertexData[vIndex - 13]; 
   				mVertexData[vIndex +  1] = mVertexData[vIndex + 14] = mVertexData[vIndex - 12]; 
   				mVertexData[vIndex +  2] = mVertexData[vIndex + 15] = mVertexData[vIndex - 11];
   				mVertexData[vIndex +  3] = mVertexData[vIndex + 16] = 1.0f;
			        			
   				mVertexData[nIndex +  0] = mVertexData[nIndex + 13] = mVertexData[nIndex - 13]; 
   				mVertexData[nIndex +  1] = mVertexData[nIndex + 14] = mVertexData[nIndex - 12]; 
   				mVertexData[nIndex +  2] = mVertexData[nIndex + 15] = mVertexData[nIndex - 11];
	
   				mVertexData[tIndex +  0] = mVertexData[tIndex + 13] = mVertexData[tIndex - 13]; 
   				mVertexData[tIndex +  1] = mVertexData[tIndex + 14] = mVertexData[tIndex - 12];
    		}			
    	}
        mNumVertices = ((m_Slices * 2 + 2) * (m_Stacks));
        
        mStride = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT + NORMAL_COMPONENT_COUNT + TEXTURE_COMPONENT_COUNT) * BYTES_PER_FLOAT;
        
        makeFloatBuffer();
        mNumVertices = ((m_Slices * 2 + 2) * (m_Stacks));
        mNumVerticesToDraw = mNumVertices - 100;
    }

	@Override
	public void onDrawFrame(float[] viewMat, float[] projMat) {
    	mLunarPos[0] = mLunarOrbitalRadius * (float) (Math.cos(mLunarAngle * Math.PI  / 180));
    	mLunarPos[1] = 0.0f;
    	mLunarPos[2] = mLunarOrbitalRadius * (float) (Math.sin(mLunarAngle * Math.PI  / 180));
    	mLunarAngle += 0.25f;
    	if (mLunarAngle == 360.0f) {
    		mLunarAngle = 0.0f;
    	}
    	Matrix.setIdentityM(mModelMatrix, 0);
   		Matrix.translateM(mModelMatrix, 0, mLunarPos[0], mLunarPos[1], mLunarPos[2]);
    	Matrix.rotateM(mModelMatrix, 0, -mLunarAngle, 0f, 1f, 0f);
    	Matrix.scaleM(mModelMatrix, 0, 0.27f, 0.27f, 0.27f);
    	

   		constructMvpMatrix(viewMat, projMat);

		mEyePosition = mManager.getEyePosition();
		//mLightPosition[0] = mEyePosition[0];
		//mLightPosition[1] = mEyePosition[1] + 2.0f;
		//mLightPosition[2] = mEyePosition[2];
		
		// Nightside
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,  mNightTexture);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,  mCloudTexture);

        //GLES20.glEnable(GLES20.GL_DEPTH_TEST);    	
		GLES20.glUseProgram(nightShader.getProgramOid());
		
        GLES20.glUniformMatrix4fv(nightShader.mUniformIds[nightShader.UNIFORM_MVP_MATRIX], 1, false, mMvpMatrix, 0);
        GLES20.glUniform3fv(nightShader.mUniformIds[nightShader.UNIFORM_LIGHT_POSITION], 1, mLightPosition,0);
        setBlendMode(PLANET_BLEND_MODE_SOLID);

        draw(nightShader.ATTRIB_VERTEX, nightShader.ATTRIB_NORMAL, 0, nightShader.ATTRIB_TEXTURE0_COORDS, mNightTexture);
        //GLES20.glDisable(GLES20.GL_DEPTH_TEST);    	

    	GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mDayTexture); 
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mCloudTexture); 
        
        //GLES20.glEnable(GLES20.GL_DEPTH_TEST);    	
        GLES20.glUseProgram(dayShader.getProgramOid());
        
        GLES20.glUniformMatrix4fv(dayShader.mUniformIds[dayShader.UNIFORM_MVP_MATRIX], 1, false, mMvpMatrix, 0);
        GLES20.glUniform3fv(dayShader.mUniformIds[dayShader.UNIFORM_LIGHT_POSITION], 1, mLightPosition,0);
        GLES20.glUniform3fv(dayShader.mUniformIds[dayShader.UNIFORM_EYE_POSITION], 1, mEyePosition,0);

        setBlendMode(PLANET_BLEND_MODE_FADE);
        draw(dayShader.ATTRIB_VERTEX, dayShader.ATTRIB_NORMAL, 0, dayShader.ATTRIB_TEXTURE0_COORDS, mDayTexture);
        //GLES20.glDisable(GLES20.GL_DEPTH_TEST);    	
	}

	@Override
    public void onSurfaceCreated(int vboIndex) {
        super.onSurfaceCreated(vboIndex);            
        mSecondaryShader.assembleShaderProgram();
        mPrimaryShader.assembleShaderProgram();
        
        dayShader = (EarthDaysideShaderProgram) mPrimaryShader;
        nightShader = (EarthNightsideShaderProgram) mSecondaryShader;
        
    	GLES20.glUseProgram(dayShader.getProgramOid());
        GLES20.glUniform1i(dayShader.mUniformIds[dayShader.UNIFORM_SAMPLER0], 0);
        GLES20.glUniform1i(dayShader.mUniformIds[dayShader.UNIFORM_SAMPLER1], 1);
        
    	GLES20.glUseProgram(nightShader.getProgramOid());
        GLES20.glUniform1i(nightShader.mUniformIds[nightShader.UNIFORM_SAMPLER0], 0);
        GLES20.glUniform1i(nightShader.mUniformIds[nightShader.UNIFORM_SAMPLER1], 1);

        initializeTextures();
    }

	private void initializeTextures() {
        mDayTexture   = createTexture(com.mdgarrison.opengl.android.R.drawable.moon);
        mNightTexture = createTexture(com.mdgarrison.opengl.android.R.drawable.moon);
        mCloudTexture = createTexture(com.mdgarrison.opengl.android.R.drawable.moon);
	}
	
	private void draw(int vertexLoc, int normalLoc, int colorLoc, int textureLoc, int textureId) {
		if (textureId >= 0) {
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,  textureId);
		}

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);

    	GLES20.glEnableVertexAttribArray(vertexLoc);
    	GLES20.glVertexAttribPointer(vertexLoc, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 0);

    	GLES20.glEnableVertexAttribArray(normalLoc);
    	GLES20.glVertexAttribPointer(normalLoc, NORMAL_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 32);
    	
    	GLES20.glEnableVertexAttribArray(textureLoc);
    	GLES20.glVertexAttribPointer(textureLoc, TEXTURE_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 44);

    	GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mNumVerticesToDraw);
    	//GLES20.glDisable(GLES20.GL_CULL_FACE);    	
	}
	
	private void drawVBO() {
		DefaultShaderProgram shader = (DefaultShaderProgram) mPrimaryShader;
		
		GLES20.glUseProgram(shader.getProgramOid());
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);    	
		
    	GLES20.glUniformMatrix4fv(shader.uMatrixLocation, 1, false, mMvpMatrix, 0);
    	GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboIndex);
    	GLES20.glEnableVertexAttribArray(shader.aPositionLocation);
    	GLES20.glVertexAttribPointer(shader.aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 0);
    	GLES20.glEnableVertexAttribArray(shader.aColorLocation);
    	GLES20.glVertexAttribPointer(shader.aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 0);
    	GLES20.glDrawArrays(mDrawMode, 0, mNumVerticesToDraw);
	}
	
	private int createTexture(int resource) {
		int[] textures = new int[1];
		
		Bitmap tempImage = BitmapFactory.decodeResource(mContext.getResources(), resource);

		GLES20.glGenTextures(1, textures, 0);

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,  0, tempImage, 0);

		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		return textures[0];
	}

    public void setBlendMode(int blendMode)
    {
    	GLES20.glEnable(GLES20.GL_BLEND);

        if(blendMode==PLANET_BLEND_MODE_NONE) {
        	GLES20.glDisable(GLES20.GL_BLEND); 
        }
        else if(blendMode==PLANET_BLEND_MODE_FADE) {
        	GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        }
        else if(blendMode==PLANET_BLEND_MODE_ATMO) {
        	GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);
        }
        else if(blendMode==PLANET_BLEND_MODE_SOLID) {
        	GLES20.glDisable(GLES20.GL_BLEND);    
        }
    }
}
