# WARNING!  I am on a low-bandwidth internet connection and have not been able to build this
# image myself yet.  Almost certainly it won't work, but the ideas are right


# Use as are base image a linux with the java8 runtime already installed
FROM openjdk:8

# Add our application logic and ALL our dependencies into the docker image
ADD  build/distributions/skeleton.tar  /

# Convenience if we ever want to log into the image and snoop around
ADD appconfig.yml /skeleton/


# The server is going to run on 8080 inside the running container, so we need to expose that port
EXPOSE 8080

# When a new container is created, the server program should be run.
ENTRYPOINT ["/skeleton/bin/skeleton","server", "appconfig.yml"]
