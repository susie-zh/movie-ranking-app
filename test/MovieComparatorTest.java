import model.Movie;
import model.Genre;
import model.MovieComparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the MovieComparator class.
 */
public class MovieComparatorTest {

  private MovieComparator comparator;
  private Movie goodMovie1;
  private Movie goodMovie2;
  private Movie badMovie1;
  private Movie badMovie2;

  @BeforeEach
  public void setUp() {
    comparator = new MovieComparator();
    goodMovie1 = new Movie("Good1", Genre.FANTASY, 2001, "USA", true);
    goodMovie2 = new Movie("Good2", Genre.SCIFI, 2002, "JAPAN", true);
    badMovie1 = new Movie("Bad1", Genre.MUSICAL, 2003, "UK", false);
    badMovie2 = new Movie("Bad2", Genre.DRAMA, 2004, "USA", false);
  }

  /**
   * Tests whether the compare method works as expected
   */
  @Test
  public void testCompare() {
    // compare Movies of same category - by score
    goodMovie1.setScore(9.0);
    goodMovie2.setScore(8.0);
    assertTrue(comparator.compare(goodMovie1, goodMovie2) < 0);
    badMovie1.setScore(4.0);
    badMovie2.setScore(5.0);
    assertTrue(comparator.compare(badMovie1, badMovie2) > 0);

    // compare Movies of different category
    assertTrue(comparator.compare(goodMovie1, badMovie1) < 0);
    assertTrue(comparator.compare(badMovie1, goodMovie1) > 0);
  }

  /**
   * Tests whether the addComparisonResult method and direct comparison overrides work as expected
   */
  @Test
  public void testDirectComparisonOverrides() {
    comparator.addComparisonResult(goodMovie1, goodMovie2, 1);
    assertEquals(1, comparator.compare(goodMovie1, goodMovie2));
    comparator.addComparisonResult(goodMovie2, goodMovie1, -1);
    assertEquals(-1, comparator.compare(goodMovie2, goodMovie1));
  }

  /**
   * Tests whether the clearComparisonResults method works as expected
   */
  @Test
  public void testClearComparisonResults() {
    comparator.addComparisonResult(goodMovie1, goodMovie2, -1);
    comparator.clearComparisonResults();
    assertNotEquals(-1, comparator.compare(goodMovie1, goodMovie2));
  }
}