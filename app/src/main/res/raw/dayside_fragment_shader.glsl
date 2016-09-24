varying lowp vec4 colorVarying;
varying lowp vec4 specularColorVarying;

precision mediump float;

varying vec2 vTextureCoord;

uniform sampler2D sTexture;
uniform sampler2D sCloudTexture;

void main() {
    vec4 finalSpecular = vec4(0,0,0,1);

    vec4 cloudColor;
    vec4 surfaceColor;
    float halfBlue;

    cloudColor = texture2D(sCloudTexture, vTextureCoord);
    
    surfaceColor = texture2D(sTexture, vTextureCoord);

    halfBlue = 0.5 * surfaceColor[2];

    if(halfBlue > 1.0) {
        halfBlue = 1.0;
    }

    if((surfaceColor[0] < halfBlue) && (surfaceColor[1] < halfBlue)) {
        finalSpecular = specularColorVarying;
    }
                                                                     
    if(cloudColor[0] > 0.2) {
        cloudColor[3] = 1.0;
        gl_FragColor = (cloudColor * 1.3 + surfaceColor * .4) * colorVarying;
    }
    else {
	      gl_FragColor = (surfaceColor + finalSpecular) * colorVarying;
	  }
}
