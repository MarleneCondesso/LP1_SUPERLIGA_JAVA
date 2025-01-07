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
