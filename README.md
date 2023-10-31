# TRABALHO 1 – SISTEMAS DISTRIBUÍDOS TSI 3713
<p><img src="https://i.ibb.co/3sNMDtV/Instituto-Federal-de-Mato-Grosso-do-Sul.png"  title="IFMS" width="160px"></img></p>

SISTEMA DE PEDIDOS EM UM RESTAURANTE PROBLEMA</br>
Você é encarregado(a) de desenvolver um sistema de pedidos para um 
restaurante movimentado que deseja melhorar sua eficiência no atendimento aos 
inúmeros clientes. Seu sistema deverá ser baseado no conceito de produtor consumidor para gerenciar os pedidos feitos pelos clientes e sua preparação pela 
equipe da cozinha.
REQUISITOS FUNCIONAIS
1. Pedidos de Clientes: Os clientes podem fazer pedidos de diferentes itens do 
cardápio (pelo menos 10 itens disponíveis para escolha, a qual pode ser aleatória). 
Os pedidos devem conter, pelo menos, os atributos:
a) nome;
b) tempo de preparo (valor aleatório de 100 a 4000 milissegundos)
c) categoria: [entrada, prato principal, sobremesa].
Cada cliente fará 3 pedidos, uma entrada, um prato principal e uma sobremesa.
Um cliente só pode realizar um pedido por vez.
2. Fila de Pedidos: Os pedidos dos clientes são colocados em uma fila de pedidos 
pendentes, onde serão aguardados para serem processados.
3. Preparação na Cozinha: A equipe da cozinha atua como o consumidor, retirando 
os pedidos da fila e preparando os itens solicitados.
4. Notificações aos Clientes: Os clientes devem ser notificados quando seus pedidos 
estiverem prontos para serem servidos. Semelhante ao tempo para produção, o 
cliente leva um tempo aleatório para consumir o pedido, o qual não deve ser inferior a
100 nem superior a 5000 milissegundos.
5. Número de clientes: Seu programa deve gerar um número aleatório, variando de 
20 a 50, cada cliente deve ser representado por uma thread.
6. Número de cozinheiros: Seu programa deve gerar um número aleatório, variando 
de 5 a 10, cada cozinheiro deve ser representado por uma thread.
REQUISITOS TÉCNICOS
1. Utilize o conceito de produtor-consumidor para gerenciar a fila de pedidos entre os 
clientes e a equipe da cozinha.
2. Implemente sincronização adequada para garantir que os pedidos sejam 
manipulados de forma segura por várias threads.
3. Crie um mecanismo de notificação para informar os clientes quando seus pedidos 
estiverem prontos.
4. O sistema deve ser capaz de lidar com múltiplos pedidos e vários cozinheiros 
trabalhando simultaneamente





```

Importação projeto no Eclipse
----------------------------
--- Passo a passo de como realizar a importação do projeto no eclipse
https://www.youtube.com/watch?v=WjQpxCwKd0w


Vai pedir usuario e senha para importar!
Precisa gerar o token
Click on settings Settings->Developer settings->Personal access tokens-> Generate new token.
https://www.youtube.com/watch?v=H_uyu9pzUE0
 
