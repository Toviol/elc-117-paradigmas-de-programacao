Arquivo explicando dependencias do trabalho, e o que foi feito:

Trabalho feito em dupla: Gabriel Souza Baggio e Tobias Viero


O programa tem as seguintes dependencias:

1. Driver do JDK para compilação em Java.

2. !! Driver do PostGreSQL, visto que a base de dados relacional 
escolhida foi o PostGres.

As classes estão em: src/main/java/org/example
A Port do banco de dados é '5432', o dbname é 'trabalho1', o user é 'postgres' e a senha é 'root'.
Foi feito com o IntelliJ


Das requisições do trabalho, foram feitas (e suas especificações):

1. Os dados de alunos e de exercícios devem ser armazenados em uma base de dados relacional 
(postgres, mysql, sqlserver etc.). Os dados de outras entidades podem ser armazenados em 
listas em memória (quem desejar, pode armazená-los no banco também).

R: Feito 100% em armazenamento de banco de dados PostGres, mesmo fechando o programa tudo será salvo.

2. Deve ser possível cadastrar alunos: incluir, alterar, excluir, listar, buscar pelo CPF, e 
pelo nome. Cada aluno deve ter: CPF, nome, data de nascimento

R: Na classe setorInstrutor foi realizado todas essas requisições, sendo usada a tabela Alunos para
armazenar essas informações, com o CPF sendo a chave primaria.

3. Deve ser possível cadastrar planos. Cada plano deve ter: código, nome, valor mensal.

R: É possível cadastrar planos, também alterar seus dados, e excluí-los.

4. Deve ser possível cadastrar exercícios. Cada exercício deve conter: número, nome, músculos ativados. 

R: Feito, com a possibilidade de alterar os dados tal qual como feito com os planos.

5. Para alunos cadastrados, deve ser possível ao instrutor:

5.1 Cadastrar um plano, contendo: data de início do plano, dados do cartão de crédito

R: Foi usada uma tabela AlunoPlano para relacionar o codigo do plano com o CPF de um aluno.
Essa tabela possui chaves primarias CPF e codigo, e possuem referencia estrangeira, impedindo
que, caso um aluno ou um plano estejam associados, eles nao possam ser excluídos nas suas tabelas
principais, pois essa tabela AlunoPlano possui dependencia com elas. Não foi feita uma exclusão
em cascata para, quando, por exemplo, um aluno A ser excluído, todas as suas associações de planos
serem excluídas também. Primeiro as associacoes de 'planos-aluno' precisam ser excluidas e depois o aluno.

5.2 Cadastrar uma ou mais opções de treino, onde cada opção de treino contém uma lista de exercícios.

R: Os treinos sao ligados diretamente com o aluno, podendo este ter varios treinos. Os treinos podem
ter quantos exercicios quiser. Tal qual como feito com a associação de planos com alunos, ao tentar
excluir exercicios ou alunos que estao vinculados a um treino resultará um erro. Sendo necessario
Excluir o treino para depois excluir os exercicios/alunos. Isso acontece pela referencia de chave
estrangeira.

5.3 Para cada exercício, informar: o número de séries, o número mínimo e máximo de repetições, a carga utilizada (em kgs) e o tempo de descanso (em minutos)

R: Depois do aluno ter seu treino vinculado, o instrutor pode alterar esses dados. Ele pode excluir
ou adicionar exercicios tambem.

5.4 Alterar ou excluir opções de treino e os dados dos exercícios cadastrados.

R: É fornecida essa opção de alterar ou excluir treinos, sendo que essa nao tem dependencias estrangeiras

6. Deve ser possível ao aluno, em determinada data, iniciar um treino:

R: Todas as requisições foram implementadas, mostra as cargas, series, repetições, descanso, etc.
Se o instrutor nao registrou nenhuma carga, numero de series nem tempo de descanso sera tudo zero.
O treino, diferentemente do resto do programa, pega a DATA do computador (a do mundo real).

7. Relatórios

7.1 Mostrar as datas em que o aluno compareceu à academia em um intervalo de datas.

R: A data que ele pega é a do mundo real, na qual o seu computador tem registrado.

7.2 Para um determinado exercício, mostrar a evolução de carga ao longo do tempo.

R: Ele pega todas as cargas passadas e suas datas de um exercicio do aluno.
A evolução da carga só é atualizada quando o aluno FAZ de fato o exercicio no treino. Se o aluno nunca fez o exercicio, mostrará '0'.