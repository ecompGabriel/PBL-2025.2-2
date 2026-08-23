package View;

import controller.UefsAnimaisController;
import controller.Validador;
import controller.PersistenciaController;
import model.*;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.time.YearMonth;

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
 * Classe principal da aplicação, responsável por toda a interação com o usuário (View).
 * Exibe os menus, coleta as entradas do usuário e direciona as ações para o controller.
 *
 * @author Gabriel Oliveira de Freitas
 * @version 1.0 (12/10/2025)
 */
public class Main {

    private static UefsAnimaisController controller;
    private static final PersistenciaController persistenciaController = new PersistenciaController();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Ponto de entrada da aplicação.
     * Define o ciclo de vida do programa: carregar dados, executar o menu principal e salvar dados ao sair.
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // Carrega os dados do arquivo JSON ao iniciar o programa.
        controller = persistenciaController.carregarDados();
        // Exibe o menu principal e interage com o usuário.
        exibirMenu();
        // Salva os dados no arquivo JSON ao encerrar o programa.
        persistenciaController.salvarDados(controller);
    }

    /**
     * Lê uma string do console, garantindo que não seja vazia ou em branco.
     * @param mensagem A mensagem a ser exibida para o usuário.
     * @return A string lida e validada.
     */
    private static String lerStringObrigatoria(String mensagem) {
        String entrada;
        do {
            System.out.print(mensagem);
            entrada = scanner.nextLine();
            if (entrada.isBlank()) {
                System.out.println("Não deixa essa informação em branco. Digite novamente:");
            }
        } while (entrada.isBlank());
        return entrada.trim();
    }

    /**
     * Lê um número inteiro do console, tratando entradas não numéricas.
     * @param entrada A mensagem a ser exibida para o usuário.
     * @return O número inteiro lido.
     */
    private static int lerInteiro(String entrada) {
        Integer numero = null;
        do {
            System.out.print(entrada);
            String input = scanner.nextLine();

            try {
                numero = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite apenas um número.");
            }
        } while (numero == null);
        return numero;
    }

    /**
     * Coordena a coleta de dados para um novo Endereco.
     * @return Um novo objeto Endereco preenchido com os dados do usuário.
     */
    private static Endereco acoesEndereco() {
        System.out.println("Digite o endereco: ");
        System.out.println();
        String rua = lerStringObrigatoria("Rua: ");
        String bairro = lerStringObrigatoria("Bairro: ");
        String cep = lerStringObrigatoria("CEP: ");
        String cidade = lerStringObrigatoria("Cidade: ");
        String estado = lerStringObrigatoria("Estado: ");
        String complemento = lerStringObrigatoria("Complemento: ");

        Endereco novoEndereco = new Endereco(rua, bairro, cep, cidade, estado, complemento);

        System.out.println("\nEndereço cadastrado com sucesso!");
        return novoEndereco;
    }

    /**
     * Exibe um menu para o usuário selecionar a situação atual de um animal.
     * @return A string correspondente à situação escolhida.
     */
    private static String selecionarSituacaoAnimal() {
        String situacao = null;
        do {
            System.out.println("\nSelecione a situação atual do animal:");
            System.out.println("1 - Em observação");
            System.out.println("2 - Disponível para adoção");
            System.out.println("3 - Em tratamento");
            int opcao = lerInteiro("Opção: ");

            switch (opcao) {
                case 1:
                    situacao = "Em observação";
                    break;
                case 2:
                    situacao = "Disponível para adoção";
                    break;
                case 3:
                    situacao = "Em tratamento";
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (situacao == null);
        return situacao;
    }

    /**
     * Exibe um menu para o usuário selecionar a espécie de um animal.
     * @return A string correspondente à espécie escolhida.
     */
    private static String selecionarEspecieAnimal() {
        String especie = null;
        do {
            System.out.println("\nSelecione a espécie do animal:");
            System.out.println("1 - Cachorro");
            System.out.println("2 - Gato");
            System.out.println("3 - Outro");
            int opcao = lerInteiro("Opção: ");

            switch (opcao) {
                case 1:
                    especie = "Cachorro";
                    break;
                case 2:
                    especie = "Gato";
                    break;
                case 3:
                    especie = "Outro";
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (especie == null);
        return especie;
    }

    /**
     * Exibe um menu para o usuário selecionar o sexo de um animal.
     * @return A string correspondente ao sexo escolhido.
     */
    private static String selecionarSexoAnimal() {
        String sexo = null;
        do {
            System.out.println("\nSelecione o sexo do animal:");
            System.out.println("1 - Macho");
            System.out.println("2 - Fêmea");
            int opcao = lerInteiro("Opção: ");

            switch (opcao) {
                case 1:
                    sexo = "Macho";
                    break;
                case 2:
                    sexo = "Fêmea";
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (sexo == null);
        return sexo;
    }

    /**
     * Gerencia o fluxo de cadastro de um novo animal, coletando todos os dados necessários.
     */
    private static void cadastrarAnimal(){
        System.out.println("\nDigite as informações do animal");
        String nome = lerStringObrigatoria("Nome: ");
        String especie = selecionarEspecieAnimal();
        System.out.println("Raça (se aplivável):");
        String raca = scanner.nextLine();
        YearMonth dataNascimento = null;
        do {
            System.out.println("Digite a data de nascimento aproximada do animal:");
            System.out.print("Ano: ");
            String anoStr = scanner.nextLine();
            System.out.print("Mês (número): ");
            String mesStr = scanner.nextLine();
            dataNascimento = Validador.validarEObterYearMonth(anoStr, mesStr);

            if (dataNascimento == null) {
                System.out.println("Data inválida. Verifique os números digitados e se a data não está no futuro.");
            }
        } while (dataNascimento == null);

        String sexo = selecionarSexoAnimal();
        String situacao = selecionarSituacaoAnimal();

        Animal novoAnimal = new Animal(nome, especie, raca, dataNascimento, sexo, situacao);
        if (controller.adicionarAnimal(novoAnimal)) {
            System.out.println("\nAnimal cadastrado com sucesso!");
        } else {
            System.out.println("\nFalha ao cadastrar o animal.");
        }
    }

    /**
     * Gerencia o fluxo de cadastro de uma nova pessoa tutora.
     */
    private static void cadastrarTutor() {
        System.out.println("Digite as informações do tutor:");
        String nome = lerStringObrigatoria("Nome: ");
        System.out.println("Endereço:");
        Endereco endereco = acoesEndereco();
        String telefone;
        do {
            System.out.print("Telefone (com DDD): ");
            telefone = scanner.nextLine();
            if (!Validador.isTelefoneValido(telefone)) {
                System.out.println("Formato de telefone inválido. Deve ter 10 ou 11 dígitos. Digite novamente: ");
            }
        } while (!Validador.isTelefoneValido(telefone));
        String email;
        do {
            System.out.println("Email: ");
            email = scanner.nextLine();
            if (!Validador.isEmailValido(email)) {
                System.out.println("Formato de email inválido. Digite novamente: ");
            }
        } while (!Validador.isEmailValido(email));

        PessoaTutora novoTutor = new PessoaTutora(nome, endereco, telefone, email);
        if (controller.adicionarTutor(novoTutor)) {
            System.out.println("\nTutor cadastrado com sucesso!");
        } else {
            System.out.println("\nFalha ao cadastrar o tutor.");
        }
    }

    /**
     * Gerencia o fluxo de cadastro de um novo setor responsável.
     */
    private static void cadastrarSetor(){
        System.out.println("Digite as informações do setor:");
        String nome = lerStringObrigatoria("Nome: ");
        System.out.println("Endereço:");
        Endereco endereco = acoesEndereco();

        SetorResponsavel novoSetor = new SetorResponsavel(nome, endereco);
        if (controller.adicionarSetor(novoSetor)) {
            System.out.println("\nSetor responsável cadastrado com sucesso!");
        } else {
            System.out.println("\nFalha ao cadastrar o setor responsável.");
        }
    }

    /**
     * Solicita um ID ao usuário e exibe os dados do animal correspondente.
     */
    private static void buscarAnimalId(){
        int id = lerInteiro("Digite o ID do animal: ");
        Animal animal = controller.buscarAnimalId(id);

        if (animal != null){
            System.out.println(animal);
        } else{
            System.out.println("Nenhum animal com o ID " + id + " foi encontrado.");
        }
    }

    /**
     * Solicita um nome ao usuário e exibe os dados de todos os animais com aquele nome.
     */
    private static void buscarAnimalNome(){
        String nome = lerStringObrigatoria("Digite o nome do animal: ");
        List<Animal> animais = controller.buscarAnimalNome(nome);

        if (animais.isEmpty()){
            System.out.println("Não foram encontrados animais com esse nome.");
        } else{
            for(Animal animal : animais){
                System.out.println("--------------------------");
                System.out.println(animal);
            }
        }
        System.out.println("--------------------------");
    }

    /**
     * Exibe o submenu de busca de animais (por ID ou por Nome).
     */
    private static void menuBuscaAnimal() {
        System.out.println("Escolha o método de busca:\n1 - Buscar por ID\n2 - Buscar por nome\n0 - Voltar");
        int opcao = lerInteiro("Escolha uma opção: ");

        switch (opcao) {
            case 1:
                buscarAnimalId();
                break;
            case 2:
                buscarAnimalNome();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    /**
     * Solicita um ID ao usuário e exibe os dados do tutor correspondente.
     */
    private static void buscarTutorId(){
        int id = lerInteiro("Digite o ID do tutor: ");
        PessoaTutora tutor = controller.buscarTutorId(id);

        if (tutor != null){
            System.out.println(tutor);
        } else{
            System.out.println("Nenhum tutor com o ID " + id + " foi encontrado.");
        }
    }

    /**
     * Solicita um nome ao usuário e exibe os dados de todos os tutores com aquele nome.
     */
    private static void buscarTutorNome(){
        String nome = lerStringObrigatoria("Digite o nome do tutor: ");
        List<PessoaTutora> tutores = controller.buscarTutorNome(nome);

        if (tutores.isEmpty()){
            System.out.println("Não foram encontrados tutores com esse nome.");
        } else{
            for(PessoaTutora tutor : tutores){
                System.out.println("--------------------------");
                System.out.println(tutor);
            }
        }
        System.out.println("--------------------------");
    }

    /**
     * Exibe o submenu de busca de tutores (por ID ou por Nome).
     */
    private static void menuBuscaTutor() {
        System.out.println("Escolha o método de busca:\n1 - Buscar por ID\n2 - Buscar por nome\n0 - Voltar");
        int opcao = lerInteiro("Escolha uma opção: ");

        switch (opcao) {
            case 1:
                buscarTutorId();
                break;
            case 2:
                buscarTutorNome();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    /**
     * Solicita um ID ao usuário e exibe os dados do setor correspondente.
     */
    private static void buscarSetorId(){
        int id = lerInteiro("Digite o ID do setor: ");
        SetorResponsavel setor = controller.buscarSetorId(id);

        if (setor != null){
            System.out.println(setor);
        } else{
            System.out.println("Nenhum setor com o ID " + id + " foi encontrado.");
        }
    }

    /**
     * Solicita um nome ao usuário e exibe os dados de todos os setores com aquele nome.
     */
    private static void buscarSetorNome(){
        String nome = lerStringObrigatoria("Digite o nome do setor: ");
        List<SetorResponsavel> setores = controller.buscarSetorNome(nome);

        if (setores.isEmpty()){
            System.out.println("Não foram encontrados setor com esse nome.");
        } else{
            for(SetorResponsavel setor : setores){
                System.out.println("--------------------------");
                System.out.println(setor);
            }
        }
        System.out.println("--------------------------");
    }

    /**
     * Exibe o submenu de busca de setores (por ID ou por Nome).
     */
    private static void menuBuscaSetor() {
        System.out.println("Escolha o método de busca:\n1 - Buscar por ID\n2 - Buscar por nome\n0 - Voltar");
        int opcao = lerInteiro("Escolha uma opção: ");

        switch (opcao) {
            case 1:
                buscarSetorId();
                break;
            case 2:
                buscarSetorNome();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    /**
     * Exibe uma lista com todos os animais cadastrados no sistema.
     */
    private static void relacaoCompletaAnimais(){
        System.out.println("\nRelação completa de animais:");
        List<Animal> animais = controller.listarAnimais();
        if(!animais.isEmpty()){
            for(Animal animal : animais){
                System.out.println("--------------------------");
                System.out.println(animal);
            }
        } else{
            System.out.println("Não existem animais cadastrados no sistema.");
        }
    }

    /**
     * Exibe um relatório de animais agrupados por cada setor.
     */
    private static void relacaoAnimaisPorSetor() {
        System.out.println("\nRelatório de Animais por Setor");
        Map<String, List<Animal>> relatorio = controller.gerarRelatorioAnimaisPorSetor();

        if (relatorio.isEmpty()) {
            System.out.println("Não há setores com animais cadastrados para exibir.");
            return;
        }

        for (Map.Entry<String, List<Animal>> entry : relatorio.entrySet()) {
            String nomeSetor = entry.getKey();
            List<Animal> animaisDoSetor = entry.getValue();
            System.out.println("\n--------------------------");
            System.out.println("Setor: " + nomeSetor + "\n");

            if (animaisDoSetor.isEmpty()) {
                System.out.println("Nenhum animal neste setor.");
            } else {
                for (Animal animal : animaisDoSetor) {
                    System.out.println(animal);
                }
            }
        }
    }

    /**
     * Exibe um relatório de responsabilidade, mostrando para cada setor os seus tutores e animais.
     */
    private static void relacaoAnimaisPorTutor() {
        System.out.println("\nRelatório de Animias por Tutor");
        List<SetorResponsavel> setores = controller.listarSetores();

        if (setores.isEmpty()) {
            System.out.println("Não há setores cadastrados para exibir.");
            return;
        }

        for (SetorResponsavel setor : setores) {
            System.out.println("\n--------------------------");
            System.out.println("Setor: " + setor.getNome() + " (ID: " + setor.getId() + ")");

            List<PessoaTutora> tutoresDoSetor = setor.getPessoasTutoras();
            System.out.println("\nTutores Responsáveis:");
            if (tutoresDoSetor.isEmpty()) {
                System.out.println("  - Nenhum tutor associado a este setor.");
            } else {
                for (PessoaTutora tutor : tutoresDoSetor) {
                    System.out.println("  - " + tutor.getNome() + " (ID: " + tutor.getId() + ")");
                }
            }

            List<Animal> animaisDoSetor = setor.getAnimais();
            System.out.println("\nAnimais no Setor:");
            if (animaisDoSetor.isEmpty()) {
                System.out.println("  - Nenhum animal associado a este setor.");
            } else {
                for (Animal animal : animaisDoSetor) {
                    System.out.println(animal);
                }
            }
        }
    }

    /**
     * Exibe uma lista com todos os tutores cadastrados no sistema.
     */
    private static void relacaoCompletaTutores(){
        System.out.println("\nRelação completa de tutores:");
        List<PessoaTutora> tutores = controller.listarTutores();
        if(!tutores.isEmpty()){
            for(PessoaTutora tutor : tutores){
                System.out.println("--------------------------");
                System.out.println(tutor);
            }
        } else{
            System.out.println("Não existem tutores cadastrados no sistema.");
        }
    }

    /**
     * Exibe uma lista com todos os setores cadastrados no sistema.
     */
    private static void relacaoCompletaSetor(){
        System.out.println("\nRelação completa de setores:");
        List<SetorResponsavel> setores = controller.listarSetores();
        if(!setores.isEmpty()){
            for(SetorResponsavel setor : setores){
                System.out.println("--------------------------");
                System.out.println(setor);
            }
        } else{
            System.out.println("Não existem setores cadastrados no sistema.");
        }
    }

    /**
     * Gerencia o fluxo para deletar um animal, incluindo a confirmação do usuário.
     */
    private static void deletarAnimal() {
        int id = lerInteiro("\nDigite o ID do animal que será deletado: ");
        System.out.print("Tem certeza que quer deletar esse animal do sistema? 1(sim)/2(não): ");
        int confirmacao = lerInteiro("");
        switch (confirmacao){
            case 1:
                if (controller.removerAnimal(id)) {
                    System.out.println("Animal deletado com sucesso!");
                } else {
                    System.out.println("Não foi possível deletá-lo. Verifique se o ID do animal está correto e se o animal não está associado a algum setor.");
                }
                break;
            case 2:
                System.out.println("Remoção cancelada!");
                break;
            default:
                System.out.println("\nOpção inexistente. Tente novamente.\n");
        }
    }

    /**
     * Gerencia o fluxo para deletar um tutor, incluindo a confirmação do usuário.
     */
    private static void deletarTutor() {
        int id = lerInteiro("\nDigite o ID do tutor que será deletado: ");
        System.out.print("Tem certeza que quer deletar esse tutor do sistema? 1(sim)/2(não): ");
        int confirmacao = lerInteiro("");
        switch (confirmacao){
            case 1:
                if (controller.removerTutor(id)) {
                    System.out.println("Tutor deletado com sucesso!");
                } else {
                    System.out.println("Não foi possível deletá-lo. Verifique se o ID do tutor está correto e se o tutor não está associado a algum setor.");
                }
                break;
            case 2:
                System.out.println("Remoção cancelada!");
                break;
            default:
                System.out.println("\nOpção inexistente. Tente novamente.\n");
        }
    }

    /**
     * Gerencia o fluxo para deletar um setor, incluindo a confirmação do usuário.
     */
    private static void deletarSetor() {
        int id = lerInteiro("\nDigite o ID do setor que será deletado: ");
        System.out.print("Tem certeza que quer deletar esse setor do sistema? 1(sim)/2(não): ");
        int confirmacao = lerInteiro("");
        switch (confirmacao){
            case 1:
                if (controller.removerSetor(id)) {
                    System.out.println("Setor deletado com sucesso!");
                } else {
                    System.out.println("Não foi possível deletá-lo. Verifique se o ID do setor está correto e se o setor não possui animais ou tutores associados..");
                }
                break;
            case 2:
                System.out.println("Remoção cancelada!");
                break;
            default:
                System.out.println("\nOpção inexistente. Tente novamente.\n");
        }
    }

    /**
     * Gerencia o fluxo de atualização dos dados de um animal existente.
     */
    private static void atualizarAnimal() {
        int id = lerInteiro("Digite o ID do animal que deseja atualizar: ");
        Animal animalBase = controller.buscarAnimalId(id);

        if (animalBase == null) {
            System.out.println("Animal com o ID informado não foi encontrado.");
            return;
        }

        System.out.println("\nAnimal encontrado: " + animalBase.getNome());
        System.out.println("Digite as novas informações (ou pressione ENTER para manter a atual).");

        System.out.print("Novo nome (" + animalBase.getNome() + "): ");
        String nome = scanner.nextLine();
        if (nome.isBlank()) {
            nome = animalBase.getNome();
        }

        String especie = animalBase.getEspecie();
        System.out.print("Deseja alterar a espécie? (" + especie + ") (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            especie = selecionarEspecieAnimal();
        }

        System.out.print("Nova raça (" + animalBase.getRaca() + "): ");
        String raca = scanner.nextLine();
        if (raca.isBlank()) {
            raca = animalBase.getRaca();
        }

        YearMonth dataNascimento = animalBase.getDataDeNascimento();
        System.out.print("Deseja alterar a data de nascimento? (" + dataNascimento + ") (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            dataNascimento = null;
            do {
                System.out.println("Digite a nova data de nascimento aproximada:");
                System.out.print("Ano: ");
                String anoStr = scanner.nextLine();
                System.out.print("Mês (número): ");
                String mesStr = scanner.nextLine();
                dataNascimento = Validador.validarEObterYearMonth(anoStr, mesStr);
                if(dataNascimento == null) {
                    System.out.println("Data inválida. Tente novamente.");
                }
            } while(dataNascimento == null);
        }

        String sexo = animalBase.getSexo();
        System.out.print("Deseja alterar o sexo? (" + sexo + ") (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            sexo = selecionarSexoAnimal();
        }

        String situacao = animalBase.getSituacaoAtual();
        System.out.print("Deseja alterar a situação? (" + situacao + ") (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            situacao = selecionarSituacaoAnimal();
        }

        if (controller.atualizarAnimal(id, nome, especie, raca, dataNascimento, sexo, situacao)) {
            System.out.println("\nCadastro do animal atualizado com sucesso!");
        } else {
            System.out.println("\nFalha ao atualizar o cadastro.");
        }
    }

    /**
     * Gerencia o fluxo de atualização dos dados de um tutor existente.
     */
    private static void atualizarTutor() {
        int id = lerInteiro("Digite o ID do tutor que deseja atualizar: ");

        PessoaTutora tutorBase = controller.buscarTutorId(id);

        if (tutorBase == null) {
            System.out.println("Tutor com o ID informado NÃO foi encontrado.");
            return;
        }

        System.out.println("\nTutor encontrado: " + tutorBase.getNome());
        System.out.println("Digite as novas informações (ou pressione ENTER para manter a atual).");

        System.out.print("Novo nome (" + tutorBase.getNome() + "): ");
        String nome = scanner.nextLine();
        if (nome.isBlank()) {
            nome = tutorBase.getNome();
        }

        String telefone = null;
        boolean telefoneValido = false;
        do {
            System.out.print("Novo telefone (" + tutorBase.getTelefone() + "): ");
            String entrada = scanner.nextLine();

            if (entrada.isBlank()) {
                telefone = tutorBase.getTelefone();
                telefoneValido = true;
            } else if (Validador.isTelefoneValido(entrada)) {
                telefone = entrada;
                telefoneValido = true;
            } else {
                System.out.println("Formato de telefone inválido. Tente novamente ou deixe em branco para manter.");
            }
        } while (!telefoneValido);

        String email = null;
        boolean emailValido = false;
        do {
            System.out.print("Novo e-mail (" + tutorBase.getEmail() + "): ");
            String entrada = scanner.nextLine();

            if (entrada.isBlank()) {
                email = tutorBase.getEmail();
                emailValido = true;
            } else if (Validador.isEmailValido(entrada)) {
                email = entrada;
                emailValido = true;
            } else {
                System.out.println("Formato de e-mail inválido. Tente novamente ou deixe em branco para manter.");
            }
        } while (!emailValido);

        Endereco endereco = tutorBase.getEndereco();
        System.out.print("Deseja atualizar o endereço? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            endereco = acoesEndereco();
        }

        if (controller.atualizarTutor(id, nome, endereco, telefone, email)) {
            System.out.println("\nCadastro do tutor atualizado com sucesso!");
        } else {
            System.out.println("\nFalha ao atualizar o cadastro.");
        }
    }

    /**
     * Gerencia o fluxo de atualização dos dados de um setor existente.
     */
    private static void atualizarSetor() {
        int id = lerInteiro("Digite o ID do setor que deseja atualizar: ");

        SetorResponsavel setorBase = controller.buscarSetorId(id);

        if (setorBase == null) {
            System.out.println("Setor com o ID informado não foi encontrado.");
            return;
        }

        System.out.println("\nSetor encontrado: " + setorBase.getNome());
        System.out.println("Digite as novas informações (ou pressione ENTER para manter a atual).");

        System.out.print("Novo nome (" + setorBase.getNome() + "): ");
        String nome = scanner.nextLine();
        if (nome.isBlank()) {
            nome = setorBase.getNome();
        }

        Endereco endereco = setorBase.getEndereco();
        System.out.print("Deseja atualizar o endereço? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            endereco = acoesEndereco();
        }

        if (controller.atualizarSetor(id, nome, endereco)) {
            System.out.println("\nCadastro do setor atualizado com sucesso!");
        } else {
            System.out.println("\nFalha ao atualizar o cadastro.");
        }
    }

    /**
     * Gerencia o fluxo para associar um animal a um setor.
     */
    private static void adicionarAnimalEmSetor() {
        int idAnimal = lerInteiro("Digite o ID do animal: ");
        System.out.println("\nSetores Disponíveis:");
        List<SetorResponsavel> setores = controller.listarSetores();

        if (setores.isEmpty()) {
            System.out.println("Não há setores cadastrados. Cadastre um setor primeiro.");
            return;
        }

        for (SetorResponsavel setor : setores) {
            System.out.println("ID: " + setor.getId() + " - " + setor.getNome());
        }
        int idSetor = lerInteiro("\nDigite o ID do setor de destino: ");

        if (controller.associarAnimalASetor(idAnimal, idSetor)) {
            System.out.println("\nAnimal associado com sucesso!");
        } else {
            System.out.println("\nFalha ao associar. Verifique os IDs ou se o setor de destino possui tutores.");
        }
    }

    /**
     * Gerencia o fluxo para desassociar um animal de um setor.
     */
    private static void removerAnimalDeSetor() {
        int idAnimal = lerInteiro("Digite o ID do animal: ");
        System.out.println("\n- Setores Cadastrados -");
        List<SetorResponsavel> setores = controller.listarSetores();

        if (setores.isEmpty()) {
            System.out.println("Não há setores cadastrados no sistema.");
            return;
        }

        for (SetorResponsavel setor : setores) {
            System.out.println("ID: " + setor.getId() + " - " + setor.getNome());
        }
        int idSetor = lerInteiro("\nDigite o ID do setor de onde o animal será removido: ");

        if (controller.desassociarAnimalDeSetor(idAnimal, idSetor)) {
            System.out.println("\nAnimal removido do setor com sucesso!");
        } else {
            System.out.println("\nFalha na operação. Verifique se os IDs estão corretos e se o animal realmente pertence a este setor.");
        }
    }

    /**
     * Gerencia o fluxo para associar um tutor a um setor.
     */
    private static void adicionarTutorEmSetor() {
        int idTutor = lerInteiro("Digite o ID do tutor: ");
        System.out.println("\nSetores Disponíveis:");
        List<SetorResponsavel> setores = controller.listarSetores();

        if (setores.isEmpty()) {
            System.out.println("Não há setores cadastrados. Cadastre um setor primeiro.");
            return;
        }

        for (SetorResponsavel setor : setores) {
            System.out.println("ID: " + setor.getId() + " - " + setor.getNome());
        }
        int idSetor = lerInteiro("\nDigite o ID do setor de destino: ");

        if (controller.associarTutorASetor(idTutor, idSetor)) {
            System.out.println("\nTutor associado com sucesso!");
        } else {
            System.out.println("\nFalha ao associar. Verifique os IDs ou se o tutor ja pertence a esse setor.");
        }
    }

    /**
     * Gerencia o fluxo para desassociar um tutor de um setor.
     */
    private static void removerTutorDeSetor() {
        int idTutor = lerInteiro("Digite o ID do tutor: ");
        System.out.println("\nSetores Disponíveis:");
        List<SetorResponsavel> setores = controller.listarSetores();

        if (setores.isEmpty()) {
            System.out.println("Não há setores cadastrados. Cadastre um setor primeiro.");
            return;
        }

        for (SetorResponsavel setor : setores) {
            System.out.println("ID: " + setor.getId() + " - " + setor.getNome());
        }
        int idSetor = lerInteiro("\nDigite o ID do setor de onde o tutor será removido: ");

        if (controller.desassociarTutorASetor(idTutor, idSetor)) {
            System.out.println("\nTutor removido do setor com sucesso!");
        } else {
            System.out.println("\nFalha na operação. Verifique os IDs ou se você está tentando remover o último tutor de um setor que ainda possui animais.");
        }
    }

    /**
     * Exibe e gerencia o menu de operações relacionadas a animais.
     */
    private static void menuAnimal() {
        int opcao = -1;
        do {
            System.out.println("\n- GERENCIAR ANIMAIS -");
            System.out.println("1 - Cadastrar animal");
            System.out.println("2 - Buscar animal");
            System.out.println("3 - Relação de todos os animais");
            System.out.println("4 - Relação de animais por setor");
            System.out.println("5 - Relação de animais por tutor");
            System.out.println("6 - Deletar animal");
            System.out.println("7 - Atualizar informações do animal");
            System.out.println("8 - Associar animal a setor");
            System.out.println("9 - Desassociar animal de setor");
            System.out.println("0 - VOLTAR");
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarAnimal();
                    break;
                case 2:
                    menuBuscaAnimal();
                    break;
                case 3:
                    relacaoCompletaAnimais();
                    break;
                case 4:
                    relacaoAnimaisPorSetor();
                    break;
                case 5:
                    relacaoAnimaisPorTutor();
                    break;
                case 6:
                    deletarAnimal();
                    break;
                case 7:
                    atualizarAnimal();
                    break;
                case 8:
                    adicionarAnimalEmSetor();
                    break;
                case 9:
                    removerAnimalDeSetor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    /**
     * Exibe e gerencia o menu de operações relacionadas a tutores.
     */
    private static void menuTutor() {
        int opcao = -1;
        do {
            System.out.println("\n- GERENCIAR PESSOA TUTORA -");
            System.out.println("1 - Cadastrar tutor");
            System.out.println("2 - Buscar tutor");
            System.out.println("3 - Relação de todos os tutores");
            System.out.println("4 - Deletar tutor");
            System.out.println("5 - Atualizar informações do tutor");
            System.out.println("6 - Associar tutor a setor");
            System.out.println("7 - Desassociar tutor de setor");
            System.out.println("0 - VOLTAR");
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarTutor();
                    break;
                case 2:
                    menuBuscaTutor();
                    break;
                case 3:
                    relacaoCompletaTutores();
                    break;
                case 4:
                    deletarTutor();
                    break;
                case 5:
                    atualizarTutor();
                    break;
                case 6:
                    adicionarTutorEmSetor();
                    break;
                case 7:
                    removerTutorDeSetor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    /**
     * Exibe e gerencia o menu de operações relacionadas a setores.
     */
    private static void menuSetor() {
        int opcao = -1;
        do {
            System.out.println("\n- GERENCIAR SETOR RESPONSÁVEL -");
            System.out.println("1 - Cadastrar setor");
            System.out.println("2 - Buscar setor");
            System.out.println("3 - Relação de todos os setores");
            System.out.println("4 - Deletar setor");
            System.out.println("5 - Atualizar informações do setor");
            System.out.println("0 - VOLTAR");
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarSetor();
                    break;
                case 2:
                    menuBuscaSetor();
                    break;
                case 3:
                    relacaoCompletaSetor();
                    break;
                case 4:
                    deletarSetor();
                    break;
                case 5:
                    atualizarSetor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    /**
     * Exibe o menu principal da aplicação e gerencia a navegação de alto nível.
     */
    private static void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n================================");
            System.out.println("Aplicação de Apoio à Proteção de Animais da UEFS");
            System.out.println("================================");
            System.out.println("1 - GERENCIAR ANIMAL");
            System.out.println("2 - GERENCIAR PESSOA TUTORA");
            System.out.println("3 - GERENCIAR SETOR RESPONSÁVEL");
            System.out.println("0 - FECHAR");
            System.out.println("================================");

            opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1:
                    menuAnimal();
                    break;
                case 2:
                    menuTutor();
                    break;
                case 3:
                    menuSetor();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("\nOpção inexistente. Tente novamente.\n");
            }
        } while (opcao != 0);
    }
}
