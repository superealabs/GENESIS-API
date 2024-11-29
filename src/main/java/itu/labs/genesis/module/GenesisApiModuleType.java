package itu.labs.genesis.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;
import itu.labs.genesis.icon.SdkIcons;
import itu.labs.genesis.wizards.DatabaseConfigurationWizardStep;
import itu.labs.genesis.wizards.GenerationOptionWizardStep;
import itu.labs.genesis.wizards.InitializationWizardStep;
import itu.labs.genesis.wizards.SpecificConfigurationWizardStep;
import itu.labs.genesis.wizards.conditionals.GenConfigConditionalWizardStep;
import itu.labs.genesis.wizards.conditionals.InitConditionalWizardStep;

import javax.swing.*;

import static itu.labs.genesis.module.GenesisApiModuleBuilder.projectGenerationContext;

final class GenesisApiModuleType extends ModuleType<GenesisApiModuleBuilder> {
    private static final String ID = "GENESIS_API_MODULE_TYPE";

    GenesisApiModuleType() {
        super(ID);
    }

    @Override
    public ModuleWizardStep @NotNull [] createWizardSteps(@NotNull WizardContext wizardContext,
                                                          @NotNull GenesisApiModuleBuilder moduleBuilder,
                                                          @NotNull ModulesProvider modulesProvider) {

        SpecificConfigurationWizardStep specificConfigurationWizardStep = new SpecificConfigurationWizardStep(projectGenerationContext);
        DatabaseConfigurationWizardStep databaseConfigurationWizardStep = new DatabaseConfigurationWizardStep(projectGenerationContext);
        InitConditionalWizardStep initConditionalWizardStep = new InitConditionalWizardStep(projectGenerationContext, databaseConfigurationWizardStep);
        GenerationOptionWizardStep generationOptionWizardStep = new GenerationOptionWizardStep(projectGenerationContext);
        GenConfigConditionalWizardStep genConfigConditionalWizardStep = new GenConfigConditionalWizardStep(projectGenerationContext, generationOptionWizardStep);

        return new ModuleWizardStep[]{
                new InitializationWizardStep(projectGenerationContext, specificConfigurationWizardStep),
                initConditionalWizardStep,
                genConfigConditionalWizardStep,
                specificConfigurationWizardStep
        };
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


}

