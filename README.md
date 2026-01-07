# ✍️ 𝗥𝗲𝗮𝗱𝗜𝗧-𝗦𝗲𝗿𝘃𝗲𝗿 ✍️

> [ReadIT 프로젝트 설명 바로 가기](https://github.com/Team4-ReadIT)

</br> 

## 👥 𝗠𝗲𝗺𝗯𝗲𝗿𝘀 𝗮𝗻𝗱 𝗥𝗼𝗹𝗲𝘀

| 이름        | 역할               | 담당 파트           |
|-------------|--------------------|---------------------|
| [노경희](https://github.com/khee2) | Backend            | 기사 추천, 마인드맵, 형광펜 및 메모  |
| [이채영](https://github.com/alwaysY0ung) | Backend            | 로그인, 회원가입  |

</br>

## 📂 𝗣𝗿𝗼𝗷𝗲𝗰𝘁 𝗙𝗼𝗹𝗱𝗲𝗿 𝗦𝘁𝗿𝘂𝗰𝘁𝘂𝗿𝗲

```plaintext
ReadIT-Server
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.team4.readit
│   │   │       ├── domain                     
│   │   │       │   ├── article                
│   │   │       │   │   ├── client             
│   │   │       │   │   ├── dto                
│   │   │       │   │   ├── controller         
│   │   │       │   │   ├── domain             
│   │   │       │   │   │   ├── repository     
│   │   │       │   │   │   ├── Article.java   
│   │   │       │   │   │   └── Keyword.java   
│   │   │       │   │   └── service            
│   │   │       │   ├── article_view           
│   │   │       │   ├── highlight              
│   │   │       │   ├── job                    
│   │   │       │   ├── mindmap                
│   │   │       │   ├── scrap                  
│   │   │       │   └── user_info             
│   │   │       ├── global                    
│   │   │       │   ├── config           
│   │   │       │   ├── converter             
│   │   │       │   ├── exception            
│   │   │       │   ├── response               # 공통 응답 구조 정의
│   │   │       │   ├── type                   # 소프트 삭제 상태 정의 (enum)
│   │   │       │   └── BaseEntity             # 생성일자, 수정일자 공통 엔티티 클래스
│   │   └── resources
│   │       ├── application-docker.yml        
├── build.gradle                              
├── Dockerfile                               
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
