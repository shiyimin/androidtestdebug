LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := rununit

LOCAL_C_INCLUDES := \
  $(LOCAL_PATH)/../../unittestpp \
  $(LOCAL_PATH)/../../unittestpp/src \
  $(LOCAL_PATH)/../../unittestpp/src/Posix

LOCAL_SRC_FILES := \
  rununit.cpp \
  testcase.cpp \
  ../../unittestpp/src/AssertException.cpp \
  ../../unittestpp/src/Checks.cpp \
  ../../unittestpp/src/CurrentTest.cpp \
  ../../unittestpp/src/DeferredTestReporter.cpp \
  ../../unittestpp/src/DeferredTestResult.cpp \
  ../../unittestpp/src/MemoryOutStream.cpp \
  ../../unittestpp/src/ReportAssert.cpp \
  ../../unittestpp/src/Test.cpp \
  ../../unittestpp/src/TestDetails.cpp \
  ../../unittestpp/src/TestList.cpp \
  ../../unittestpp/src/TestReporter.cpp \
  ../../unittestpp/src/TestReporterStdout.cpp \
  ../../unittestpp/src/TestResults.cpp \
  ../../unittestpp/src/TestRunner.cpp \
  ../../unittestpp/src/TimeConstraint.cpp \
  ../../unittestpp/src/XmlTestReporter.cpp \
  ../../unittestpp/src/Posix/SignalTranslator.cpp \
  ../../unittestpp/src/Posix/TimeHelpers.cpp

LOCAL_CFLAGS := \
  -DUNITTEST_POSIX -DNULL=0 \
  -DUNITTEST_NO_DEFERRED_REPORTER \
  -DUNITTEST_NO_EXCEPTIONS

include $(BUILD_SHARED_LIBRARY)
