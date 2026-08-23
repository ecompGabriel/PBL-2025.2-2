package controller;

import model.Endereco;
import model.PessoaTutora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

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
 * Classe de teste para a classe {@link PessoaTutoraController}.
 * Verifica o comportamento de todas as operações de CRUD (Create, Read, Update, Delete)
 * para a entidade PessoaTutora.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
class PessoaTutoraControllerTest {

    private PessoaTutoraController pessoaTutoraController;
    private Endereco enderecoPadrao;

    /**
     * Configura o ambiente de teste antes da execução de cada método.
     * Inicializa um novo controller e um endereço padrão para garantir o isolamento
     * e a consistência dos testes.
     */
    @BeforeEach
    void setUp() {
        pessoaTutoraController = new PessoaTutoraController();
        enderecoPadrao = new Endereco("Rua Teste", "Bairro Teste", "12345-678", "Cidade Teste", "TS", "Comp Teste");
    }

    /**
     * Testa a funcionalidade de adicionar uma nova pessoa tutora.
     * Verifica se o método retorna sucesso, se a lista de tutores aumenta e se o ID é atribuído.
     */
    @Test
    @DisplayName("Deve adicionar um tutor e atribuir um ID corretamente")
    void deveAdicionarTutorComSucesso() {
        PessoaTutora tutor = new PessoaTutora("Gabriel", enderecoPadrao, "75999998888", "gabriel@email.com");
        boolean resultado = pessoaTutoraController.adicionarTutor(tutor);

        assertTrue(resultado);
        assertEquals(1, pessoaTutoraController.listarTutores().size());
        assertEquals(1, tutor.getId());
    }

    /**
     * Testa a busca de uma pessoa tutora por um ID que existe.
     * Verifica se o objeto retornado não é nulo e se seus dados correspondem ao esperado.
     */
    @Test
    @DisplayName("Deve buscar um tutor pelo ID existente")
    void deveBuscarTutorPorIdExistente() {
        PessoaTutora tutorAdicionado = new PessoaTutora("Gabriel", enderecoPadrao, "75999998888", "gabriel@email.com");
        pessoaTutoraController.adicionarTutor(tutorAdicionado);

        PessoaTutora tutorEncontrado = pessoaTutoraController.buscarTutorId(1);

        assertNotNull(tutorEncontrado);
        assertEquals("Gabriel", tutorEncontrado.getNome());
        assertEquals(1, tutorEncontrado.getId());
    }

    /**
     * Testa a busca de uma pessoa tutora por um ID que não existe no sistema.
     * O resultado esperado é nulo.
     */
    @Test
    @DisplayName("Deve retornar nulo ao buscar um tutor por ID inexistente")
    void deveRetornarNuloParaIdInexistente() {
        PessoaTutora tutorEncontrado = pessoaTutoraController.buscarTutorId(99);
        assertNull(tutorEncontrado);
    }

    /**
     * Testa a funcionalidade de busca por nome, incluindo o caso onde múltiplos
     * tutores possuem o mesmo nome.
     */
    @Test
    @DisplayName("Deve encontrar tutores pelo nome")
    void deveBuscarTutoresPeloNome() {
        pessoaTutoraController.adicionarTutor(new PessoaTutora("Gabriel", enderecoPadrao, "111", "email1@test.com"));
        pessoaTutoraController.adicionarTutor(new PessoaTutora("Maria", enderecoPadrao, "222", "email2@test.com"));
        pessoaTutoraController.adicionarTutor(new PessoaTutora("Gabriel", enderecoPadrao, "333", "email3@test.com"));

        List<PessoaTutora> tutoresEncontrados = pessoaTutoraController.buscarTutorNome("Gabriel");

        assertEquals(2, tutoresEncontrados.size());
    }

    /**
     * Testa a remoção bem-sucedida de uma pessoa tutora existente.
     * Verifica se o método retorna sucesso, se a lista diminui e se o tutor não pode mais ser encontrado.
     */
    @Test
    @DisplayName("Deve remover um tutor com sucesso")
    void deveRemoverTutorExistente() {
        PessoaTutora tutor = new PessoaTutora("Gabriel", enderecoPadrao, "75999998888", "gabriel@email.com");
        pessoaTutoraController.adicionarTutor(tutor);
        assertEquals(1, pessoaTutoraController.listarTutores().size());

        boolean resultado = pessoaTutoraController.removerTutor(1);

        assertTrue(resultado);
        assertEquals(0, pessoaTutoraController.listarTutores().size());
        assertNull(pessoaTutoraController.buscarTutorId(1));
    }

    /**
     * Testa a tentativa de remoção de um tutor com um ID que não existe.
     * O método deve retornar falso.
     */
    @Test
    @DisplayName("Não deve remover um tutor com ID inexistente")
    void naoDeveRemoverTutorInexistente() {
        boolean resultado = pessoaTutoraController.removerTutor(99);
        assertFalse(resultado);
    }

    /**
     * Testa a funcionalidade de atualização de dados de uma pessoa tutora.
     * Verifica se o método retorna sucesso e se os dados são de fato alterados.
     */
    @Test
    @DisplayName("Deve atualizar os dados de um tutor corretamente")
    void deveAtualizarTutorComSucesso() {
        PessoaTutora tutorOriginal = new PessoaTutora("Gabriel", enderecoPadrao, "75999998888", "gabriel@email.com");
        pessoaTutoraController.adicionarTutor(tutorOriginal);

        Endereco novoEndereco = new Endereco("Nova Rua", "Novo Bairro", "54321-123", "Nova Cidade", "NV", "Casa");
        boolean resultado = pessoaTutoraController.atualizarTutor(1, "Gabriel Freitas", novoEndereco, "75988887777", "gfreitas@email.com");

        assertTrue(resultado);
        PessoaTutora tutorAtualizado = pessoaTutoraController.buscarTutorId(1);
        assertNotNull(tutorAtualizado);
        assertEquals("Gabriel Freitas", tutorAtualizado.getNome());
        assertEquals("gfreitas@email.com", tutorAtualizado.getEmail());
        assertEquals("Nova Rua", tutorAtualizado.getEndereco().getRua());
    }
}