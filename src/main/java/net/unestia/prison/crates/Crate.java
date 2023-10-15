package net.unestia.prison.crates;

import net.unestia.prison.crates.type.CrateType;

public class Crate {

    private final String name;
    private final String localizedName;
    private final CrateType crateType;

    public Crate(String name, String localizedName, CrateType crateType) {
        this.name = name;
        this.localizedName = localizedName;
        this.crateType = crateType;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public CrateType getCrateType() {
        return this.crateType;
    }
}
