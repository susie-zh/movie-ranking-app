import model.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the BinarySearchRanker class.
 */
public class BinarySearchRankerTest {

  private BinarySearchRanker ranker;
  private MovieComparator comparator;
  private List<Movie> movieList;

  @BeforeEach
  public void setUp() {
    comparator = new MovieComparator();
    ranker = new BinarySearchRanker(comparator);
    movieList = new ArrayList<>();
  }

  /**
   * Tests whether the findPosition method works as expected
   */
  @Test
  public void testFindPositionSameCategory() {
    Movie good1 = new Movie("Good1", Genre.ROMANCE, 2010, "USA", true);
    Movie good2 = new Movie("Good2", Genre.ACTION, 2011, "USA", true);
    good1.setScore(9.0);
    good2.setScore(8.0);
    movieList.add(good1);
    movieList.add(good2);

    // add a new Movie of same category
    Movie goodTest = new Movie("Good Test", Genre.ANIMATION, 2012, "JAPAN", true);
    goodTest.setScore(8.5);
    movieList.add(goodTest);
    int posGood = ranker.findPosition(movieList, goodTest);
    assertEquals(1, posGood);

    // add a new Movie of different category
    Movie badTest = new Movie("Bad Test", Genre.DOCUMENTARY, 2015, "UK", false);
    movieList.add(badTest);
    int posBad = ranker.findPosition(movieList, badTest);
    assertEquals(3, posBad);
  }
}