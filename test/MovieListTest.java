import model.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the MovieList class.
 */
public class MovieListTest {

  private MovieList movieList;
  private Movie good1, good2, bad1, bad2;

  @BeforeEach
  public void setUp() {
    movieList = new MovieList();
    good1 = new Movie("Good 1", Genre.DRAMA, 2010, "USA", true);
    good2 = new Movie("Good 2", Genre.COMEDY, 2012, "UK", true);
    bad1 = new Movie("Bad 1", Genre.HORROR, 2015, "JAPAN", false);
    bad2 = new Movie("Bad 2", Genre.ACTION, 2004, "USA", false);
  }

  /**
   * Tests whether the addMovie and addMovieAtPosition methods work as expected
   */
  @Test
  public void testAddMovieAtPosition() {
    // addMovie
    movieList.addMovie(good1);
    List<Movie> result0 = movieList.getMovies();
    assertEquals(1, result0.size());
    assertTrue(result0.contains(good1));

    // addMovieAtPosition
    movieList.addMovieAtPosition(bad1, 1);
    List<Movie> result1 = movieList.getMovies();
    assertEquals(2, result1.size());
    assertEquals(bad1, result1.get(1));

    // addMovieAtPosition with a negative position
    movieList.addMovieAtPosition(good2, -5);
    List<Movie> result2 = movieList.getMovies();
    assertEquals(good2, result2.get(0));

    // addMovieAtPosition with an out-of-range position
    movieList.addMovieAtPosition(bad2, 10);
    List<Movie> result3 = movieList.getMovies();
    assertEquals(bad2, result3.get(result3.size() - 1));
  }

  /**
   * Tests whether the filterMovies method works as expected
   */
  @Test
  public void testFilterMovies() {
    movieList.addMovie(good1);
    movieList.addMovie(good2);
    movieList.addMovie(bad1);
    Filter filter = new Filter.Builder().withGoodMovieCategory(true).build();
    List<Movie> filtered = movieList.filterMovies(filter);

    assertEquals(2, filtered.size());
    assertTrue(filtered.contains(good1));
    assertFalse(filtered.contains(bad1));
  }

  /**
   * Test whether the updateScores method works as expected
   */
  @Test
  public void testUpdateScores() {
    movieList.addMovie(good1);
    assertEquals(10.0, good1.getScore());
    movieList.addMovie(good2);
    assertTrue(good1.getScore() > good2.getScore());
  }
}