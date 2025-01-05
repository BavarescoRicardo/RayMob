# 🎮 RayCasting Game

Bem-vindo ao **RayCasting Game**, um projeto inspirado nos clássicos como **Doom**! Criado com **Java** e a poderosa biblioteca **LibGDX**, este jogo explora técnicas de Ray Casting para simular uma visão em primeira pessoa em um ambiente 2D. 🕹️

## 📝 Descrição

Neste projeto, você controla um personagem que explora um mapa em estilo retrô, interagindo com elementos como paredes e passagens. O ambiente é renderizado em tempo real usando Ray Casting, oferecendo uma perspectiva imersiva para o jogador.

O jogo utiliza conceitos de geometria e física para criar uma experiência única de renderização e movimentação.

---

## 🚀 Funcionalidades

- **Mapa 2D Dinâmico**:
    - O mapa é definido por uma matriz que representa paredes e espaços livres.
- **Movimentação Suave**:
    - Controle o jogador com `W`, `A`, `S`, `D`.
    - Rotação do campo de visão com precisão.
- **Simulação de Primeira Pessoa**:
    - Realismo através de cálculos de ângulos e projeções.
- **Visual 2D e 3D**:
    - Alternância entre visão superior (mini mapa) e renderização 3D.

---

## 📸 Preview

![Preview do Jogo](preview.png)

---

## 🛠️ Tecnologias Utilizadas

- **Java** para lógica de jogo e algoritmos.
- **LibGDX** para renderização gráfica e engine de jogo.
- **Gradle** para gerenciamento de dependências.

---

## 🎮 Como Jogar

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/raycasting-game.git


🌟 Sobre o Desenvolvedor

Ricardo Bavaresco
Software Developer
Joaçaba, Santa Catarina, Brasil

Sou um desenvolvedor Full Stack apaixonado por programação, com habilidades em Java, JavaScript, e frameworks como Spring Boot, Node.js, e React. Tenho experiência em desenvolvimento de jogos, integração de algoritmos complexos e sempre busco criar experiências inovadoras.

🔗 Meu LinkedIn
⚙️ Detalhes do Projeto
Estrutura

    Core: Lógica principal compartilhada por todas as plataformas.
    LWJGL3: Plataforma desktop para execução local.
    Android: Plataforma para dispositivos móveis Android.

Gradle

Este projeto utiliza Gradle para gerenciamento de dependências. Algumas tarefas úteis incluem:

    build: Compila o código e cria os artefatos.
    clean: Limpa os diretórios de build.
    lwjgl3:run: Inicia o jogo na plataforma desktop.
    android:lint: Valida o projeto Android.
