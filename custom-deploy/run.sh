# 1. Stop all module
echo "1. Stop all module"
BACKEND_PID=${pgrep -f ski-0.0.1-SNAPSHOT.jar}
SSE_PID=${pgrep -f sse-server-0.0.1-SNAPSHOT.jar}
FRONT-PID=${pgrep -f /home/ubuntu/.nvm/versions/node/v16.13.0/bin/serve}
kill -9 $BACKEND_PID
kill -9 $SSE_PID
kill -9 $FRONT-PID

# 2. run web-module
echo "2. Run web-module"
cd ~/application
chmod u+x ski-0.0.1-SNAPSHOT.jar
nohup java -jar ski-0.0.1-SNAPSHOT.jar 1>>backend-std.out 2>>backend-err.out &

# 3. run webflux-module
echo "3. Run webflux-module"
chmod u+x sse-server-0.0.1-SNAPSHOT.jar
nohup java -jar sse-server-0.0.1-SNAPSHOT.jar 1>sse-std.out 2>sse-err.out &

# 4. run front-module
echo "4. Run front-module"
nvm use 16.13.0
chmod u+x build.zip
mkdir build
unzip build.zip -d build
nohup npx serve -l 3000 -s build 1>front-std.out 2>front-err.out &
