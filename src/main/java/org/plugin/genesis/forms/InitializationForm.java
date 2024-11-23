
package org.plugin.genesis.forms;

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
public class InitializationForm {
    private JPanel initializationPanel;
    private JTextField projectNameField;
    private TextFieldWithBrowseButton locationField;
    private JComboBox<Language> languageOptions;
    private JComboBox<String> languageVersionOptions;
    private JComboBox<String> frameworkOptions;
    private JComboBox<Project> buildToolOptions;
    private JLabel languageVersionLabel;
    private JComboBox<Framework> projectTypeOptions;
    private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel languageLabel;
    private JLabel frameworkLabel;
    private JLabel buildToolLabel;
    private JLabel projectType;

    public InitializationForm() {
        // Initialize the location field with a folder chooser
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

        // Populate initial options and set default selections
        populateLanguageOptions();
        initializeDefaultSelections();

        // Add listeners for dependent comboboxes
        addListeners();
    }

    private void addListeners() {
        languageOptions.addActionListener(e -> {
            Language selectedLanguage = (Language) languageOptions.getSelectedItem();
            if (selectedLanguage != null) {
                populateLanguageVersionOptions(selectedLanguage);
                populateFrameworkOptions(selectedLanguage);
            }
        });

        frameworkOptions.addActionListener(e -> {
            String selectedFramework = (String) frameworkOptions.getSelectedItem();
            if (selectedFramework != null) {
                populateBuildToolOptions(selectedFramework);
                populateProjectTypeOptions(selectedFramework);
            }
        });
    }

    private void initializeDefaultSelections() {
        if (languageOptions.getItemCount() > 0) {
            languageOptions.setSelectedIndex(0);
            Language defaultLanguage = (Language) languageOptions.getSelectedItem();
            if (defaultLanguage != null) {
                populateLanguageVersionOptions(defaultLanguage);
                populateFrameworkOptions(defaultLanguage);
            }
        }

        if (frameworkOptions.getItemCount() > 0) {
            frameworkOptions.setSelectedIndex(0);
            String defaultFramework = (String) frameworkOptions.getSelectedItem();
            if (defaultFramework != null) {
                populateBuildToolOptions(defaultFramework);
                populateProjectTypeOptions(defaultFramework);
            }
        }
    }

    private void populateLanguageOptions() {
        List<Language> languages = ProjectGenerator.languages.values().stream().toList();
        for (Language language : languages) {
            languageOptions.addItem(language);
        }
    }

    private void populateFrameworkOptions(Language language) {
        frameworkOptions.removeAllItems();
        List<String> frameworks = ProjectGenerator.frameworks.values().stream()
                .filter(f -> f.getLanguageId() == language.getId())
                .map(Framework::getBaseFramework)
                .distinct()
                .toList();

        for (String framework : frameworks) {
            frameworkOptions.addItem(framework);
        }

        if (frameworkOptions.getItemCount() > 0) {
            frameworkOptions.setSelectedIndex(0);
        }
    }

    private void populateProjectTypeOptions(String baseFramework) {
        projectTypeOptions.removeAllItems();
        List<Framework> frameworks = ProjectGenerator.frameworks.values().stream()
                .filter(f -> f.getBaseFramework().equalsIgnoreCase(baseFramework))
                .distinct()
                .toList();

        for (Framework framework : frameworks) {
            projectTypeOptions.addItem(framework);
        }

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

        boolean hasItems = languageVersionOptions.getItemCount() > 0;

        if (hasItems) {
            languageVersionLabel.setEnabled(true);
            languageVersionOptions.setEnabled(true);
            languageVersionOptions.setSelectedIndex(0);
        } else {
            languageVersionLabel.setEnabled(false);
            languageVersionOptions.setEnabled(false);
            languageVersionOptions.addItem("Not applicable");
            languageVersionOptions.setSelectedIndex(0);
        }
    }
}