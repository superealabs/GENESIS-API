package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import handler.ProjectGenerationContext;
import org.plugin.genesis.forms.GenerationOptionForm;

import javax.swing.*;

public class GenerationOptionWizardStep extends ModuleWizardStep  {
    private final GenerationOptionForm generationOptionForm;
    private ProjectGenerationContext projectGenerationContext;

    public GenerationOptionWizardStep(ProjectGenerationContext projectGenerationContext) {
        generationOptionForm = new GenerationOptionForm();
        this.projectGenerationContext = projectGenerationContext;
    }


    @Override
    public JComponent getComponent() {
        return generationOptionForm.getMainPanel();
    }

    @Override
    public void updateDataModel() {

    }
}
