import java.util.Random;

class Partida {
    Tabuleiro tabuleiro;
    Jogador[] jogadores;
    int[] posicoes;
    int rodadas;

    public Partida(Tabuleiro tabuleiro, Jogador[] jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        this.posicoes = new int[jogadores.length];
        this.rodadas = 0;
    }

    public void jogar() {
        Random dado = new Random();

        while (rodadas < 1000 && jogadoresAtivos() > 1) {
            for (int i = 0; i < jogadores.length; i++) {
                if (!jogadores[i].ativo) continue;

                int movimento = dado.nextInt(6) + 1;
                posicoes[i] = (posicoes[i] + movimento) % tabuleiro.propriedades.length;
                Propriedade propriedade = tabuleiro.getPropriedade(posicoes[i]);

                processarJogada(jogadores[i], propriedade);
            }
            rodadas++;
        }
    }

    private void processarJogada(Jogador jogador, Propriedade propriedade) {
        if (propriedade.dono == null) {
            if (jogador.decideCompra(propriedade, jogador.nome) && jogador.coins >= propriedade.valorVenda) {
                jogador.coins -= propriedade.valorVenda;
                propriedade.dono = jogador.nome;
            }
        } else if (!propriedade.dono.equals(jogador.nome)) {
            Jogador dono = encontrarJogadorPorNome(propriedade.dono);
            if (dono != null && jogador.coins >= propriedade.valorAluguel) {
                jogador.coins -= propriedade.valorAluguel;
                dono.coins += propriedade.valorAluguel;
            }
        }

        if (jogador.coins < 0) {
            jogador.ativo = false;
            liberarPropriedades(jogador);
        }
    }

    private Jogador encontrarJogadorPorNome(String nome) {
        for (Jogador jogador : jogadores) {
            if (jogador.nome.equals(nome)) return jogador;
        }
        return null;
    }

    private void liberarPropriedades(Jogador jogador) {
        for (Propriedade propriedade : tabuleiro.propriedades) {
            if (jogador.nome.equals(propriedade.dono)) {
                propriedade.dono = null;
            }
        }
    }

    private int jogadoresAtivos() {
        int ativos = 0;
        for (Jogador jogador : jogadores) {
            if (jogador.ativo) ativos++;
        }
        return ativos;
    }

    public Jogador getVencedor() {
        Jogador vencedor = null;
        for (Jogador jogador : jogadores) {
            if (jogador.ativo && (vencedor == null || jogador.coins > vencedor.coins)) {
                vencedor = jogador;
            }
        }
        return vencedor;
    }
}
