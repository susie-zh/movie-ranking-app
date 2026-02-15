import controller.MovieController;
import model.Genre;
import model.Movie;
import view.MovieListPanel;

import javax.swing.*;
import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the MovieListPanel class.
 */
public class MovieListPanelTest {

  private MovieListPanel movieListPanel;
  private MovieController movieController;
  private JFrame frame;

  @BeforeEach
  public void setUp() {
    movieController = new MovieController();
    movieListPanel = new MovieListPanel(movieController);
    frame = new JFrame();
    frame.setLayout(new BorderLayout());
    frame.add(movieListPanel);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Tests whether all components are initialized and added to the panel
   */
  @Test
  public void testPanelInitializesCorrectly() {
    assertNotNull(movieListPanel);
    Component[] components = movieListPanel.getComponents();
    assertTrue(components.length > 0);
  }

  /**
   * Tests whether the updateMovieList method works as expected
   */
  @Test
  public void testUpdateMovieListDisplaysCorrectly() {
    Movie movie1 = new Movie("Test 1", Genre.SCIFI, 2009, "USA", true);
    Movie movie2 = new Movie("Test 2", Genre.MUSICAL, 2019, "UK", false);
    movieController.addMovie(movie1);
    movieController.addMovie(movie2);
    movieListPanel.updateMovieList();

    JList<?> jList = findMovieJList(movieListPanel);
    assertEquals(2, jList.getModel().getSize());
    assertEquals(movie1, jList.getModel().getElementAt(0));
    assertEquals(movie2, jList.getModel().getElementAt(1));
  }


  // utility method to find a component by its type and name
  private JList<?> findMovieJList(Container container) {
    for (Component c : container.getComponents()) {
      if (c instanceof JList<?>) {
        return (JList<?>) c;
      }
      if (c instanceof Container child) {
        JList<?> result = findMovieJList(child);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }
}