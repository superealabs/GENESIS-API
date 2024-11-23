package org.plugin.genesis.module.form;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import genesis.config.langage.ConfigurationMetadata;
import genesis.config.langage.Framework;
import genesis.config.langage.Language;
import genesis.config.langage.Project;
import genesis.config.langage.generator.project.ProjectGenerator;
import lombok.Getter;

import javax.swing.*;
import java.util.List;

@Getter
public class InitialisationForm {
    private JPanel initializationPanel;
    private JTextField projectNameField;
    private TextFieldWithBrowseButton locationField; // Updated to TextFieldWithBrowseButton
    private JComboBox<Language> languageOptions;
    private JComboBox<String> languageVersionOptions;
    private JComboBox<String> frameworkOptions;
    private JComboBox<Project> buildToolOptions;
    private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel languageLabel;
    private JLabel languageVersionLabel;
    private JLabel frameworkLabel;
    private JLabel buildToolLabel;
    private JComboBox<Framework> projectTypeOptions;
    private JLabel projectType;

    public InitialisationForm() {

        // Initialize the locationField with a folder chooser
        FileChooserDescriptor folderChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        folderChooserDescriptor.setTitle("Select Folder");
        folderChooserDescriptor.setDescription("Choose a directory for the project location");

        TextBrowseFolderListener folderListener = new TextBrowseFolderListener(folderChooserDescriptor) {
            @Override
            protected void onFileChosen(VirtualFile chosenFile) {
                locationField.setText(chosenFile.getPath());
            }
        };

        locationField.addBrowseFolderListener(folderListener);
        populateLanguageOptions();

        languageOptions.addActionListener(e -> {
            Language selectedLanguage = (Language) languageOptions.getSelectedItem();
            if (selectedLanguage != null) {
                populateLanguageVersionOptions(selectedLanguage);
                populateFrameworkOptions(selectedLanguage);
            }
        });

        frameworkOptions.addActionListener(e -> {
            String selectedBaseFramework = (String) frameworkOptions.getSelectedItem();
            if (selectedBaseFramework != null) {
                populateBuildToolOptions(selectedBaseFramework);
                populateProjectTypeOptions(selectedBaseFramework);
            }
        });
    }


    private void populateLanguageOptions() {
        List<Language> languages = ProjectGenerator.languages.values()
                .stream()
                .toList();

        for (Language language : languages) {
            languageOptions.addItem(language);
        }

        // Set the first item as selected by default
        if (languageVersionOptions.getItemCount() > 0) {
            languageVersionOptions.setSelectedIndex(0);
        }
    }

    private void populateFrameworkOptions(Language language) {
        frameworkOptions.removeAllItems();
        List<String> listBaseFramework = ProjectGenerator.frameworks.values().stream()
                .filter(framework -> framework.getLanguageId() == language.getId())
                .map(Framework::getBaseFramework)
                .distinct()
                .toList();

        for (String framework : listBaseFramework) {
            frameworkOptions.addItem(framework);
        }
        // Set the first item as selected by default
        if (frameworkOptions.getItemCount() > 0) {
            frameworkOptions.setSelectedIndex(0);
        }
    }


    private void populateProjectTypeOptions(String baseFramework) {
        projectTypeOptions.removeAllItems();
        List<Framework> listFramework = ProjectGenerator.frameworks.values().stream()
                .filter(framework -> framework.getBaseFramework().equalsIgnoreCase(baseFramework))
                .distinct()
                .toList();

        for (Framework framework : listFramework) {
            projectTypeOptions.addItem(framework);
        }

        // Set the first item as selected by default
        if (projectTypeOptions.getItemCount() > 0) {
            projectTypeOptions.setSelectedIndex(0);
        }
    }

    private void populateBuildToolOptions(String framework) {
        buildToolOptions.removeAllItems();
        for (Project project : ProjectGenerator.projects.values()) {
            if (project.getBaseFrameworks().contains(framework)) {
                buildToolOptions.addItem(project);
            }
        }
        // Set the first item as selected by default
        if (buildToolOptions.getItemCount() > 0) {
            buildToolOptions.setSelectedIndex(0);
        }
    }

    private void populateLanguageVersionOptions(Language language) {
        languageVersionOptions.removeAllItems();
        for (ConfigurationMetadata config : language.getConfigurations()) {
            if ("languageVersion".equals(config.getVariableName())) {
                for (String version : config.getOptions()) {
                    languageVersionOptions.addItem(version);
                }
                break;
            }
        }

        // Vérifier si des éléments ont été ajoutés
        boolean hasItems = languageVersionOptions.getItemCount() > 0;

        if (hasItems) {
            // Activer les composants et sélectionner le premier élément
            languageVersionLabel.setEnabled(true);
            languageVersionOptions.setEnabled(true);
            languageVersionOptions.setSelectedIndex(0);
        } else {
            // Désactiver les composants et afficher "Not applicable"
            languageVersionLabel.setEnabled(false);
            languageVersionOptions.setEnabled(false);
            languageVersionOptions.addItem("Not applicable");
            languageVersionOptions.setSelectedIndex(0);
        }
    }
}
