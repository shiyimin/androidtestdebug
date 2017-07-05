针对Android工程创建jenkins job的话，需要安装以下这些插件：

1. Git Plugin
2. 如果Android Studio工程，需要安装下面这个插件
	1. Gradle Plugin
	2. JUnit Plugin
	3. Android Emulator Plugin（如果需要在模拟器里执行测试用例，可以安装这个插件，否则不必要）
	4. JaCoCo plugin
3. 配置jenkins工程，其中里面各个插件的配置：
	1. git工程，配置git的位置，在示例里，我配置了保存在本机的git位置：`/Users/shiyimin/git-workspace/androidtestdebug/lecture-demo/jenkins/android-testing/.git`  
	2. 在`构建`这个步骤里，在`增加构建步骤`里选择添加`Invoke Gradle script`，勾选`Use Gradle Wrapper`，并设置`gradlew`的路径，示例里偷懒直接写了同步到jenkins的workspace里android工程的gradlew路径：`/Users/shiyimin/.jenkins/workspace/android-ci-demo`。在gradle的运行任务，配置要执行的gradle任务：
		1. 在示例里，为了演示收集代码覆盖率，我使用了任务：`createProdDebugCoverageReport`；
		2. 如果只是执行自动化测试用例的话，可以使用任务：`connectedAndroidTest`、或者 `connectedDebugAndroidTest`。
		3. 在`构建后操作`这个步骤里，在`增加构建后操作步骤`里选择添加`Publishi JUnit test result report`步骤，配置JUnit Plugin里，配置Gradle里的JUnit报告位置：`app/build/outputs/androidTest-results/connected/flavors/PROD/*.xml`，看Android工程的设置，/flavors/PROD这两个文件夹可能是没有的，或者直接写：**/**.xml，看Android工程的设置，
		4. 在`构建后操作`这个步骤里，在`增加构建后操作步骤`里选择添加`Record JaCoCo coverage report`步骤，代码覆盖率Jacoco的设置：
			1. Path to exec files：**/**.ec
			2. Path to class directories: **/classes
			3. Path to source directories: **/src/main/java
        5. 如果是需要在模拟器里执行，可以在`构建环境`这个步骤里，勾选`Run an Android emulator during build`：
        	1. 可以勾选`Run existing emulator`，填上已经创建好的模拟器名称即可
        	2. 或者选择`Run emulator with properties`，来动态创建模拟器
4. 如果是Eclipse工程，需要安装下面这个插件
	1. Ant Plugin
	2. JUnit Plugin
	3. Android Emulator Plugin
	4. Emma plugin
5. 配置jenkins工程，其中里面各个插件的配置：
	1. git插件与Android Studio工程的配置相同；
	2. 在`构建`这个步骤里，在`增加构建步骤`里选择添加`Invoke Ant`，`Targets`：
		1. 如果需要收集代码覆盖率，则根据SDK版本不同：
			1. SDK 14及以上：填上`emma debug install test`;
			2. 否则填上`coverage`
    	2. 否则填上：`debug install test`
	3. JUnit插件设置与Android Studio工程的配置相同；
	4. 在`构建后操作`这个步骤里，在`增加构建后操作步骤`里选择添加`Record Emma coverage report`步骤，代码覆盖率Emma的设置可以使用默认设置

示例jenkins job已经导出了，可以在`jenkins-job/android-ci-demo`找到，里面的`config.xml`就是配置清单。