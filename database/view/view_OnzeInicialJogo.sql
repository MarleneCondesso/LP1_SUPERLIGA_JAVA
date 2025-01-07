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

