package questions;

import questions.Question.Category;
import questions.Question.Difficulty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class QuestionBank {
    private List<Question> questions;
    private List<Question> usedQuestions;
    private Random random;

    public QuestionBank() {
        this.questions = new ArrayList<>();
        this.usedQuestions = new ArrayList<>();
        this.random = new Random();
        loadQuestions();
    }

    private void loadQuestions() {
        loadHistoryQuestions();
        loadBiologyQuestions();
        loadProgrammingQuestions();
        loadGamingQuestions();
    }

    private void loadHistoryQuestions() {
        // Fácil
        questions.add(new MultipleChoiceQuestion(
                "Em que ano o Brasil foi descoberto por Pedro Álvares Cabral?",
                Category.HISTORIA, Difficulty.FACIL,
                "1500", "1492", "1550", "1600"
        ));

        questions.add(new TrueFalseQuestion(
                "A Revolução Francesa ocorreu no século XVIII.",
                Category.HISTORIA, Difficulty.FACIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Quem foi o primeiro presidente do Brasil?",
                Category.HISTORIA, Difficulty.FACIL,
                "Deodoro da Fonseca", "Getúlio Vargas", "Dom Pedro II", "Juscelino Kubitschek"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Quem proclamou a Independência do Brasil?",
                Category.HISTORIA, Difficulty.FACIL,
                "Dom Pedro I", "Tiradentes", "Getúlio Vargas", "Dom Pedro II"
        ));

        questions.add(new TrueFalseQuestion(
                "O muro de Berlim caiu em 1989.",
                Category.HISTORIA, Difficulty.FACIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Quem foi conhecido como o 'Libertador da América'?",
                Category.HISTORIA, Difficulty.FACIL,
                "Simón Bolívar", "Che Guevara", "Napoleão", "Fidel Castro"
        ));

        questions.add(new TrueFalseQuestion(
                "Os samurais eram guerreiros do Japão feudal.",
                Category.HISTORIA, Difficulty.FACIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual nave levou o homem à Lua pela primeira vez?",
                Category.HISTORIA, Difficulty.FACIL,
                "Apollo 11", "Sputnik 1", "Challenger", "Voyager 1"
        ));

        questions.add(new FillBlankQuestion(
                "A independência do Brasil foi proclamada por Dom ____ I em 1822.",
                Category.HISTORIA, Difficulty.FACIL, "Pedro"
        ));

        // Médio
        questions.add(new MultipleChoiceQuestion(
                "Qual foi a principal consequência econômica da Revolução Industrial?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "Industrialização da produção", "Fim do comércio marítimo", "Extinção das cidades", "Redução do capitalismo"
        ));

        questions.add(new TrueFalseQuestion(
                "A Segunda Guerra Mundial terminou em 1945.",
                Category.HISTORIA, Difficulty.MEDIO, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual era o nome da política econômica baseada no acúmulo de metais preciosos durante a Idade Moderna?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "Mercantilismo", "Feudalismo", "Liberalismo", "Socialismo"
        ));

        questions.add(new TrueFalseQuestion(
                "Napoleão Bonaparte nasceu na França continental.",
                Category.HISTORIA, Difficulty.MEDIO, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual foi a principal rota comercial da Idade Média entre Europa e Oriente?",
                Category.HISTORIA, Difficulty.MEDIO,
                "Rota da Seda", "Rota do Ouro", "Caminho de Santiago", "Rota Atlântica"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual civilização criou um famoso calendário e vivia na América Central?",
                Category.HISTORIA, Difficulty.MEDIO,
                "Maias", "Romanos", "Egípcios", "Fenícios"
        ));

        questions.add(new TrueFalseQuestion(
                "A Guerra Fria envolveu confronto direto militar constante entre EUA e União Soviética.",
                Category.HISTORIA, Difficulty.MEDIO, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Quem escreveu as 95 teses que deram início à Reforma Protestante?",
                Category.HISTORIA, Difficulty.MEDIO,
                "Martinho Lutero", "João Calvino", "Henrique VIII", "Voltaire"
        ));

        // Difícil
        questions.add(new MultipleChoiceQuestion(
                "Em que ano ocorreu a Queda de Constantinopla?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "1453", "1492", "1519", "1389"
        ));

        questions.add(new TrueFalseQuestion(
                "A Guerra dos Cem Anos durou exatamente 100 anos.",
                Category.HISTORIA, Difficulty.DIFICIL, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual tratado encerrou oficialmente a Primeira Guerra Mundial?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "Tratado de Versalhes", "Tratado de Paris", "Tratado de Utrecht", "Tratado de Tordesilhas"
        ));

        questions.add(new TrueFalseQuestion(
                "O Império Bizantino tinha Constantinopla como capital.",
                Category.HISTORIA, Difficulty.DIFICIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual império era governado por Gêngis Khan?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "Império Mongol", "Império Otomano", "Império Persa", "Império Romano"
        ));

        questions.add(new TrueFalseQuestion(
                "A Revolução Industrial começou na Alemanha.",
                Category.HISTORIA, Difficulty.DIFICIL, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual foi o principal objetivo das Cruzadas?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "Reconquistar Jerusalém", "Expandir Roma", "Descobrir a América", "Unificar a Alemanha"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual conflito ficou conhecido como a guerra entre Atenas e Esparta na Grécia Antiga?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "Guerra do Peloponeso", "Guerras Médicas", "Guerra Púnica", "Guerra dos Trinta Anos"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual povo antigo ficou conhecido pela criação da escrita cuneiforme?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "Sumérios", "Egípcios", "Fenícios", "Hebreus"
        ));

        questions.add(new MultipleChoiceQuestion(
                "A Inconfidência Mineira ocorreu principalmente devido ao descontentamento com:",
                Category.HISTORIA, Difficulty.DIFICIL,
                "os altos impostos cobrados pela Coroa Portuguesa",
                "a invasão holandesa no Nordeste",
                "a proibição da escravidão indígena",
                "a transferência da capital para Brasília"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual foi a principal característica política do período conhecido como República Velha no Brasil?",
                Category.HISTORIA, Difficulty.DIFICIL,
                "A política do café com leite",
                "O parlamentarismo imperial",
                "A ditadura militar",
                "O voto universal secreto"
        ));

        questions.add(new FillBlankQuestion(
                "A crise econômica iniciada com a quebra da Bolsa de Nova York em 1929 ficou conhecida como Grande ____.",
                Category.HISTORIA, Difficulty.DIFICIL, "Depressao"
        ));

        questions.add(new FillBlankQuestion(
                "O acordo de 1494 que dividiu as novas terras do Atlântico entre as coroas portuguesa e espanhola, influenciando diretamente a formação territorial do Brasil, foi o Tratado de ____.",
                Category.HISTORIA, Difficulty.DIFICIL, "Tordesilhas"
        ));
    }

    private void loadBiologyQuestions() {
        // Fácil
        questions.add(new MultipleChoiceQuestion(
                "Qual é a organela responsável pela respiração celular?",
                Category.BIOLOGIA, Difficulty.FACIL,
                "Mitocôndria", "Ribossomo", "Núcleo", "Lisossomo"
        ));

        questions.add(new TrueFalseQuestion(
                "O DNA possui dupla hélice.",
                Category.BIOLOGIA, Difficulty.FACIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Quantos ossos tem o corpo humano adulto?",
                Category.BIOLOGIA, Difficulty.FACIL,
                "206", "208", "300", "196"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual é o maior mamífero do mundo?",
                Category.BIOLOGIA, Difficulty.FACIL,
                "Baleia-azul", "Elefante", "Tubarão-baleia", "Girafa"
        ));

        questions.add(new FillBlankQuestion(
                "A ____ é a organela responsável pela produção de energia (ATP) na célula.",
                Category.BIOLOGIA, Difficulty.FACIL, "Mitocôndria", "Mitocondria"
        ));

        // Médio
        questions.add(new MultipleChoiceQuestion(
                "Qual é o maior órgão do corpo humano?",
                Category.BIOLOGIA, Difficulty.MEDIO,
                "Pele", "Fígado", "Intestino", "Pulmão"
        ));

        questions.add(new TrueFalseQuestion(
                "Os vírus são considerados seres vivos pela maioria dos cientistas.",
                Category.BIOLOGIA, Difficulty.MEDIO, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual processo converte CO2 e H2O em glicose usando luz solar?",
                Category.BIOLOGIA, Difficulty.MEDIO,
                "Fotossíntese", "Respiração celular", "Quimiossíntese", "Fermentação"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual vitamina é produzida pela exposição ao Sol?",
                Category.BIOLOGIA, Difficulty.MEDIO,
                "Vitamina D", "Vitamina C", "Vitamina A", "Vitamina B12"
        ));

        questions.add(new TrueFalseQuestion(
                "Todos os fungos realizam fotossíntese.",
                Category.BIOLOGIA, Difficulty.MEDIO, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual parte da célula contém o material genético?",
                Category.BIOLOGIA, Difficulty.MEDIO,
                "Núcleo", "Citoplasma", "Membrana", "Mitocôndria"
        ));

        // Difícil
        questions.add(new MultipleChoiceQuestion(
                "Qual estrutura celular é responsável pela síntese de proteínas?",
                Category.BIOLOGIA, Difficulty.DIFICIL,
                "Ribossomo", "Complexo de Golgi", "Retículo endoplasmático", "Lisossomo"
        ));

        questions.add(new TrueFalseQuestion(
                "Todas as células eucarióticas possuem parede celular.",
                Category.BIOLOGIA, Difficulty.DIFICIL, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual é o nome do processo de divisão celular que gera gametas?",
                Category.BIOLOGIA, Difficulty.DIFICIL,
                "Meiose", "Mitose", "Fissão binária", "Clonagem"
        ));

        questions.add(new TrueFalseQuestion(
                "As bactérias são organismos procariontes.",
                Category.BIOLOGIA, Difficulty.DIFICIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "O processo pelo qual o RNA mensageiro é sintetizado a partir de uma molécula de DNA recebe o nome de:",
                Category.BIOLOGIA, Difficulty.DIFICIL,
                "Transcrição", "Replicação", "Tradução", "Mutação"
        ));

        questions.add(new MultipleChoiceQuestion(
                "A utilização de extratos de origem natural, mais especificamente, os produtos de origem botânica que combatem insetos, podem auxiliar no controle da:",
                Category.BIOLOGIA, Difficulty.DIFICIL, "leishmaniose",
                "esquistossomose", "leptospirose", "aids"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual grupo sanguíneo pode doar hemácias para qualquer outro grupo do sistema ABO?",
                Category.BIOLOGIA, Difficulty.DIFICIL,
                "O", "B", "A", "AB"
        ));

        questions.add(new MultipleChoiceQuestion(
                "A enzima responsável por sintetizar uma nova fita de DNA durante a replicação é a:",
                Category.BIOLOGIA, Difficulty.DIFICIL,
                "DNA Polimerase", "Helicase", "Ligase", "Primase"
        ));

        questions.add(new FillBlankQuestion(
                "O processo de divisão celular que origina gametas é chamado de ____.",
                Category.BIOLOGIA, Difficulty.DIFICIL, "Meiose"
        ));
    }

    private void loadProgrammingQuestions() {
        // Fácil
        questions.add(new MultipleChoiceQuestion(
                "Qual linguagem é conhecida como 'Write Once, Run Anywhere'?",
                Category.PROGRAMACAO, Difficulty.FACIL,
                "Java", "Python", "C++", "JavaScript"
        ));

        questions.add(new TrueFalseQuestion(
                "Em Java, uma classe pode herdar de múltiplas classes.",
                Category.PROGRAMACAO, Difficulty.FACIL, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "O que significa POO?",
                Category.PROGRAMACAO, Difficulty.FACIL,
                "Programação Orientada a Objetos", "Programação Online Operacional",
                "Processo de Otimização de Objetos", "Protocolo de Operações Online"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual símbolo é usado para comentários de uma linha em Java?",
                Category.PROGRAMACAO, Difficulty.FACIL,
                "//", "/*", "#", "<!--"
        ));

        questions.add(new TrueFalseQuestion(
                "Java diferencia letras maiúsculas e minúsculas.",
                Category.PROGRAMACAO, Difficulty.FACIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual estrutura é usada para repetir código várias vezes?",
                Category.PROGRAMACAO, Difficulty.FACIL,
                "Loop", "Classe", "Objeto", "Interface"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual linguagem é muito utilizada em ciência de dados e inteligência artificial?",
                Category.PROGRAMACAO, Difficulty.FACIL,
                "Python", "HTML", "CSS", "SQL"
        ));

        questions.add(new TrueFalseQuestion(
                "Python utiliza indentação para organizar blocos de código.",
                Category.PROGRAMACAO, Difficulty.FACIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual destas NÃO é uma linguagem de programação?",
                Category.PROGRAMACAO, Difficulty.FACIL,
                "Html", "Python", "Java", "C#"
        ));

        // Médio
        questions.add(new MultipleChoiceQuestion(
                "Qual é o resultado de: 10 % 3 em Java?",
                Category.PROGRAMACAO, Difficulty.MEDIO,
                "1", "3", "0", "10"
        ));

        questions.add(new TrueFalseQuestion(
                "Uma interface em Java pode conter implementação de métodos usando 'default'.",
                Category.PROGRAMACAO, Difficulty.MEDIO, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual palavra-chave é usada para criar uma instância de uma classe?",
                Category.PROGRAMACAO, Difficulty.MEDIO,
                "new", "create", "instance", "make"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual tipo armazena valores verdadeiros ou falsos em Java?",
                Category.PROGRAMACAO, Difficulty.MEDIO,
                "boolean", "int", "String", "double"
        ));

        questions.add(new TrueFalseQuestion(
                "Uma variável 'final' pode ter seu valor alterado.",
                Category.PROGRAMACAO, Difficulty.MEDIO, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual coleção permite elementos duplicados em Java?",
                Category.PROGRAMACAO, Difficulty.MEDIO,
                "ArrayList", "Set", "HashSet", "TreeSet"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual estrutura de repetição existe tanto em Java quanto em Python?",
                Category.PROGRAMACAO, Difficulty.MEDIO,
                "for", "repeat-until", "goto", "foreachitem"
        ));

        questions.add(new TrueFalseQuestion(
                "Em Python, listas podem armazenar diferentes tipos de dados.",
                Category.PROGRAMACAO, Difficulty.MEDIO, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual símbolo é usado para comentários de uma linha em Python?",
                Category.PROGRAMACAO, Difficulty.MEDIO,
                "#", "//", "/*", "--"
        ));

        questions.add(new FillBlankQuestion(
                "Em POO, o pilar que esconde os detalhes internos de uma classe é o ____.",
                Category.PROGRAMACAO, Difficulty.MEDIO, "Encapsulamento"
        ));


        // Difícil
        questions.add(new TrueFalseQuestion(
                "Em Java, o garbage collector pode ser invocado manualmente com garantia de execução imediata.",
                Category.PROGRAMACAO, Difficulty.DIFICIL, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual padrão de projeto garante que uma classe tenha apenas uma instância?",
                Category.PROGRAMACAO, Difficulty.DIFICIL,
                "Singleton", "Factory", "Observer", "Strategy"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual exceção ocorre ao dividir um número inteiro por zero em Java?",
                Category.PROGRAMACAO, Difficulty.DIFICIL,
                "ArithmeticException", "IOException", "NullPointerException", "ClassException"
        ));

        questions.add(new TrueFalseQuestion(
                "O método main em Java deve ser static.",
                Category.PROGRAMACAO, Difficulty.DIFICIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual paradigma de programação é focado em funções imutáveis e evita alterar estados?",
                Category.PROGRAMACAO, Difficulty.DIFICIL,
                "Programação funcional", "Programação procedural", "Programação orientada a eventos", "Programação imperativa"
        ));

        questions.add(new TrueFalseQuestion(
                "Compiladores traduzem código-fonte diretamente para linguagem de máquina.",
                Category.PROGRAMACAO, Difficulty.DIFICIL, true
        ));

        questions.add(new FillBlankQuestion(
                "A palavra-chave usada em Java para herdar de uma classe é ____.",
                Category.PROGRAMACAO, Difficulty.FACIL, "extends"
        ));
    }

    private void loadGamingQuestions() {
        // Fácil
        questions.add(new MultipleChoiceQuestion(
                "Qual é o personagem principal da série The Legend of Zelda?",
                Category.GAMES, Difficulty.FACIL,
                "Link", "Zelda", "Ganondorf", "Sheik"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Mario foi criado por qual empresa?",
                Category.GAMES, Difficulty.FACIL,
                "Nintendo", "EA Games","SEGA", "Konami"
        ));

        questions.add(new TrueFalseQuestion(
                "Em Minecraft, o material que é usado para fazer uma picareta mais básica de todas é pedra.",
                Category.GAMES, Difficulty.FACIL, false
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual personagem é conhecido por coletar anéis dourados?",
                Category.GAMES, Difficulty.FACIL,
                "Sonic", "Mario", "Crash", "Pac-Man"
        ));

        questions.add(new TrueFalseQuestion(
                "Pikachu é um Pokémon do tipo elétrico.",
                Category.GAMES, Difficulty.FACIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual empresa criou o console Xbox?",
                Category.GAMES, Difficulty.FACIL,
                "Microsoft", "Sony", "Nintendo", "SEGA"
        ));

        questions.add(new FillBlankQuestion(
                "Em Super Mario, o irmão de Mario se chama ____.",
                Category.GAMES, Difficulty.FACIL, "Luigi"
        ));

        // Médio
        questions.add(new MultipleChoiceQuestion(
                "Qual jogo popularizou o gênero Battle Royale?",
                Category.GAMES, Difficulty.MEDIO,
                "PUBG", "Fortnite", "Apex Legends", "Call of Duty Warzone"
        ));

        questions.add(new TrueFalseQuestion(
                "O primeiro jogo da série Final Fantasy foi lançado em 1987.",
                Category.GAMES, Difficulty.MEDIO, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual é o nome do protagonista de God of War (2018)?",
                Category.GAMES, Difficulty.MEDIO,
                "Kratos", "Atreus", "Ares", "Thor"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual é o nome da cidade principal de GTA: San Andreas?",
                Category.GAMES, Difficulty.MEDIO,
                "Los Santos", "Vice City", "Liberty City", "Raccoon City"
        ));

        questions.add(new TrueFalseQuestion(
                "O personagem Master Chief pertence à franquia Halo.",
                Category.GAMES, Difficulty.MEDIO, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual empresa desenvolveu o jogo Minecraft?",
                Category.GAMES, Difficulty.MEDIO,
                "Mojang", "Valve", "Ubisoft", "Blizzard"
        ));

        // Difícil
        questions.add(new MultipleChoiceQuestion(
                "Qual é o nome do criador do jogo Metal Gear Solid?",
                Category.GAMES, Difficulty.DIFICIL,
                "Hideo Kojima", "Shigeru Miyamoto", "Hidetaka Miyazaki", "Masahiro Sakurai"
        ));

        questions.add(new MultipleChoiceQuestion(
                "O jogo Dark Souls foi desenvolvido por qual empresa?",
                Category.GAMES, Difficulty.DIFICIL,
                "From Software", "BluePoint", "Capcom", "Sony"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Em qual ano foi lançado o primeiro console PlayStation?",
                Category.GAMES, Difficulty.DIFICIL,
                "1994", "1996", "1992", "1998"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual é o nome da espada principal de Cloud em Final Fantasy VII?",
                Category.GAMES, Difficulty.DIFICIL,
                "Buster Sword", "Masamune", "Gunblade", "Excalibur"
        ));

        questions.add(new TrueFalseQuestion(
                "Resident Evil originalmente foi inspirado em Sweet Home.",
                Category.GAMES, Difficulty.DIFICIL, true
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual estúdio criou a franquia Half-Life?",
                Category.GAMES, Difficulty.DIFICIL,
                "Valve", "id Software", "Bethesda", "Rockstar"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Qual o nome do protagonista da série Ace Attorney?",
                Category.GAMES, Difficulty.DIFICIL,
                "Phoenix Wright", "Euclides Graça", "Miles Edgeworth", "Furio Tigre"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Quantos Colossos enfrentamos no jogo Shadow of the Colossus?",
                Category.GAMES, Difficulty.DIFICIL,
                "16", "14", "17", "20"
        ));

        questions.add(new MultipleChoiceQuestion(
                "Em Rocket League, qual é o nome da mecânica usada para virar rapidamente o carro em 180 graus mantendo velocidade?",
                Category.GAMES, Difficulty.DIFICIL,
                "Half Flip", "Musty Flick", "Speed Jump", "Air Dribble"
        ));

        questions.add(new FillBlankQuestion(
                "A empresa responsável pela franquia Hollow Knight é a ____.",
                Category.GAMES, Difficulty.DIFICIL, "Team Cherry"
        ));
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            resetQuestions();
        }

        int index = random.nextInt(questions.size());
        Question question = questions.remove(index);
        usedQuestions.add(question);
        return question;
    }

    public Question getQuestionForLevel(int roundNumber) {

        int chance = random.nextInt(100);

        Difficulty difficulty;

        // Rodadas 1 e 2 -> foco em perguntas fáceis
        if (roundNumber <= 2) {

            if (chance < 70) {
                difficulty = Difficulty.FACIL;
            } else if (chance < 95) {
                difficulty = Difficulty.MEDIO;
            } else {
                difficulty = Difficulty.DIFICIL;
            }

        }
        // Rodadas 3 e 4 -> equilíbrio
        else if (roundNumber <= 4) {

            if (chance < 25) {
                difficulty = Difficulty.FACIL;
            } else if (chance < 65) {
                difficulty = Difficulty.MEDIO;
            } else {
                difficulty = Difficulty.DIFICIL;
            }

        }
        // Rodada 5 -> final difícil
        else {

            if (chance < 5) {
                difficulty = Difficulty.FACIL;
            } else if (chance < 20) {
                difficulty = Difficulty.MEDIO;
            } else {
                difficulty = Difficulty.DIFICIL;
            }
        }

        return getQuestionByDifficulty(difficulty);
    }

    public Question getQuestionByCategory(Category category) {
        List<Question> categoryQuestions = questions.stream()
                .filter(q -> q.getCategory() == category)
                .collect(Collectors.toList());

        if (categoryQuestions.isEmpty()) {
            return getRandomQuestion();
        }

        int index = random.nextInt(categoryQuestions.size());
        Question question = categoryQuestions.get(index);
        questions.remove(question);
        usedQuestions.add(question);
        return question;
    }


    public Question getQuestionByDifficulty(Difficulty difficulty) {
        List<Question> difficultyQuestions = questions.stream()
                .filter(q -> q.getDifficulty() == difficulty)
                .collect(Collectors.toList());

        if (difficultyQuestions.isEmpty()) {
            return getRandomQuestion();
        }

        int index = random.nextInt(difficultyQuestions.size());
        Question question = difficultyQuestions.get(index);
        questions.remove(question);
        usedQuestions.add(question);
        return question;
    }

    public void resetQuestions() {
        questions.addAll(usedQuestions);
        usedQuestions.clear();
        Collections.shuffle(questions);
    }

    public int getAvailableQuestionsCount() {
        return questions.size();
    }

    public int getTotalQuestionsCount() {
        return questions.size() + usedQuestions.size();
    }
}