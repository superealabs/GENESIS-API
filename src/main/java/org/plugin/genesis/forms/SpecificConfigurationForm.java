package org.plugin.genesis.forms;

import com.intellij.ui.components.JBList;
import genesis.config.langage.Framework;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.plugin.genesis.wizards.SpecificConfigurationWizardStep.frameworkHasConfiguration;

@Getter
public class SpecificConfigurationForm {
    private JPanel mainPanel;
    private JTextField projectPortField;
    private JLabel projectPortLabel;
    private JLabel projectDescriptionLabel;
    private JTextField projectDescriptionField;
    private JLabel loggingLevelLabel;
    private JComboBox<String> loggingLevelOptions;
    private JComboBox<String> ddlAutoOptions;
    private JLabel hibernateDDLAutoLabel;
    private JCheckBox useAnEurekaServerCheckBox;
    private JLabel routeConfigurationLabel;
    private JTextField eurekaServerHostField;
    private JLabel eurekaServerHostLabel;
    private JTable routeConfigurationOption;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel defaultUsernameLabel;
    private JLabel passwordLabel;

    private Framework framework; // Ajout pour stocker le framework sélectionné

    /**
     * Initialise les composants avec un framework par défaut.
     */
    public void initializeForm() {
        // Masquer tous les composants dépendants au début
        hibernateDDLAutoLabel.setVisible(false);
        ddlAutoOptions.setVisible(false);
        loggingLevelLabel.setVisible(false);
        loggingLevelOptions.setVisible(false);
        routeConfigurationLabel.setVisible(false);
        routeConfigurationOption.setVisible(false);
        defaultUsernameLabel.setVisible(false);
        usernameField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);

        // Activer le champ Eureka par défaut
        eurekaServerHostField.setEnabled(true);

        // Ajouter un listener pour le bouton "useAnEurekaServer"
        useAnEurekaServerCheckBox.addActionListener(e -> {
            boolean selected = useAnEurekaServerCheckBox.isSelected();
            eurekaServerHostField.setEnabled(selected);
        });
    }

    /**
     * Met à jour dynamiquement les composants en fonction du framework sélectionné.
     *
     * @param framework Le framework sélectionné.
     */
    public void updateFormWithFramework(Framework framework) {
        this.framework = framework;

        // Gérer hibernateDDL Auto
        if (frameworkHasConfiguration(framework, "hibernateDdlAuto")) {
            populateDdlAutoOptions(framework);
            hibernateDDLAutoLabel.setVisible(true);
            ddlAutoOptions.setVisible(true);
        } else {
            hibernateDDLAutoLabel.setVisible(false);
            ddlAutoOptions.setVisible(false);
        }

        // Gérer loggingLevel
        if (frameworkHasConfiguration(framework, "loggingLevel")) {
            populateLoggingLevelOptions(framework);
            loggingLevelLabel.setVisible(true);
            loggingLevelOptions.setVisible(true);
        } else {
            loggingLevelLabel.setVisible(false);
            loggingLevelOptions.setVisible(false);
        }

        // Gérer le tableau de configuration des routes
        if (framework != null && framework.getIsGateway()) {
            routeConfigurationLabel.setVisible(true);
            routeConfigurationOption.setVisible(true);
            initializeRouteConfigurationTable();

            defaultUsernameLabel.setVisible(true);
            usernameField.setVisible(true);
            passwordLabel.setVisible(true);
            passwordField.setVisible(true);

        } else {
            routeConfigurationLabel.setVisible(false);
            routeConfigurationOption.setVisible(false);
        }
    }

    private void populateDdlAutoOptions(Framework framework) {
        ddlAutoOptions.removeAllItems(); // Nettoyer les options précédentes
        framework.getConfigurations().stream()
                .filter(config -> "hibernateDdlAuto".equals(config.getVariableName()))
                .flatMap(config -> config.getOptions().stream())
                .forEach(option -> ddlAutoOptions.addItem(option)); // Ajouter chaque option
    }

    private void populateLoggingLevelOptions(Framework framework) {
        loggingLevelOptions.removeAllItems(); // Nettoyer les options précédentes
        framework.getConfigurations().stream()
                .filter(config -> "loggingLevel".equals(config.getVariableName()))
                .flatMap(config -> config.getOptions().stream())
                .forEach(option -> loggingLevelOptions.addItem(option)); // Ajouter chaque option
    }

    private void initializeRouteConfigurationTable() {
        String[] columnNames = {"Route ID", "URI", "Path", "Methods"};
        Object[][] initialData = {};

        routeConfigurationOption.setModel(new DefaultTableModel(initialData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Toutes les cellules sont modifiables
            }
        });
    }

    public List<Map<String, String>> getRouteConfigurationData() {
        DefaultTableModel model = (DefaultTableModel) routeConfigurationOption.getModel();
        List<Map<String, String>> routes = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            Map<String, String> route = new HashMap<>();
            route.put("id", (String) model.getValueAt(i, 0));
            route.put("uri", (String) model.getValueAt(i, 1));
            route.put("path", (String) model.getValueAt(i, 2));
            route.put("method", (String) model.getValueAt(i, 3));
            routes.add(route);
        }
        return routes;
    }
}
