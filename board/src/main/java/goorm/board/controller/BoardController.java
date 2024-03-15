package goorm.board.controller;

import goorm.board.model.request.BoardDeleteRequest;
import goorm.board.model.request.BoardPostRequest;
import goorm.board.model.request.BoardUpdateRequest;
import goorm.board.model.response.BoardListResponse;
import goorm.board.model.response.BoardResponse;
import goorm.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 등록
    @PostMapping("board")
    public BoardResponse writeBoard(@RequestBody BoardPostRequest request) {
        return boardService.writeBoard(request);
    }

    // 목록 조회
    @GetMapping("boards")
    public List<BoardListResponse> searchBoardList(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize
    ) {
        return boardService.searchBoardList(page, pageSize);
    }

    // 게시글 단건 조회
    @GetMapping("board")
    public BoardResponse searchBoard(@RequestParam("boardNo") Long boardNo) {
        return boardService.searchBoard(boardNo);
    }

    // 수정
    @PatchMapping("board")
    public BoardResponse updateBoard(@RequestBody BoardUpdateRequest request) {
        return boardService.updateBoard(request);
    }

    // 삭제
    @DeleteMapping("board")
    public String deleteBoard(@RequestBody BoardDeleteRequest request) {
        return boardService.deleteBoard(request);
    }
}
