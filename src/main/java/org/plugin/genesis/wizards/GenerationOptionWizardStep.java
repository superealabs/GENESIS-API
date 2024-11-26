package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import handler.ProjectGenerationContext;
import org.plugin.genesis.forms.GenerationOptionForm;

import javax.swing.*;
import java.util.List;

public class GenerationOptionWizardStep extends ModuleWizardStep {
    private final GenerationOptionForm generationOptionForm;
    private final ProjectGenerationContext projectGenerationContext;

    public GenerationOptionWizardStep(ProjectGenerationContext projectGenerationContext) {
        this.projectGenerationContext = projectGenerationContext;
        this.generationOptionForm = new GenerationOptionForm(projectGenerationContext);
    }

    @Override
    public JComponent getComponent() {
        return generationOptionForm.getMainPanel();
    }

    @Override
    public void updateDataModel() {
        // Get selected table names
        List<String> selectedTables = generationOptionForm.getTableNamesList().getSelectedValuesList();
        projectGenerationContext.setEntityNames(selectedTables);

        // Get selected component
        List<String> selectedComponent = generationOptionForm.getComponentChoice().getSelectedValuesList();
        if (selectedComponent != null) {
            projectGenerationContext.setGenerationOptions(selectedComponent);
        }

    }

    @Override
    public boolean validate() throws ConfigurationException {
        if (generationOptionForm.getTableNamesList().getSelectedValuesList().isEmpty()) {
            throw new ConfigurationException("Please select at least one table.");
        }
        if (generationOptionForm.getComponentChoice().getSelectedValue() == null) {
            throw new ConfigurationException("Please select a component to generate.");
        }
        return true;
    }
}
