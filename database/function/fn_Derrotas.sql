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