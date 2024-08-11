import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MoviesUnitTest {

    @Inject
    MoviesService moviesService;

    @BeforeEach
    @Transactional
    public void setup() {

        moviesService.deleteAllMovies();

        Movies movie1 = new Movies();
        movie1.setImdbID(1);
        movie1.setTitle("test1");
        movie1.setYear(2001);
        movie1.setDescription("test1 desc");
        moviesService.addMovie(movie1);

        Movies movie2 = new Movies();
        movie2.setImdbID(2);
        movie2.setTitle("test2");
        movie2.setYear(2002);
        movie2.setDescription("test2 desc");
        moviesService.addMovie(movie2);
    }


    @Test
    public void testGetAllMoviesEndpoint() {

        var response = given()
                .when().get("/movies")
                .then();

        System.out.println("Response Status Code: " + response.extract().statusCode());
        System.out.println("Response Body: " + response.extract().body().asString());
    }

    @Test
    public void testGetMovieByIdEndpoint() {

        List<Movies> moviesList = moviesService.listAll();
        if (moviesList.size() > 0) {
            var response = given()
                    .when().get("/movies/" + moviesList.get(0).getImdbID())
                    .then();

            System.out.println("Response Status Code: " + response.extract().statusCode());
            System.out.println("Response Body: " + response.extract().body().asString());
        } else {
            System.out.println("Response: no movies in db");
        }

    }

    @Test
    @Transactional
    public void testAddMovieEndpoint() {
        String movieJson = "{ \"title\": \"New Movie\", \"year\": 2023, \"description\": \"A great movie\" }";

        var response = given()
                .header("Content-Type", "application/json")
                .body(movieJson)
                .when().post("/movies")
                .then();

        System.out.println("Response Status Code: " + response.extract().statusCode());
        System.out.println("Response Body: " + response.extract().body().asString());
    }

    @Test
    @Transactional
    public void testUpdateMovieEndpoint() {
        String movieJson = "{ \"title\": \"Updated Movie\", \"year\": 2023, \"description\": \"An updated description\" }";
        List<Movies> moviesList = moviesService.listAll();
        if (moviesList.size() > 0) {
            var response = given()
                    .header("Content-Type", "application/json")
                    .body(movieJson)
                    .when().put("/movies/" + moviesList.get(0).getImdbID())
                    .then();

            System.out.println("Response Status Code: " + response.extract().statusCode());
            System.out.println("Response Body: " + response.extract().body().asString());
        } else {
            System.out.println("Response: no movies in db");
        }
    }

    @Test
    @Transactional
    public void testDeleteMovieEndpoint() {
        List<Movies> moviesList = moviesService.listAll();
        System.out.println("number of movies before: " + moviesService.listAll().size());
        if (moviesList.size() > 0) {

            var response = given()
                    .when().delete("/movies/" + moviesList.get(0).getImdbID())
                    .then();

            System.out.println("Response Status Code: " + response.extract().statusCode());
            System.out.println("Response Body: " + response.extract().body().asString());
            System.out.println("number of movies after: " + moviesService.listAll().size());
        } else {
            System.out.println("Response: no movies in db");
        }
    }

    @Test
    public void testPaginationMovieEndpoint() {

        var response = given()
                .when().get("/movies/1/1")
                .then();

        System.out.println("Response Status Code: " + response.extract().statusCode());
        System.out.println("Response Body: " + response.extract().body().asString());
    }

    @Test
    public void testSearchTitleMovieEndpoint() {

        var response = given()
                .when().get("/movies/search/test1")
                .then();

        System.out.println("Response Status Code: " + response.extract().statusCode());
        System.out.println("Response Body: " + response.extract().body().asString());

    }

}