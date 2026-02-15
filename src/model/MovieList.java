package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a list of Movies. It implements IModel interface.
 */
public class MovieList implements IModel {

  private List<Movie> movies;
  private BinarySearchRanker ranker;
  private MovieComparator comparator;

  /**
   * Constructs a MovieList object
   */
  public MovieList() {
    this.movies = new ArrayList<>();
    this.comparator = new MovieComparator();
    this.ranker = new BinarySearchRanker(comparator);
  }

  /**
   * Gets the MovieComparator used for sorting MovieList
   *
   * @return the MovieComparator
   */
  public MovieComparator getComparator() {
    return this.comparator;
  }

  /**
   * Gets the list of Movies
   *
   * @return the list of Movies
   */
  public List<Movie> getMovies() {
    return new ArrayList<>(movies);
  }

  /**
   * Adds a Movie to the MovieList at its designated position determined by the ranker
   *
   * @param movie the Movie to be added
   */
  public void addMovie(Movie movie) {
    int position = ranker.findPosition(movies, movie);
    movies.add(position, movie);
    updateScores(); // update scores of all Movies in MovieList
  }

  /**
   * Adds a Movie at a specific position in MovieList
   *
   * @param movie the Movie to be added
   * @param position the position to insert Movie at
   */
  public void addMovieAtPosition(Movie movie, int position) {
    // handle out of bounds
    if (position < 0) {
      position = 0;
    } else if (position > movies.size()) {
      position = movies.size();
    }
    movies.add(position, movie);
    updateScores(); // update scores of all Movies in MovieList
  }

  /**
   * Filters the MovieList
   *
   * @param filter the Filter object to be applied
   * @return the MovieList after filtering
   */
  public List<Movie> filterMovies(Filter filter) {
    return movies.stream()
        .filter(filter::apply)
        .collect(Collectors.toList());
  }

  /**
   * Updates the scores of Movies in MovieList based on their positions
   */
  private void updateScores() {
    int size = movies.size();
    // if there's only 1 Movie, check its category
    if (size == 1) {
      Movie movie = movies.get(0);
      movie.setScore(movie.isGoodMovie() ? 10.0 : 5.9);
      return;
    }

    for (int i = 0; i < size; i++) {
      Movie movie = movies.get(i);
      if (movie.isGoodMovie()) {
        // good Movies score from 10.0 down to 6.0
        // normalize the index to [0,1] and then scale it to [6.0, 10.0]
        movie.setScore(10.0 - ((double) i / (size - 1)) * 4.0);
      } else {
        // bad Movies score from 6.0 down to 2.0
        // not 0.0 because it doesn't need to be so harsh
        movie.setScore(6.0 - ((double) i / (size - 1)) * 4.0);
      }
    }
  }
}