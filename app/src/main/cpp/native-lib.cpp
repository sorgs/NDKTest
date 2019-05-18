#include <jni.h>
#include <string>
#include <android/native_window_jni.h>

#include <sys/mman.h>

#include "ProjectorFocusManagerExt.h"

char *m_buf = NULL;

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_sorgs_ndktest_XgimiCamera_getCameraDate(JNIEnv *env, jobject instance, jint add) {

    m_buf = (char *) mmap(0, 1024, PROT_READ | PROT_WRITE, MAP_SHARED, add, 0);
    if (m_buf == MAP_FAILED) {
        perror("mmap: ");
    }
    jbyteArray array = env->NewByteArray(1024);
    env->SetByteArrayRegion(array, 0, 1024, reinterpret_cast<const jbyte *>(m_buf));
    return array;
}