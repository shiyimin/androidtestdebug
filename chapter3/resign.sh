#
# 重新给product签名，确保其使用的签名与测试用例的签名一致
# 第一步是删除产品里已有的签名
#
echo 重新打包$1.apk
unzip -o $1.apk -d product
cd product
rm -r -f META-INF/
zip -r product.apk *
mv product.apk ..
cd ..
rm -f -r product
#
# 使用调试用签名重新签名
#
jarsigner -keystore ~/.android/debug.keystore -storepass android -keypass android product.apk androiddebugkey
zipalign 4 product.apk $1-resigned.apk

