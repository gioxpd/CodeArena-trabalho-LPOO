package abilities;

import characters.Ability;
import characters.Character;


public class HealAbility implements Ability {
    private static final String NAME = "Cura Divina";
    private static final String DESCRIPTION = "Canaliza energia sagrada para curar aliados.";
    private int healAmount;

    /**
     * Construtor da habilidade de cura.
     *
     * @param healAmount Quantidade de HP a curar
     */
    public HealAbility(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public String execute(Character user) {
        user.heal(healAmount);
        return user.getName() + " usa " + NAME + "! 💚✨ Recuperou " + healAmount + " HP!";
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION + " Cura " + healAmount + " HP.";
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public boolean canUse(Character user) {
        return user.isAlive();
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}