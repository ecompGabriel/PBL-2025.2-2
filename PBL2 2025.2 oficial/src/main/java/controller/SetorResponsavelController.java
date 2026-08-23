package controller;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Autor: Gabriel Oliveira de Freitas
 * Componente Curricular: MI - Programação
 * Concluido em: 12/10/2025
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 * trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 * apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 * de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 * do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 *
 * Controller responsável por gerenciar as operações de CRUD (Create, Read, Update, Delete)
 * para a entidade SetorResponsavel. Utiliza um mapa para armazenamento em memória.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
public class SetorResponsavelController {

    private Map<Integer, SetorResponsavel> mapaDeSetores;
    private int idAtual = 1;

    /**
     * Construtor da classe SetorResponsavelController.
     * Inicializa a estrutura de dados em memória para armazenar os setores.
     */
    public SetorResponsavelController(){
        this.mapaDeSetores = new HashMap<>();
    }

    /**
     * Adiciona um novo setor ao sistema.
     * O método atribui um ID único e sequencial antes de armazenar.
     *
     * @param novoSetor O objeto SetorResponsavel a ser adicionado.
     * @return Sempre retorna true, indicando sucesso na operação.
     */
    public boolean adicionarSetor(SetorResponsavel novoSetor){
        novoSetor.setId(idAtual);
        mapaDeSetores.put(idAtual, novoSetor);
        idAtual++;
        return true;
    }

    /**
     * Busca um setor no sistema pelo seu identificador único.
     *
     * @param id O ID do setor a ser buscado.
     * @return O objeto SetorResponsavel correspondente ao ID, ou null se não for encontrado.
     */
    public SetorResponsavel buscarSetorId(int id){
        return this.mapaDeSetores.get(id);
    }

    /**
     * Busca setores no sistema pelo nome, ignorando diferenças entre maiúsculas e minúsculas.
     *
     * @param nome O nome do setor a ser buscado.
     * @return Uma lista de objetos SetorResponsavel que correspondem ao nome fornecido. A lista estará vazia se nenhum setor for encontrado.
     */
    public List<SetorResponsavel> buscarSetorNome(String nome){
        List<SetorResponsavel> setores = new ArrayList<>();

        for(SetorResponsavel setor : this.mapaDeSetores.values()){
            if(setor.getNome().equalsIgnoreCase(nome)){
                setores.add(setor);
            }
        }
        return setores;
    }

    /**
     * Remove um setor do sistema com base no seu ID.
     *
     * @param id O ID do setor a ser removido.
     * @return true se a remoção foi bem-sucedida, false se nenhum setor com o ID fornecido foi encontrado.
     */
    public boolean removerSetor(int id){
        if(mapaDeSetores.containsKey(id)){
            mapaDeSetores.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Retorna uma lista com todos os setores cadastrados no sistema.
     *
     * @return Uma nova lista contendo todos os setores.
     */
    public List<SetorResponsavel> listarSetores(){
        // Retorna uma cópia da lista de valores para proteger o mapa original de modificações externas.
        return new ArrayList<>(this.mapaDeSetores.values());
    }

    /**
     * Atualiza os dados de um setor já existente no sistema.
     *
     * @param id O ID do setor a ser atualizado.
     * @param novoNome O novo nome para o setor.
     * @param novoEndereco O novo objeto Endereco para o setor.
     * @return true se a atualização foi bem-sucedida, false se o setor com o ID fornecido não foi encontrado.
     */
    public boolean atualizarSetor(int id, String novoNome, Endereco novoEndereco) {
        if (mapaDeSetores.containsKey(id)) {
            SetorResponsavel setorParaAtualizar = mapaDeSetores.get(id);
            setorParaAtualizar.setNome(novoNome);
            setorParaAtualizar.setEndereco(novoEndereco);
            return true;
        }
        return false;
    }
}