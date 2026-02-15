package view;

import controller.MovieController;
import controller.ComparisonController;
import java.util.List;
import model.Movie;
import model.Genre;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the panel for adding a new Movie.
 */
public class AddMoviePanel extends JPanel {

  private MovieController controller;
  private ComparisonController comparisonController;

  private JTextField titleField;
  private JComboBox<Genre> genreCombo;
  private JTextField yearField;
  private JTextField countryField;
  private JRadioButton goodMovieRadio;
  private JRadioButton badMovieRadio;
  private JButton addButton;

  /**
   * Constructs the AddMoviePanel with the specified MovieController and ComparisonController
   *
   * @param controller           the MovieController to use
   * @param comparisonController the ComparisonController to use
   */
  public AddMoviePanel(MovieController controller, ComparisonController comparisonController) {
    this.controller = controller;
    this.comparisonController = comparisonController;

    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createTitledBorder("Add New Movie"));
    initializeComponents();
    layoutComponents();
    registerListeners();
  }

  /**
   * Initializes all components of the panel
   */
  private void initializeComponents() {
    titleField = new JTextField(20);
    genreCombo = new JComboBox<>(Genre.values());
    yearField = new JTextField(20);
    countryField = new JTextField(20);

    goodMovieRadio = new JRadioButton("This is great!", true);
    badMovieRadio = new JRadioButton("Ehh.. Fine.", false);
    ButtonGroup categoryGroup = new ButtonGroup();
    categoryGroup.add(goodMovieRadio);
    categoryGroup.add(badMovieRadio);

    addButton = new JButton("Add Movie");
  }

  /**
   * Sets up the layout of all components in the panel
   */
  private void layoutComponents() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Title row
    gbc.gridx = 0;
    gbc.gridy = 0;
    add(new JLabel("Movie Title:"), gbc);
    gbc.gridx = 1;
    add(titleField, gbc);

    // Genre row
    gbc.gridx = 0;
    gbc.gridy = 1;
    add(new JLabel("Genre:"), gbc);
    gbc.gridx = 1;
    add(genreCombo, gbc);

    // Year row
    gbc.gridx = 0;
    gbc.gridy = 2;
    add(new JLabel("Year:"), gbc);
    gbc.gridx = 1;
    add(yearField, gbc);

    // Country row
    gbc.gridx = 0;
    gbc.gridy = 3;
    add(new JLabel("Country:"), gbc);
    gbc.gridx = 1;
    add(countryField, gbc);

    // Category row
    gbc.gridx = 0;
    gbc.gridy = 4;
    add(new JLabel("Category:"), gbc);
    JPanel categoryPanel = new JPanel();
    categoryPanel.add(goodMovieRadio);
    categoryPanel.add(badMovieRadio);
    gbc.gridx = 1;
    add(categoryPanel, gbc);

    // Button row
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    add(addButton, gbc);
  }

  /**
   * Registers action listeners for the components
   */
  private void registerListeners() {
    addButton.addActionListener(e -> {
      try {
        String title = titleField.getText().trim();
        // Design Rule #6: catch and handle/report errors as early as possible.
        // error handling for empty title
        if (title.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Title is required.",
              "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
        Genre genre = (Genre) genreCombo.getSelectedItem();
        int year = yearField.getText().isEmpty() ? 0 : Integer.parseInt(yearField.getText().trim());
        String country = countryField.getText().trim();
        boolean isGood = goodMovieRadio.isSelected();

        // create the Movie from user input, but don't add it to the list yet
        Movie newMovie = new Movie(title, genre, year, country, isGood);

        // check if this movie already exists in the list
        List<Movie> existingMovies = controller.getMovies();
        if (existingMovies.contains(newMovie)) {
          int confirm = JOptionPane.showConfirmDialog(
              this,
              "This movie already exists in your list.\nDo you want to add it anyway?",
              "Duplicate Movie",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE
          );
          if (confirm != JOptionPane.YES_OPTION) {
            return; // User canceled addition
          }
        }

        // calls the ComparisonController to start the comparison process
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        comparisonController.startComparisonProcess(newMovie, parentFrame);

        // clear fields after adding
        titleField.setText("");
        genreCombo.setSelectedIndex(0);
        yearField.setText("");
        countryField.setText("");

      } catch (NumberFormatException ex) {
        // Design Rule #8: check inputs.
        // error handling for invalid year input
        JOptionPane.showMessageDialog(this, "Year must be a number.", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    });
  }
}