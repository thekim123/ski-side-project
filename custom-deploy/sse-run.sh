# 1. Stop webflux-module
ehco "1. Stop webflux-module"
SSE_PID=${pgrep -f sse-server-0.0.1-SNAPSHOT.jar}
kill -9 $SSE_PID

# 2. Run webflux-module
echo "Run webflux-module"
cd ~/application
chmod u+x sse-server-0.0.1-SNAPSHOT.jar
nohup java -jar sse-server-0.0.1-SNAPSHOT.jar 1>sse-std.out 2>sse-err.out &
