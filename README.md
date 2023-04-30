# 🪧 Q&A 게시판 만들기
커피 주문 어플리케이션을 활용하여, 등록된 회원이 질문을 남기면 관리자가 답변해줄 수 있는 게시판을 구현한다.

## ☑️ 기능 구현 목록
#### 엔티티 설계
- 필드 설정
  - [x] member
  - [x] admin
  - [x] question
  - [x] answer
- 테이블 설정
  - [x] member
  - [x] admin
  - [x] question
  - [x] answer
- 연관관계 매핑
  - [ ] member
  - [ ] admin
  - [ ] question
  - [ ] answer
#### 리포지토리 설계
#### 서비스단 설계
- 등록
  - 이메일 전송
- 조회
- 삭제
#### dto 설계
- validation
  - [ ] member
  - [ ] admin
  - [ ] question
  - [ ] answer
#### mapper 설계
#### 컨트롤러 설계

## 🚀 요구사항 명세서
#### 회원이 커피 매장에 질문을 등록하는 기능
- [ ]  질문은 회원(고객)만 등록할 수 있다.
  - [ ] 회원은 회원의 상태 값이 필요하다.
    - MEMBER_ACTIVE - 활동중
    - MEMBER_SLEEP - 휴면 상태
    - MEMBER_QUIT - 탈퇴 상태
- [ ]  질문 등록시 등록 날짜가 생성 되어야 한다.
- [ ]  질문은 질문의 상태 값이 필요하다
    - QUESTION_REGISTERED- 질문 등록 상태
    - QUESTION_ANSWERED - 답변 완료 상태
    - QUESTION_DELETED - 질문 삭제 상태
    - QUESTION_DEACTIVATED - 질문 비활성화 상태: 회원 탈퇴 시, 질문 비활성화 상태
- [ ]  질문 등록 시, 초기 상태 값은 QUESTION_REGISTERED 이어야 한다.
- [ ]  질문 제목과 내용은 필수입력 사항이다.
- [ ]  질문은 비밀글과 공개글 둘 중에 하나로 설정되어야 한다.
    - PUBLIC - 공개글 상태
    - SECRET - 비밀글 상태

#### 회원이 등록한 1건의 질문을 조회하는 기능
- [ ]  1건의 특정 질문은 회원(고객)과 관리자 모두 조회할 수 있다.
- [ ]  비밀글 상태인 질문은 질문을 등록한 회원(고객)과 관리자만 조회할 수 있다.
- [ ]  1건의 질문 조회 시, 해당 질문에 대한 답변이 존재한다면 답변도 함께 조회되어야 한다.
- [ ]  이미 삭제 상태인 질문은 조회할 수 없다.

#### 회원이 등록한 1건의 질문을 삭제하는 기능
- [ ]  1건의 질문은 회원(고객)만 삭제할 수 있다.
- [ ]  1건의 질문 삭제는 질문을 등록한 회원만 가능하다.
- [ ]  질문 삭제 시, 테이블에서 row 자체가 삭제되는 것이 아니라 질문 상태 값이(QUESTION_DELETE)으로 변경되어야 한다.
- [ ]  이미 삭제 상태인 질문은 삭제할 수 없다.

#### 회원이 등록한 1건의 질문에 대한 답변을 관리자가 수정하는  기능
- [ ]  등록된 답변의 내용은 답변을 등록한 관리자만 수정할 수 있어야 한다.

#### 회원이 등록한 1건의 질문에 대한 답변을 관리자가 삭제하는 기능
- [ ]  답변은 관리자만 삭제할 수 있다.
- [ ]  답변 삭제 시, 테이블에서 row 가 완전히 삭제되도록 한다.(기능 난이도를 고려한 요구사항)

#### 회원이 등록한 1건의 질문에 관리자가 답변을 등록하는 기능
- [ ]  답변은 관리자만 등록할 수 있다.
- [ ]  답변은 관리자가 한 건만 등록할 수 있다.
- [ ]  답변 등록시 답변 등록 날짜가 생성 되어야 한다.
- [ ]  답변이 등록되면 , 질문의 상태 값은 QUESTION_ANSWERED로 변경되어야 한다.
- [ ]  답변 내용은 필수입력 사항이다.
- [ ]  답변의 경우 질문이 비밀글이면 답변도 비밀글이 되어야 하고, 질문이 공개글이면 답변도 공개글이 되어야 한다.

#### 회원이 등록한 질문을 수정하는 기능
- [ ]  등록된 질문의 제목과 내용은 질문을 등록한 회원(고객)만 수정할 수 있어야 한다.
- [ ]  회원이 등록한 질문을 비밀글로 변경할 경우, QUESTION_SECRET 상태로 수정되어야 한다.
- [ ]  질문 상태 중에서 QUESTION_ANSWERED 로의 변경은 관리자만 가능하다.
- [ ]  회원이 등록한 질문을 회원이 삭제할 경우, QUESTION_DELETED 상태로 수정되어야 한다.
- [ ]  답변 완료된 질문은 수정할 수 없다.

#### 회원이 등록한 여려 건의 질문을 조회하는 기능
- [ ]  여러 건의 질문 목록은 회원(고객)과 관리자 모두 조회할 수 있다.
- [ ]  삭제 상태가 아닌 질문만 조회할 수 있다.
- [ ]  여러 건의 질문 목록에서 각각의 질문에 답변이 존재한다면 답변도 함께 조회할 수 있어야 한다.
- [ ]  여러 건의 질문 목록은 페이지네이션 처리가 되어 일정 건수 만큼의 데이터만 조회할 수 있어야 한다.
- [ ]  여러 건의 질문 목록은 아래의 조건으로 정렬해서 조회할 수 있어야 한다.
   - [ ]  최신글 순으로
   - [ ]  오래된 글 순으로
   - [ ]  좋아요가 많은 순으로(좋아요 구현 이후 적용)
   - [ ]  좋아요가 적은 순으로(좋아요 구현 이후 적용)
   - [ ]  조회수가 많은 순으로(조회수 구현 이후 적용)
   - [ ]  조회수가 적은 순으로(조회수 구현 이후 적용)

#### 커피 주문 애플리케이션의 Q&A 게시판에 적용되는 공통 기능
- [ ]  Q&A 게시판은 회원 전용 게시판이다.
   - [ ]  따라서 회원으로 등록한 회원만 해당 Q&A 게시판 기능을 이용할 수 있다.
- [ ]  Spring Security를 학습하지 않았으므로 별도의 회원 로그인 인증 과정은 없는 것으로 가정한다. (Spring Security를 적용해도 무방하다)
- [ ]  `admin@gmail.com`만 관리자의 역할을 하며, 애플리케이션에 상수 형태로 고정해서 사용해도 무방하다.

#### 최신 글
- [ ]  UI에서 최신글임을 알 수 있도록 'new' 같은 아이콘 등으로 표시할 수 있도록 상태값이 필요하다.
- [ ]  최신글의 기준은 질문이 등록일을 기준으로 2일이다. 즉, 질문 등록일 기준으로 2일이 지나면 최신글이 아니다.

#### 첨부파일 업로드
- [ ]  질문 등록 시, 첨부파일을 업로드 할 수 있어야 한다.
- [ ]  첨부 파일은 JPEG/PNG/GIF 이미지만 업로드 가능해야 한다.

#### 질문을 특정 검색 조건에 따라 검색하는 기능
- [ ]  질문을 검색할 수 있어야 한다
- [ ]  검색 조건
   - [ ]  키워드

#### 좋아요 등록
- [ ]  질문 글에 `좋아요`를 추가할 수 있어야 한다.
- [ ]  한 건의 질문에 `좋아요`를 한번만 추가할 수 있어야 한다.
- [ ]  `좋아요`의 상태는 사용자가 변경할 수 있어야 한다.
- [ ]  질문 글에 좋아요의 개수를 표시할 수 있어야 한다.

#### 조회 수
- [ ]  질문 글의 조회 수를 알 수 있어야 한다.
- [ ]  회원(고객) 또는 관리자가 질문을 확인할 때마다 질문 글의 조회수는 증가해야 한다.

#### 알림
- [ ]  질문 등록 시, 관리자는 질문 등록에 대한 알림을 받을 수 있어야 한다.
   - [ ]  관리자는 질문 등록에 대한 알림을 이메일로 받을 수 있어야 한다.
- [ ]  답변 등록 시, 회원(고객)은 답변 등록에 대한 알림을 받을 수 있어야 한다.
   - [ ]  회원(고객)은 답변 등록에 대한 알림을 이메일로 받을 수 있어야 한다.