#For generation cases
java -Dfile.name=site -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=page -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=article -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=user -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=tag -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar

java -Dfile.name=user-follows-user -Ddir.path=/Users/pyco/Documents/bechmark-test/user-follows-user -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=user-follows-page -Ddir.path=/Users/pyco/Documents/bechmark-test/user-follows-page -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=user-likes-article -Ddir.path=/Users/pyco/Documents/bechmark-test/user-likes-article -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=article-publishedby-page -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=article-publishedto-site -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=article-interested-tag -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar
java -Dfile.name=article-has-paragraph -Ddir.path=/Users/pyco/Documents/bechmark-test -jar ./mbc-poc-datastax-benchmark.jar

java -Dfile.name=site -Ddir.path=/Users/pyco/Documents/bechmark-test -jar target/mbc-poc-datastax-benchmark-1.0-SNAPSHOT-jar-with-dependencies.jar 

#For benchmark cases
java -DXmx=2048m -DXms=1024m -Dorientdb.host=localhost/jartest -Dorientdb.user=admin -Dorientdb.password=admin -Ddata.path=/Users/pyco/Documents/benchmark-data/ -Dlog.path=/Users/pyco/Documents/bechmark-test/log -jar ./mbc-poc-datastax-benchmark.jar

java -Xmx3072m -Xmx3072m -Dneo4j.host=172.31.30.116 -Dneo4j.user=neo4j -Dneo4j.password=admin -Ddata.path=/opt/mountpoint/benchmark/data -Dlog.path=/opt/mountpoint/benchmark/log -jar mbc-poc-datastax-benchmark.jar

sudo java -Xms2048m -Xmx3072m -Dfile.name=user-likes-article -Ddir.path=/opt/mountpoint/benchmark/data/user-likes-article -jar mbc-poc-datastax-benchmark.jar
 
 /opt/mountpoint/neo4j/data
 /opt/mountpoint/neo4j/log
 
 CREATE INDEX ON :User(id)
 CREATE INDEX ON :Article(id)
 CREATE INDEX ON :Site(id)
 CREATE INDEX ON :Tag(id)
 CREATE INDEX ON :Page(id)
 CREATE INDEX ON :Paragraph(id)
 
 CREATE CONSTRAINT ON u:User ASSERT u.id is UNIQUE
 CREATE CONSTRAINT ON (s:Site) ASSERT s.id is UNIQUE
 CREATE CONSTRAINT ON (a:Article) ASSERT a.id is UNIQUE
 
 sudo nohup java -Xms4096m -Xmx6144m -Dneo4j.host=172.31.30.116 -Dneo4j.user=neo4j -Dneo4j.password=admin -Ddata.path=/opt/mountpoint/benchmark/data/ -Dlog.path=/opt/mountpoint/benchmark/log -Dtracking.path=/opt/mountpoint/benchmark/tracking/neo4j.tracking -jar mbc-poc-datastax-benchmark-1.0-SNAPSHOT-jar-with-dependencies.jar &
 
 sudo nohup java -Xms3g -Xmx3g -Ddse.host=172.31.50.87 -Ddse.port=9042 -Ddse.database=benchmark -Ddata.path=/opt/mountpoint/benchmark/data/ -Dlog.path=/opt/mountpoint/benchmark/log -Dtracking.path=/opt/mountpoint/benchmark/tracking/datastax.tracking -jar mbc-poc-datastax-benchmark-1.0-SNAPSHOT-jar-with-dependencies.jar &