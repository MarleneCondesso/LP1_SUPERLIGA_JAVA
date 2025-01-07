CREATE OR ALTER VIEW  view_MelhoresEquipasMarcadores
AS
SELECT 
P.id AS 'EquipaId',
P.nome as 'EquipaNome',
JG.nr_epoca as 'Epoca',
JG.nr_jornada as 'Jornada',
COUNT(1) as 'Golos'
FROM
Tipo_Evento T, Evento E, Jogador J, Jogador_Contrato JC, Equipa P, jogo JG
where
T.id='GL' AND
E.tipo=T.id AND
J.nr_atleta=E.id_jogador_jogo AND
JC.nr_atleta=J.nr_atleta AND
P.id=JC.id_equipa AND
JG.id=E.id_jogo AND
JC.data_entrada_atleta  <= JG.data_hora_jogo AND
(JC.data_saida_atleta IS NULL OR JC.data_saida_atleta > JG.data_hora_jogo)
GROUP BY P.id,P.nome,JG.nr_epoca, JG.nr_jornada  
