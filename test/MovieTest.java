import model.Movie;
import model.Genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the Movie class.
 */
public class MovieTest {

  private Movie goodMovie;
  private Movie badMovie;

  @BeforeEach
  public void setUp() {
    goodMovie = new Movie("Good1", Genre.SCIFI, 2001, "USA", true);
    badMovie = new Movie("Bad2", Genre.DRAMA, 2003, "USA", false);
  }

  /**
   * Tests whether the constructor and getters work as expected
   */
  @Test
  public void testConstructorAndGetters() {
    assertEquals("Good1", goodMovie.getTitle());
    assertEquals(Genre.SCIFI, goodMovie.getGenre());
    assertEquals(2001, goodMovie.getYear());
    assertEquals("USA", goodMovie.getCountry());
    assertTrue(goodMovie.isGoodMovie());
    assertEquals(8.0, goodMovie.getScore());
  }

  /**
   * Tests whether the setters work as expected
   */
  @Test
  public void testSetters() {
    badMovie.setTitle("SetterTest");
    badMovie.setGenre(Genre.HORROR);
    badMovie.setYear(2010);
    badMovie.setCountry("UK");
    badMovie.setGoodMovie(true);
    badMovie.setScore(9.0);

    assertEquals("SetterTest", badMovie.getTitle());
    assertEquals(Genre.HORROR, badMovie.getGenre());
    assertEquals(2010, badMovie.getYear());
    assertEquals("UK", badMovie.getCountry());
    assertTrue(badMovie.isGoodMovie());
    assertEquals(9.0, badMovie.getScore());
  }

  /**
   * Tests whether the toString method works as expected
   */
  @Test
  public void testToString() {
    String expected1 = "Good1 (2001) - 8.0";
    String expected2 = "Bad2 (2003) - 4.5";
    assertEquals(expected1, goodMovie.toString());
    assertEquals(expected2, badMovie.toString());
  }

  /**
   * Tests whether the equals and hashCode methods work as expected
   */
  @Test
  public void testEquals() {
    Movie sameMovie = new Movie("Good1", Genre.SCIFI, 2001, "USA", true);
    Movie differentMovie = new Movie("Different", Genre.ACTION, 2010, "CHINA", true);

    assertTrue(goodMovie.equals(sameMovie));
    assertFalse(goodMovie.equals(differentMovie));
    assertFalse(goodMovie.equals(null));
    assertEquals(goodMovie.hashCode(), sameMovie.hashCode());
    assertNotEquals(goodMovie.hashCode(), differentMovie.hashCode());
  }
}