#https://github.com/marketplace/actions/build-and-push-docker-images
#https://hub.docker.com/_/eclipse-temurin
#https://stackoverflow.com/questions/53375613/why-is-the-java-11-base-docker-image-so-large-openjdk11-jre-slim
#https://www.youtube.com/watch?v=NppkHKvnrqc
#https://medium.com/geekculture/deploy-your-spring-boot-java-application-to-aws-ec2-using-github-actions-and-docker-e28c456a4b1a

#FROM openjdk:8-jre-alpine 84 MB
#FROM openjdk:11-jre-slim: 283 MB
#ok FROM openjdk:11			354.48 MB
#FROM eclipse-temurin:11 	?
FROM eclipse-temurin:11		

MAINTAINER Ismael Ortiz <ismaeloe@yahoo.com.mx>

ENV SERVER_PORT=8093
#ENV DEBUG_PORT=8093

#ok ENV APP_HOME /opt
ENV APP_HOME /opt/app
ENV ARTIFACT_NAME spring-boot.jar

#Create Dir
RUN mkdir $APP_HOME

#ENV SPRING_BOOT_USER spring-boot
#ENV SPRING_BOOT_GROUP spring-boot

VOLUME /tmp

# RUN addgroup -S $SPRING_BOOT_USER && adduser -S -g $SPRING_BOOT_GROUP $SPRING_BOOT_USER && \
#  chmod 555 $APP_HOME/entrypoint.sh && sh -c 'touch $APP_HOME/$ARTIFACT_NAME'
 
#EXPOSE 8093
EXPOSE $SERVER_PORT
#EXPOSE $DEBUG_PORT

WORKDIR $APP_HOME
#USER $SPRING_BOOT_USER

#TODO ONBUILD COPY Vs COPY
#ONBUILD COPY assets/spring-boot.jar $APP_HOME/$ARTIFACT_NAME
#COPY target/webflux_order_service_jpa.jar app.jar
COPY target/webflux_order_service_jpa.jar $APP_HOME/$ARTIFACT_NAME

#configure a container that will run as an executable
ENTRYPOINT ["java", "-jar", "spring-boot.jar"]


#BUILD docker build -t webflux-order-jpa-h2:v1 .

#TODO
#BUILD docker build -t webflux-order-jpa-h2:openjdk-11 .
#BUILD docker build -t webflux-order-jpa-h2:openjdk-11-jre-slim .
#BUILD docker build -t webflux-order-jpa-h2:eclipse-temurin-11 .

#PUSH docker push ismaeloe/webflux-order-jpa-h2:tagname

#PULL docker pull ismaeloe/webflux-order-jpa-h2:latest

#RUN => docker run -it --rm -p 8093:8093 webflux-order-jpa-h2:v1
#RUN => docker exec -it test ps aux