package com.mdgarrison.opengl.android.util;

public final class Quaternion {
	public double x;
	public double y;
	public double z;
	public double w;
	public String TAG = "myActivity";

	public Quaternion(final Quaternion q) 
	{
		this(q.x, q.y, q.z, q.w);
	}

	public Quaternion(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void set(final Quaternion q) 
	{
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
	}

	public Quaternion(Vector3 axis, double angle) 
	{
		rotate(axis, angle);
	}

	public double norm() 
	{
		return Math.sqrt(dot(this));
	}

	public double getW() 
	{
		return w;
	}

	public double getX() 
	{
		return x;
	}

	public double getY() 
	{
		return y;
	}

	public double getZ() 
	{
		return z;
	}

	public Quaternion rotate(Vector3 axis, double angle) 
	{
		double s = (double) Math.sin(angle / 2);
		w = (double) Math.cos(angle / 2);
		x = axis.getX() * s;
		y = axis.getY() * s;
		z = axis.getZ() * s;

		return this;
	}

	public Quaternion mulThis(Quaternion q) 
	{
		double nw = w * q.w - x * q.x - y * q.y - z * q.z;
		double nx = w * q.x + x * q.w + y * q.z - z * q.y;
		double ny = w * q.y + y * q.w + z * q.x - x * q.z;
		z = w * q.z + z * q.w + x * q.y - y * q.x;
		w = nw;
		x = nx;
		y = ny;

		return this;
	}

	public Quaternion scaleThis(double scale)
	{
		if (scale != 1) 
		{
			w *= scale;
			x *= scale;
			y *= scale;
			z *= scale;
		}
		return this;
	}

	public Quaternion divThis(double scale) 
	{
		if (scale != 1) 
		{
			w /= scale;
			x /= scale;
			y /= scale;
			z /= scale;
		}
		return this;
	}

	public double dot(Quaternion q) 
	{
		return x * q.x + y * q.y + z * q.z + w * q.w;
	}

	public boolean equals(Quaternion q) 
	{
		return x == q.x && y == q.y && z == q.z && w == q.w;
	}

	public Quaternion interpolateThis(Quaternion q, double t) 
	{
		if (!equals(q)) 
		{
			double d = dot(q);
			double qx, qy, qz, qw;

			if (d < 0f) 
			{
				qx = -q.x;
				qy = -q.y;
				qz = -q.z;
				qw = -q.w;
				d = -d;
			} 
			else 
			{
				qx = q.x;
				qy = q.y;
				qz = q.z;
				qw = q.w;
			}

			double f0, f1;

			if ((1 - d) > 0.1f) 
			{
				double angle = (double) Math.acos(d);
				double s = (double) Math.sin(angle);
				double tAngle = t * angle;
				f0 = (double) Math.sin(angle - tAngle) / s;
				f1 = (double) Math.sin(tAngle) / s;
			} 
			else 
			{
				f0 = 1 - t;
				f1 = t;
			}

			x = f0 * x + f1 * qx;
			y = f0 * y + f1 * qy;
			z = f0 * z + f1 * qz;
			w = f0 * w + f1 * qw;
		}

		return this;
	}

	public Quaternion normalizeThis() 
	{
		return divThis(norm());
	}

	public Quaternion interpolate(Quaternion q, double t) 
	{
		return new Quaternion(this).interpolateThis(q, t);
	}

	public void fromMat4(float[] tMat) {
		double T = 1 + tMat[0] + tMat[5] + tMat[10];
		double S = 0;
		
		if (T > 0.00000001) {
			S = Math.sqrt(T) * 2;
			x = (tMat[9] - tMat[6]) / S;
			y = (tMat[2] - tMat[8]) / S;
			z = (tMat[4] - tMat[1]) / S;
			w = 0.25 * S;
		}
		else {
			if ((tMat[0] > tMat[5]) && (tMat[0] > tMat[10])) {
				S = Math.sqrt(1.0 + tMat[0] - tMat[5] - tMat[10]) * 2.0;
				x = 0.25 * S;
				y = (tMat[4] + tMat[1]) / S;
				z = (tMat[2] + tMat[8]) / S;
				w = (tMat[9] - tMat[6]) / S;
			}
			else if (tMat[5] > tMat[10]) {
				S = Math.sqrt(1.0 + tMat[5] - tMat[0] - tMat[10]) * 2.0;
				x = (tMat[4] + tMat[1]) / S;
				y = 0.25 * S;
				z = (tMat[6] + tMat[9]) / S;
				w = (tMat[2] - tMat[8]) / S;
			}
			else {
				S = Math.sqrt(1.0 + tMat[10] - tMat[0] - tMat[5]) * 2.0;
				x = (tMat[2] + tMat[8]) / S;
				y = (tMat[6] + tMat[9]) / S;
				z = 0.25 * S;
				w = (tMat[4] - tMat[1]) / S;
			}
		}
	}
	
	public float[] toMat4() {
		float[] tMat4 = new float[16];
		tMat4[ 0] = (float) (1.0f - (2.0f * ((y * y) + (z * z))));
		tMat4[ 1] = (float) (2.0f * ((x * y) - (z * w)));
		tMat4[ 2] = (float) (2.0f * ((x * z) + (y * w)));
		tMat4[ 3] = 0.0f;
		tMat4[ 4] = (float) (2.0f * ((x * y) + (z * w)));
		tMat4[ 5] = (float) (1.0f - (2.0f * ((x * x) + (z * z))));
		tMat4[ 6] = (float) (2.0f * ((y * z) - (x * w)));
		tMat4[ 7] = 0.0f;
		tMat4[ 8] = (float) (2.0f * ((x * z) - (y * w)));
		tMat4[ 9] = (float) (2.0f * ((y * z) + (x * w)));
		tMat4[10] = (float) (1.0f - (2.0f * ((x * x) + (y * y))));
		tMat4[11] = 0.0f;
		tMat4[12] = 0.0f;
		tMat4[13] = 0.0f;
		tMat4[14] = 0.0f;
		tMat4[15] = 1.0f;
		return tMat4;
	}
	
	public float[][] toMatrix() 
	{
		float matrixs[][] = new float[3][3];
		toMatrix(matrixs);
		return matrixs;
	}

	public final void toMatrix(float[][] matrixs) 
	{
		matrixs[0][0] = (float) (1.0f - (2.0f * ((y * y) + (z * z))));
		matrixs[0][1] = (float) (2.0f * ((x * y) - (z * w)));
		matrixs[0][2] = (float) (2.0f * ((x * z) + (y * w)));

		matrixs[1][0] = (float) (2.0f * ((x * y) + (z * w)));
		matrixs[1][1] = (float) (1.0f - (2.0f * ((x * x) + (z * z))));
		matrixs[1][2] = (float) (2.0f * ((y * z) - (x * w)));

		matrixs[2][0] = (float) (2.0f * ((x * z) - (y * w)));
		matrixs[2][1] = (float) (2.0f * ((y * z) + (x * w)));
		matrixs[2][2] = (float) (1.0f - (2.0f * ((x * x) + (y * y))));
	}

	public float[][] tranposeMatrix(float[][] matrix) 
	{
		float temp[][] = new float[3][3];
		 for (int i = 0; i < 3; i++)
	            for (int j = 0; j < 3; j++)
	                temp[j][i] = matrix[i][j];
	    
		return (temp);
	}

	public Quaternion QuaternionMakeWithMatrix3(float[][] matrix) 
	{
		int i=0,j=0,k=0;
		
		double trace=matrix[0][0] + matrix[1][1] + matrix[2][2];
		double root;
		
		if(trace>0.0)
		{
			root=Math.sqrt(trace+1);
			w=0.5*root;
			
			root=.5/root;
			
	        x = (matrix[2][1] - matrix[1][2]) * root;
	        y = (matrix[0][2] - matrix[2][0]) * root;
	        z = (matrix[1][0] - matrix[0][1]) * root;				
		}
		else
		{
	        double xyz[]=new double[3];
	        
	        if (matrix[1][1] > matrix[i][i])
	            i = 1;
	        
	        if (matrix[2][2] > matrix[i][i])
	            i = 2;
	        
	         j = (i == 2) ? 0 : i + 1;
	         k = (j == 2) ? 0 : j + 1;

	         root = (double) Math.sqrt(matrix[i][i] - matrix[j][j] - matrix[k][k] + 1.0);
	        
	        
	         xyz[i] = (double) 0.5 * root;
	        
			 if(root!=0.0)
				 root = (double) 0.5 / root;

	        w = (matrix[k][j] - matrix[j][k]) * root;				

	        xyz[j] = (matrix[j][i] + matrix[i][j]) * root;
	        xyz[k] = (matrix[k][i] + matrix[i][k]) * root;
	        
	        x=xyz[0];
	        y=xyz[1];
	        z=xyz[2];			
		}
		
		return this;	
	}

	public Vector3 QuaternionAxis() 
	{
	    double magSquared = x * x + y * y + z * z;
		Vector3 temp = new Vector3(0, 0, 0);
		if (magSquared > (double) 1e-10)
	    {
	    	double s =  (double) 1 / (double) Math.sqrt(magSquared);
	        temp.x = x * s;
	        temp.y = y * s;
	        temp.z = z * s;
	        
	        if (w <= -1 || w >= 1) {
			} else {
			}
	    }
	    else
	    {
	        // The angle is zero, so we pick an arbitrary unit axis
	    	temp.x = 1;
	    	temp.y = 0;
	    	temp.z = 0;
	    }

		return temp;
	}

	public float QuaternionAngle() 
	{
	    double magSquared = x * x + y * y + z * z;
		double angle;
		
	    if (magSquared > (double) 1e-10)
	    {        
	        if (w <= -1 || w >= 1)
	            angle = 0;
	        else
	            angle = (double) Math.acos(w) * 2;
	    }
	    else
	    {

	        angle = 0;
	    }
	    
		return (float)angle;
	}

	public Vector3 Matrix3MultiplyVector3(float[][] matrix3, Vector3 offsetv) 
	{
		Vector3 temp = new Vector3(0, 0, 0);
		temp.x = matrix3[0][0]*offsetv.x + matrix3[0][1]*offsetv.y + matrix3[0][2]*offsetv.z;
		temp.y = matrix3[1][0]*offsetv.x + matrix3[1][1]*offsetv.y + matrix3[1][2]*offsetv.z;
		temp.z = matrix3[2][0]*offsetv.x + matrix3[2][1]*offsetv.y + matrix3[2][2]*offsetv.z;
		return temp;
	}
}