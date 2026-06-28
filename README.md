# CodeArena: Batalha do Conhecimento

Jogo de RPG por turnos feito em **Java (Swing)** onde o jogador responde perguntas
de diferentes categorias (História, Biologia, Programação, Games) para derrotar
inimigos ao longo de 5 estágios, até enfrentar o chefe final. Projeto da disciplina
de **LPOO**.

## Como jogar

1. No menu inicial, digite o nome do jogador e inicie a partida.
2. A cada rodada, uma pergunta é exibida (múltipla escolha, verdadeiro/falso ou preencher a lacuna).
3. **Acertar** faz um dos heróis atacar o inimigo e acumula carga de habilidade.
4. **Errar** faz o inimigo atacar o grupo (o Tank pode proteger os aliados).
5. Ao acumular cargas suficientes, use a **habilidade especial** de um herói.
6. Derrote o inimigo de cada estágio para avançar. Vencer o estágio 5 conclui o jogo.

## Heróis

| Classe   | Destaque                                                        |
|----------|-----------------------------------------------------------------|
| Warrior  | Ataque equilibrado com golpe crítico                            |
| Healer   | Cura os aliados do grupo                                        |
| Mage     | Dano mágico extra em perguntas difíceis                         |
| Archer   | Ataques rápidos com chance de crítico/esquiva                   |
| Tank     | Alta defesa e proteção dos aliados (muralha de pedra)           |

## Requisitos

- **JDK 8 ou superior** instalado (`java` e `javac` disponíveis no terminal).
  - Verifique com: `java -version` e `javac -version`

## Estrutura do projeto

```
.
├── src/                  # código-fonte Java
│   ├── Main.java         # ponto de entrada
│   ├── characters/       # heróis, classe base Character e habilidades
│   ├── enemies/          # inimigos e a fábrica de inimigos por estágio
│   ├── questions/        # banco de perguntas, tipos de pergunta e avaliação
│   ├── system/           # jogador e sistema de pontuação
│   └── ui/               # interface gráfica (Swing)
└── assets/
    └── images/           # sprites dos personagens e inimigos
```

## Como executar

> Importante: o jogo lê a pasta `assets/` a partir do diretório onde você roda os
> comandos. **Execute sempre a partir da raiz do projeto.**

### Pelo terminal (Linux / macOS)

```bash
# 1. Compilar (saída na pasta out/)
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")

# 2. Executar
java -cp out Main
```

### Pelo terminal (Windows / PowerShell)

```powershell
# 1. Compilar
mkdir out
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src).FullName

# 2. Executar
java -cp out Main
```

### Pelo IntelliJ IDEA

1. Abra a pasta do projeto no IntelliJ.
2. Marque a pasta `src` como **Sources Root**, caso ainda não esteja
   (clique com o botão direito na pasta → *Mark Directory as → Sources Root*).
3. Rode a classe `Main` (botão ▶ ao lado do `main`).
   - Se as imagens não carregarem, confira em **Run → Edit Configurations** se o
     *Working directory* aponta para a raiz do projeto (onde fica a pasta `assets/`).

## Observações

- As imagens ficam em `assets/images/` e são carregadas em tempo de execução. Por
  isso, execute o jogo a partir da raiz do projeto para que os caminhos relativos
  funcionem corretamente.
