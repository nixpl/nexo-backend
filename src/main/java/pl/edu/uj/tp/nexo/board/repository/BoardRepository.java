package pl.edu.uj.tp.nexo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.tp.nexo.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrganizationId(Long organizationId);
}
