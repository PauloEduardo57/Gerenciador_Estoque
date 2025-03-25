-- Criação de procedures
-- Cadastrando Produtos
DELIMITER //
	CREATE PROCEDURE cadastrarProduto(
		in p_nome varchar(100),
		in p_descricao text,
		in p_quantidade int, 
		in p_precoCompra decimal(10,2),
		in p_precoVenda decimal(10,2),
		in p_idCategoria int
	)
	BEGIN
		insert into produto (nome, quantidade, preco_compra, preco_venda, descricao, id_categoria)
		values(p_nome, p_quantidade, p_precoCompra, p_precoVenda, p_descricao, p_idCategoria);
	END //
DELIMITER ;

-- Editando Produtos
DELIMITER //
	CREATE PROCEDURE atualizarProduto(
		in p_idProduto int,
        in p_nome varchar(100),
		in p_descricao text,
		in p_quantidade int, 
		in p_precoCompra decimal(10,2),
		in p_precoVenda decimal(10,2),
		in p_idCategoria int
	)
	BEGIN
		update produto
        set nome = p_nome, 
			quantidade = p_quantidade, 
            descricao = p_descricao, 
            preco_compra = p_precoCompra,
            preco_venda = p_precoVenda,
            id_categoria = p_idCategoria
		where id_produto = p_idProduto;
	END //
DELIMITER ;

-- deletando produtos
DELIMITER //
	CREATE PROCEDURE deletarProduto(
		in p_idProduto int
	)
	BEGIN
		delete from produto
        where id_produto = p_idProduto;
	END //
DELIMITER ;

-- Cadastrando Categorias
DELIMITER //
	CREATE PROCEDURE cadastrarCategoria(
		in p_nome varchar(100),
		in p_descricao text
	)
	BEGIN
		insert into categoria (nome, descricao)
		values(p_nome, p_descricao);
	END //
DELIMITER ;

-- Editando Categorias
DELIMITER //
	CREATE PROCEDURE atualizarCategoria(
        in p_idCategoria int,
        in p_nome varchar(100),
		in p_descricao text
		
	)
	BEGIN
		update categoria
        set nome = p_nome, 
            descricao = p_descricao
		where id_categoria = p_idCategoria;
	END //
DELIMITER ;

-- deletando categorias
DELIMITER //
	CREATE PROCEDURE deletarCategoria(
		in p_idCategoria int
	)
	BEGIN
		delete from categoria
        where id_categoria = p_idCategoria;
	END //
DELIMITER ;

-- registrando entrada de produtos no estoque
DELIMITER //
	CREATE PROCEDURE registrar_entrada_estoque(
		IN p_idProduto INT,
		IN p_quantidade INT
	)
	BEGIN
		-- Atualiza a quantidade do produto
		UPDATE produto
		SET quantidade = quantidade + p_quantidade
		WHERE id_produto = p_idProduto;
		
		-- Registra a movimentação
		INSERT INTO MovimentacaoEstoque (id_produto, quantidade, tipo)
		VALUES (p_idProduto, p_quantidade, 'ENTRADA');
	END //
DELIMITER ;

-- registrando saída de produtos no estoque
DELIMITER //
	CREATE PROCEDURE registrar_saida_estoque(
		IN p_idProduto INT,
		IN p_quantidade INT
	)
	BEGIN
		-- Verifica se há quantidade suficiente em estoque
		IF (SELECT quantidade FROM Produto WHERE id_produto = p_idProduto) >= p_quantidade THEN
			-- Atualiza a quantidade do produto
			UPDATE Produto
			SET quantidade = quantidade - p_quantidade
			WHERE id_produto = p_idProduto;
			
			-- Registra a movimentação
			INSERT INTO MovimentacaoEstoque (id_produto, quantidade, tipo)
			VALUES (p_idProduto, p_quantidade, 'SAIDA');
		ELSE
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente para realizar a saída.';
		END IF;
	END //
DELIMITER ;

-- Consultando produtos com filtros
DELIMITER //
	CREATE PROCEDURE consultar_produtos(
		IN p_nome VARCHAR(255),
		IN p_categoria INT,
		IN p_quantidade INT
	)
	BEGIN
		SELECT 
			p.id_produto,
			p.nome AS  nome_produto,
			p.descricao,
			p.quantidade,
			p.preco_compra,
			p.preco_venda,
			c.nome AS nome_categoria
		FROM Produto p
		INNER JOIN Categoria c ON p.id_categoria = c.id_categoria
		WHERE 
			(p_nome IS NULL OR p.nome LIKE CONCAT('%', p_nome, '%')) AND
			(p_categoria IS NULL OR p.id_categoria = p_categoria) AND
			(p_quantidade IS NULL OR p.quantidade >= p_quantidade);
	END //
DELIMITER ;

-- Consultando categorias
DELIMITER //
	CREATE PROCEDURE consultar_categorias()
	BEGIN
		SELECT 
			id_categoria,
			nome,
			descricao
		FROM Categoria;
	END //
DELIMITER ;

-- relatorio de movimentações no estoque
DELIMITER //
	CREATE PROCEDURE relatorio_movimentacao_estoque()
	BEGIN
		SELECT 
			me.id_movimentacao,
			p.nome AS produto_nome,
			me.quantidade,
			me.tipo,
			me.data
		FROM MovimentacaoEstoque me
		JOIN produto p ON me.id_produto = p.id_produto;
	END //
DELIMITER ;

-- relatorio de produtos com pouco estoque
DELIMITER //
	CREATE PROCEDURE relatorio_produtos_baixo_estoque()
	BEGIN
		SELECT 
			p.id_produto,
			p.nome AS produto_nome,
			p.quantidade,
			c.nome AS categoria_nome
		FROM produto p
		JOIN categoria c ON p.id_categoria = c.id_categoria
		WHERE p.quantidade < 15; -- valor base de quantidade minima
	END //
DELIMITER ;

-- relatório de vendas e lucro
DELIMITER //
	CREATE PROCEDURE relatorio_vendas_lucro()
	BEGIN
		SELECT 
			p.id_produto,
			p.nome AS produto_nome,
			SUM(me.quantidade) AS total_vendido,
			p.preco_venda,
			p.preco_compra,
			SUM(me.quantidade * (p.preco_venda - p.preco_compra)) AS lucro_total
		FROM MovimentacaoEstoque me
		JOIN produto p ON me.id_produto = p.id_produto
		WHERE me.tipo = 'SAIDA'  -- Considera apenas as saídas (vendas)
		GROUP BY p.id_produto;
	END //
DELIMITER ;

-- trigger de alerta
DELIMITER //
	CREATE TRIGGER verificar_estoque_baixo
	BEFORE UPDATE ON Produto
	FOR EACH ROW
	BEGIN
		-- Supondo que a quantidade mínima seja 10
		IF NEW.quantidade < 10 THEN
			-- Bloquear a operação se a quantidade for inferior a 10
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente: Produto com estoque baixo.';
		END IF;
	END //
DELIMITER ;