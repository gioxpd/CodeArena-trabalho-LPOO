package characters;

/**
 * Classe Archer (Arqueiro) - Representa o herói MudMug, um Goblin Arqueiro.
 *
 * Função: Velocidade e precisão
 * Características:
 * - Ataques rápidos
 * - Chance de esquiva
 * - Crítico elevado
 *
 * Demonstra: Herança com mecânicas de chance/probabilidade
 */
public class Archer extends Character {
    private double criticalChance;
    private double dodgeChance;
    private boolean precisionShotReady;
    private int comboCount;
    private static final int BASE_HP = 75;
    private static final int BASE_ATTACK = 22;
    private static final int BASE_DEFENSE = 10;
    private static final int BASE_SPEED = 20;
    private static final double BASE_CRIT_CHANCE = 0.25;
    private static final double BASE_DODGE_CHANCE = 0.20;
    private static final double CRIT_MULTIPLIER = 1.8;


    public Archer(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.characterClass = "Arqueiro";
        this.criticalChance = BASE_CRIT_CHANCE;
        this.dodgeChance = BASE_DODGE_CHANCE;
        this.precisionShotReady = false;
        this.comboCount = 0;
    }

    @Override
    public int atacar() {
        int damage = super.atacar();

        // Flecha Certeira garante crítico
        if (precisionShotReady) {
            damage = (int)(damage * CRIT_MULTIPLIER);
            precisionShotReady = false;
            comboCount++;
            return damage;
        }

        // Chance normal de crítico
        if (Math.random() < criticalChance + (comboCount * 0.05)) {
            damage = (int)(damage * CRIT_MULTIPLIER);
            comboCount++;
        } else {
            comboCount = 0; // Reset combo se não critar
        }

        return damage;
    }

    @Override
    public int receberDano(int damage) {
        // Verifica esquiva
        if (Math.random() < dodgeChance) {
            return 0; // Esquivou!
        }
        return super.receberDano(damage);
    }

     //Flecha Certeira: próximo ataque tem crítico garantido
    @Override
    public String usarHabilidade() {
        precisionShotReady = true;
        return name + " mira cuidadosamente... 🏹✨ Próximo ataque será uma FLECHA CERTEIRA!";
    }

    @Override
    public String getClassIcon() {
        return "🏹";
    }

    @Override
    public String getStatus() {
        String combo = comboCount > 0 ? " | Combo: x" + comboCount : "";
        return String.format("%s %s - %s%s",
                getClassIcon(), name, getHealthBar(), combo);
    }

    // Getters e Setters específicos

    public double getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(double criticalChance) {
        this.criticalChance = Math.max(0, Math.min(1, criticalChance));
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(double dodgeChance) {
        this.dodgeChance = Math.max(0, Math.min(0.5, dodgeChance));
    }

    public int getComboCount() {
        return comboCount;
    }

    public boolean isPrecisionShotReady() {
        return precisionShotReady;
    }
}