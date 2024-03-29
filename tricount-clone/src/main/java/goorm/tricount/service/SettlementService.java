package goorm.tricount.service;

import goorm.tricount.model.Member;
import goorm.tricount.model.Settlement;
import goorm.tricount.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;

    // 생성 및 다른 멤버와 조인
    @Transactional
    public Settlement createAndJoinSettlement(String settlementName, Member member) {
        // TODO 예외 처리

        // 새로운 정산 생성
        Settlement settlement = settlementRepository.create(settlementName);
        // 해당 사용자를 정산 참여자에 추가(요구사항) - 중간 테이블
        settlement.setParticipants(Collections.singletonList(member));
        settlementRepository.addParticipantToSettlement(settlement.getId(), member.getId());

        return settlement;
    }

    // 다른 멤버와 조인
    @Transactional
    public void joinSettlement(Long settlementId, Long memberId) {
        // TODO 예외 처리 - 없는 정산이나 유저에 대해서 요청을 보낸 경우

        settlementRepository.addParticipantToSettlement(settlementId, memberId);
    }
}
