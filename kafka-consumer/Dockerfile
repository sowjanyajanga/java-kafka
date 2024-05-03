# To build this docker image 
#     docker build --build-arg JAR_FILE=target/*.jar -t sowjanyajanga/kafka-consumer:0.0.1 .
# Login to docker hub to be able to push your image
#     docker login -u "myusername" -p "mypassword" docker.io
#     docker push myusername/myimage:0.0.1
# To push this image to the docker repo that can be read from EC2
#     docker image push sowjanyajanga/kafka-consumer:0.0.1
# To run this image
#     docker run -t -p 8080:8080 sowjanyajanga/kafka-consumer:0.0.1
# To login to the container
#    docker images
#       get image id
#    docker exec -t sowjanyajanga/kafka-consumer:0.0.1
FROM ubuntu:22.04

RUN apt-get -qq update \
    && apt-get --assume-yes install ssh \
    && apt-get --assume-yes install openjdk-21-jdk

VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar


ENTRYPOINT ["java","-jar","/app.jar"]