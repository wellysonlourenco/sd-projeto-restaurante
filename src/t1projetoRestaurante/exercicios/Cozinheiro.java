package t1projetoRestaurante.exercicios;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Cozinheiro extends Thread {
    private Queue<Pedido> filaPedidos; 					// Fila de pedidos compartilhada
    private Lock lock; 									// Lock para sincronização
    private Condition cozinheirosAvisados; 				// Condição para notificar os cozinheiros

    
    
    
    public Cozinheiro(Queue<Pedido> filaPedidos, Lock lock, Condition cozinheirosAvisados) {
        // Construtor da classe Cozinheiro, inicializando as variáveis
    	this.filaPedidos = filaPedidos;    				// Inicializa a fila de pedidos compartilhada
        this.lock = lock;        						// Inicializa o lock para sincronização
        this.cozinheirosAvisados = cozinheirosAvisados; // Inicializa a condição de notificação
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido;

            // Início da seção crítica
            lock.lock();								// Bloqueia o lock para acesso exclusivo à fila de pedidos
            try {
                while (filaPedidos.isEmpty()) {
                    try {
                        cozinheirosAvisados.await(); 	// Aguardar se a fila estiver vazia
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                pedido = filaPedidos.poll(); 			// Retirar um pedido da fila
            } finally {
                lock.unlock();							// Libera o lock
            }
            // Fim da seção crítica

            
            
            // Processamento do pedido
            System.out.println(">>> Cozinheiro " + this.getName() + " esta preparando " + pedido.getNome() + " do cliente " + pedido.getCliente().getName());
            pedido.preparar(); // Preparar o pedido
            System.out.println("Pedido " + pedido.getNome() + " cliente " +" preparado pelo cozinheiro " + this.getName());
        }
    }
}



/*
 * Aqui está uma explicação detalhada do código da classe Cozinheiro:

1 - Cozinheiro é uma classe que estende Thread e representa uma thread que simula o comportamento de um cozinheiro em um restaurante.

2 - O construtor Cozinheiro recebe três parâmetros: a fila de pedidos compartilhada (filaPedidos), um lock para sincronização (lock), e uma condição para notificar os cozinheiros (cozinheirosAvisados). Ele inicializa essas variáveis com os valores fornecidos.

3 - No método run(), há um loop infinito (while (true)) que representa o comportamento contínuo do cozinheiro de verificar e preparar pedidos.

4 - A seção crítica é protegida pelo lock (lock.lock() e lock.unlock()) para garantir que apenas um cozinheiro por vez acesse a fila de pedidos.

5 - Dentro da seção crítica, o cozinheiro verifica se a fila de pedidos (filaPedidos) está vazia. Se estiver vazia, o cozinheiro espera (cozinheirosAvisados.await()) até que algum cliente coloque um pedido na fila.

6 - Quando um pedido está disponível na fila, ele é removido da fila (filaPedidos.poll()) para ser preparado pelo cozinheiro.

7 - O cozinheiro então simula a preparação do pedido chamando pedido.preparar(). Este é o momento em que o pedido é processado.

8 - Após a preparação do pedido, o cozinheiro exibe uma mensagem indicando qual pedido foi preparado.

9 - O loop continua, e o cozinheiro verifica novamente a fila de pedidos, repetindo o processo.
 * 
 */