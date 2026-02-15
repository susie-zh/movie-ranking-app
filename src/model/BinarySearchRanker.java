package model;

import java.util.List;

/**
 * This class represents a binary search ranker used to rank Movies.
 * It uses the MovieComparator to determine the order of Movies.
 */
public class BinarySearchRanker {

  private MovieComparator comparator;

  /**
   * Constructs a BinarySearchRanker
   *
   * @param comparator the MovieComparator used
   */
  public BinarySearchRanker(MovieComparator comparator) {
    this.comparator = comparator;
  }


  /**
   * Determines the position of a new Movie in the MovieList using binary search
   *
   * @param movies   the list of Movies
   * @param newMovie the new Movie to be added
   * @return the position index of the new Movie
   */
  public int findPosition(List<Movie> movies, Movie newMovie) {
    if (movies.isEmpty()) {
      return 0;
    }

    // Step 1: divide the list by good/bad category, narrowing the range
    List<Movie> sameCategoryMovies = movies.stream()
        .filter(m -> m.isGoodMovie() == newMovie.isGoodMovie())
        .toList();
    if (sameCategoryMovies.isEmpty()) {
      // if category empty -> first movie in its category
      return newMovie.isGoodMovie() ? 0 : movies.size();
    }

    // Step 2: perform binary search
    int left = 0;
    int right = sameCategoryMovies.size() - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      int result = comparator.compare(newMovie, sameCategoryMovies.get(mid));

      if (result == 0) {
        return movies.indexOf(sameCategoryMovies.get(mid));
      } else if (result < 0) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    // Step 3: calculate actual position of the Movie in the full list
    if (left >= sameCategoryMovies.size()) {
      // the new Movie should be placed after all Movies of the same category
      return movies.indexOf(sameCategoryMovies.get(sameCategoryMovies.size() - 1)) + 1;
    } else {
      // the Movie should be placed at the leftmost position among Movies of the same category
      return movies.indexOf(sameCategoryMovies.get(left));
    }
  }
}