package goorm.board.model.request;

import lombok.Data;

@Data
public class CommentUpdateRequest {
    private Long boardNo;
    private Long commentNo;
    private String commentBody;
}
