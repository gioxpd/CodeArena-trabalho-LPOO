# CodeArena: Batalha do Conhecimento

Jogo de RPG por turnos feito em **Java (Swing)** onde o jogador responde perguntas
de diferentes categorias (História, Biologia, Programação, Games) para derrotar
inimigos. Projeto da disciplina de **LPOO**.

## Requisitos

- **JDK 8 ou superior** instalado (`java` e `javac` disponíveis no terminal).
  - Verifique com: `java -version` e `javac -version`
- A biblioteca **JLayer** já está incluída em `lib/jlayer-1.0.1.jar` (usada para tocar MP3).

## Estrutura do projeto

```
.
├── src/                  # código-fonte Java
│   ├── Main.java         # ponto de entrada
│   ├── audio/            # SoundManager (controle das músicas)
│   ├── characters/       # heróis e habilidades
│   ├── enemies/          # inimigos
│   ├── questions/        # banco de perguntas
│   ├── system/           # jogador e pontuação
│   └── ui/               # interface gráfica (Swing)
├── assets/
│   ├── images/           # sprites dos personagens e inimigos
│   └── audio/            # músicas do jogo (MP3) — veja abaixo
└── lib/
    └── jlayer-1.0.1.jar  # biblioteca para reprodução de MP3
```

## Músicas

Coloque os 3 arquivos MP3 na pasta `assets/audio/` com **exatamente** estes nomes:

| Arquivo          | Quando toca                          | Loop |
|------------------|--------------------------------------|------|
| `background.mp3` | Durante as batalhas                  | Sim  |
| `victory.mp3`    | Tela de vitória                      | Não  |
| `defeat.mp3`     | Tela de derrota                      | Não  |

> A música de fundo para automaticamente quando a tela de vitória/derrota aparece,
> e também ao voltar para o menu. Se algum arquivo faltar, o jogo roda normalmente
> sem aquela faixa.

## Como executar

> Importante: o jogo lê as pastas `assets/` e `lib/` a partir do diretório onde
> você roda os comandos. **Execute sempre a partir da raiz do projeto.**

### Pelo terminal (Linux / macOS)

```bash
# 1. Compilar (saída na pasta out/)
javac -encoding UTF-8 -cp "lib/jlayer-1.0.1.jar" -d out $(find src -name "*.java")

# 2. Executar
java -cp "out:lib/jlayer-1.0.1.jar" Main
```

### Pelo terminal (Windows / PowerShell)

```powershell
# 1. Compilar
javac -encoding UTF-8 -cp "lib/jlayer-1.0.1.jar" -d out (Get-ChildItem -Recurse -Filter *.java src).FullName

# 2. Executar (no Windows o separador de classpath é ";")
java -cp "out;lib/jlayer-1.0.1.jar" Main
```

### Pelo IntelliJ IDEA

1. Abra a pasta do projeto no IntelliJ.
2. Adicione a biblioteca ao classpath:
   **File → Project Structure → Libraries → + → Java →** selecione `lib/jlayer-1.0.1.jar`.
3. Rode a classe `Main` (botão ▶ ao lado do `main`).
   - Se as imagens/músicas não carregarem, confira em
     **Run → Edit Configurations** se o *Working directory* é a raiz do projeto.

## Como jogar

1. No menu, digite seu nome e inicie a partida.
2. A cada turno, leia a pergunta e escolha a resposta.
   - Acertar causa dano ao inimigo; errar deixa o inimigo atacar.
3. Derrote todos os estágios de inimigos para vencer.
