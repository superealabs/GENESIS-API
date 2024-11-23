package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;

import javax.swing.*;

public class FinalModuleWizardStep extends ModuleWizardStep {
    private JPanel panel;

    public FinalModuleWizardStep() {
        panel = new JPanel();
        panel.add(new JLabel("Configuration finale du projet"));
        // Ajoutez ici les composants nécessaires pour la configuration finale
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public void updateDataModel() {
        // Logique pour mettre à jour le modèle avec les données finales
        System.out.println("Configuration finale du projet");
        // Exemple : model.setFinalConfiguration(...);
    }
}
