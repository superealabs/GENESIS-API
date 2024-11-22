package org.plugin.genesis.module.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import org.plugin.genesis.module.form.InitialisationForm;

import javax.swing.*;
public class InitializationWizardStep extends ModuleWizardStep {
    private final InitialisationForm newProjectPanel;

    public InitializationWizardStep() {
        newProjectPanel = new InitialisationForm();
    }

    @Override
    public JComponent getComponent() {
        return newProjectPanel.getInitializationPanel();
    }

    @Override
    public void updateDataModel() {
        // This method can be used to update the data model if needed
    }

    @Override
    public boolean validate() {
        // Retrieve selected values
        String projectName = newProjectPanel.getProjectNameField().getText();
        String location = newProjectPanel.getLocationField().getText();
        String language = (String) newProjectPanel.getLanguageOptions().getSelectedItem();
        String languageVersion = (String) newProjectPanel.getLanguageVersionOptions().getSelectedItem();
        String framework = (String) newProjectPanel.getFrameworkOptions().getSelectedItem();
        String projectType = (String) newProjectPanel.getProjectTypeOptions().getSelectedItem();

        // Display selected values in the terminal
        System.out.println("Project Name: " + projectName);
        System.out.println("Location: " + location);
        System.out.println("Language: " + language);
        System.out.println("Language Version: " + languageVersion);
        System.out.println("Framework: " + framework);
        System.out.println("Project Type: " + projectType);

        // Perform validation if necessary
        // Return true to allow proceeding to the next step
        return true;
    }
}

