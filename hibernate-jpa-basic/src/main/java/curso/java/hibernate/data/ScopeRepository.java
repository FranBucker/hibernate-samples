package curso.java.hibernate.data;

import curso.java.hibernate.data.entity.Scope;
import curso.java.hibernate.data.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long> {
}