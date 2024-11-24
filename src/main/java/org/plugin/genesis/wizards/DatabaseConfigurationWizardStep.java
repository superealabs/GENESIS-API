package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
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
        return databaseConfigurationForm.getDatabaseConfigurationPanel();
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        return true;
    }
}
