Source code for presentation on Mesos and friends.
==================================================

Source code
-----------
Source code resides in the `sqrt` directory, and consists of several modules.

- `sqrt-server` - web application, keeps work items and displays results. Registers itself in a Eureka server under the name 'sqrt-server'.
- `sqrt-client` - command-line app that checks out a work item (square root approximation), calculates next approximation and sends it back to the server. Location of the server is discovered via Eureka
- `mesos-framework` - a very simple implementation of a Mesos framework. On receiving an offer from Mesos makes a call to the `sqrt-server` app to see if there are work items to work on. If work items found, creates a Mesos task to launch a container with `sqrt-client`.
