FROM tomcat:10.0-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/sms.war /usr/local/tomcat/webapps/sms.war

EXPOSE 8080

CMD ["catalina.sh","run"]