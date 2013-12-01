#include "cn_hzbook_android_test_chapter9_jnidemo_MainActivity.h"
#include <assert.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>

static volatile int flag1 = 0;
static volatile int flag2 = 0;
static volatile int turn  = 1;
static volatile int gSharedCounter = 0;

int gLoopCount;
int gOnePercent;

void dekker1( ) {
        flag1 = 1;
        turn  = 2;
        while((flag2 ==  1) && (turn == 2)) ;
        // Critical section
        gSharedCounter++;
        // Let the other task run
        flag1 = 0;
}

void dekker2(void) {
        flag2 = 1;
        turn = 1;
        while((flag1 ==  1) && (turn == 1)) ;
        // critical section
        gSharedCounter++;        
        // leave critical section
        flag2 = 0;
}

void *task1(void *arg) {
    int i = 0, j = 0;
#ifdef PRINT_PROGRESS
	for(i=0;i<100;i++) {
          __android_log_print(ANDROID_LOG_DEBUG, "JNI DEMO", "[One] at %d%%\n",i);
	  for(j=gOnePercent;j>0;j--) {
	    dekker1();
	  }
	}
#else
    for(i = gLoopCount; i > 0; i--) {
		dekker1();
    }
#endif
	return 0;
}

void *task2(void *arg) {
    int i = 0, j = 0;
#ifdef PRINT_PROGRESS
	for(i=0;i<100;i++) {
          __android_log_print(ANDROID_LOG_DEBUG, "JNI DEMO", "[Two] at %d%%\n",i);
	  for(j=gOnePercent;j>0;j--) {
	    dekker2();
	  }
	}
#else
	for(i = gLoopCount; i > 0; i--) {
		dekker2();
	}
#endif

	return 0;
}

JNIEXPORT jstring Java_cn_hzbook_android_test_chapter9_jnidemo_MainActivity_getMyData
(JNIEnv* pEnv, jobject pThis)
{

    int            loopCount = 1000000;
        pthread_t      dekker_thread_1;
        pthread_t      dekker_thread_2;
        void           * returnCode;
        int            result;
        int            expected_sum;

		/* Parse argument */
	gLoopCount  = loopCount;
	gOnePercent = loopCount/100;
	expected_sum = 2*loopCount;
        
        result = pthread_create(&dekker_thread_1, NULL, task1, NULL);
        result = pthread_create(&dekker_thread_2, NULL, task2, NULL);

        /* Wait for the threads to end */
        result = pthread_join(dekker_thread_1,&returnCode);
        result = pthread_join(dekker_thread_2,&returnCode);
        __android_log_print(ANDROID_LOG_DEBUG, "JNI DEMO", "Both threads terminated\n");

	/* Check result */
	if( gSharedCounter != expected_sum ) {
            __android_log_print(ANDROID_LOG_DEBUG, "JNI DEMO", 
                                "[-] Dekker did not work, sum %d rather than %d.\n", gSharedCounter, expected_sum);
            __android_log_print(ANDROID_LOG_DEBUG, "JNI DEMO", 
                                "    %d missed updates due to memory consistency races.\n", (expected_sum-gSharedCounter));
	} else {
             __android_log_print(ANDROID_LOG_DEBUG, "JNI DEMO", "[+] Dekker worked.\n");
	}

    return (*pEnv)->NewStringUTF(pEnv, "从C/C++里返回的字符串！");
}

