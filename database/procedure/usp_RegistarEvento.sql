-- Procedimento que regista eventos aliados a um jogo.
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
