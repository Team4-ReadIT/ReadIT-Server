> [프로젝트 설명 바로 가기](https://github.com/Team4-ReadIT)

## 👥 𝗠𝗲𝗺𝗯𝗲𝗿𝘀 𝗮𝗻𝗱 𝗥𝗼𝗹𝗲𝘀

| 이름        | 역할               | 담당 파트           |
|-------------|--------------------|---------------------|
| [노경희](https://github.com/khee2) | Frontend            | 기사 추천, 마인드맵, 형광펜 및 메모  |
| [이채영](https://github.com/alwaysY0ung) | Frontend            | 로그인, 회원가입  |

</br>

## 📂 𝗣𝗿𝗼𝗷𝗲𝗰𝘁 𝗙𝗼𝗹𝗱𝗲𝗿 𝗦𝘁𝗿𝘂𝗰𝘁𝘂𝗿𝗲

```plaintext
ReadIT-Server
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.team4.readit
│   │   │       ├── domain                     # 주요 도메인 로직
│   │   │       │   ├── article                # 기사 관련 도메인
│   │   │       │   │   ├── client             # 외부 API 호출 관련 클래스
│   │   │       │   │   ├── dto                
│   │   │       │   │   ├── controller         
│   │   │       │   │   ├── domain             
│   │   │       │   │   │   ├── repository     
│   │   │       │   │   │   ├── Article.java   # 게시글 엔티티
│   │   │       │   │   │   └── Keyword.java   # 키워드 엔티티
│   │   │       │   │   └── service            
│   │   │       │   ├── article_view           # 기사 조회 관련 도메인
│   │   │       │   ├── highlight              # 하이라이트 및 메모 관련 도메인
│   │   │       │   ├── job                    # 직무 관련 도메인
│   │   │       │   ├── mindmap                # 기사 마인드맵 관련 도메인
│   │   │       │   ├── scrap                  # 기사 스크랩 관련 도메인
│   │   │       │   └── user_info              # 멤버 관련 도메인
│   │   │       ├── global                     # 전역 설정 및 유틸리티
│   │   │       │   ├── config                 # 전역 설정 (JWT 등)
│   │   │       │   ├── converter              # DTO 변환을 위한 변환기 클래스
│   │   │       │   ├── exception              # 공통 예외 처리 핸들러
│   │   │       │   ├── response               # 공통 응답 구조 정의
│   │   │       │   ├── type                   # 소프트 삭제 상태 정의 (enum)
│   │   │       │   └── BaseEntity             # 생성일자, 수정일자 공통 엔티티 클래스
│   │   └── resources
│   │       ├── application-docker.yml         # Docker 환경 설정 파일
├── build.gradle                               # Gradle 빌드 설정 파일
├── Dockerfile                                 # Docker 컨테이너 설정 파일
├── README.md                                  
└── settings.gradle                            

```

</br>

## ✨ 𝗕𝗿𝗮𝗻𝗰𝗵 𝗖𝗼𝗻𝘃𝗲𝗻𝘁𝗶𝗼𝗻𝘀

| **브랜치 유형**      | **설명**                                                                 |
|--------------------|-------------------------------------------------------------------------|
| `develop`             | 최종 개발 코드                                                |
| `feature/<이슈번호>-<기능명>`  | 새로운 기능 개발 브랜치                                                  |

</br>
