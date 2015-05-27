#!/bin/bash
#url=`curl -v --silent http://localhost:8761/eureka/apps/SQRT-SERVER/ 2>&1 | grep homePageUrl |sed -r 's/ *<[/]*homePageUrl>//g'`; curl -X POST $url/newinput/random
curl -X POST -H "Content-Type: application/json" http://registry.dmitriyvolk.net:4400/scheduler/iso8601 -d'{
        "name": "random-input-server",
        "schedule": "R20/2015-05-23T02:01:00Z/PT30S",
        "scheduleTimeZone": "America/Los_Angeles",
        "command": "/usr/bin/curl -X POST 'http://ec2-52-25-169-1.us-west-2.compute.amazonaws.com:31080/newinput/random'",
        "epsilon":  "PT15M",
        "owner": "dmitriy.volk@gmail.com"
}'

