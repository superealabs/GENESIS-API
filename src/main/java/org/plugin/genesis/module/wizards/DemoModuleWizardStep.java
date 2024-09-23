package org.plugin.genesis.module.wizards;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import org.plugin.genesis.module.form.NewProject;

import javax.swing.*;

public class DemoModuleWizardStep extends ModuleWizardStep {
    private NewProject newProjectPanel;

    public DemoModuleWizardStep() {
        newProjectPanel = new NewProject();
    }

    @Override
    public JComponent getComponent() {
        return newProjectPanel;
    }

    @Override
    public void updateDataModel() {
        // Récupère les valeurs des champs de l'interface utilisateur et les met à jour dans le modèle
        String projectName = newProjectPanel.getProjectNameField().getText();
        String projectLanguage = (String) newProjectPanel.getProjectLanguageComboBox().getSelectedItem();
        boolean includeTests = newProjectPanel.getIncludeTestsCheckBox().isSelected();

        // Logique pour mettre à jour le modèle avec ces valeurs (exemple)
        System.out.println("Nom du projet: " + projectName);
        System.out.println("Langage: " + projectLanguage);
        System.out.println("Inclure les tests: " + includeTests);

        // Ici, vous pouvez implémenter la logique spécifique pour mettre à jour les données de votre modèle
        // Exemple : model.setProjectName(projectName);
        //           model.setProjectLanguage(projectLanguage);
        //           model.setIncludeTests(includeTests);
    }

}