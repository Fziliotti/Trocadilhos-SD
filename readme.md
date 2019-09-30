# Trabalho de SD

## TO DO

- Arrumar o timer que está bugado, sempre esperando os 60 ou 20 segundos default (se todos os usuarios votarem tem que sair do while)
- Fazer um While no Game.run() , para repetir as rodadas até algum jogador alcançar a pontuação máxima (a definir)
- Salvar o estado do jogo a cada rodada em um arquivo( Salvar a classe game)

- Fazer os testes que foram solicitados pelo professor


# Jogo de Trocadilhos

O jogo � um desafio de trocadilhos que pode reunir v�rios jogadores simult�neos. Ele � separado em rodadas e cada rodada consiste em, dado algum tema sorteado, um tempo � dado para cada pessoa escrever um trocadilho relacionado com aquele tema. Ao fim de cada rodada, todos os trocadilhos s�o exibidos e todos os jogadores avaliam. O trocadilho com mais votos vence a rodada e o jogador acumula uma pontua��o. 

"****** Ao final das rodadas, quem tiver a maior pontua��o � o vencedor.****** CONFIRMAR ESSA PARTE. Se o vencedor ser� dado no final das rodadas ou quando atingir determinaada pontua��o."

## Como Rodar o programa e jogar

1. Execute a classe "Server.java" para iniciar o servidor do jogo.
2. Execute a classe "Client.java" para iniciar o primeiro participante do jogo. Ser� solicitado o n�mero total de jogadores que ir�o jogar. Para uma melhor experi�ncia, coloque 3 ou mais e aperte ENTER. Menos do que isso, como por exemplo dois jogadores, a experi�ncia ser� ruim, pois no momento da vota��o do melhor trocadilho apenas o trocadinho do outro jogador estar� dispon�vel para votar.
3. Ap�s escolher o n�mero total de jogadores ser� solicitado o nickname do jogador. Escreva o nome e aperte ENTER. O jogo dar� as boas vindas e dir� para aguardar a entrada dos outros participantes. A quantidade total de jogadores ativos e a esperada � mostrado na tela.
4. Execute a classe "Client.java" a quantidade de vezes que voc� colocou como n�mero total de jogadores, toda execu��o � um novo cliente. A cada execu��o ser� solicitado o nickname do novo usu�rio, o jogo dar� as boas vindas e mostrar� quantos jogadores faltam para iniciar a partida.u
5. Quando o n�mero de jogadores atingir o esperado a partida se iniciar� com uma contagem regressiva para a primeira rodada. Um tema ser� gerado aleatoriamente a cada rodada. Os temas dispon�veis s�o:

- Animais
- Celebridades
- Cinema
- Comida
- Desenho
- Futebol
- Her�is
- M�sica
- Saude
- Video Games

6. Quandos os jogadores enviarem os seus trocadilhos a vota��o para a escolha do melhor da rodada se abrir�. Cada um poder� votar uma vez em qualquer um dos trocadilhos enviados pelos participantes, menos no seu.
7. Ao final da vota��o ser� mostrado o resultado da vota��o e o placar geral.
8. Uma nova rodada se iniciar� at� algu�m vencer.

## Execu��o em mais de uma m�quina

O jogo desenvolvido permite a execu��o em mais de uma m�quina. Por exemplo, para rodar em m�quinas que compartilham a mesma rede sem a necessidade de uma IDE, gere o execut�vel do programa com a classe "Server.java" marcada como a principal. Na IDE Eclipse siga o caminho abaixo:
File, Export, JAR File, Selecione o projeto, Next, Next, em Main Class escolha a classe "Server", Finish.

Ao executar teremos o servidor dispon�vel para os clientes se conectarem. O JAR file gerado pode ser executado dando dois cliques ou pela linha de comando. Na linha de comando entre no local onde o arquivo foi salvo e digite o comando:
java - jar NOMEDOARQUIVO.jar

Para os clientes ser� necess�rio inserir o IP da m�quina que est� rodando o servidor. Esse IP deve ser inserido no "Client.java" em:
Socket socket = new Socket("localhost", 12345); 
Alterar o "localhost" pelo IP do servidor. O localhost � utilizado para rodarmos o jogo na m�quina local, onde os clientes e servidor est�o nela.

Ap�s inserir o IP siga os mesmos passos do servidor, mas ao exportar e criar o JAR file selecione a classe "Client.java" em Main Class. Execute a quantidade de clientes que for necess�rio para iniciar a partida.
