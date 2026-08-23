package controller;

import model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.YearMonth;
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
 * Classe de teste para a classe {@link AnimalController}.
 * Verifica o comportamento de todas as operações de CRUD (Create, Read, Update, Delete)
 * para a entidade Animal.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
class AnimalControllerTest {

    private AnimalController animalController;

    /**
     * Configura o ambiente de teste antes da execução de cada método de teste.
     * Garante que cada teste execute com uma nova instância do controller,
     * assegurando o isolamento dos testes.
     */
    @BeforeEach
    void setUp() {
        animalController = new AnimalController();
    }

    /**
     * Testa a funcionalidade de adicionar um novo animal.
     * Verifica se o método retorna sucesso, se a lista de animais aumenta e se o ID é atribuído.
     */
    @Test
    @DisplayName("Deve adicionar um animal e atribuir um ID corretamente")
    void deveAdicionarAnimalComSucesso() {
        Animal animal = new Animal("Rex", "Cachorro", "Vira-lata", YearMonth.of(2022, 1), "Macho", "Disponível");
        boolean resultado = animalController.adicionarAnimal(animal);

        assertTrue(resultado);
        assertEquals(1, animalController.listarAnimais().size());
        assertEquals(1, animal.getId());
    }

    /**
     * Testa a busca de um animal por um ID que existe.
     * Verifica se o animal retornado não é nulo e se seus dados correspondem ao esperado.
     */
    @Test
    @DisplayName("Deve buscar um animal pelo ID existente")
    void deveBuscarAnimalPorIdExistente() {
        Animal animalAdicionado = new Animal("Rex", "Cachorro", "Vira-lata", YearMonth.of(2022, 1), "Macho", "Disponível");
        animalController.adicionarAnimal(animalAdicionado);

        Animal animalEncontrado = animalController.buscarAnimalId(1);

        assertNotNull(animalEncontrado);
        assertEquals("Rex", animalEncontrado.getNome());
        assertEquals(1, animalEncontrado.getId());
    }

    /**
     * Testa a busca de um animal por um ID que não existe no sistema.
     * O resultado esperado é nulo.
     */
    @Test
    @DisplayName("Deve retornar nulo ao buscar um animal por ID inexistente")
    void deveRetornarNuloParaIdInexistente() {
        Animal animalEncontrado = animalController.buscarAnimalId(99);
        assertNull(animalEncontrado);
    }

    /**
     * Testa a funcionalidade de busca por nome, incluindo o caso onde múltiplos
     * animais possuem o mesmo nome. A busca não deve diferenciar maiúsculas/minúsculas.
     */
    @Test
    @DisplayName("Deve encontrar animais pelo nome")
    void deveBuscarAnimaisPeloNome() {
        animalController.adicionarAnimal(new Animal("Rex", "Cachorro", "Vira-lata", null, null, null));
        animalController.adicionarAnimal(new Animal("Mimi", "Gato", "Siamês", null, null, null));
        animalController.adicionarAnimal(new Animal("Rex", "Cachorro", "Pastor Alemão", null, null, null));

        List<Animal> animaisEncontrados = animalController.buscarAnimalNome("Rex");

        assertEquals(2, animaisEncontrados.size());
    }

    /**
     * Testa a remoção bem-sucedida de um animal existente.
     * Verifica se o método retorna sucesso, se a lista diminui e se o animal não pode mais ser encontrado.
     */
    @Test
    @DisplayName("Deve remover um animal com sucesso")
    void deveRemoverAnimalExistente() {
        Animal animal = new Animal("Rex", "Cachorro", "Vira-lata", null, null, null);
        animalController.adicionarAnimal(animal);
        assertEquals(1, animalController.listarAnimais().size());

        boolean resultado = animalController.removerAnimal(1);

        assertTrue(resultado);
        assertEquals(0, animalController.listarAnimais().size());
        assertNull(animalController.buscarAnimalId(1));
    }

    /**
     * Testa a tentativa de remoção de um animal com um ID que não existe.
     * O método deve retornar falso, indicando que a operação falhou.
     */
    @Test
    @DisplayName("Não deve remover um animal com ID inexistente")
    void naoDeveRemoverAnimalInexistente() {
        boolean resultado = animalController.removerAnimal(99);
        assertFalse(resultado);
    }

    /**
     * Testa a funcionalidade de atualização de dados de um animal.
     * Verifica se o método retorna sucesso e se os dados do animal são de fato alterados.
     */
    @Test
    @DisplayName("Deve atualizar os dados de um animal corretamente")
    void deveAtualizarAnimalComSucesso() {
        Animal animalOriginal = new Animal("Rex", "Cachorro", "Vira-lata", YearMonth.of(2022, 1), "Macho", "Disponível");
        animalController.adicionarAnimal(animalOriginal);

        boolean resultado = animalController.atualizarAnimal(1, "Bobby", "Cachorro", "Golden Retriever", YearMonth.of(2023, 5), "Macho", "Adotado");

        assertTrue(resultado);
        Animal animalAtualizado = animalController.buscarAnimalId(1);
        assertNotNull(animalAtualizado);
        assertEquals("Bobby", animalAtualizado.getNome());
        assertEquals("Adotado", animalAtualizado.getSituacaoAtual());
        assertEquals(YearMonth.of(2023, 5), animalAtualizado.getDataDeNascimento());
    }
}