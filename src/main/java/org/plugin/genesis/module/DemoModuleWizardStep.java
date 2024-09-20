package org.plugin.genesis.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;

import javax.swing.*;

public class DemoModuleWizardStep extends ModuleWizardStep {

    @Override
    public JComponent getComponent() {
        return new JLabel("\tProvide some setting here");
    }

    @Override
    public void updateDataModel() {
        //todo update model according to UI
    }

}