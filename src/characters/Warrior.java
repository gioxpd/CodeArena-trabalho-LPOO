package characters;

import abilities.DoubleDamageAbility;

/**
 * Classe Warrior (Guerreiro) - Representa o herói Astyanax.
 *
 * Função: DPS físico equilibrado
 * Características:
 * - Vida alta
 * - Ataque forte
 * - Defesa média
 * - Habilidade especial: Golpe Crítico
 *
 * Demonstra: Herança (extends Character)
 */
public class Warrior extends Character {
    private boolean criticalStrikeActive;
    private static final int BASE_HP = 120;
    private static final int BASE_ATTACK = 25;
    private static final int BASE_DEFENSE = 15;
    private static final int BASE_SPEED = 10;


    public Warrior(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.characterClass = "Guerreiro";
        this.criticalStrikeActive = false;
        this.specialAbility = new DoubleDamageAbility();
    }

    @Override
    public int atacar() {
        int damage = super.atacar();
        if (criticalStrikeActive) {
            damage *= 2;
            criticalStrikeActive = false;
        }
        return damage;
    }

    @Override
    public String usarHabilidade() {
        criticalStrikeActive = true;
        return name + " concentra sua força! Próximo ataque causará DANO CRÍTICO! ⚔️💥";
    }

    @Override
    public String getClassIcon() {
        return "⚔️";
    }

    public boolean isCriticalStrikeActive() {
        return criticalStrikeActive;
    }
}