# 1. jdk install
sudo apt-get update
sudo apt-get install openjdk-11-jdk -y

# 2. net-tools install
sudo apt install net-tools

# 3. node install
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.37.2/install.sh
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.37.2/install.sh | bash
source ~/.bashrc
nvm list-remote
nvm install v16.13.0
nvm use 16.13.0
npm install -g npm@latest
npm install -g yarn
npm install -g serve

# 4. git clone
rm -rf ski-side-project
git clone https://github.com/thekim123/ski-side-project.git

# 4. run web-module
cd ~/ski-side-project/ski-backend
sudo chmod u+x gradlew
./gradlew clean build
cd ~/sski-side-project/ski-backend/build/libs
nohup java -jar ski-0.0.1-SNAPSHOT.jar 1>backend-std.out 2>backend-err.out &

# 5. run web-module
cd ~/ski-side-project/sse-server
sudo chmod u+x gradlew
./gradlew clean build
cd ~/ski-side-project/sse-server/build/libs
nohup java -jar sse-server-0.0.1-SNAPSHOT.jar 1>backend-std.out 2>backend-err.out &

# 6. run front-module
cd ~/ski-side-project/frontend
nohup npx serve -s build &
