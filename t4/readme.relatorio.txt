Arquivo explicando dependencias do trabalho, e o que foi feito:

Trabalho feito em dupla: Gabriel Souza Baggio e Tobias Viero


O programa tem as seguintes dependencias:

1. Driver do JDK para compilação em Java.

2. !! Driver do PostGreSQL, visto que a base de dados relacional 
escolhida foi o PostGres.

A Port do banco de dados é '5432', o dbname é 'trabalho1', o user é 'postgres' e a senha é 'root'.
Foi feito com o IntelliJ. Foi usado o trabalho 1 pra testar.


Notas: O arquivo DAO criado considera a primeira coluna como chave primária, os arquivos teste de
exemplo testam as funções da classe DAO.

Métodos DAO criados: Apenas os básicos, Inserir, Buscar, Remover, Atualizar e BuscarTodos.

Atributos e métodos das classes de entidades: Foram criados declarações privadas, sets e gets pra
toda coluna.

Classes de exemplo: Testa as funcionalidades das classes de entidade e classes DAO.
