FROM openjdk:17-jdk-slim-buster

# Python과 pip 설치
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

# Python 경로 설정
ENV PYTHON_PATH=/usr/bin/python3
ENV PATH="${PYTHON_PATH}:${PATH}"

# 필요한 Python 패키지 설치 (requirements.txt가 있는 경우)
COPY requirements.txt .
RUN pip3 install --no-cache-dir -r requirements.txt

# 빌드된 jar 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=docker"]

