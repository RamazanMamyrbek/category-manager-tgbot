FROM openjdk:21-oracle
COPY target/*.jar category-manager-tgbot.jar
ENTRYPOINT ["java", "-jar", "category-manager-tgbot.jar"]