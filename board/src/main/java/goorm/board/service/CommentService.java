package goorm.board.service;

import goorm.board.model.DeleteStatus;
import goorm.board.model.entity.Board;
import goorm.board.model.entity.Comment;
import goorm.board.model.request.CommentDeleteRequest;
import goorm.board.model.request.CommentPostRequest;
import goorm.board.model.request.CommentUpdateRequest;
import goorm.board.model.response.BoardResponse;
import goorm.board.model.response.CommentResponse;
import goorm.board.repository.BoardRepository;
import goorm.board.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 등록
    @Transactional
    public BoardResponse writeComment(CommentPostRequest request) {
        // 게시글이 존재하는지 확인
        Optional<Board> boardOptional = boardRepository.findBoardWithCommentsByBoardNo(request.getBoardNo());
        Board board = boardOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        board.addComment(request.getCommentBody());
        boardRepository.save(board);

        return BoardResponse.from(board);
    }

    // 수정
    @Transactional
    public BoardResponse updateComment(CommentUpdateRequest request) {
        // 댓글이 존재하는지 확인
        Optional<Comment> commentOptional = commentRepository
                .findByCommentNoAndBoard_BoardNoAndDeleteStatus(
                        request.getCommentNo(), request.getBoardNo(), DeleteStatus.ACTIVE);
        Comment comment = commentOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));

        comment.setBody(request.getCommentBody());

        commentRepository.save(comment);

        Board board = comment.getBoard();
        boardRepository.save(board);

        return BoardResponse.from(board);
    }

    // 삭제
    public String deleteComment(CommentDeleteRequest request) {
        // 댓글이 존재하는지 확인
        Optional<Comment> commentOptional = commentRepository
                .findByCommentNoAndBoard_BoardNoAndDeleteStatus(
                        request.getCommentNo(), request.getBoardNo(), DeleteStatus.ACTIVE);
        Comment comment = commentOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));

        commentRepository.delete(comment);

        return "OK";
    }
}
