import java.util.Random;

class Jogador {
    String nome;
    int coins;
    boolean ativo;

    public Jogador(String nome) {
        this.nome = nome;
        this.coins = 300;
        this.ativo = true;
    }

    public boolean decideCompra(Propriedade propriedade, String tipo) {
        if (tipo.equals("Impulsivo")) {
            return true;
        } else if (tipo.equals("Exigente")) {
            return propriedade.valorAluguel > 50;
        } else if (tipo.equals("Cauteloso")) {
            return this.coins - propriedade.valorVenda >= 80;
        } else if (tipo.equals("Aleatorio")) {
            return new Random().nextBoolean();
        }
        return false;
    }
}
