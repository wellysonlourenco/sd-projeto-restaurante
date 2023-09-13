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


## Grupo
- wellyson
- Benedito
- Edmilson

```
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Classe que representa um pedido
class Pedido {
    private String nome;
    private long tempoPreparo;
    private String categoria;

    public Pedido(String nome, long tempoPreparo, String categoria) {
        this.nome = nome;
        this.tempoPreparo = tempoPreparo;
        this.categoria = categoria;
    }

    // Getters para os atributos

    // Método para simular a preparação do pedido
    public void preparar() {
        try {
            Thread.sleep(tempoPreparo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Classe que representa um cliente
class Cliente extends Thread {
    private static final String[] ITENS_CARDÁPIO = {"Item1", "Item2", "Item3", /* ... */, "Item10"};
    private static final String[] CATEGORIAS = {"entrada", "prato principal", "sobremesa"};

    private Queue<Pedido> filaPedidos; // Fila de pedidos compartilhada
    private Random random = new Random();
    private Lock lock; // Lock para sincronização
    private Condition cozinheirosAvisados; // Condição para notificar os cozinheiros

    public Cliente(Queue<Pedido> filaPedidos, Lock lock, Condition cozinheirosAvisados) {
        this.filaPedidos = filaPedidos;
        this.lock = lock;
        this.cozinheirosAvisados = cozinheirosAvisados;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            String nomeItem = ITENS_CARDÁPIO[random.nextInt(ITENS_CARDÁPIO.length)];
            long tempoPreparo = random.nextInt(3900) + 100; // Entre 100 e 4000 milissegundos
            String categoria = CATEGORIAS[i];

            Pedido pedido = new Pedido(nomeItem, tempoPreparo, categoria);

            // Início da seção crítica
            lock.lock();
            try {
                filaPedidos.add(pedido); // Adicionar pedido à fila
                System.out.println("Cliente " + this.getName() + " fez um pedido: " + pedido.getNome());
                cozinheirosAvisados.signal(); // Notificar os cozinheiros que há um novo pedido
            } finally {
                lock.unlock();
            }
            // Fim da seção crítica

            try {
                Thread.sleep(random.nextInt(4900) + 100); // Tempo entre pedidos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Tempo para consumir o pedido
            long tempoConsumo = random.nextInt(4900) + 100; // Entre 100 e 5000 milissegundos
            try {
                Thread.sleep(tempoConsumo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Classe que representa um cozinheiro
class Cozinheiro extends Thread {
    private Queue<Pedido> filaPedidos; // Fila de pedidos compartilhada
    private Lock lock; // Lock para sincronização
    private Condition cozinheirosAvisados; // Condição para notificar os cozinheiros

    public Cozinheiro(Queue<Pedido> filaPedidos, Lock lock, Condition cozinheirosAvisados) {
        this.filaPedidos = filaPedidos;
        this.lock = lock;
        this.cozinheirosAvisados = cozinheirosAvisados;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido;

            // Início da seção crítica
            lock.lock();
            try {
                while (filaPedidos.isEmpty()) {
                    try {
                        cozinheirosAvisados.await(); // Aguardar se a fila estiver vazia
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                pedido = filaPedidos.poll(); // Retirar um pedido da fila
            } finally {
                lock.unlock();
            }
            // Fim da seção crítica

            System.out.println("Cozinheiro " + this.getName() + " está preparando " + pedido.getNome());
            pedido.preparar(); // Preparar o pedido
            System.out.println("Pedido " + pedido.getNome() + " preparado pelo cozinheiro " + this.getName());
        }
    }
}

// Classe principal do programa
public class SistemaDistribuido {
    public static void main(String[] args) {
        Queue<Pedido> filaPedidos = new LinkedList<>(); // Fila de pedidos compartilhada
        int numClientes = new Random().nextInt(31) + 20; // Entre 20 e 50 clientes
        int numCozinheiros = new Random().nextInt(6) + 5; // Entre 5 e 10 cozinheiros
        Lock lock = new ReentrantLock(); // Lock para sincronização
        Condition cozinheirosAvisados = lock.newCondition(); // Condição para notificar os cozinheiros

        // Iniciar as threads dos clientes
        for (int i = 1; i <= numClientes; i++) {
            new Cliente(filaPedidos, lock, cozinheirosAvisados).start();
        }

        // Iniciar as threads dos cozinheiros
        for (int i = 1; i <= numCozinheiros; i++) {
            new Cozinheiro(filaPedidos, lock, cozinheirosAvisados).start();
        }
    }
}



```
