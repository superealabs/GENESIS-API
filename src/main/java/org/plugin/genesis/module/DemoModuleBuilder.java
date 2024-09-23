package org.plugin.genesis.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ModifiableRootModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.plugin.genesis.module.wizards.DemoModuleWizardStep;

public class DemoModuleBuilder extends ModuleBuilder {
    private final static DemoModuleType moduleType = new DemoModuleType();


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
        return new DemoModuleWizardStep();
    }

    @NotNull
    @Override
    public String getName() {
        return "SDK Module Type";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Example custom module type";
    }
}
