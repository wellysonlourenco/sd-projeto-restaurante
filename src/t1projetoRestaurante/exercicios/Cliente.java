package t1projetoRestaurante.exercicios;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Cliente extends Thread {
	
	
    private static final String[] ITENS_CARDÁPIO = {
    		"Batata Frita", "Arroz Carreteiro", "Espetinho", "Pudim",
    		"Pizza", "Pastel", "Brigadeiro", "Sonho", "Bolo", "Sanduiche"};
    
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

            Pedido pedido = new Pedido(nomeItem, tempoPreparo, categoria, this);

            // Início da seção crítica
            lock.lock();
            try {
            	// Lógica do cliente
                filaPedidos.add(pedido); // Adicionar pedido à fila
                System.out.println("Cliente " + this.getName() + " fez um pedido: " + pedido.getNome());
                cozinheirosAvisados.signal(); // Notificar os cozinheiros que há um novo pedido
            } finally {
            	// Cliente terminou, sinalize que terminou
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