#
# Cookbook Name:: maven
# Provider::      default
# Author:: Bryan W. Berry <bryan.berry@gmail.com>
# Copyright 2011, Opscode Inc.
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

action :install do
  require 'fileutils'
  # create the destination path if it doesn't already exist
  unless ::File.exists?(new_resource.dest)
    FileUtils.mkdir_p new_resource.dest, { :mode => 0755 }
  end
  artifact_file = ::File.join new_resource.dest, "#{new_resource.artifact_id}-#{new_resource.version}.#{new_resource.packaging}"
  group_id = "-DgroupId=" + new_resource.group_id
  artifact_id = "-DartifactId=" + new_resource.artifact_id
  version = "-Dversion=" + new_resource.version
  dest = "-Ddest=" + artifact_file
  repos = "-DremoteRepositories=" + new_resource.repositories.join(',')
  packaging = "-Dpackaging=" + new_resource.packaging
  plugin_version = '2.4'
  plugin = "org.apache.maven.plugins:maven-dependency-plugin:#{plugin_version}:get"
  command = %Q{mvn #{plugin} #{group_id} #{artifact_id} #{version} #{packaging} #{dest} #{repos}}
  unless ::File.exists?("#{artifact_file}")
    b = Chef::Resource::Script::Bash.new "download maven artifact", run_context
    b.code command
    b.run_action(:run)
    FileUtils.chown new_resource.owner, new_resource.owner, artifact_file
    FileUtils.chmod new_resource.mode.to_i, artifact_file
    new_resource.updated_by_last_action(true)
  end
end
