# Getting Started

## context
Sample application for job-scheduling.

## design
Following are the key components
Job Acceptor: a rest endpoint which will accept a job to occur at specified times and repeats. It breaks down the job into events and stores in redis 
Job Scanner: a scheduled task that runs and scans redis for scheduled events, and publishes to kafka topic for processing
Job listener: a kafka topic listener that processes events 


Redis is used as job-store. 
Kafka is used for distribution of events for processors. 

## pre-requisites
Needs JDK 17, Docker 


### build
Edit the build.sh file to set JAVA_HOME. (I used jenv). Also check the docker-compose file for ports and variables. Individual application properties are in respective application.yml file
1. Build
> ./build.sh

2. docker compose up -d

## run
1. create events for jobs as shown below through API. The API will return all events derived from the job definition and an acknowledgement id. 
<pre>
curl --request POST \
  --url http://localhost:8080/api/jobs \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.0.0' \
  --data '{
	"jobType" : "task-reminders",
	"title": "Remember to pay for wife'\''s mobile",
	"repeat": {
		"eventsAt": ["15-09-2024 23:54", "15-09-2024 23:54", "15-09-2024 23:55", "15-09-2024 23:55", "15-09-2024 23:55"]
	},
	"event": {
		"info": "Airtel 2 to be paid"
	}
}'
</pre>