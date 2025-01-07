
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
