import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesAPI {

    @Inject
    MoviesService moviesService;

    @GET
    public Response getAllMovies() {
        List<Movies> movies = moviesService.listAll();
        return Response.ok(movies).build();
    }

    @GET
    @Path("/{id}")
    public Response getMovieById(@PathParam("id") long id) {
        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID cannot be null").build();
        }
        Movies movie = moviesService.findById(id);
        if (movie != null) {
            return Response.ok(movie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
    }

    @POST
    public Response addMovie(Movies movie) {
        if (movie == null || movie.getTitle() == null || movie.getTitle().trim().isEmpty() ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Movie data cannot be null and title needs to be set").build();
        }

        try {
            moviesService.addMovie(movie);
            return Response.status(Response.Status.CREATED).entity(movie).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding movie: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") long id, Movies movie) {
        if (movie == null || movie.getTitle() == null || movie.getTitle().trim().isEmpty() ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Movie data cannot be null and title needs to be set").build();
        }
        try {
            moviesService.updateMovie(id, movie);
            return Response.ok(movie).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating movie: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") long id) {
        try {
            moviesService.deleteMovie(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting movie: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{page}/{size}")
    public Response getAllMoviesOnPage(@PathParam("page") long page, @PathParam("size") long size) {
        try {
            List<Movies> movies = moviesService.listAll().stream()
                    .skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList());
            return Response.ok(movies).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving movies: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/{title}")
    public Response searchMoviesByTitle(@PathParam("title") String title) {
        if (title == null || title.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Title lookup cannot be empty").build();
        }
        List<Movies> movies = moviesService.searchByTitle(title);
        return Response.ok(movies).build();
    }

}
