package com.tencent.matrix.openglleak.detector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.tencent.matrix.openglleak.comm.FuncNameString;
import com.tencent.matrix.openglleak.hook.OpenGLHook;
import com.tencent.matrix.openglleak.utils.EGLHelper;
import com.tencent.matrix.util.MatrixLog;

import java.util.HashMap;
import java.util.Map;

public class OpenglIndexDetectorService extends Service {

    private final static String TAG = "matrix.OpenglIndexDetectorService";

    private IOpenglIndexDetector.Stub stub = new IOpenglIndexDetector.Stub() {
        @Override
        public Map<String, Integer> seekIndex() throws RemoteException {
            return seekOpenglFuncIndex();
        }

        @Override
        public void destory() throws RemoteException {
            stopSelf();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private Map<String, Integer> seekOpenglFuncIndex() {
        // 初始化 egl 环境，目的为了初始化 gl 表
        EGLHelper.initOpenGL();

        OpenGLHook.getInstance().init();
        MatrixLog.i(TAG, "init env succ");

        int glGenTexturesIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_GEN_TEXTURES);
        MatrixLog.i(TAG, "glGenTextures index:" + glGenTexturesIndex);
        int glDeleteTexturesIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_DELETE_TEXTURES);
        MatrixLog.i(TAG, "glDeleteTextures index:" + glDeleteTexturesIndex);

        int glGenBuffersIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_GEN_BUFFERS);
        MatrixLog.i(TAG, "glGenBuffers index:" + glGenBuffersIndex);
        int glDeleteBuffersIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_DELETE_BUFFERS);
        MatrixLog.i(TAG, "glDeleteBuffers index:" + glDeleteBuffersIndex);

        int glGenFramebuffersIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_GEN_FRAMEBUFFERS);
        MatrixLog.i(TAG, "glGenFramebuffers index:" + glGenFramebuffersIndex);
        int glDeleteFramebuffersIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_DELETE_FRAMEBUFFERS);
        MatrixLog.i(TAG, "glDeleteFramebuffers index:" + glDeleteFramebuffersIndex);

        int glGenRenderbuffersIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_GEN_RENDERBUFFERS);
        MatrixLog.i(TAG, "glGenRenderbuffers index:" + glGenRenderbuffersIndex);
        int glDeleteRenderbuffersIndex = FuncSeeker.getFuncIndex(FuncNameString.GL_DELETE_RENDERBUFFERS);
        MatrixLog.i(TAG, "glDeleteRenderbuffers index:" + glDeleteRenderbuffersIndex);

        if ((glGenTexturesIndex * glDeleteTexturesIndex * glGenBuffersIndex * glDeleteBuffersIndex * glGenFramebuffersIndex * glDeleteFramebuffersIndex * glGenRenderbuffersIndex * glDeleteRenderbuffersIndex) == 0) {
            MatrixLog.e(TAG, "seek func index fail!");
            return null;
        }

        Map<String, Integer> out = new HashMap<>();
        out.put(FuncNameString.GL_GEN_TEXTURES, glGenTexturesIndex);
        out.put(FuncNameString.GL_DELETE_TEXTURES, glDeleteTexturesIndex);
        out.put(FuncNameString.GL_GEN_BUFFERS, glGenBuffersIndex);
        out.put(FuncNameString.GL_DELETE_BUFFERS, glDeleteBuffersIndex);
        out.put(FuncNameString.GL_GEN_FRAMEBUFFERS, glGenFramebuffersIndex);
        out.put(FuncNameString.GL_DELETE_FRAMEBUFFERS, glDeleteFramebuffersIndex);
        out.put(FuncNameString.GL_GEN_RENDERBUFFERS, glGenRenderbuffersIndex);
        out.put(FuncNameString.GL_DELETE_RENDERBUFFERS, glDeleteRenderbuffersIndex);

        MatrixLog.i(TAG, "seek func index succ!");
        return out;
    }

}
