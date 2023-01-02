# 1. make directory
rm -rf custom-build
mkdir custom-build

# 2. ski-backend
cd ski-backend
./gradlew clean build
cd build/libs
cp *.jar ../../../custom-build/

# 3. ssse-server
cd ../../../sse-server
./gradlew clean build
cd build/libs
cp *.jar ../../../custom-build/

# 4. frontend
cd ../../../frontend/
yarn build run
cd build
zip -r build.zip ./*
cp build.zip ../../custom-build/