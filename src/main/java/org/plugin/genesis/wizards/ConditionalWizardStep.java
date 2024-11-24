/**
 * MIT License
 * Copyright (c) 2024 nomena
 */

package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import handler.ProjectGenerationContext;

import javax.swing.*;

public class ConditionalWizardStep extends ModuleWizardStep {
    private final ProjectGenerationContext context;
    private final ModuleWizardStep actualStep;

    public ConditionalWizardStep(ProjectGenerationContext context, ModuleWizardStep actualStep) {
        this.context = context;
        this.actualStep = actualStep;
    }

    @Override
    public JComponent getComponent() {
        if (context.getFramework().getUseDB()) {
            return actualStep.getComponent();
        } else {
            return new JLabel("This step is ignored given the actual configuration.");
        }
    }

    @Override
    public void updateDataModel() {
        if (context.getFramework().getUseDB()) {
            actualStep.updateDataModel();
        }
    }

    @Override
    public boolean isStepVisible() {
        return context.getFramework().getUseDB();
    }
}
