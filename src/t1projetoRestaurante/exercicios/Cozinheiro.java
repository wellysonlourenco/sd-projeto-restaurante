package t1projetoRestaurante.exercicios;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Cozinheiro extends Thread {
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

            System.out.println("Cozinheiro " + this.getName() + " esta preparando " + pedido.getNome());
            pedido.preparar(); // Preparar o pedido
            System.out.println("Pedido " + pedido.getNome() + " preparado pelo cozinheiro " + this.getName());
        }
    }
}