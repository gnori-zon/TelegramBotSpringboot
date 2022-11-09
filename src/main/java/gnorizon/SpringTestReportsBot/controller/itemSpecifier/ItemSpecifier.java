package gnorizon.SpringTestReportsBot.controller.itemSpecifier;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;

/**
 * Controller to define data and write it to the model
 */
public interface ItemSpecifier {
    /**
     * Main method to define step and call help-method
     * @param message it's data
     * @param report it's model-entity
     * @return string for response to client
     */
    String checkingAndWrite(String message, Report report);

}
