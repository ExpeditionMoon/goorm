package goorm.board.controller;

import goorm.board.model.request.CommentDeleteRequest;
import goorm.board.model.request.CommentPostRequest;
import goorm.board.model.request.CommentUpdateRequest;
import goorm.board.model.response.BoardResponse;
import goorm.board.model.response.CommentResponse;
import goorm.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 등록
    @PostMapping("comment")
    public BoardResponse writeComment(@RequestBody CommentPostRequest request) {
        return commentService.writeComment(request);
    }

    // 수정
    @PatchMapping("comment")
    public BoardResponse updateComment(@RequestBody CommentUpdateRequest request) {
        return commentService.updateComment(request);
    }

    // 삭제
    @DeleteMapping("comment")
    public String deleteComment(@RequestBody CommentDeleteRequest request) {
        return commentService.deleteComment(request);
    }
}
