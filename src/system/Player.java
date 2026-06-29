package system;

import characters.Character;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa o jogador e seu grupo de heróis.
 */
public class Player {
    private String name;
    private List<Character> heroes;
    private int totalScore;
    private int battlesWon;
    private int battlesLost;

    public Player(String name, List<Character> heroes) {
        this.name = name;
        this.heroes = new ArrayList<>(heroes);
        this.totalScore = 0;
        this.battlesWon = 0;
        this.battlesLost = 0;
    }

    public List<Character> getAliveHeroes() {
        return heroes.stream()
                .filter(Character::isAlive)
                .collect(Collectors.toList());
    }

    public int getAliveHeroesCount() {
        return (int) heroes.stream().filter(Character::isAlive).count();
    }

    public boolean canFight() {
        return getAliveHeroesCount() > 0;
    }

    public void addScore(int points) {
        this.totalScore += points;
    }

    public void registerVictory() {
        this.battlesWon++;
    }

    public void registerDefeat() {
        this.battlesLost++;
    }

    public int getTotalPartyHp() {
        return heroes.stream()
                .mapToInt(Character::getHp)
                .sum();
    }

    public int getTotalPartyMaxHp() {
        return heroes.stream()
                .mapToInt(Character::getMaxHp)
                .sum();
    }

    /**
     * Exibe o status completo do grupo.
     * @return String formatada com status de todos os heróis
     */
    public String getPartyStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════╗\n");
        sb.append("║    GRUPO DE ").append(padRight(name.toUpperCase(), 25)).append("║\n");
        sb.append("╠═══════════════════════════════════════╣\n");

        for (Character hero : heroes) {
            String status;
            if (hero.isAlive()) {
                status = hero.getStatus();
            } else {
                status = "☠️ " + hero.getName() + " (Derrotado)";
            }
            sb.append("║  ").append(padRight(status, 36)).append("║\n");
        }

        sb.append("╠═══════════════════════════════════════╣\n");
        sb.append("║  HP Total: ").append(padRight(getTotalPartyHp() + "/" + getTotalPartyMaxHp(), 26)).append("║\n");
        sb.append("║  Heróis vivos: ").append(padRight(getAliveHeroesCount() + "/" + heroes.size(), 22)).append("║\n");
        sb.append("╚═══════════════════════════════════════╝");

        return sb.toString();
    }

    private String padRight(String text, int length) {
        if (text.length() >= length) {
            return text.substring(0, length);
        }
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < length) {
            sb.append(" ");
        }
        return sb.toString();
    }

    // Getters e Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Character> getHeroes() {
        return new ArrayList<>(heroes);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getBattlesWon() {
        return battlesWon;
    }

    public int getBattlesLost() {
        return battlesLost;
    }

    public double getWinRate() {
        int totalBattles = battlesWon + battlesLost;
        if (totalBattles == 0) return 0;
        return (double) battlesWon / totalBattles * 100;
    }
}