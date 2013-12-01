#include "cn_hzbook_android_test_chapter9_jnidemo_MainActivity.h"

JNIEXPORT jstring Java_cn_hzbook_android_test_chapter9_jnidemo_MainActivity_getMyData
(JNIEnv* pEnv, jobject pThis)
{
    return (*pEnv)->NewStringUTF(pEnv, "从C/C++里返回的字符串！");
}

