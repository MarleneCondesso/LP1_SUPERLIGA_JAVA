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