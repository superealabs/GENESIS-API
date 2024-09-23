package org.plugin.genesis.module.form;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;


public class NewProject extends JPanel {
    private JTextField projectNameField;
    private JComboBox<String> projectLanguageComboBox;
    private JCheckBox includeTestsCheckBox;

    public NewProject() {
        JPanel panel = new JPanel(new GridLayoutManager(3, 3));  // Ajuste le nombre de lignes et colonnes

        // Nom du projet
        JLabel projectNameLabel = new JLabel("Nom du projet :");
        projectNameField = new JTextField();
        panel.add(projectNameLabel, new GridConstraints(0, 0, 1, 1,  // Row, Column, RowSpan, ColSpan
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(projectNameField, new GridConstraints(0, 1, 1, 2,  // Ajouté sur 2 colonnes pour donner de l'espace
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

        // Langage du projet
        JLabel projectLanguageLabel = new JLabel("Langage :");
        String[] languages = {"Java", "Kotlin", "Scala"};
        projectLanguageComboBox = new ComboBox<>(languages);
        panel.add(projectLanguageLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(projectLanguageComboBox, new GridConstraints(1, 1, 1, 2,  // Même principe, 2 colonnes pour plus d'espace
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

        // Inclure les tests
        JLabel includeTestsLabel = new JLabel("Inclure les tests ?");
        includeTestsCheckBox = new JCheckBox();
        panel.add(includeTestsLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(includeTestsCheckBox, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

        // Ajout du panneau principal
        this.add(panel);
    }

    public JTextField getProjectNameField() {
        return projectNameField;
    }

    public NewProject setProjectNameField(JTextField projectNameField) {
        this.projectNameField = projectNameField;
        return this;
    }

    public JComboBox<String> getProjectLanguageComboBox() {
        return projectLanguageComboBox;
    }

    public NewProject setProjectLanguageComboBox(JComboBox<String> projectLanguageComboBox) {
        this.projectLanguageComboBox = projectLanguageComboBox;
        return this;
    }

    public JCheckBox getIncludeTestsCheckBox() {
        return includeTestsCheckBox;
    }

    public NewProject setIncludeTestsCheckBox(JCheckBox includeTestsCheckBox) {
        this.includeTestsCheckBox = includeTestsCheckBox;
        return this;
    }
}
