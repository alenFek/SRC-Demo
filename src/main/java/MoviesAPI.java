import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesAPI {

    @GET
    public List<Movies> getAllMovies() {
        return null;
    }

    @GET
    @Path("/{id}")
    public Movies getMovieById(@PathParam("id") long id) {
        return null;
    }

    @POST
    public Response addMovie(Movies movie) {
        return null;
    }

    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") long id, Movies movie) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") long id) {
      return null;
    }

    @GET
    @Path("/{page}/{size}")
    public List<Movies> getAllMoviesOnPage(@PathParam("page") int page, @PathParam("size") int size) {
        return null;
    }

    @GET
    @Path("/search/{title}")
    public List<Movies> searchMoviesByTitle(@PathParam("title") String title) {
        return null;
    }

}
