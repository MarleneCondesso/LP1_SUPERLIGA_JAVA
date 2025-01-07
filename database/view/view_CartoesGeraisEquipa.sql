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
