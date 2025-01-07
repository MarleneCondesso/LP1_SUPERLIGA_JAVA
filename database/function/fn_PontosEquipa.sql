-- Função que devolve os pontos de uma equipa numa determinada época
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