CREATE OR ALTER VIEW  view_MelhoresMarcadoresGerais
AS
SELECT 
J.nr_atleta AS 'AtletaNr',
J.nome as 'AtletaNome',
JG.nr_epoca as 'Epoca',
JG.nr_jornada as 'Jornada',
COUNT(1) as 'Golos'
FROM
Tipo_Evento T , Evento E, Jogador J,  jogo JG, Jogador_Jogo JJ
where
T.id='GL' AND
E.tipo=T.id AND
JJ.id_jogo=E.id_jogo AND
JJ.nr_atleta=E.id_jogador_jogo AND 
J.nr_atleta=E.id_jogador_jogo AND
JG.id=E.id_jogo  
GROUP BY J.nr_atleta,J.nome,JG.nr_epoca, JG.nr_jornada 