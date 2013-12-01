/*
 * 采用pthread实现Dekker算法。
 *
 * 编译命令：
 * gcc -O2 -o dekker dekker.c -lpthread
 *
 * 要查看执行进度的话，执行命令：
 * gcc -DPRINT_PROGRESS -O2 -o dekker dekker.c -lpthread
 */ 

#include <assert.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

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
        // 关键区域 - 已经获取了锁
        gSharedCounter++;
        // 允许另一个线程执行
        flag1 = 0;
}

void dekker2(void) {
        flag2 = 1;
        turn = 1;
        while((flag1 ==  1) && (turn == 1)) ;
        // 关键区域 - 已经获取了锁
        gSharedCounter++;        
        // 离开关键区域并允许另一个线程执行
        flag2 = 0;
}

void *task1(void *arg) {
        int i,j;
        printf("开始task1\n");
#ifdef PRINT_PROGRESS
	for(i=0;i<100;i++) {
	  printf("[task1]的进度：%d%%\n",i);
	  for(j=gOnePercent;j>0;j--) {
	    dekker1();
	  }
	}
#else
        for(i=gLoopCount;i>0;i--) {
                dekker1();
        }
#endif

}

void *task2(void *arg) {
        int i,j;
        printf("开始task2\n");
#ifdef PRINT_PROGRESS
	for(i=0;i<100;i++) {
	  printf("[task2]的进度：%d%%\n",i);
	  for(j=gOnePercent;j>0;j--) {
	    dekker2();
	  }
	}
#else
        for(i=gLoopCount;i>0;i--) {
                dekker2();
        }
#endif
}

int
main(int argc, char ** argv)
{
        int            loopCount = 0;
        pthread_t      dekker_thread_1;
        pthread_t      dekker_thread_2;
        void           * returnCode;
        int            result;
        int            expected_sum;

        if(argc != 2) 
        {
                fprintf(stderr, "USAGE: %s <循环次数>\n", argv[0]);
                exit(1);
        }

        loopCount   = atoi(argv[1]);	
        gLoopCount  = loopCount;
	gOnePercent = loopCount/100;
        expected_sum = 2*loopCount;
        
        /* 启动线程 */
        result = pthread_create(&dekker_thread_1, NULL, task1, NULL);
        result = pthread_create(&dekker_thread_2, NULL, task2, NULL);

        /* 等待线程介绍 */
        result = pthread_join(dekker_thread_1,&returnCode);
        result = pthread_join(dekker_thread_2,&returnCode);
        printf("两个线程执行完毕！\n");

        /* 结果判断 */
        if( gSharedCounter != expected_sum ) {
                printf("[-] Dekker不能用, 得到的实际结果 %d 与期望结果 %d 不同。\n", gSharedCounter, expected_sum);
                printf("    由于内存模型的问题，共有 %d 次更新因竞争访问内存资源而遗漏了！\n", (expected_sum-gSharedCounter));
                return 1;
        } else {
                printf("[+] Dekker算法可用.\n");
                return 0;
        }
}

