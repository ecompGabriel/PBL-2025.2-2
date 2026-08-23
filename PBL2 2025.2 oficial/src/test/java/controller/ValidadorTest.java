package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
 * Classe de teste para a classe utilitária {@link Validador}.
 * Verifica o comportamento dos métodos de validação de e-mail, telefone e YearMonth
 * com uma variedade de entradas válidas e inválidas.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
class ValidadorTest {

    /**
     * Testa o método isEmailValido com um conjunto de endereços de e-mail que
     * devem ser considerados válidos.
     * @param email Um e-mail válido fornecido pelo @ValueSource.
     */
    @ParameterizedTest
    @ValueSource(strings = {"teste@email.com", "nome.sobrenome@dominio.co.br", "email123@provedor.net"})
    @DisplayName("Deve retornar true para e-mails válidos")
    void deveValidarEmailsCorretos(String email) {
        assertTrue(Validador.isEmailValido(email));
    }

    /**
     * Testa o método isEmailValido com um conjunto de entradas que devem ser
     * consideradas inválidas, incluindo formatos incorretos e strings vazias.
     * @param email Um e-mail inválido fornecido pelo @ValueSource.
     */
    @ParameterizedTest
    @ValueSource(strings = {"emailsemarroba.com", "@dominio.com", "email@.com", "", " ", "email@dominio"})
    @DisplayName("Deve retornar false para e-mails inválidos")
    void deveRejeitarEmailsInvalidos(String email) {
        assertFalse(Validador.isEmailValido(email));
    }

    /**
     * Testa especificamente o caso de uma entrada nula para a validação de e-mail.
     * O resultado esperado é falso.
     */
    @Test
    @DisplayName("Deve retornar false para e-mail nulo")
    void deveRejeitarEmailNulo() {
        assertFalse(Validador.isEmailValido(null));
    }

    /**
     * Testa o método isTelefoneValido com um conjunto de números de telefone que
     * devem ser considerados válidos, incluindo diferentes formatações.
     * @param telefone Um telefone válido fornecido pelo @ValueSource.
     */
    @ParameterizedTest
    @ValueSource(strings = {"75999998888", "7532214455", "(75) 99999-8888", "75 3221-4455"})
    @DisplayName("Deve retornar true para telefones válidos")
    void deveValidarTelefonesCorretos(String telefone) {
        assertTrue(Validador.isTelefoneValido(telefone));
    }

    /**
     * Testa o método isTelefoneValido com um conjunto de entradas que devem ser
     * consideradas inválidas, como strings curtas, longas ou não numéricas.
     * @param telefone Um telefone inválido fornecido pelo @ValueSource.
     */
    @ParameterizedTest
    @ValueSource(strings = {"12345", "123456789012", "abcdefghij", "", " "})
    @DisplayName("Deve retornar false para telefones inválidos")
    void deveRejeitarTelefonesInvalidos(String telefone) {
        assertFalse(Validador.isTelefoneValido(telefone));
    }

    /**
     * Testa especificamente o caso de uma entrada nula para a validação de telefone.
     * O resultado esperado é falso.
     */
    @Test
    @DisplayName("Deve retornar false para telefone nulo")
    void deveRejeitarTelefoneNulo() {
        assertFalse(Validador.isTelefoneValido(null));
    }

    /**
     * Testa a conversão bem-sucedida de strings de ano e mês para um objeto YearMonth,
     * usando uma data válida no passado.
     */
    @Test
    @DisplayName("Deve retornar um YearMonth válido para data no passado")
    void deveCriarYearMonthParaDataValida() {
        YearMonth dataEsperada = YearMonth.of(2023, 12);
        YearMonth dataRecebida = Validador.validarEObterYearMonth("2023", "12");
        assertNotNull(dataRecebida);
        assertEquals(dataEsperada, dataRecebida);
    }

    /**
     * Testa a regra de negócio que impede a criação de um YearMonth para uma data futura.
     * O resultado esperado é nulo.
     */
    @Test
    @DisplayName("Deve retornar nulo para uma data no futuro")
    void deveRejeitarDataFutura() {
        YearMonth dataFutura = YearMonth.now().plusMonths(1);
        String ano = String.valueOf(dataFutura.getYear());
        String mes = String.valueOf(dataFutura.getMonthValue());

        YearMonth dataRecebida = Validador.validarEObterYearMonth(ano, mes);
        assertNull(dataRecebida);
    }

    /**
     * Testa a validação de mês, garantindo que valores fora do intervalo 1-12 sejam rejeitados.
     * O resultado esperado é nulo.
     */
    @Test
    @DisplayName("Deve retornar nulo para mês inválido")
    void deveRejeitarMesInvalido() {
        assertNull(Validador.validarEObterYearMonth("2023", "13"));
        assertNull(Validador.validarEObterYearMonth("2023", "0"));
    }

    /**
     * Testa a robustez do método contra entradas que não são numéricas,
     * garantindo que exceções são tratadas e o resultado é nulo.
     */
    @Test
    @DisplayName("Deve retornar nulo para entrada não numérica")
    void deveRejeitarEntradaNaoNumerica() {
        assertNull(Validador.validarEObterYearMonth("abc", "12"));
        assertNull(Validador.validarEObterYearMonth("2023", "dez"));
        assertNull(Validador.validarEObterYearMonth("dois mil", "doze"));
    }
}