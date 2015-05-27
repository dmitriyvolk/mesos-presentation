#!/bin/bash
#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d '{
  "id": "sqrt-mesos-framework",
  "cmd": "",
  "cpus": 0.5,
  "instances": 1,
  "mem" : 512,
  "container": {
    "type": "DOCKER",
    "docker": {
      "image": "registry.dmitriyvolk.net:5000/sqrt/mesos-framework",
      "network": "HOST"
    }
  }
}' http://registry.dmitriyvolk.net:8080/v2/apps 