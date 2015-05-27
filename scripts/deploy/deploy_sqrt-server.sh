#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": "sqrt-server",
  "cmd": "",
  "cpus": 1,
  "instances": 1,
  "mem" : 1024,
  "container": {
    "type": "DOCKER",
    "docker": {
      "image": "registry.dmitriyvolk.net:5000/sqrt/sqrt-server",
      "network": "HOST"
    }
  }
}' http://registry.dmitriyvolk.net:8080/v2/apps 