attribute vec4 aPosition;
attribute vec3 aNormal;
attribute vec2 aTextureCoord;

varying vec2 vTextureCoord;
varying lowp vec4 colorVarying;
varying lowp vec4 specularColorVarying;

uniform vec3 uLightPosition;
uniform vec3 uEyePosition;
uniform mat4 uMVPMatrix;
uniform mat4 uMVMatrix;

void main() {
    float shininess=25.0;
    float balance=.75;
    float specular=0.0;

    vTextureCoord = aTextureCoord;

    vec3 modelViewVertex = vec3(uMVMatrix * aPosition);
    vec3 modelViewNormal = vec3(uMVMatrix * vec4(aNormal, 0.0));
    vec3 lightVector     = normalize(uLightPosition - modelViewVertex);

    vec3 eyeNormal = normalize(uEyePosition);

    vec4 diffuseColor = vec4(1.0, 1.0, 1.0, 1.0);

    float nDotVP = max(0.0, dot(modelViewNormal, lightVector));
    
    float nDotVPReflection = dot(reflect(-lightVector, modelViewNormal), eyeNormal);
    
    specular = pow(max(0.0, nDotVPReflection), shininess) * balance;

    specularColorVarying=vec4(specular, specular, specular, 0.0);

    colorVarying = diffuseColor * nDotVP * 1.3;
    
    gl_Position = uMVPMatrix * aPosition;
}
