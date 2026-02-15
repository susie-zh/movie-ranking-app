package controller;

import model.Filter;
import model.Movie;
import model.MovieList;
import model.Genre;
import view.MainFrame;

import java.util.List;

/**
 * This class represents a MovieController that manages the Model and interacts with the View.
 * It implements the IController interface; owns FilterController and ComparisonController.
 */
public class MovieController implements IController {

  private MovieList movieList;
  private MainFrame view;
  private FilterController filterController;
  private ComparisonController comparisonController;

  /**
   * Constructs a MovieController object
   */
  public MovieController() {
    this.movieList = new MovieList();
    this.filterController = new FilterController(movieList);
    this.comparisonController = new ComparisonController(movieList, this);
  }

  /**
   * Sets the View for the MovieController
   *
   * @param view the MainFrame object to be set as the View
   */
  public void setView(MainFrame view) {
    this.view = view;
  }

  /**
   * Gets the MovieList from the Model
   *
   * @return the MovieList object
   */
  public MovieList getMovieList() {
    return this.movieList;
  }

  /**
   * Gets the ComparisonController used to compare Movies
   *
   * @return the ComparisonController
   */
  public ComparisonController getComparisonController() {
    return this.comparisonController;
  }

  /**
   * Gets the FilterController used to filter Movies in MovieList
   *
   * @return the Filter Controller
   */
  public FilterController getFilterController() {
    return this.filterController;
  }

  /**
   * Creates a Movie object from user input
   *
   * @param title   the title of the Movie
   * @param genre   the genre of the Movie
   * @param year    the year of the Movie
   * @param country the country of the Movie
   * @param isGood  the user's opinion of the Movie
   */
  public void addMovie(String title, Genre genre, int year, String country, boolean isGood) {
    Movie m = new Movie(title, genre, year, country, isGood);
    addMovie(m);  // adds the Movie to MovieList
  }

  /**
   * Adds a Movie to the MovieList; updates the View after adding
   *
   * @param movie the Movie to add
   */
  public void addMovie(Movie movie) {
    movieList.addMovie(movie);
    updateView();
  }

  /**
   * Adds a Movie at a specific position in the MovieList; updates the View after adding
   *
   * @param movie    The Movie to add
   * @param position The position to insert the Movie
   */
  public void addMovieAtPosition(Movie movie, int position) {
    movieList.addMovieAtPosition(movie, position);
    updateView();
  }

  /**
   * Gets the list of Movies from MovieList once Filter is applied
   *
   * @return the filtered list of Movies
   */
  public List<Movie> getMovies() {
    Filter currentFilter = filterController.getCurrentFilter();
    return currentFilter == null ? movieList.getMovies() : movieList.filterMovies(currentFilter);
  }

  /**
   * Updates the View when necessary
   */
  public void updateView() {
    if (view != null) {
      view.refresh();
    }
  }
}