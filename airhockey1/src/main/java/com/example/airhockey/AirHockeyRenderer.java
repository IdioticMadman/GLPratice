package com.example.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.airhockey.utils.LoggerConfig;
import com.example.airhockey.utils.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    public static final int POSITION_COMPONENT_COUNT = 2;
    public static final int BYTE_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    private final Context mContext;

    public static final String U_COLOR = "u_Color";
    private int uColorLocation;

    public static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private int mLinkProgramId;

    public AirHockeyRenderer(Context context) {
        this.mContext = context;
        //三角形都是逆时针的，卷曲顺序
        float[] tableVerticesWithTriangles = {
                // X, Y, R, G, B
                //triangles fan
                0.0f, 0.0f, 1f, 1f, 1f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

                // line1
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,

                // mallets
                0.0f, -0.25f, 0f, 0f, 1f,
                0.0f, 0.25f, 1f, 0f, 0f,
        };
        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTE_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // 获取shader source
        final String vertexShader = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.sample_vertex_shader);
        final String fragShader = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.sample_fragment_shader);
        // 编译shader
        final int vertexShaderId = ShaderHelper.compileVertexShader(vertexShader);
        final int fragShaderId = ShaderHelper.compileFragShader(fragShader);
        // 链接shader
        final int linkProgramId = ShaderHelper.linkProgram(vertexShaderId, fragShaderId);
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(linkProgramId);
        }
        // 使用program
        glUseProgram(linkProgramId);
        // 获取uniform 位置，
        uColorLocation = glGetUniformLocation(linkProgramId, U_COLOR);
        // 获取属性的位置
        aPositionLocation = glGetAttribLocation(linkProgramId, A_POSITION);
        // 重置 buffer的游标
        vertexData.position(0);
        // 告诉openGL 在缓冲区 vertexData中找a_Position的数据
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, 0, vertexData);
        // 启用数据
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置窗口大小
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        // 设置fragShader的color
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        // 画两个三角形-> 桌子
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // 设置fragShader的color
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        // 画直线 -> 球杆
        glDrawArrays(GL_LINES, 6, 2);

        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
