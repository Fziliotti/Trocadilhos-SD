# Trabalho de SD

## TO DO

- Arrumar o timer que estÃ¡ bugado, sempre esperando os 60 ou 20 segundos default (se todos os usuarios votarem tem que sair do while)
- Fazer um While no Game.run() , para repetir as rodadas atÃ© algum jogador alcanÃ§ar a pontuaÃ§Ã£o mÃ¡xima (a definir)
- Salvar o estado do jogo a cada rodada em um arquivo( Salvar a classe game)

- Fazer os testes que foram solicitados pelo professor


# Jogo de Trocadilhos

O jogo é um desafio de trocadilhos que pode reunir vários jogadores simultâneos. Ele é separado em rodadas e cada rodada consiste em, dado algum tema sorteado, um tempo é dado para cada pessoa escrever um trocadilho relacionado com aquele tema. Ao fim de cada rodada, todos os trocadilhos são exibidos e todos os jogadores avaliam. O trocadilho com mais votos vence a rodada e o jogador acumula uma pontuação. 

"****** Ao final das rodadas, quem tiver a maior pontuação é o vencedor.****** CONFIRMAR ESSA PARTE. Se o vencedor será dado no final das rodadas ou quando atingir determinaada pontuação."

## Como Rodar o programa e jogar

1. Execute a classe "Server.java" para iniciar o servidor do jogo.
2. Execute a classe "Client.java" para iniciar o primeiro participante do jogo. Será solicitado o número total de jogadores que irão jogar. Para uma melhor experiência, coloque 3 ou mais e aperte ENTER. Menos do que isso, como por exemplo dois jogadores, a experiência será ruim, pois no momento da votação do melhor trocadilho apenas o trocadinho do outro jogador estará disponível para votar.
3. Após escolher o número total de jogadores será solicitado o nickname do jogador. Escreva o nome e aperte ENTER. O jogo dará as boas vindas e dirá para aguardar a entrada dos outros participantes. A quantidade total de jogadores ativos e a esperada é mostrado na tela.
4. Execute a classe "Client.java" a quantidade de vezes que você colocou como número total de jogadores, toda execução é um novo cliente. A cada execução será solicitado o nickname do novo usuário, o jogo dará as boas vindas e mostrará quantos jogadores faltam para iniciar a partida.u
5. Quando o número de jogadores atingir o esperado a partida se iniciará com uma contagem regressiva para a primeira rodada. Um tema será gerado aleatoriamente a cada rodada. Os temas disponíveis são:

- Animais
- Celebridades
- Cinema
- Comida
- Desenho
- Futebol
- Heróis
- Música
- Saude
- Video Games

6. Quandos os jogadores enviarem os seus trocadilhos a votação para a escolha do melhor da rodada se abrirá. Cada um poderá votar uma vez em qualquer um dos trocadilhos enviados pelos participantes, menos no seu.
7. Ao final da votação será mostrado o resultado da votação e o placar geral.
8. Uma nova rodada se iniciará até alguém vencer.

## Execução em mais de uma máquina

O jogo desenvolvido permite a execução em mais de uma máquina. Por exemplo, para rodar em máquinas que compartilham a mesma rede sem a necessidade de uma IDE, gere o executável do programa com a classe "Server.java" marcada como a principal. Na IDE Eclipse siga o caminho abaixo:
File, Export, JAR File, Selecione o projeto, Next, Next, em Main Class escolha a classe "Server", Finish.

Ao executar teremos o servidor disponível para os clientes se conectarem. O JAR file gerado pode ser executado dando dois cliques ou pela linha de comando. Na linha de comando entre no local onde o arquivo foi salvo e digite o comando:
java - jar NOMEDOARQUIVO.jar

Para os clientes será necessário inserir o IP da máquina que está rodando o servidor. Esse IP deve ser inserido no "Client.java" em:
Socket socket = new Socket("localhost", 12345); 
Alterar o "localhost" pelo IP do servidor. O localhost é utilizado para rodarmos o jogo na máquina local, onde os clientes e servidor estão nela.

Após inserir o IP siga os mesmos passos do servidor, mas ao exportar e criar o JAR file selecione a classe "Client.java" em Main Class. Execute a quantidade de clientes que for necessário para iniciar a partida.
