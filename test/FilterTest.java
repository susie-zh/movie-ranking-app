import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the Filter class.
 */
public class FilterTest {

  private Movie test1;

  @BeforeEach
  public void setUp() {
    test1 = new Movie("Test1", Genre.SCIFI, 2016, "CHINA", true);
  }

  /**
   * Tests whether the apply method works as expected - filter by genre
   */
  @Test
  public void testFilterByGenre() {
    Filter match = new Filter.Builder().withGenre(Genre.SCIFI).build();
    Filter mismatch = new Filter.Builder().withGenre(Genre.COMEDY).build();
    assertTrue(match.apply(test1));
    assertFalse(mismatch.apply(test1));
  }

  /**
   * Tests whether the apply method works as expected - filter by year
   */
  @Test
  public void testFilterByYearRange() {
    Filter filter = new Filter.Builder().withYearRange(2010, 2020).build();
    Filter outOfRange = new Filter.Builder().withYearRange(2000, 2010).build();
    assertTrue(filter.apply(test1));
    assertFalse(outOfRange.apply(test1));
  }

  /**
   * Tests whether the apply method works as expected - filter by country
   */
  @Test
  public void testFilterByCountry() {
    Filter match = new Filter.Builder().withCountry("CHINA").build();
    Filter mismatch = new Filter.Builder().withCountry("UK").build();
    assertTrue(match.apply(test1));
    assertFalse(mismatch.apply(test1));
  }

  /**
   * Tests whether the apply method works as expected - filter by category
   */
  @Test
  public void testFilterByCategory() {
    Filter good = new Filter.Builder().withGoodMovieCategory(true).build();
    Filter bad = new Filter.Builder().withGoodMovieCategory(false).build();
    assertTrue(good.apply(test1));
    assertFalse(bad.apply(test1));
  }
}