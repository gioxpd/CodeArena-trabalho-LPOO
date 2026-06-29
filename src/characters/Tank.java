package characters;

import abilities.ShieldAbility;
import java.util.List;

/**
 * Classe Tank (Tanque) - Representa o herói Edrius, um Golem.
 * Função: Defesa
 * Características:
 * - Defesa extremamente alta
 * - Absorve dano
 * - Protege aliados
 * Demonstra: Herança com mecânicas de proteção de grupo
 */
public class Tank extends Character {
    private boolean stoneWallActive;
    private int stoneWallTurns;
    private List<Character> protectedAllies;
    private static final int BASE_HP = 150;
    private static final int BASE_ATTACK = 15;
    private static final int BASE_DEFENSE = 30;
    private static final int BASE_SPEED = 5;
    private static final int STONE_WALL_DURATION = 2;
    private static final double DAMAGE_ABSORPTION = 0.5;


    public Tank(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.characterClass = "Tank";
        this.stoneWallActive = false;
        this.stoneWallTurns = 0;
        this.specialAbility = new ShieldAbility(STONE_WALL_DURATION);
    }

    public void setProtectedAllies(List<Character> allies) {
        this.protectedAllies = allies;
    }

    @Override
    public int receberDano(int damage) {
        if (stoneWallActive) {
            damage = (int)(damage * (1 - DAMAGE_ABSORPTION));
        }
        return super.receberDano(damage);
    }

    public int protectAlly(Character ally, int incomingDamage) {
        if (stoneWallActive && this.isAlive()) {
            int absorbedDamage = (int)(incomingDamage * DAMAGE_ABSORPTION);
            int remainingDamage = incomingDamage - absorbedDamage;

            // Tank absorve parte do dano
            this.receberDano(absorbedDamage / 2);

            return remainingDamage;
        }
        return incomingDamage;
    }


    @Override
    public String usarHabilidade() {
        stoneWallActive = true;
        stoneWallTurns = STONE_WALL_DURATION;
        return name + " ergue um ESCUDO DE MURALHA! 🛡️ O grupo está protegido por "
                + STONE_WALL_DURATION + " turnos!";
    }

    public void updateStoneWall() {
        if (stoneWallActive) {
            stoneWallTurns--;
            if (stoneWallTurns <= 0) {
                stoneWallActive = false;
            }
        }
    }

    @Override
    public String getClassIcon() {
        return "🛡️";
    }

    @Override
    public String getStatus() {
        String wall = stoneWallActive ? " | Muralha: " + stoneWallTurns + " turnos" : "";
        return String.format("%s %s - %s%s",
                getClassIcon(), name, getHealthBar(), wall);
    }

    // Getters específicos

    public boolean isStoneWallActive() {
        return stoneWallActive;
    }

    public int getStoneWallTurns() {
        return stoneWallTurns;
    }

    public double getDamageAbsorption() {
        return DAMAGE_ABSORPTION;
    }
}