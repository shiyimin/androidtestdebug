javac gcdemo.java
dx --dex --output=dc.jar DemoClass.class  gcdemo.class
adb -e push dc.jar /sdcard/
adb -e shell dalvikvm -classpath /sdcard/dc.jar gcdemo