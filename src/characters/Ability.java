package characters;


public interface Ability {

    String execute(Character user);

    String getName();

    String getDescription();

    //Retorna o custo (se houver) da habilidade.

    int getCost();

    boolean canUse(Character user);
}