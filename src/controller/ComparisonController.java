package controller;

import javax.swing.*;
import java.util.List;

import model.Movie;
import model.MovieComparator;
import model.MovieList;
import view.ComparisonDialog;

/**
 * This class represents a ComparisonController that manages comparing Movies.
 */
public class ComparisonController {

  private MovieList movieList;
  private MovieComparator comparator;
  private MovieController movieController;

  // Variables to track binary search state
  private Movie newMovie;
  private List<Movie> sameCategoryMovies;
  private int left, right, mid;
  private JFrame parentFrame;

  /**
   * Constructs a ComparisonController
   *
   * @param movieList       the MovieList to be managed
   * @param movieController the MovieController that it works with
   */
  public ComparisonController(MovieList movieList, MovieController movieController) {
    this.movieList = movieList;
    this.movieController = movieController;
    this.comparator = movieList.getComparator();
  }


  /**
   * Starts the comparison process for a new Movie
   *
   * @param newMovie    the Movie to rank
   * @param parentFrame the parent frame for dialog display
   * @return true if the comparison process was started successfully, false otherwise
   */
  public boolean startComparisonProcess(Movie newMovie, JFrame parentFrame) {
    // clear past comparison results for a new session
    comparator.clearComparisonResults();

    // initialize binary search state
    this.newMovie = newMovie;
    this.parentFrame = parentFrame;

    // get Movies from the same category (good/bad)
    List<Movie> allMovies = movieList.getMovies();
    sameCategoryMovies = allMovies.stream()
        .filter(m -> m.isGoodMovie() == newMovie.isGoodMovie())
        .toList();
    if (sameCategoryMovies.isEmpty()) {
      // if no Movie in the same category, simply add
      movieController.addMovie(newMovie);
      return true;
    }

    // initialize binary search bounds
    left = 0;
    right = sameCategoryMovies.size() - 1;
    // begin comparison flow
    return continueBinarySearch();
  }

  /**
   * Recursively continues the binary search process by showing the next Movie for comparison
   *
   * @return true if the process was completed, false if cancelled
   */
  public boolean continueBinarySearch() {
    if (left > right) {
      // binary search is complete, insert movie at final position
      int position = getFinalPosition();
      movieController.addMovieAtPosition(newMovie, position);
      return true;
    }

    // calculate the middle index for comparison
    mid = left + (right - left) / 2;
    Movie comparisonMovie = sameCategoryMovies.get(mid);

    // create and show the comparison dialog
    ComparisonDialog dialog = new ComparisonDialog(
        parentFrame, newMovie, comparisonMovie, this, movieController);
    dialog.setVisible(true);

    // check if the comparison was completed or cancelled
    if (!dialog.isComparisonCompleted()) {
      // user cancelled the process
      return false;
    }

    // continue with the next comparison with recursion
    return continueBinarySearch();
  }

  /**
   * Records the result of a comparison
   *
   * @param newMovie        the new Movie being ranked
   * @param comparisonMovie the Movie it was compared with
   * @param result          1 if new Movie is better, -1 if worse
   */
  public void recordComparison(Movie newMovie, Movie comparisonMovie, int result) {
    // update the MovieComparator with this comparison result
    comparator.addComparisonResult(newMovie, comparisonMovie, result);

    // update binary search bounds
    if (result < 0) {
      // new Movie is better - should be placed before the comparison Movie
      right = mid - 1;
    } else {
      // new Movie is worse - should be placed after the comparison Movie
      left = mid + 1;
    }
  }

  /**
   * Determines the final position for the new Movie in the full MovieList
   * after completing binary search within a subset of Movies of the same category
   *
   * @return the index where the new Movie should be inserted
   */
  public int getFinalPosition() {
    if (sameCategoryMovies.isEmpty()) {
      // first Movie in its category, simply add it
      List<Movie> allMovies = movieList.getMovies();
      return newMovie.isGoodMovie() ? 0 : allMovies.size();
    }

    // the position in the full list depends on where other Movies of the same category are
    List<Movie> allMovies = movieList.getMovies();  // retrieve full list
    if (left > right) { // binary search completed
      if (left >= sameCategoryMovies.size()) {
        // the Movie should be placed after all same-category Movies
        int lastRelevantIndex = allMovies.indexOf(
            sameCategoryMovies.get(sameCategoryMovies.size() - 1));
        return lastRelevantIndex + 1;
      } else {
        // the Movie should be placed at the 'left' position among same-category Movies
        int insertIndex = allMovies.indexOf(sameCategoryMovies.get(left));
        return insertIndex;
      }
    }
    // this should not happen, but added for safety
    throw new IllegalStateException("Failed to determine final position for movie: "
        + newMovie.getTitle());
  }
}