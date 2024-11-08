FROM openjdk:17-jdk-slim-buster

# Python 경로 설정
ENV PYTHON_PATH=/usr/bin/python3
ENV PATH="${PYTHON_PATH}:${PATH}"

# 빌드된 jar 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=docker"]

