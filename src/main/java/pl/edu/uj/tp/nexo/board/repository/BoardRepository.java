package pl.edu.uj.tp.nexo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.tp.nexo.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
