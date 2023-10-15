package net.unestia.prison.crates;

import net.unestia.prison.crates.type.CrateType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CrateManager {

    public Map<String, Crate> crates = new HashMap<>();

    public CrateManager() {

        this.addCrate(new Crate("MINE_KEY", "MINE_KEY", CrateType.MINE_KEY));
        this.addCrate(new Crate("VOTE_KEY", "VOTE_KEY", CrateType.VOTE_KEY));
        this.addCrate(new Crate("PRESTIGE_KEY", "PRESTIGE_KEY", CrateType.PRESTIGE_KEY));
        this.addCrate(new Crate("DRAGON_KEY", "DRAGON_KEY", CrateType.DRAGON_KEY));
        this.addCrate(new Crate("ZOMBIE_KEY", "ZOMBIE_KEY", CrateType.ZOMBIE_KEY));
        this.addCrate(new Crate("VAMPIRE_KEY", "VAMPIRE_KEY", CrateType.VAMPIRE_KEY));
        this.addCrate(new Crate("CURRENT_KEY", "CURRENT_KEY", CrateType.CURRENT_KEY));
        this.addCrate(new Crate("OP_KEY", "OP_KEY", CrateType.OP_KEY));
        this.addCrate(new Crate("GOLD_KEY", "GOLD_KEY", CrateType.GOLD_KEY));

        this.crates.forEach((s, crate) -> {
            Collections.shuffle(crate.getCrateType().getNormalWins());
            Collections.shuffle(crate.getCrateType().getEpicWins());
        });

    }

    public void addCrate(Crate crate) {
        this.crates.put(crate.getName(), crate);
    }

    public Crate getCrate(String crate) {
        if (this.crates.containsKey(crate)) return this.crates.get(crate);
        return null;
    }

    public Map<String, Crate> getCrates() {
        return this.crates;
    }
}
