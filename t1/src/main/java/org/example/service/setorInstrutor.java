package org.example.service;

import org.example.DAO.*;
import org.example.model.*;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class setorInstrutor {
    public void escolhasInstrutor(AlunoDAO Adao, PlanoDAO Pdao, ExercicioDAO Edao, AlunoPlanoDAO AlPldao, TreinoDAO Tdao, Connection conn){
        int selecionar;
        do {
            System.out.println("Olá instrutor, selecione a opção desejada:");
            System.out.println("(1) Modificar, listar ou buscar dados de alunos.");
            System.out.println("(2) Modificar, buscar ou associar planos."); // associar um dos planos existentes a um aluno
            System.out.println("(3) Cadastrar, alterar, remover ou listar exercícios."); // aqui é só pra ter o repositorio de exercicios, e seus dados
            System.out.println("(4) Cadastrar, alterar ou remover treinos, ou associá-los a alunos."); // um aluno pode ter um ou mais treinos associados, deve ser selecionado um aluno e criado um treino pra ele
            System.out.println("(-1) Voltar ao menu inicial.");
            Scanner entrada = new Scanner(System.in);
            selecionar = entrada.nextInt();
            System.out.print("\n\n\n\n\n\n\n\n\n");
            switch (selecionar){
                case 1:
                    manipularDadosAlunos(Adao, Pdao, AlPldao);
                break;

                case 2:
                    manipularPlanos(Adao, Pdao, AlPldao);
                break;

                case 3:
                    manipularExercicios(Edao);
                break;

                case 4:
                    manipularTreinos(Adao, Tdao, Edao);
                break;

                default:
                    selecionar = -1;
            }
        }while(selecionar != -1);

    }


    private void manipularTreinos(AlunoDAO Adao, TreinoDAO Tdao, ExercicioDAO Edao) {
        Scanner read = new Scanner(System.in);
        int pausa;
        int menu;
        do {
            System.out.println("Digite a opção:");
            System.out.println("(1) Cadastrar treino.");
            System.out.println("(2) Alterar/Inserir dados do treino.");
            System.out.println("(3) Excluir treino de um aluno.");
            System.out.println("(4) Exibir treinos do aluno.");
            System.out.println("(-1) Voltar ao menu instrutor.");

            menu = read.nextInt();
            read.nextLine();

            Aluno A;
            Exercicio E;

            switch (menu){
                case 1:


                    System.out.println("Criar treino.");
                    System.out.println("Digite o nome do treino: ");
                    String nome = read.nextLine();
                    System.out.println("Digite o cpf do aluno ao qual deseja vincular(xxx.xxx.xxx-xx): ");
                    A = Adao.pesquisarCpf(read.nextLine());
                    if(A!=null)
                    {
                        int idTreino = Tdao.insertTreinos(A, nome);
                        if(idTreino>0){
                            System.out.println("Exercicios Disponiveis: ");
                            List <Exercicio> exercicios;
                            exercicios = Edao.buscarTodos();
                            for (Exercicio printExercicio : exercicios) {
                                System.out.println("\t"+ printExercicio);
                            }
                            while(true)
                            {
                                System.out.println("Digite o número do exercício escolhido ou digite -1 quando acabar: ");
                                int ex = read.nextInt();
                                if(ex<0)
                                {
                                    break;
                                }
                                E = Edao.pesquisarNumero(ex);
                                if (E!=null)
                                {
                                    Tdao.associarExercicio(idTreino, E.getNumero());
                                }
                                else{
                                    System.out.println("Exercicio inexistente!");
                                }
                            }
                            System.out.println("Treino criado com sucesso!");
                        }
                    }
                    else{
                        System.out.println("Aluno nao encontrado!");
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = read.nextInt();
                    break;
                case 2:
                    System.out.println("Digite o CPF do aluno: ");
                    A = Adao.pesquisarCpf(read.nextLine());
                    if(A!=null){
                        System.out.println("Aluno: "+A.getNome()+"\n");
                        List <Treino> treinos;
                        treinos = Tdao.buscarTreinosCpf(A.getCpf());
                        if (treinos!=null)
                        {
                            for (Treino printTreino : treinos) {
                                System.out.println("\t"+ printTreino);
                            }
                            System.out.println();

                            System.out.println("Escolha o treino a ser alterado (id): ");
                            int idTreino = read.nextInt();
                            Treino treino;
                            treino = Tdao.buscarTreinoId(idTreino);
                            if (treino!=null&&treino.getCpf_aluno().equals(A.getCpf()))
                            {
                                System.out.println("Treino: "+treino+"\n");
                                System.out.println("Exercícios:\n");

                                List <Exercicio> exerciciosTreino;
                                exerciciosTreino=Tdao.buscarExerciciosTreino(treino);
                                for (Exercicio printExercicio : exerciciosTreino) {
                                    System.out.println("\t"+ printExercicio);
                                }

                                System.out.println("Escolha uma opção:");
                                System.out.println("(1)Renomear Treino.");
                                System.out.println("(2)Acrescentar Exercicio.");
                                System.out.println("(3)Excluir Exercicio.");
                                System.out.println("(4)Inserir Séries, Repetições, Cargas...");
                                int altDados = read.nextInt();
                                read.nextLine();

                                switch (altDados){
                                    case 1:
                                        System.out.println("Digite o novo nome: ");
                                        Tdao.alterarNomeTreino(treino.getId(), read.nextLine());
                                        break;
                                    case 2:
                                        System.out.println("Exercicios Disponiveis: ");
                                        List <Exercicio> exercicios;
                                        exercicios = Edao.buscarTodos();
                                        for (Exercicio printExercicio : exercicios) {
                                            System.out.println("\t"+ printExercicio);
                                        }
                                        while(true)
                                        {
                                            System.out.println("Digite o número do exercício escolhido ou digite -1 para voltar: ");
                                            int ex = read.nextInt();
                                            if(ex<0)
                                            {
                                                break;
                                            }
                                            E = Edao.pesquisarNumero(ex);
                                            if (E!=null)
                                            {
                                                Tdao.associarExercicio(treino.getId(), E.getNumero());
                                            }
                                            else{
                                                System.out.println("Exercicio inexistente!");
                                            }
                                        }
                                        break;

                                    case 3:
                                        System.out.println("Digite o número do exercício para remoção: ");
                                        Tdao.desvincularExercicio(treino.getId(), read.nextInt());
                                        break;

                                    case 4:
                                        System.out.println("Exercicios Disponiveis: ");
                                        List <Exercicio> exercicios2;
                                        exercicios2 = Tdao.buscarExerciciosTreino(treino);
                                        for (Exercicio printExercicio : exercicios2) {
                                            System.out.println("\t"+ printExercicio);
                                        }
                                        while(true)
                                        {
                                            System.out.println("Digite o número do exercício escolhido ou digite -1 para voltar: ");
                                            int ex = read.nextInt();
                                            if(ex<0)
                                            {
                                                break;
                                            }
                                            E = Edao.pesquisarNumero(ex);
                                            if (E!=null)
                                            {
                                                System.out.println("Insira as informações: ");
                                                System.out.println("Séries: ");
                                                int series = read.nextInt();
                                                System.out.println("Repetições Minimas: ");
                                                int repMin = read.nextInt();
                                                System.out.println("Repetições Maximas: ");
                                                int repMax = read.nextInt();
                                                System.out.println("Carga(kg): ");
                                                int carga = read.nextInt();
                                                System.out.println("Descanso(min): ");
                                                double descanso = read.nextDouble();
                                                Tdao.inserirDadosExercicio(treino.getId(), E.getNumero(), series, repMin, repMax, carga, descanso);
                                            }
                                            else{
                                                System.out.println("Exercicio inexistente!");
                                            }
                                        }
                                    default:
                                        break;
                                }
                            }
                            else {
                                System.out.println("Treino "+idTreino+" não encontrado");
                            }

                        }
                        else{
                            System.out.println("Nenhum treino registrado.");
                        }
                    }
                    else{
                        System.out.println("Aluno nao encontrado!");
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = read.nextInt();

                    break;
                case 3:
                    System.out.println("Digite o CPF do aluno: ");
                    A = Adao.pesquisarCpf(read.nextLine());
                    if(A!=null) {
                        System.out.println("Aluno: " + A.getNome() + "\n");
                        List<Treino> treinos;
                        treinos = Tdao.buscarTreinosCpf(A.getCpf());
                        if (treinos != null) {
                            for (Treino printTreino : treinos) {
                                System.out.println("\t"+ printTreino);
                            }
                            System.out.println();

                            System.out.println("Escolha o treino a ser removido (id): ");
                            int idTreino = read.nextInt();
                            Treino treino;
                            treino = Tdao.buscarTreinoId(idTreino);
                            if (treino!=null&&treino.getCpf_aluno().equals(A.getCpf()))
                            {
                                Tdao.deletarTreino(treino.getId());
                            }
                            else{
                                System.out.println("Treino não encontrado.");
                            }
                        }
                        else{
                            System.out.println("O aluno não tem treinos registrados");
                        }
                    }
                    else{
                        System.out.println("Aluno nao encontrado!");
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = read.nextInt();
                    break;
                case 4:
                    System.out.println("Digite o CPF do aluno: ");
                    A = Adao.pesquisarCpf(read.nextLine());
                    if(A!=null) {
                        System.out.println("Aluno: " + A.getNome() + "\n");
                        List<Treino> treinos;
                        treinos = Tdao.buscarTreinosCpf(A.getCpf());
                        if (treinos != null) {
                            for (Treino printTreino : treinos) {
                                System.out.println("\t"+ printTreino);
                            }
                            System.out.println();
                        }
                        else{
                            System.out.println("O aluno não tem treinos registrados");
                        }
                    }
                    else{
                        System.out.println("Aluno não encontrado!");
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = read.nextInt();
                    break;
                default:
                    menu = -1;
            }

        }while(menu != -1);


    }

    private void manipularDadosAlunos(AlunoDAO Adao, PlanoDAO Pdao, AlunoPlanoDAO AlPldao) {
        int escolha;
        Aluno A;
        int pausa;
        do {
            System.out.println("Digite a opção:");
            System.out.println("(1) Adicionar aluno.");
            System.out.println("(2) Alterar dados de aluno.");
            System.out.println("(3) Excluir aluno.");
            System.out.println("(4) Listar alunos.");
            System.out.println("(5) Buscar pelo CPF.");
            System.out.println("(6) Buscar pelo nome.");
            System.out.println("(-1) Voltar ao menu instrutor.");
            Scanner entrada = new Scanner(System.in);
            escolha = entrada.nextInt();
            System.out.print("\n\n\n\n\n\n\n\n\n");
            switch (escolha){
                case 1:
                    System.out.println("Digite o CPF (XXX.XXX.XXX-XX), nome e data de nascimento do aluno (formato DD/MM/YYYY) (SEPARAR POR 'ENTER' CADA DADO):");

                    String cpf = "";
                    String nome = "";
                    String dataNascimento = "";

                    while(cpf.isEmpty()){
                        cpf = entrada.nextLine().trim();
                    }
                    while(nome.isEmpty()){
                        nome = entrada.nextLine().trim();
                    }
                    while(dataNascimento.isEmpty()){
                        dataNascimento = entrada.nextLine().trim();
                    }
                    A = new Aluno();
                    A.setCpf(cpf);
                    A.setNome(nome);
                    A.setData_nascimento(dataNascimento);
                    Adao.insertAluno(A);
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 2:
                    System.out.println("Digite o CPF (XXX.XXX.XXX-XX) do aluno do qual deseje alterar dados:");
                    String cpfAlterar = "";
                    while(cpfAlterar.isEmpty()){
                        cpfAlterar = entrada.nextLine().trim();
                    }
                    A = new Aluno();
                    A = Adao.pesquisarCpf(cpfAlterar);
                    if(A == null){
                        System.out.println("CPF não encontrado!");
                        break;
                    }
                    System.out.print("ALUNO: " + A);
                    System.out.println("Voce deseja alterar o CPF (1), nome (2) ou data de nascimento (3)?");
                    int dadoalterado = entrada.nextInt();
                    if(dadoalterado == 1){
                        System.out.println("Digite o CPF (XXX.XXX.XXX-XX) novo:");
                        String cpfNovo = "";
                        while(cpfNovo.isEmpty()){
                            cpfNovo = entrada.nextLine().trim();
                        }
                        String cpfAntigo = A.getCpf();
                        A.setCpf(cpfNovo);
                        Adao.updateAluno(A, cpfAntigo);
                    }
                    else if(dadoalterado == 2){
                        System.out.println("Digite o nome novo:");
                        String nomeNovo = "";
                        while(nomeNovo.isEmpty()){
                            nomeNovo = entrada.nextLine().trim();
                        }
                        A.setNome(nomeNovo);
                        Adao.updateAluno(A, A.getCpf());
                    }
                    else if(dadoalterado == 3){
                        System.out.println("Digite a data de nascimento nova:");
                        String dataNascimentoNova = "";
                        while(dataNascimentoNova.isEmpty()){
                            dataNascimentoNova = entrada.nextLine().trim();
                        }
                        A.setData_nascimento(dataNascimentoNova);
                        Adao.updateAluno(A, A.getCpf());
                    }
                    else{
                        System.out.println("Opção não encontrada!");
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 3:
                    System.out.println("Digite o CPF (XXX.XXX.XXX-XX) do aluno que deseje excluir:");
                    String cpfExcluir = "";
                    while(cpfExcluir.isEmpty()){
                        cpfExcluir = entrada.nextLine().trim();
                    }
                    Adao.deleteAluno(cpfExcluir);
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 4:
                    List<Aluno> alunos;
                    alunos = Adao.todosAlunos();
                    int i = 1;
                    if(alunos == null){
                        System.out.println("Sem alunos!");
                    }
                    else {
                        for (Aluno printAluno : alunos) {
                            System.out.print("Aluno " + i + ": " + printAluno);
                            i++;
                        }
                    }
                    System.out.println("Digite (1) para continuar quando terminar de olhar a lista.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 5:
                    System.out.println("Digite o CPF do aluno que deseje buscar:");
                    String cpfBuscar = "";
                    while(cpfBuscar.isEmpty()){
                        cpfBuscar = entrada.nextLine().trim();
                    }
                    A = Adao.pesquisarCpf(cpfBuscar);
                    System.out.print("Aluno: " + A);
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 6:
                    System.out.println("Digite o nome do(s) aluno(s) que deseje buscar:");
                    String nomeBuscar = "";
                    while(nomeBuscar.isEmpty()){
                        nomeBuscar = entrada.nextLine().trim();
                    }
                    List<Aluno> alunosNome = Adao.pesquisarNome(nomeBuscar);
                    System.out.println("Alunos com esse nome:");
                    for(Aluno alunoNome : alunosNome){
                        System.out.print("Aluno: " + alunoNome);
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                default:
                    escolha = -1;

            }
        }while(escolha != -1);
    }

    private void manipularPlanos(AlunoDAO Adao, PlanoDAO Pdao,  AlunoPlanoDAO AlPldao){
        int escolha;
        Aluno A;
        Plano P;
        int pausa;
        do {
            System.out.println("Digite a opção:");
            System.out.println("(1) Adicionar plano.");
            System.out.println("(2) Alterar dados de plano.");
            System.out.println("(3) Excluir plano.");
            System.out.println("(4) Listar planos.");
            System.out.println("(5) Associar um plano a um aluno pelo CPF e código do plano.");
            System.out.println("(6) Ver planos de um aluno pelo CPF.");
            System.out.println("(-1) Voltar ao menu instrutor.");
            Scanner entrada = new Scanner(System.in);
            escolha = entrada.nextInt();
            System.out.print("\n\n\n\n\n\n\n\n\n");
            switch (escolha) {
                case 1:
                    System.out.println("Digite o código (APENAS DIGITOS), o nome e o preço mensal do do plano (colocar ',' pra casa de centavos) (SEPARAR POR 'ENTER' CADA DADO)");
                    int codigo = entrada.nextInt();
                    String nome = "";
                    while (nome.isEmpty()){
                        nome = entrada.nextLine().trim();
                    }
                    double preco = entrada.nextDouble();
                    P = new Plano();
                    P.setCodigo(codigo);
                    P.setNome(nome);
                    P.setPreco(preco);
                    Pdao.insertPlano(P);
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 2:
                    System.out.println("Digite o código (APENAS DIGITOS) do plano que deseje alterar dados:");
                    int codigoAlterar = entrada.nextInt();
                    P = new Plano();
                    P = Pdao.pesquisarCodigo(codigoAlterar);
                    if(P == null){
                        System.out.println("Código não encontrado!");
                        break;
                    }
                    System.out.print("Plano: " + P);
                    System.out.println("Voce deseja alterar o nome (1) ou o preço mensal (2) do plano?");
                    int dadoalterado = entrada.nextInt();
                    if(dadoalterado == 1){
                        System.out.println("Digite o nome novo:");
                        String nomeNovo = "";
                        while(nomeNovo.isEmpty()){
                            nomeNovo = entrada.nextLine().trim();
                        }
                        P.setNome(nomeNovo);
                        Pdao.updatePlano(P);
                    }
                    else if(dadoalterado == 2){
                        System.out.println("Digite o preço novo (colocar ',' para separar centavos):");
                        double precoNovo = entrada.nextDouble();
                        P.setPreco(precoNovo);
                        Pdao.updatePlano(P);
                    }
                    else{
                        System.out.println("Opção não encontrada!");
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 3:
                    System.out.println("Digite o código (APENAS DIGITOS) do plano que deseje excluir:");
                    int codigoExcluir = entrada.nextInt();
                    Pdao.deletePlano(codigoExcluir);
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 4:
                    List<Plano> planos;
                    planos = Pdao.buscarTodos();
                    int i = 1;
                    if(planos == null){
                        System.out.println("Sem planos!");
                    }
                    else {
                        for (Plano printPlano : planos) {
                            System.out.print("Plano " + i + ": " + printPlano);
                            i++;
                        }
                    }
                    System.out.println("Digite (1) para continuar quando terminar de olhar a lista.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                case 5:
                    System.out.println("Digite o CPF (XXX.XXX.XXX-XX) do aluno:");
                    String cpfAluno = "";
                    while(cpfAluno.isEmpty()){
                        cpfAluno = entrada.nextLine().trim();
                    }
                    A = new Aluno();
                    A = Adao.pesquisarCpf(cpfAluno);
                    if(A == null){
                        System.out.println("CPF não encontrado!");
                        break;
                    }

                    System.out.println("Digite o código (APENAS DIGITOS) do plano que deseja vincular ao aluno:");
                    int codigoPlano = entrada.nextInt();
                    P = new Plano();
                    P = Pdao.pesquisarCodigo(codigoPlano);
                    if(P == null){
                        System.out.println("Código não encontrado!");
                        break;
                    }

                    System.out.println("Digite a data de inicio do plano (formato DD/MM/YYYY):");
                    String dataInicio = "";
                    while(dataInicio.isEmpty()){
                        dataInicio = entrada.nextLine().trim();
                    }

                    System.out.println("Digite os dados do cartão de crédito para pagamento: ");
                    String dadosCartao = "";
                    while(dadosCartao.isEmpty()){
                        dadosCartao = entrada.nextLine().trim();
                    }

                    AlunoPlano aluno_plano = new AlunoPlano(A.getCpf(), P.getCodigo(), dataInicio, dadosCartao);

                    AlPldao.insertAlunoPlano(aluno_plano);

                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");


                break;

                case 6:
                    System.out.println("Digite o CPF (XXX.XXX.XXX-XX) do aluno:");
                    String cpfAlunoBuscar = "";
                    while(cpfAlunoBuscar.isEmpty()){
                        cpfAlunoBuscar = entrada.nextLine().trim();
                    }
                    A = new Aluno();
                    A = Adao.pesquisarCpf(cpfAlunoBuscar);
                    if(A == null){
                        System.out.println("CPF não encontrado!");
                        break;
                    }
                    List<AlunoPlano> alunoPlanos=AlPldao.getPlanosAluno(A);

                    for (AlunoPlano alunoPlano : alunoPlanos) {
                        System.out.println("Código Plano: " + alunoPlano.getCodigoPlano());
                        System.out.println("Data de Início: " + alunoPlano.getDataInicio());
                        System.out.println("---");
                    }

                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                break;

                default:
                    escolha = -1;
            }
        }while(escolha != -1);
    }

    private void manipularExercicios(ExercicioDAO Edao){
        int escolha;
        Exercicio E;
        int pausa;
        do {
            System.out.println("Digite a opção:");
            System.out.println("(1) Adicionar exercício.");
            System.out.println("(2) Alterar dados de exercício.");
            System.out.println("(3) Excluir exercício.");
            System.out.println("(4) Listar exercícios.");
            System.out.println("(-1) Voltar ao menu instrutor.");
            Scanner entrada = new Scanner(System.in);
            escolha = entrada.nextInt();
            System.out.print("\n\n\n\n\n\n\n\n\n");
            switch (escolha) {
                case 1:
                    System.out.println("Digite o número (APENAS DIGITOS), o nome e os músculos ativados pelo exercício.");
                    int numero;
                    String nome = "";
                    String musculos = "";

                    numero = entrada.nextInt();
                    while(nome.isEmpty()){
                        nome = entrada.nextLine().trim();
                    }
                    while(musculos.isEmpty()){
                        musculos = entrada.nextLine().trim();
                    }
                    E = new Exercicio();
                    E.setNumero(numero);
                    E.setNome(nome);
                    E.setMusculos(musculos);
                    Edao.insertExercicio(E);
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                    break;

                case 2:
                    System.out.println("Digite o número do exercício que deseja alterar:");
                    int numeroAlterar = entrada.nextInt();
                    E = new Exercicio();
                    E = Edao.pesquisarNumero(numeroAlterar);
                    if(E == null){
                        System.out.println("Número não encontrado!");
                        break;
                    }
                    System.out.print("Exercício: " + E);
                    System.out.println("Voce deseja alterar o nome (1) ou os músculos ativados (2) do exercício?");
                    int dadoalterado = entrada.nextInt();
                    if(dadoalterado == 1){
                        System.out.println("Digite o nome novo:");
                        String nomeNovo = "";
                        while(nomeNovo.isEmpty()){
                            nomeNovo = entrada.nextLine().trim();
                        }
                        E.setNome(nomeNovo);
                        Edao.updateExercicio(E);
                    }
                    else if(dadoalterado == 2){
                        System.out.println("Digite os músculos novos");
                        String musculosNovos = "";
                        while(musculosNovos.isEmpty()){
                            musculosNovos = entrada.nextLine().trim();
                        }
                        E.setMusculos(musculosNovos);
                        Edao.updateExercicio(E);
                    }
                    else{
                        System.out.println("Opção não encontrada!");
                    }
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                    break;

                case 3:
                    System.out.println("Digite o número do exercício que deseje excluir:");
                    int numeroExcluir = entrada.nextInt();
                    Edao.deleteExercicio(numeroExcluir);
                    System.out.println("Digite (1) para continuar.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                    break;

                case 4:
                    List <Exercicio> exercicios;
                    exercicios = Edao.buscarTodos();
                    int i = 1;
                    if(exercicios == null){
                        System.out.println("Sem exercícios!");
                    }
                    else {
                        for (Exercicio printExercicio : exercicios) {
                            System.out.print("Exercício " + i + ": " + printExercicio);
                            i++;
                        }
                    }
                    System.out.println("Digite (1) para continuar quando terminar de olhar a lista.");
                    pausa = entrada.nextInt();
                    System.out.print("\n\n\n\n\n\n\n\n\n");
                    break;

                default:
                    escolha = -1;
            }
        }while(escolha != -1);

    }
}
