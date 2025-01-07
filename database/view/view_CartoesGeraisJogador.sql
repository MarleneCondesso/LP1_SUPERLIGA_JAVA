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
