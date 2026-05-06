#  Spring MVC2 실무 기술 저장소 (First-Board)

본 프로젝트는 김영한님의 Spring MVC2강의의 내용을 복습하고 본인 프로젝트로 이식하기 위한 프로젝트입니다.

## 핵심 구현 및 학습 성과 (Section 9: Validation)

### 1. 전용 DTO를 활용한 데이터 무결성 확보
- **문제 인식**: 도메인 엔티티(`Board`)를 외부에 직접 노출할 경우, 불필요한 필드 노출 및 데이터 오염의 위험이 있음을 인지했습니다.
- **해결책**: 등록(`BoardSaveForm`)과 수정(`BoardUpdateForm`) 목적에 맞는 전용 DTO를 설계하여 데이터 수집의 책임을 명확히 분리했습니다.

### 2. Spring 통합 검증 체계(Bean Validation) 구축
- **표준화**: `@NotBlank`, `@Size` 등 표준 어노테이션을 사용하여 코드의 가독성을 높이고 선언적인 검증 로직을 구현했습니다.
- **예외 설계**: `BindingResult`를 활용해 검증 실패 시 즉각적으로 사용자에게 피드백을 전달하는 'Fail-Fast' 구조를 적용했습니다.
- **UX 최적화**: 부트스트랩 CSS와 Thymeleaf 속성(`th:errorclass`, `th:errors`)을 결합하여 가시성 높은 에러 핸들링을 구현했습니다.

### 3. 유연한 메시지 관리 전략
- `errors.properties`를 도입하여 에러 메시지를 코드와 분리, 다국어 처리 및 유지보수가 용이한 구조를 설계했습니다.