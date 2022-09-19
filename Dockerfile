FROM openjdk:11
#FROM openjdk:8-jre-alpine

MAINTAINER Ismael Ortiz <ismaeloe@yahoo.com.mx>

ENV SERVER_PORT=8093
#ENV DEBUG_PORT=8093

ENV APP_HOME /opt/app
ENV ARTIFACT_NAME spring-boot.jar

#ENV SPRING_BOOT_USER spring-boot
#ENV SPRING_BOOT_GROUP spring-boot

VOLUME /tmp

# RUN addgroup -S $SPRING_BOOT_USER && adduser -S -g $SPRING_BOOT_GROUP $SPRING_BOOT_USER && \
#  chmod 555 $APP_HOME/entrypoint.sh && sh -c 'touch $APP_HOME/$ARTIFACT_NAME'
 
#EXPOSE 8093
EXPOSE $SERVER_PORT
#EXPOSE $DEBUG_PORT

#WORKDIR $APP_HOME
#USER $SPRING_BOOT_USER

#TODO ONBUILD COPY Vs COPY
#ONBUILD COPY assets/spring-boot.jar $APP_HOME/$ARTIFACT_NAME
#COPY target/webflux_order_service_jpa.jar app.jar
COPY target/webflux_order_service_jpa.jar $APP_HOME/$ARTIFACT_NAME

ENTRYPOINT ["java", "-jar", "/spring-boot.jar"]