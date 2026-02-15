import controller.FilterController;
import controller.MovieController;
import model.Filter;
import model.Genre;
import view.FilterPanel;

import javax.swing.*;
import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the FilterPanel class.
 */
public class FilterPanelTest {

  private FilterPanel filterPanel;
  private MovieController movieController;
  private FilterController filterController;
  private JFrame frame;

  @BeforeEach
  public void setUp() {
    movieController = new MovieController();
    filterController = movieController.getFilterController();
    filterPanel = new FilterPanel(movieController, filterController);
    frame = new JFrame();
    frame.setLayout(new BorderLayout());
    frame.add(filterPanel);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Tests whether all components are initialized and added to the panel
   */
  @Test
  public void testPanelComponentsExist() {
    Component[] components = filterPanel.getComponents();
    assertTrue(containsComponentType(components, JComboBox.class));
    assertTrue(containsComponentType(components, JTextField.class));
    assertTrue(containsComponentType(components, JButton.class));
  }

  /**
   * Tests whether the filter is applied correctly when a year range is selected
   */
  @Test
  public void testApplyFilterWithYearRange() {
    JTextField fromYear = (JTextField) findComponentByName(filterPanel, JTextField.class, 0);
    JTextField toYear = (JTextField) findComponentByName(filterPanel, JTextField.class, 1);
    JButton applyButton = (JButton) findComponentByText(filterPanel, "Apply Filter");
    fromYear.setText("2000");
    toYear.setText("2010");
    applyButton.doClick();
    Filter current = filterController.getCurrentFilter();

    assertNotNull(current);
    assertTrue(current.apply(new model.Movie("Test 1", Genre.ACTION, 2005, "USA", true)));
    assertFalse(current.apply(new model.Movie("Test 2", Genre.ANIMATION, 1995, "UK", true)));
  }

  /**
   * Tests whether the filter is reset correctly when the reset button is clicked
   */
  @Test
  public void testResetFilterClearsFields() {
    JTextField fromYear = (JTextField) findComponentByName(filterPanel, JTextField.class, 0);
    fromYear.setText("2000");
    JButton resetButton = (JButton) findComponentByText(filterPanel, "Reset Filter");
    resetButton.doClick();

    assertEquals("", fromYear.getText());
    assertNull(filterController.getCurrentFilter());
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

  private Component findComponentByText(Container container, String text) {
    for (Component c : container.getComponents()) {
      if (c instanceof JButton b && b.getText().equals(text)) {
        return b;
      }
      if (c instanceof Container child) {
        Component result = findComponentByText(child, text);
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