这是随《[Android应用测试与调试实战](http://www.amazon.cn/%E7%A7%BB%E5%8A%A8%E5%BC%80%E5%8F%91-Android%E5%BA%94%E7%94%A8%E6%B5%8B%E8%AF%95%E4%B8%8E%E8%B0%83%E8%AF%95%E5%AE%9E%E6%88%98-%E6%96%BD%E6%87%BF%E6%B0%91/dp/B00JPUUATC/ref=tmm_pap_title_0?ie=UTF8&qid=1398769605&sr=8-1)》附带的示例代码，**欢迎购买我新书的读者下载** - 没有购买的网友也可以下载。

有两个方法下载书中提到的虚拟机：

1. [虚拟机的下载地址](http://aqkwdkgcpv.l55.yunpan.cn/lk/QNpYuw7QWu8gK)， 访问密码 10eb 。
2. 按下面的方式从本源码自动创建并部署一个虚拟机，;)。

** 自动部署虚拟机的方法还没有做完，请不要尝试！！！ **

### 使用说明
1. 下载并安装最新的Virtual Box: https://www.virtualbox.org/wiki/Downloads
2. 下载并安装最新的Vagrant: http://vagrantup.com/
3. 第一次运行时,打开命令行，进入源代码的根目录,执行（如果是windows平台，在下面的命令后面加.bat后缀）：

    -----------------------
    
       vagrant box add base http://files.vagrantup.com/lucid32.box       
       vagrant up
       
    -----------------------
    
    `有时因为“长城”的关系，下载过程会中断，建议先将http://files.vagrantup.com/lucid32.box下载到本机，例如d:\lucid32.box，然后运行更改过的命令创建：`
    
    -----------------------
    
       vagrant box add base d:\lucid32.box     
       vagrant up
       
    -----------------------


4. 下次就只需要执行下面的命令就可以使用:
    
    -----------------------
    
       vagrant up
       
    -----------------------

5. 命令运行完毕后，一会功夫就可以看到虚拟机界面，第一次运行的时候，因为需要从网上下载安装不少软件，应该只能看到命令行界面，要耐心等待命令运行完毕，运行完命令后，就可以使用虚拟机了。

6. 如果需要进入命令行调试的话，在windows平台，用putty登录：127.0.0.1:2222，用户名/密码：vagrant/vagrant

6. 在非Windows平台
    
    -----------------------
    
       vagrant ssh
       
    -----------------------
   
7. 进入示例代码工作主目录 - 这个虚拟机上的主目录是你宿主机执行vagrant命令的目录
   
    -----------------------
    
       cd /vagrant

       ls
       
    -----------------------   
