attribute vec4 a_Position;
attribute vec4 a_Color;

varying vec4 v_color;

void main(){
    v_color = a_Color;
    gl_Position = a_Position;
    gl_PointSize = 10.0;
}