import controller.FilterController;
import model.Filter;
import model.MovieList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the FilterController class.
 */
public class FilterControllerTest {

  private FilterController filterController;
  private Filter filter;

  @BeforeEach
  public void setUp() {
    filterController = new FilterController(new MovieList());
    filter = new Filter.Builder().withCountry("USA").build();
  }

  /**
   * Tests whether the getCurrentFilter and applyFilter methods work as expected
   */
  @Test
  public void testApplyFilter() {
    assertNull(filterController.getCurrentFilter());
    filterController.applyFilter(filter);
    assertEquals(filter, filterController.getCurrentFilter());
  }

  /**
   * Tests whether the resetFilter method works as expected
   */
  @Test
  public void testResetFilter() {
    filterController.applyFilter(filter);
    filterController.resetFilter();
    assertNull(filterController.getCurrentFilter());
  }
}