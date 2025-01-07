--preencher tabelas de setup 

INSERT INTO Epoca ( nr_epoca, data_inicio, data_fim)
VALUES
('2020/2021','20210601', '20210831')
;
 
INSERT INTO Tipo_Evento (id, nome)
VALUES
('SI','Substituicao In'),
('SO','Substituicao Out'),
('AG', 'Auto Golo'),
('GL','Golo'),
('GA','Golo Anulado'),
('CV','Cartao Vermelho'),
('CA','Cartao Amarelo'),
('EX','Expulsao')
;

INSERT INTO Jogador (nr_atleta, nome, data_nasc)
VALUES
(100,'Dionisio Machado','20000101'),
(200,'Luis Pereira','20010507'),
(300,'Arnaldo Dantas','20021201'),
(400,'Cristovao Silva','20020314'),
(500,'Inacio Oliveira', '20020805'),
(600,'Oracio Oliveira', '20020905'),
(700,'Joao Oliveira', '20021005'),
(800,'Martins Oliveira', '20021105'),
(900,'Antonio Oliveira', '20021205'),
(1000,'Ricardo Oliveira', '20020801'),
(1100,'Luis Oliveira', '20020822'),
--
(102,'Andre Machado','20000101'),
(202,'Bruno Pereira','20010507'),
(302,'Abel Dantas','20021201'),
(402,'Tomas Silva','20020314'),
(502,'Jose Oliveira', '20020805'),
(602,'Mario Oliveira', '20020905'),
(702,'Manuel Oliveira', '20021005'),
(802,'Martim Jesus', '20021105'),
(902,'Tiago Sousa', '20021205'),
(1002,'Cristiano Oliveira', '20020801'),
(1102,'Fernando Fernandes', '20020822'),
--
(103,'Manuel Machado','20000101'),
(203,'Miguel Pereira','20010507'),
(303,'Miguel Duarte','20021201'),
(403,'Joao Pereira','20020314'),
(503,'Inacio Dias', '20020805'),
(603,'Sandro Oliveira', '20020905'),
(703,'Antonio Sousa', '20021005'),
(803,'Martins Oliveira', '20021105'),
(903,'Antonio Dias', '20021205'),
(1003,'Ricardo Jesus', '20020801'),
(1103,'Luis Azevedo', '20020822'),
--
(104,'Jaime Sousa','20000101'),
(204,'Joao Costa','20010507'),
(304,'Arnaldo Fonseca','20021201'),
(404,'Cristovao Fonseca','20020314'),
(504,'Jorge Oliveira', '20020805'),
(604,'Tiago Oliveira', '20020905'),
(704,'Sergio Silva', '20021005'),
(804,'Jose Borges', '20021105'),
(904,'Rui Pinho', '20021205'),
(1004,'Manuel Capucho', '20020801'),
(1104,'Simao Oliveira', '20020822')
;

INSERT INTO Posicao (id, descricao)
VALUES
('GR','Guarda-Redes'),
('DC','Defesa Central'),
('DCLESQ','Defesa Lateral Esquerdo'),
('DCLDIR','Defesa Lateral Direito'),
('EESQ','Extremo Esquerdo'),
('EDIR','Extremo Direito'),
('MC','Medio Centro'),
('MO','Medio Ofensivo'),
('MD','Medio Defensivo')
;

INSERT INTO Pais (id, descricao)
VALUES
(351,'Portugal'),
(34,'Espanha'),
(33,'Franca'),
(41,'Suica')
;
GO

INSERT INTO Localidade (id_pais,descricao)
VALUES
(351,'Lisboa'),
(351,'Porto'),
(351,'Braga'),
(351,'Algarve'),
(351,'Coimbra'),
(34,'Madrid'),
(33,'Paris'),
(41,'Berna')
;
GO

INSERT INTO Estadio (nome, id_local, id_pais)
VALUES
('Estadio do Dragao',1,351),
('Estádio Santiago Bernabéu',5,34),
('Estádio Parc des Princes',6,33),
('Wankdorf',7,41)
;
GO


INSERT INTO Equipa (nome, sigla, id_estadio)
VALUES
('FC Porto', 'FCP', 0),
('FC Madrid', 'FCM', 1),
('FC Paris', 'FCP', 2),
('FC Berna','FCB',3)
;

INSERT INTO jornada (nr_epoca, nr_jornada, data_inicio, data_fim)
VALUES
('2020/2021',1,'20210101','20210131'),
('2020/2021',2,'20210201','20210228'),
('2020/2021',3,'20210301','20210331'),
('2020/2021',4,'20210401','20210831');
GO

INSERT INTO Jogador_Contrato (nr_atleta, id_equipa, data_entrada_atleta, data_saida_atleta, nr_camisola)
VALUES
(100, 0,  '20200101', NULL,1),
(200, 0,  '20200101', NULL,2),
(300, 0,  '20200101', NULL,3),
(400, 0,  '20200101', NULL,4),
(500, 0,  '20200101', NULL,5),
(600, 0,  '20200101', NULL,6),
(700, 0,  '20200101', NULL,7),
(800, 0,  '20200101', NULL,8),
(900, 0,  '20200101', NULL,9),
(1000, 0, '20200101', NULL,10),
(1100, 0, '20200101', NULL,11);
GO

INSERT INTO Jogador_Contrato (nr_atleta, id_equipa, data_entrada_atleta, data_saida_atleta, nr_camisola)
VALUES
(102,  1, '20200101', NULL,1),
(202,  1, '20200101', NULL,2),
(302,  1, '20200101', NULL,3),
(402,  1, '20200101', NULL,4),
(502,  1, '20200101', NULL,5),
(602,  1, '20200101', NULL,6),
(702,  1, '20200101', NULL,7),
(802,  1, '20200101', NULL,8),
(902,  1, '20200101', NULL,9),
(1002, 1, '20200101', NULL,10),
(1102, 1, '20200101', NULL,11);

INSERT INTO Jogador_Contrato (nr_atleta, id_equipa, data_entrada_atleta, data_saida_atleta, nr_camisola)
VALUES
(103,  2, '20200101', NULL,1),
(203,  2, '20200101', NULL,2),
(303,  2, '20200101', NULL,3),
(403,  2, '20200101', NULL,4),
(503,  2, '20200101', NULL,5),
(603,  2, '20200101', NULL,6),
(703,  2, '20200101', NULL,7),
(803,  2, '20200101', NULL,8),
(903,  2, '20200101', NULL,9),
(1003, 2, '20200101', NULL,10),
(1103, 2, '20200101', NULL,11);

INSERT INTO Jogador_Contrato (nr_atleta, id_equipa, data_entrada_atleta, data_saida_atleta, nr_camisola)
VALUES
(104,  3, '20200101', NULL,1),
(204,  3, '20200101', NULL,2),
(304,  3, '20200101', NULL,3),
(404,  3, '20200101', NULL,4),
(504,  3, '20200101', NULL,5),
(604,  3, '20200101', NULL,6),
(704,  3, '20200101', NULL,7),
(804,  3, '20200101', NULL,8),
(904,  3, '20200101', NULL,9),
(1004, 3, '20200101', NULL,10),
(1104, 3, '20200101', NULL,11);
GO


INSERT INTO Jogo (id_equipa_casa, id_equipa_fora, nr_epoca, nr_jornada, data_hora_jogo, minutos_intervalo, duracao, id_estadio)
VALUES
(0,1,'2020/2021',1,'2021-01-01 11:00:00',0,90,0),
(2,3,'2020/2021',1,'2021-01-04 14:00:00',0,90,2),
(1,0,'2020/2021',1,'2021-02-01 11:00:00',0,90,1),
(3,2,'2020/2021',1,'2021-02-04 14:00:00',0,90,3);
GO

INSERT INTO Jogador_jogo (nr_atleta, id_jogo, posicao)
VALUES
( 100, 0, 'GR'		),
( 200, 0, 'DC'		),
( 300, 0, 'DCLESQ'	),
( 400, 0, 'DCLDIR'	),
( 500, 0, 'EESQ'	),
( 600, 0, 'EDIR'	),
( 700, 0, 'MC'		),
( 800, 0, 'MO'		),
( 900, 0, 'MD'		),
(1000, 0, 'MD'		),
(1100, 0, 'MD'      ),
(102 , 0, 'GR'      ),
(202 , 0, 'DC'		),
(302 , 0, 'DCLESQ'	),
(402 , 0, 'DCLDIR'	),
(502 , 0, 'EESQ'	),
(602 , 0, 'EDIR'	),
(702 , 0, 'MC'		),
(802 , 0, 'MO'		),
(902 , 0, 'MD'		),
(1002, 0, 'MD'		),
(1102, 0, 'MD'      ),
(103 , 1, 'GR'		),
(203 , 1, 'DC'		),
(303 , 1, 'DCLESQ'	),
(403 , 1, 'DCLDIR'	),
(503 , 1, 'EESQ'	),
(603 , 1, 'EDIR'	),
(703 , 1, 'MC'		),
(803 , 1, 'MO'		),
(903 , 1, 'MD'		),
(1003, 1, 'MD'		),
(1103, 1, 'MD'      ),
(104 , 1, 'GR'		),
(204 , 1, 'DC'		),
(304 , 1, 'DCLESQ'	),
(404 , 1, 'DCLDIR'	),
(504 , 1, 'EESQ'	),
(604 , 1, 'EDIR'	),
(704 , 1, 'MC'		),
(804 , 1, 'MO'		),
(904 , 1, 'MD'		),
(1004, 1, 'MD'		),
(1104, 1, 'MD'      ),
(102 , 2, 'GR'      ),
(202 , 2, 'DC'		),
(302 , 2, 'DCLESQ'	),
(402 , 2, 'DCLDIR'	),
(502 , 2, 'EESQ'	),
(602 , 2, 'EDIR'	),
(702 , 2, 'MC'		),
(802 , 2, 'MO'		),
(902 , 2, 'MD'		),
(1002, 2, 'MD'		),
(1102, 2, 'MD'      ),
( 100, 2, 'GR'		),
( 200, 2, 'DC'		),
( 300, 2, 'DCLESQ'	),
( 400, 2, 'DCLDIR'	),
( 500, 2, 'EESQ'	),
( 600, 2, 'EDIR'	),
( 700, 2, 'MC'		),
( 800, 2, 'MO'		),
( 900, 2, 'MD'		),
(1000, 2, 'MD'		),
(1100, 2, 'MD'      ),
(104 , 3, 'GR'		),
(204 , 3, 'DC'		),
(304 , 3, 'DCLESQ'	),
(404 , 3, 'DCLDIR'	),
(504 , 3, 'EESQ'	),
(604 , 3, 'EDIR'	),
(704 , 3, 'MC'		),
(804 , 3, 'MO'		),
(904 , 3, 'MD'		),
(1004, 3, 'MD'		),
(1104, 3, 'MD'      ),
(103 , 3, 'GR'		),
(203 , 3, 'DC'		),
(303 , 3, 'DCLESQ'	),
(403 , 3, 'DCLDIR'	),
(503 , 3, 'EESQ'	),
(603 , 3, 'EDIR'	),
(703 , 3, 'MC'		),
(803 , 3, 'MO'		),
(903 , 3, 'MD'		),
(1003, 3, 'MD'		),
(1103, 3, 'MD'      )
;
GO

/* tipo_evento:
SI Substituicao In
SO Substituicao Out
AG Auto Golo
GL Golo
GA Golo Anulado
CV Cartao Vermelho
CA Cartao Amarelo
*/
INSERT INTO Evento(id_jogador_jogo, tempo, tipo,id_jogo )
VALUES
(200 ,15, 'GA',0),
(200 ,17, 'CA',0),
(400 ,20, 'GL',0),
(304 ,60, 'SI',1),
(204 , 2, 'GL',1),
(202 , 5, 'CV',2),
(102 ,10, 'CV',2),
(100 , 3, 'SI',2),
(400 , 1, 'GL',2),
(1104,70, 'SO',3),
(904 ,70, 'GL',3),
(903 , 5, 'GL',3),
(1003,35, 'GL',3);
GO
