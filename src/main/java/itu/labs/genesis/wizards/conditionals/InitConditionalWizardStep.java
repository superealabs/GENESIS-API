/**
 * MIT License
 * Copyright (c) 2024 nomena
 */

package itu.labs.genesis.wizards.conditionals;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import genesis.config.langage.Framework;
import handler.ProjectGenerationContext;

import javax.swing.*;

public class InitConditionalWizardStep extends ModuleWizardStep {
    private final ProjectGenerationContext context;
    private final ModuleWizardStep actualStep;

    public InitConditionalWizardStep(ProjectGenerationContext context, ModuleWizardStep actualStep) {
        this.context = context;
        this.actualStep = actualStep;
    }

    @Override
    public JComponent getComponent() {
        if (isStepVisible()) {
            return actualStep.getComponent();
        } else {
            return new JLabel("This step is not visible");
        }
    }

    @Override
    public void updateDataModel() {
        if (isStepVisible()) {
            actualStep.updateDataModel();
        }
    }

    @Override
    public boolean isStepVisible() {
        Framework framework = context.getFramework();
        return framework != null
                && framework.getUseDB();
    }

}