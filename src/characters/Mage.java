package characters;

/**
 * Classe Mage (Mago) - Representa o herói Fynrall.
 * Função: Dano mágico
 * Características:
 * - Alto ataque mágico
 * - Pouca vida
 * Demonstra: Herança com mecânicas especializadas
 */
public class Mage extends Character {
    private int mana;
    private int maxMana;
    private boolean arcaneExplosionReady;
    private static final int BASE_HP = 60;
    private static final int BASE_ATTACK = 35;
    private static final int BASE_DEFENSE = 5;
    private static final int BASE_SPEED = 15;
    private static final int BASE_MANA = 100;
    private static final int EXPLOSION_COST = 40;
    private static final double EXPLOSION_MULTIPLIER = 2.5;


    public Mage(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.characterClass = "Mago";
        this.maxMana = BASE_MANA;
        this.mana = maxMana;
        this.arcaneExplosionReady = false;
    }

    @Override
    public int atacar() {
        int damage = super.atacar();
        if (arcaneExplosionReady && mana >= EXPLOSION_COST) {
            damage = (int)(damage * EXPLOSION_MULTIPLIER);
            mana -= EXPLOSION_COST;
            arcaneExplosionReady = false;
        }
        return damage;
    }

    @Override
    public String usarHabilidade() {
        if (mana >= EXPLOSION_COST) {
            arcaneExplosionReady = true;
            return name + " canaliza energia arcana! 🔮⚡ Próximo ataque causará EXPLOSÃO ARCANA!";
        } else {
            return name + " não tem mana suficiente! (Mana: " + mana + "/" + maxMana + ")";
        }
    }

    public void onCorrectAnswer(int difficulty) {
        int manaRecovery = 10 * difficulty;
        mana = Math.min(maxMana, mana + manaRecovery);
    }

    @Override
    public String getClassIcon() {
        return "🔮";
    }

    @Override
    public String getStatus() {
        return String.format("%s %s - %s | Mana: %d/%d",
                getClassIcon(), name, getHealthBar(), mana, maxMana);
    }

    // Getters e Setters específicos

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(maxMana, mana));
    }

    public int getMaxMana() {
        return maxMana;
    }

    public boolean isArcaneExplosionReady() {
        return arcaneExplosionReady;
    }

    public void restoreMana(int amount) {
        this.mana = Math.min(maxMana, mana + amount);
    }
}