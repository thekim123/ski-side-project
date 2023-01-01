# 1. Stop front-module
echo "1. Stop front-module"
FRONT-PID=${pgrep -f /home/ubuntu/.nvm/versions/node/v16.13.0/bin/serve}
kill -9 $FRONT-PID

# 2. Run front-module
echo "2. Run front-module"
cd ~/application
nvm use 16.13.0
chmod u+x build.zip
mkdir build
unzip build.zip -d build
nohup npx serve -l 3000 -s build 1>front-std.out 2>front-err.out &
