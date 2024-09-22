# Spring Boot Single Module Template

이 프로젝트는 **Spring Boot** 기반의 싱글 모듈 템플릿으로, **3-tier Layered Architecture** 패키지 구조를 채택하여 확장성과 유지보수성을 높였습니다. 새로운 프로젝트를 빠르게 시작할 수 있도록 기본 설정과 구조를 제공하며, 주요 기능을 쉽게 추가할 수 있도록 설계되었습니다.

## Development Stack

- Language: Java 17
- Framework: Spring Boot 3.3.4
- Database: PostgreSQL
- Build: Gradle

## Package Structure

```text
├── postman                          
└── src
    ├── main
    │   ├── java
    │   │   └── com.example.demo
    │   │       ├── api                 # Presentation Layer
    │   │       │   ├── filter
    │   │       │   ├── handler
    │   │       │   ├── request
    │   │       │   └── response
    │   │       ├── domain              # Application Layer
    │   │       │   └── post            # - Domain
    │   │       ├── infrastructure      # Infrastructure Layer
    │   │       │   ├── cache           # - Cache
    │   │       │   ├── database        # - Database
    │   │       │   └── slack           # - Slack
    │   │       └── support             
    │   │           └── error
    │   └── resources
    │       ├── static
    │       └── templates
    └── test                            # 테스트
```

## Installation and Getting Started

TODO

## Configuration

TODO

## License

이 프로젝트는 [MIT 라이선스](LICENSE)를 따릅니다. 자세한 내용은 `LICENSE` 파일을 참고하세요.

## Contact

프로젝트에 대한 질문이나 피드백은 [ttggbbgg2@gmail.com](mailto:ttggbbgg2@gmail.com)으로 연락해 주세요.
