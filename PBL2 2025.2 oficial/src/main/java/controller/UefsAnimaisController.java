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
 * Controller principal da aplicação.
 * Centraliza o acesso à lógica de negócio e orquestra as operações entre os controllers
 * específicos de cada entidade (Animal, PessoaTutora, SetorResponsavel).
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.1 (14/10/2025)
 */
public class UefsAnimaisController {

    private AnimalController animalController;
    private PessoaTutoraController pessoaTutoraController;
    private SetorResponsavelController setorResponsavelController;

    /**
     * Construtor da classe UefsAnimaisController.
     * Inicializa os controllers de cada entidade que compõem o sistema.
     */
    public UefsAnimaisController(){
        this.animalController = new AnimalController();
        this.pessoaTutoraController = new PessoaTutoraController();
        this.setorResponsavelController = new SetorResponsavelController();
    }

    /**
     * Delega a adição de um novo animal para o AnimalController.
     * @param novoAnimal O animal a ser adicionado.
     * @return true se a operação for bem-sucedida.
     */
    public boolean adicionarAnimal(Animal novoAnimal) {
        return animalController.adicionarAnimal(novoAnimal);
    }

    /**
     * Delega a busca de um animal por ID para o AnimalController.
     * @param id O ID do animal.
     * @return O Animal encontrado ou null.
     */
    public Animal buscarAnimalId(int id) {
        return animalController.buscarAnimalId(id);
    }

    /**
     * Delega a busca de animais por nome para o AnimalController.
     * @param nome O nome a ser buscado.
     * @return Uma lista de animais.
     */
    public List<Animal> buscarAnimalNome(String nome){
        return animalController.buscarAnimalNome(nome);
    }

    /**
     * Delega a atualização de um animal para o AnimalController.
     * @param id O ID do animal a ser atualizado.
     * @param nome O novo nome.
     * @param especie A nova espécie.
     * @param raca A nova raça.
     * @param dataDeNascimento A nova data de nascimento.
     * @param sexo O novo sexo.
     * @param situacao A nova situação.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean atualizarAnimal(int id, String nome, String especie, String raca, YearMonth dataDeNascimento, String sexo, String situacao) {
        return animalController.atualizarAnimal(id, nome, especie, raca, dataDeNascimento, sexo, situacao);
    }

    /**
     * Remove um animal do sistema, apenas se ele não estiver associado a nenhum setor.
     * A verificação é feita por ID para garantir a consistência.
     *
     * @param id O ID do animal a ser removido.
     * @return true se o animal foi removido com sucesso, false caso contrário.
     */
    public boolean removerAnimal(int id) {
        if (animalController.buscarAnimalId(id) == null) {
            return false;
        }
        for (SetorResponsavel setor : setorResponsavelController.listarSetores()) {
            for (Animal animalNoSetor : setor.getAnimais()) {
                if (animalNoSetor.getId() == id) {
                    return false;
                }
            }
        }
        return animalController.removerAnimal(id);
    }

    /**
     * Delega a listagem de todos os animais para o AnimalController.
     * @return Uma lista de todos os animais.
     */
    public List<Animal> listarAnimais() {
        return animalController.listarAnimais();
    }

    /**
     * Delega a adição de uma nova pessoa tutora para o PessoaTutoraController.
     * @param novoTutor A pessoa tutora a ser adicionada.
     * @return true se a operação for bem-sucedida.
     */
    public boolean adicionarTutor(PessoaTutora novoTutor) {
        return pessoaTutoraController.adicionarTutor(novoTutor);
    }

    /**
     * Delega a busca de uma pessoa tutora por ID para o PessoaTutoraController.
     * @param id O ID da pessoa tutora.
     * @return A PessoaTutora encontrada ou null.
     */
    public PessoaTutora buscarTutorId(int id) {
        return pessoaTutoraController.buscarTutorId(id);
    }

    /**
     * Delega a busca de pessoas tutoras por nome para o PessoaTutoraController.
     * @param nome O nome a ser buscado.
     * @return Uma lista de pessoas tutoras.
     */
    public List<PessoaTutora> buscarTutorNome(String nome){
        return pessoaTutoraController.buscarTutorNome(nome);
    }

    /**
     * Delega a atualização de uma pessoa tutora para o PessoaTutoraController.
     * @param id O ID da pessoa tutora a ser atualizada.
     * @param nome O novo nome.
     * @param endereco O novo endereço.
     * @param telefone O novo telefone.
     * @param email O novo e-mail.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean atualizarTutor(int id, String nome, Endereco endereco, String telefone, String email) {
        return pessoaTutoraController.atualizarTutor(id, nome, endereco, telefone, email);
    }

    /**
     * Remove uma pessoa tutora do sistema, apenas se ela não estiver associada a nenhum setor.
     * A verificação é feita por ID para garantir a consistência.
     *
     * @param id O ID da pessoa tutora a ser removida.
     * @return true se a pessoa tutora foi removida com sucesso, false caso contrário.
     */
    public boolean removerTutor(int id) {
        if (pessoaTutoraController.buscarTutorId(id) == null) {
            return false;
        }
        for (SetorResponsavel setor : setorResponsavelController.listarSetores()) {
            for (PessoaTutora tutorNoSetor : setor.getPessoasTutoras()) {
                if (tutorNoSetor.getId() == id) {
                    return false;
                }
            }
        }
        return pessoaTutoraController.removerTutor(id);
    }

    /**
     * Delega a listagem de todas as pessoas tutoras para o PessoaTutoraController.
     * @return Uma lista de todas as pessoas tutoras.
     */
    public List<PessoaTutora> listarTutores() {
        return pessoaTutoraController.listarTutores();
    }

    /**
     * Delega a adição de um novo setor para o SetorResponsavelController.
     * @param novoSetor O setor a ser adicionado.
     * @return true se a operação for bem-sucedida.
     */
    public boolean adicionarSetor(SetorResponsavel novoSetor) {
        return setorResponsavelController.adicionarSetor(novoSetor);
    }

    /**
     * Delega a busca de um setor por ID para o SetorResponsavelController.
     * @param id O ID do setor.
     * @return O SetorResponsavel encontrado ou null.
     */
    public SetorResponsavel buscarSetorId(int id) {
        return setorResponsavelController.buscarSetorId(id);
    }

    /**
     * Delega a busca de setores por nome para o SetorResponsavelController.
     * @param nome O nome a ser buscado.
     * @return Uma lista de setores.
     */
    public List<SetorResponsavel> buscarSetorNome(String nome){
        return setorResponsavelController.buscarSetorNome(nome);
    }

    /**
     * Delega a atualização de um setor para o SetorResponsavelController.
     * @param id O ID do setor a ser atualizado.
     * @param novoNome O novo nome.
     * @param novoEndereco O novo endereço.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean atualizarSetor(int id, String novoNome, Endereco novoEndereco) {
        return setorResponsavelController.atualizarSetor(id, novoNome, novoEndereco);
    }

    /**
     * Remove um setor do sistema, apenas se ele não tiver animais ou tutores associados.
     * Esta regra de negócio protege a integridade dos dados, impedindo que associações sejam perdidas.
     *
     * @param id O ID do setor a ser removido.
     * @return true se o setor foi removido com sucesso, false se não foi encontrado ou se possui associações.
     */
    public boolean removerSetor(int id) {
        SetorResponsavel setorParaRemover = setorResponsavelController.buscarSetorId(id);
        if (setorParaRemover == null) {
            return false;
        }
        if (!setorParaRemover.getAnimais().isEmpty() || !setorParaRemover.getPessoasTutoras().isEmpty()) {
            return false;
        }
        return setorResponsavelController.removerSetor(id);
    }

    /**
     * Delega a listagem de todos os setores para o SetorResponsavelController.
     * @return Uma lista de todos os setores.
     */
    public List<SetorResponsavel> listarSetores() {
        return setorResponsavelController.listarSetores();
    }

    /**
     * Associa uma pessoa tutora a um setor responsável.
     *
     * @param idTutor O ID da pessoa tutora a ser associada.
     * @param idSetor O ID do setor que receberá a associação.
     * @return true se a associação for bem-sucedida, false se o tutor ou setor não existirem ou se a associação já existir.
     */
    public boolean associarTutorASetor(int idTutor, int idSetor){
        PessoaTutora tutor = pessoaTutoraController.buscarTutorId(idTutor);
        SetorResponsavel setor = setorResponsavelController.buscarSetorId(idSetor);

        if(tutor != null && setor != null && !setor.getPessoasTutoras().contains(tutor)){
            setor.getPessoasTutoras().add(tutor);
            return true;
        }
        return false;
    }

    /**
     * Desassocia uma pessoa tutora de um setor responsável.
     * Contém uma regra de negócio que impede a desassociação se o setor possuir animais
     * e este for o último tutor responsável, para não deixar animais "órfãos".
     *
     * @param idTutor O ID da pessoa tutora a ser desassociada.
     * @param idSetor O ID do setor do qual o tutor será removido.
     * @return true se a desassociação for bem-sucedida, false caso contrário.
     */
    public boolean desassociarTutorASetor(int idTutor, int idSetor) {
        PessoaTutora tutorParaRemover = pessoaTutoraController.buscarTutorId(idTutor);
        SetorResponsavel setor = setorResponsavelController.buscarSetorId(idSetor);

        if (tutorParaRemover == null || setor == null) {
            return false;
        }
        if (!setor.getAnimais().isEmpty() && setor.getPessoasTutoras().size() == 1) {
            if (setor.getPessoasTutoras().get(0).getId() == idTutor) {
                return false;
            }
        }
        return setor.getPessoasTutoras().removeIf(tutor -> tutor.getId() == idTutor);
    }

    /**
     * Associa um animal a um setor responsável.
     * Contém uma regra de negócio: um animal só pode ser associado a um setor que já possua pelo menos um tutor.
     *
     * @param idAnimal O ID do animal a ser associado.
     * @param idSetor O ID do setor que receberá a associação.
     * @return true se a associação for bem-sucedida, false caso contrário.
     */
    public boolean associarAnimalASetor(int idAnimal, int idSetor){
        Animal animal = animalController.buscarAnimalId(idAnimal);
        SetorResponsavel setor = setorResponsavelController.buscarSetorId(idSetor);

        if(animal != null && setor != null && !setor.getAnimais().contains(animal) && !setor.getPessoasTutoras().isEmpty()){
            setor.getAnimais().add(animal);
            return true;
        }
        return false;
    }

    /**
     * Desassocia um animal de um setor responsável.
     *
     * @param idAnimal O ID do animal a ser desassociado.
     * @param idSetor O ID do setor do qual o animal será removido.
     * @return true se a desassociação for bem-sucedida, false se o animal ou setor não existirem ou se a associação não existir.
     */
    public boolean desassociarAnimalDeSetor(int idAnimal, int idSetor) {
        Animal animal = animalController.buscarAnimalId(idAnimal);
        SetorResponsavel setor = setorResponsavelController.buscarSetorId(idSetor);

        if (animal != null && setor != null && setor.getAnimais().contains(animal)) {
            setor.getAnimais().remove(animal);
            return true;
        }
        return false;
    }

    /**
     * Gera um mapa para o relatório de animais por setor.
     *
     * @return Um Map onde a chave é o nome do setor e o valor é a lista de animais naquele setor.
     */
    public Map<String, List<Animal>> gerarRelatorioAnimaisPorSetor(){
        Map<String, List<Animal>> relatorio = new HashMap<>();
        List<SetorResponsavel> setores = setorResponsavelController.listarSetores();

        for(SetorResponsavel setor : setores){
            relatorio.put(setor.getNome(), setor.getAnimais());
        }
        return relatorio;
    }
}