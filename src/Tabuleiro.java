class Tabuleiro {
    Propriedade[] propriedades;

    public Tabuleiro(Propriedade[] propriedades) {
        this.propriedades = propriedades;
    }

    public Propriedade getPropriedade(int posicao) {
        return propriedades[posicao % propriedades.length];
    }
}
