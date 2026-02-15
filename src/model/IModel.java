package model;

import java.util.List;

/**
 * This Interface represents the Model in the MVC pattern.
 * It defines all methods that the Model should implement.
 */
public interface IModel {

  /**
   * Adds a Movie to the MovieList at its designated position determined by the ranker
   *
   * @param movie the Movie to be added
   */
  void addMovie(Movie movie);

  /**
   * Adds a Movie at a specific position in MovieList
   *
   * @param movie    the Movie to be added
   * @param position the position to insert Movie at
   */
  void addMovieAtPosition(Movie movie, int position);

  /**
   * Gets the list of Movies
   *
   * @return the list of Movies
   */
  List<Movie> getMovies();

  /**
   * Filters the MovieList
   *
   * @param filter the Filter object to be applied
   * @return the MovieList after filtering
   */
  List<Movie> filterMovies(Filter filter);

  /**
   * Gets the MovieComparator used for sorting MovieList
   *
   * @return the MovieComparator
   */
  MovieComparator getComparator();
}