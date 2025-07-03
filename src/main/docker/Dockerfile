# 기본 이미지: Java 17 사용
FROM openjdk:17-jdk-slim
# 작업 디렉토리 설정
WORKDIR /app
# JAR 파일 복사
COPY ../../../build/libs/spring-learning-2025-05-30-0.0.1-SNAPSHOT.jar app.jar
# 컨테이너 시작 시 실행 명령
#ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
ENTRYPOINT ["java", "-jar", "app.jar"]