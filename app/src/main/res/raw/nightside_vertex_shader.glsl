attribute vec4 aPosition;
attribute vec3 aNormal;
attribute vec2 aTextureCoord;

varying vec2 vTextureCoord;
varying lowp vec4 colorVarying;

uniform vec3 uLightPosition;
uniform mat4 uMVPMatrix;
uniform mat4 uMVMatrix;

void main() {
    vTextureCoord = aTextureCoord;    
    vec3 modelViewVertex = vec3(uMVMatrix * aPosition);
    vec3 modelViewNormal = vec3(uMVMatrix * vec4(aNormal, 0.0));
    vec3 lightVector     = normalize(uLightPosition - modelViewVertex);
    float nDotVP         = max(0.0, dot(modelViewNormal, lightVector));

    vec4 diffuseColor = vec4(1.0, 1.0, 1.0, 1.0);
    
    colorVarying = diffuseColor * nDotVP;
    
    gl_Position = uMVPMatrix * aPosition;
}
