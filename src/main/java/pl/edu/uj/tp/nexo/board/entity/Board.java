package pl.edu.uj.tp.nexo.board.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.uj.tp.nexo.organization.entity.Organization;
import pl.edu.uj.tp.nexo.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "board_users",
        joinColumns = @JoinColumn(name = "board_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stage> stages;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
