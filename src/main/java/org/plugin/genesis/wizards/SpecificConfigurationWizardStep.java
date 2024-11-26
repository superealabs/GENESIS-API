package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import genesis.config.langage.Framework;
import genesis.config.langage.generator.project.ProjectGenerator;
import handler.ProjectGenerationContext;
import org.plugin.genesis.forms.SpecificConfigurationForm;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SpecificConfigurationWizardStep extends ModuleWizardStep {
    private final SpecificConfigurationForm specificConfigurationForm;
    private final ProjectGenerationContext projectGenerationContext;

    public SpecificConfigurationWizardStep(ProjectGenerationContext projectGenerationContext) {
        this.specificConfigurationForm = new SpecificConfigurationForm();
        this.projectGenerationContext = projectGenerationContext;

        // Initialiser les composants du formulaire
        specificConfigurationForm.initializeForm();
    }

    @Override
    public JComponent getComponent() {
        return specificConfigurationForm.getMainPanel();
    }
    @Override
    public void updateDataModel() {
        Framework framework = projectGenerationContext.getFramework();
        Map<String, Object> frameworkConfiguration = new HashMap<>();

        // Gestion d'Eureka
        if (specificConfigurationForm.getUseAnEurekaServerCheckBox().isSelected()) {
            frameworkConfiguration.put("eurekaServerHost", specificConfigurationForm.getEurekaServerHostField().getText().trim());
        }

        // Gestion de loggingLevel
        frameworkConfiguration.put("loggingLevel", Objects.requireNonNullElseGet(
                specificConfigurationForm.getLoggingLevelOptions().getSelectedItem(), () -> "").toString()
        );

        // Gestion de hibernate ddl option
        frameworkConfiguration.put("hibernateDdlAuto", Objects.requireNonNullElseGet(
                specificConfigurationForm.getDdlAutoOptions().getSelectedItem(), () -> "").toString()
        );

        // Gestion des routes si c'est une Gateway
        if (framework != null && framework.getIsGateway()) {
            frameworkConfiguration.put("routes", specificConfigurationForm.getRouteConfigurationData());
        }

        // Ajouter projectPort et projectDescription au contexte
        projectGenerationContext.setProjectPort(specificConfigurationForm.getProjectPortField().getText().trim());
        projectGenerationContext.setProjectDescription(specificConfigurationForm.getProjectDescriptionField().getText().trim());

        projectGenerationContext.setFrameworkConfiguration(frameworkConfiguration);

        // Génération du projet
        generateProject();
    }


    @Override
    public boolean validate() throws ConfigurationException {
        Framework framework = projectGenerationContext.getFramework();

        // Valider les options spécifiques au framework
        validateLoggingLevel(framework);
        validateHibernateDdlAuto(framework);

        // Valider les options Eureka Server
        validateEurekaServer();

        // Valider les champs spécifiques au projet
        validateProjectPort();
        validateProjectDescription();

        return true;
    }

    private void validateProjectPort() throws ConfigurationException {
        String projectPort = specificConfigurationForm.getProjectPortField().getText().trim();
        if (projectPort.isEmpty()) {
            throw new ConfigurationException("Project Port cannot be empty.");
        }
        try {
            int port = Integer.parseInt(projectPort);
            if (port < 1 || port > 65535) {
                throw new ConfigurationException("Project Port must be a valid number between 1 and 65535.");
            }
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Project Port must be a valid integer.");
        }
    }

    private void validateProjectDescription() throws ConfigurationException {
        String projectDescription = specificConfigurationForm.getProjectDescriptionField().getText().trim();
        if (projectDescription.isEmpty()) {
            throw new ConfigurationException("Project Description cannot be empty.");
        }
    }


    private void validateLoggingLevel(Framework framework) throws ConfigurationException {
        if (frameworkHasConfiguration(framework, "loggingLevel")) {
            String loggingLevel = Objects.toString(specificConfigurationForm.getLoggingLevelOptions().getSelectedItem(), "").trim();
            if (loggingLevel.isEmpty()) {
                throw new ConfigurationException("Logging Level cannot be empty.");
            }
        }
    }

    private void validateHibernateDdlAuto(Framework framework) throws ConfigurationException {
        if (frameworkHasConfiguration(framework, "hibernateDdlAuto")) {
            String hibernateDdlAuto = Objects.toString(specificConfigurationForm.getDdlAutoOptions().getSelectedItem(), "").trim();
            if (hibernateDdlAuto.isEmpty()) {
                throw new ConfigurationException("Hibernate DDL Auto cannot be empty.");
            }
        }
    }

    private void validateEurekaServer() throws ConfigurationException {
        if (specificConfigurationForm.getUseAnEurekaServerCheckBox().isSelected() &&
                specificConfigurationForm.getEurekaServerHostField().getText().trim().isEmpty()) {
            throw new ConfigurationException("Eureka Server Host cannot be empty if Eureka is enabled.");
        }
    }

    private void generateProject() {
        ProjectGenerator projectGenerator = new ProjectGenerator();
        try {
            projectGenerator.generateProject(projectGenerationContext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Project generation failed: " + e.getMessage(), e);
        }
    }



    /**
     * Met à jour le formulaire en fonction du framework sélectionné.
     */
    public void onFrameworkSelected(Framework framework) {
        if (framework == null) {
            throw new IllegalArgumentException("Framework must not be null");
        }
        specificConfigurationForm.updateFormWithFramework(framework);
    }


    public static boolean frameworkHasConfiguration(Framework framework, String variableName) {
        return framework != null && framework.getConfigurations().stream()
                .anyMatch(config -> variableName.equals(config.getVariableName()));
    }

}
