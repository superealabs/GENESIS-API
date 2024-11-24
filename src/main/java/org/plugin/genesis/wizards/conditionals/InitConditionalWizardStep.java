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
        System.out.println("Actual component: " + actualStep.getClass().getName() + ", isVisible : " + isStepVisible()+"\n\n");
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
        boolean isVisible = framework != null && framework.getUseDB();
        System.out.println("\n\nThe step : "+actualStep.getClass().getName() + ", isVisible : " + isVisible);
        return isVisible;
    }

}
