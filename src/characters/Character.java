package characters;

public abstract class Character {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected boolean isDefending;
    protected Ability specialAbility;
    protected String characterClass;


    public Character(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.isDefending = false;
    }

    public int atacar() {
        int damage = attack + (int)(Math.random() * (attack / 4));
        return damage;
    }


    public int receberDano(int damage) {
        int defenseMultiplier = isDefending ? 2 : 1;
        int actualDamage = Math.max(1, damage - (defense * defenseMultiplier / 2));
        hp = Math.max(0, hp - actualDamage);
        isDefending = false;
        return actualDamage;
    }


    public abstract String usarHabilidade();

    public abstract String getClassIcon();


     //Ativa o modo de defesa do personagem, vou usar depois
    public void defender() {
        this.isDefending = true;
    }

    public void heal(int amount) {
        this.hp = Math.min(maxHp, hp + amount);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getHealthBar() {
        int barLength = 10;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isDefending() {
        return isDefending;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public Ability getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(Ability ability) {
        this.specialAbility = ability;
    }
}