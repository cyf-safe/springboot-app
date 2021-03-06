@echo off
:: start ./Redis-x64-3.0.504/redis-server ./Redis-x64-3.0.504/redis.windows.conf

start java -Xmx512m -Xms512m -Xmn256m -Xss1m -XX:PermSize=50m -XX:MaxPermSize=50m -XX:SurvivorRatio=6 -XX:MaxTenuringThreshold=5 -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:+CMSScavengeBeforeRemark -jar sentinel-dashboard-1.6.3.jar --server.port=9999
