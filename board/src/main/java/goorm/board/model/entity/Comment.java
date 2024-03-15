package goorm.board.model.entity;

import goorm.board.model.DeleteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comment SET DELETE_STATUS = 'DELETE' WHERE COMMENT_NO = ?")
@Where(clause = "DELETE_STATUS = 'ACTIVE'")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @Column(length = 1000)
    private String body;

    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_NO")
    private Board board;
}
