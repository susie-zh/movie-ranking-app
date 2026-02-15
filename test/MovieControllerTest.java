import controller.MovieController;
import model.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the MovieController class.
 */
public class MovieControllerTest {

  private MovieController movieController;
  private Movie good1;
  private Movie bad1;

  @BeforeEach
  public void setUp() {
    movieController = new MovieController();
    good1 = new Movie("Good 1", Genre.DRAMA, 2014, "USA", true);
    bad1 = new Movie("Bad 1", Genre.DOCUMENTARY, 2003, "UK", false);
  }

  /**
   * Tests whether the addMovie (from user input) method works as expected
   */
  @Test
  public void testAddMovieFromUser() {
    movieController.addMovie("Test 1", Genre.SCIFI, 2024, "USA", true);
    List<Movie> movies = movieController.getMovies();
    assertEquals(1, movies.size());
    assertEquals("Test 1", movies.get(0).getTitle());
  }

  /**
   * Tests whether the addMovie (to MovieList) method works as expected
   */
  @Test
  public void testAddMovieToList() {
    movieController.addMovie(good1);
    List<Movie> movies = movieController.getMovies();
    assertEquals(1, movies.size());
    assertEquals(good1.getTitle(), movies.get(0).getTitle());
  }

  /**
   * Tests whether the addMovieAtPosition method works as expected
   */
  @Test
  public void testAddMovieAtPosition() {
    movieController.addMovie(good1);
    movieController.addMovieAtPosition(bad1, 0);
    List<Movie> movies = movieController.getMovies();
    assertEquals(bad1, movies.get(0));
    assertEquals(good1, movies.get(1));
  }

  /**
   * Tests whether the getMovies method works as expected
   */
  @Test
  public void testGetMovies() {
    movieController.addMovie(good1);
    movieController.addMovie(bad1);
    Filter goodOnly = new Filter.Builder().withGoodMovieCategory(true).build();
    movieController.getFilterController().applyFilter(goodOnly);
    List<Movie> filtered = movieController.getMovies();

    assertEquals(1, filtered.size());
    assertEquals("Good 1", filtered.get(0).getTitle());
  }

  /**
   * Tests whether the updateView method works as expected
   */
  @Test
  public void testUpdateView() {
    movieController.addMovie(good1);
    movieController.addMovie(bad1);
    movieController.updateView();
    // Should not throw any exception even if no view is set
    assertDoesNotThrow(() -> movieController.updateView());
    // Assuming the view is updated correctly, we can check if the movies are in the list
    List<Movie> movies = movieController.getMovies();
    assertTrue(movies.contains(good1));
    assertTrue(movies.contains(bad1));
  }
}