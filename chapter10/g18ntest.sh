#!/bin/bash

# 将系统语言设置为英文，其中 -e 选项表明修改
# 模拟器系统的语言设置，如果需要修改Android设备
# 的设置，则需要指定 -d 选项
adb -e shell setprop persist.sys.language en
# 设置语言的子区域，即国家或地区的ISO名
# 这是指定系统语言的美国英语 
adb -e shell setprop persist.sys.country US
# 重新启动模拟器
killall -9 emulator-arm
emulator -avd Android2 &
# 因为启动模拟器需要一段时间，这里等待1分钟
sleep 60
# 取消键盘锁定
adb shell input keyevent 82
# 下面的代码也可以重新启动模拟器，但在测试虚拟
# 机上，模拟器在start命令之后，就会停在那里
# 因此只好采用杀掉模拟器进程，并重启的方式暴力解决
# adb -e shell stop
# sleep 5
# adb -e shell start
# 启动示例国际化应用
adb shell am start -n cn.hzbook.android.test.chapter10.g18ndemo/.MainActivity
# 启动应用也需要一段时间，这里也等待1分钟
sleep 60

# 重新用中文语言再次运行一遍
# 设置系统语言为中文
adb -e shell setprop persist.sys.language zh
# 地区为中国
adb -e shell setprop persist.sys.country CN
killall -9 emulator-arm
emulator -avd Android2 &
sleep 60
adb shell input keyevent 82
adb shell am start -n cn.hzbook.android.test.chapter10.g18ndemo/.MainActivity
