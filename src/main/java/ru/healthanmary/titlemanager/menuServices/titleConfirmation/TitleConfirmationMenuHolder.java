package ru.healthanmary.titlemanager.menuServices.titleConfirmation;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.menuServices.TitleMenuHolder;

public class TitleConfirmationMenuHolder implements InventoryHolder, TitleMenuHolder {

    @Getter
    private final String title;
    public TitleConfirmationMenuHolder(String title) {
        this.title = title;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
