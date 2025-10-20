#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 u_resolution;
uniform vec2 u_lightPos;    // 👈 new: torch center
uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoords;

const float outerRadius = 0.3;
const float innerRadius = 0.01;
const float intensity = 1.0;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords) * v_color;

    // Shader needs 0-1 coords
    vec2 uv = gl_FragCoord.xy / u_resolution;

    // Calculate distance to centre
    float len = length(uv - u_lightPos);

    // Set vignette smoothness
    float vignette = smoothstep(outerRadius, innerRadius, len);

    // Darken the image according to the sahder
    color.rgb = mix(color.rgb, color.rgb * vignette, intensity);
    

    gl_FragColor = color;
}
