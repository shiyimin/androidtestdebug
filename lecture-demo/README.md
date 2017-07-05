运行uiautomatorviewer命令：~/Library/Android/sdk/tools/bin/uiautomatorviewer &

## Android Studio Appium的注意事项

1. 需要设置环境变量：ANDROID_HOME
2. 需要设置环境变量`APPIUM_BINARY_PATH`指明appium.js的位置，通过npm install appium安装，如：/Users/shiyimin/workspace/lecture-demo/appium/sample-code-master/sample-code/examples/node/node_modules/appium/build/lib/main.js
3. 需要是JUnit测试

## Jenkins在Mac上运行android用例

1. 需要在运行jenkins的命令行里设置环境变量：ANDROID_HOME
2. 设置PATH能够找到gradle：export PATH=$PATH:/Applications/Android\ Studio.app/Contents/gradle/gradle-3.2/bin/
2. 需要设置LD加载路径环境变量：export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$ANDROID_HOME/tools/lib
3. 在android-emulator-plugin->Advanced->Emulator的高级选项里添加: -engine auto
参考文档：https://issues.jenkins-ci.org/browse/JENKINS-43557?page=com.atlassian.streams.streams-jira-plugin%3Aactivity-stream-issue-tab