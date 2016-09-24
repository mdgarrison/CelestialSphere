package com.mdgarrison.opengl.android.tutorials;

import com.mdgarrison.opengl.android.R;
import com.mdgarrison.opengl.android.util.TextResourceReader;

import android.content.Context;
import android.opengl.GLES20;

public class EarthDaysideShaderProgram extends ShaderProgram {
	public final int[] mUniformIds = new int[10];
    
    public final int UNIFORM_MVP_MATRIX     = 0;
    public final int UNIFORM_LIGHT_POSITION = 1;
    public final int UNIFORM_EYE_POSITION   = 2;
    public final int UNIFORM_SAMPLER0       = 3;
    public final int UNIFORM_SAMPLER1       = 4;
    public final int UNIFORM_MV_MATRIX      = 5;

    public final int ATTRIB_VERTEX          = 1;
    public final int ATTRIB_NORMAL          = 2;
    public final int ATTRIB_TEXTURE0_COORDS = 3;
    
    public EarthDaysideShaderProgram(Context context) {
		super(context);
	}
	
    @Override
	public int assembleShaderProgram() {
    	int progId = 0;

    	String vertShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.dayside_vertex_shader);
        String fragShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.dayside_fragment_shader);

        int vertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertShader,  vertShaderSource);
        GLES20.glCompileShader(vertShader);
        int[] vertCompiled = new int[1];
        GLES20.glGetShaderiv(vertShader, GLES20.GL_COMPILE_STATUS, vertCompiled, 0);

        int fragShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragShader,  fragShaderSource);
        GLES20.glCompileShader(fragShader);
        int[] fragCompiled = new int[1];
        GLES20.glGetShaderiv(vertShader, GLES20.GL_COMPILE_STATUS, fragCompiled, 0);
        
        progId = GLES20.glCreateProgram();

        GLES20.glAttachShader(progId, vertShader);
        
        GLES20.glAttachShader(progId, fragShader);
        
  	    GLES20.glBindAttribLocation(progId, ATTRIB_VERTEX, "aPosition");
  	    GLES20.glBindAttribLocation(progId, ATTRIB_NORMAL, "aNormal");
  	    GLES20.glBindAttribLocation(progId, ATTRIB_TEXTURE0_COORDS, "aTextureCoord");

        GLES20.glLinkProgram(progId);

        mUniformIds[UNIFORM_MVP_MATRIX]     = GLES20.glGetUniformLocation(progId, "uMVPMatrix");
        mUniformIds[UNIFORM_EYE_POSITION]   = GLES20.glGetUniformLocation(progId, "uEyePosition");
        mUniformIds[UNIFORM_LIGHT_POSITION] = GLES20.glGetUniformLocation(progId, "uLightPosition");
        mUniformIds[UNIFORM_SAMPLER0]       = GLES20.glGetUniformLocation(progId, "sTexture");
        mUniformIds[UNIFORM_SAMPLER1]       = GLES20.glGetUniformLocation(progId, "sCloudTexture");
        mUniformIds[UNIFORM_MV_MATRIX]      = GLES20.glGetUniformLocation(progId, "uMVMatrix");
        mProgramOid = progId;
        return progId;
	}

	@Override
	public void onSurfaceCreated() {
	}
}
