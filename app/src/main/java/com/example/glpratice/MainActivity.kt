package com.example.glpratice

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {

    lateinit var mGlSurfaceView: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val glSurfaceView = GLSurfaceView(this)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(FirstGlRender())
        mGlSurfaceView = glSurfaceView
        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        mGlSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mGlSurfaceView.onResume()
    }


    class FirstGlRender : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10?) {
            glClear(GL_COLOR_BUFFER_BIT)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            glViewport(0, 0, width, height)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        }

    }
}
