# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant::Config.run do |config|
  config.vm.define :server do |server_config|
    server_config.vm.box = "base"
    server_config.vm.provision :chef_solo do |chef|
      chef.cookbooks_path = "cookbooks"
      chef.add_recipe "vagrant_main"
      chef.add_recipe "java"
      chef.add_recipe "maven"
    end

    server_config.vm.network :hostonly, "192.168.2.10"
    server_config.vm.forward_port 80, 5886
    server_config.vm.forward_port 5005, 5005
  end

  config.vm.boot_mode = :gui
end
