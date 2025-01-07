/* BD Remota : Grupo 1
	use DIAS_Grupo1_2021;
	go
*/

/* BD Local : */
	CREATE DATABASE superliga;
	go
	use superliga;
	go


--criar tabelas

/* Epoca: Tabela que regista a informacao da epoca*/
CREATE TABLE Epoca (
    nr_epoca 		NVARCHAR(10) CONSTRAINT pk_epoca_nr_epoca PRIMARY KEY NOT NULL,
    data_inicio 	DATE NOT NULL,
    data_fim 		DATE NOT NULL
)

/* Tipo_Evento: Tabela que regista o tipo de evento ex: 'Substituicao';'Golo';'Cartao Vermelho'*/
CREATE TABLE Tipo_Evento (
    id 		NVARCHAR(5) CONSTRAINT pk_tipo_evento_id PRIMARY KEY NOT NULL,  
	nome 	NVARCHAR(50) NOT NULL
)
CREATE NONCLUSTERED INDEX idx_tipo_evento_nome
ON Tipo_Evento ( nome);

/* Jogador: Tabela que regista a informacao do atleta*/
CREATE TABLE Jogador (
    nr_atleta 	INTEGER CONSTRAINT pk_jogador_nr_atleta PRIMARY KEY NOT NULL, 
	nome		NVARCHAR(50) NOT NULL,
	data_nasc 	DATE NOT NULL
)

/* Posicao: Tabela que regista a posicao de um jogador ex : 'GR-Guarda-Redes'; 'DC-Defesa Central' */
CREATE TABLE Posicao (
    id			NVARCHAR(10) CONSTRAINT pk_posicao_id PRIMARY KEY NOT NULL, 
	descricao	NVARCHAR(50) NOT NULL
)

/* Pais: Tabela que regista a informacao do pais */
CREATE TABLE Pais (
    id 			INTEGER CONSTRAINT pk_pais_id PRIMARY KEY NOT NULL, 
	descricao 	NVARCHAR(50) NOT NULL
)
GO

/* Localidade: Tabela que regista a localidade*/
CREATE TABLE Localidade (
    id 			INT IDENTITY(0,1) NOT NULL, 
	id_pais 	INTEGER NOT NULL,
	descricao	NVARCHAR(50) NOT NULL,
CONSTRAINT fk_localidade_pais FOREIGN KEY (id_pais) REFERENCES pais(id),
CONSTRAINT pk_localidade PRIMARY KEY (id asc, id_pais asc)
)
GO

/* Estadio: Tabela que guarda informacao do estadio*/
CREATE TABLE Estadio (
    id 			INT IDENTITY(0,1) CONSTRAINT pk_estadio_id PRIMARY KEY NOT NULL, 
	nome 		NVARCHAR(100) NOT NULL,
	id_local	INTEGER NOT NULL,
	id_pais 	INTEGER NOT NULL,
CONSTRAINT fk_estadio_localidade FOREIGN KEY (id_local, id_pais) REFERENCES localidade(id, id_pais)
)
GO

/* Equipa: Tabela que regista informacao inerente a equipa*/
CREATE TABLE Equipa (
    id		INT IDENTITY(0,1) CONSTRAINT pk_equipa_id PRIMARY KEY NOT NULL, 
	nome	NVARCHAR(50) NOT NULL,
	sigla	NVARCHAR(3) ,
	id_estadio 	INTEGER NOT NULL CONSTRAINT fk_equipa_estadio FOREIGN KEY (id_estadio) REFERENCES estadio(id)
)

/* Jornada: Tabela que armazena as jornadas referentes a uma epoca */
CREATE TABLE Jornada (
    nr_epoca 		NVARCHAR(10) NOT NULL, 
	nr_jornada 		INTEGER NOT NULL CONSTRAINT CK_jornada_nr_jornada CHECK(nr_jornada >=1 and nr_jornada <=38 ), 
    data_inicio 	DATE NOT NULL,
    data_fim 		DATE NOT NULL,
CONSTRAINT fk_jornada_epoca FOREIGN KEY (nr_epoca) REFERENCES epoca(nr_epoca),
CONSTRAINT pk_jornada PRIMARY KEY (nr_epoca asc, nr_jornada asc)
)
GO

/* Jogador_Contrato: Tabela que regista a equipa onde um jogador joga ou jogou */
CREATE TABLE Jogador_Contrato (
    nr_atleta 			INTEGER NOT NULL, 
	id_equipa 			INTEGER NOT NULL,
	data_entrada_atleta DATE NOT NULL,
	data_saida_atleta 	DATE ,
	nr_camisola 		INTEGER NOT NULL,
CONSTRAINT fk_jogador_contrato_equipa FOREIGN KEY (id_equipa) REFERENCES equipa(id),	
CONSTRAINT fk_jogador_contrato_jogador FOREIGN KEY (nr_atleta) REFERENCES jogador(nr_atleta),
CONSTRAINT pk_jogador_contrato PRIMARY KEY (nr_atleta asc, data_entrada_atleta asc)	
)

/* Jogo: Tabela que regista o jogo entre equipa da casa e equipa de fora */
CREATE TABLE Jogo (
    id					INT IDENTITY(0,1) CONSTRAINT pk_jogo_id PRIMARY KEY NOT NULL,
	id_equipa_casa 		INTEGER NOT NULL,
	id_equipa_fora 		INTEGER NOT NULL,
	nr_epoca 			NVARCHAR(10) NOT NULL,
	nr_jornada 			INTEGER NOT NULL,
	data_hora_jogo 		DATETIME NOT NULL,
	minutos_intervalo 	INTEGER NOT NULL DEFAULT '45',
	duracao 			INTEGER NOT NULL DEFAULT '90',
	id_estadio 			INTEGER NOT NULL,
CONSTRAINT fk_jogo_equipa_casa FOREIGN KEY (id_equipa_casa) REFERENCES equipa(id),	
CONSTRAINT fk_jogo_equipa_fora FOREIGN KEY (id_equipa_fora) REFERENCES equipa(id),	
CONSTRAINT fk_jogo_jornada FOREIGN KEY (nr_epoca,nr_jornada) REFERENCES jornada(nr_epoca,nr_jornada),
CONSTRAINT fk_jogo_estadio FOREIGN KEY (id_estadio) REFERENCES estadio(id)
)
GO

/* Jogador_Jogo: Tabela que regista o onze inicial de um jogo */
CREATE TABLE Jogador_Jogo (
    nr_atleta 	INTEGER NOT NULL,
	id_jogo 	INTEGER NOT NULL,
	posicao 	NVARCHAR(10) NOT NULL,
CONSTRAINT fk_jogador_jogo_jogador FOREIGN KEY (nr_atleta) REFERENCES jogador(nr_atleta),
CONSTRAINT fk_jogador_jogo_jogo FOREIGN KEY (id_jogo) REFERENCES jogo(id),
CONSTRAINT fk_jogador_jogo_posicao FOREIGN KEY (posicao) REFERENCES posicao(id),
CONSTRAINT pk_jogador_jogo PRIMARY KEY (nr_atleta asc, id_jogo asc)
)
GO

/* Evento: Tabela que representa todos os eventos do jogo: substituicoes, golos, cartoes */
CREATE TABLE Evento (
    id_jogador_jogo 	INTEGER NOT NULL,
	id_jogo 			INTEGER NOT NULL,
	tempo 				DECIMAL(4,2) NOT NULL,
	tipo 				NVARCHAR(5) NOT NULL,
CONSTRAINT fk_evento_jogador_jogo FOREIGN KEY (id_jogador_jogo, id_jogo) REFERENCES jogador_jogo(nr_atleta, id_jogo),
CONSTRAINT fk_evento_tipo_evento FOREIGN KEY (tipo) REFERENCES tipo_evento(id),
CONSTRAINT pk_evento PRIMARY KEY (id_jogador_jogo asc, id_jogo asc, tempo asc)	
)
GO

/* trigger_DoisAmarelos */
CREATE OR ALTER TRIGGER trigger_DoisAmarelos
ON EVENTO
FOR INSERT, UPDATE
AS
BEGIN
	DECLARE @playerYellowCards INT,
			@yellowCardId NVARCHAR(5),
			@idJogo INT,
			@idJogador INT,
			@tempo INT,
			@redCardId INT,
			@expeledEventId INT;


	SELECT @idJogo = id_jogo,
		   @idJogador = id_jogador_jogo,
		   @tempo = tempo
	FROM INSERTED;

	SET @yellowCardId =  (SELECT id
						 FROM Tipo_Evento te
						 WHERE te.id = 'CA');

	SET @playerYellowCards = (SELECT COUNT(1)
						 	FROM  Evento e
						 	WHERE e.id_jogo = @idJogo 			 AND
						 		  e.id_jogador_jogo = @idJogador AND
						 		  e.tipo = @yellowCardId);

						 		 -- Se este insert/update for o 3 faz rollback

	IF (@playerYellowCards = 2)
	BEGIN
		-- Se o erro for mais de dois cartões amarelos
		-- Atribui vermelho e expulsa o jogador
			SET @redCardId = (SELECT id
						 FROM Tipo_Evento
						 WHERE id = 'CV');

			SET @expeledEventId = (SELECT id
								  FROM Tipo_Evento
								  WHERE ID = 'EX');

			INSERT INTO Evento (id_jogador_jogo, id_jogo, tempo, tipo)
			VALUES
				-- Evento cartão vermelho
				  (@idJogador, @idJogo, (@tempo + 0.01), @redCardId)
				-- Evento de Expulsao
				, (@idJogador, @idJogo, (@tempo + 0.02), @expeledEventId);
	END
	ELSE IF (@playerYellowCards = 3)
		THROW 57001, 'O jogador já tem dois cartões amarelos', 1
END
GO

/* trigger_JogadorContrato : Este trigger tem como objetivo impedir que um atleta esteja no ativo em mais que um clube */ 
CREATE OR ALTER TRIGGER trigger_JogadorContrato
ON Jogador_Contrato
AFTER INSERT, UPDATE
AS 
BEGIN
DECLARE		@v_nr_atleta INTEGER, 
			@v_data_entrada_atleta DATE, 
			@v_data_saida_atleta DATE,
			@v_cont INTEGER,
			@v_exist_contr_end_date_null INT,
			@v_error INTEGER,
			@v_error_msg NVARCHAR(1000) ;

			SELECT @v_nr_atleta =nr_atleta,
			@v_data_entrada_atleta=data_entrada_atleta,  
			@v_data_saida_atleta=data_saida_atleta    
			FROM 
			inserted;

	BEGIN TRY
		--
		if (@v_data_entrada_atleta > @v_data_saida_atleta )
		begin
		    --
			set @v_error =54000;
			set @v_error_msg ='ERRO: A data de entrada não pode ser superior à data de saida'  ;
			ROLLBACK TRANSACTION;
			THROW 51000, '', 1
		end
		--
		select @v_exist_contr_end_date_null=count(1) 
		from Jogador_Contrato JC where JC.nr_atleta= @v_nr_atleta and JC.data_saida_atleta is not null;

		if (@v_exist_contr_end_date_null > 0)
		begin
		    --
			set @v_error =53000;
			set @v_error_msg ='ERRO: O atleta já tem contrato ativo, sem data fim'  ;
			ROLLBACK TRANSACTION;
			THROW 51000, '', 1
		end

		--
		SET @v_cont=0;
		--
		select @v_cont=count(1)
		from Jogador_Contrato JC
		where JC.nr_atleta= @v_nr_atleta and
		@v_data_entrada_atleta between JC.data_entrada_atleta and jc.data_saida_atleta;
		--
		IF @v_cont >1 BEGIN
 			set @v_error =52000;
			set @v_error_msg ='ERRO: O atleta já tem contrato ativo'  ;
			ROLLBACK TRANSACTION;
			THROW 51000, '', 1
		END
	END TRY
	BEGIN CATCH
		THROW @v_error,@v_error_msg, 1		
	END CATCH;
END
GO

/* view_MelhoresEquipasMarcadores: Informação das melhores equipas marcadoras */
 CREATE OR ALTER VIEW  view_MelhoresEquipasMarcadores
AS
SELECT 
P.id AS 'EquipaId',
P.nome as 'EquipaNome',
JG.nr_epoca as 'Epoca',
JG.nr_jornada as 'Jornada',
COUNT(1) as 'Golos'
FROM
Tipo_Evento T, Evento E, Jogador J, Jogador_Contrato JC, Equipa P, jogo JG
where
T.id='GL' AND
E.tipo=T.id AND
J.nr_atleta=E.id_jogador_jogo AND
JC.nr_atleta=J.nr_atleta AND
P.id=JC.id_equipa AND
JG.id=E.id_jogo AND
JC.data_entrada_atleta  <= JG.data_hora_jogo AND
(JC.data_saida_atleta IS NULL OR JC.data_saida_atleta > JG.data_hora_jogo)
GROUP BY P.id,P.nome,JG.nr_epoca, JG.nr_jornada  
GO

/* view_MelhoresMarcadoresGerais: Informação dos melhores marcadores gerais */
CREATE OR ALTER VIEW  view_MelhoresMarcadoresGerais
AS
SELECT 
J.nr_atleta AS 'AtletaNr',
J.nome as 'AtletaNome',
JG.nr_epoca as 'Epoca',
JG.nr_jornada as 'Jornada',
COUNT(1) as 'Golos'
FROM
Tipo_Evento T , Evento E, Jogador J,  jogo JG, Jogador_Jogo JJ
where
T.id='GL' AND
E.tipo=T.id AND
JJ.id_jogo=E.id_jogo AND
JJ.nr_atleta=E.id_jogador_jogo AND 
J.nr_atleta=E.id_jogador_jogo AND
JG.id=E.id_jogo  
GROUP BY J.nr_atleta,J.nome,JG.nr_epoca, JG.nr_jornada 
GO

/* view_OnzeInicialJogo: Informação do onze inicial de um determinado jogo */
CREATE OR ALTER VIEW  view_OnzeInicialJogo
AS
SELECT
'Casa' as 'equipa',
JGJ.nr_atleta as 'numeroAtleta', 
J.nome as 'nome',
JGJ.id_jogo as 'jogo',
JG.data_hora_jogo as 'dataJogo',
P.descricao as 'posicao',
E.nome as 'nomeEquipa',
JG.id AS 'idJogo',
JG.nr_epoca as 'Epoca',
JG.nr_jornada as 'Jornada'
FROM
jogo JG , jogador_jogo JGJ, jogador J, posicao P, equipa E, Jogador_Contrato JC
where 
JGJ.id_jogo = JG.id AND
P.id=JGJ.posicao AND
J.nr_atleta=JGJ.nr_atleta AND
E.id=JG.id_equipa_casa AND
JC.nr_atleta=J.nr_atleta AND
JG.id_equipa_casa  =JC.id_equipa
UNION ALL
SELECT
'Fora' as 'equipa',
JGJ.nr_atleta as 'numeroAtleta', 
J.nome as 'nome',
JGJ.id_jogo as 'jogo',
JG.data_hora_jogo as 'dataJogo',
P.descricao as 'posicao',
E.nome as 'equipa',
JG.id AS 'idJogo',
JG.nr_epoca as 'Epoca',
JG.nr_jornada as 'Jornada'
FROM
jogo JG , jogador_jogo JGJ, jogador J, posicao P, equipa E, Jogador_Contrato JC
where 
JGJ.id_jogo = JG.id AND
P.id=JGJ.posicao AND
J.nr_atleta=JGJ.nr_atleta AND
E.id=JG.id_equipa_fora AND
JC.nr_atleta=J.nr_atleta AND
JG.id_equipa_fora=JC.id_equipa AND
JC.data_entrada_atleta  <= JG.data_hora_jogo AND 
(JC.data_saida_atleta IS NULL OR JC.data_saida_atleta > JG.data_hora_jogo)
GO

/* view_EquipasComMaisVitorias */
CREATE OR ALTER VIEW view_EquipasComMaisVitorias
AS
SELECT nr_epoca, equipa, COUNT(1) QtdVitorias from (
SELECT * FROM (
-- jogos em casa
SELECT * FROM (
SELECT
nr_epoca,
equipa,
COALESCE (sum(QtdGolosEquipa) ,0 ) as QtdGolosEquipa,
COALESCE (sum(QtdGolosEquipaAdversaria),0) as QtdGolosEquipaAdversaria
FROM (
SELECT J.nr_epoca as [nr_epoca],
J.id_equipa_casa as equipa,
COUNT(1)QtdGolosEquipa,
0 QtdGolosEquipaAdversaria
FROM  Jogo J,
Tipo_Evento TE,
Evento E,
Jogador_Contrato JC
WHERE
TE.id='GL' AND
E.tipo=TE.id AND
E.id_jogo=j.id AND
JC.id_equipa=J.id_equipa_casa AND
JC.nr_atleta=E.id_jogador_jogo
GROUP BY j.nr_epoca, j.id_equipa_casa
UNION ALL
SELECT
J.nr_epoca as [nr_epoca],
J.id_equipa_casa as equipa,
0 QtdGolosEquipa,
COUNT(1) QtdGolosEquipaAdversaria
FROM  Jogo J,
Tipo_Evento TE,
Evento E,
Jogador_Contrato JC
WHERE
TE.id='GL' AND
E.tipo=TE.id AND
E.id_jogo=j.id AND
JC.id_equipa=J.id_equipa_fora AND
JC.nr_atleta=E.id_jogador_jogo
group by j.nr_epoca, j.id_equipa_casa
) A group by nr_epoca, equipa
) A2
where A2.QtdGolosEquipa > A2.QtdGolosEquipaAdversaria
UNION ALL
-- jogos fora

SELECT * FROM (
SELECT
nr_epoca,
equipa,
COALESCE (sum(QtdGolosEquipa) ,0 ) as QtdGolosEquipa,
COALESCE (sum(QtdGolosEquipaAdversaria),0) as QtdGolosEquipaAdversaria
FROM (
SELECT
J.nr_epoca as [nr_epoca],
J.id_equipa_fora as equipa,
COUNT(1) QtdGolosEquipa,
0 QtdGolosEquipaAdversaria
FROM  Jogo J,
Tipo_Evento TE, Evento E,
Jogador_Contrato JC
WHERE
TE.id='GL' AND
E.tipo=TE.id AND
E.id_jogo=j.id AND
JC.id_equipa=J.id_equipa_fora AND
JC.nr_atleta=E.id_jogador_jogo
group by J.nr_epoca, J.id_equipa_fora
union all
SELECT
J.nr_epoca as [nr_epoca],
J.id_equipa_fora as equipa ,
0 QtdGolosEquipa,
COUNT(1) QtdGolosEquipaAdversaria
FROM  Jogo J,
Tipo_Evento TE,
Evento E,
Jogador_Contrato JC
WHERE TE.id='GL' AND
E.tipo=TE.id AND
E.id_jogo=j.id AND
JC.id_equipa=J.id_equipa_CASA AND
JC.nr_atleta=E.id_jogador_jogo
group by J.nr_epoca, J.id_equipa_fora
) C group by nr_epoca, equipa
) C2
where C2.QtdGolosEquipa > C2.QtdGolosEquipaAdversaria
) D
) E group by nr_epoca, equipa
GO

-- Cartoes gerais jogador
CREATE OR ALTER VIEW view_CartoesGeraisJogador AS
	SELECT
		J.nr_epoca as [Época],
		Jogador.nome as [Jogador],
		TE.Nome as [Cartão],
		Count(TE.id) as [Contagem]
	FROM
		Evento E
	INNER JOIN Tipo_Evento TE ON
		E.tipo = TE.id AND
		TE.id LIKE 'C%'
	INNER JOIN Jogador_Jogo JJ ON
		JJ.nr_atleta = E.id_jogador_jogo AND
		JJ.id_jogo = E.id_jogo
	INNER JOIN Jogo J ON
		J.id = JJ.id_jogo
	INNER JOIN Jogador ON
		Jogador.nr_atleta = E.id_jogador_jogo
	GROUP BY
		J.nr_epoca ,
		jogador.nome,
		TE.nome
GO
-- Cartoes por equipa
CREATE OR ALTER VIEW view_CartoesGeraisEquipa AS
	SELECT
		J.nr_epoca as [Época],
		Equipa.nome as [Equipa],
		TE.Nome as [Cartão],
		Count(TE.id) as [Contagem]
	FROM
		Evento E
	INNER JOIN Tipo_Evento TE ON
		E.tipo = TE.id
		AND TE.id LIKE 'C%'
	INNER JOIN Jogador_Jogo JJ ON
		JJ.id_jogo = E.id_jogo AND
		JJ.nr_atleta = E.id_jogador_jogo
	INNER JOIN Jogo J ON
		J.id = JJ.id_jogo
	INNER JOIN Jogador ON
		Jogador.nr_atleta = E.id_jogador_jogo
	INNER JOIN jogador_contrato JC ON
		JC.nr_atleta = JJ.nr_atleta
	INNER JOIN Equipa ON
		JC.id_equipa = Equipa.id
	GROUP BY
		J.nr_epoca,
		Equipa.nome,
		TE.nome
GO

/* User Stored Procedure (USP) : usp_RegistarJogo : Este procedure tem como objetivo registar o jogo */
CREATE OR ALTER PROCEDURE usp_RegistarJogo @id_equipa_casa INTEGER, @id_equipa_fora INTEGER, @nr_epoca NVARCHAR(10) , @nr_jornada INTEGER,
										   @data_hora_jogo DATETIME, @minutos_intervalo INTEGER, @duracao INTEGER, @id_estadio INTEGER
AS
BEGIN
	INSERT INTO Jogo(id_equipa_casa, id_equipa_fora, nr_epoca, nr_jornada, data_hora_jogo, minutos_intervalo, duracao, id_estadio)
	values (@id_equipa_casa, @id_equipa_fora, @nr_epoca, @nr_jornada, @data_hora_jogo, @minutos_intervalo, @duracao, @id_estadio)
	print '0-Sucesso'
	IF @@ROWCOUNT =1 
		RETURN 0
	ELSE 
		RETURN 1
END
GO

/* User Stored Procedure (USP) : usp_RegistarJogadorJogo : Este procedure tem como objetivo registar o jogador do jogo */
CREATE OR ALTER PROCEDURE usp_RegistarJogadorJogo @nr_atleta INTEGER, @id_jogo INTEGER, @posicao NVARCHAR(10)
AS
BEGIN
	INSERT INTO Jogador_Jogo (nr_atleta, id_jogo, posicao)
	values (@nr_atleta, @id_jogo, @posicao)
	print '0-Sucesso'
	IF @@ROWCOUNT =1 
		RETURN 0
	ELSE 
		RETURN 1
END
GO

/* Procedimento que regista eventos aliados a um jogo. */
CREATE OR ALTER PROCEDURE usp_RegistarEvento
	@id_evento INT,
	@id_jogo INT,
	@id_jogador INT,
	@tempo INT
AS
BEGIN

	DECLARE @doesPlayerExistInGame BIT,
			@doesGameExist BIT,
		    @doesEventTypeExist BIT,
		    @redCardId INT,
		    @expeledEventId INT;

	SET @doesGameExist = ( SELECT COUNT(1)
						   FROM Jogo
						   WHERE id = @id_jogo );

	IF (@doesGameExist = 0)
		THROW 55100, 'O jogo introduzido não existe.', 1

	-- Verifica se o jogador foi convocado e/ou a jogar em campo no tempo dado (substituicoes)
	SET @doesPlayerExistInGame = ( SELECT COUNT(1)
								   FROM Jogador_Jogo jj
								   WHERE jj.nr_atleta = @id_jogador AND
									     jj.id_jogo = @id_jogo AND
										 jj.nr_atleta NOT IN ( SELECT id_jogador_jogo
										 					   FROM  Evento e
										 					   WHERE (LOWER(tipo) = 'substituicao in'  AND e.tempo <= @tempo) OR
																     (LOWER(tipo) = 'substituicao out' AND e.tempo >= @tempo) OR
										 						     (LOWER(tipo) = 'cartao vermelho'  AND e.tempo <= @tempo)));

	 -- Verifica se o tipo de evento e valido
	SET @doesEventTypeExist = ( SELECT COUNT(1)
								FROM Tipo_Evento
								WHERE id = @id_evento );

	IF (@doesEventTypeExist = 0)
		THROW 51300, 'O Evento introduzido não existe.', 1
	ELSE IF (@doesPlayerExistInGame = 0)
		THROW 51001, 'O Jogador não foi convocado ou não está em campo no dado tempo.', 1
	ELSE
	BEGIN
		-- Introducao de dados
		INSERT INTO Evento (id_jogo, id_jogador_jogo, tipo, tempo)
		VALUES (@id_jogo, @id_jogador, @id_evento, @tempo)
	END
END
GO
/* Função : fn_Vitorias : Esta função devolve a quantidade de vitorias , para uma equipa numa epoca.
PARAMETROS ENTRADA:
	EPOCA
	EQUIPA
PARAMETROS DE SAÍDA
	Quantidade de vitorias
*/
/* Função : fn_Vitorias : Esta função devolve a quantidade de vitorias , para uma equipa numa epoca.
PARAMETROS ENTRADA:
	EPOCA
	EQUIPA
PARAMETROS DE SAÍDA
	Quantidade de vitorias
*/


CREATE OR ALTER FUNCTION fn_Vitorias (@nr_epoca NVARCHAR(10), @id_equipa INTEGER)
RETURNS int
AS 
BEGIN
	DECLARE @qtdVitoriasEquipa INT;
	
	SELECT @qtdVitoriasEquipa = SUM(QTD)  FROM (
	-- jogos em casa
		SELECT COUNT(1) QTD from (
			SELECT * FROM (
				SELECT
				COALESCE (sum(QtdGolosEquipa) ,0 )as QtdGolosEquipa, COALESCE (sum(QtdGolosEquipaAdversaria),0) as QtdGolosEquipaAdversaria 
				FROM (
				SELECT COUNT(1)QtdGolosEquipa,0 QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_casa = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_casa AND
				JC.nr_atleta=E.id_jogador_jogo 
				union all
				SELECT 0 QtdGolosEquipa,COUNT(1) QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_casa = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_fora AND
				JC.nr_atleta=E.id_jogador_jogo
				) A
			) A2
			where A2.QtdGolosEquipa > A2.QtdGolosEquipaAdversaria
		) B  
	UNION ALL
	-- jogos fora
		SELECT COUNT(1) QTD from (
			SELECT * FROM (
				SELECT
				COALESCE (sum(QtdGolosEquipa) ,0 )as QtdGolosEquipa, COALESCE (sum(QtdGolosEquipaAdversaria),0) as QtdGolosEquipaAdversaria 
				FROM (
				SELECT COUNT(1)QtdGolosEquipa,0 QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_fora = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_fora AND
				JC.nr_atleta=E.id_jogador_jogo 
				union all
				SELECT 0 QtdGolosEquipa,COUNT(1) QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_fora = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_CASA AND
				JC.nr_atleta=E.id_jogador_jogo 
				) C
			) C2
			where C2.QtdGolosEquipa > C2.QtdGolosEquipaAdversaria
		) D  
	) E
	RETURN (@qtdVitoriasEquipa);
END
GO

/* Função : fn_Derrotas : Esta função devolve a quantidade de Derrotas , para uma equipa numa epoca.
PARAMETROS ENTRADA:
	EPOCA
	EQUIPA
PARAMETROS DE SAÍDA
	Quantidade de Derrotas
*/

/* Função : fn_Derrotas : Esta função devolve a quantidade de Derrotas , para uma equipa numa epoca.
PARAMETROS ENTRADA:
	EPOCA
	EQUIPA
PARAMETROS DE SAÍDA
	Quantidade de Derrotas
*/


CREATE OR ALTER FUNCTION fn_Derrotas (@nr_epoca NVARCHAR(10), @id_equipa INTEGER)
RETURNS int
AS 
BEGIN
	DECLARE @qtdDerrotasEquipa INT;
	
	SELECT @qtdDerrotasEquipa = SUM(QTD)  FROM (
	-- jogos em casa
		SELECT COUNT(1) QTD from (
			SELECT * FROM (
				SELECT
				COALESCE (sum(QtdGolosEquipa) ,0 )as QtdGolosEquipa, COALESCE (sum(QtdGolosEquipaAdversaria),0) as QtdGolosEquipaAdversaria 
				FROM (
				SELECT COUNT(1)QtdGolosEquipa,0 QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_casa = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_casa AND
				JC.nr_atleta=E.id_jogador_jogo 
				union all
				SELECT 0 QtdGolosEquipa,COUNT(1) QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_casa = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_fora AND
				JC.nr_atleta=E.id_jogador_jogo
				) A
			) A2
			where A2.QtdGolosEquipa < A2.QtdGolosEquipaAdversaria
		) B  
	UNION ALL
	-- jogos fora
		SELECT COUNT(1) QTD from (
			SELECT * FROM (
				SELECT
				COALESCE (sum(QtdGolosEquipa) ,0 )as QtdGolosEquipa, COALESCE (sum(QtdGolosEquipaAdversaria),0) as QtdGolosEquipaAdversaria 
				FROM (
				SELECT COUNT(1)QtdGolosEquipa,0 QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_fora = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_fora AND
				JC.nr_atleta=E.id_jogador_jogo 
				union all
				SELECT 0 QtdGolosEquipa,COUNT(1) QtdGolosEquipaAdversaria FROM  Jogo J, Tipo_Evento TE, Evento E, Jogador_Contrato JC
				WHERE J.nr_epoca=@nr_epoca AND
				J.id_equipa_fora = @id_equipa AND
				UPPER(TE.nome)='GOLO' AND
				E.tipo=TE.id AND
				E.id_jogo=j.id AND
				JC.id_equipa=J.id_equipa_CASA AND
				JC.nr_atleta=E.id_jogador_jogo 
				) C
			) C2
			where C2.QtdGolosEquipa < C2.QtdGolosEquipaAdversaria
		) D  
	) E
	RETURN (@qtdDerrotasEquipa);
END
GO

/*fn_PontosEquipa:  Função que devolve os pontos de uma equipa numa determinada época*/
CREATE OR ALTER FUNCTION fn_PontosEquipa(@epoca NVARCHAR(10), @id_equipa INT)
RETURNS INT
AS 
BEGIN
	DECLARE @pontos_vitorias INT,
		 	@pontos_empates INT; 

	SET @pontos_vitorias = (dbo.fn_Vitorias(@epoca, @id_equipa) * 3);
	-- Em construção:
	-- SET @pontos_empates = (dbo.fn_Empates(@epoca, @id_equipa));

	RETURN (@pontos_vitorias + @pontos_empates);
END
