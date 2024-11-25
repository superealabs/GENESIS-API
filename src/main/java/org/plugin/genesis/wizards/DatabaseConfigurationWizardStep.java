package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;
import genesis.connexion.Credentials;
import genesis.connexion.Database;
import handler.ProjectGenerationContext;
import org.plugin.genesis.forms.DatabaseConfigurationForm;

import javax.swing.*;

public class DatabaseConfigurationWizardStep extends ModuleWizardStep {
    private final DatabaseConfigurationForm databaseConfigurationForm;
    private final ProjectGenerationContext projectGenerationContext;

    public DatabaseConfigurationWizardStep(ProjectGenerationContext projectGenerationContext) {
        databaseConfigurationForm = new DatabaseConfigurationForm();
        this.projectGenerationContext = projectGenerationContext;
    }

    @Override
    public JComponent getComponent() {
        return databaseConfigurationForm.getMainPanel();
    }
    @Override
    public void updateDataModel() {
        // Retrieve selected database
        Database selectedDatabase = (Database) databaseConfigurationForm.getDmsOptions().getSelectedItem();

        if (selectedDatabase != null) {
            // Update database in the context
            projectGenerationContext.setDatabase(selectedDatabase);

            // Create credentials from the form inputs
            Credentials credentials = selectedDatabase.getCredentials();

            // Update credentials in the context
            projectGenerationContext.setCredentials(credentials);

            // Attempt to establish the connection
            try {
                // Close existing connection if any
                if (projectGenerationContext.getConnection() != null) {
                    projectGenerationContext.getConnection().close();
                }

                // Establish a new connection and update the context
                projectGenerationContext.setConnection(selectedDatabase.getConnection(credentials));

                // Update connection status label
                databaseConfigurationForm.getConnectionStatusLabel().setText("<html>Connection successful!</html>");
                databaseConfigurationForm.getConnectionStatusLabel().setForeground(JBColor.GREEN);
                databaseConfigurationForm.setConnectionSuccessful(true);
            } catch (Exception e) {
                // Update connection status label with error message
                String formattedMessageHtml = formatErrorMessageHtml(e.getMessage());
                databaseConfigurationForm.getConnectionStatusLabel().setText("<html>Connection failed:<br>" + formattedMessageHtml + "</html>");
                databaseConfigurationForm.getConnectionStatusLabel().setForeground(JBColor.RED);
                databaseConfigurationForm.setConnectionSuccessful(false);
            }
        }
    }

    @Override
    public boolean validate() throws ConfigurationException {
        // Validate required fields
        validateRequiredFields();

        // Validate port number
        validatePort();

        // Validate database-specific fields
        validateDatabaseSpecificFields();

        // Test connection and ensure it is successful
        if (!databaseConfigurationForm.isConnectionSuccessful()) {
            throw new ConfigurationException("Please test the connection and ensure it is successful before proceeding.");
        }

        // Display a message indicating success
        Messages.showInfoMessage(
                databaseConfigurationForm.getMainPanel(),
                "Connection test passed successfully. Proceeding to the next step.",
                "Connection Test Success"
        );

        return true;
    }


    private void validateRequiredFields() throws ConfigurationException {
        String host = databaseConfigurationForm.getHostField().getText().trim();
        String portStr = databaseConfigurationForm.getPortField().getText().trim();
        String databaseName = databaseConfigurationForm.getDatabaseField().getText().trim();
        String username = databaseConfigurationForm.getUsernameField().getText().trim();

        if (host.isEmpty()) {
            throw new ConfigurationException("Host field cannot be empty.");
        }
        if (portStr.isEmpty()) {
            throw new ConfigurationException("Port field cannot be empty.");
        }
        if (databaseName.isEmpty()) {
            throw new ConfigurationException("Database name cannot be empty.");
        }
        if (username.isEmpty()) {
            throw new ConfigurationException("Username field cannot be empty.");
        }
    }

    private void validatePort() throws ConfigurationException {
        String portStr = databaseConfigurationForm.getPortField().getText().trim();

        try {
            int port = Integer.parseInt(portStr);
            if (port <= 0 || port > 65535) {
                throw new ConfigurationException("Port must be between 1 and 65535.");
            }
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Port must be a valid integer.");
        }
    }

    private void validateDatabaseSpecificFields() throws ConfigurationException {
        Database selectedDatabase = (Database) databaseConfigurationForm.getDmsOptions().getSelectedItem();

        if (selectedDatabase != null && "Oracle".equalsIgnoreCase(selectedDatabase.getName())) {
            String sid = databaseConfigurationForm.getSidField().getText().trim();
            String driverType = databaseConfigurationForm.getDriverNameField().getText().trim();

            if (sid.isEmpty()) {
                throw new ConfigurationException("SID field cannot be empty for Oracle databases.");
            }
            if (driverType.isEmpty()) {
                throw new ConfigurationException("Driver Type field cannot be empty for Oracle databases.");
            }
        }
    }

    private void validateConnectionTest() throws ConfigurationException {
        if (!databaseConfigurationForm.isConnectionSuccessful()) {
            throw new ConfigurationException("Please test the connection and ensure it is successful before proceeding.");
        }
    }

    // Utility method to format error messages as HTML
    private String formatErrorMessageHtml(String message) {
        if (message == null || message.isEmpty()) {
            return "Unknown error.";
        }
        // Split the message by ". " (period followed by a space) and join with <br> for HTML
        return String.join("<br>", message.split("\\.\\s"));
    }
}
