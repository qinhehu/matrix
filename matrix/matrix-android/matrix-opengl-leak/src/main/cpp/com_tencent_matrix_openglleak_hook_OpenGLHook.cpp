//
// Created by 邓沛堆 on 2020-04-27.
//

#include <jni.h>
#include <android/log.h>
#include <GLES2/gl2.h>
#include <EGL/egl.h>
#include "com_tencent_matrix_openglleak_hook_OpenGLHook.h"
#include <string>
#include "get_tls.h"
#include "type.h"
#include "my_functions.h"

extern "C" JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_init
        (JNIEnv *env, jobject thiz) {
    if (class_OpenGLHook == NULL) {
        // 暂存 class, hook 处只能用 bootclassloader
        class_OpenGLHook = env->FindClass("com/tencent/matrix/openglleak/hook/OpenGLHook");
        class_OpenGLHook = (jclass) env->NewGlobalRef(class_OpenGLHook);

        method_onGlGenTextures = env->GetStaticMethodID(class_OpenGLHook, "onGlGenTextures",
                                                        "([ILjava/lang/String;Ljava/lang/String;J)V");
        method_onGlDeleteTextures = env->GetStaticMethodID(class_OpenGLHook, "onGlDeleteTextures",
                                                           "([ILjava/lang/String;)V");
        method_onGlGenBuffers = env->GetStaticMethodID(class_OpenGLHook, "onGlGenBuffers", "([ILjava/lang/String;Ljava/lang/String;J)V");
        method_onGlDeleteBuffers = env->GetStaticMethodID(class_OpenGLHook, "onGlDeleteBuffers",
                                                          "([ILjava/lang/String;)V");
        method_onGlGenFramebuffers = env->GetStaticMethodID(class_OpenGLHook, "onGlGenFramebuffers",
                                                            "([ILjava/lang/String;Ljava/lang/String;J)V");
        method_onGlDeleteFramebuffers = env->GetStaticMethodID(class_OpenGLHook,
                                                               "onGlDeleteFramebuffers", "([ILjava/lang/String;)V");
        method_onGlGenRenderbuffers = env->GetStaticMethodID(class_OpenGLHook,
                                                             "onGlGenRenderbuffers", "([ILjava/lang/String;Ljava/lang/String;J)V");
        method_onGlDeleteRenderbuffers = env->GetStaticMethodID(class_OpenGLHook,
                                                                "onGlDeleteRenderbuffers", "([ILjava/lang/String;)V");
        method_onGetError = env->GetStaticMethodID(class_OpenGLHook, "onGetError", "(I)V");
        method_getStack = env->GetStaticMethodID(class_OpenGLHook, "getStack",
                                                 "()Ljava/lang/String;");

        return true;
    }

    return false;
}

extern "C" JNIEXPORT void JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_setNativeStackDump (JNIEnv *, jobject thiz, jboolean open) {
    enable_stacktrace(open);
}

extern "C" JNIEXPORT void JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_setJavaStackDump (JNIEnv *, jobject thiz, jboolean open) {
    enable_javastack(open);
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    m_java_vm = vm;
    return JNI_VERSION_1_6;
}


/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenTextures
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenTextures
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glGenTextures = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glGenTextures;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteTextures
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteTextures
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glDeleteTextures = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glDeleteTextures;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenBuffers
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenBuffers
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glGenBuffers = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glGenBuffers;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteBuffers
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteBuffers
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glDeleteBuffers = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glDeleteBuffers;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenFramebuffers
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenFramebuffers
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glGenFramebuffers = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glGenFramebuffers;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteFramebuffers
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteFramebuffers
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glDeleteFramebuffers = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glDeleteFramebuffers;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenRenderbuffers
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenRenderbuffers
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glGenRenderbuffers = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glGenRenderbuffers;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteRenderbuffers
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteRenderbuffers
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glDeleteRenderbuffers = (System_GlNormal_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glDeleteRenderbuffers;

    return true;
}

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGetError
 * Signature: (I)Z
 */
extern "C" JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGetError
        (JNIEnv *, jclass, jint index) {
    gl_hooks_t *hooks = get_gl_hooks();
    if (NULL == hooks) {
        return false;
    }

    void **origFunPtr = NULL;

    origFunPtr = (void **) (&hooks->gl.foo1 + index);
    system_glGetError = (System_GlGetError_TYPE) *origFunPtr;
    *origFunPtr = (void *) my_glGetError;

    return true;
}

