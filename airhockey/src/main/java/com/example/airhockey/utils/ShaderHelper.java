package com.example.airhockey.utils;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        // 创建shader引用
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            if (LogConfig.ON) {
                Log.w(TAG, "compileShader: Could not create shader");
            }
            return 0;
        }
        // 设置shader source
        glShaderSource(shaderObjectId, shaderCode);
        // 编译shader
        glCompileShader(shaderObjectId);
        final int[] compileStatus = new int[1];
        // 获取编译shader的状态
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (LogConfig.ON) {
            // 打印shader编译日志
            String info = glGetShaderInfoLog(shaderObjectId);
            Log.i(TAG, "Results of compiling source:  \n" + shaderCode + "\n: " + info);
        }
        if (compileStatus[0] == 0) {
            //编译失败，删除shader
            glDeleteShader(shaderObjectId);
            if (LogConfig.ON) {
                Log.w(TAG, "compileShader failed, delete");
            }
            return 0;
        }
        // 编译成功返回shader进行使用
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragShaderId) {
        // 创建program对象
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            if (LogConfig.ON) {
                Log.w(TAG, "createProgram failed: ");
            }
            return 0;
        }
        // 将顶点着色器和片元着色器依附到 program上
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragShaderId);
        // 链接着色器
        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        // 获取连接器的状态
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (LogConfig.ON) {
            Log.i(TAG, "Results of linking Program: \n" + glGetProgramInfoLog(programObjectId));
        }
        if (linkStatus[0] == 0) {
            // 如果链接失败，删除program
            glDeleteProgram(programObjectId);
            if (LogConfig.ON) {
                Log.w(TAG, "linking of program failed! ");
            }
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.i(TAG, "Results of validate Program: " + validateStatus[0] + "\n Log: "
                + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }
}
