package controller;

import model.Genre;
import view.MainFrame;

/**
 * This Interface represents the Controller in the MVC pattern.
 * It defines all methods that the MovieController should implement.
 */
// Design Rule #19: loose coupling over tight coupling.
public interface IController {

  /**
   * Sets the View for the MovieController
   *
   * @param view the MainFrame object to be set as the View
   */
  void setView(MainFrame view);

  /**
   * Updates the View when necessary
   */
  void updateView();

  /**
   * Adds a Movie from user input
   *
   * @param title   the title of the Movie
   * @param genre   the genre of the Movie
   * @param year    the year of the Movie
   * @param country the country of the Movie
   * @param isGood  the user's opinion of the Movie
   */
  void addMovie(String title, Genre genre, int year, String country, boolean isGood);

  /**
   * Gets the FilterController used to filter Movies in MovieList
   *
   * @return the Filter Controller
   */
  FilterController getFilterController();

  /**
   * Gets the ComparisonController used to compare Movies
   *
   * @return the ComparisonController
   */
  ComparisonController getComparisonController();
}