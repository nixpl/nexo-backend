package pl.edu.uj.tp.nexo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.uj.tp.nexo.board.entity.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
}
