user "foobarbaz"

# basic test
maven "mysql-connector-java" do
  group_id "mysql"
  version "5.1.19"
  mode   0755
  owner  'foobarbaz'
  dest "/usr/local/foobar/lib/"
end

# test from alternate repo
maven "java persistence library"  do
  artifact_id "javax.persistence"
  group_id "org.eclipse.persistence"
  version "2.0.0"
  repositories [ 'http://mirrors.ibiblio.org/pub/mirrors/maven2/' ]
  dest "/usr/local/foobar/lib"
end

# test from multiple repositories
maven "postgresql" do
  group_id "postgresql"
  version "9.0-801.jdbc4"
  repositories [ 'http://mirrors.ibiblio.org/pub/mirrors/maven2/', "http://repo1.maven.apache.org/maven2"  ]
  dest "/usr/local/foobar/lib"
end


# test downloading hudson plugin and use old alias
maven "mm-mysql"  do
  groupId "mm-mysql"
  version "2.0.13"
  packaging 'pom'
  dest "/usr/local/foobar/lib"
end
