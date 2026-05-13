package abilities;

import characters.Ability;
import characters.Character;


 //Usada pelo Guerreiro.

public class DoubleDamageAbility implements Ability {
    private static final String NAME = "Golpe Crítico";
    private static final String DESCRIPTION = "Concentra toda a força para dobrar o dano do próximo ataque.";
    private static final int COST = 0;

    @Override
    public String execute(Character user) {
        return user.getName() + " ativa " + NAME + "! ⚔️💥 Próximo ataque causará dano dobrado!";
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public boolean canUse(Character user) {
        return user.isAlive();
    }
}