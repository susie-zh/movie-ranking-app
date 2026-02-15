package view;

import controller.FilterController;
import controller.MovieController;
import model.Filter;
import model.Genre;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the panel for filtering the MovieList.
 */
public class FilterPanel extends JPanel {

  private MovieController movieController;
  private FilterController filterController;

  private JComboBox<Genre> genreCombo;
  private JTextField fromYearField;
  private JTextField toYearField;
  private JTextField countryField;
  private JComboBox<String> categoryCombo;
  private JButton applyFilterButton;
  private JButton resetFilterButton;

  /**
   * Constructs the FilterPanel with the specified MovieController and FilterController
   *
   * @param movieController  the MovieController to use
   * @param filterController the FilterController to use
   */
  public FilterPanel(MovieController movieController, FilterController filterController) {
    this.movieController = movieController;
    this.filterController = filterController;

    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createTitledBorder("Filter Movies"));
    setPreferredSize(new Dimension(200, 400));
    initializeComponents();
    layoutComponents();
    registerListeners();
  }

  /**
   * Initializes all components of the panel
   */
  private void initializeComponents() {
    // creates a drop-down list of genres for user to choose from
    DefaultComboBoxModel<Genre> genreModel = new DefaultComboBoxModel<>();
    // show the first option as blank in the drop-down list
    genreModel.addElement(null);
    for (Genre g : Genre.values()) {
      genreModel.addElement(g);
    }
    genreCombo = new JComboBox<>(genreModel);

    fromYearField = new JTextField(15);
    toYearField = new JTextField(15);
    countryField = new JTextField(15);

    String[] categories = {"All", "Good Movies", "Bad Movies"};
    categoryCombo = new JComboBox<>(categories);

    applyFilterButton = new JButton("Apply Filter");
    resetFilterButton = new JButton("Reset Filter");
  }

  /**
   * Sets up the layout of all components in the panel
   */
  private void layoutComponents() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;

    // Gnere row
    gbc.gridx = 0;
    gbc.gridy = 0;
    add(new JLabel("Genre:"), gbc);
    gbc.gridy = 1;
    add(genreCombo, gbc);

    // FromYear row
    gbc.gridy = 2;
    add(new JLabel("From Year:"), gbc);
    gbc.gridy = 3;
    add(fromYearField, gbc);

    // ToYear row
    gbc.gridy = 4;
    add(new JLabel("To Year:"), gbc);
    gbc.gridy = 5;
    add(toYearField, gbc);

    // Country row
    gbc.gridy = 6;
    add(new JLabel("Country:"), gbc);
    gbc.gridy = 7;
    add(countryField, gbc);

    // Category row
    gbc.gridy = 8;
    add(new JLabel("Category:"), gbc);
    gbc.gridy = 9;
    add(categoryCombo, gbc);

    // Button rows
    gbc.gridy = 10;
    gbc.insets = new Insets(15, 5, 5, 5);
    add(applyFilterButton, gbc);
    gbc.gridy = 11;
    gbc.insets = new Insets(5, 5, 5, 5);
    add(resetFilterButton, gbc);
  }

  /**
   * Registers action listeners for the components
   */
  private void registerListeners() {
    applyFilterButton.addActionListener(e -> {
      Filter.Builder filterBuilder = new Filter.Builder();

      // get genre
      Genre selectedGenre = (Genre) genreCombo.getSelectedItem();
      if (selectedGenre != null) {
        filterBuilder.withGenre(selectedGenre);
      }

      // get year range
      try {
        Integer fromYear = fromYearField.getText().trim().isEmpty() ? null
            : Integer.parseInt(fromYearField.getText().trim());
        Integer toYear = toYearField.getText().trim().isEmpty() ? null
            : Integer.parseInt(toYearField.getText().trim());
        if (fromYear != null || toYear != null) {
          filterBuilder.withYearRange(fromYear, toYear);
        }
      } catch (NumberFormatException ex) {
        // error handling for invalid year input
        JOptionPane.showMessageDialog(this, "Year must be a number.",
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      // get country
      String country = countryField.getText().trim();
      if (!country.isEmpty()) {
        filterBuilder.withCountry(country);
      }

      // get category
      String selectedCategory = (String) categoryCombo.getSelectedItem();
      if (!"All".equals(selectedCategory)) {
        filterBuilder.withGoodMovieCategory("Good Movies".equals(selectedCategory));
      }

      Filter filter = filterBuilder.build();
      filterController.applyFilter(filter);
      movieController.updateView();
    });

    // reset all filter fields when Reset button is clicked
    resetFilterButton.addActionListener(e -> {
      genreCombo.setSelectedIndex(0);
      fromYearField.setText("");
      toYearField.setText("");
      countryField.setText("");
      categoryCombo.setSelectedIndex(0);
      filterController.resetFilter();
      movieController.updateView();
    });
  }
}