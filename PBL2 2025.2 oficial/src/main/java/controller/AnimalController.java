package controller;

import model.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
 * para a entidade Animal. Utiliza um mapa para armazenamento em memória.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
public class AnimalController {

    private Map<Integer, Animal> mapaDeAnimais;
    private int idAtual = 1;

    /**
     * Construtor da classe AnimalController.
     * Inicializa a estrutura de dados em memória para armazenar os animais.
     */
    public AnimalController(){
        this.mapaDeAnimais = new HashMap<>();
    }

    /**
     * Adiciona um novo animal ao sistema.
     * O método atribui um ID único e sequencial ao animal antes de armazená-lo.
     *
     * @param novoAnimal O objeto Animal a ser adicionado.
     * @return Sempre retorna true, indicando sucesso na operação.
     */
    public boolean adicionarAnimal(Animal novoAnimal){
        novoAnimal.setId(idAtual);
        mapaDeAnimais.put(idAtual, novoAnimal);
        idAtual++;
        return true;
    }

    /**
     * Busca um animal no sistema pelo seu identificador único.
     *
     * @param id O ID do animal a ser buscado.
     * @return O objeto Animal correspondente ao ID, ou null se não for encontrado.
     */
    public Animal buscarAnimalId(int id) {
        return this.mapaDeAnimais.get(id);
    }

    /**
     * Busca animais no sistema pelo nome, ignorando diferenças entre maiúsculas e minúsculas.
     *
     * @param nome O nome do animal a ser buscado.
     * @return Uma lista de objetos Animal que correspondem ao nome fornecido. A lista estará vazia se nenhum animal for encontrado.
     */
    public List<Animal> buscarAnimalNome(String nome){
        List<Animal> animais = new ArrayList<>();

        for(Animal animal : this.mapaDeAnimais.values()){
            if(animal.getNome().equalsIgnoreCase(nome)){
                animais.add(animal);
            }
        }
        return animais;
    }

    /**
     * Remove um animal do sistema com base no seu ID.
     *
     * @param id O ID do animal a ser removido.
     * @return true se o animal foi removido com sucesso, false se nenhum animal com o ID fornecido foi encontrado.
     */
    public boolean removerAnimal(int id){
        if(mapaDeAnimais.containsKey(id)){
            mapaDeAnimais.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Retorna uma lista com todos os animais cadastrados no sistema.
     *
     * @return Uma nova lista contendo todos os animais.
     */
    public List<Animal> listarAnimais(){
        // Retorna uma cópia da lista de valores para proteger o mapa original de modificações externas.
        return new ArrayList<>(this.mapaDeAnimais.values());
    }

    /**
     * Atualiza os dados de um animal já existente no sistema.
     *
     * @param id O ID do animal a ser atualizado.
     * @param nome O novo nome do animal.
     * @param especie A nova espécie do animal.
     * @param raca A nova raça do animal.
     * @param dataDeNascimento A nova data de nascimento aproximada.
     * @param sexo O novo sexo do animal.
     * @param situacao A nova situação atual do animal.
     * @return true se a atualização foi bem-sucedida, false se o animal com o ID fornecido não foi encontrado.
     */
    public boolean atualizarAnimal(int id, String nome, String especie, String raca, YearMonth dataDeNascimento, String sexo, String situacao) {
        if (mapaDeAnimais.containsKey(id)) {
            Animal animalParaAtualizar = mapaDeAnimais.get(id);
            animalParaAtualizar.setNome(nome);
            animalParaAtualizar.setEspecie(especie);
            animalParaAtualizar.setRaca(raca);
            animalParaAtualizar.setDataDeNascimento(dataDeNascimento);
            animalParaAtualizar.setSexo(sexo);
            animalParaAtualizar.setSituacaoAtual(situacao);
            return true;
        }
        return false;
    }
}