# 1. Stop web-module
echo "1. stop web-module"
BACKEND_PID=${pgrep -f ski-0.0.1-SNAPSHOT.jar}
kill -9 $BACKEND_PID

# 2. run web-module
echo "2. run web-module"
cd ~/application
chmod u+x ski-0.0.1-SNAPSHOT.jar
nohup java -jar ski-0.0.1-SNAPSHOT.jar 1>>backend-std.out 2>>backend-err.out &

