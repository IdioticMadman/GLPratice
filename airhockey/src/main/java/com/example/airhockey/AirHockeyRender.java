package com.example.airhockey;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AirHockeyRender implements GLSurfaceView.Renderer {
    public static final int POSITION_COMPONENT_COUNT = 2;
    public static final int BYTE_PER_FLOAT = 4;
    private final FloatBuffer vertexData;

    /**
     * 顶点数据
     */
    private float[] tableVertices = {
            0f, 0f,
            0f, 14f,
            9f, 14f,
            9f, 0f
    };

    public AirHockeyRender() {
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

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
