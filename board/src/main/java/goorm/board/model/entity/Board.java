package goorm.board.model.entity;

import goorm.board.model.DeleteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE board SET DELETE_STATUS = 'DELETE' WHERE BOARD_NO = ?")
@Where(clause = "DELETE_STATUS = 'ACTIVE'")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;

    private String title;

    @Column(length = 1000)
    private String body;

    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Where(clause = "DELETE_STATUS = 'ACTIVE'")
    private List<Comment> comments = new ArrayList<>();

    public Board addComment(String commentBody) {
        Comment comment = new Comment();
        comment.setBody(commentBody);
        comment.setBoard(this);
        comment.setDeleteStatus(DeleteStatus.ACTIVE);

        this.getComments().add(comment);
        return this;
    }
}
