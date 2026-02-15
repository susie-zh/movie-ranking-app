import controller.ComparisonController;
import controller.MovieController;
import model.Genre;
import model.Movie;
import model.MovieList;

import javax.swing.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the ComparisonController class.
 */
public class ComparisonControllerTest {

  private ComparisonController comparisonController;
  private MovieList movieList;
  private MovieController movieController;
  private Movie good1, good2, test1;

  @BeforeEach
  public void setUp() {
    movieController = new MovieController();
    movieList = movieController.getMovieList();
    comparisonController = new ComparisonController(movieList, movieController);

    good1 = new Movie("Good 1", Genre.SCIFI, 2010, "USA", true);
    good2 = new Movie("Good 2", Genre.ADVENTURE, 2014, "UK", true);
    test1 = new Movie("Test 1", Genre.MUSICAL, 2021, "FRANCE", true);
  }

  /**
   * Tests whether the startComparisonProcess method works as expected
   */
  @Test
  public void testStartComparisonProcessWithNoSameCategory() {
    Movie bad = new Movie("Bad 1", Genre.DRAMA, 2003, "USA", false);
    movieList.addMovie(bad);
    boolean started = comparisonController.startComparisonProcess(test1, new JFrame());
    List<Movie> result = movieList.getMovies();
    // if no Movie in the same category, simply add
    assertTrue(started);
    assertTrue(result.contains(test1));
  }

  /**
   * Tests whether the recordComparison method works as expected
   */
  @Test
  public void testRecordComparisonAdjustsBounds() {
    movieList.addMovie(good1);
    movieList.addMovie(good2);
    comparisonController.startComparisonProcess(test1, new JFrame());

    comparisonController.recordComparison(test1, good2, -1);
    comparisonController.recordComparison(test1, good1, 1);
    // if logic proceeds correctly, these bounds would push the new movie between the two
    List<Movie> allMovies = movieList.getMovies();
    int position = comparisonController.getFinalPosition();

    assertTrue(position >= 0 && position <= allMovies.size());
  }

  /**
   * Tests whether the getFinalPosition method works as expected
   */
  @Test
  public void testFinalPositionCalculationAfterBinarySearch() {
    movieList.addMovie(good1);
    movieList.addMovie(good2);
    comparisonController.startComparisonProcess(test1, new JFrame());

    // simulate full binary search result by mocking comparison steps
    comparisonController.recordComparison(test1, good2, -1);
    comparisonController.recordComparison(test1, good1, -1);

    List<Movie> all = movieList.getMovies();
    int finalPos = comparisonController.getFinalPosition();
    int good1Index = all.indexOf(good1);

    assertTrue(finalPos <= good1Index);
  }
}