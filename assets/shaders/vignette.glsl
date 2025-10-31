#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 u_resolution;
uniform vec2 u_lightPos;
uniform vec2 u_borderSize;
uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoords;

const float outerRadius = 0.2;
const float innerRadius = 0.01;
const float intensity = 1.0;


void main() {
    float aspectRatio = u_resolution.x / u_resolution.y;

    vec4 color = texture2D(u_texture, v_texCoords) * v_color;

    // Correct light position for aspect ratio
    vec2 correctedLightPos = vec2(u_lightPos.x * aspectRatio, u_lightPos.y);

    // Change to 0-1 coords for fragment position
    vec2 decimalCoords = gl_FragCoord.xy / u_resolution;

    // Correct for resolution
    vec2 correctedCoords = vec2(decimalCoords.x * (aspectRatio), decimalCoords.y);

    // Calculate distance to centre
    float len = length(correctedCoords - correctedLightPos);

    // Set smoothness
    float vignette = smoothstep(outerRadius, innerRadius, len);

    // Apply the vignette
    color.rgb = mix(color.rgb, color.rgb * vignette, intensity);

    gl_FragColor = color;

}