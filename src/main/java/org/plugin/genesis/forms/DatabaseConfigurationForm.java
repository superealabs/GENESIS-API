/**
 * MIT License
 * Copyright (c) 2024 nomena
 */

package org.plugin.genesis.forms;

import genesis.connexion.Database;
import lombok.Getter;

import javax.swing.*;

@Getter
public class DatabaseConfigurationForm {
    private JPanel mainPanel;
    private JLabel dmsLabel;
    private JComboBox<Database> dmsOptions;
    private JLabel databaseLabel;
    private JTextField databaseField;
    private JLabel hostLabel;
    private JLabel usernameLabel;
    private JTextField hostField;
    private JTextField usernameField;
    private JTextField portField;
    private JLabel portLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JTextField schemaField;
    private JCheckBox trustCertificateCheckBox;
    private JLabel schemaLabel;
    private JCheckBox useSSLCheckBox;
    private JCheckBox allowKeyRetrievalCheckBox;
    private JLabel driverTypeLabel;
    private JTextField driverTypeField;
    private JLabel URLLabel;
    private JTextField textField1;
    private JTextField sidField;
    private JLabel SIDLabel;
}
