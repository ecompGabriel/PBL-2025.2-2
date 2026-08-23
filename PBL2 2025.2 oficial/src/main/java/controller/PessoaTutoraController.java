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
 * para a entidade PessoaTutora. Utiliza um mapa para armazenamento em memória.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
public class PessoaTutoraController {

    private Map<Integer, PessoaTutora> mapaDeTutores;
    private int idAtual = 1;

    /**
     * Construtor da classe PessoaTutoraController.
     * Inicializa a estrutura de dados em memória para armazenar as pessoas tutoras.
     */
    public PessoaTutoraController(){
        this.mapaDeTutores = new HashMap<>();
    }

    /**
     * Adiciona uma nova pessoa tutora ao sistema.
     * O método atribui um ID único e sequencial antes de armazenar.
     *
     * @param novoTutor O objeto PessoaTutora a ser adicionado.
     * @return Sempre retorna true, indicando sucesso na operação.
     */
    public boolean adicionarTutor(PessoaTutora novoTutor){
        novoTutor.setId(idAtual);
        mapaDeTutores.put(idAtual, novoTutor);
        idAtual++;
        return true;
    }

    /**
     * Busca uma pessoa tutora no sistema pelo seu identificador único.
     *
     * @param id O ID da pessoa tutora a ser buscada.
     * @return O objeto PessoaTutora correspondente ao ID, ou null se não for encontrado.
     */
    public PessoaTutora buscarTutorId(int id){
        return this.mapaDeTutores.get(id);
    }

    /**
     * Busca pessoas tutoras no sistema pelo nome, ignorando diferenças entre maiúsculas e minúsculas.
     *
     * @param nome O nome a ser buscado.
     * @return Uma lista de objetos PessoaTutora que correspondem ao nome fornecido. A lista estará vazia se ninguém for encontrado.
     */
    public List<PessoaTutora> buscarTutorNome(String nome){
        List<PessoaTutora> tutores = new ArrayList<>();

        for(PessoaTutora tutor : this.mapaDeTutores.values()){
            if(tutor.getNome().equalsIgnoreCase(nome)){
                tutores.add(tutor);
            }
        }
        return tutores;
    }

    /**
     * Remove uma pessoa tutora do sistema com base no seu ID.
     *
     * @param id O ID da pessoa tutora a ser removida.
     * @return true se a remoção foi bem-sucedida, false se nenhuma pessoa com o ID fornecido foi encontrada.
     */
    public boolean removerTutor(int id){
        if(mapaDeTutores.containsKey(id)){
            mapaDeTutores.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Retorna uma lista com todas as pessoas tutoras cadastradas no sistema.
     *
     * @return Uma nova lista contendo todas as pessoas tutoras.
     */
    public List<PessoaTutora> listarTutores(){
        // Retorna uma cópia da lista de valores para proteger o mapa original de modificações externas.
        return new ArrayList<>(this.mapaDeTutores.values());
    }

    /**
     * Atualiza os dados de uma pessoa tutora já existente no sistema.
     *
     * @param id O ID da pessoa tutora a ser atualizada.
     * @param nome O novo nome.
     * @param endereco O novo objeto Endereco.
     * @param telefone O novo número de telefone.
     * @param email O novo endereço de e-mail.
     * @return true se a atualização foi bem-sucedida, false se a pessoa com o ID fornecido não foi encontrada.
     */
    public boolean atualizarTutor(int id, String nome, Endereco endereco, String telefone, String email) {
        if (mapaDeTutores.containsKey(id)) {
            PessoaTutora tutorParaAtualizar = mapaDeTutores.get(id);
            tutorParaAtualizar.setNome(nome);
            tutorParaAtualizar.setEndereco(endereco);
            tutorParaAtualizar.setTelefone(telefone);
            tutorParaAtualizar.setEmail(email);
            return true;
        }
        return false;
    }
}