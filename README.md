# TiTi Backend
티티 백엔드

## Links
|            | url     |
|------------|---------|
| dev docs   | (추가 예정) |

## Getting Started

### Env
- [JDK 17](https://github.com/TimerTiTi/backend/wiki/JDK-17%EB%A1%9C-%EC%97%85%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%9C-%ED%95%98%EA%B8%B0)

### Setting local environment

```
$ docker-compose up && docker-compose rm -fsv
```

## 프로젝트 구성

```
.
├── .github                     # Github Template
├── titi-adapter                # 시스템 외부로 나가는 구체적인 동작을 구현한 어댑터 모듈
├── titi-api                    # TiTi API 애플리케이션 모듈
├── titi-application            # 도메인에 접근, 비즈니스 로직 등을 담당하며 in, out 포트를 제공하는 모듈
├── titi-common                 # 공통으로 사용되는 코드와 유틸리티를 모아둔 모듈
├── titi-data                   # 데이터 저장을 위해 Jpa 구현체를 모아둔 모듈
├── titi-domain                 # 토큰, 알림 등 도메인 모듈
├── sql                         # titi DB 스키마 관리용
├── docker-compose.yml          # Mysql 로컬 환경 구성을 위한 스크립트
├── build.gradle
├── settings.gradle
├── versions.properties
└── README.md
```
