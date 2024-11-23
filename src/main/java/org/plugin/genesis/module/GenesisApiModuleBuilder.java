package org.plugin.genesis.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ModifiableRootModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.plugin.genesis.wizards.InitializationWizardStep;

public class GenesisApiModuleBuilder extends ModuleBuilder {
    private final static GenesisApiModuleType moduleType = new GenesisApiModuleType();

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel modifiableRootModel) {
    }

    @Override
    public ModuleType<?> getModuleType() {
        return moduleType;
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new InitializationWizardStep();
    }

    @NotNull
    @Override
    public String getName() {
        return "GENESIS-API";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "A polyglot code generator";
    }
}
