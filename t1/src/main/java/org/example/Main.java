package org.example;
import org.example.DAO.*;
import org.example.service.setorAluno;
import org.example.service.setorInstrutor;
import org.example.util.DbFunctions;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("trabalho1", "postgres", "root");
        setorAluno sA = new setorAluno();
        setorInstrutor sI = new setorInstrutor();
        AlunoDAO Adao = new AlunoDAO(conn);
        PlanoDAO Pdao = new PlanoDAO(conn);
        ExercicioDAO Edao = new ExercicioDAO(conn);
        AlunoPlanoDAO AlPldao = new AlunoPlanoDAO(conn);
        TreinoDAO Tdao = new TreinoDAO(conn);
        PresencaDAO PRdao = new PresencaDAO(conn);

        if (conn != null) {
            db.createTableAlunos(conn);
            db.createTablePlanos(conn);
            db.createTableExercicios(conn);
            db.createTableAlunoPlano(conn);
            db.createTableTreinos(conn);
            db.createTableTreinosExercicios(conn);
            db.createTablePresencas(conn);
            db.createTableProgressaoCarga(conn);
        } else {
            System.out.println("Falha ao criar tabelas");
        }



        int setor;
        do {
            System.out.println("Bem vindo ao programa de treino da academia 'GIGANTE E FORTE!'!\nPor favor insira se voce é um aluno ou um instrutor.\n(1) Aluno.\n(2) Instrutor.\n(-1) Sair.");
            var entrada = new Scanner(System.in);
            setor = entrada.nextInt();
            System.out.print("\n\n\n\n\n\n\n\n\n");
            switch (setor) {
                case 1:
                    sA.escolhasAluno(Adao, Pdao, Edao, AlPldao, Tdao, PRdao, conn);
                break;

                case 2:
                    sI.escolhasInstrutor(Adao, Pdao, Edao, AlPldao, Tdao, conn);
                break;

                default:
                    setor = -1;
            }
        } while (setor != -1);
    }
}