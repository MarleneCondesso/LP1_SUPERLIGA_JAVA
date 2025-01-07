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