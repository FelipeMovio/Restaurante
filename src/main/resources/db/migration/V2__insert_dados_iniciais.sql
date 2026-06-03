INSERT INTO mesas ( numero, descricao, capacidade) VALUES
                                                       (1,'Mesa proxima a entrada',4),
                                                       (2,'Mesa central',4),
                                                       (3,'Mesa proxima a janela',2),
                                                       (4,'Mesa familia',6),
                                                       (5,'Mesa proxima externa',4);

INSERT INTO categorias_produtos(nome) VALUES
                                          ('Entradas'),
                                          ('Pratos Principais'),
                                          ('Bebidas'),
                                          ('Sobremesas');

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Batata Frita', 'Porcao de batata frita crocante', 28.90, 15
FROM categorias_produtos WHERE nome = 'Entradas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Onion Rings', 'Aneis de cebola empanados', 24.90, 12
FROM categorias_produtos WHERE nome = 'Entradas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Isca de Frango', 'Cubos de frango empanados', 32.90, 18
FROM categorias_produtos WHERE nome = 'Entradas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Bruschetta', 'Pao italiano com tomate e manjericao', 22.90, 10
FROM categorias_produtos WHERE nome = 'Entradas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Hamburguer Artesanal', 'Hamburguer bovino com queijo e bacon', 42.90, 25
FROM categorias_produtos WHERE nome = 'Pratos Principais';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'File de Frango Grelhado', 'Acompanhado de arroz e legumes', 39.90, 20
FROM categorias_produtos WHERE nome = 'Pratos Principais';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Picanha na Chapa', 'Picanha grelhada com fritas', 69.90, 35
FROM categorias_produtos WHERE nome = 'Pratos Principais';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Spaghetti a Bolonhesa', 'Massa ao molho bolonhesa artesanal', 44.90, 25
FROM categorias_produtos WHERE nome = 'Pratos Principais';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Coca-Cola Lata', 'Refrigerante 350ml', 7.90, 1
FROM categorias_produtos WHERE nome = 'Bebidas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Suco de Laranja', 'Suco natural de laranja 500ml', 12.90, 5
FROM categorias_produtos WHERE nome = 'Bebidas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Agua Mineral', 'Agua sem gas 500ml', 4.90, 1
FROM categorias_produtos WHERE nome = 'Bebidas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Chopp Pilsen', 'Caneca de chopp artesanal 500ml', 14.90, 2
FROM categorias_produtos WHERE nome = 'Bebidas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Petit Gateau', 'Bolo de chocolate com sorvete', 24.90, 15
FROM categorias_produtos WHERE nome = 'Sobremesas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Pudim de Leite', 'Pudim tradicional com calda de caramelo', 14.90, 3
FROM categorias_produtos WHERE nome = 'Sobremesas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Mousse de Maracuja', 'Mousse cremoso de maracuja', 12.90, 3
FROM categorias_produtos WHERE nome = 'Sobremesas';

INSERT INTO produtos(categoria_id, nome, descricao, preco, tempo_preparo_minutos)
SELECT id, 'Torta de Limão', 'Torta gelada com cobertura de merengue', 16.90, 5
FROM categorias_produtos WHERE nome = 'Sobremesas';