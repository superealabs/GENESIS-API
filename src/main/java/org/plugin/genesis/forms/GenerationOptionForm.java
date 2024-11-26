package org.plugin.genesis.forms;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.labels.LinkLabel;
import genesis.connexion.Database;
import handler.ProjectGenerationContext;
import lombok.Getter;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

@Getter
public class GenerationOptionForm {
    private final ProjectGenerationContext projectGenerationContext;
    private JPanel mainPanel;
    private JBList<String> tableNamesList;
    private JLabel tableAvailableLabel;
    private LinkLabel<String> refreshLinkLabel;
    private JBList<String> componentChoice;
    private JLabel componentsLabel;

    public GenerationOptionForm(ProjectGenerationContext projectGenerationContext) {
        this.projectGenerationContext = projectGenerationContext;
        setupListeners();
    }

    private void setupListeners() {
        assert refreshLinkLabel != null;
        refreshLinkLabel.setListener((LinkLabel<String> source, String data) -> populateTableNames(), null);
    }

    public void populateTableNames() {
        Database database = projectGenerationContext.getDatabase();
        Connection connection = projectGenerationContext.getConnection();

        if (database != null && connection != null) {
            try {
                List<String> allTableNames = database.getAllTableNames(connection);
                allTableNames.addFirst("* (select all)");
                tableNamesList.setListData(allTableNames.toArray(new String[0]));
            } catch (Exception e) {
                Messages.showErrorDialog(
                        mainPanel,
                        "Failed to retrieve table names: " + e.getMessage(),
                        "Error"
                );
            }
        } else {
            Messages.showErrorDialog(
                    mainPanel,
                    "Database or connection not defined.",
                    "Error"
            );
        }
    }
}

