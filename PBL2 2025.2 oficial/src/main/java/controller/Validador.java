package controller;

import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.regex.Pattern;

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
 * Classe utilitária que centraliza todos os métodos de validação de dados da aplicação.
 * Fornece métodos estáticos para validar formatos de e-mail, telefone e datas.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
public class Validador {

    /**
     * Padrão Regex para validar formatos de e-mail.
     * É pré-compilado como uma constante estática para otimizar a performance.
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^(?:[a-zA-Z0-9'^&+/=?`{|}~!$%*-]+(?:\\.[a-zA-Z0-9'^&+/=?`{|}~!$%-]+)*)@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$");

    /**
     * Valida um endereço de e-mail com base em um padrão Regex.
     * Verifica se o formato corresponde a um e-mail padrão (ex: usuario@dominio.com).
     *
     * @param email A String do e-mail a ser validada.
     * @return true se o e-mail tiver um formato válido, false caso contrário (incluindo nulo ou vazio).
     */
    public static boolean isEmailValido(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Valida um número de telefone, aceitando formatos comuns no Brasil (10 (telefone fixo) ou 11 dígitos).
     * O método primeiro remove todos os caracteres não numéricos antes de verificar o comprimento.
     *
     * @param telefone A String do telefone a ser validada.
     * @return true se o telefone for válido, false caso contrário.
     */
    public static boolean isTelefoneValido(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return false;
        }
        // Remove caracteres de formatação como '(', ')', '-', ' ' para analisar apenas os números.
        String apenasDigitos = telefone.replaceAll("[^0-9]", "");

        // Verifica se a quantidade de dígitos corresponde a um telefone fixo (10) ou móvel (11).
        return apenasDigitos.matches("\\d{10,11}");
    }

    /**
     * Tenta converter e validar um ano e um mês (em formato String) para um objeto YearMonth.
     * A validação falha se os valores não forem numéricos, se o mês for inválido (fora de 1-12),
     * ou se a data resultante for posterior ao mês e ano atuais.
     *
     * @param anoStr O ano em formato de String (ex: "2023").
     * @param mesStr O mês em formato de String (ex: "5").
     * @return Um objeto YearMonth se a data for válida e não for futura; null caso contrário.
     */
    public static YearMonth validarEObterYearMonth(String anoStr, String mesStr) {
        try {
            int ano = Integer.parseInt(anoStr);
            int mes = Integer.parseInt(mesStr);

            // Garante que o número do mês está no intervalo válido (1 a 12).
            if (mes < 1 || mes > 12) {
                return null;
            }

            YearMonth data = YearMonth.of(ano, mes);

            // Regra de negócio: Impede o cadastro de animais com data de nascimento futura.
            if (data.isAfter(YearMonth.now())) {
                return null;
            }

            return data;

        } catch (NumberFormatException | DateTimeException e) {
            // Captura erros de conversão (ex: "abc") ou de criação de data (ex: ano muito grande).
            return null;
        }
    }
}