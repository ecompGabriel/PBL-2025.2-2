package controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

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
 * Controller responsável por gerenciar a persistência dos dados da aplicação.
 * Utiliza a biblioteca Jackson para serializar/desserializar o estado completo do
 * controller principal (`UefsAnimaisController`) em um arquivo JSON.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
public class PersistenciaController {

    /**
     * Nome constante do arquivo onde os dados da aplicação são armazenados.
     */
    private static final String NOME_ARQUIVO = "dados_app_animais.json";

    /**
     * Objeto principal da biblioteca Jackson, responsável por mapear Objetos Java para JSON e vice-versa.
     */
    private ObjectMapper objectMapper;

    /**
     * Construtor da classe PersistenciaController.
     * Inicializa e configura o ObjectMapper com as definições necessárias para o projeto.
     */
    public PersistenciaController() {
        this.objectMapper = new ObjectMapper();
        // Habilita a formatação "pretty print" para que o arquivo JSON seja legível.
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Registra o módulo que ensina o Jackson a lidar com classes do pacote java.time, como YearMonth.
        objectMapper.registerModule(new JavaTimeModule());
        // Configura o Jackson para acessar diretamente os atributos (fields) privados, sem a necessidade de getters.
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    /**
     * Salva o estado atual do controller principal em um arquivo JSON.
     * Serializa o objeto UefsAnimaisController e todo o seu conteúdo.
     *
     * @param controller O objeto do controller principal que contém todos os dados da aplicação.
     */
    public void salvarDados(UefsAnimaisController controller) {
        try {
            objectMapper.writeValue(new File(NOME_ARQUIVO), controller);
            System.out.println("Dados salvos com sucesso em " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    /**
     * Carrega o estado da aplicação a partir do arquivo JSON.
     * Se o arquivo existir e for válido, desserializa seu conteúdo para um objeto UefsAnimaisController.
     * Caso contrário, retorna uma nova instância limpa do controller.
     *
     * @return Uma instância de UefsAnimaisController com os dados carregados ou uma nova instância vazia.
     */
    public UefsAnimaisController carregarDados() {
        File arquivo = new File(NOME_ARQUIVO);
        // Verifica se o arquivo de dados existe e não está vazio antes de tentar lê-lo.
        if (arquivo.exists() && arquivo.length() > 0) {
            try {
                UefsAnimaisController controller = objectMapper.readValue(arquivo, UefsAnimaisController.class);
                System.out.println("Dados carregados com sucesso de " + NOME_ARQUIVO);
                return controller;
            } catch (IOException e) {
                System.err.println("Erro ao carregar os dados. Um novo arquivo será criado: " + e.getMessage());
            }
        }
        // Retorna um controller novo se o arquivo não existir ou se ocorrer um erro na leitura.
        return new UefsAnimaisController();
    }
}