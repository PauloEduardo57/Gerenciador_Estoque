# Gerenciador de Estoque

O Gerenciador de Estoque é um sistema completo desenvolvido em Java com integração a um banco de dados relacional utilizando SQL. Projetado para oferecer uma gestão eficiente de estoques, ele atende empresas de diferentes portes, facilitando o controle de produtos, categorias, movimentações e relatórios analíticos.

## 📋 Principais Funcionalidades

1. CRUD de Categorias: Cadastro, atualização, exclusão e consulta de categorias de produtos.

2. CRUD de Produtos: Cadastro, atualização, exclusão e consulta de produtos, com filtros avançados.

3. Movimentações de Estoque: Registro de entradas e saídas de produtos e geração de relatórios detalhados.

4. Relatórios de Vendas e Lucro: Análise financeira por produto, com relatórios completos.

5. Relatório de Baixo Estoque: Identificação de produtos com estoque abaixo de um limite especificado.

## 🚀 Como Usar o Sistema
### 1. Clonar o Repositório
Para obter o código do projeto, siga os passos abaixo:

Certifique-se de ter o Git instalado no seu computador.

Abra o terminal e execute o comando:

git clone (https://github.com/PauloEduardo57/Gerenciador_Estoque)

### 2. Configurar o Banco de Dados
Antes de executar o sistema, é necessário criar o banco de dados no MySQL:


1- Certifique-se de que o MySQL esteja instalado e configurado.

2- Abra o MySQL Workbench ou o terminal do MySQL.

3- Localize os arquivos de script SQL fornecidos no projeto:

`Tables.sql`

`Procedures_triggers.sql`

4- Execute ambos os scripts para criar as tabelas e configurar os procedimentos e gatilhos no banco de dados.

## 3. Configurar a Conexão com o Banco de Dados

O arquivo Conexao.java gerencia a conexão do sistema com o banco de dados. Atualize as credenciais do MySQL seguindo os passos abaixo:


1- Abra o arquivo `Conexao.java` na pasta no editor de código.

2- Localize a constante URL e verifique se ela corresponde ao endereço do seu servidor MySQL:

```java
private static final String URL = "jdbc:mysql://localhost:3306/gerenciador_estoque";
```

3- Localize as constantes `USUARIO`, `SENHA` e `URL`.

4- Substitua pelos valores das suas credenciais:

```java
private static final String USUARIO = "root"; // Alterar se o usuário não for 'root'
private static final String SENHA = "SuaSenha"; // Substituir por sua senha
```

## 4. Executar o Sistema
Certifique-se de que o Java e sua IDE estejam configurados corretamente.

Localize o arquivo Main.java e execute-o para iniciar o programa.
