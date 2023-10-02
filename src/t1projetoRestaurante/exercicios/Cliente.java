package t1projetoRestaurante.exercicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Cliente extends Thread {
    private static final String[] ITENS_CARDÁPIO = {
        "Batata Frita", "Arroz Carreteiro", "Espetinho", "Pudim",
        "Pizza", "Pastel", "Brigadeiro", "Sonho", "Bolo", "Sanduiche"
    };

    private static final String[] CATEGORIAS = {"entrada", "prato principal", "sobremesa"};

    // Mapeamento de itens do cardápio para categorias
    private static final Map<String, String> ITEM_CATEGORIA_MAP = new HashMap<>();
    static {
        ITEM_CATEGORIA_MAP.put("Batata Frita", "entrada");
        ITEM_CATEGORIA_MAP.put("Arroz Carreteiro", "prato principal");
        ITEM_CATEGORIA_MAP.put("Espetinho", "prato principal");
        ITEM_CATEGORIA_MAP.put("Pudim", "sobremesa");
        ITEM_CATEGORIA_MAP.put("Pizza", "prato principal");
        ITEM_CATEGORIA_MAP.put("Pastel", "entrada");
        ITEM_CATEGORIA_MAP.put("Brigadeiro", "sobremesa");
        ITEM_CATEGORIA_MAP.put("Sonho", "sobremesa");
        ITEM_CATEGORIA_MAP.put("Bolo", "sobremesa");
        ITEM_CATEGORIA_MAP.put("Sanduiche", "prato principal");
    }

    private Queue<Pedido> filaPedidos;
    private Random random = new Random();
    private Lock lock;
    private Condition cozinheirosAvisados;
    private static int totalPedidos = 0; // Variável para acompanhar o número total de pedidos


    public Cliente(Queue<Pedido> filaPedidos, Lock lock, Condition cozinheirosAvisados) {
        this.filaPedidos = filaPedidos;
        this.lock = lock;
        this.cozinheirosAvisados = cozinheirosAvisados;
    }

    public void run() {
        for (String categoria : CATEGORIAS) {
            String nomeItem = getRandomItemFromCategory(categoria);
            long tempoPreparo = random.nextInt(3900) + 100; // Entre 100 e 4000 milissegundos

            Pedido pedido = new Pedido(nomeItem, tempoPreparo, categoria, this);

            // Início da seção crítica
            lock.lock();
            try {
                // Lógica do cliente
                filaPedidos.add(pedido); // Adicionar pedido à fila
                System.out.println("Cliente " + this.getName() + " fez um pedido: " + pedido.getNome() +
                        " (Categoria: " + pedido.getCategoria() + ", Tempo de Preparo: " + pedido.getTempoPreparo() + "ms)");
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
        }
    }

    private String getRandomItemFromCategory(String categoria) {
        List<String> itemsInCategory = new ArrayList<>();
        for (String item : ITENS_CARDÁPIO) {
            if (ITEM_CATEGORIA_MAP.get(item).equals(categoria)) {
                itemsInCategory.add(item);
            }
        }
        return itemsInCategory.get(random.nextInt(itemsInCategory.size()));
    }
}


    /*@Override
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
        }*/