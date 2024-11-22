package org.plugin.genesis.module.form;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;

public class InitialisationForm {
    private JPanel initializationPanel;
    private JTextField projectNameField;
    private TextFieldWithBrowseButton locationField; // Updated to TextFieldWithBrowseButton
    private JComboBox<String> languageOptions;
    private JComboBox<String> languageVersionOptions;
    private JComboBox<String> frameworkOptions;
    private JComboBox<String> projectTypeOptions;
    private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel languageLabel;
    private JLabel languageVersionLabel;
    private JLabel frameworkLabel;
    private JLabel projectTypeLabel;

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

        // Populate combo boxes with options
        populateLanguageOptions();
        populateLanguageVersionOptions();
        populateFrameworkOptions();
        populateProjectTypeOptions();
    }


    private void populateLanguageOptions() {
        String[] languages = {"Java", "C#"};
        for (String language : languages) {
            languageOptions.addItem(language);
        }
    }

    private void populateLanguageVersionOptions() {
        String[] versions = {"17", "21", "23"};
        for (String version : versions) {
            languageVersionOptions.addItem(version);
        }
    }

    private void populateFrameworkOptions() {
        String[] frameworks = {"Spring", ".NET"};
        for (String framework : frameworks) {
            frameworkOptions.addItem(framework);
        }
    }

    private void populateProjectTypeOptions() {
        String[] projectTypes = {"Web API", "Application", "Microservice"};
        for (String projectType : projectTypes) {
            projectTypeOptions.addItem(projectType);
        }
    }

    public JPanel getInitializationPanel() {
        return initializationPanel;
    }

    public JTextField getProjectNameField() {
        return projectNameField;
    }

    public TextFieldWithBrowseButton getLocationField() {
        return locationField;
    }

    public JComboBox<String> getLanguageOptions() {
        return languageOptions;
    }

    public JComboBox<String> getLanguageVersionOptions() {
        return languageVersionOptions;
    }

    public JComboBox<String> getFrameworkOptions() {
        return frameworkOptions;
    }

    public JComboBox<String> getProjectTypeOptions() {
        return projectTypeOptions;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getLocationLabel() {
        return locationLabel;
    }

    public JLabel getLanguageLabel() {
        return languageLabel;
    }

    public JLabel getLanguageVersionLabel() {
        return languageVersionLabel;
    }

    public JLabel getFrameworkLabel() {
        return frameworkLabel;
    }

    public JLabel getProjectTypeLabel() {
        return projectTypeLabel;
    }
}
