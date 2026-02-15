import controller.MovieController;
import controller.ComparisonController;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the MainFrame class.
 */
public class MainFrameTest {

  private MainFrame mainFrame;
  private MovieController movieController;
  private ComparisonController comparisonController;

  @BeforeEach
  public void setUp() {
    movieController = new MovieController();
    comparisonController = movieController.getComparisonController();
    mainFrame = new MainFrame(movieController, comparisonController);
    mainFrame.setVisible(true);
  }

  /**
   * Tests whether the frame is initialized as expected
   */
  @Test
  public void testFrameInitialization() {
    assertEquals("My Movie List", mainFrame.getTitle());
    assertEquals(new Dimension(800, 700), mainFrame.getSize());
    assertEquals(JFrame.EXIT_ON_CLOSE, mainFrame.getDefaultCloseOperation());
  }

  /**
   * Tests whether all components are initialized and added to the frame
   */
  @Test
  public void testPanelLayout() {
    Component[] components = mainFrame.getContentPane().getComponents();
    boolean hasTopPanel = false;
    boolean hasFilterPanel = false;
    boolean hasMovieListPanel = false;

    for (Component c : components) {
      if (c instanceof JPanel panel) {
        LayoutManager layout = panel.getLayout();
        if (layout instanceof BorderLayout) {
          hasTopPanel = true;
        }
      }
      if (c.getClass().getSimpleName().contains("FilterPanel")) {
        hasFilterPanel = true;
      }
      if (c.getClass().getSimpleName().contains("MovieListPanel")) {
        hasMovieListPanel = true;
      }
    }

    assertTrue(hasTopPanel);
    assertTrue(hasFilterPanel);
    assertTrue(hasMovieListPanel);
  }
}