#include <string.h>
#include <stdio.h>

#include "unittestpp.h"
#include "TestRunner.h"
#include "TestResults.h"
#include "TestReporter.h"
#include "TestReporterStdout.h"
#include "cn_hzbook_android_test_chapter9_rununit_MainActivity.h"

using namespace UnitTest;

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_cn_hzbook_android_test_chapter9_rununit_MainActivity_runUnitTests
  (JNIEnv *pEnv, jobject jthis)
{
  // 下面的代码的功能与 UnitTest::RunAllTests() 类似
  TestReporterStdout reporter;
  // 创建执行测试用例的TestRunner对象
  TestRunner runner(reporter);
  // 获取库文件里的测试用例列表，并依次执行
  runner.RunTestsIf(Test::GetTestList(), NULL, True(), 0);
  // 等待测试用例执行完毕，并收集测试结果
  TestResults* testResults = runner.GetTestResults();

  // 准备将测试结果发到Java端以便显示
  char result[128];
  // 如果有执行失败的测试用例
  if (testResults->GetFailedTestCount() > 0)
  {
    // 说明有多少用例失败
    sprintf(result, "测试结果: 在总共 %d 的用例中\n" \
      "有 %d 的用例执行失败！(%d 错误).",
      testResults->GetTotalTestCount(),
      testResults->GetFailedTestCount(),
      testResults->GetFailureCount());
  }
  // 否则就显示所有用例执行成功的消息
  else
  {
    sprintf(result, "测试结果: 总共有\n" \
      "%d 个测试用例通过测试！", testResults->GetTotalTestCount());
  }

  // 将结果返回到Java端
  return pEnv->NewStringUTF(result);    
}

#ifdef __cplusplus
}
#endif
