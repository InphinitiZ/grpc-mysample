FROM maven:3.5.3-jdk-8
COPY . /app
WORKDIR /app
RUN mvn clean package -Dmaven.test.skip=true
ENTRYPOINT java -jar target/grpc-mysample-1.0-SNAPSHOT.jar
EXPOSE 8080
