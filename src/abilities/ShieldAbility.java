package abilities;

import characters.Ability;
import characters.Character;


 //Usada pelo Tank
public class ShieldAbility implements Ability {
    private static final String NAME = "Escudo Muralha";
    private static final String DESCRIPTION = "Ergue um escudo que protege todo o grupo.";
    private int duration;

    /**
     * Construtor da habilidade de escudo.
     *
     * @param duration Duração em turnos
     */
    public ShieldAbility(int duration) {
        this.duration = duration;
    }

    @Override
    public String execute(Character user) {
        return user.getName() + " ergue a " + NAME + "! 🛡️🪨 O grupo está protegido por "
                + duration + " turnos!";
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION + " Dura " + duration + " turnos.";
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public boolean canUse(Character user) {
        return user.isAlive();
    }

    public int getDuration() {
        return duration;
    }
}