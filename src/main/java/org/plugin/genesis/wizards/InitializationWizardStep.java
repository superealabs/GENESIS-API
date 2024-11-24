package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import genesis.config.langage.Framework;
import genesis.config.langage.Language;
import genesis.config.langage.Project;
import handler.ProjectGenerationContext;
import org.plugin.genesis.forms.InitializationForm;

import javax.swing.*;
import java.util.Map;

public class InitializationWizardStep extends ModuleWizardStep {
    private final InitializationForm newProjectPanel;
    private final ProjectGenerationContext projectGenerationContext;

    public InitializationWizardStep(ProjectGenerationContext projectGenerationContext) {
        newProjectPanel = new InitializationForm();
        this.projectGenerationContext = projectGenerationContext;
    }

    @Override
    public JComponent getComponent() {
        return newProjectPanel.getMainPanel();
    }

    @Override
    public void updateDataModel() {
        // Récupérer les valeurs depuis le formulaire
        String projectName = newProjectPanel.getProjectNameField().getText().trim();
        String location = newProjectPanel.getLocationField().getText().trim();
        Language language = (Language) newProjectPanel.getLanguageOptions().getSelectedItem();
        String languageVersion = (String) newProjectPanel.getLanguageVersionOptions().getSelectedItem();
        Framework framework = (Framework) newProjectPanel.getFrameworkOptions().getSelectedItem();
        Project buildTool = (Project) newProjectPanel.getBuildToolOptions().getSelectedItem();

        assert languageVersion != null;
        Map<String, Object> languageConfiguration = Map.of("languageVersion", languageVersion);
        projectGenerationContext.setLanguageConfiguration(languageConfiguration);

        projectGenerationContext
                .setProjectName(projectName)
                .setDestinationFolder(location)
                .setLanguage(language)
                .setFramework(framework)
                .setProject(buildTool);
    }

    @Override
    public boolean validate() throws ConfigurationException {
        // Retrieve selected values
        String projectName = newProjectPanel.getProjectNameField().getText().trim();
        String location = newProjectPanel.getLocationField().getText().trim();
        Language language = (Language) newProjectPanel.getLanguageOptions().getSelectedItem();
        String languageVersion = (String) newProjectPanel.getLanguageVersionOptions().getSelectedItem();
        String framework = (String) newProjectPanel.getCoreFrameworkOptions().getSelectedItem();
        Framework projectType = (Framework) newProjectPanel.getFrameworkOptions().getSelectedItem();
        Project buildTool = (Project) newProjectPanel.getBuildToolOptions().getSelectedItem();

        // Validate the project name
        if (projectName.isEmpty()) {
            throw new ConfigurationException("The project name cannot be empty.");
        }
        if (!projectName.matches("^[a-zA-Z0-9_]+$")) {
            throw new ConfigurationException("""
                        The project name must be a single word containing only letters, numbers, and underscores.
                        No special characters or spaces are allowed.
                    """);
        }

        // Check that the location path is not empty
        if (location.isEmpty()) {
            throw new ConfigurationException("The location path cannot be empty.");
        }

        // Check that a language is selected
        if (language == null) {
            throw new ConfigurationException("Please select a language.");
        }

        // Check that a language version is selected
        if (languageVersion == null || languageVersion.equals("Not applicable")) {
            throw new ConfigurationException("Please select a language version.");
        }

        // Check that a framework is selected
        if (framework == null) {
            throw new ConfigurationException("Please select a framework.");
        }

        // Check that a project type is selected
        if (projectType == null) {
            throw new ConfigurationException("Please select a project type.");
        }

        // Check that a build tool is selected
        if (buildTool == null) {
            throw new ConfigurationException("Please select a build tool.");
        }

        // Print selected values to the console
        System.out.println("Project Name: " + projectName);
        System.out.println("Location: " + location);
        System.out.println("Language: " + language.getName());
        System.out.println("Language Version: " + languageVersion);
        System.out.println("Framework: " + framework);
        System.out.println("Project Type: " + projectType.getName());
        System.out.println("Build Tool: " + buildTool.getName());

        // If all validations pass, return true
        return true;
    }


}
