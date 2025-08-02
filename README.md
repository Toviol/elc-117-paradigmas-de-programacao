# Trabalhos da Disciplina de Paradigmas de Programação

Este repositório reúne os trabalhos desenvolvidos ao longo da disciplina de Paradigmas de Programação, abordando diferentes conceitos e práticas de programação. Abaixo, você encontrará uma descrição detalhada de cada trabalho e orientações para execução.

## Estrutura do Repositório

### Trabalho 1 (t1)

Este projeto é um sistema desenvolvido em Java utilizando a estrutura Maven. Ele implementa funcionalidades para o gerenciamento de alunos, planos, exercícios, presenças e treinos em um ambiente de academia. A organização do código segue boas práticas de programação, como o uso do padrão DAO para acesso a dados e a separação entre camadas de modelo, serviço e utilitários.

- **src/main/java/org/example/DAO/**: Contém classes responsáveis pelo acesso ao banco de dados, como `AlunoDAO`, `PlanoDAO`, `ExercicioDAO`, entre outras.
- **src/main/java/org/example/model/**: Define os modelos das entidades do sistema, como `Aluno`, `Plano`, `Exercicio`, `Treino`, etc.
- **src/main/java/org/example/service/**: Implementa a lógica de negócio, como operações relacionadas a alunos e instrutores.
- **src/main/java/org/example/util/**: Funções utilitárias, incluindo métodos para configuração e interação com o banco de dados.
- **src/main/resources/**: Arquivos de recursos do projeto.
- **src/test/java/**: Testes automatizados para validar as funcionalidades.
- **pom.xml**: Arquivo de configuração do Maven para gerenciamento de dependências.
- **readme.relatorio.txt**: Relatório detalhado sobre o trabalho.

### Trabalho 2 (t2)

Este trabalho é apresentado em formato de documento PDF (`t2.pdf`) e contém uma análise teórica ou prática relacionada à disciplina. Consulte o arquivo para mais detalhes.

### Trabalho 3 (t3)

Uma coleção de exercícios de programação em Java. Cada arquivo (`ex1.java`, `ex2.java`, etc.) representa um exercício individual, abordando diferentes conceitos e desafios de programação. Esses exercícios podem ser compilados e executados individualmente.

### Trabalho 4 (t4)

Outro projeto em Java que explora o uso de DAOs, entidades e conexão com banco de dados. Este trabalho inclui exemplos práticos de como utilizar as classes implementadas.

- **src/ClassesDAO/**: Contém as classes DAO para manipulação de entidades no banco de dados.
- **src/Entidades/**: Define os modelos das entidades, como `Aluno`, `Plano` e `Exercicio`.
- **src/conection/**: Classe responsável pela conexão com o banco de dados.
- **src/Exemplos/**: Exemplos de uso das entidades e DAOs, como `AlunoExemplo`, `PlanoExemplo`, etc.
- **readme.relatorio.txt**: Relatório detalhado sobre o trabalho.

## Como Executar

### Trabalho 1

Para executar o projeto, certifique-se de ter o Maven instalado. Navegue até o diretório `t1` e utilize os comandos Maven para compilar e executar o projeto:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.Main"
```

### Trabalho 3

Compile e execute os arquivos `.java` individualmente. Por exemplo:

```bash
javac ex1.java
java ex1
```

### Trabalho 4

Compile os arquivos `.java` e execute os exemplos para testar as funcionalidades. Certifique-se de configurar corretamente a conexão com o banco de dados antes de executar.

## Autor

Tobias Viero

---

Este repositório será atualizado conforme novos trabalhos forem desenvolvidos ou modificados.
