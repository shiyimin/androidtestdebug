#include "unittestpp.h"

namespace {
    // 创建一个可执行通过的测试用例
    TEST(UnitTestDemoPass)
    {
        bool const b = true;
        CHECK(b);
    }

    // 创建一个执行失败的测试用例
    TEST(UnitTestDemoFail)
    {
        bool const b = false;
        CHECK(b);
    }
}
