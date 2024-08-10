import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MoviesRepository implements PanacheRepository<Movies> {

    public List<Movies> findByTitle(String title) {
        return find("title LIKE ?1", "%" + title + "%").list();
    }
}