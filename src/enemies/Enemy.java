package enemies;

import questions.Question.Category;


public class Enemy {
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private Category associatedCategory;
    private String description;
    private String icon;
    private int stage;

    public Enemy(String name, int maxHp, int attack, int defense,
                 Category category, String icon) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.associatedCategory = category;
        this.icon = icon;
        this.stage = 1;
    }

    public int atacar() {
        int damage = attack + (int)(Math.random() * (attack / 3));
        return damage;
    }

    public int receberDano(int damage) {
        int actualDamage = Math.max(1, damage - defense / 2);
        hp = Math.max(0, hp - actualDamage);
        return actualDamage;
    }


    public boolean isAlive() {
        return hp > 0;
    }

    public String getHealthBar() {
        int barLength = 20;
        int filledBars = (int) ((double) hp / maxHp * barLength);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filledBars) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ").append(hp).append("/").append(maxHp);
        return bar.toString();
    }

    public String getStatus() {
        return String.format("%s %s\n   HP: %s", icon, name, getHealthBar());
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("╔════════════════════════════════════╗\n");
        sb.append("║           ").append(icon).append(" ").append(name);
        sb.append(padSpaces(24 - name.length())).append("║\n");
        sb.append("╠════════════════════════════════════╣\n");
        sb.append("║  ").append(getHealthBar()).append("   ║\n");
        sb.append("║  ATK: ").append(padRight(String.valueOf(attack), 8));
        sb.append(" DEF: ").append(padRight(String.valueOf(defense), 8)).append("║\n");
        if (description != null && !description.isEmpty()) {
            sb.append("║  ").append(padRight(description, 32)).append("║\n");
        }
        sb.append("╚════════════════════════════════════╝\n");
        return sb.toString();
    }

    private String padSpaces(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.max(0, count); i++) {
            sb.append(" ");
        }
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(maxHp, hp));
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Category getAssociatedCategory() {
        return associatedCategory;
    }

    public void setAssociatedCategory(Category category) {
        this.associatedCategory = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}