#
# Cookbook Name:: maven
# Recipe:: maven3
# Author:: Seth Chisamore (<seth@opscode.com>)
# Author:: Bryan W. Berry (<bryan.berry@gmail.com>)
#
# Copyright 2011, Opscode
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

include_recipe "ark"

ark "maven" do
  url node['maven']['3']['url']
  checksum node['maven']['3']['checksum']
  home_dir node['maven']["m2_home"]
  version "3.0.3"
  append_env_path true
  action :install
end

template "/etc/mavenrc" do
  source "mavenrc.erb"
  mode "0755"
end
