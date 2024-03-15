package goorm.board.service;

import goorm.board.model.DeleteStatus;
import goorm.board.model.entity.Board;
import goorm.board.model.request.BoardDeleteRequest;
import goorm.board.model.request.BoardPostRequest;
import goorm.board.model.request.BoardUpdateRequest;
import goorm.board.model.response.BoardListResponse;
import goorm.board.model.response.BoardResponse;
import goorm.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    // 등록
    @Transactional
    public BoardResponse writeBoard(BoardPostRequest request) {
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setBody(request.getBody());
        board.setDeleteStatus(DeleteStatus.ACTIVE);

        return BoardResponse.from(boardRepository.save(board));
    }

    // 목록 조회
    public List<BoardListResponse> searchBoardList(int page, int pageSize) {
        return boardRepository.findAllByDeleteStatus(
                DeleteStatus.ACTIVE,
                PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "boardNo"))
        ).map(BoardListResponse::from).toList();
    }

    // 게시글 단건 조회
    public BoardResponse searchBoard(Long boardNo) {
        return boardRepository.findBoardWithCommentsByBoardNo(boardNo)
                .map(BoardResponse::from)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    // 수정
    @Transactional
    public BoardResponse updateBoard(BoardUpdateRequest request) {
        // 게시글이 존재하는지 확인
        Optional<Board> boardOptional = boardRepository.findById(request.getBoardNo());
        Board board = boardOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        board.setTitle(request.getTitle());
        board.setBody(request.getBody());

        boardRepository.save(board);

        return BoardResponse.from(board);
    }

    // 삭제
    @Transactional
    public String deleteBoard(BoardDeleteRequest request) {
        Optional<Board> boardOptional = boardRepository.findById(request.getBoardNo());
        Board board = boardOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        boardRepository.delete(board);

        return "OK";
    }
}
