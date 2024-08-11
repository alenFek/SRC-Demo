import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MoviesService {

    @Inject
    MoviesRepository movieRepository;

    public List<Movies> listAll() {
        return movieRepository.listAll();
    }

    public Movies findById(long id) {
        return movieRepository.findById(id);
    }

    public List<Movies> searchByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Transactional
    public void addMovie(Movies movie) {
        movieRepository.getEntityManager().merge(movie);
    }

    @Transactional
    public void updateMovie(long id, Movies movie) throws Exception {
        Movies entity = movieRepository.findById(id);
        if (entity != null) {
            entity.setTitle(movie.getTitle());
            entity.setDescription(movie.getDescription());
            entity.setYear(movie.getYear());
            entity.setImages(movie.getImages());
            movieRepository.getEntityManager().merge(entity);
        }
        else{
            throw new Exception("Movie not found");
        }

    }

    @Transactional
    public void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }
}
