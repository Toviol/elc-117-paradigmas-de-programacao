package org.example.service;
import org.example.DAO.*;
import org.example.model.*;

import java.time.LocalDate;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class setorAluno {
    public void escolhasAluno(AlunoDAO Adao, PlanoDAO Pdao, ExercicioDAO Edao, AlunoPlanoDAO AlPldao, TreinoDAO Tdao, PresencaDAO PRdao, Connection conn){
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");

        Scanner read = new Scanner(System.in);
        Aluno A;
        System.out.println("Digite o seu cpf: ");
        A= Adao.pesquisarCpf(read.nextLine());
        if (A!=null)
        {
            PRdao.inserirPresenca(A.getCpf());

            int selecionar;
            do {
                System.out.println("Olá "+A.getNome()+", selecione a opção desejada:");
                System.out.println("(1) Começar um treino.");
                System.out.println("(2) Relatório do aluno.");
                System.out.println("(-1) Voltar ao menu inicial.");
                Scanner entrada = new Scanner(System.in);
                selecionar = entrada.nextInt();
                System.out.print("\n\n\n\n\n\n\n\n\n"); // da uma limpada na tela
                switch (selecionar){
                    case 1:
                        comecarTreino(A, Adao, Tdao, Edao);
                        break;

                    case 2:
                        relatoriosAluno(A, Adao, Tdao, Edao, PRdao);
                        break;

                    default:
                        selecionar = -1;
                }
            }while(selecionar != -1);
        }

    }

    public void comecarTreino(Aluno A, AlunoDAO Adao, TreinoDAO Tdao, ExercicioDAO Edao)
    {
        Scanner read = new Scanner(System.in);
        List<Treino> treinos;
        treinos = Tdao.buscarTreinosCpf(A.getCpf());
        if (treinos!=null)
        {
            for (Treino printTreino : treinos) {
                System.out.println("\t"+ printTreino);
            }
            System.out.println();

            System.out.println("Escolha um treino(ID): ");
            int idTreino = read.nextInt();
            Treino treino;
            treino = Tdao.buscarTreinoId(idTreino);
            if (treino!=null&&treino.getCpf_aluno().equals(A.getCpf()))
            {
                System.out.println("Você iniciou o treino "+treino.getNome());
                int menu = 0;
                while (menu!=4)
                {
                    System.out.println("Selecione a opção desejada:");
                    System.out.println("(1) Consultar exercicios a serem feitos.");
                    System.out.println("(2) Concluir exercicio.");
                    System.out.println("(3) Alterar carga do exercicio.");
                    System.out.println("(4) Finalizar Treino.");

                    menu = read.nextInt();
                    switch (menu){
                        case 1:
                            List<TreinoExercicio> exerciciosAtivos = Tdao.getExerciciosAtivosPorTreino(treino.getId());
                            if (exerciciosAtivos!=null)
                            {
                                for (TreinoExercicio exercicio : exerciciosAtivos) {
                                    System.out.print("Numero: " + exercicio.getIdExercicio());
                                    System.out.print(" / Nome: " + exercicio.getNomeExercicio());
                                    System.out.print(" / Séries: " + exercicio.getSeries());
                                    System.out.print(" / RepMin: " + exercicio.getRepeticoesMin());
                                    System.out.print(" / RepMax: " + exercicio.getRepeticoesMax());
                                    System.out.print(" / Carga: " + exercicio.getCarga());
                                    System.out.println(" / Descanso: " + exercicio.getDescanso());
                                }
                            }
                            else {
                                System.out.print("Não há exercicios a serem feitos.");
                            }
                            break;
                        case 2:
                            Exercicio E;
                            List<TreinoExercicio> exerciciosAtivos2 = Tdao.getExerciciosAtivosPorTreino(treino.getId());
                            if (exerciciosAtivos2!=null){
                                for (TreinoExercicio exercicio : exerciciosAtivos2) {
                                    System.out.print("Numero: " + exercicio.getIdExercicio());
                                    System.out.println(" / Nome: " + exercicio.getNomeExercicio());
                                }
                                System.out.println("Digite o número para concluir: ");
                                E=Edao.pesquisarNumero(read.nextInt());
                                if (E!=null)
                                {
                                    TreinoExercicio exercicio = Tdao.concluirExercicio(treino.getId(), E.getNumero());
                                    if (exercicio!=null)
                                    {

                                        int ultimaCarga = Tdao.getCargaMaiorId(exercicio.getId());
                                        if (ultimaCarga!=exercicio.getCarga())
                                        {
                                            Tdao.adicionarProgressaoCarga(exercicio.getId(), exercicio.getCarga());
                                        }

                                    }
                                }
                            }
                            else{
                                System.out.print("Não há exercicios a serem feitos.");
                            }
                            break;

                        case 3:
                            Exercicio E2;
                            List<TreinoExercicio> todosExericios = Tdao.getTodosExerciciosTreino(treino.getId());

                            for (TreinoExercicio exercicio : todosExericios) {
                                System.out.print("Numero: " + exercicio.getIdExercicio());
                                System.out.print(" / Nome: " + exercicio.getNomeExercicio());
                                System.out.println(" / Carga Atual: " + exercicio.getCarga());
                            }

                            System.out.println("Digite o número do exercício para trocar de carga: ");
                            E2=Edao.pesquisarNumero(read.nextInt());
                            if (E2!=null)
                            {
                                System.out.println("Digite a nova carga: ");
                                Tdao.modificarCargaExercicio(treino.getId(), E2.getNumero(), read.nextInt());
                            }
                            break;

                        case 4:
                            Tdao.concluirTreino(treino.getId());
                            break;
                        default:
                    }
                }

            }
            else{
                System.out.println("Treino "+idTreino+" não encontrado");
            }
        }
        else{
            System.out.println("Não há treinos disponiveis.");
        }
    }

    public void relatoriosAluno(Aluno A, AlunoDAO Adao, TreinoDAO Tdao, ExercicioDAO Edao, PresencaDAO PRdao)
    {
        Scanner read = new Scanner(System.in);
        int menu = 0;
        while (menu!=-1){
            System.out.println("(1)Presenças em um intervalo.");
            System.out.println("(2)Progressão de carga.");
            System.out.println("(-1)Voltar ao menu aluno.");

            menu = read.nextInt();

            switch (menu)
            {
                case 1:
                    read.nextLine();
                    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    System.out.print("Digite a data de início no formato dd/MM/yyyy: ");
                    String dataInicioStr = read.nextLine();

                    try {
                        LocalDate dataInicio = LocalDate.parse(dataInicioStr, dateformatter);

                        System.out.print("Digite a data de fim no formato dd/MM/yyyy: ");
                        String dataFimStr = read.nextLine();

                        try {
                            LocalDate dataFim = LocalDate.parse(dataFimStr, dateformatter);

                            List<Presenca> presencas = PRdao.buscarPresencasNoIntervalo(A.getCpf(), dataInicio, dataFim);

                            for (Presenca presenca : presencas) {
                                System.out.println(presenca);
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("A data de fim está em um formato inválido. Por favor, tente novamente.");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("A data de início está em um formato inválido. Por favor, tente novamente.");
                    }

                    break;
                case 2:
                    List<Treino> treinos;
                    treinos = Tdao.buscarTreinosCpf(A.getCpf());
                    if (treinos!=null) {
                        for (Treino printTreino : treinos) {
                            System.out.println("\t" + printTreino);
                        }
                        System.out.println();

                        System.out.println("Escolha um treino(ID): ");
                        int idTreino = read.nextInt();
                        Treino treino;
                        treino = Tdao.buscarTreinoId(idTreino);
                        if (treino != null && treino.getCpf_aluno().equals(A.getCpf())) {
                            Exercicio E2;
                            List<TreinoExercicio> todosExericios = Tdao.getTodosExerciciosTreino(treino.getId());

                            for (TreinoExercicio exercicio : todosExericios) {
                                System.out.print("Numero: " + exercicio.getIdExercicio());
                                System.out.print(" / Nome: " + exercicio.getNomeExercicio());
                                System.out.println(" / Carga Atual: " + exercicio.getCarga());
                            }

                            System.out.println("Digite o número do exercício para exibir a progressão: ");
                            E2=Edao.pesquisarNumero(read.nextInt());

                            if(E2!=null)
                            {
                                TreinoExercicio t1 = Tdao.buscarIdTreinoExercicio(treino.getId(), E2.getNumero());
                                if (t1!=null)
                                {
                                    List<ProgressaoCarga> progressaoCargas = Tdao.getProgressoesCargaPorTreinoExercicioId(t1.getId());

                                    for (ProgressaoCarga printProgressao : progressaoCargas) {
                                        System.out.println(printProgressao);
                                    }
                                }

                            }


                        }
                    }
                    break;
                default:
                    break;
            }
        }

    }

}
