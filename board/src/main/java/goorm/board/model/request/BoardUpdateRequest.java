package goorm.board.model.request;

import lombok.Data;

@Data
public class BoardUpdateRequest {
    private Long boardNo;
    private String title;
    private String body;
}
