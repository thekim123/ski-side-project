## 스키 플랫폼 사이드 프로젝트입니다.

- 제가 구현한 부분은 User, Board, Carpool 부분입니다.
- 또한 배포를 담당하였습니다.
- 처음에는 프론트, 스프링웹 서버, 스프링 웹플럭스 서버를 각각 두어서 운영 하려 했으나 
- 비용 부담과 Docker를 아직 잘 다루지 못해 하나의 서버에서 3개의 모듈을 돌리는 것으로 변경하였습니다.

### 1. 기술 스택
- 공통
  - Spring Boot 2.7.5
  - Lombok
  - Java 11
  
- Spring Web Module
  - Spring Web
  - OAuth2
  - MariaDB

- Spring Webflux Module
  - Spring Webflux
  - Reactive MongoDB
  
### 2. Spring - Web (ski-backend)
![img.png](img.png)
- erd입니다.
#### (1) User
- Spring Security + Jwt(auth00) 조합으로 사용자 인증을 구현하였습니다.

#### (2) Board
- 기본적인 게시판 비즈니스 로직으로 제목과 내용으로 이루어져있습니다.
- 좋아요와 싫어요를 누를 수 있고, 좋아요 싫어요 합계 수가 많은 게시글을 추천글로 제공하는 API를 구현했습니다.
- 추천글 API의 경우 아래와 같이 Native Query로 작성하였습니다.
  - ```java
        @Query(value = "SELECT b.* FROM board b INNER JOIN (SELECT boardId, COUNT(boardId) likeCount FROM likes GROUP BY boardId) c ON b.id = c.boardId ORDER BY likeCount DESC", nativeQuery = true)
        List<Board> getPopular();
    ```
- 좋아요의 경우에는 로그인한 사용자가 게시글을 조회할 때 좋아요 상태와 수를 체크하도록 구현 하였습니다.
  - ```java
        // 좋아요와 싫어요 상태를 반영하는 메소드
         public void loadLikesAndDislikes(long principalId) {

            long likeCount = this.getLikes().size();
            long dislikeCount = this.getDislikes().size();
      
            this.setLikeCount(likeCount);
            this.setDislikeCount(dislikeCount);
            this.setTotalLikeCount(likeCount - dislikeCount);
      
            this.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId) {
                    this.setLikeState(true);
                }
            });
      
            this.getDislikes().forEach(dislikes -> {
                if (dislikes.getUser().getId() == principalId) {
                    this.setDislikeState(true);
                }
            });
        }
  }
     ```
  
#### (3) Carpool
- Carpool 역시 게시판 기반의 Entity입니다.
- 다른 점은 협상 가능한 부분을 구현해달라는 요구사항이 있었습니다. 예를 들어 출발시간의 경우 협상이 가능한지 체크하는 기능이 그것입니다.
- 이 부분을 Negotiate라는 Entity를 만들어서 해결하였습니다.
- 카풀을 신청하면 Submit Entity가 저장이 되고, Submit Entity의 state가 default value = 0 으로 저장됩니다.
- 승인을 하면 state = admit으로 되고 완료됩니다.
- 거부할 시에는 거부당한 사용자가 악의적으로 무한신청을 하는 것을 막기 위해서 30분간 신청을 못하도록 막는 기능을 추가하였습니다.
  - ```java
        // 기능 추가 완료시 작성
    ```
- 그리고 카풀의 경우 신청자와 글 작성자 간의 채팅 기능이 제공되는데, Whisper Entity가 그 교두보 역할을 합니다.
- 


### 3. Spring - Webflux (sse-server)
- 채팅 서버입니다.
- Webflux의 경우 netty 기반의 서버를 사용하여 servlet기반의 multi thread 방식보다 우월한 성능을 자랑합니다.
- 처음 기획은 대용량을 핸들할 것이라 했기에 이 기술을 도입하였습니다.
- @Tailable 어노테이션을 사용하여 사용자가 처음 request 한 후에도 response하는 통신을 끊는 것이 아닌 업데이트 사항이 있으면 지속적으로 사용자에게 전송합니다.
- MariaDB를 계속해서 사용하려면 R2DBC를 사용해야 하는데, 이보다는 MongoDB를 새로 사용하는 것이 좋다고 생각했습니다.

## 4. 배포
- 배포의 경우 제가 이번에 한 경험들을 블로그에 작성하였습니다.

#### (1) github action
- 처음 배포 시나리오는 github action - aws elasticbeanstalk을 이용한 CI/CD 였습니다.
- 하지만 Docker를 사용하지 않고 하나의 어플리케이션 플랫폼을 java로 만들시 node기반 어플리케이션을 함께 실행시키는데 어려움이 있었습니다.
- 해서 ec2에 수동으로 배포하는 전략을 채택했습니다.
- 수동으로 배포하는데 손이 너무 많이 가서 배포 스크립트를 작성하였습니다.

#### (2) 수동배포
- git clone ~ 프로그램 실행까지 원스톱 스크립트를 작성했습니다.
- 그러나 build과정에서 cpu 자원을 너무 많이 소모하여 ec2의 cpu 크레딧을 모두 소모하여 멈춰버리는 현상이 발생했습니다.
- 하여 로컬에서 빌드를 자동으로 하는 스크립트를 작성하고,
- 빌드가 된파일을 sftp로 옮긴 후 실행시키는 방식으로 전환하였습니다.

## 5. 프로젝트 후 느낀점
- Docker를 할 줄 알면 좋겠다. ( 배워야겠다. )
- 좀 더 많은 비즈니스 로직을 구현해보고 싶다.
- 프론트앤드 공부를 아예 안하는것은 득보다 실이 많다.
