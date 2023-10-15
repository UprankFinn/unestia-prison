package net.unestia.prison.database.player;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.unestia.api.UnestiaAPI;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Player {

    private final UUID uniqueId;
    private String displayName;

    private Integer tokens;
    private Integer minedBlocks;

    private String mine;
    private Integer prestige;
    private Integer rebirth;

    private List<String> allowedMines;

    public Player(UUID uniqueId, String displayName, Integer tokens, Integer minedBlocks, String mine, Integer prestige, Integer rebirth, List<String> allowedMines) {
        this.uniqueId = uniqueId;
        this.displayName = displayName;
        this.tokens = tokens;
        this.minedBlocks = minedBlocks;

        this.mine = mine;

        this.prestige = prestige;
        this.rebirth = rebirth;

        this.allowedMines = allowedMines;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("displayName", displayName));
        this.displayName = displayName;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setTokens(Integer tokens) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("tokens", tokens));
        this.tokens = tokens;
    }

    public void addTokens(Integer tokens) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("tokens", this.tokens + tokens));
        this.tokens += tokens;
    }

    public void removeTokens(Integer tokens) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("tokens", this.tokens - tokens));
        this.tokens -= tokens;
    }

    public Integer getMinedBlocks() {
        return minedBlocks;
    }

    public void addMinedBlock(Integer minedBlocks) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("minedBlocks", this.minedBlocks + minedBlocks));
        this.minedBlocks += minedBlocks;
    }

    public String getMine() {
        return mine;
    }

    public void setMine(String mine) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("mine", mine));
        this.mine = mine;
    }

    public Integer getPrestige() {
        return prestige;
    }

    public void setPrestige(Integer prestige) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("prestige", prestige));
        this.prestige = prestige;
    }

    public Integer getRebirth() {
        return rebirth;
    }

    public void setRebirth(Integer rebirth) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("rebirth", rebirth));
        this.rebirth = rebirth;
    }

    public boolean isPrestige() {
        return prestige != 0;
    }

    public boolean isRebirth() {
        return rebirth != 0;
    }

    public List<String> getAllowedMines() {
        return allowedMines;
    }

    public Map<String, UUID> getMines() {

        Map<String, UUID> mines = new HashMap<>();

        for (String mine : this.allowedMines) {
            mines.put(mine, this.uniqueId);
        }
        return mines;
    }

    public void addAllowedMine(String mine) {
        if (this.allowedMines.contains(mine)) return;
        this.allowedMines.add(mine);
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("allowedMines", allowedMines));
    }

    public void removeAllowedMine(String mine) {
        if (!(this.allowedMines.contains(mine))) return;
        this.allowedMines.remove(mine);
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players").updateOne(
                Filters.eq("uniqueId", uniqueId.toString()), Updates.set("allowedMines", allowedMines));
    }

    public org.bukkit.entity.Player toPlayer() {
        return Bukkit.getPlayer(this.uniqueId);
    }
}
