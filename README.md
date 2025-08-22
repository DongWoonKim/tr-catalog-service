## 📚 tr-catalog-service (도서 정보 서비스)

`tr-catalog-service`는 **도서 정보(Catalog)**를 제공하는 서비스로,  
ISBN, 제목, 저자, 출판사, 발행일, 표지 URL 등 도서의 핵심 데이터를 관리합니다.  

이 서비스는 도서 데이터의 **진실의 원천(Source of Truth)** 역할을 하며,  
정확하고 일관된 도서 정보를 다른 서비스(Search 등)나 클라이언트(Web)에 제공합니다.  

이를 통해 사용자는 **도서 목록 조회** 및 **도서 상세 조회** 기능을 안정적으로 이용할 수 있습니다.

---

### 📚 주요 특징 (tr-catalog-service)

- **도서 정보의 진실의 원천 (Source of Truth)**
  - ISBN, 제목/부제, 저자, 출판사, 발행일, 표지 URL 등 도서의 핵심 정보를 관리합니다.   

- **도서 목록/상세 조회 API 제공**
  - 발행일 기준 최신순 목록 조회 API
  - ISBN 기반 도서 상세 조회 API를 제공합니다.(현재는 deprecated된 상태이며 search service가 담당합니다.)

- **데이터 정합성 유지**
  - 도서 정보의 일관성과 정확성을 보장하며, 중복 및 불일치를 방지합니다.
 
### ⚙️ 내부 아키텍처

```bash
com.trevari.spring.trcatalogservice
├─ application
│  └─ BookService.java
├─ config
│  └─ OpenApiConfig.java
├─ domain
│  └─ book
│     ├─ Book.java
│     ├─ BookRepository.java
│     └─ Isbn.java
├─ exception
│  ├─ BookNotFoundException.java
│  ├─ ErrorResponse.java
│  └─ GlobalExceptionHandler.java
├─ infrastructure
│  └─ persistence
│     ├─ BookEntity.java
│     ├─ BookJpaRepository.java
│     └─ BookRepositoryAdapter.java
├─ interfaces
│  ├─ dto
│  │  └─ BookResponseDTO.java
│  ├─ http
│  │  └─ BookController.java
│  └─ mapper
│     └─ BookMapper.java
└─ TrCatalogServiceApplication.java

resources
├─ db
│  └─ migration
│     └─ V2__insert_seed_books.sql
├─ static
├─ templates
└─ application.yml
```

 - application package : 위의 아키텍처에서 application package의 하위 항목들은 비즈니스 로직을 담는 서비스가 들어옵니다.  

 - domain : catalog-service의 순수 도메인 모델을 정의하는 영역입니다.  
            이곳의 BookRepository는 인프라 기술에 의존하지 않는 순수 POJO 인터페이스로 작성되었습니다.
            -> 비즈니스 로직은 순수 도메인 모델에 의존하도록 설계하여, 외부 기술이나 환경 변화로부터 받는 영향을 최소화합니다.  

 - infrastructure/persistence : DB와 직접 맞닿는 영역으로, 도메인 모델과 영속성 기술(JPA 등) 사이를 연결해주는 역할을 합니다.  
    - BookRepositoryAdapter : domain 계층의 **BookRepository 인터페이스(포트)**를 구현한 어댑터입니다.
                              BookJpaRepository를 내부에서 사용하면서도, 도메인 계층은 오직 BookRepository 인터페이스만 바라보도록 만들어 관심사 분리를 달성합니다.
                              -> 즉, 도메인 계층은 JPA가 무엇인지 전혀 몰라도 되고, 나중에 MongoDB, MyBatis 등 다른 기술로 교체해도 도메인 로직은 그대로 유지할 수 있습니다.  

 - interfaces : 외부와의 입출력 경계를 담당하는 영역으로, 도메인 로직을 그대로 노출하지 않고 DTO나 매퍼를 통해 안전하게 전달하는 역할을 합니다.
   - mapper/BookMapper : 도메인 모델(Book) ↔ DTO(BookResponseDTO) 간 변환을 담당합니다.
                         -> 변환 로직을 별도 클래스로 분리함으로써, 컨트롤러나 서비스 로직이 불필요하게 지저분해지는 것을 방지합니다.  

- resources/db/migration : DB 마이그레이션 및 초기 데이터(seed) 관리와 관련된 영역입니다.
  - V2__insert_seed_books.sql : Flyway 마이그레이션 스크립트 파일이며, 이 스크립트는 **초기 도서 데이터(seed data)**를 DB에 삽입하는 역할을 합니다.
                                -? 애플리케이션 기동 후 바로 Catalog API를 통해 도서 데이터를 확인할 수 있도록 해줍니다.

### 📌 고민 사항
1. 내부 아키텍처 설계 : 이부분은 tr-store-infra에서 기재하였으므로 생략하겠습니다.
2. 초기 데이터 처리 
  - 첫 번째 : 애플리케이션이 기동되면 초기 데이터 요청 API를 제공하여 외부 공공데이터를 받아와서 insert후 search service로 이벤트를 발행하여 색인 작업과 데이터를 넘겨준다.  
  - 두 번째 : flyway로 초기 데이터를 넣어 놓고 밀어넣는다. 색인 작업 또한 search service에서 같은 데이터 스크립트를 가지고 밀어넣는다.  
     ✔️ 두 번째 대안 선택 : 이유는 물론 실제 서비스를 가정하고 만들지만 결과물은 제가 서비스를 제공하여 최종 구동된 상태의 서비스를 제공하는 것이 아닌
     사용자가 인프라를 구동해서 확인 해야하는 상황에서 굳이 첫 번째 대안을 선택하여 위험도를 높일 필요가 없다고 생각하였습니다.  
     특히 요구사항 조건에 "최소 100건 이상의 데이터"를 충족하면 되기에 이정도 데이터는 데이터 파이프라인을 구축하기보단 flyway를 통해 직접 밀어 넣는 방식이 더 낫다고 판단하여 선택하였습니다.

### 🔖 테스트 커버리지
 - 테스트 코드 비즈니스 계층(application/**)만 적용 : 일정적인 부분에 있어서 조금 빠듯해서 비즈스로직만 적용하였습니다 외부 입출력 영역은 web-service를 통해 확인할 수 있는 부분이기에 넣지 않았습니다.
 - catalog service 테스트 커버리지 결과
<img width="1086" height="132" alt="스크린샷 2025-08-22 오전 10 34 50" src="https://github.com/user-attachments/assets/c6f1f4f8-47c4-454e-be56-0ea56e2f87a0" />


   








