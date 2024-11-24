package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import handler.ProjectGenerationContext;
import org.plugin.genesis.forms.SpecificConfigurationForm;

import javax.swing.*;

public class SpecificConfigurationWizardStep extends ModuleWizardStep {
    private final SpecificConfigurationForm specificConfigurationForm;
    private ProjectGenerationContext projectGenerationContext;


    public SpecificConfigurationWizardStep(ProjectGenerationContext projectGenerationContext) {
        specificConfigurationForm = new SpecificConfigurationForm();
        this.projectGenerationContext = projectGenerationContext;
    }

    @Override
    public JComponent getComponent() {
        return specificConfigurationForm.getMainPanel();
    }

    @Override
    public void updateDataModel() {
        // Logique pour mettre à jour le modèle avec les données finales
        System.out.println("Configuration finale du projet");
        // Exemple : model.setFinalConfiguration(...);
    }

    @Override
    public boolean validate() throws ConfigurationException {
        return true;
    }
}
