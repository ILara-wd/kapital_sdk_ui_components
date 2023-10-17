#include <jni.h>

JNIEXPORT jstring JNICALL

Java_com_app_kapital_core_crypto_CryptoUtil_getAPIKey(JNIEnv *env, jobject instance) {

return (*env)-> NewStringUTF(env, "Zq4t7w!z%C*F-JaNdRgUjXn2r5u8x/A?");
}