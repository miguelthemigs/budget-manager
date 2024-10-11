FROM gradle:7.5.0-jdk17

WORKDIR /opt/app

COPY ./build/libs/budget-manager-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar budget-manager-0.0.1-SNAPSHOT.jar"]