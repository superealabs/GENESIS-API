package org.plugin.genesis.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ModifiableRootModel;
import org.jetbrains.annotations.NotNull;

public class GenesisApiModuleBuilder extends ModuleBuilder {
    private final static GenesisApiModuleType moduleType = new GenesisApiModuleType();

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel modifiableRootModel) {
    }

    @Override
    public ModuleType<?> getModuleType() {
        return moduleType;
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
