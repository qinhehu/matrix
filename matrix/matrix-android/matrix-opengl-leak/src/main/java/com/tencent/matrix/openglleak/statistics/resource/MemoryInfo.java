package com.tencent.matrix.openglleak.statistics.resource;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
import static android.opengl.GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
import static android.opengl.GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
import static android.opengl.GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static android.opengl.GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
import static android.opengl.GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
import static android.opengl.GLES30.GL_TEXTURE_2D_ARRAY;
import static android.opengl.GLES30.GL_TEXTURE_3D;

public class MemoryInfo {

    private int target;

    private int level;

    private int internalFormat;

    private int width;

    private int height;

    private int depth;

    private int border;

    private int format;

    private int type;

    private int id;

    private long eglContextId;

    private int usage;

    private String javaStack = "";

    private String nativeStack = "";

    private long nativeStackPtr;

    private final OpenGLInfo.TYPE resType;

    // use for buffer & renderbuffer
    private long size;

    // only use for textures
    private FaceInfo[] faces;

    public MemoryInfo(OpenGLInfo.TYPE type) {
        this.resType = type;
        if (resType == OpenGLInfo.TYPE.TEXTURE) {
            final int cubeMapFaceCount = 6;
            faces = new FaceInfo[cubeMapFaceCount];
        }
    }

    private int getFaceId(int target) {
        switch (target) {
            case GL_TEXTURE_2D:
            case GL_TEXTURE_3D:
            case GL_TEXTURE_2D_ARRAY:
            case GL_TEXTURE_CUBE_MAP_POSITIVE_X:
                return 0;
            case GL_TEXTURE_CUBE_MAP_NEGATIVE_X:
                return 1;
            case GL_TEXTURE_CUBE_MAP_POSITIVE_Y:
                return 2;
            case GL_TEXTURE_CUBE_MAP_NEGATIVE_Y:
                return 3;
            case GL_TEXTURE_CUBE_MAP_POSITIVE_Z:
                return 4;
            case GL_TEXTURE_CUBE_MAP_NEGATIVE_Z:
                return 5;
            default:
                return -1;
        }
    }

    public void setTexturesInfo(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, int id, long eglContextId, long size, String javaStack, long nativeStackPtr) {
        int faceId = getFaceId(target);
        if (faceId == -1) {
            return;
        }

        this.target = target;
        this.level = level;
        this.internalFormat = internalFormat;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.border = border;
        this.format = format;
        this.type = type;
        this.id = id;
        this.eglContextId = eglContextId;
        this.javaStack = javaStack;
        this.nativeStackPtr = nativeStackPtr;

        FaceInfo faceInfo = faces[faceId];
        if (faceInfo == null) {
            faceInfo = new FaceInfo();
        }
        faceInfo.setSize(size);
        faceInfo.setParams("MemoryInfo{" +
                "target=" + this.getTarget() +
                ", id=" + this.getId() +
                ", eglContextNativeHandle='" + this.getEglContextId() + '\'' +
                ", level=" + this.getLevel() +
                ", internalFormat=" + this.getInternalFormat() +
                ", width=" + this.getWidth() +
                ", height=" + this.getHeight() +
                ", depth=" + this.getDepth() +
                ", border=" + this.getBorder() +
                ", format=" + this.getFormat() +
                ", type=" + this.getType() +
                ", size=" + this.getSize() +
                '}');
        faces[faceId] = faceInfo;
    }

    public OpenGLInfo.TYPE getResType() {
        return resType;
    }

    public String getJavaStack() {
        return javaStack;
    }

    public String getNativeStack() {
        if (nativeStack.isEmpty() && nativeStackPtr != 0) {
            nativeStack = ResRecordManager.dumpNativeStack(nativeStackPtr);
        }
        return nativeStack;
    }

    public long getNativeStackPtr() {
        return nativeStackPtr;
    }

    public int getTarget() {
        return target;
    }

    public int getLevel() {
        return level;
    }

    public int getInternalFormat() {
        return internalFormat;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getBorder() {
        return border;
    }

    public int getFormat() {
        return format;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public FaceInfo[] getFaces() {
        return faces;
    }

    public long getSize() {
        if (this.resType == OpenGLInfo.TYPE.TEXTURE) {
            for (FaceInfo faceInfo : faces) {
                if (faceInfo != null) {
                    this.size += faceInfo.getSize();
                }
            }
        }
        return size;
    }

    public long getEglContextId() {
        return eglContextId;
    }

    public int getUsage() {
        return usage;
    }


    public void setBufferInfo(int target, int usage, int id, long eglContextId, long size, String javaStack, long nativeStackPtr) {
        this.target = target;
        this.usage = usage;
        this.id = id;
        this.eglContextId = eglContextId;
        this.size = size;
        this.javaStack = javaStack;
        this.nativeStackPtr = nativeStackPtr;
    }

    public void setRenderbufferInfo(int target, int width, int height, int internalFormat, int id, long eglContextId, long size, String javaStack, long nativeStackPtr) {
        this.target = target;
        this.width = width;
        this.height = height;
        this.internalFormat = internalFormat;
        this.id = id;
        this.eglContextId = eglContextId;
        this.size = size;
        this.javaStack = javaStack;
        this.nativeStackPtr = nativeStackPtr;
    }


}
