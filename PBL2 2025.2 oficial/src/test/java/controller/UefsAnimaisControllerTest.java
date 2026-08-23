package controller;

import model.Animal;
import model.Endereco;
import model.PessoaTutora;
import model.SetorResponsavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
 * Classe de teste para a classe {@link UefsAnimaisController}.
 * Foca em testar a lógica de orquestração e as operações de associação, desassociação
 * e as regras de negócio de proteção de dados.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.1 (14/10/2025)
 */
class UefsAnimaisControllerTest {

    private UefsAnimaisController controller;
    private Endereco enderecoPadrao;

    /**
     * Configura o ambiente de teste antes da execução de cada método.
     * Inicializa um novo controller facade e um endereço padrão para garantir
     * o isolamento e a consistência dos testes.
     */
    @BeforeEach
    void setUp() {
        controller = new UefsAnimaisController();
        enderecoPadrao = new Endereco("Rua", "Bairro", "CEP", "Cidade", "BA", "Comp");
    }

    /**
     * Testa a associação bem-sucedida de uma pessoa tutora a um setor.
     * Verifica se o tutor é adicionado corretamente à lista de tutores do setor.
     */
    @Test
    @DisplayName("Deve associar um tutor a um setor com sucesso")
    void deveAssociarTutorASetorComSucesso() {
        PessoaTutora tutor = new PessoaTutora("Gabriel", enderecoPadrao, "111", "email@test.com");
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        controller.adicionarTutor(tutor);
        controller.adicionarSetor(setor);

        boolean resultado = controller.associarTutorASetor(1, 1);

        assertTrue(resultado);
        SetorResponsavel setorVerificacao = controller.buscarSetorId(1);
        assertEquals(1, setorVerificacao.getPessoasTutoras().size());
        assertEquals("Gabriel", setorVerificacao.getPessoasTutoras().get(0).getNome());
    }

    /**
     * Testa a regra de negócio que impede que um mesmo tutor seja associado
     * duas vezes ao mesmo setor.
     */
    @Test
    @DisplayName("Não deve associar um tutor que já está no setor")
    void naoDeveAssociarTutorJaAssociado() {
        PessoaTutora tutor = new PessoaTutora("Gabriel", enderecoPadrao, "111", "email@test.com");
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        controller.adicionarTutor(tutor);
        controller.adicionarSetor(setor);
        controller.associarTutorASetor(1, 1);

        boolean resultadoSegundaVez = controller.associarTutorASetor(1, 1);

        assertFalse(resultadoSegundaVez);
        assertEquals(1, controller.buscarSetorId(1).getPessoasTutoras().size());
    }

    /**
     * Testa a desassociação bem-sucedida de uma pessoa tutora de um setor.
     * Verifica se o tutor é removido da lista de tutores do setor.
     */
    @Test
    @DisplayName("Deve desassociar um tutor de um setor com sucesso")
    void deveDesassociarTutorDeSetorComSucesso() {
        PessoaTutora tutor = new PessoaTutora("Gabriel", enderecoPadrao, "111", "email@test.com");
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        controller.adicionarTutor(tutor);
        controller.adicionarSetor(setor);
        controller.associarTutorASetor(1, 1);

        boolean resultado = controller.desassociarTutorASetor(1, 1);

        assertTrue(resultado);
        assertTrue(controller.buscarSetorId(1).getPessoasTutoras().isEmpty());
    }

    /**
     * Testa a associação bem-sucedida de um animal a um setor que já possui
     * um tutor, cumprindo a regra de negócio.
     */
    @Test
    @DisplayName("Deve associar um animal a um setor que já possui tutor")
    void deveAssociarAnimalASetorComSucesso() {
        Animal animal = new Animal("Rex", "Cachorro", "Vira-lata", null, null, null);
        PessoaTutora tutor = new PessoaTutora("Gabriel", enderecoPadrao, "111", "email@test.com");
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        controller.adicionarAnimal(animal);
        controller.adicionarTutor(tutor);
        controller.adicionarSetor(setor);
        controller.associarTutorASetor(1, 1);

        boolean resultado = controller.associarAnimalASetor(1, 1);

        assertTrue(resultado);
        assertEquals(1, controller.buscarSetorId(1).getAnimais().size());
    }

    /**
     * Testa a regra de negócio que impede a associação de um animal a um setor
     * que não possui nenhuma pessoa tutora.
     */
    @Test
    @DisplayName("Não deve associar um animal a um setor sem tutores")
    void naoDeveAssociarAnimalASetorSemTutor() {
        Animal animal = new Animal("Rex", "Cachorro", "Vira-lata", null, null, null);
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        controller.adicionarAnimal(animal);
        controller.adicionarSetor(setor);

        boolean resultado = controller.associarAnimalASetor(1, 1);

        assertFalse(resultado);
        assertTrue(controller.buscarSetorId(1).getAnimais().isEmpty());
    }

    /**
     * Testa a desassociação bem-sucedida de um animal de um setor.
     * Verifica se o animal é removido da lista de animais do setor.
     */
    @Test
    @DisplayName("Deve desassociar um animal de um setor com sucesso")
    void deveDesassociarAnimalDeSetorComSucesso() {
        Animal animal = new Animal("Rex", "Cachorro", "Vira-lata", null, null, null);
        PessoaTutora tutor = new PessoaTutora("Gabriel", enderecoPadrao, "111", "email@test.com");
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        controller.adicionarAnimal(animal);
        controller.adicionarTutor(tutor);
        controller.adicionarSetor(setor);
        controller.associarTutorASetor(1, 1);
        controller.associarAnimalASetor(1, 1);

        boolean resultado = controller.desassociarAnimalDeSetor(1, 1);

        assertTrue(resultado);
        assertTrue(controller.buscarSetorId(1).getAnimais().isEmpty());
    }

    /**
     * Testa a regra de negócio que impede a remoção de um animal que está associado a um setor.
     */
    @Test
    @DisplayName("Não deve remover um animal se ele estiver associado a um setor")
    void naoDeveRemoverAnimalSeEstiverAssociadoAumSetor() {
        Animal animal = new Animal("Rex", "Cachorro", "Vira-lata", null, null, null);
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        controller.adicionarAnimal(animal);
        controller.adicionarSetor(setor);
        PessoaTutora tutor = new PessoaTutora("Tutor Temp", enderecoPadrao, "111", "email@temp.com");
        controller.adicionarTutor(tutor);
        controller.associarTutorASetor(1, 1);
        controller.associarAnimalASetor(1, 1);

        boolean resultado = controller.removerAnimal(1);

        assertFalse(resultado);
        assertNotNull(controller.buscarAnimalId(1));
    }

    /**
     * Testa a regra de negócio que impede a remoção de um tutor que está associado a um setor.
     */
    @Test
    @DisplayName("Não deve remover um tutor se ele estiver associado a um setor")
    void naoDeveRemoverTutorSeEstiverAssociadoAumSetor() {
        PessoaTutora tutor = new PessoaTutora("Ana", enderecoPadrao, "111", "ana@email.com");
        SetorResponsavel setor = new SetorResponsavel("Biblioteca", enderecoPadrao);
        controller.adicionarTutor(tutor);
        controller.adicionarSetor(setor);
        controller.associarTutorASetor(1, 1);

        boolean resultado = controller.removerTutor(1);

        assertFalse(resultado);
        assertNotNull(controller.buscarTutorId(1));
    }

    /**
     * Testa a regra de negócio que impede a desassociação do último tutor de um setor que possui animais.
     */
    @Test
    @DisplayName("Não deve desassociar o último tutor de um setor que contém animais")
    void naoDeveDesassociarUltimoTutorDeSetorComAnimais() {
        Animal animal = new Animal("Frajola", "Gato", "SRD", null, null, null);
        PessoaTutora tutor = new PessoaTutora("Ana", enderecoPadrao, "111", "ana@email.com");
        SetorResponsavel setor = new SetorResponsavel("Biblioteca", enderecoPadrao);
        controller.adicionarAnimal(animal);
        controller.adicionarTutor(tutor);
        controller.adicionarSetor(setor);
        controller.associarTutorASetor(1, 1);
        controller.associarAnimalASetor(1, 1);

        boolean resultado = controller.desassociarTutorASetor(1, 1);

        assertFalse(resultado);
        assertEquals(1, controller.buscarSetorId(1).getPessoasTutoras().size());
    }
}