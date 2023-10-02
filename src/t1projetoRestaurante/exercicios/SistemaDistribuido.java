package t1projetoRestaurante.exercicios;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SistemaDistribuido {
    public static void main(String[] args) {
        Queue<Pedido> filaPedidos = new LinkedList<>(); // Fila de pedidos compartilhada
        int numClientes = new Random().nextInt(31) + 20; // Entre 20 e 50 clientes
        int numCozinheiros = new Random().nextInt(6) + 5; // Entre 5 e 10 cozinheiros
        Lock lock = new ReentrantLock(); // Lock para sincronização
        Condition cozinheirosAvisados = lock.newCondition(); // Condição para notificar os cozinheiros

        
        // Inicialização de variáveis:

        // - `filaPedidos`: É uma fila compartilhada onde os clientes colocarão seus pedidos e os cozinheiros os retirarão.
        // - `numClientes`: É um número aleatório entre 20 e 50 que representa a quantidade de clientes que serão criados.
        // - `numCozinheiros`: É um número aleatório entre 5 e 10 que representa a quantidade de cozinheiros que serão criados.
        // - `lock`: É um objeto do tipo `Lock` usado para sincronização.
        // - `cozinheirosAvisados`: É uma condição associada ao lock para notificar os cozinheiros quando há pedidos na fila.

        
        
        
        
        // Iniciar as threads dos clientes
        // O loop `for` inicia threads de clientes. Ele cria um número aleatório de clientes entre 20 e 50, conforme determinado por `numClientes`.
        for (int i = 1; i <= numClientes; i++) {
            new Cliente(filaPedidos, lock, cozinheirosAvisados).start(); 	// Inicia uma nova thread de cliente
        }

        // Iniciar as threads dos cozinheiros
        // O loop `for` inicia threads de cozinheiros. Ele cria um número aleatório de cozinheiros entre 5 e 10, conforme determinado por `numCozinheiros`.
        for (int i = 1; i <= numCozinheiros; i++) {
            new Cozinheiro(filaPedidos, lock, cozinheirosAvisados).start(); // Inicia uma nova thread de cozinheiro
        }

        // Exibir mensagem após o término do loop de iniciação dos clientes
        //System.out.println("Todas as threads de clientes foram iniciadas.");
              

    }
}