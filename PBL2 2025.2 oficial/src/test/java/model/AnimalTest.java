package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.YearMonth;

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
 * Classe de teste para a classe {@link Animal}.
 * Verifica principalmente o comportamento do método de cálculo de idade em diferentes cenários.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
class AnimalTest {

    /**
     * Testa o cálculo da idade para um animal cujo mês de aniversário no ano corrente já passou.
     * Ex: Nasceu há 2 anos e 3 meses. Idade esperada = 2.
     */
    @Test
    @DisplayName("Deve calcular a idade corretamente quando o aniversário no ano atual já passou")
    void deveCalcularIdadeCorretamenteAniversarioPassado() {
        YearMonth dataNascimento = YearMonth.now().minusYears(2).minusMonths(3);
        Animal animal = new Animal("Rex", "Cachorro", "Vira-lata", dataNascimento, "Macho", "Disponível");
        int idadeCalculada = animal.getIdade();
        assertEquals(2, idadeCalculada);
    }

    /**
     * Testa o cálculo da idade para um animal cujo mês de aniversário no ano corrente ainda não chegou.
     * Ex: Nasceu há quase 3 anos, mas faltam 2 meses para o aniversário. Idade esperada = 2.
     */
    @Test
    @DisplayName("Deve calcular a idade corretamente quando o aniversário no ano atual ainda não chegou")
    void deveCalcularIdadeCorretamenteAniversarioFuturo() {
        YearMonth dataNascimento = YearMonth.now().minusYears(3).plusMonths(2);
        Animal animal = new Animal("Fifi", "Gato", "Siamês", dataNascimento, "Fêmea", "Em observação");
        int idadeCalculada = animal.getIdade();
        assertEquals(2, idadeCalculada);
    }

    /**
     * Testa o cálculo da idade para um animal com menos de um ano de vida.
     * A idade em anos completos deve ser 0.
     */
    @Test
    @DisplayName("Deve retornar idade 0 para um animal com menos de 1 ano")
    void deveRetornarIdadeZeroParaFilhote() {
        YearMonth dataNascimento = YearMonth.now().minusMonths(6);
        Animal filhote = new Animal("Bolinha", "Cachorro", "Poodle", dataNascimento, "Macho", "Disponível");
        int idadeCalculada = filhote.getIdade();
        assertEquals(0, idadeCalculada);
    }

    /**
     * Testa o comportamento do cálculo de idade quando a data de nascimento não foi informada (nula).
     * O método deve retornar 0 por segurança.
     */
    @Test
    @DisplayName("Deve retornar idade 0 se a data de nascimento for nula")
    void deveRetornarIdadeZeroParaDataNula() {
        Animal animalSemData = new Animal("Fantasma", "Gato", "SRD", null, "Macho", "Disponível");
        int idadeCalculada = animalSemData.getIdade();
        assertEquals(0, idadeCalculada);
    }

    /**
     * Testa o cenário onde o aniversário do animal é exatamente no mês corrente.
     * Ex: Nasceu há exatos 5 anos. Idade esperada = 5.
     */
    @Test
    @DisplayName("Deve calcular a idade corretamente para um animal que nasceu exatamente há X anos")
    void deveCalcularIdadeExata() {
        YearMonth dataNascimento = YearMonth.now().minusYears(5);
        Animal animal = new Animal("Trovão", "Cachorro", "Pastor Alemão", dataNascimento, "Macho", "Disponível");
        int idadeCalculada = animal.getIdade();
        assertEquals(5, idadeCalculada);
    }
}