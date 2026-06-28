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

Coloque os 4 arquivos MP3 na pasta `assets/audio/` com **exatamente** estes nomes:

| Arquivo          | Quando toca                          | Loop |
|------------------|--------------------------------------|------|
| `background.mp3` | Durante as batalhas normais (estágios 1 a 4) | Sim  |
| `boss.mp3`       | Durante a batalha do chefe final (estágio 5) | Sim  |
| `victory.mp3`    | Tela de vitória                      | Não  |
| `defeat.mp3`     | Tela de derrota                      | Não  |

> A música de fundo troca automaticamente para a faixa do chefe ao chegar no
> estágio final, e para quando a tela de vitória/derrota aparece (e ao voltar
> para o menu). Se algum arquivo faltar, o jogo roda normalmente sem aquela faixa.

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
2. **Adicione a biblioteca JLayer ao classpath** (passo obrigatório):
   **File → Project Structure → Libraries → + (New Project Library) → Java →**
   selecione `lib/jlayer-1.0.1.jar` → **OK** → **Apply**.
3. Rode a classe `Main` (botão ▶ ao lado do `main`).
   - Se as imagens/músicas não carregarem, confira em
     **Run → Edit Configurations** se o *Working directory* é a raiz do projeto.

> **Erro `package javazoom.jl.player.advanced does not exist`?**
> Isso significa que o JLayer **não foi adicionado ao classpath**. Faça o passo 2 acima.
> Depois de adicionar a biblioteca, use **Build → Rebuild Project** e rode novamente.
> (Alternativa: clique com o botão direito em `lib/jlayer-1.0.1.jar` no painel de
> projeto e escolha **Add as Library...**.)

## Como jogar

1. No menu, digite seu nome e inicie a partida.
2. A cada turno, leia a pergunta e escolha a resposta.
   - Acertar causa dano ao inimigo; errar deixa o inimigo atacar.
3. Derrote todos os estágios de inimigos para vencer.
