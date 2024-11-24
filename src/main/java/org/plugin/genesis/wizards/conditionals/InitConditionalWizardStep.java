/**
 * MIT License
 * Copyright (c) 2024 nomena
 */

package org.plugin.genesis.wizards.conditionals;

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
            return new JLabel("Cette étape est ignorée en fonction de la configuration actuelle.");
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
        return framework != null && framework.getUseDB();
    }

}
