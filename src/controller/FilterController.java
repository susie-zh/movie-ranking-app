package controller;

import model.Filter;
import model.MovieList;

/**
 * This class represents a FilterController that filters Movies in MovieList.
 */
public class FilterController {

  private MovieList movieList;
  private Filter currentFilter;

  /**
   * Constructs a FilterController object
   *
   * @param movieList the MovieList object to be managed
   */
  public FilterController(MovieList movieList) {
    this.movieList = movieList;
  }

  /**
   * Gets the current Filter
   *
   * @return the Filter being applied
   */
  public Filter getCurrentFilter() {
    return currentFilter;
  }

  /**
   * Applies a Filter to MovieList
   *
   * @param filter the Filter to be applied
   */
  public void applyFilter(Filter filter) {
    this.currentFilter = filter;
  }

  /**
   * Resets the current Filter
   */
  public void resetFilter() {
    this.currentFilter = null;
  }
}