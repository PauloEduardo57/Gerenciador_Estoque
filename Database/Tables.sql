CREATE DATABASE Gerenciador_Estoque;

-- Criação de tabelas
-- tabela categoria
CREATE TABLE categoria(
	id_categoria int unsigned auto_increment primary key,
    nome varchar(100) not null,
    descricao text
);

-- tabela produtos
CREATE TABLE produto(
	id_produto int unsigned auto_increment primary key, 
    id_categoria int unsigned not null,
    nome varchar(100) not null,
    quantidade int not null check(quantidade >= 0),
    preco_compra decimal(10,2) not null check(preco_compra >= 0),
    preco_venda decimal(10,2) not null check(preco_venda >= 0),
    descricao text,
    foreign key(id_categoria) references categoria(id_categoria) on delete cascade,
    index(id_categoria)
);

-- tabela de movimentações no estoque de produtos
CREATE TABLE MovimentacaoEstoque(
	id_movimentacao int unsigned auto_increment primary key, 
    id_produto int unsigned not null, -- registra o id do produto, msm que seja excluido posteriomente
    quantidade int not null check(quantidade >= 0),
    tipo enum('ENTRADA', 'SAIDA') not null,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    index(id_produto)
);