import controller.MovieController;
import controller.ComparisonController;
import model.Genre;
import view.MainFrame;

import javax.swing.*;

/**
 * This class represents the entry point of the Movie Ranking application.
 * It initializes the app by creating the main frame and setting up the Controllers.
 */
public class MovieRankingApp {

  public static void main(String[] args) {
    // use the Event Dispatch Thread for Swing applications
    SwingUtilities.invokeLater(() -> {
      MovieController controller = new MovieController();
      ComparisonController comparisonController = controller.getComparisonController();
      MainFrame view = new MainFrame(controller, comparisonController);
      controller.setView(view);

      // add sample Movies to create an initial MovieList to make scoring appear reasonable
      controller.addMovie("Interstellar", Genre.SCIFI, 2014, "USA", true);
      controller.addMovie("Avengers: Endgame", Genre.ACTION, 2019, "USA", true);
      controller.addMovie("Barbie", Genre.COMEDY, 2023, "USA", true);
      controller.addMovie("Robot Dreams", Genre.ANIMATION, 2023, "SPAIN", true);
      controller.addMovie("Good Will Hunting", Genre.DRAMA, 1997, "USA", true);
      controller.addMovie("Parasite", Genre.DRAMA, 2019, "SOUTH KOREA", true);

      controller.addMovie("The Substance", Genre.HORROR, 2024, "FRANCE", false);
      controller.addMovie("Joker: Folie a Deux", Genre.MUSICAL, 2024, "USA", false);
      controller.addMovie("The Equalizer", Genre.ACTION, 2014, "USA", false);
      controller.addMovie("American Psycho", Genre.HORROR, 2000, "USA", false);

      view.setVisible(true);
    });
  }
}