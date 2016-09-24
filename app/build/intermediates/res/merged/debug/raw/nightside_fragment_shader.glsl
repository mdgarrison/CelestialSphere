varying lowp vec4 colorVarying;

precision mediump float;

varying vec2 vTextureCoord;

uniform sampler2D sTexture;
uniform sampler2D sCloudTexture;

void main() {
    vec4 newColor;
    vec4 cloudColor;
    vec4 surfaceColor;

    float cloudNightBrightness = 0.1;

    newColor = 1.0 - colorVarying;

    cloudColor = texture2D(sCloudTexture, vTextureCoord);

    surfaceColor = texture2D(sTexture, vTextureCoord);

    if(cloudColor[0] > 0.4) {
        cloudColor[3] = 1.0;

        gl_FragColor = cloudNightBrightness * cloudColor + 0.6 * surfaceColor * newColor;
    }
    else {
        gl_FragColor = surfaceColor * newColor;
    }
}
