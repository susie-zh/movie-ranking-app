package model;

/**
 * This class represents a Filter for Movies in MovieList.
 * It filters Movies based on genre, year, country, and user's opinion of it.
 */
// DesignRule #13: one class, one responsibility.
public class Filter {

  private Genre genre;
  private Integer fromYear; // Integer instead of int for nullability
  private Integer toYear;
  private String country;
  private Boolean isGoodMovie;

  /**
   * A nested Builder class to create and configure a Filter object.
   * Internally creates an instance of Filter and allows setting its fields through its methods.
   */
  public static class Builder {

    private Filter filter;

    /**
     * Constructs a Builder
     */
    public Builder() {
      filter = new Filter();
    }

    /**
     * Sets the genre for the Filter
     *
     * @param genre the genre to be set
     * @return the Builder after setting the genre
     */
    public Builder withGenre(Genre genre) {
      filter.genre = genre;
      return this;
    }

    /**
     * Sets the year range for the Filter
     *
     * @param from the start year to be set
     * @param to   the end year to be set
     * @return the Builder after setting the year range
     */
    public Builder withYearRange(Integer from, Integer to) {
      filter.fromYear = from;
      filter.toYear = to;
      return this;
    }

    /**
     * Sets the country for the Filter
     *
     * @param country the country to be set
     * @return the Builder after setting the country
     */
    public Builder withCountry(String country) {
      filter.country = country;
      return this;
    }

    /**
     * Sets the good/bad category for the Filter
     *
     * @param isGood whether the Movie belongs to the good or bad category
     * @return the Builder after setting the good/bad category
     */
    public Builder withGoodMovieCategory(Boolean isGood) {
      filter.isGoodMovie = isGood;
      return this;
    }

    /**
     * Builds and returns the Filter
     *
     * @return the constructed Filter object
     */
    public Filter build() {
      return filter;
    }
  }


  /**
   * Applies the Filter to a Movie
   *
   * @param movie the Movie to be filtered
   * @return true if the Movie matches the Filter criteria, false otherwise
   */
  public boolean apply(Movie movie) {
    boolean result = true;

    if (genre != null) {
      result = result && movie.getGenre() == genre;
    }
    if (fromYear != null) {
      result = result && movie.getYear() >= fromYear;
    }
    if (toYear != null) {
      result = result && movie.getYear() <= toYear;
    }
    if (country != null && !country.isEmpty()) {
      result = result && movie.getCountry().equalsIgnoreCase(country);
    }
    if (isGoodMovie != null) {
      result = result && movie.isGoodMovie() == isGoodMovie;
    }

    return result;
  }
}