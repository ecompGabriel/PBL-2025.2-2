package controller;

import model.Endereco;
import model.SetorResponsavel;
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
 * Classe de teste para a classe {@link SetorResponsavelController}.
 * Verifica o comportamento de todas as operações de CRUD (Create, Read, Update, Delete)
 * para a entidade SetorResponsavel.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
class SetorResponsavelControllerTest {

    private SetorResponsavelController setorResponsavelController;
    private Endereco enderecoPadrao;

    /**
     * Configura o ambiente de teste antes da execução de cada método.
     * Inicializa um novo controller e um endereço padrão para garantir o isolamento
     * e a consistência dos testes.
     */
    @BeforeEach
    void setUp() {
        setorResponsavelController = new SetorResponsavelController();
        enderecoPadrao = new Endereco("Avenida Transnordestina", "Campus", "44036-900", "Feira de Santana", "BA", "UEFS");
    }

    /**
     * Testa a funcionalidade de adicionar um novo setor.
     * Verifica se o método retorna sucesso, se o ID é atribuído e se as listas internas são inicializadas.
     */
    @Test
    @DisplayName("Deve adicionar um setor e atribuir um ID corretamente")
    void deveAdicionarSetorComSucesso() {
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        boolean resultado = setorResponsavelController.adicionarSetor(setor);

        assertTrue(resultado);
        assertEquals(1, setorResponsavelController.listarSetores().size());
        assertEquals(1, setor.getId());
        assertNotNull(setor.getAnimais());
        assertNotNull(setor.getPessoasTutoras());
    }

    /**
     * Testa a busca de um setor por um ID que existe.
     * Verifica se o objeto retornado não é nulo e se seus dados correspondem ao esperado.
     */
    @Test
    @DisplayName("Deve buscar um setor pelo ID existente")
    void deveBuscarSetorPorIdExistente() {
        SetorResponsavel setorAdicionado = new SetorResponsavel("Módulo 3", enderecoPadrao);
        setorResponsavelController.adicionarSetor(setorAdicionado);

        SetorResponsavel setorEncontrado = setorResponsavelController.buscarSetorId(1);

        assertNotNull(setorEncontrado);
        assertEquals("Módulo 3", setorEncontrado.getNome());
        assertEquals(1, setorEncontrado.getId());
    }

    /**
     * Testa a busca de um setor por um ID que não existe no sistema.
     * O resultado esperado é nulo.
     */
    @Test
    @DisplayName("Deve retornar nulo ao buscar um setor por ID inexistente")
    void deveRetornarNuloParaIdInexistente() {
        SetorResponsavel setorEncontrado = setorResponsavelController.buscarSetorId(99);
        assertNull(setorEncontrado);
    }

    /**
     * Testa a funcionalidade de busca por nome, incluindo o caso onde múltiplos
     * setores possuem o mesmo nome.
     */
    @Test
    @DisplayName("Deve encontrar setores pelo nome")
    void deveBuscarSetoresPeloNome() {
        setorResponsavelController.adicionarSetor(new SetorResponsavel("Módulo 3", enderecoPadrao));
        setorResponsavelController.adicionarSetor(new SetorResponsavel("Biotério", enderecoPadrao));
        setorResponsavelController.adicionarSetor(new SetorResponsavel("Módulo 3", enderecoPadrao));

        List<SetorResponsavel> setoresEncontrados = setorResponsavelController.buscarSetorNome("Módulo 3");

        assertEquals(2, setoresEncontrados.size());
    }

    /**
     * Testa a remoção bem-sucedida de um setor existente.
     * Verifica se o método retorna sucesso, se a lista diminui e se o setor não pode mais ser encontrado.
     */
    @Test
    @DisplayName("Deve remover um setor com sucesso")
    void deveRemoverSetorExistente() {
        SetorResponsavel setor = new SetorResponsavel("Biotério", enderecoPadrao);
        setorResponsavelController.adicionarSetor(setor);
        assertEquals(1, setorResponsavelController.listarSetores().size());

        boolean resultado = setorResponsavelController.removerSetor(1);

        assertTrue(resultado);
        assertEquals(0, setorResponsavelController.listarSetores().size());
        assertNull(setorResponsavelController.buscarSetorId(1));
    }

    /**
     * Testa a tentativa de remoção de um setor com um ID que não existe.
     * O método deve retornar falso.
     */
    @Test
    @DisplayName("Não deve remover um setor com ID inexistente")
    void naoDeveRemoverSetorInexistente() {
        boolean resultado = setorResponsavelController.removerSetor(99);
        assertFalse(resultado);
    }

    /**
     * Testa a funcionalidade de atualização de dados de um setor.
     * Verifica se o método retorna sucesso e se os dados são de fato alterados.
     */
    @Test
    @DisplayName("Deve atualizar os dados de um setor corretamente")
    void deveAtualizarSetorComSucesso() {
        SetorResponsavel setorOriginal = new SetorResponsavel("Módulo Antigo", enderecoPadrao);
        setorResponsavelController.adicionarSetor(setorOriginal);

        Endereco novoEndereco = new Endereco("Rua Nova", "Bairro Novo", "11111-222", "Cidade Nova", "NV", "Perto da praça");
        boolean resultado = setorResponsavelController.atualizarSetor(1, "Módulo Novo", novoEndereco);

        assertTrue(resultado);
        SetorResponsavel setorAtualizado = setorResponsavelController.buscarSetorId(1);
        assertNotNull(setorAtualizado);
        assertEquals("Módulo Novo", setorAtualizado.getNome());
        assertEquals("Rua Nova", setorAtualizado.getEndereco().getRua());
    }
}