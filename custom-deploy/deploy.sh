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
npm install -g serve@11.3.2
