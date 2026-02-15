package model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * This class modifies the default Comparator to allow for user-defined comparisons.
 */
public class MovieComparator implements Comparator<Movie> {

  // Use HashMap to store user comparison results
  private Map<String, Integer> comparisonResults = new HashMap<>();

  /**
   * Generates a unique key for a pair of Movies
   *
   * @param m1 first Movie compared
   * @param m2 second Movie compared
   * @return a unique string key for the comparison
   */
  private String getComparisonKey(Movie m1, Movie m2) {
    // use titles to create a unique identifier for the comparison
    if (m1.getTitle().compareTo(m2.getTitle()) < 0) {
      return m1.getTitle() + "|" + m2.getTitle();
    } else {
      return m2.getTitle() + "|" + m1.getTitle();
    }
  }

  /**
   * Compares two Movie objects based on user-defined criteria
   *
   * @param m1 the first object to be compared
   * @param m2 the second object to be compared
   * @return the comparison result
   */
  @Override
  public int compare(Movie m1, Movie m2) {
    // first check if these Movies have been directly compared
    // if so, return the stored result
    int directComparison = getComparisonResult(m1, m2);
    if (directComparison != 0) {
      return directComparison;
    }

    // if Movies are in different categories (good/bad)
    // good Movie always rank higher
    if (m1.isGoodMovie() != m2.isGoodMovie()) {
      return m1.isGoodMovie() ? -1 : 1;
    }

    // if no direct comparison exists, fall back to score comparison
    return Double.compare(m2.getScore(), m1.getScore());
  }

  /**
   * Adds a comparison result between two Movies
   *
   * @param m1     first Movie to compare
   * @param m2     second Movie to compare
   * @param result 1 if m1 is better, -1 if m2 is better
   */
  public void addComparisonResult(Movie m1, Movie m2, int result) {
    String key = getComparisonKey(m1, m2);
    comparisonResults.put(key, result);
  }

  /**
   * Gets the result of a previous comparison between two Movies
   *
   * @param m1 first Movie compared
   * @param m2 second Movie compared
   * @return 1 if m1 is better, -1 if m2 is better, 0 if no comparison exists
   */
  private int getComparisonResult(Movie m1, Movie m2) {
    String key = getComparisonKey(m1, m2);
    return comparisonResults.getOrDefault(key, 0);
  }

  /**
   * Clears all stored comparison results
   */
  public void clearComparisonResults() {
    comparisonResults.clear();
  }
}