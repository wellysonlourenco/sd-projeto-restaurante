package t1projetoRestaurante.exercicios;

public class Pedido {
    private String nome;
    private long tempoPreparo;
    private String categoria;
    private Cliente cliente; // Adicione um atributo para o cliente


    public Pedido(String nome, long tempoPreparo, String categoria, Cliente cliente) {
        this.nome = nome;
        this.tempoPreparo = tempoPreparo;
        this.categoria = categoria;
        this.cliente = cliente;
    }

    

    // Método para simular a preparação do pedido
    public void preparar() {
        try {
            Thread.sleep(tempoPreparo); // tempo de preparo aguardando
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Getters para os atributos
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public long getTempoPreparo() {
		return tempoPreparo;
	}



	public void setTempoPreparo(long tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}



	public String getCategoria() {
		return categoria;
	}



	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
    
}





