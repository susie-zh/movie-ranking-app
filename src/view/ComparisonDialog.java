package view;

import controller.ComparisonController;
import controller.MovieController;
import model.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class represents a dialog for comparing the user-input Movie with an existing one.
 */
public class ComparisonDialog extends JDialog {

  private Movie newMovie;
  private Movie comparisonMovie;
  private ComparisonController comparisonController;
  private MovieController movieController;

  private JLabel movieInfoLabel;
  private JLabel comparisonLabel;
  private JLabel questionLabel;
  private JButton betterButton;
  private JButton worseButton;
  private boolean comparisonCompleted = false;

  /**
   * Constructs the ComparisonDialog
   *
   * @param parentFrame          the parent frame for dialog display
   * @param newMovie             the user-input Movie to compare
   * @param comparisonMovie      the existing Movie in MovieList to compare with
   * @param comparisonController the ComparisonController to use
   * @param movieController      the MovieController to use
   */
  public ComparisonDialog(JFrame parentFrame, Movie newMovie, Movie comparisonMovie,
      ComparisonController comparisonController, MovieController movieController) {
    // a 'modal' is a type of dialog that requires user interaction/decision before
    // returning to the main application
    super(parentFrame, "Compare Movies", true);
    this.newMovie = newMovie;
    this.comparisonMovie = comparisonMovie;
    this.comparisonController = comparisonController;
    this.movieController = movieController;

    initializeComponents();
    layoutComponents();
    registerListeners();

    // set dialog properties
    pack();
    setMinimumSize(new Dimension(400, 250));
    setLocationRelativeTo(parentFrame);
    // keep dialog open until user closes it
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

    // add a window listener to handle dialog closing
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        // add a confirmation prompt when user tries to close the dialog
        int confirm = JOptionPane.showConfirmDialog(
            ComparisonDialog.this,
            "Are you sure you want to cancel adding this movie?",
            null,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        // when user clicks 'YES'
        if (confirm == JOptionPane.YES_OPTION) {
          comparisonCompleted = false;
          dispose();
        }
      }
    });
  }

  /**
   * Initializes all components of the dialog
   */
  private void initializeComponents() {
    String newMovieInfo = String.format("<html><b>New movie:</b> %s (%d) - %s</html>",
        newMovie.getTitle(), newMovie.getYear(), newMovie.getGenre());

    String comparisonMovieInfo = String.format("<html><b>Compared with:</b> %s (%d) - %s</html>",
        comparisonMovie.getTitle(), comparisonMovie.getYear(), comparisonMovie.getGenre());

    questionLabel = new JLabel("<html><h3>Which do you think is better?</h3></html>");
    movieInfoLabel = new JLabel(newMovieInfo);
    comparisonLabel = new JLabel(comparisonMovieInfo);
    betterButton = new JButton(newMovie.getTitle());
    worseButton = new JButton(comparisonMovie.getTitle());
  }

  /**
   * Sets up the layout of all components in the dialog
   */
  private void layoutComponents() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 0;

    panel.add(questionLabel, gbc);

    gbc.gridy = 1;
    panel.add(movieInfoLabel, gbc);

    gbc.gridy = 2;
    panel.add(comparisonLabel, gbc);

    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
    buttonPanel.add(betterButton);
    buttonPanel.add(worseButton);

    gbc.gridy = 3;
    gbc.insets = new Insets(20, 10, 10, 10);
    panel.add(buttonPanel, gbc);

    getContentPane().add(panel);
  }

  /**
   * Registers action listeners for the components
   */
  private void registerListeners() {
    betterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // new Movie is better than comparison Movie
        // (-1 since better means it comes before)
        comparisonController.recordComparison(newMovie, comparisonMovie, -1);
        comparisonCompleted = true;
        dispose();
      }
    });

    worseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // new Movie is worse than comparison Movie
        // (1 since worse means it comes after)
        comparisonController.recordComparison(newMovie, comparisonMovie, 1);
        comparisonCompleted = true;
        dispose();
      }
    });
  }

  /**
   * Returns whether the comparison is completed
   *
   * @return true if comparison is completed, false otherwise
   */
  public boolean isComparisonCompleted() {
    return comparisonCompleted;
  }
}