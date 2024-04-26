# Download docker images of kafka by cloning https://github.com/conduktor/kafka-stack-docker-compose
  git clone https://github.com/conduktor/kafka-stack-docker-compose.git
  
# Run docker demon on the machine 
  command + spacebar - docker -- make sure the "kafka stack" is running 

# Run docker compose to run docker container with a one zoo keeper and one broker
  docker-compose -f zk-single-kafka-single.yml up

# Configure topic and manage this kafka cluster, install the conduktor app from 
    https://www.conduktor.io/download/
    https://www.youtube.com/watch?v=WhMPqUgYYCQ&list=PLYmXYyXCMsfMMhiKPw4k1FF7KWxOEajsA&index=5
    
    Run conduktor by Applications > Conductor
    	This would open a conduktor app on tha window 
    Configure a kafka topic called streaming.unknown.topic on the above cluster using conduktor


# Configure the kafka stream java class as per this 
	https://developer.confluent.io/courses/spring/process-messages-with-kafka-streams/    
	

# Build kafka-models, kafka-producer and kafka-consumer projects
   Please go through the readme.md file in those projects

	
# Run this spring boot application by
	eclipse -> com.sowjanya.kafka.KafkaApplication	
	When you go to http://localhost:8080/hiThere you should see
	{"message" : "Happy Birthday"}
	
# To Deploy this application on AWS lambda as a serverless app following this
	inspiration: https://github.com/aws/serverless-java-container/wiki/Quick-start---Spring-Boot3
	
	Run maven clean, install and package
	mvn clean install package
	
	create a template.yaml
	
	Then run 
	./deploy.sh
	
	Outcome
	Trying to deploy this project as a lambda did not work as the size of the package generated was 
	 420 MB and allowed package size limit was 262144000 bytes or 250 MB
	 


	