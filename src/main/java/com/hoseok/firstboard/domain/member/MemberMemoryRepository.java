package com.hoseok.firstboard.domain.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MemberMemoryRepository implements MemberRepository {

    private static HashMap<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //id 값 올린 후 멤버 객체에 저장
        store.put(sequence, member); // key를 id로 객체와 함께 put
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        //filter 사용 -> 스트림으로 하나씩 보면서 필터로 함수로 조건 조회 후 findAny() 찾으면 옵셔널로 감싼 값 or 옵셔널로 감싼 null 반환
        Optional<Member> member = findAll().stream().filter(m ->
                        m.getLoginId().equals(loginId))
                .findAny();
        return member;
    }

    @Override
    public Optional<Member> findByNameAndEmailAndPhoneNumber(String name, String email, String phoneNumber) {
        Optional<Member> member = findAll().stream().filter(m -> m.getName().equals(name)) //이름으로 찾은 것들 중에서 내러서
                .filter(m -> m.getEmail().equals(email)) // 이름이 맞는 것들 중(동명이인 존재 가능성) 이메일로 거르고
                .filter(m -> m.getPhoneNumber().equals(phoneNumber)) // 마지막으로 폰번호까지 확인 후 반환
                .findAny(); // 필터 체이닝으로 쓰면 나중에 조건 삭제 등 유지보수 시 유리함 &&를 사용하면 코드가 길어짐
        return member;
    }

    @Override
    public Optional<Member> findByLoginIdAndNameAndEmailAndPhoneNumber(String loginId, String name, String email, String phoneNumber) {
        Optional<Member> member = findAll().stream().filter(m -> m.getLoginId().equals(loginId)) //로그인 아이디 찾고
                .filter(m -> m.getName().equals(name)) //이름으로 찾은 것들 중에서 내러서
                .filter(m -> m.getEmail().equals(email)) // 이름이 맞는 것들 중(동명이인 존재 가능성) 이메일로 거르고
                .filter(m -> m.getPhoneNumber().equals(phoneNumber)) // 마지막으로 폰번호까지 확인 후 반환
                .findAny(); // 필터 체이닝으로 쓰면 나중에 조건 삭제 등 유지보수 시 유리함 &&를 사용하면 코드가 길어짐
        return member;
    }

    @Override
    public List<Member> findAll() {
        ArrayList<Member> list = new ArrayList<>(store.values()); // store.values()는 Collection 반환 캐스팅 안됨
                                                                // + 방어적 복사(내부 저장소 변복조 방지)
        return list;
    }

    @Override
    public void clearStore() {
        store.clear();
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }
}
