package com.example.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRender implements GLSurfaceView.Renderer {
    public static final int POSITION_COMPONENT_COUNT = 2;
    public static final int BYTE_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    private final Context mContext;

    /**
     * 顶点数据
     */
    private float[] tableVertices = {
            0f, 0f,
            0f, 14f,
            9f, 14f,
            9f, 0f
    };

    public AirHockeyRender(Context context) {
        this.mContext = context;
        //三角形都是逆时针的，卷曲顺序
        float[] tableVerticesWithTriangles = {
                //triangles 1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                //triangles 2
                0f, 0f,
                9f, 0f,
                9f, 14f,
                // line1
                0f, 7f,
                9f, 7f,
                // mallets
                4.5f, 2f,
                4.5f, 12f
        };
        vertexData = ByteBuffer
                .allocate(tableVerticesWithTriangles.length * BYTE_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClear(GL_COLOR_BUFFER_BIT);
        String vertexShader = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.sample_vertex_shader);
        String fragShader = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.fragment_shader);

        
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }
}
