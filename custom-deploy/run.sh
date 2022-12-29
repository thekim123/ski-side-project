# 1. run web-module
echo "1. run web-module"
cd ~/application
chmod u+x ski-0.0.1-SNAPSHOT.jar
nohup java -jar ski-0.0.1-SNAPSHOT.jar 1>backend-std.out 2>backend-err.out &

# 2. run web-module
echo "2. run web-module"
chmod u+x sse-server-0.0.1-SNAPSHOT.jar
nohup java -jar sse-server-0.0.1-SNAPSHOT.jar 1>sse-std.out 2>sse-err.out &

# 3. run front-module
echo "3. run front-module"
nvm use 16.13.0
chmod u+x build.zip
mkdir build
unzip build.zip -d build -o
nohup npx serve -l 3000 -s build &
