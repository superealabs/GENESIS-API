package org.plugin.genesis.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;
import org.plugin.genesis.icon.SdkIcons;
import org.plugin.genesis.wizards.FinalModuleWizardStep;

import javax.swing.*;

final class GenesisApiModuleType extends ModuleType<GenesisApiModuleBuilder> {
    private static final String ID = "DEMO_MODULE_TYPE";

    GenesisApiModuleType() {
        super(ID);
    }

    @NotNull
    @Override
    public GenesisApiModuleBuilder createModuleBuilder() {
        return new GenesisApiModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "GENESIS-API";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Let's build microservices.";
    }

    @Override
    public @NotNull Icon getNodeIcon(boolean isOpened) {
        return SdkIcons.Sdk_default_icon;
    }

    @Override
    public ModuleWizardStep @NotNull [] createWizardSteps(@NotNull WizardContext wizardContext,
                                                          @NotNull GenesisApiModuleBuilder moduleBuilder,
                                                          @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{
                new FinalModuleWizardStep()
        };
    }
}
