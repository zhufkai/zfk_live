//
// Created by zhufk on 2019/8/20.
//
#include <jni.h>
/*
 * Class:     com_dongnaoedu_live_jni_PushNative
 * Method:    startPush
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_zhufk_zfk_1live_live_PushNative_startPush
(JNIEnv *, jobject, jstring);

/*
 * Class:     com_dongnaoedu_live_jni_PushNative
 * Method:    stopPush
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_zhufk_zfk_1live_live_PushNative_stopPush
(JNIEnv *, jobject);

/*
 * Class:     com_dongnaoedu_live_jni_PushNative
 * Method:    release
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_zhufk_zfk_1live_live_PushNative_release
(JNIEnv *, jobject);

/*
 * Class:     com_dongnaoedu_live_jni_PushNative
 * Method:    setVideoOptions
 * Signature: (IIII)V
 */
JNIEXPORT void JNICALL Java_com_zhufk_zfk_1live_live_PushNative_setVideoOptions
(JNIEnv *, jobject, jint, jint, jint, jint);

/*
 * Class:     com_dongnaoedu_live_jni_PushNative
 * Method:    setAudioOptions
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_zhufk_zfk_1live_live_PushNative_setAudioOptions
(JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_dongnaoedu_live_jni_PushNative
 * Method:    fireVideo
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_zhufk_zfk_1live_live_PushNative_fireVideo
(JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_dongnaoedu_live_jni_PushNative
 * Method:    fireAudio
 * Signature: ([BI)V
 */
JNIEXPORT void JNICALL Java_com_zhufk_zfk_1live_live_PushNative_fireAudio
(JNIEnv *, jobject, jbyteArray, jint);

