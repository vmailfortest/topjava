# CURL REQUESTS

## get
curl http://localhost:8080/topjava_war_exploded/rest/meals/100005

## getAll
curl http://localhost:8080/topjava_war_exploded/rest/meals

## getBetween2param
curl "http://localhost:8080/topjava_war_exploded/rest/meals?startDateTime=2015-05-30T13:00:00&endDateTime=2015-05-30T23:00:00"

## getBetween4param
curl "http://localhost:8080/topjava_war_exploded/rest/meals?startDate=2015-05-30&startTime=13:00&endDate=2015-05-30&endTime=22:00"

## create
curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\": \"2019-11-27T10:10:00\", \"description\": \"curlCreate\", \"calories\":\"150\"}" http://localhost:8080/topjava_war_exploded/rest/meals

## update
curl -X PUT -H "Content-Type: application/json" -d "{\"dateTime\": \"2019-11-27T10:15:00\", \"description\": \"curlUpdate\", \"calories\":\"150\"}" http://localhost:8080/topjava_war_exploded/rest/meals/100017

## delete
curl -X DELETE http://localhost:8080/topjava_war_exploded/rest/meals/100019
