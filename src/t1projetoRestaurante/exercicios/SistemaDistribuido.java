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