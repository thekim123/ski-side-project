# 1. make directory
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
cd ../../../frontend
yarn build run
zip build.zip ./build/*
cp build.zip ../custom-build/