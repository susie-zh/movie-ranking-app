package view;

import controller.MovieController;
import model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represents the panel for displaying the MovieList.
 */
public class MovieListPanel extends JPanel {

  private MovieController controller;
  private JList<Movie> movieList;
  private DefaultListModel<Movie> listModel;

  /**
   * Constructs the MovieListPanel with the specified MovieController
   *
   * @param controller the MovieController to use
   */
  public MovieListPanel(MovieController controller) {
    this.controller = controller;

    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder("Ranked Movies"));

    listModel = new DefaultListModel<>();
    movieList = new JList<>(listModel);
    movieList.setCellRenderer(new MovieCellRenderer());
    JScrollPane scrollPane = new JScrollPane(movieList);
    add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * Updates the movie list by calling the MovieController to add all Movies
   */
  public void updateMovieList() {
    listModel.clear();  // clear the list first
    List<Movie> movies = controller.getMovies();
    for (Movie movie : movies) {
      listModel.addElement(movie);
    }
  }

  /**
   * Custom cell renderer for the JList to display Movie details
   */
  private class MovieCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
          cellHasFocus);
      if (value instanceof Movie) {
        Movie movie = (Movie) value;
        // set the text to display Movie details
        label.setText(String.format("%s (%d)  |  %s  |  %s  -  %.1f", movie.getTitle(),
            movie.getYear(), movie.getCountry(), movie.getGenre(), movie.getScore()));

        // set different background colors for good/bad Movies
        if (!isSelected) {
          if (movie.isGoodMovie()) {
            label.setBackground(new Color(230, 255, 230)); // light green
          } else {
            label.setBackground(new Color(255, 230, 230)); // light red
          }
        }

        // set font size
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 16));
      }
      return label;
    }
  }
}