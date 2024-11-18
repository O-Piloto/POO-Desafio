import java.io.*;
import java.util.*;

public class Simulacao {
    public static void main(String[] args) throws IOException {
        Propriedade[] propriedades = carregarPropriedades("gameConfig.txt");
        Tabuleiro tabuleiro = new Tabuleiro(propriedades);

        String[] tipos = {"Impulsivo", "Exigente", "Cauteloso", "Aleatorio"};
        Map<String, Integer> vitorias = new HashMap<>();
        int totalRodadas = 0, timeouts = 0;

        for (int i = 0; i < 300; i++) {
            Jogador[] jogadores = new Jogador[4];
            for (int j = 0; j < 4; j++) {
                jogadores[j] = new Jogador(tipos[j]);
            }

            Partida partida = new Partida(tabuleiro, jogadores);
            partida.jogar();

            Jogador vencedor = partida.getVencedor();
            if (vencedor != null) {
                vitorias.put(vencedor.nome, vitorias.getOrDefault(vencedor.nome, 0) + 1);
            }
            if (partida.rodadas >= 1000) timeouts++;
            totalRodadas += partida.rodadas;
        }

        System.out.println("Partidas terminadas por timeout: " + timeouts);
        System.out.println("Rodadas médias por partida: " + (totalRodadas / 300.0));
        for (String tipo : tipos) {
            System.out.println(tipo + " venceu " + vitorias.getOrDefault(tipo, 0) + " vezes.");
        }
    }

    private static Propriedade[] carregarPropriedades(String caminho) throws IOException {
        List<Propriedade> propriedades = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(caminho));

        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split("\\s+");
            int valorVenda = Integer.parseInt(partes[0]);
            int valorAluguel = Integer.parseInt(partes[1]);
            propriedades.add(new Propriedade(valorVenda, valorAluguel));
        }
        reader.close();

        return propriedades.toArray(new Propriedade[0]);
    }
}
