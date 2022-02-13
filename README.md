# Spring Boot JPA Project
스프링부트를 이용한 JPA의 간편한 CRUD 기능을 구현한 프로젝트이다. 해당 프로젝트는 Backend API Server의 역할만을 하며 프로젝트 주요 내용은 게시글과 해당 글에 대한 댓글을 등록 할 수 있는 구조로 되어있다.
> 로그인 미구현으로 인한 권한 레벨이 없기에 각 게시물과 댓글의 삭제 변경에 대한 권한은 작성자에 있으므로 이부분 참고하기 바란다.

![DB 구성도](https://user-images.githubusercontent.com/33931721/153780809-0d9a27f2-e872-40b1-ba25-716a6d2b07e9.png)
## 프로젝트 구성
+ SpringBoot
+ SpringRestDocs
+ JPA
+ JDK11
+ Gradle
+ Junit
## 프로젝트 빌드
    ./gradlew clean build
## API 문서
http://localhost:8888/docs/api-guide.html
