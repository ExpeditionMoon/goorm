package goorm.tricount.controller;

import goorm.tricount.dto.SettlementRequest;
import goorm.tricount.model.ApiResponse;
import goorm.tricount.model.Settlement;
import goorm.tricount.service.SettlementService;
import goorm.tricount.util.MemberContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settles")
public class SettlementController {
    private final SettlementService settlementService;

    // 새로운 정산 생성
    @PostMapping("/create")
    public ApiResponse<Settlement> createSettlement(@Valid @RequestBody SettlementRequest settlementRequest) {
        return new ApiResponse<Settlement>().ok(
                settlementService.createAndJoinSettlement(
                        settlementRequest.getSettlementName(), MemberContext.getCurrentMember()));
    }

    // 정산 참여
    @PostMapping("/{id}/join")
    public ApiResponse joinSerrlement(@PathVariable("id") Long settlementId) {
        settlementService.joinSettlement(settlementId, MemberContext.getCurrentMember().getId());
        return new ApiResponse().ok();
    }
}
