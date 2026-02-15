package model;

import java.util.Objects;

/**
 * This class represents a Movie with its title, genre, year, country, and category.
 */
public class Movie {

  private String title;
  private Genre genre;
  private int year;
  private String country;
  private boolean isGoodMovie; // Flag for initial categorization
  private double score;

  /**
   * Constructs a Movie object with the specified title, genre, year, country, and category
   *
   * @param title       the title of the Movie
   * @param genre       the genre of the Movie
   * @param year        the year the Movie is made
   * @param country     the country the Movie is from
   * @param isGoodMovie true if the Movie is considered good, false otherwise
   */
  public Movie(String title, Genre genre, int year, String country, boolean isGoodMovie) {
    this.title = title;
    this.genre = genre;
    this.year = year;
    this.country = country;
    this.isGoodMovie = isGoodMovie;
    this.score = isGoodMovie ? 8.0 : 4.5; // Default initial scores
  }

  /**
   * Gets the title of the Movie
   *
   * @return the title of the Movie
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Sets the title of the Movie
   *
   * @param title the title of the Movie
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the genre of the Movie
   *
   * @return the genre of the Movie
   */
  public Genre getGenre() {
    return this.genre;
  }

  /**
   * Sets the genre of the Movie
   *
   * @param genre the genre of the Movie
   */
  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  /**
   * Gets the year of the Movie
   *
   * @return the year of the Movie
   */
  public int getYear() {
    return this.year;
  }

  /**
   * Sets the year of the Movie
   *
   * @param year the year of the Movie
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Gets the country of the Movie
   *
   * @return the country of the Movie
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * Sets the country of the Movie
   *
   * @param country the country of the Movie
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Gets the score of the Movie
   *
   * @return the score of the Movie
   */
  public double getScore() {
    return this.score;
  }

  /**
   * Sets the score of the Movie
   *
   * @param score the score of the Movie
   */
  public void setScore(double score) {
    this.score = score;
  }

  /**
   * Gets user's opinion on this Movie
   *
   * @return true if the Movie is considered good, false otherwise
   */
  public boolean isGoodMovie() {
    return this.isGoodMovie;
  }

  /**
   * Sets user's opinion on the Movie
   *
   * @param goodMovie true if the Movie is considered a good movie, false otherwise
   */
  public void setGoodMovie(boolean goodMovie) {
    this.isGoodMovie = goodMovie;
  }

  /**
   * String representation of this Movie
   *
   * @return a formatted string containing title, year, and score of the Movie
   */
  @Override
  public String toString() {
    return this.title + " (" + this.year + ") - " + this.score;
  }

  /**
   * Checks if this Movie is equal to another object
   * Two Movies are the same if they have the same title, year, and country
   *
   * @param other the object to compare with
   * @return true if the Movies are equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;

    Movie o = (Movie) other;
    return this.year == o.year &&
        this.title.equalsIgnoreCase(o.title) &&
        this.country.equalsIgnoreCase(o.country);
  }

  /**
   * Returns a hash code value for this Movie
   *
   * @return the hash code value for this Movie
   */
  @Override
  public int hashCode() {
    return Objects.hash(year, title, country);
  }
  // Design Rule #21: if you override equals(), override hashCode(), and vice-versa.
}