## Connectist
과학기술원을 포함한 과학기술 특성화대학(KAIST,POSTECH,GIST,DGIST,UNIST,KENTCH) 을 통합하는 커뮤니티 앱 제작한다.

## 목적
과기원 학생들만의 커뮤니티 앱을 개설하여, 다양한 정보(취업, 대학원) 등을 교류하고 연합 동아리를 포함한 여러 기능들을 제공하여 학생들이 다양한 사람들과 의사소통을 할 수 있도록 도와주기 위해 앱을 제작한다.

## 기술적 목표
- DDD 방식을 통한 아키텍처 설계한다
- GitHub Actions을 통한 CI/CD 파이프라인 구축한다
- 소형, 중형, 대형테스트로 분리하여 테스트코드의 속도와 가독성을 높인다
- Code Review, PR, Issue을 적극 활용하여 협업방식을 익힌다

단단한 **아키텍쳐를 설계**하고, **새로운 기술**들을 도입하여 **성능**적으로 개선하며 , **Git**을 사용한 체계적인 **협업**을 목표로 한다. 
단기적으로 빨리 끝내는게 목표가 아니다. **장기적인 관점**에서 앱을 바라보고, 지속적으로 **유지보수**가 가능한 앱을 만드는 것을 목적으로 한다.

## 기술스택

백엔드: Spring Boot 3.3, Spring Cloud 

배포 : AWS, Github Actions 

DB: MongoDB, MySQL, Redis

메세지 큐 : Kafka

## 배포

### 배포 아키텍처 
![Untitled (9)](https://github.com/connetist/connectist-backend/assets/89409079/dbc2c857-3efc-45d4-b409-9d8dcb3d8a4c)

### 상세 배포과정

![Untitled (10)](https://github.com/connetist/connectist-backend/assets/89409079/1ed58bb6-aab1-41cd-a9e4-0e784e49beda)


## DDD 설계 과정

### Bounded Context 

<img src="https://github.com/connetist/connectist-backend/assets/89409079/59c08adf-e39c-46d0-aed6-9f9b681dca51"  width="200" height="200"/>

### Context Map
<img src="https://github.com/connetist/connectist-backend/assets/89409079/6ef9b459-4894-4be6-b59f-01f41c8031ce"  width="400" height="200"/>

### Aggregate Map 

<img src="https://github.com/connetist/connectist-backend/assets/89409079/b721821b-94de-442d-9cab-492abb86d26e"  width="700" height="700"/>


