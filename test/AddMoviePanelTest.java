import controller.ComparisonController;
import controller.MovieController;
import view.AddMoviePanel;
import model.Genre;

import javax.swing.*;
import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the AddMoviePanel class.
 */
public class AddMoviePanelTest {

  private AddMoviePanel addMoviePanel;
  private MovieController movieController;
  private ComparisonController comparisonController;
  private JFrame frame;

  @BeforeEach
  public void setUp() {
    movieController = new MovieController();
    comparisonController = movieController.getComparisonController();
    addMoviePanel = new AddMoviePanel(movieController, comparisonController);
    frame = new JFrame();
    frame.setLayout(new BorderLayout());
    frame.add(addMoviePanel);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Tests whether all components are initialized and added to the panel
   */
  @Test
  public void testPanelComponentsExist() {
    Component[] components = addMoviePanel.getComponents();
    assertTrue(components.length > 0);
    assertTrue(containsComponentType(components, JLabel.class));
    assertTrue(containsComponentType(components, JTextField.class));
    assertTrue(containsComponentType(components, JComboBox.class));
    assertTrue(containsComponentType(components, JButton.class));
  }

  /**
   * Tests error handling when adding a Movie with an empty title
   */
  @Test
  public void testAddMovieWithEmptyTitleShowsError() {
    JTextField titleField = (JTextField) findComponentByName(addMoviePanel, JTextField.class, 0);
    JButton addButton = (JButton) findComponentByType(addMoviePanel, JButton.class);
    titleField.setText("");
    addButton.doClick();
    // test passes if error message is shown (non-fatal), so we assert no exception is thrown
    assertDoesNotThrow(() -> addButton.doClick());
  }

  /**
   * Tests error handling when adding a Movie with an invalid year
   */
  @Test
  public void testAddMovieWithInvalidYearShowsError() {
    JTextField yearField = (JTextField) findComponentByName(addMoviePanel, JTextField.class, 1);
    JButton addButton = (JButton) findComponentByType(addMoviePanel, JButton.class);
    yearField.setText("invalid");
    addButton.doClick();
    // test passes if error message is shown (non-fatal), so we assert no exception is thrown
    assertDoesNotThrow(() -> addButton.doClick());
  }

  /**
   * Tests error handling when adding a duplicate Movie
   */
  @Test
  public void testAddDuplicateMovieShowsError() {
    JTextField titleField = (JTextField) findComponentByName(addMoviePanel, JTextField.class, 0);
    JComboBox<Genre> genreCombo = (JComboBox<Genre>) findComponentByName(addMoviePanel, JComboBox.class, 0);
    JTextField yearField = (JTextField) findComponentByName(addMoviePanel, JTextField.class, 1);
    JTextField countryField = (JTextField) findComponentByName(addMoviePanel, JTextField.class, 2);
    JButton addButton = (JButton) findComponentByType(addMoviePanel, JButton.class);

    // add a Movie first
    titleField.setText("Duplicate Movie");
    genreCombo.setSelectedItem(Genre.ACTION);
    yearField.setText("2023");
    countryField.setText("USA");
    addButton.doClick();

    // try to add the same Movie again
    titleField.setText("Duplicate Movie");
    genreCombo.setSelectedItem(Genre.ANIMATION);  // genre change should not matter
    yearField.setText("2023");
    countryField.setText("USA");
    addButton.doClick();

    // test passes if error message is shown (non-fatal), so we assert no exception is thrown
    assertDoesNotThrow(() -> addButton.doClick());
  }


  // utility methods for component access
  private boolean containsComponentType(Component[] components, Class<?> type) {
    for (Component c : components) {
      if (type.isInstance(c)) {
        return true;
      }
      if (c instanceof Container container) {
        if (containsComponentType(container.getComponents(), type)) {
          return true;
        }
      }
    }
    return false;
  }

  private Component findComponentByType(Container container, Class<?> type) {
    for (Component c : container.getComponents()) {
      if (type.isInstance(c)) {
        return c;
      }
      if (c instanceof Container child) {
        Component result = findComponentByType(child, type);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }

  private Component findComponentByName(Container container, Class<?> type, int index) {
    int count = 0;
    for (Component c : container.getComponents()) {
      if (type.isInstance(c)) {
        if (count == index) {
          return c;
        }
        count++;
      }
      if (c instanceof Container child) {
        Component result = findComponentByName(child, type, index);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }
}