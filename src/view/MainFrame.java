package view;

import controller.MovieController;
import controller.ComparisonController;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the main frame of the application.
 * It implements the IView interface and contains main components of the GUI.
 */
public class MainFrame extends JFrame implements IView {

  private MovieController controller;
  private ComparisonController comparisonController;
  private AddMoviePanel addMoviePanel;
  private MovieListPanel movieListPanel;
  private FilterPanel filterPanel;

  /**
   * Constructs the MainFrame with the specified MovieController and ComparisonController
   *
   * @param controller           the MovieController to use
   * @param comparisonController the Comparison Controller to use
   */
  public MainFrame(MovieController controller, ComparisonController comparisonController) {
    this.controller = controller;
    this.comparisonController = comparisonController;

    setTitle("My Movie List");
    setSize(800, 700);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // initialize all 3 panels
    addMoviePanel = new AddMoviePanel(controller, comparisonController);
    movieListPanel = new MovieListPanel(controller);
    filterPanel = new FilterPanel(controller, controller.getFilterController());

    // add all 3 panels to frame
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(addMoviePanel, BorderLayout.CENTER);
    add(topPanel, BorderLayout.NORTH);
    add(filterPanel, BorderLayout.WEST);
    add(movieListPanel, BorderLayout.CENTER);
  }

  /**
   * Refreshes the MovieListPanel when updates are made
   */
  public void refresh() {
    movieListPanel.updateMovieList();
    revalidate();
    repaint();
  }
}