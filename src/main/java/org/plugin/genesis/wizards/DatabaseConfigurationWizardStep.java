package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import handler.ProjectGenerationContext;

import javax.swing.*;

public class DatabaseConfigurationWizardStep extends ModuleWizardStep {
    private final ProjectGenerationContext projectGenerationContext;

    public DatabaseConfigurationWizardStep(ProjectGenerationContext projectGenerationContext) {
        //newProjectPanel = new InitializationForm();
        this.projectGenerationContext = projectGenerationContext;
    }

    @Override
    public JComponent getComponent() {
        return null;
    }

    @Override
    public void updateDataModel() {

    }
}
