package goorm.board.model.request;

import lombok.Data;

@Data
public class BoardPostRequest {
    private String title;
    private String body;
}
