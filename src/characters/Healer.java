package characters;

import abilities.HealAbility;
import java.util.List;

/**
 * Classe Healer (Curandeira) - Representa a heroína Falistra.
 *
 * Função: Suporte e cura
 * Características:
 * - Baixa defesa
 * - Habilidades de cura
 * - Recupera HP do grupo
 *
 * Demonstra: Herança e comportamento especializado
 */
public class Healer extends Character {
    private List<Character> partyMembers;
    private static final int BASE_HP = 80;
    private static final int BASE_ATTACK = 10;
    private static final int BASE_DEFENSE = 8;
    private static final int BASE_SPEED = 12;
    private static final int HEAL_POWER = 30;

    public Healer(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.characterClass = "Curandeira";
        this.specialAbility = new HealAbility(HEAL_POWER);
    }

    public void setParty(List<Character> party) {
        this.partyMembers = party;
    }

    @Override
    public String usarHabilidade() {
        StringBuilder result = new StringBuilder();
        result.append(name).append(" invoca a Cura Divina! 💚✨\n");

        if (partyMembers != null) {
            for (Character member : partyMembers) {
                if (member.isAlive()) {
                    int healAmount = HEAL_POWER;
                    member.heal(healAmount);
                    result.append("   ").append(member.getName())
                            .append(" recuperou ").append(healAmount).append(" HP!\n");
                }
            }
        } else {
            // Se não tiver grupo definido, cura a si mesma
            this.heal(HEAL_POWER);
            result.append("   ").append(name).append(" recuperou ").append(HEAL_POWER).append(" HP!");
        }

        return result.toString();
    }

    public int healAlly(Character target) {
        if (target.isAlive()) {
            target.heal(HEAL_POWER);
            return HEAL_POWER;
        }
        return 0;
    }

    //Cura de emergência: cura maior, mas consome HP da curandeira, vou uasar depois
    public int emergencyHeal(Character target) {
        int emergencyHealAmount = HEAL_POWER * 2;
        int hpCost = 20;

        if (target.isAlive() && this.hp > hpCost) {
            this.hp -= hpCost;
            target.heal(emergencyHealAmount);
            return emergencyHealAmount;
        }
        return 0;
    }

    @Override
    public String getClassIcon() {
        return "💚";
    }

    public int getHealPower() {
        return HEAL_POWER;
    }
}