package model;

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
 * Classe de teste para a classe {@link Endereco}.
 * Verifica a correta criação de instâncias e a modificação de seus atributos através dos setters.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
class EnderecoTest {

    /**
     * Testa se o construtor da classe Endereco atribui corretamente todos os
     * parâmetros aos seus respectivos atributos e se os getters retornam esses valores.
     */
    @Test
    @DisplayName("Deve criar um endereço e verificar se todos os dados foram atribuídos corretamente")
    void deveCriarEnderecoEConsultarDadosCorretamente() {
        Endereco endereco = new Endereco(
                "Avenida Transnordestina",
                "Campus",
                "44036-900",
                "Feira de Santana",
                "BA",
                "Próximo ao Módulo 7"
        );
        assertNotNull(endereco);
        assertEquals("Avenida Transnordestina", endereco.getRua());
        assertEquals("Campus", endereco.getBairro());
        assertEquals("44036-900", endereco.getCep());
        assertEquals("Feira de Santana", endereco.getCidade());
        assertEquals("BA", endereco.getEstado());
        assertEquals("Próximo ao Módulo 7", endereco.getComplemento());
    }

    /**
     * Testa se os métodos setters da classe Endereco alteram os atributos
     * corretamente, sem afetar os outros campos.
     */
    @Test
    @DisplayName("Deve alterar um dado com um setter e verificar a mudança")
    void deveAlterarDadoComSetterCorretamente() {
        Endereco endereco = new Endereco("Rua Antiga", "Bairro Antigo", "111", "Cidade Antiga", "SP", "Comp Antigo");
        endereco.setRua("Rua Nova");
        endereco.setCidade("Cidade Nova");
        assertEquals("Rua Nova", endereco.getRua());
        assertEquals("Cidade Nova", endereco.getCidade());
        assertEquals("Bairro Antigo", endereco.getBairro());
    }
}