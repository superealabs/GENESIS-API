package org.plugin.genesis.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import handler.ProjectGenerationContext;

import javax.swing.*;

public class FinalModuleWizardStep extends ModuleWizardStep {
    private final JPanel panel;
    private final ProjectGenerationContext projectGenerationContext;


    public FinalModuleWizardStep(ProjectGenerationContext projectGenerationContext) {
        panel = new JPanel();
        panel.add(new JLabel("Configuration finale du projet"));
        // Ajoutez ici les composants nécessaires pour la configuration finale
        this.projectGenerationContext = projectGenerationContext;
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
